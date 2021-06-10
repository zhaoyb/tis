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
package com.qlangtech.tis.plugin.ds;

import com.alibaba.citrus.turbine.Context;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.qlangtech.tis.TIS;
import com.qlangtech.tis.extension.Describable;
import com.qlangtech.tis.extension.Descriptor;
import com.qlangtech.tis.plugin.IdentityName;
import com.qlangtech.tis.runtime.module.misc.IControlMsgHandler;
import com.qlangtech.tis.util.IPluginContext;
import org.apache.commons.lang.StringUtils;

import java.sql.*;
import java.util.*;

/**
 * Abstract the dataSource modal
 *
 * @author: baisui 百岁
 * @create: 2020-11-24 10:40
 **/
public abstract class DataSourceFactory implements Describable<DataSourceFactory>, IdentityName, DataSourceMeta {

    public static final String DS_TYPE_MYSQL = "MySQL";

//    public static List<DataSourceFactory> all() {
//        return TIS.get().getExtensionList(DataSourceFactory.class);
//    }


    /**
     * DataSource like TiSpark has store format as RDD shall skip the phrase of data dump
     *
     * @return
     */
    public boolean skipDumpPhrase() {
        return false;
    }

    /**
     * Get all the dump
     *
     * @return
     */
    public DataDumpers getDataDumpers(TISTable table) {
        throw new UnsupportedOperationException("datasource:" + this.identityValue() + " is not support direct dump");
    }

    @Override
    public final Descriptor<DataSourceFactory> getDescriptor() {
        Descriptor<DataSourceFactory> descriptor = TIS.get().getDescriptor(this.getClass());
        if (!(descriptor instanceof BaseDataSourceFactoryDescriptor)) {
            throw new IllegalStateException(this.getClass().getSimpleName() + " must implement the Descriptor of "
                    + BaseDataSourceFactoryDescriptor.class.getSimpleName());
        }
        return descriptor;
    }

    protected static void validateConnection(String jdbcUrl, String username, String password, BasicDataSourceFactory.IConnProcessor p) {
        Connection conn = null;
        try {
            conn = getConnection(jdbcUrl, username, password);
            p.vist(conn);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Throwable e) {
                }
            }
        }
    }

    protected static Connection getConnection(String jdbcUrl, String username, String password) throws SQLException {
        // 密码可以为空
        return DriverManager.getConnection(jdbcUrl, username, StringUtils.trimToNull(password));
    }


    protected List<ColumnMetaData> parseTableColMeta(String table, String userName, String password, String jdbcUrl) {
        final List<ColumnMetaData> columns = Lists.newArrayList();
        validateConnection(jdbcUrl, userName, password, (conn) -> {
            DatabaseMetaData metaData1 = null;
            ResultSet primaryKeys = null;
            ResultSet columns1 = null;
            try {
                metaData1 = conn.getMetaData();
                primaryKeys = metaData1.getPrimaryKeys(null, null, table);
                columns1 = metaData1.getColumns(null, null, table, null);
                Set<String> pkCols = Sets.newHashSet();
                while (primaryKeys.next()) {
                    // $NON-NLS-1$
                    String columnName = primaryKeys.getString("COLUMN_NAME");
                    pkCols.add(columnName);
                }
                int i = 0;
                String colName = null;
                while (columns1.next()) {
                    columns.add(new ColumnMetaData((i++), (colName = columns1.getString("COLUMN_NAME"))
                            , columns1.getInt("DATA_TYPE"), pkCols.contains(colName)));
                }

            } finally {
                closeResultSet(columns1);
                closeResultSet(primaryKeys);
            }
        });
        return columns;
    }

    protected void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                // ignore
                ;
            }
        }
    }

    public abstract static class BaseDataSourceFactoryDescriptor extends Descriptor<DataSourceFactory> {
        @Override
        public final String getDisplayName() {
            return this.getDataSourceName();
        }

        @Override
        public final Map<String, Object> getExtractProps() {
            Map<String, Object> eprops = new HashMap<>();
            eprops.put("supportFacade", this.supportFacade());
            eprops.put("facadeSourceTypes", this.facadeSourceTypes());
            return eprops;
        }

        /**
         * Get DB name
         *
         * @return
         */
        protected abstract String getDataSourceName();

        /**
         * Support facade datasource for incr process
         *
         * @return
         */
        protected abstract boolean supportFacade();

        protected List<String> facadeSourceTypes() {
            if (supportFacade()) {
                throw new UnsupportedOperationException("shall overwrite facadeSourceTypes");
            }
            return Collections.emptyList();
        }

        @Override
        protected boolean validate(IControlMsgHandler msgHandler, Context context, PostFormVals postFormVals) {

            ParseDescribable<DataSourceFactory> ds
                    = this.newInstance((IPluginContext) msgHandler, postFormVals.rawFormData, Optional.empty());

            try {
                List<String> tables = ds.instance.getTablesInDB();
                msgHandler.addActionMessage(context, "find " + tables.size() + " table in db");
            } catch (Exception e) {
                msgHandler.addErrorMessage(context, e.getMessage());
                return false;
            }

            return true;
        }

    }
}
