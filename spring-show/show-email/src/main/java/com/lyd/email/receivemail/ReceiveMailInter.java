package com.lyd.email.receivemail;

import javax.mail.Message;

public interface ReceiveMailInter {

	Message[] receiveMail() throws Exception;
}
