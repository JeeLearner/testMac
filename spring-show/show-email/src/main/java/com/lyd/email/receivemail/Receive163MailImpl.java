package com.lyd.email.receivemail;

import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Session;
import javax.mail.Store;

import com.lyd.email.EmailConstant;

public class Receive163MailImpl extends AbstractReceiveMail {

	@Override
	public Store getStore(Session session) throws Exception {
		Store store = session.getStore(EmailConstant.PROTOCOL);
		// POP3服务器的登录认证
		store.connect(EmailConstant.POP163SERVER, EmailConstant.getEmailAccount(), EmailConstant.getEmailPwd());
		return store;
	}

	@Override
	public Properties getProperties() {
		Properties props = new Properties();
		// 使用的协议（JavaMail规范要求）
		props.setProperty("mail.transport.protocol", EmailConstant.PROTOCOL);
		// 发件人的邮箱的SMTP服务器地址
		props.setProperty("mail.pop.host", EmailConstant.POP163SERVER);
		return props;
	}

	@Override
	public Folder getFolder(Store store) throws Exception {
		Folder folder = null;
		folder = store.getFolder("INBOX");// 获得用户的邮件帐户
		folder.open(Folder.READ_WRITE); // 设置对邮件帐户的访问权限
		return folder;
	}

	@Override
	public Session getSession(Properties props) {
		// 获取连接
		Session session = Session.getDefaultInstance(props);
		session.setDebug(false);
		return session;
	}

}
