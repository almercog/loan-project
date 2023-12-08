package com.integrator.jasper.report.application.domain.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.integrator.jasper.report.application.port.in.ReportUseCase;
import com.integrator.jasper.report.common.Constants;
import com.integrator.jasper.report.common.ExportType;
import com.integrator.jasper.report.common.ExportUtil;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JsonDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

/**
 * 
 * @author <a href="mailto:almercog@gmail.com">Giancarlo Almerco</a>
 * @since 2023-10-16
 *
 */
@Component
public class ReportService implements ReportUseCase {

	private static final Logger logger = LoggerFactory.getLogger(ReportService.class);

	private OutputStream exportReport(JasperPrint jasperPrint, ExportType exportType) throws JRException {
		logger.info(Constants.JASPER_REPORT_EXPORT_OS, exportType);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		switch (exportType) {
		case PDF:
			ExportUtil.exportReportToPdfStream(jasperPrint, outputStream);
			break;
		case XLS:
			ExportUtil.exportReportXLSToStream(jasperPrint, outputStream);
			break;
		case CSV:
			ExportUtil.exportReportCSVToStream(jasperPrint, outputStream);
			break;
		case HTML:
			ExportUtil.exportReportHTMLToStream(jasperPrint, outputStream);
			break;
		case DOC:
			ExportUtil.exportReportDocToStream(jasperPrint, outputStream);
			break;
		default:
			throw new ExportReportException("ExportType is not support by jasper " + exportType.name());
		}
		return outputStream;
	}

	private void exportReport(JasperPrint jasperPrint, ExportType exportType, File destFile)
			throws JRException, FileNotFoundException {
		logger.info(Constants.JASPER_REPORT_EXPORT, destFile.getAbsolutePath());
		switch (exportType) {
		case PDF:
			ExportUtil.exportReportToPdfFile(jasperPrint, destFile.getAbsolutePath());
			break;
		case XLS:
			ExportUtil.exportToXLSFile(jasperPrint, destFile.getAbsolutePath());
			break;
		case CSV:
			ExportUtil.exportToCSVFile(jasperPrint, destFile.getAbsolutePath());
			break;
		case HTML:
			ExportUtil.exportReportToHtmlFile(jasperPrint, destFile.getAbsolutePath());
			break;
		case DOC:
			ExportUtil.exportReportToDocFile(jasperPrint, destFile.getAbsolutePath());
			break;
		default:
			throw new ExportReportException("ExportType is not support by jasper " + exportType.name());
		}
	}

	private JasperReport getJasperReport(String jrxml) throws JRException {
		InputStream jrxmlInput = getResource(jrxml);
		JasperDesign design = JRXmlLoader.load(jrxmlInput);
		return JasperCompileManager.compileReport(design);
	}

	private InputStream getResource(String jrxmlResourcePath) {
		logger.debug("getResource {}", jrxmlResourcePath);
		try {
			Resource resource = new ClassPathResource(jrxmlResourcePath);
			if (resource.exists()) {
				return resource.getInputStream();
			}
			return getClass().getResourceAsStream(jrxmlResourcePath);
		} catch (Exception e) {
			throw new ExportReportException("Resource not found in class path " + jrxmlResourcePath, e);
		}
	}

	public OutputStream generateReport(String jrxmlResourcePath, Map<String, Object> parameters, DataSource dataSource,
			ExportType jasperExportType) {
		try (Connection conn = dataSource.getConnection()) {
			logger.info(Constants.JASPER_REPORT_LOADING);
			JasperReport jasperReport = getJasperReport(jrxmlResourcePath);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, conn);
			return exportReport(jasperPrint, jasperExportType);
		} catch (JRException | SQLException e) {
			throw new ExportReportException(Constants.JASPER_REPORT_FAILED, e);
		}
	}

	public void generateReport(String jrxmlResourcePath, Map<String, Object> parameters, DataSource dataSource,
			ExportType jasperExportType, File destFile) {
		try (Connection conn = dataSource.getConnection()) {
			logger.info(Constants.JASPER_REPORT_LOADING);
			JasperReport jasperReport = getJasperReport(jrxmlResourcePath);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, conn);
			exportReport(jasperPrint, jasperExportType, destFile);
		} catch (JRException | FileNotFoundException | SQLException e) {
			throw new ExportReportException(Constants.JASPER_REPORT_FAILED, e);
		}
	}

	public OutputStream generateReport(String jrxmlResourcePath, Map<String, Object> parameters, String data,
			ExportType jasperExportType) {
		try {
			logger.info(Constants.JASPER_REPORT_LOADING);
			JasperReport jasperReport = getJasperReport(jrxmlResourcePath);
			JsonDataSource jsonDataSource = new JsonDataSource(new ByteArrayInputStream(data.getBytes()));
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, jsonDataSource);
			return exportReport(jasperPrint, jasperExportType);
		} catch (JRException e) {
			throw new ExportReportException(Constants.JASPER_REPORT_FAILED, e);
		}
	}

	public void generateReport(String jrxmlResourcePath, Map<String, Object> parameters, String data,
			ExportType jasperExportType, File destFile) {
		try {
			logger.info(Constants.JASPER_REPORT_LOADING);
			JasperReport jasperReport = getJasperReport(jrxmlResourcePath);
			JsonDataSource jsonDataSource = new JsonDataSource(new ByteArrayInputStream(data.getBytes()));
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, jsonDataSource);
			exportReport(jasperPrint, jasperExportType, destFile);
		} catch (JRException | FileNotFoundException e) {
			throw new ExportReportException(Constants.JASPER_REPORT_FAILED, e);
		}
	}

}
