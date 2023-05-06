package org.javaboy.mailserver;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailserverApplicationTests {

    @Autowired
    JavaMailSender javaMailSender;

    @Test
    public void contextLoads() {
    }


    @Test
    public void sendSimpleMail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("这是一封测试邮件");
        message.setFrom("1164864987@qq.com");
        message.setTo("mingorg1024@gmail.com");
        message.setCc("mingorg@163.com");
        //message.setBcc("1164864987@qq.com");
        message.setSentDate(new Date());
        message.setText("这是测试邮件的正文");
        javaMailSender.send(message);
        System.out.println(javaMailSender);

    }

}