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
package com.qlangtech.tis.extension.init;

import org.jvnet.hudson.reactor.Executable;
import org.jvnet.hudson.reactor.Milestone;
import org.jvnet.hudson.reactor.TaskBuilder;
import org.jvnet.hudson.reactor.TaskGraphBuilder;

/**
 * Various key milestone in the initialization process of Hudson.
 * <p>
 * Plugins can use these milestones to execute their initialization at the right moment
 * (in addition to defining their own milestones by implementing {@link Milestone}.
 * <p>
 * These milestones are achieve in this order:
 * <ol>
 *  <li>STARTED
 *  <li>PLUGINS_LISTED
 *  <li>PLUGINS_PREPARED
 *  <li>PLUGINS_STARTED
 *  <li>EXTENSIONS_AUGMENTED
 *  <li>JOB_LOADED
 *  <li>COMPLETED
 * </ol>
 *
 * @author 百岁（baisui@qlangtech.com）
 * @date 2020/04/13
 */
public enum InitMilestone implements Milestone {

    /**
     * The very first milestone that gets achieved without doing anything.
     *
     * This is used in {@link #()} since annotations cannot have null as the default value.
     */
    STARTED("Started initialization"),
    /**
     * By this milestone, all plugins metadata are inspected and their dependencies figured out.
     */
    PLUGINS_LISTED("Listed all plugins"),
    /**
     * By this milestone, all plugin metadata are loaded and its classloader set up.
     */
    PLUGINS_PREPARED("Prepared all plugins"),
    /**
     * By this milestone, all plugins start executing, all extension points loaded, descriptors instantiated
     * and loaded.
     *
     * <p>
     * This is a separate milestone from {@link #PLUGINS_PREPARED} since the execution
     * of a plugin often involves finding extension point implementations, which in turn
     * require all the classes from all the plugins to be loadable.
     */
    PLUGINS_STARTED("Started all plugins"),
    /**
     * The very last milestone
     *
     * This is used in {@link #()} since annotations cannot have null as the default value.
     */
    COMPLETED("Completed initialization");

    private final String message;

    InitMilestone(String message) {
        this.message = message;
    }

    /**
     * Creates a set of dummy tasks to enforce ordering among {@link InitMilestone}s.
     */
    public static TaskBuilder ordering() {
        TaskGraphBuilder b = new TaskGraphBuilder();
        InitMilestone[] v = values();
        for (int i = 0; i < v.length - 1; i++) b.add(null, Executable.NOOP).requires(v[i]).attains(v[i + 1]);
        return b;
    }

    @Override
    public String toString() {
        return message;
    }
}
