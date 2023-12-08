package com.integrator.jasper.report.application.domain.service;

public class ExportReportException extends RuntimeException {

  private static final long serialVersionUID = -6956875427901980932L;
 
  public ExportReportException() {
  	super();
  }

  public ExportReportException(String message) {
    super(message);
  }

  public ExportReportException(String message, Throwable cause) {
    super(message, cause);
  }
}
