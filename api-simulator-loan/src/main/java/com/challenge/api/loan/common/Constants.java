package com.challenge.api.loan.common;

import java.io.File;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;

public class Constants {

	private Constants() {
		throw new IllegalStateException("Constants class");
	}

	public static final int SCALE_1 = 1;
	public static final int SCALE_2 = 2;
	public static final int SCALE_4 = 4;
	public static final int SCALE_8 = 8;

	public static final String IND_SI = "S";
	public static final String IND_NO = "N";
	public static final String INITIAL_SUM = "0";

	public static final Integer DEF_GRACE_PERIOD = 0;

	public static final String TERM_TYPE_YEAR = "A";
	public static final String TERM_TYPE_MONTH = "M";

	public static final LocalDate TODAY = LocalDate.now();

	public static final String DECIMAL_FORMAT = "#,##0.00";

	public static final int API_COD_RESP_OK = 200;
	public static final int API_COD_RESP_ERROR = 405;
	public static final String API_DSC_RESP_OK = "OK";
	public static final String API_DSC_RESP_ERROR = "Error";
	public static final String API_MSG_RESP_OK = "We sent you an email to ";
	public static final String API_MSG_RESP_PAR_ERROR = "Setup your parameters";

	public static final BigDecimal DECIMAL_CERO = new BigDecimal("0");
	public static final BigDecimal DECIMAL_UNO = new BigDecimal("1");

	public static final BigDecimal PERCENTAGE_100 = new BigDecimal("100");

	public static final String TEMPLATE = "templates".concat(File.separator);
	public static final String TEMPLATE_REPORT = TEMPLATE.concat("report").concat(File.separator);
	public static final String PATH_REPORT = "classpath:".concat(File.separator).concat(TEMPLATE_REPORT);
	public static final String LOAN_REPORT = File.separator.concat(TEMPLATE_REPORT).concat("LoanMainReport.jrxml");
	public static final String TEMPLATE_MAIL = TEMPLATE.concat("mail").concat(File.separator);
	public static final String LOAN_MAIL_LOGO = TEMPLATE_MAIL.concat("logoBbvaWhite.png");
	public static final String LOAN_MAIL_HTML = TEMPLATE_MAIL.concat("template-velocity.vm");

	public static final String PARAMETER_CODE = "LOAN_SIMULATOR";
	public static final String PARAMETER_NAME_PF = "PAYMENT_FRECUENCY";
	public static final String PARAMETER_NAME_GP = "GRACE_PERIOD";
	public static final String PARAMETER_NAME_RD = "REPORT_DIRECTORY";
	public static final String PARAMETER_NAME_MC = "MAIL_CONFIG";
	public static final String PARAMETER_NAME_PF_ER = "effectiveRate";
	public static final String PARAMETER_NAME_VAL_DSC = "description";

	public static final String EXT_FILES = ".pdf";
	public static final String NOREPLY_ADDRESS = "noreply@baeldung.com";

	public static final MathContext MATH_CONTEXT_HALF_EVEN_3 = new MathContext(3, RoundingMode.HALF_EVEN);
	public static final MathContext MATH_CONTEXT_HALF_EVEN_4 = new MathContext(4, RoundingMode.HALF_EVEN);
	public static final MathContext MATH_CONTEXT_HALF_EVEN_8 = new MathContext(8, RoundingMode.HALF_EVEN);
}
