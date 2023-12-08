package com.integrator.jasper.report.application.domain.service;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:almercog@gmil.com">Giancarlo Almerco</a>
 * @since 2023-10-16
 */
@Service
@PropertySource("connector.properties")
public class DataSourceProvider {

	@Value("${db.url}")
	private String connectionURL;

	@Value("${db.username}")
	private String username;

	@Value("${db.password}")
	private String password;

	@Value("${db.driver.class.name}")
	private String driverClass;

	private BasicDataSource dataSource = null;

	public DataSource getDataSource() {
		if (dataSource == null) {
			dataSource = new BasicDataSource();
			try {
				dataSource.setDriverClassName(driverClass);
				dataSource.setUrl(connectionURL);
				dataSource.setUsername(username);
				dataSource.setPassword(password);
			} catch (Exception e) {
				throw new RuntimeException("Failed to build database connection", e);
			}
		}
		return dataSource;
	}

}
