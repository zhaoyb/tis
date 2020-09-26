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
package com.koubei.web.tag.pager;

import org.apache.commons.lang.StringUtils;

/**
 * @author 百岁（baisui@qlangtech.com）
 * @date 2020/04/13
 */
public abstract class AroundTag {

    public abstract String getStart();

    public abstract String getEnd();

    public static AroundTag AroundTag1 = new AroundTag() {

        public String getStart() {
            return "<div class=\"pagination\">\n<ul>\n";
        }

        public String getEnd() {
            return "\n</ul></div>\n";
        }
    };

    public static AroundTag GlobalTag1 = new AroundTag() {

        public String getStart() {
            return "<div class='yk-pagination'>\n";
        }

        public String getEnd() {
            return "\n</div>\n";
        }
    };

    public static AroundTag NULL = new AroundTag() {

        public String getStart() {
            return StringUtils.EMPTY;
        }

        public String getEnd() {
            return StringUtils.EMPTY;
        }
    };
}
