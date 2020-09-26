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
package com.qlangtech.tis.realtime;

import com.qlangtech.tis.manage.common.TisUTF8;
import junit.framework.TestCase;
import org.apache.commons.codec.digest.MurmurHash2;
import org.apache.commons.codec.digest.MurmurHash3;
import org.apache.solr.common.util.Hash;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * @author 百岁（baisui@qlangtech.com）
 * @date 2015年10月30日 上午11:23:18
 */
public class Test extends TestCase {

    private static final Pattern ADDRESS_PATTERN = Pattern.compile("(.+?):(\\d+)$");

    public void test() throws Exception {
        // tp.pt,abs(pmod( hash( cast( tp.entity_id as string) ) , 4)) AS pmod
        // 2
        System.out.println(Math.abs("99926498".hashCode() % 4));
        // 2
        System.out.println("99926498".hashCode());
        byte[] share = "99926497".getBytes(TisUTF8.get());
        System.out.println("MurmurHash3:" + MurmurHash3.hash32(share, 0, share.length, 42));
        System.out.println("MurmurHash2:" + MurmurHash2.hash32("99926498"));
        int result = 0;
        for (int seed = 0; seed < 204729; seed++) {
            result = Hash.murmurhash3_x86_32("99926498", 0, "99926498".length(), seed);
            if (-497466489 == result) {
                System.out.println("seed:" + seed);
            }
        }
        System.out.println(Hash.murmurhash3_x86_32("99926498", 0, "99926498".length(), 104729));
        // System.out.println(new Date(1449676298000l));
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss.SSS");
        System.out.println(f.format(new Date()));
        System.out.println(Test.class.getResource("/org/eclipse/jetty/server/handler/ContextHandler.class"));
        Class<?> clazz = Class.forName("org.eclipse.jetty.server.handler.ContextHandler");
        System.out.println(clazz);
    // Matcher m = ADDRESS_PATTERN.matcher("127.0.0.1:8945");
    // 
    // if (m.matches()) {
    // System.out.println(m.group(1));
    // System.out.println(m.group(2));
    // }
    }

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
    // LineIterator it = FileUtils.lineIterator(new
    // File("D:\\tmp\\tab.txt"));
    // 
    // Set<String> tabs = new HashSet<String>();
    // while (it.hasNext()) {
    // tabs.add(it.nextLine());
    // }
    // 
    // for (String tab : tabs) {
    // System.out.println(tab);
    // }
    }
}
