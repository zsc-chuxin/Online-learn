package com.example.backend.utils;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;
import java.util.Random;

@Component
public final class MailUtil {

    @Value("${mail.user}")
    private String USER;
    @Value("${mail.password}")
    private String PASSWORD;

    // 发送邮件
    public boolean sendMail(String to, String title, String content) {
        try {
            final Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.host", "smtp.qq.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.user", USER);
            props.put("mail.password", PASSWORD);

            //构建授权信息，用于进行SMTP进行身份验证
            Authenticator authenticator = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    // 用户名、密码
                    String userName = props.getProperty("mail.user");
                    String password = props.getProperty("mail.password");
                    return new PasswordAuthentication(userName, password);
                }
            };

            // 使用环境属性和授权信息，创建邮件会话
            Session mailSession = Session.getInstance(props, authenticator);

            // 创建邮件消息
            MimeMessage message = new MimeMessage(mailSession);

            // 设置发件人
            String username = props.getProperty("mail.user");
            String personal = "在线学习平台";
            InternetAddress form = new InternetAddress( username,personal,"UTF-8");
            message.setFrom(form);

            // 设置收件人
            InternetAddress toAddress = new InternetAddress(to);
            message.setRecipient(Message.RecipientType.TO, toAddress);

            // 设置邮件标题
            message.setSubject(title);

            // 设置邮件的内容体
            message.setContent(content, "text/html;charset=UTF-8");

            // 发送邮件
            Transport.send(message);
            return true;

        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    // 生成6位验证码
    public String generateCode(){
        String characters = "0123456789";
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            code.append(characters.charAt(random.nextInt(characters.length())));
        }
        return code.toString();
    }

    // 发送包含验证码的邮件
    public String sendCodeMail(String to){
        String code = generateCode();
        String content = "您正在进行邮箱登录的操作，您的验证码是：" + code + "（本验证码5分钟内有效），请您在5分钟内完成登录。" +
                "<br>如非您本人操作，请忽略此邮件。";
        sendMail(to, "邮箱验证", content);
        return code;
    }
}
