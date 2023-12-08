package com.challenge.api.loan.application.domain.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.challenge.api.loan.application.domain.model.Loan;
import com.challenge.api.loan.application.domain.model.LoanDetail;
import com.challenge.api.loan.application.domain.model.LoanRequest;
import com.challenge.api.loan.application.domain.model.LoanResponse;
import com.challenge.api.loan.application.domain.model.Parameter;
import com.challenge.api.loan.application.port.in.JavaMailUseCase;
import com.challenge.api.loan.application.port.in.LoanUseCase;
import com.challenge.api.loan.application.port.in.ParameterUseCase;
import com.challenge.api.loan.application.port.in.JasperReportUseCase;
import com.challenge.api.loan.application.port.out.LoanDetailPort;
import com.challenge.api.loan.application.port.out.LoanPort;
import com.challenge.api.loan.common.Constants;
import com.challenge.api.loan.common.UseCase;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@UseCase
@Transactional
public class LoanService implements LoanUseCase {

	private static final Logger logger = LoggerFactory.getLogger(LoanService.class);
	private final LoanPort loanPort;
	private final LoanDetailPort loanDetailPort;

	private final JavaMailUseCase mailService;
	private final JasperReportUseCase reportService;
	private final ParameterUseCase parameterService;

	@Override
	public LoanResponse simulate(LoanRequest loanRequest) {
		logger.info("payload {}", loanRequest.getPayload());
		Loan loan = loanRequest.getPayload();
		loan.startDate();
		loan.endDate();
		logger.debug("loan {}", loan);
		loan = loanPort.saveLoan(loan);
		LoanResponse res = new LoanResponse();
		res.setCode(Constants.API_COD_RESP_OK);
		res.setType(Constants.API_DSC_RESP_OK);

		try {
			List<Parameter> lstParameter = parameterService.findAllByCode();
			if (lstParameter == null || lstParameter.isEmpty()) {
				res.setCode(Constants.API_COD_RESP_ERROR);
				res.setType(Constants.API_DSC_RESP_ERROR);
				res.setMessage(Constants.API_MSG_RESP_PAR_ERROR);
				return res;
			}
			Parameter parameter = parameterService.findParameterByName(lstParameter, Constants.PARAMETER_NAME_PF);
			JSONObject jsonPaymentFrecuency = parameterService.findByPaymentFrecuency(parameter, loan.getTermType());
			LocalDate dueDate = loan.getLoanStartDate();
			BigDecimal interest;
			BigDecimal amortization;
			BigDecimal rate = loan.rateToDecimal(jsonPaymentFrecuency.get(Constants.PARAMETER_NAME_PF_ER).toString());
			BigDecimal interestSum = Constants.DECIMAL_CERO;
			BigDecimal totalAmortized = Constants.DECIMAL_CERO;
			BigDecimal outstandingCapital = loan.amountToDecimal();
			BigDecimal due = loan.dueToDecimal(outstandingCapital, rate, loan.getRepaymentTerm());

			List<LoanDetail> listLoanDetail = new LinkedList<>();
			int term = loan.getRepaymentTerm() + loan.getGracePeriod();
			for (int period = 0; period <= term; period++) {
				LoanDetail loanDetail = new LoanDetail();
				dueDate = loanDetail.dueDate(loan.getTermType(), dueDate, Constants.SCALE_1);
				loanDetail.setIdLoan(loan.getId());
				loanDetail.setPeriod(period);
				loanDetail.setTermPaymentDueDate(dueDate.toString());
				if (period > 0 && period > loan.getGracePeriod()) {
					interest = loanDetail.interestToDecimal(outstandingCapital, rate);
					interestSum = interestSum.add(interest);
					amortization = loanDetail.amoritizationDecimal(due, interest);
					totalAmortized = loanDetail.totalAmortizedDecimal(totalAmortized, amortization);
					outstandingCapital = loanDetail.outstandingCapitalDecimal(outstandingCapital, amortization);
					loanDetail.setDue(due.toString());
					loanDetail.setInterest(interest.toString());
					loanDetail.setAmortization(amortization.toString());
					loanDetail.setTotalAmortized(totalAmortized.toString());
				}
				loanDetail.setOutstandingCapital(outstandingCapital.toString());
				listLoanDetail.add(loanDetail);
				loanDetailPort.saveLoanDetail(loanDetail);
			}
			loan.setInterestSum(interestSum.toString());
			loan.setFeeSum(loan.amountToDecimal().add(interestSum).toString());
			loanPort.updateLoan(loan);
			JSONObject data = getJSONObject(loan.getJsonObject(rate, jsonPaymentFrecuency), listLoanDetail);
			byte[] loanPdf = this.generatePDFReport(data);
			this.sendMail(data, loanPdf);
			res.setMessage(Constants.API_MSG_RESP_OK.concat(loan.getEmail()));
		} catch (Exception e) {
			logger.error("ERROR {0}", e);
			res.setCode(Constants.API_COD_RESP_ERROR);
			res.setType(Constants.API_DSC_RESP_ERROR);
			res.setMessage(e.getMessage());
		}
		return res;
	}

	private JSONObject getJSONObject(JSONObject loan, List<LoanDetail> listLoanDetail) {
		JSONObject data = new JSONObject();
		data.put("loan", loan);
		data.put("data", listLoanDetail);
		return data;
	}

	private byte[] generatePDFReport(JSONObject data) throws IOException {
		logger.info("generatePDFReport {} ", data);
		return reportService.generatePDFReport(data.toString());
	}

	private void sendMail(JSONObject data, byte[] loanPdf) throws MessagingException, IOException {
		logger.debug("sendMail {}", data);
		 mailService.sendMessage(data.getJSONObject("loan"), loanPdf);
	}
}
