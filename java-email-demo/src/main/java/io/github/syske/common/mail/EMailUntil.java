/* Copyright © 2022 syske. All rights reserved. */
package io.github.syske.common.mail;

import com.sun.mail.smtp.SMTPMessage;
import com.sun.mail.util.logging.MailHandler;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * 邮件工具类
 *
 * @author syske
 * @version 1.0
 * @date 2022-03-29 22:25
 */
public class EMailUntil {
    public static void sendEmail() {
        String account = "";
        String password = "";
        String toAccount = "";
        Properties properties = new Properties();
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(account, password);
            }
        };
        Session session = Session.getDefaultInstance(properties, authenticator);
        Message message = new MimeMessage(session);
        try {
            message.setSubject("邮件主题");
            message.setFrom(new InternetAddress(toAccount));
            message.setText("这里是邮件内容");
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
