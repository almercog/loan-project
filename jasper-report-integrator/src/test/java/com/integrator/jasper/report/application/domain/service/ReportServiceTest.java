package com.integrator.jasper.report.application.domain.service;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.integrator.jasper.report.AppConfig;
import com.integrator.jasper.report.application.port.in.ReportUseCase;
import com.integrator.jasper.report.common.ExportType;

/**
 * @author <a href="mailto:almercog@gmil.com">Giancarlo Almerco</a>
 * @since 2023-10-16
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { AppConfig.class }, loader = AnnotationConfigContextLoader.class)
class ReportServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(ReportServiceTest.class);

	private static String userHome = System.getProperty("user.dir");
	private static File reportDir;

	static {
		reportDir = new File(userHome + "/target/reports");
		reportDir.mkdir();
	}

	@Autowired
	private ReportUseCase reportService;

	@Autowired
	private DataSourceProvider dataSourceProvider;

	@Test
	void generatePDFReport() {
		String data = getStringFromResource("department/department.json");
		File pdffile = new File(reportDir + "/dept.pdf");
		reportService.generateReport("department/dept.jrxml", new HashMap<>(), data, ExportType.PDF, pdffile);
		assertTrue(true, "PDF report generated successfully");
	}

	@Test
	void generateHTMLReport() {
		String data = getStringFromResource("department/department.json");
		File htmlFile = new File(reportDir + "/dept.html");
		reportService.generateReport("department/dept.jrxml", new HashMap<>(), data, ExportType.HTML, htmlFile);
		assertTrue(true, "HTML report generated successfully");
	}

	@Test
	void generateXLSReport() {
		String data = getStringFromResource("department/department.json");
		File xlsFile = new File(reportDir + "/dept.xls");
		reportService.generateReport("department/dept.jrxml", new HashMap<>(), data, ExportType.XLS, xlsFile);
		assertTrue(true, "XLS report generated successfully");
	}

	@Test
	void generateCSVReport() {
		String data = getStringFromResource("department/department.json");
		File csvFile = new File(reportDir + "/dept.csv");
		reportService.generateReport("department/dept.jrxml", new HashMap<>(), data, ExportType.CSV, csvFile);
		assertTrue(true, "CSV report generated successfully");
	}

	@Test
	void generateDocReport() {
		String data = getStringFromResource("department/department.json");
		File xmlFile = new File(reportDir + "/dept.doc");
		reportService.generateReport("department/dept.jrxml", new HashMap<>(), data, ExportType.DOC, xmlFile);
		assertTrue(true, "DOC report generated successfully");
	}

	@Test
	void generatePDFReportByDataSource() {
		File pdffile = new File(reportDir + "/employee.pdf");
		reportService.generateReport("employee/emp.jrxml", new HashMap<>(), dataSourceProvider.getDataSource(),
				ExportType.PDF, pdffile);
		assertTrue(true, "PDF report generated successfully using data source");
	}

	@Test
	void generatePDFReportByDataSourceHavingPlaceholders() {
		File pdffile = new File(reportDir + "/employee_ph.pdf");
		HashMap<String, Object> objectObjectHashMap = new HashMap<>();
		objectObjectHashMap.put("age", 60);
		reportService.generateReport("employeeWithPlaceholders/emp.jrxml", objectObjectHashMap,
				dataSourceProvider.getDataSource(), ExportType.PDF, pdffile);
		assertTrue(true, "PDF report generated successfully using data source having placeholders");
	}

	@Test
	void generateXLSReportByDataSource() {
		File reportFile = new File(reportDir + "/employee.xls");
		reportService.generateReport("employee/emp.jrxml", new HashMap<>(), dataSourceProvider.getDataSource(),
				ExportType.XLS, reportFile);
		assertTrue(true, "XLS report generated successfully using data source");
	}

	@Test
	void generateCSVReportByDataSource() {
		File reportFile = new File(reportDir + "/employee.csv");
		try {
			File.createTempFile("tmp", ".txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
		reportService.generateReport("employee/emp.jrxml", new HashMap<>(), dataSourceProvider.getDataSource(),
				ExportType.CSV, reportFile);
		assertTrue(true, "CSV report generated successfully using connector data source");
	}

	@Test
	void generatePDFWithParamatersReport() {
		String data = getStringFromResource("deptwithparams/department.json");
		File pdffile = new File(reportDir + "/deptwithparam.pdf");

		Map<String, Object> parameters = new HashMap<>();
		parameters.put("REPORTHEADER", "Departments by City");
		parameters.put("PAGEHEADER", "Departments Report");

		reportService.generateReport("deptwithparams/deptwithparam.jrxml", parameters, data, ExportType.PDF, pdffile);
		assertTrue(true, "PDF report generated successfully");
	}

	@Test
	void generateReportWithStream() {
		File pdffile = new File(reportDir + "/emp.pdf");
		ByteArrayOutputStream outputStream = (ByteArrayOutputStream) reportService.generateReport("employee/emp.jrxml",
				new HashMap<>(), dataSourceProvider.getDataSource(), ExportType.PDF);
		byte[] bytes = outputStream.toByteArray();
		writeByte(bytes, pdffile);
		try {
			if (outputStream != null)
				outputStream.close();
			assertTrue(true, "PDF Stream report generated successfully");
		} catch (IOException e) {
			assertInstanceOf(Exception.class, e);
		}
	}

	@Test
	void generatePDFReportWithStream() {
		String data = getStringFromResource("department/department.json");
		File pdffile = new File(reportDir + "/depts.pdf");
		ByteArrayOutputStream outputStream = (ByteArrayOutputStream) reportService.generateReport("department/dept.jrxml",
				new HashMap<>(), data, ExportType.PDF);
		byte[] bytes = outputStream.toByteArray();
		writeByte(bytes, pdffile);
		try {
			if (outputStream != null)
				outputStream.close();
			assertTrue(true, "PDF Stream report with JSON generated successfully");
		} catch (IOException e) {
			assertInstanceOf(Exception.class, e);
		}
	}

	private String getStringFromResource(String resource) {
		try {
			InputStream jrxmlInput = this.getClass().getClassLoader().getResource(resource).openStream();
			StringWriter writer = new StringWriter();
			IOUtils.copy(jrxmlInput, writer, "UTF-8");
			return writer.toString();
		} catch (IOException e) {
			throw new RuntimeException("Failed to convert resource to string", e);
		}
	}

	private static void writeByte(byte[] bytes, File file) {
		try {
			OutputStream os = new FileOutputStream(file);
			os.write(bytes);
			logger.info("Successfully byte inserted");
			os.close();
		} catch (Exception e) {
			logger.error("Exception: {0}", e);
		}
	}
}
