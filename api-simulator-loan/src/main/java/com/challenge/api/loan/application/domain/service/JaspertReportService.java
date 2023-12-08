package com.challenge.api.loan.application.domain.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.Transactional;

import com.challenge.api.loan.application.port.in.JasperReportUseCase;
import com.challenge.api.loan.common.Constants;
import com.challenge.api.loan.common.UseCase;
import com.integrator.jasper.report.application.port.in.ReportUseCase;
import com.integrator.jasper.report.common.ExportType;

@UseCase
@Transactional
@ComponentScan("com.integrator.jasper.report.application.domain.service")
public class JaspertReportService implements JasperReportUseCase {

	private static final Logger logger = LoggerFactory.getLogger(JaspertReportService.class);

	@Autowired
	private ReportUseCase reportService;

	@Override
	public String generateUrlPDFReport(String path, String id, String content) {
		logger.debug("generateUrlPDFReport {}", content);
		String pdfFileName = path + id + Constants.EXT_FILES;
		File pdffile = new File(pdfFileName);
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("jsonData", content);
		parameters.put("reportDir", "classpath:".concat(Constants.TEMPLATE_REPORT));
		reportService.generateReport(Constants.LOAN_REPORT, parameters, content, ExportType.PDF, pdffile);
		return pdfFileName;
	}

	@Override
	public byte[] generatePDFReport(String content) throws IOException {
		logger.debug("generatePDFReport {}", content);
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("jsonData", content);
		parameters.put("reportDir", Constants.PATH_REPORT);
		ByteArrayOutputStream baos = (ByteArrayOutputStream) reportService.generateReport(Constants.LOAN_REPORT, parameters,
				content, ExportType.PDF);
		return baos.toByteArray();
	}
}
