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
package com.qlangtech.tis.compiler.java;

import org.apache.commons.lang.StringUtils;

import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.Map;
import java.util.jar.JarOutputStream;

/**
 * @author 百岁（baisui@qlangtech.com）
 * @date 2019年10月9日
 */
public class NestClassFileObject extends SimpleJavaFileObject implements IOutputEntry {

    private ByteArrayOutputStream outPutStream;

    private final ZipPath zipPath;

    public NestClassFileObject(ZipPath zipPath) {
        super(URI.create("file:///mock"), Kind.CLASS);
        this.zipPath = zipPath;
    }

    public static NestClassFileObject getNestClassFileObject(String qualifiedClassName, Map<String, IOutputEntry> fileObjects) {
        String pathParent = StringUtils.substringBeforeLast(qualifiedClassName, ".");
        String className = StringUtils.substringAfterLast(qualifiedClassName, ".");
        ZipPath zipPath = new //
                ZipPath(//
                StringUtils.replace(pathParent, ".", "/"), className, Kind.CLASS);
        NestClassFileObject fileObj = new NestClassFileObject(zipPath);
        fileObjects.put(qualifiedClassName, fileObj);
        return fileObj;
    }

    @Override
    public boolean containCompiledClass() {
        return true;
    }

    @Override
    public void processSource(JarOutputStream jaroutput) throws Exception {
    }

    @Override
    public OutputStream openOutputStream() throws IOException {
        outPutStream = new ByteArrayOutputStream();
        return outPutStream;
    }

    public ByteArrayOutputStream getOutputStream() throws IOException {
        if (outPutStream == null) {
            throw new IllegalStateException("outputStream can not be null");
        }
        return outPutStream;
    }

    @Override
    public ZipPath getZipPath() {
        return this.zipPath;
    }

    @Override
    public JavaFileObject getFileObject() {
        return this;
    }
}
