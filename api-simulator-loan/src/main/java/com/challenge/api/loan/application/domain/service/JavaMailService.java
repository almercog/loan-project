package com.challenge.api.loan.application.domain.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.transaction.annotation.Transactional;

import com.challenge.api.loan.application.port.in.JavaMailUseCase;
import com.challenge.api.loan.common.Constants;
import com.challenge.api.loan.common.UseCase;
import com.integrator.mail.application.domain.model.Mail;
import com.integrator.mail.application.port.in.MailUseCase;

import jakarta.mail.MessagingException;

@UseCase
@Transactional
@ComponentScan(basePackages = "com.integrator.mail.*")
public class JavaMailService implements JavaMailUseCase {

	private static final Logger logger = LoggerFactory.getLogger(JavaMailService.class);

	@Autowired
	private MailUseCase mailUseCase;

	@Override
	public void sendMessage(JSONObject loan, byte[] loanPdf) throws MessagingException, IOException {
		logger.debug("sendMessageHtmlWithAttachment");
		String fileName = "reporte.pdf";
		Map<String, byte[]> attachment = new HashMap<>();
		attachment.put(fileName, loanPdf);
		Mail mail = new Mail();
		mail.setTo(loan.getString("email"));
		mail.setSubject("BBVA - Simulacion de Prestamos");
		mail.setTemplateModel(loan.toMap());
		mail.setAttachment(attachment);
		mail.setInlineImages(getResource(Constants.LOAN_MAIL_LOGO));
		mail.setHtml(getStringFromResource(Constants.LOAN_MAIL_HTML));
		mailUseCase.sendMessageUsingVelocityTemplate(mail);
	}

	private Map<String, Resource> getResource(String logo) throws IOException {
		Resource resource = new ClassPathResource("classpath:/".concat(logo));
		if (resource.exists()) {
			logger.info("OK...");
		} else {
			resource = new ClassPathResource(logo);
		}
		Map<String, Resource> mapResource = new HashMap<>();
		mapResource.put("logo", resource);
		return mapResource;
	}

	private String getStringFromResource(String resource) throws IOException {
		InputStream template = getClass().getClassLoader().getResource(resource).openStream();
		StringWriter writer = new StringWriter();
		IOUtils.copy(template, writer, StandardCharsets.UTF_8);
		return writer.toString();
	}
}
