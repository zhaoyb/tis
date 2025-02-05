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

import com.qlangtech.tis.exec.TestActionInvocation;
import com.qlangtech.tis.exec.datax.TestDataXExecuteInterceptor;
import com.qlangtech.tis.full.dump.TestDefaultChainContext;
import com.qlangtech.tis.fullbuild.servlet.TestTisServlet;
import com.qlangtech.tis.fullbuild.taskflow.TestReactor;
import com.qlangtech.tis.log.TestRealtimeLoggerCollectorAppender;
import com.qlangtech.tis.order.center.TestIndexSwapTaskflowLauncher;
import com.qlangtech.tis.order.center.TestIndexSwapTaskflowLauncherWithDataXTrigger;
import com.qlangtech.tis.order.center.TestIndexSwapTaskflowLauncherWithSingleTableIndexBuild;
import com.qlangtech.tis.rpc.server.TestIncrStatusServer;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @author 百岁（baisui@qlangtech.com）
 * @date 2020/04/13
 */
public class TestAll extends TestCase {
    static {
        System.setProperty("logback.configurationFile", "src/main/resources/logback-assemble.xml");
    }

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(TestDefaultChainContext.class);
        suite.addTestSuite(TestTisServlet.class);
        suite.addTestSuite(TestActionInvocation.class);
        suite.addTestSuite(TestReactor.class);
        suite.addTestSuite(TestIndexSwapTaskflowLauncher.class);
       // suite.addTestSuite(TestIndexSwapTaskflowLauncherWithSingleTableIndexBuild.class);
        suite.addTestSuite(TestIndexSwapTaskflowLauncherWithDataXTrigger.class);
        suite.addTestSuite(TestIncrStatusServer.class);
        suite.addTestSuite(TestRealtimeLoggerCollectorAppender.class);
        suite.addTestSuite(TestDataXExecuteInterceptor.class);
        return suite;
    }
}
