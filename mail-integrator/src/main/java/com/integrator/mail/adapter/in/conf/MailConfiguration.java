package com.integrator.mail.adapter.in.conf;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.TemplateLoader;

import java.util.Properties;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

@Configuration
@PropertySource("classpath:application.yml")
public class MailConfiguration {

	@Value("${spring.mail.host}")
	private String mailServerHost;

	@Value("${spring.mail.port}")
	private Integer mailServerPort;

	@Value("${spring.mail.username}")
	private String mailServerUsername;

	@Value("${spring.mail.password}")
	private String mailServerPassword;

	@Value("${spring.mail.properties.mail.smtp.auth}")
	private String mailServerAuth;

	@Value("${spring.mail.properties.mail.smtp.starttls.enable}")
	private String mailServerStartTls;

	@Value("${spring.mail.templates.path}")
	private String mailTemplatesPath;

	@Bean("javaMailSender")
	JavaMailSender getJavaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(mailServerHost);
		mailSender.setPort(mailServerPort);
		mailSender.setUsername(mailServerUsername);
		mailSender.setPassword(mailServerPassword);

		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", mailServerAuth);
		props.put("mail.smtp.starttls.enable", mailServerStartTls);
		props.put("mail.debug", "true");
		return mailSender;
	}

	@Bean
	SimpleMailMessage templateSimpleMessage() {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setText("This is the test email template for your email: \n%s\n");
		return message;
	}

	@Bean
	SpringTemplateEngine thymeleafTemplateEngine(ITemplateResolver templateResolver) {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(templateResolver);
		templateEngine.setTemplateEngineMessageSource(emailMessageSource());
		return templateEngine;
	}

	@Bean
	ClassLoaderTemplateResolver templateResolver() {
		ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
		templateResolver.setPrefix(mailTemplatesPath + "/");
		templateResolver.setTemplateMode("HTML5");
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode("XHTML");
		templateResolver.setCharacterEncoding("UTF-8");
		templateResolver.setOrder(1);
		return templateResolver;
	}

	@Bean
	FreeMarkerConfigurer freemarkerClassLoaderConfig() {
		freemarker.template.Configuration configuration = new freemarker.template.Configuration(
				freemarker.template.Configuration.VERSION_2_3_27);
		TemplateLoader templateLoader = new ClassTemplateLoader(this.getClass(), "/" + mailTemplatesPath);
		configuration.setTemplateLoader(templateLoader);
		FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
		freeMarkerConfigurer.setConfiguration(configuration);
		return freeMarkerConfigurer;
	}

	@Bean
	VelocityEngine getVelocityEngine() throws VelocityException {
		VelocityEngine engine = new VelocityEngine();
		Properties props = new Properties();
		props.put("resource.loader", "class");
		props.put("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		engine.init(props);
		return engine;
	}

	@Bean
	ResourceBundleMessageSource emailMessageSource() {
		final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("mailMessages");
		return messageSource;
	}
}
