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
/* * Copyright 2020 QingLang, Inc.
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import java.io.RandomAccessFile;

/**
 * @author 百岁（baisui@qlangtech.com）
 * @date 2020/04/13
 */
public class RandomAccessFileTest {

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        RandomAccessFile randomAccess = new RandomAccessFile("D:\\tmp\\test.txt", "r");
        boolean eol = false;
        int c = -1;
        long fileLength = randomAccess.length();
        long size = 1;
        ww: while (!eol) {
            long offset = fileLength - (size++);
            randomAccess.seek(offset);
            switch(c = randomAccess.read()) {
                case -1:
                case '\n':
                case '\r':
                    randomAccess.seek(offset + 1);
                    break ww;
            }
        }
        String line = null;
        while ((line = randomAccess.readLine()) != null) {
            System.out.println(line);
        }
        randomAccess.close();
    }
}
