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
package com.qlangtech.tis.runtime.module.action;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import com.alibaba.citrus.turbine.Context;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.qlangtech.tis.manage.PermissionConstant;
import com.qlangtech.tis.manage.biz.dal.pojo.ServerGroup;
import com.qlangtech.tis.manage.biz.dal.pojo.ServerGroupCriteria;
import com.qlangtech.tis.manage.biz.dal.pojo.ServerPool;
import com.qlangtech.tis.manage.biz.dal.pojo.ServerPoolCriteria;
import com.qlangtech.tis.manage.spring.aop.Func;
import com.qlangtech.tis.pubhook.common.RunEnvironment;
import junit.framework.Assert;

/**
 * 响应所有组内的请求
 *
 * @author 百岁（baisui@qlangtech.com）
 * @date 2012-4-1
 */
public abstract class GroupAction extends BasicModule {

    private static final long serialVersionUID = 1L;

    // @Func(PermissionConstant.PERMISSION_BASE_DATA_MANAGE)
    public void doChangeEnvironment(Context context) throws Exception {
        Integer appid = this.getInt("appid");
        Integer runtime = this.getInt("runtime");
        ServerGroupCriteria query = new ServerGroupCriteria();
        query.createCriteria().andRuntEnvironmentEqualTo(runtime.shortValue()).andAppIdEqualTo(appid).andNotDelete();
        query.setOrderByClause("group_index desc");
        List<ServerGroup> queryResult = this.getServerGroupDAO().selectByExample(query);
        int groupIndex = 0;
        for (com.qlangtech.tis.manage.biz.dal.pojo.ServerGroup group : queryResult) {
            groupIndex = group.getGroupIndex() + 1;
            break;
        }
        getResponse().getWriter().write(String.valueOf(groupIndex));
    }

    @Override
    public boolean isEnableDomainView() {
        return false;
    }

    /**
     * @param context
     * @throws Exception
     */
    public void doAddServerSuggest(Context context) throws Exception {
        final String query = this.getString("query");
        final RunEnvironment envir = RunEnvironment.getEnum(this.getString("envir"));
        List<ServerPool> serverList = Collections.emptyList();
        if (StringUtils.isNotBlank(query)) {
            ServerPoolCriteria criteria = new ServerPoolCriteria();
            ServerPoolCriteria.Criteria and = criteria.createCriteria().andIpAddressLike('%' + query + '%').andRuntEnvironmentEqualTo(envir.getId());
            criteria.or(criteria.createCriteria().andServerNameLike('%' + query + '%').andRuntEnvironmentEqualTo(envir.getId()));
            serverList = this.getServerPoolDAO().selectByExample(criteria);
        }
        writeSuggest2Response(query, serverList, new SuggestCallback<ServerPool>() {

            @Override
            public String getLiteral(ServerPool o) {
                return o.getServerName() + "[" + o.getIpAddress() + "]";
            }

            @Override
            public Object getValue(ServerPool o) {
                return o.getSpId();
            }
        }, getResponse());
    }

    public static <T> void writeSuggest2Response(final String query, List<T> serverList, SuggestCallback<T> callback, HttpServletResponse response) throws JSONException, IOException {
        JSONArray suggestions = new JSONArray();
        JSONArray d = new JSONArray();
        for (int i = 0; i < serverList.size(); i++) {
            T pool = serverList.get(i);
            suggestions.put(i, callback.getLiteral(pool));
            d.put(i, callback.getValue(pool));
        }
        JSONObject json = new JSONObject();
        json.put("query", query);
        json.put("suggestions", suggestions);
        json.put("data", d);
        response.setContentType("json;charset=UTF-8");
        // this.getResponse().getWriter().write(j);
        json.write(response.getWriter());
    }

    public interface SuggestCallback<T> {

        public Object getValue(T o);

        public String getLiteral(T o);
    }

    /**
     * 添加一个组
     *
     * @param context
     * @throws Exception
     */
    @Func(PermissionConstant.APP_SERVER_GROUP_SET)
    public void doAddGroup(Context context) throws Exception {
        RunEnvironment runtime = RunEnvironment.getEnum(this.getShort("runtime"));
        Integer groupIndex = this.getInt("groupIndex");
        Integer appid = this.getInt("appid");
        if (groupIndex == null) {
            this.addErrorMessage(context, "请填写组编号");
            return;
        }
        if (runtime == null) {
            this.addErrorMessage(context, "请选择运行环境");
            return;
        }
        Integer publishSnapshotId = -1;
        createGroup(context, runtime, groupIndex, appid, publishSnapshotId, this);
        this.addActionMessage(context, "【" + runtime.getDescribe() + "】中新创建一条服务器组成功");
    }

    public static Integer createGroup(Context context, RunEnvironment runtime, Integer groupIndex, Integer appid, Integer publishSnapshotId, BasicModule basicModule) {
        Assert.assertNotNull(appid);
        ServerGroupCriteria query = new ServerGroupCriteria();
        query.createCriteria().andRuntEnvironmentEqualTo(runtime.getId()).andGroupIndexEqualTo(groupIndex.shortValue()).andAppIdEqualTo(appid).andNotDelete();
        if (basicModule.getServerGroupDAO().countByExample(query) > 0) {
            basicModule.addErrorMessage(context, "gruop index重复");
            return null;
        }
        ServerGroup group = new ServerGroup();
        group.setRuntEnvironment(runtime.getId());
        group.setGroupIndex(groupIndex.shortValue());
        group.setCreateTime(new Date());
        group.setAppId(appid);
        if (publishSnapshotId > 0) {
            group.setPublishSnapshotId(publishSnapshotId);
        }
        return basicModule.getServerGroupDAO().insertSelective(group);
    }

    /**
     * 删除服务器
     *
     * @param context
     * @throws Exception
     */
    @Func(PermissionConstant.APP_SERVER_SET)
    public void doDeleteServer(Context context) throws Exception {
        Integer serverId = this.getInt("serverid");
        Assert.assertNotNull("serverId can not be null", serverId);
        getResponse().getWriter().write("delete success");
    }

    /**
     * 删除服务器组
     *
     * @param context
     * @throws Exception
     */
    @Func(PermissionConstant.APP_SERVER_GROUP_SET)
    public void doDeleteGroup(Context context) throws Exception {
        final Integer groupid = this.getInt("groupid");
        Assert.assertNotNull("groupid can not be null", groupid);
        ServerGroupCriteria query = new ServerGroupCriteria();
        query.createCriteria().andGidEqualTo(groupid);
        // 删除group
        this.getServerGroupDAO().deleteByExample(query);
        this.addActionMessage(context, "成功删除组ID：" + groupid);
    }
}
