package com.lyd.email.sendemail;

public interface SendEmailInter {

	void sendMail(String toEmail, String title, String content) throws Exception;
}
