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

package com.qlangtech.tis.manage;

import com.qlangtech.tis.datax.impl.DataxProcessor;
import com.qlangtech.tis.manage.impl.DataFlowAppSource;
import com.qlangtech.tis.manage.impl.SingleTableAppSource;
import com.qlangtech.tis.sql.parser.tuple.creator.IStreamIncrGenerateStrategy;

/**
 * @author: 百岁（baisui@qlangtech.com）
 * @create: 2021-04-27 15:26
 **/
public interface IBasicAppSource extends IAppSource, IStreamIncrGenerateStrategy {
    <T> T accept(IAppSourceVisitor<T> visitor);

    interface IAppSourceVisitor<T> {

        T visit(SingleTableAppSource single);

        T visit(DataFlowAppSource dataflow);

//        default public T visit(ISolrAppSource app) {
//            return null;
//        }


        default public T visit(DataxProcessor app) {
            return null;
        }
    }
}
