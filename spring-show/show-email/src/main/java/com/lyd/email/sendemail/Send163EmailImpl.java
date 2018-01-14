package com.lyd.email.sendemail;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.lyd.email.EmailConstant;

/**
 * 163邮箱发送邮件
 * @author lyd
 * @date 2017年9月8日
 * @version 1.0
 * @CSDN http://blog.csdn.net/it_lyd
 */
public class Send163EmailImpl implements SendEmailInter{

	public void sendMail(String toEmail, String title, String content) throws Exception {
		Properties properties = new Properties();
		properties.setProperty("mail.transport.protocol", "smtp"); //设置传输协议
		properties.put("mail.smtp.host", EmailConstant.SMTP163SERVER); //设置发信邮箱的smtp地址
		properties.setProperty("mail.smtp.auth", "true"); //验证
		// 使用验证，创建一个Authenticator
		Authenticator auth = new Authenticator() {
			// 创建传入身份验证信息的 Authenticator类
			@Override
			protected PasswordAuthentication getPasswordAuthentication(){
				return new PasswordAuthentication(EmailConstant.getEmailAccount(), EmailConstant.getEmailPwd());
			}
		};
		Session session = Session.getDefaultInstance(properties, auth); // 根据Properties，Authenticator创建Session
		Message message = new MimeMessage(session); // Message存储发送的电子邮件信息
		message.setFrom(new InternetAddress(EmailConstant.getEmailAccount()));
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
		// 指定邮箱内容及ContentType和编码方式
		message.setContent(content, "text/html;charset=utf-8");
		message.setSubject(title);// 设置主题
		message.setSentDate(new Date());// 设置发信时间
		Transport.send(message);// 发送
		System.out.println("发送完毕！");
	}

	
}
