/**
 * Copyright (c) 2020 QingLang, Inc. <baisui@qlangtech.com>
 *
 * This program is free software: you can use, redistribute, and/or modify
 * it under the terms of the GNU Affero General Public License, version 3
 * or later ("AGPL"), as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.qlangtech.tis.config.module.action;

import java.io.InputStream;
import java.util.Date;
import java.util.List;
import com.alibaba.citrus.turbine.Context;
import com.qlangtech.tis.manage.common.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.qlangtech.tis.manage.PermissionConstant;
import com.qlangtech.tis.manage.biz.dal.dao.IDepartmentDAO;
import com.qlangtech.tis.manage.biz.dal.pojo.Application;
import com.qlangtech.tis.manage.biz.dal.pojo.ApplicationCriteria;
import com.qlangtech.tis.manage.biz.dal.pojo.Department;
import com.qlangtech.tis.manage.biz.dal.pojo.DepartmentCriteria;
import com.qlangtech.tis.manage.biz.dal.pojo.ServerGroup;
import com.qlangtech.tis.manage.biz.dal.pojo.ServerGroupCriteria;
import com.qlangtech.tis.manage.biz.dal.pojo.Snapshot;
import com.qlangtech.tis.manage.biz.dal.pojo.UploadResource;
import com.qlangtech.tis.manage.servlet.BasicServlet;
import com.qlangtech.tis.manage.spring.aop.Func;
import com.qlangtech.tis.pubhook.common.RunEnvironment;
import com.qlangtech.tis.runtime.module.action.AddAppAction;
import com.qlangtech.tis.runtime.module.action.BasicModule;
import com.qlangtech.tis.runtime.module.action.SchemaAction.CreateSnapshotResult;
import com.qlangtech.tis.runtime.module.action.jarcontent.SaveFileContentAction;
import com.qlangtech.tis.runtime.pojo.ConfigPush;
import com.qlangtech.tis.runtime.pojo.ResSynManager;

/**
 * 负责接收日常向线上发送的应用同步请求
 *
 * @author 百岁（baisui@qlangtech.com）
 * @date 2016年10月20日
 */
public class AppSynAction extends BasicModule {

    private static final long serialVersionUID = 1L;

    // private ITerminatorTriggerBizDalDAOFacade triggerContext;
    /**
     * 接收从日常环境中推送上来的配置文件，<br>
     * 当第一次推送的时候线上还不存在索引实例的时候，会自动创建索引实例
     *
     * @param context
     * @throws Exception
     */
    @Func(PermissionConstant.APP_ADD)
    public void doInitAppFromDaily(Context context) throws Exception {
        // if (ManageUtils.isDevelopMode()) {
        // throw new RuntimeException("this method shall execute online");
        // }
        String content = null;
        try (InputStream reader = DefaultFilter.getReqeust().getInputStream()) {
            content = IOUtils.toString(reader, getEncode());
            if (StringUtils.isEmpty(content)) {
                throw new IllegalArgumentException("upload content can not be null");
            }
        }
        final ConfigPush configPush = (ConfigPush) HttpConfigFileReader.xstream.fromXML(content);
        final String collection = configPush.getCollection();
        // 校验当前的snapshot 版本是否就是传输上来的snapshot版本
        ServerGroup serverGroup = null;
        if (configPush.getRemoteSnapshotId() != null) {
            serverGroup = this.getServerGroupDAO().load(collection, (short) 0, /* groupIndex */
            RunEnvironment.ONLINE.getId());
            if (serverGroup.getPublishSnapshotId() != (configPush.getRemoteSnapshotId() + 0)) {
                this.addErrorMessage(context, "exist snapshotid:" + serverGroup.getPublishSnapshotId() + " is not equal push snapshotid:" + configPush.getRemoteSnapshotId());
                return;
            }
        }
        // List<UploadResource> resources = configPush.getUploadResources();
        Snapshot snapshot = null;
        SnapshotDomain snapshotDomain = null;
        Application app = null;
        ApplicationCriteria criteria = new ApplicationCriteria();
        criteria.createCriteria().andProjectNameEqualTo(collection);
        List<Application> apps = this.getApplicationDAO().selectByExample(criteria);
        for (Application p : apps) {
            app = p;
            break;
        }
        if (app == null) {
            // 在服务端创建新应用
            app = new Application();
        // Integer newAppid = this.createNewApp(context, configPush);
        // app.setAppId(newAppid);
        }
        String snycDesc = "NEW CREATE";
        serverGroup = this.getServerGroupDAO().load(collection, (short) 0, /* groupIndex */
        RunEnvironment.ONLINE.getId());
        boolean newSnapshot = false;
        if (serverGroup == null || serverGroup.getPublishSnapshotId() == null) {
            snapshot = new Snapshot();
            snapshot.setSnId(-1);
            snapshot.setPreSnId(-1);
            snapshot.setAppId(app.getAppId());
            newSnapshot = true;
        } else {
            snycDesc = "PUSH FROM DAILY";
            snapshotDomain = this.getSnapshotViewDAO().getView(configPush.getRemoteSnapshotId());
            snapshot = snapshotDomain.getSnapshot();
        }
        if (snapshot == null) {
            throw new IllegalStateException("snapshot can not be null,collection:" + collection);
        }
        snapshot.setCreateUserId(0l);
        snapshot.setCreateUserName(configPush.getReception());
        // ///////////////////////////////////
        // 组装新的snapshot
        PropteryGetter pGetter = null;
        for (UploadResource res : configPush.getUploadResources()) {
            pGetter = ConfigFileReader.createPropertyGetter(res.getResourceType());
            // 校验配置是否相等
            if (!newSnapshot) {
                final String md5 = ConfigFileReader.md5file(res.getContent());
                if (StringUtils.equals(md5, pGetter.getMd5CodeValue(snapshotDomain))) {
                    this.addErrorMessage(context, "resource " + pGetter.getFileName() + " is newest,shall not be updated");
                    return;
                }
            }
            Integer newResId = ResSynManager.createNewResource(context, res.getContent(), ConfigFileReader.md5file(res.getContent()), pGetter, this, this);
            snapshot = pGetter.createNewSnapshot(newResId, snapshot);
        }
        serverGroup = new ServerGroup();
        serverGroup.setPublishSnapshotId(SaveFileContentAction.createNewSnapshot(snapshot, snycDesc, this, 0l, configPush.getReception()));
        serverGroup.setUpdateTime(new Date());
        ServerGroupCriteria serverGroupCriteria = new ServerGroupCriteria();
        serverGroupCriteria.createCriteria().andAppIdEqualTo(app.getAppId()).andRuntEnvironmentEqualTo(RunEnvironment.ONLINE.getId()).andGroupIndexEqualTo((short) 0);
        this.getServerGroupDAO().updateByExampleSelective(serverGroup, serverGroupCriteria);
        // /////////////////////////////////////
        this.addActionMessage(context, "synsuccess");
    }

    // protected Integer createNewApp(Context context, final ConfigPush configPush) throws Exception {
    // Department department = configPush.getDepartment();
    // Assert.assertNotNull("department can not be null", department);
    // final String dptFullName = department.getFullName();
    // Department dpt = getDpt(dptFullName);
    // if (dpt == null) {
    // // 该部门还没有被创建
    // insertDepartment(dptFullName, getDepartmentDAO(), 0, 0);
    // dpt = getDpt(dptFullName);
    // Assert.assertNotNull("dpt can not be null", dpt);
    // }
    // Application app = new Application();
    // app.setAppId(null);
    // app.setDptId(dpt.getDptId());
    // app.setDptName(dpt.getFullName());
    // app.setCreateTime(new Date());
    // app.setUpdateTime(new Date());
    // app.setIsAutoDeploy(true);
    // app.setProjectName(configPush.getCollection());
    // app.setRecept(configPush.getReception());
    // app.setNobleAppId(0);
    // app.setNobleAppName("default");
    // // 创建应用
    // CreateSnapshotResult newid = AddAppAction.createApplication(app, -1, /* publishSnapshotId */
    // null, /* schemaContent */
    // context, this, triggerContext);
    // return newid.getNewAppId();
    // }
    public static // User
    void insertDepartment(// User
    String departmentName, // User
    final IDepartmentDAO dptDAO, // User
    int iterateCount, // user
    Integer aliGroupDptId) {
        String[] dptary = StringUtils.split(departmentName, "-");
        Department department = null;
        Integer parentDptId = null;
        StringBuffer parentPath = new StringBuffer();
        for (int i = 0; i < dptary.length; i++) {
            parentPath.append(dptary[i]);
            if (!hasNode(dptDAO, parentPath.toString())) {
                // 新建一个节点
                // 插入新的部门
                department = new Department();
                department.setParentId((parentDptId == null) ? -1 : parentDptId);
                department.setFullName(parentPath.toString());
                department.setName(dptary[i]);
                if ((i + 1) == dptary.length) {
                    department.setAlibabaDptId(aliGroupDptId);
                }
                department.setGmtCreate(new Date());
                department.setGmtModified(new Date());
                department.setLeaf((i + 1) == dptary.length);
                // System.out.println("inser :" + departmentName
                // + " iterateCount:" + iterateCount);
                parentDptId = dptDAO.insertSelective(department);
            } else {
                parentDptId = getParentId(dptDAO, parentPath.toString());
                if ((i + 1) == dptary.length) {
                    department = new Department();
                    department.setAlibabaDptId(aliGroupDptId);
                    DepartmentCriteria q = new DepartmentCriteria();
                    q.createCriteria().andDptIdEqualTo(parentDptId);
                    dptDAO.updateByExampleSelective(department, q);
                    return;
                }
            }
            if (i + 1 < dptary.length) {
                parentPath.append("-");
            }
        }
    }

    private static Integer getParentId(final IDepartmentDAO dptDAO, String parentName) {
        if (StringUtils.isNotEmpty(parentName)) {
            DepartmentCriteria dptCriteria = new DepartmentCriteria();
            dptCriteria.createCriteria().andFullNameEqualTo(parentName);
            for (Department dpt : dptDAO.selectByExample(dptCriteria)) {
                return dpt.getDptId();
            }
        }
        return null;
    }

    private static boolean hasNode(final IDepartmentDAO dptDAO, String name) {
        DepartmentCriteria dptCriteria = new DepartmentCriteria();
        dptCriteria.createCriteria().andFullNameEqualTo(name);
        return StringUtils.isNotEmpty(name) && dptDAO.countByExample(dptCriteria) > 0;
    }

    // @Autowired
    // public void setTerminatorTriggerBizDalDaoFacade(ITerminatorTriggerBizDalDAOFacade triggerDaoContext) {
    // this.triggerContext = triggerDaoContext;
    // }
    /**
     * 查询部门信息
     *
     * @param dptFullName
     * @return
     */
    private Department getDpt(String dptFullName) {
        DepartmentCriteria dptCriteria = new DepartmentCriteria();
        dptCriteria.createCriteria().andFullNameEqualTo(dptFullName);
        for (Department dpt : this.getDepartmentDAO().selectByExample(dptCriteria)) {
            return dpt;
        }
        return null;
    }
}
