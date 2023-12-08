package com.challenge.api.loan.application.port.in;

import java.io.IOException;

public interface JasperReportUseCase {

	String generateUrlPDFReport(String path, String id, String content);

	byte[] generatePDFReport(String content) throws IOException;
}
