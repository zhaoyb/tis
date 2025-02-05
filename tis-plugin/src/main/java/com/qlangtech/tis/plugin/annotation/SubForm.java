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
package com.qlangtech.tis.plugin.annotation;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.fastjson.JSONObject;
import com.qlangtech.tis.extension.IPropertyType;
import com.qlangtech.tis.extension.impl.SuFormProperties;
import com.qlangtech.tis.runtime.module.misc.IControlMsgHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

/**
 * plugin form can have an sub form ,where build form needs multi step
 *
 * @author 百岁（baisui@qlangtech.com）
 * @date 2021-04-10 19:25
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface SubForm {
    // get describe form bean class
    Class<?> desClazz();

    /**
     * 至少选一个
     *
     * @return
     */
    boolean atLeastOne() default true;

    /**
     * id list fetch method name which owned to describable plugin instance
     *
     * @return
     */
    String idListGetScript();

    interface ISubFormItemValidate {
        public boolean validateSubFormItems(IControlMsgHandler msgHandler, Context context, SuFormProperties props
                , IPropertyType.SubFormFilter subFormFilter, Map<String, /*** attr key */JSONObject> formData);
    }
}
