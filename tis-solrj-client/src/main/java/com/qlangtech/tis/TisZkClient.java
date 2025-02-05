/**
 * Copyright (c) 2020 QingLang, Inc. <baisui@qlangtech.com>
 * <p>
 * This program is free software: you can use, redistribute, and/or modify
 * it under the terms of the GNU Affero General Public License, version 3
 * or later ("AGPL"), as published by the Free Software Foundation.
 * <p>
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.
 * <p>
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.qlangtech.tis;

import com.qlangtech.tis.cloud.ITISCoordinator;
import com.qlangtech.tis.solr.common.cloud.ZkRepeatClientConnectionStrategy;
import org.apache.solr.common.cloud.*;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 百岁（baisui@qlangtech.com）
 * @date 2019年1月17日
 */
public class TisZkClient implements ITISCoordinator {

    private final List<OnReconnect> reconnectList;

    @Override
    public TisZkClient unwrap() {
        return this;
    }

    @Override
    public void create(String path, byte[] data, boolean persistent, boolean sequential) {
        //throw new UnsupportedOperationException();
        CreateMode createMode = null;
        if (persistent) {
            createMode = sequential ? CreateMode.PERSISTENT_SEQUENTIAL : CreateMode.PERSISTENT;
        } else {
            createMode = sequential ? CreateMode.EPHEMERAL_SEQUENTIAL : CreateMode.EPHEMERAL;
        }
        try {
            zkclient.create(path, data, createMode, true);
        } catch (Exception e) {
            throw new RuntimeException("path:" + path, e);
        }
    }

    @Override
    public boolean shallConnect2RemoteIncrStatusServer() {
        return true;
    }

    private final SolrZkClient zkclient;

    public TisZkClient(String zkServerAddress, int zkClientTimeout) {
        this(zkServerAddress, zkClientTimeout, new ArrayList<>());
    }

    /**
     * @param zkServerAddress
     * @param zkClientTimeout
     */
    public TisZkClient(String zkServerAddress, int zkClientTimeout, final List<OnReconnect> reconnectList) {
        // this.zkclient = ;
        this(new TisSolrZkClient(zkServerAddress, zkClientTimeout, zkClientTimeout, new ZkRepeatClientConnectionStrategy(), new OnReconnect() {

            @Override
            public void command() {
                try {
                    for (OnReconnect re : reconnectList) {
                        re.command();
                    }
                } catch (KeeperException.SessionExpiredException e) {
                    throw new RuntimeException(e);
                }
            }
        }), reconnectList);
    }

    public TisZkClient(SolrZkClient zkclient, List<OnReconnect> reconnectList) {
        this.zkclient = zkclient;
        this.reconnectList = reconnectList;
    }

    public SolrZkClient getZK() {
        return this.zkclient;
    }

    private void addOnReconnect(OnReconnect conn) {
        this.reconnectList.add(conn);
    }

    @Override
    public void addOnReconnect(IOnReconnect onReconnect) {
        this.addOnReconnect(new OnReconnect() {

            @Override
            public void command() throws KeeperException.SessionExpiredException {
                onReconnect.command();
            }
        });
    }

    public int getZkClientTimeout() {
        return zkclient.getZkClientTimeout();
    }

    public boolean equals(Object obj) {
        return zkclient.equals(obj);
    }

    public ConnectionManager getConnectionManager() {
        return zkclient.getConnectionManager();
    }

    public ZkClientConnectionStrategy getZkClientConnectionStrategy() {
        return zkclient.getZkClientConnectionStrategy();
    }

    public boolean isConnected() {
        return zkclient.isConnected();
    }

    public void delete(String path, int version, boolean retryOnConnLoss) throws InterruptedException, KeeperException {
        zkclient.delete(path, version, retryOnConnLoss);
    }

    public Stat exists(String path, Watcher watcher, boolean retryOnConnLoss) throws KeeperException, InterruptedException {
        return zkclient.exists(path, watcher, retryOnConnLoss);
    }

    public boolean exists(String path, boolean retryOnConnLoss) {
        try {
            return zkclient.exists(path, retryOnConnLoss);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getChildren(String path, Watcher watcher, boolean retryOnConnLoss) {
        try {
            return zkclient.getChildren(path, watcher, retryOnConnLoss);
        } catch (Exception e) {
            throw new RuntimeException(path, e);
        }
    }

    public byte[] getData(String path, Watcher watcher, Stat stat, boolean retryOnConnLoss) {
        try {
            return zkclient.getData(path, watcher, stat, retryOnConnLoss);
        } catch (Exception e) {
            throw new RuntimeException(path, e);
        }
    }

    public Stat setData(String path, byte[] data, int version, boolean retryOnConnLoss) throws KeeperException, InterruptedException {
        return zkclient.setData(path, data, version, retryOnConnLoss);
    }

    public String create(String path, byte[] data, CreateMode createMode, boolean retryOnConnLoss) throws KeeperException, InterruptedException {
        return zkclient.create(path, data, createMode, retryOnConnLoss);
    }

    public void makePath(String path, boolean retryOnConnLoss) throws KeeperException, InterruptedException {
        zkclient.makePath(path, retryOnConnLoss);
    }

    public void makePath(String path, boolean failOnExists, boolean retryOnConnLoss) throws KeeperException, InterruptedException {
        zkclient.makePath(path, failOnExists, retryOnConnLoss);
    }

    public void makePath(String path, File file, boolean failOnExists, boolean retryOnConnLoss) throws IOException, KeeperException, InterruptedException {
        zkclient.makePath(path, file, failOnExists, retryOnConnLoss);
    }

    public void makePath(String path, File file, boolean retryOnConnLoss) throws IOException, KeeperException, InterruptedException {
        zkclient.makePath(path, file, retryOnConnLoss);
    }

    public void makePath(String path, CreateMode createMode, boolean retryOnConnLoss) throws KeeperException, InterruptedException {
        zkclient.makePath(path, createMode, retryOnConnLoss);
    }

    public void makePath(String path, byte[] data, boolean retryOnConnLoss) throws KeeperException, InterruptedException {
        zkclient.makePath(path, data, retryOnConnLoss);
    }

    public void makePath(String path, byte[] data, CreateMode createMode, boolean retryOnConnLoss) throws KeeperException, InterruptedException {
        zkclient.makePath(path, data, createMode, retryOnConnLoss);
    }

    public void makePath(String path, byte[] data, CreateMode createMode, Watcher watcher, boolean retryOnConnLoss) throws KeeperException, InterruptedException {
        zkclient.makePath(path, data, createMode, watcher, retryOnConnLoss);
    }

    public void makePath(String path, byte[] data, CreateMode createMode
            , Watcher watcher, boolean failOnExists, boolean retryOnConnLoss) throws KeeperException, InterruptedException {
        zkclient.makePath(path, data, createMode, watcher, failOnExists, retryOnConnLoss);
    }

    public void makePath(String zkPath, CreateMode createMode, Watcher watcher, boolean retryOnConnLoss) throws KeeperException, InterruptedException {
        zkclient.makePath(zkPath, createMode, watcher, retryOnConnLoss);
    }

    public Stat setData(String path, byte[] data, boolean retryOnConnLoss) throws KeeperException, InterruptedException {
        return zkclient.setData(path, data, retryOnConnLoss);
    }

    public Stat setData(String path, File file, boolean retryOnConnLoss) throws IOException, KeeperException, InterruptedException {
        return zkclient.setData(path, file, retryOnConnLoss);
    }

    public List<OpResult> multi(Iterable<Op> ops, boolean retryOnConnLoss) throws InterruptedException, KeeperException {
        return zkclient.multi(ops, retryOnConnLoss);
    }

    public void printLayout(String path, int indent, StringBuilder string) throws KeeperException, InterruptedException {
        zkclient.printLayout(path, indent, string);
    }

    public void printLayoutToStdOut() throws KeeperException, InterruptedException {
        zkclient.printLayoutToStdOut();
    }

    public boolean isClosed() {
        return zkclient.isClosed();
    }

    public SolrZooKeeper getSolrZooKeeper() {
        return zkclient.getSolrZooKeeper();
    }

    public void clean(String path) throws InterruptedException, KeeperException {
        zkclient.clean(path);
    }

    public String getZkServerAddress() {
        return zkclient.getZkServerAddress();
    }

    public ZkACLProvider getZkACLProvider() {
        return zkclient.getZkACLProvider();
    }
}
