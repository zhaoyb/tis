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
package com.qlangtech.tis.indexbuilder.merger;

import com.qlangtech.tis.fs.IPath;
import com.qlangtech.tis.fs.ITISFileSystem;

/**
 * @author 百岁（baisui@qlangtech.com）
 * @date 2019年1月17日
 */
public class LuceneIndexFileNameFilter implements ITISFileSystem.IPathFilter {

    private static final LuceneIndexFileNameFilter singleton = new LuceneIndexFileNameFilter();

    public static LuceneIndexFileNameFilter getFilter() {
        return singleton;
    }

    private LuceneIndexFileNameFilter() {
    }

    public boolean accept(IPath path) {
        return true;
    }
}
