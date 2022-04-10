/* Copyright © 2022 syske. All rights reserved. */
package io.github.syske.common.mail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

/**
 * 邮件工具类
 *
 * @author syske
 * @version 1.0
 * @date 2022-03-29 22:25
 */
public class EMailUntil {

    /**
     * 发送邮件的方法
     *
     * @param host    :邮件服务器地址
     * @param account :账户（发送方）
     * @param password :账户密码（发送方）
     * @param port    :邮箱服务器端口（发送方）
     * @param toUser  :收件人
     * @param title   :标题
     * @param content :内容
     */
    public static void sendMail(String host, String account, String password, String port, String toUser, String title, String content) throws Exception {

        Properties props = System.getProperties();
        props.setProperty("mail.host", host);
        props.setProperty("mail.smtp.port", port);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "false");
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//        props.put("mail.smtp.socketFactory.port", port);
        // 发送邮件超时时间
        props.put("mail.smtp.writetimeout", 1000);
        // 连接超时时间
        props.put("mail.smtp.connectiontimeout", 1000);

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(account, password);
            }
        });

        Message msg = new MimeMessage(session);

        msg.setFrom(new InternetAddress(account));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toUser));
        msg.setContent(content, "text/html;charset=utf-8");
        msg.setSubject(title);
        msg.setSentDate(new Date());
        Transport.send(msg);
    }

    public static void main(String[] args) throws Exception {
        sendMail("smtp.163.com", "12323112@163.com", "123123123",
                "465", "12323112@163.com", "测试邮件666", "邮件发送测试");
    }
}
