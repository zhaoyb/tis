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
package com.qlangtech.tis.offline.pojo;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * git仓库所有的提交记录
 *
 * @author 百岁（baisui@qlangtech.com）
 * @date 2020/04/13
 */
public class GitRepositoryCommitPojo {

    private String authorName;

    private String authorEmail;

    private String createdAt;

    private String id;

    private String shortId;

    private String title;

    private String message;

    private static final ThreadLocal<SimpleDateFormat> FORMAT_yyyyMMddHHmmss = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

    public GitRepositoryCommitPojo() {
    }

    public GitRepositoryCommitPojo(JSONObject jsonObject) throws ParseException {
        String date = jsonObject.getString("created_at");
        date = StringUtils.substringBeforeLast(date, "+08:00");
        date = StringUtils.replace(date, "T", " ");
        date = FORMAT_yyyyMMddHHmmss.get().format(FORMAT_yyyyMMddHHmmss.get().parse(date));
        setAuthorName(jsonObject.getString("author_name"));
        setAuthorEmail(jsonObject.getString("author_email"));
        setCreatedAt(date);
        setId(jsonObject.getString("id"));
        setShortId(jsonObject.getString("short_id"));
        setTitle(jsonObject.getString("title"));
        setMessage(jsonObject.getString("message"));
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public void setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShortId() {
        return shortId;
    }

    public void setShortId(String shortId) {
        this.shortId = shortId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
