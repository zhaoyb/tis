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
package com.qlangtech.tis.solrextend.fieldtype.s4WaitingUser;

import java.util.List;
import org.apache.lucene.index.IndexableField;
import org.json.JSONObject;
import com.qlangtech.tis.solrextend.fieldtype.JSONField2;

/**
 * Created by . on 2018/5/24.
 *
 * @author 百岁（baisui@qlangtech.com）
 * @date 2019年1月17日
 */
public class WaitingUserJsonParseField extends JSONField2 {

    private String TYPE_FIELD_NAME = "is_ent_takeout";

    @Override
    protected void addExtraField(List<IndexableField> result, String key, String val) {
    }

    @Override
    protected void addExtraField(List<IndexableField> result, JSONObject extension) {
        result.add(schema.getFieldOrNull(getPropPrefix() + TYPE_FIELD_NAME).createField(extension.isNull("companyId") ? "0" : "1"));
    }
}
