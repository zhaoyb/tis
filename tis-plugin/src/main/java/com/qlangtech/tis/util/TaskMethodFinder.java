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

package com.qlangtech.tis.util;


import com.qlangtech.tis.TIS;
import org.jvnet.hudson.reactor.*;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.logging.Logger;
/**
 * @author: 百岁（baisui@qlangtech.com）
 * @create: 2021-05-11 10:15
 **/

/**
 * @author Kohsuke Kawaguchi
 */
abstract class TaskMethodFinder<T extends Annotation> extends TaskBuilder {
    private static final Logger LOGGER = Logger.getLogger(TaskMethodFinder.class.getName());
    protected final ClassLoader cl;
    private final Set<Method> discovered = new HashSet<>();

    private final Class<T> type;
    private final Class<? extends Enum> milestoneType;

    TaskMethodFinder(Class<T> type, Class<? extends Enum> milestoneType, ClassLoader cl) {
        this.type = type;
        this.milestoneType = milestoneType;
        this.cl = cl;
    }

    // working around the restriction that Java doesn't allow annotation types to extend interfaces
    protected abstract String displayNameOf(T i);

    protected abstract String[] requiresOf(T i);

    protected abstract String[] attainsOf(T i);

    protected abstract Milestone afterOf(T i);

    protected abstract Milestone beforeOf(T i);

    protected abstract boolean fatalOf(T i);

    @Override
    public Collection<Task> discoverTasks(Reactor session) throws IOException {
//        List<Task> result = new ArrayList<>();
//        for (Method e : Index.list(type, cl, Method.class)) {
//            if (filter(e)) continue;   // already reported once
//
//            T i = e.getAnnotation(type);
//            if (i == null) continue; // stale index
//
//            result.add(new TaskImpl(i, e));
//        }
//        return result;
        return Collections.emptyList();
    }

    /**
     * Return true to ignore this method.
     */
    protected boolean filter(Method e) {
        return !discovered.add(e);
    }

    /**
     * Obtains the display name of the given initialization task
     */
    protected String getDisplayNameOf(Method e, T i) {
        Class<?> c = e.getDeclaringClass();
        String key = displayNameOf(i);

        //if (key.length() == 0)
        return c.getSimpleName() + "." + e.getName();
        // try {
//            ResourceBundleHolder rb = ResourceBundleHolder.get(
//                    c.getClassLoader().loadClass(c.getPackage().getName() + ".Messages"));
//            return rb.format(key);
//        } catch (ClassNotFoundException x) {
//            LOGGER.log(WARNING, "Failed to load " + x.getMessage() + " for " + e.toString(), x);
//            return key;
//        } catch (MissingResourceException x) {
//            LOGGER.log(WARNING, "Could not find key '" + key + "' in " + c.getPackage().getName() + ".Messages", x);
//            return key;
//        }
    }

    /**
     * Invokes the given initialization method.
     */
    protected void invoke(Method e) {
        try {
            Class<?>[] pt = e.getParameterTypes();
            Object[] args = new Object[pt.length];
            for (int i = 0; i < args.length; i++)
                args[i] = lookUp(pt[i]);

            e.invoke(
                    Modifier.isStatic(e.getModifiers()) ? null : lookUp(e.getDeclaringClass()),
                    args);
        } catch (IllegalAccessException x) {
            throw (Error) new IllegalAccessError().initCause(x);
        } catch (InvocationTargetException x) {
            throw new Error(x);
        }
    }

    /**
     * Determines the parameter injection of the initialization method.
     */
    private Object lookUp(Class<?> type) {
        TIS j = TIS.get();
        assert j != null : "This method is only invoked after the Jenkins singleton instance has been set";
        return j;
    }

    /**
     * Task implementation.
     */
    public class TaskImpl implements Task {
        final Collection<Milestone> requires;
        final Collection<Milestone> attains;
        private final T i;
        private final Method e;

        private TaskImpl(T i, Method e) {
            this.i = i;
            this.e = e;
            requires = toMilestones(requiresOf(i), afterOf(i));
            attains = toMilestones(attainsOf(i), beforeOf(i));
        }

        /**
         * The annotation on the {@linkplain #getMethod() method}
         */
        public T getAnnotation() {
            return i;
        }

        /**
         * Method that runs the initialization, that this task wraps.
         */
        public Method getMethod() {
            return e;
        }

        @Override
        public Collection<Milestone> requires() {
            return requires;
        }

        @Override
        public Collection<Milestone> attains() {
            return attains;
        }

        @Override
        public String getDisplayName() {
            return getDisplayNameOf(e, i);
        }

        @Override
        public boolean failureIsFatal() {
            return fatalOf(i);
        }

        @Override
        public void run(Reactor session) {
            invoke(e);
        }

        @Override
        public String toString() {
            return e.toString();
        }

        private Collection<Milestone> toMilestones(String[] tokens, Milestone m) {
            List<Milestone> r = new ArrayList<>();
            for (String s : tokens) {
                try {
                    r.add((Milestone) Enum.valueOf(milestoneType, s));
                } catch (IllegalArgumentException x) {
                    r.add(new MilestoneImpl(s));
                }
            }
            r.add(m);
            return r;
        }
    }
}
