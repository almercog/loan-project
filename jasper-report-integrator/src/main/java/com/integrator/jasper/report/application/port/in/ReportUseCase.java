package com.integrator.jasper.report.application.port.in;

import java.io.File;
import java.io.OutputStream;
import java.util.Map;

import javax.sql.DataSource;

import com.integrator.jasper.report.common.Connector;
import com.integrator.jasper.report.common.ExportType;

/**
 * 
 * @author <a href="mailto:almercog@gmail.com">Giancarlo Almerco</a>
 * @since 2023-10-16
 *
 */
@Connector(name = "jasper", description = "This connector is used to generate reports using jasper framework")
public interface ReportUseCase {

	public OutputStream generateReport(String jrxmlResourcePath, Map<String, Object> parameters, DataSource dataSource,
			ExportType jasperExportType);

	public void generateReport(String jrxmlResourcePath, Map<String, Object> parameters, DataSource dataSource,
			ExportType jasperExportType, File destFile);

	public OutputStream generateReport(String jrxmlResourcePath, Map<String, Object> parameters, String data,
			ExportType jasperExportType);

	public void generateReport(String jrxmlResourcePath, Map<String, Object> parameters, String data,
			ExportType jasperExportType, File destFile);

}