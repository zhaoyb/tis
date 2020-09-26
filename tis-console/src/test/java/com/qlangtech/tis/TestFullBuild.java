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
package com.qlangtech.tis;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import com.qlangtech.tis.manage.common.ConfigFileContext.StreamProcess;
import com.qlangtech.tis.manage.common.HttpUtils;
import junit.framework.TestCase;

/**
 * @author 百岁（baisui@qlangtech.com）
 * @date 2017年7月3日
 */
public class TestFullBuild extends TestCase {

    public void testFullBuild() throws Exception {
        URL url = new URL("http://10.1.5.129:8080/trigger?appname=search4xxx&workflow_id=15");
        HttpUtils.processContent(url, new StreamProcess<String>() {

            @Override
            public String p(int status, InputStream stream, Map<String, List<String>> headerFields) {
                try {
                    System.out.println(IOUtils.toString(stream, "utf8"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return null;
            }
        });
    }
}
