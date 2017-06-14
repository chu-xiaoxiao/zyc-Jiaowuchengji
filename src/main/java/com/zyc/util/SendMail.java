package com.zyc.util;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail {
	public void sendmain(String head,String text,String sendAdd){
		Properties props = new Properties();
		// 开启debug调试
		props.setProperty("mail.debug", "true");
		// 发送服务器需要身份验证
		props.setProperty("mail.smtp.auth", "true");
		// 设置邮件服务器主机名
		props.setProperty("mail.host", "smtp.163.com");
		// 发送邮件协议名称
		props.setProperty("mail.transport.protocol", "smtp");

		Session session = Session.getInstance(props);
		Message message = new MimeMessage(session);
		try {
			message.setSubject(head);
			message.setText(text);
			message.setFrom(new InternetAddress("18842495319@163.com"));
			Transport transport = session.getTransport();
			transport.connect("18842495319@163.com","zhangyuchen1016");
			transport.sendMessage(message,new Address[]{new InternetAddress(sendAdd)});
			transport.close();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
	}
}
