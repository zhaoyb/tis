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
package com.qlangtech.tis.plugin.incr;

import com.qlangtech.tis.TIS;
import com.qlangtech.tis.extension.Describable;
import com.qlangtech.tis.extension.Descriptor;
import com.qlangtech.tis.plugin.IdentityName;
import com.qlangtech.tis.coredefine.module.action.IIncrSync;

/**
 * @author 百岁（baisui@qlangtech.com）
 * @date 2020/04/13
 */
public abstract class IncrStreamFactory implements Describable<IncrStreamFactory>, IdentityName {

    public abstract IIncrSync getIncrSync();

    // public static DescriptorExtensionList<IncrK8sConfig, Descriptor<IncrK8sConfig>> all() {
    // return TIS.get()
    // .<IncrK8sConfig, Descriptor<IncrK8sConfig>>getDescriptorList(IncrK8sConfig.class);
    // }
    @Override
    public final Descriptor<IncrStreamFactory> getDescriptor() {
        return TIS.get().getDescriptor(this.getClass());
    }
}
