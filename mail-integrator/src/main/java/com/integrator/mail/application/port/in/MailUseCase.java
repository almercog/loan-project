package com.integrator.mail.application.port.in;

import java.io.IOException;

import com.integrator.mail.application.domain.model.Mail;
import com.integrator.mail.common.Connector;

import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;

/**
 * 
 * @author <a href="mailto:almercog@gmail.com">Giancarlo Almerco</a>
 * @since 2023-10-16
 *
 */
@Connector(name = "mail", description = "This connector is used to send mail using java mail framework")
public interface MailUseCase {

	void sendSimpleMessage(Mail mail);

	void sendSimpleMessageUsingTemplate(Mail mail);

	void sendMessageWithAttachment(Mail mail) throws MessagingException;

	void sendMessageUsingVelocityTemplate(Mail mail) throws MessagingException;

	void sendMessageUsingThymeleafTemplate(Mail mail) throws IOException, MessagingException;

	void sendMessageUsingFreemarkerTemplate(Mail mail) throws IOException, TemplateException, MessagingException;
}
