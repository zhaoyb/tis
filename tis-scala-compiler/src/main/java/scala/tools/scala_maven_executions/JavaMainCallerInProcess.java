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
package scala.tools.scala_maven_executions;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

/**
 * This class will call a java main method via reflection.
 * <p>
 * Note: a -classpath argument *must* be passed into the jvmargs.
 *
 * @author 百岁（baisui@qlangtech.com）
 * @date 2020/04/13
 */
public class JavaMainCallerInProcess extends JavaMainCallerSupport {

    private ClassLoader _cl;

    public JavaMainCallerInProcess(String mainClassName, String classpath, String[] jvmArgs, String[] args) throws Exception {
        super(mainClassName, "", jvmArgs, args);
        // Pull out classpath and create class loader
        ArrayList<URL> urls = new ArrayList<>();
        for (String path : classpath.split(File.pathSeparator)) {
            try {
                urls.add(new File(path).toURI().toURL());
            } catch (MalformedURLException e) {
            // TODO - Do something usefull here...
            // requester.getLog().error(e);
            }
        }
        _cl = new URLClassLoader(urls.toArray(new URL[] {}), null);
    }

    @Override
    public void addJvmArgs(String... args0) {
        // TODO - Ignore classpath
        if (args0 != null) {
            for (String arg : args0) {
            // requester.getLog().warn("jvmArgs are ignored when run in process :" + arg);
            }
        }
    }

    @Override
    public boolean run(boolean displayCmd, boolean throwFailure) throws Exception {
        try {
            runInternal(displayCmd);
            return true;
        } catch (Exception e) {
            if (throwFailure) {
                throw e;
            }
            return false;
        }
    }

    // /**
    // * spawns a thread to run the method
    // */
    // @Override
    // public SpawnMonitor spawn(final boolean displayCmd) {
    // final Thread t = new Thread(() -> {
    // try {
    // runInternal(displayCmd);
    // } catch (Exception e) {
    // // Ignore
    // }
    // });
    // t.start();
    // return t::isAlive;
    // }
    /**
     * Runs the main method of a java class
     */
    private void runInternal(boolean displayCmd) throws Exception {
        String[] argArray = args.toArray(new String[] {});
        if (displayCmd) {
        // requester.getLog().info("cmd : " + mainClassName + "(" + StringUtils.join(argArray, ",") + ")");
        }
        MainHelper.runMain(mainClassName, args, _cl);
    }

    @Override
    public void redirectToLog() {
    // requester.getLog().warn("redirection to log is not supported for 'inProcess' mode");
    }
}
