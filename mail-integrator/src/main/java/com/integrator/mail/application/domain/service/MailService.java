package com.integrator.mail.application.domain.service;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.integrator.mail.application.domain.model.Mail;
import com.integrator.mail.application.port.in.MailUseCase;
import com.integrator.mail.common.Constants;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

/**
 * 
 * @author <a href="mailto:almercog@gmail.com">Giancarlo Almerco</a>
 * @since 2023-11-22
 *
 */
@Component
public class MailService implements MailUseCase {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private SimpleMailMessage mailMessage;

	@Autowired
	private VelocityEngine velocityEngine;

	@Autowired
	private SpringTemplateEngine thymeleafTemplateEngine;

	@Autowired
	private FreeMarkerConfigurer freemarkerConfigurer;

	@Override
	public void sendSimpleMessage(Mail mail) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(Constants.NOREPLY_ADDRESS);
		message.setTo(mail.getTo());
		message.setSubject(mail.getSubject());
		message.setText(mail.getText());
		mailSender.send(message);
	}

	@Override
	public void sendSimpleMessageUsingTemplate(Mail mail) {
		String msgBody = String.format(mailMessage.getText(), mail.getTemplateModel().values());
		mail.setText(msgBody);
		sendSimpleMessage(mail);
	}

	@Override
	public void sendMessageWithAttachment(Mail mail) throws MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setFrom(Constants.NOREPLY_ADDRESS);
		helper.setTo(mail.getTo());
		helper.setSubject(mail.getSubject());
		helper.setText(mail.getText());
		if (mail.getAttachment() != null) {
			for (String key : mail.getAttachment().keySet()) {
				ByteArrayResource resource = new ByteArrayResource(mail.getAttachment().get(key));
				helper.addAttachment(key, resource);
			}
		}
		mailSender.send(message);
	}

	@Override
	public void sendMessageUsingVelocityTemplate(Mail mail) throws MessagingException {
		String htmlBody;
		VelocityContext context = new VelocityContext(mail.getTemplateModel());
		StringWriter stringWriter = new StringWriter();
		if (mail.getHtml() != null && !mail.getHtml().isEmpty()) {
			Velocity.evaluate(context, stringWriter, "", mail.getHtml());
		} else {
			velocityEngine.mergeTemplate(mail.getTemplateName(), "UTF-8", context, stringWriter);
		}
		htmlBody = stringWriter.toString();
		mail.setHtml(htmlBody);
		sendHtmlMessage(mail);
	}

	@Override
	public void sendMessageUsingThymeleafTemplate(Mail mail) throws IOException, MessagingException {
		String htmlBody;
		if (mail.getTemplateName() == null || mail.getTemplateName().isEmpty()) {
			throw new MailException("Template name is null!");
		}
		Context thymeleafContext = new Context();
		thymeleafContext.setVariables(mail.getTemplateModel());
		if (mail.getHtml() != null && !mail.getHtml().isEmpty()) {
			htmlBody = thymeleafTemplateEngine.process(mail.getHtml(), thymeleafContext);
		} else {
			htmlBody = thymeleafTemplateEngine.process(mail.getTemplateName(), thymeleafContext);
		}
		mail.setHtml(htmlBody);
		sendHtmlMessage(mail);
	}

	@Override
	public void sendMessageUsingFreemarkerTemplate(Mail mail) throws IOException, TemplateException, MessagingException {
		String htmlBody;
		if (mail.getTemplateName() == null || mail.getTemplateName().isEmpty()) {
			throw new MailException("Template name is null!");
		}
		Configuration configuration = freemarkerConfigurer.getConfiguration();
		if (mail.getHtml() != null && !mail.getHtml().isEmpty()) {
			StringTemplateLoader stringLoader = new StringTemplateLoader();
			stringLoader.putTemplate(mail.getTemplateName(), mail.getHtml());
			configuration.setTemplateLoader(stringLoader);
			Template freemarkerTemplate = configuration.getTemplate(mail.getTemplateName());
			htmlBody = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerTemplate, mail.getTemplateModel());
		} else {
			Template freemarkerTemplate = configuration.getTemplate(mail.getTemplateName());
			htmlBody = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerTemplate, mail.getTemplateModel());
		}
		mail.setHtml(htmlBody);
		sendHtmlMessage(mail);
	}

	private void sendHtmlMessage(Mail mail) throws MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
		helper.setFrom(Constants.NOREPLY_ADDRESS);
		helper.setTo(mail.getTo());
		helper.setSubject(mail.getSubject());
		if (mail.getCc() != null) {
			InternetAddress[] cc = mail.getCc().stream().toArray(InternetAddress[]::new);
			helper.setCc(cc);
		}
		if (mail.getBcc() != null) {
			InternetAddress[] bcc = mail.getBcc().stream().toArray(InternetAddress[]::new);
			helper.setBcc(bcc);
		}
		if (mail.getText() != null) {
			helper.setText(mail.getText());
		}
		if (mail.getHtml() != null) {
			helper.setText(mail.getHtml(), true);
		}
		if (mail.getInlineImages() != null) {
			for (String key : mail.getInlineImages().keySet()) {
				helper.addInline(key, mail.getInlineImages().get(key));
			}
		}
		if (mail.getAttachment() != null) {
			for (String key : mail.getAttachment().keySet()) {
				ByteArrayResource resource = new ByteArrayResource(mail.getAttachment().get(key));
				helper.addAttachment(key, resource);
			}
		}
		mailSender.send(message);
	}
}
