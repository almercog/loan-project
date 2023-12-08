package com.integrator.mail.application.domain.model;

import java.util.List;
import java.util.Map;

import org.springframework.core.io.Resource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mail {
	private String to;
	private String subject;
	private String text;
	private String html;
	private String templateName;
	private String pathToAttachment;
	private List<String> cc;
	private List<String> bcc;
	private Map<String, byte[]> attachment;
	private Map<String, Object> templateModel;
	private Map<String, Resource> inlineImages;

}
