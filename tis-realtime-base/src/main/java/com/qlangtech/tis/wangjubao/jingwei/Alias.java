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
package com.qlangtech.tis.wangjubao.jingwei;

import com.qlangtech.tis.realtime.transfer.IRowValueGetter;
import com.qlangtech.tis.realtime.transfer.UnderlineUtils;
import java.util.concurrent.Callable;

/**
 * @author 百岁（baisui@qlangtech.com）
 * @date 2016年10月14日
 */
public class Alias {

    private final String name;

    private final String toName;

    // 无中生有列
    public final ICreator creator;

    ITransfer valTransfer = NUll_TRANSFER;

    ILazyTransfer valLazyTransfer;

    final boolean copy;

    final boolean ignoreChange;

    // 是否是主键
    // = false;
    final boolean pk;

    // 该列是否为代表记录时间戳生成的列
    // = false;
    final boolean timeVer;

    private Alias(ICreator creator, String name, String toName, boolean copy, boolean ignoreChange, boolean pk, boolean timeVer) {
        super();
        this.name = name;
        this.toName = toName;
        this.creator = creator;
        this.copy = copy;
        this.ignoreChange = ignoreChange;
        this.pk = pk;
        this.timeVer = timeVer;
    }

    public ILazyTransfer getValLazyTransfer() {
        return this.valLazyTransfer;
    }

    @Override
    public String toString() {
        return "{" + "name='" + name + '\'' + ", toName='" + toName + '\'' + ", creator=" + creator + ", copy=" + copy + ", ignoreChange=" + ignoreChange + ", pk=" + pk + '}';
    }

    public static class Builder {

        private final String name;

        private final String toName;

        private boolean build = false;

        private ITransfer valTransfer;

        private ILazyTransfer valLazyTransfer;

        boolean copy = true;

        boolean ignoreChange = false;

        // 是否是主键
        boolean pk = false;

        // 该列是否为代表记录时间戳生成的列
        boolean timeVer = false;

        private ICreator creator;

        Builder(String name, String toName) {
            super();
            this.name = name;
            this.toName = toName;
            this.creator = null;
        }

        Builder(ICreator creator, String toName) {
            super();
            this.name = null;
            this.toName = toName;
            this.creator = creator;
        }

        public static Builder create(ICreator creator, String toName) {
            return (new Builder(creator, toName)).ignoreChange();
        }

        public static Builder $(String name, String toName) {
            return new Builder(name, toName);
        }

        public static Builder alias(String name, String toName) {
            return $(name, toName);
        }

        public static Builder $(String name) {
            return new Builder(name, name);
        }

        public static Builder alias(String name) {
            return $(name);
        }

        public Builder t(ITransfer valTransfer) {
            checkAccess();
            if (valTransfer == null) {
                throw new IllegalArgumentException("param valTransfer can not be null");
            }
            this.valTransfer = valTransfer;
            return this;
        }

        public Builder c(ILazyTransfer valTransfer) {
            checkAccess();
            if (valTransfer == null) {
                throw new IllegalArgumentException("param valTransfer can not be null");
            }
            this.valLazyTransfer = valTransfer;
            return this;
        }

        public Builder ignoreChange() {
            checkAccess();
            this.ignoreChange = true;
            return this;
        }

        /**
         * 在执行copy的时候本列忽略
         *
         * @return
         */
        public Builder notCopy() {
            checkAccess();
            this.copy = false;
            return this;
        }

        public Builder timestampVer() {
            this.timeVer = true;
            return this;
        }

        public Builder PK() {
            checkAccess();
            this.pk = true;
            return this;
        }

        public Alias build() {
            build = true;
            Alias result = null;
            if (this.creator != null) {
                result = new Alias(this.creator, null, this.toName, copy, ignoreChange, pk, timeVer);
            } else {
                result = new Alias(null, this.name, this.toName, copy, ignoreChange, pk, timeVer);
            }
            if (this.valTransfer != null) {
                result.valTransfer = this.valTransfer;
            }
            if (this.valLazyTransfer != null) {
                result.valLazyTransfer = this.valLazyTransfer;
            }
            return result;
        }

        private void checkAccess() {
            if (build) {
                throw new IllegalStateException(" can not add Alias.Builder after build");
            }
        }

        @Override
        public String toString() {
            return "name:" + this.name + ",toName:" + this.toName + ",ispk:" + this.pk + ",isCopy:" + this.copy + ",ignoreChange:" + this.ignoreChange;
        }

        public String getToName() {
            return this.toName;
        }
    }

    // 外键
    // private boolean fk = false;
    // 外键关联的表名称
    // private String fkRefTabName;
    // String getFkRefTabName() {
    // return this.fkRefTabName;
    // }
    public boolean isPk() {
        return pk;
    }

    // 啥都不做
    private static final ITransfer NUll_TRANSFER = new ITransfer() {

        @Override
        public Object process(IRowValueGetter row, String fieldValue) {
            return fieldValue;
        }

        @Override
        public String toString() {
            return "DummpTransfer";
        }
    };

    public ITransfer getValTransfer() {
        if (this.valTransfer == null) {
            throw new IllegalStateException("valTransfer can not be null");
        }
        return this.valTransfer;
    }

    public String getBeanPropName() {
        return UnderlineUtils.removeUnderline(this.getName()).toString();
    }

    public String getName() {
        return name;
    }

    public String getToName() {
        return toName;
    }

    @Override
    public int hashCode() {
        return (this.name + "_" + this.toName).hashCode();
    }

    /**
     * 创建字段
     */
    public interface ICreator {

        /**
         * @param row 整個一條記錄
         *            字段的值
         * @return
         */
        public Object process(IRowValueGetter row);
    }

    public interface ITransfer {

        /**
         * @param row 整個一條記錄
         *            字段的值
         * @return
         */
        public Object process(IRowValueGetter row, String fieldValue);
    }

    public interface ILazyTransfer {

        /**
         * @param row 整個一條記錄
         *            字段的值
         * @return
         */
        public Callable<Object> process(IRowValueGetter row);
    }

    public Object getVal(IRowValueGetter tab) {
        return getVal(tab, false);
    }

    public long getLong(IRowValueGetter row) {
        return Long.parseLong(String.valueOf(getVal(row, true)));
    }

    /**
     * 取得原始值
     *
     * @param tab
     * @return
     */
    public String getRawVal(IRowValueGetter tab) {
        if (this.creator != null) {
            throw new IllegalStateException("creator shall be null");
        }
        return tab.getColumn(this.getName());
    }

    public Object getVal(IRowValueGetter tab, boolean force) {
        if (tab == null) {
            throw new IllegalArgumentException("param tab can not be null");
        }
        Object val = null;
        if (force || this.copy) {
            if (this.creator != null) {
                val = this.creator.process(tab);
            } else {
                val = this.getValTransfer().process(tab, getRawVal(tab));
            }
            if (val == null && this.isPk()) {
                throw new IllegalStateException("row pk:" + this.getName() + " can not be null " + tab.toString());
            }
        }
        return val;
    }
}
