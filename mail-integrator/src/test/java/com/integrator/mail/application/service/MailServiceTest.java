package com.integrator.mail.application.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.integrator.mail.AppConfig;
import com.integrator.mail.application.domain.model.Mail;
import com.integrator.mail.application.port.in.MailUseCase;

import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;

/**
 * @author giancarlo.almerco
 *
 */
@SpringBootTest
@TestPropertySource("classpath:application.properties")
@ContextConfiguration(classes = { AppConfig.class }, loader = AnnotationConfigContextLoader.class)
class MailServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(MailServiceTest.class);

	@Autowired
	private MailUseCase mailUseCase;

	@Test
	void sendSimpleMessage() {
		logger.info("sendSimpleMessage");
		Mail mail = new Mail();
		mail.setTo("almercog@gmail.com");
		mail.setSubject("BBVA - Sistema de amortizacion Frances para simulacion de Prestamos");
		mail.setText("Example message Simple");
		mailUseCase.sendSimpleMessage(mail);
		assertTrue(true, "send simple message successfully");
	}

	@Test
	void sendSimpleMessageUsingTemplate() {
		logger.info("sendSimpleMessageUsingTemplate");
		Map<String, Object> templateModel = new HashMap<>();
		templateModel.put("M", "MacOS");
		templateModel.put("L", "Linux");
		Mail mail = new Mail();
		mail.setTo("almercog@gmail.com");
		mail.setSubject("BBVA - Sistema de amortizacion Frances para simulacion de Prestamos");
		mail.setTemplateModel(templateModel);
		mailUseCase.sendSimpleMessageUsingTemplate(mail);
		assertTrue(true, "send simple message using template successfully");
	}

	@Test
	void sendMessageWithAttachment() throws MessagingException, IOException {
		logger.info("sendMessageWithAttachment");
		String fileName = "its-all-okay.jpg";
		Map<String, byte[]> attachment = new HashMap<>();
		byte[] data = this.getClass().getClassLoader().getResourceAsStream("assets/".concat(fileName)).readAllBytes();
		attachment.put(fileName, data);
		Mail mail = new Mail();
		mail.setTo("almercog@gmail.com");
		mail.setSubject("BBVA - Sistema de amortizacion Frances para simulacion de Prestamos");
		mail.setText("Example message with attachment");
		mail.setAttachment(attachment);
		mailUseCase.sendMessageWithAttachment(mail);
		assertTrue(true, "send simple message with attachment successfully");
	}

	@Test
	void sendMessageUsingVelocityTemplate() throws MessagingException, IOException {
		logger.info("sendMessageUsingVelocityTemplate");
		Mail mail = new Mail();
		mail.setTo("almercog@gmail.com");
		mail.setSubject("BCP - Sistema de amortizacion Frances para simulacion de Prestamos");
		mail.setHtml(getStringFromResource("templates/template-velocity.vm"));
		mail.setInlineImages(getResource("logo-bcp.png"));
		mail.setTemplateModel(getTemplateModel());
		mailUseCase.sendMessageUsingVelocityTemplate(mail);
		assertTrue(true, "send simple message using velocity template successfully");
	}

	@Test
	void sendMessageUsingThymeleafTemplate() throws IOException, MessagingException {
		Mail mail = new Mail();
		mail.setTo("almercog@gmail.com");
		mail.setSubject("BBVA - Sistema de amortizacion Frances para simulacion de Prestamos");
		mail.setTemplateName("template-thymeleaf.html");
		mail.setInlineImages(getResource("logo-bbva.png"));
		mail.setTemplateModel(getTemplateModel());
		mailUseCase.sendMessageUsingThymeleafTemplate(mail);
		assertTrue(true, "send simple message using velocity template successfully");
	}

	@Test
	void sendMessageUsingFreemarkerTemplate() throws IOException, TemplateException, MessagingException {
		Mail mail = new Mail();
		mail.setTo("almercog@gmail.com");
		mail.setSubject("Interbank - Sistema de amortizacion Frances para simulacion de Prestamos");
		mail.setHtml(getStringFromResource("templates/template-freemarker.ftl"));
		mail.setTemplateName("template-freemarker.html");
		mail.setInlineImages(getResource("logo-interbank.png"));
		mail.setTemplateModel(getTemplateModel());
		mailUseCase.sendMessageUsingFreemarkerTemplate(mail);
		assertTrue(true, "send simple message using velocity template successfully");
	}

	@Test
	void sendMessageUsingVelocityTemplateWithAttachment() throws MessagingException, IOException {
		logger.info("sendMessageUsingVelocityTemplate");
		String fileName = "its-all-okay.jpg";
		Map<String, byte[]> attachment = new HashMap<>();
		byte[] data = this.getClass().getClassLoader().getResourceAsStream("assets/".concat(fileName)).readAllBytes();
		attachment.put(fileName, data);
		Mail mail = new Mail();
		mail.setTo("almercog@gmail.com");
		mail.setSubject("BCP - Sistema de amortizacion Frances para simulacion de Prestamos");
		mail.setHtml(getStringFromResource("templates/template-velocity.vm"));
		mail.setInlineImages(getResource("logo-bcp.png"));
		mail.setTemplateModel(getTemplateModel());
		mail.setAttachment(attachment);
		mailUseCase.sendMessageUsingVelocityTemplate(mail);
		assertTrue(true, "send simple message using velocity template successfully");
	}

	private Map<String, Resource> getResource(String logo) throws FileNotFoundException {
		Resource resource = new ClassPathResource("assets/".concat(logo));
		Map<String, Resource> mapResource = new HashMap<>();
		mapResource.put("logo", resource);
		return mapResource;
	}

	private String getStringFromResource(String resource) {
		try {
			InputStream template = this.getClass().getClassLoader().getResource(resource).openStream();
			StringWriter writer = new StringWriter();
			IOUtils.copy(template, writer, "UTF-8");
			return writer.toString();
		} catch (IOException e) {
			throw new RuntimeException("Failed to convert resource to string", e);
		}
	}

	private Map<String, Object> getTemplateModel() throws IOException {
		Map<String, Object> templateModel = new HashMap<>();
		templateModel.put("amount", "8,000.00");
		templateModel.put("rate", "5.11");
		templateModel.put("termType", "Anual");
		templateModel.put("repaymentTerm", "10");
		templateModel.put("loanStartDate", "2023-10-29");
		templateModel.put("loanEndDate", "2033-10-29");
		templateModel.put("email", "euna_Bergstrom@hotmail.com");
		return templateModel;
	}
}
