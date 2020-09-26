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
package com.qlangtech.tis.email;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 百岁（baisui@qlangtech.com）
 * @date 2013-12-4
 */
public class SendMail {

    private static final Logger logger = LoggerFactory.getLogger(SendMail.class);

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        SendMail sender = new SendMail();
        List<String> recipient = new ArrayList<String>();
        // recipient.add("baisui@taobao.com");
        // sender.send(recipient);
        String content = null;
        sender.send("hello this is test please ignor it", "hello", "qixi@2dfire.com");
    // System.out.println(content);
    }

    // // 邮箱服务器
    // private String host = "smtp.aliyun.com";
    // // // 这个是你的邮箱用户名
    // private String username = "baisui@2dfire.com";
    // // 你的邮箱密码:
    // private String password = "flzxsqc85815545!";
    // 
    // private String mail_from = "baisui@2dfire.com";
    // 邮箱服务器
    private String host = "smtp.126.com";

    // // 这个是你的邮箱用户名
    private String username = "mozhenghua19811109@126.com";

    // 你的邮箱密码:
    private String password = "0010105074";

    private String mail_from = "mozhenghua19811109@126.com";

    // private String host = "vpn.gecareers.cn";
    // // // 这个是你的邮箱用户名
    // private String username = "tis@2dfire.tech";
    // // 你的邮箱密码:
    // private String password = "nX2izEe4xEmkQP";
    // 
    // private String mail_from = "tis@2dfire.tech";
    private String personalName = "TIS日报表";

    private static final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

    /**
     * 此段代码用来发送普通电子邮件
     */
    /**
     * @param subject
     * @param content
     * @throws Exception
     */
    public void send(String subject, String content, String mail_to2) throws Exception {
        try {
            // 获取系统环境
            Properties props = new Properties();
            // 进行邮件服务器用户认证
            Authenticator auth = new Email_Autherticator();
            props.put("mail.smtp.host", host);
            props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
            props.setProperty("mail.smtp.socketFactory.fallback", "false");
            props.setProperty("mail.smtp.port", "465");
            props.setProperty("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.auth", "true");
            Session session = Session.getDefaultInstance(props, auth);
            // session.setDebug(true);
            MimeMessage message = new MimeMessage(session);
            // message.setHeader("Content-Type", "text/html;charset=utf8");
            // message.setHeader("Content-Transfer-Encoding", "utf8");
            // 设置邮件主题
            message.setSubject(MimeUtility.encodeText(subject, "gb2312", "B"));
            // message.setHeader(name, value); // 设置邮件正文
            // message.setHeader(mail_head_name, mail_head_value); // 设置邮件标题
            // 设置邮件发送日期
            message.setSentDate(new Date());
            Address address = new InternetAddress(mail_from, personalName);
            // 设置邮件发送者的地址
            message.setFrom(address);
            message.setContent(content, "text/html;charset=gb2312");
            logger.info("sendto email:" + mail_to2);
            String[] destAddress = StringUtils.split(mail_to2, ",");
            for (String to : destAddress) {
                Address toAddress2 = new InternetAddress(to);
                message.addRecipient(Message.RecipientType.TO, toAddress2);
            }
            // 发送邮件
            Transport.send(message);
            System.out.println("send ok!");
        } catch (Exception ex) {
            throw new Exception(ex.getMessage(), ex);
        }
    }

    /**
     * 用来进行服务器对用户的认证
     */
    private class Email_Autherticator extends Authenticator {

        public Email_Autherticator() {
            super();
        }

        // Email_Autherticator(String user, String pwd) {
        // super();
        // username = user;
        // password = pwd;
        // }
        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(username, password);
        }
    }
}
