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

package com.qlangtech.tis.fullbuild.indexbuild.impl;

import com.qlangtech.tis.fullbuild.indexbuild.IRemoteJobTrigger;

/**
 * 异步任务
 * @author: 百岁（baisui@qlangtech.com）
 * @create: 2021-06-02 18:03
 **/
public abstract class AsynRemoteJobTrigger implements IRemoteJobTrigger {
    private final String jobName;

    public AsynRemoteJobTrigger(String jobName) {
        this.jobName = jobName;
    }

    @Override
    public final boolean isAsyn() {
        return true;
    }

    @Override
    public String getAsynJobName() {
        return jobName;
    }
}
