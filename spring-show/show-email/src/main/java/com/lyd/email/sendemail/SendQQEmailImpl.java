package com.lyd.email.sendemail;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.lyd.email.EmailConstant;

/**
 * QQ邮箱发送邮件
 * @author lyd
 * @date 2017年9月8日
 * @version 1.0
 * @CSDN http://blog.csdn.net/it_lyd
 */
public class SendQQEmailImpl implements SendEmailInter{

	public void sendMail(String toEmail, String title, String content) throws Exception {
		Properties properties = new Properties();
		properties.setProperty("mail.host", "smtp.qq.com");
		properties.setProperty("mail.transport.protocol", "smtp");
		properties.setProperty("mail.smtp.auth", "true");
		properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.setProperty("mail.smtp.port", "465");
		properties.setProperty("mail.smtp.socketFactory.port", "465");
		Session session = Session.getInstance(properties);
		// session.setDebug(true);
		Message msg = new MimeMessage(session);
		msg.setSubject(title);
		//msg.setText(content);
		msg.setContent(content, "text/html;charset=UTF-8");
		msg.setFrom(new InternetAddress(EmailConstant.getEmailAccount()));
		String[] allEmail = toEmail.replaceAll("；", ";").split(";");
		Address[] address = new Address[allEmail.length];
		for (int i = 0; i < allEmail.length; i++) {
			address[i] = new InternetAddress(allEmail[i]);
		}
		Transport transport = session.getTransport();
		transport.connect("smtp.qq.com", EmailConstant.getEmailAccount(), EmailConstant.getEmailPwd());
		transport.sendMessage(msg, address);
		transport.close();
		System.out.println("发送完毕！");
	}

}
