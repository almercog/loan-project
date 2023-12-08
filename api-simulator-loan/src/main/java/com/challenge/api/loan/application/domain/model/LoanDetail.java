package com.challenge.api.loan.application.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.challenge.api.loan.common.Constants;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanDetail {

	private String id;
	private String idLoan;
	private Integer period;
	private String due;
	private String interest;
	private String amortization;
	private String totalAmortized;
	private String outstandingCapital;
	private String termPaymentDueDate;

	public BigDecimal interestToDecimal(BigDecimal amount, BigDecimal rate) {
		return amount.multiply(rate, Constants.MATH_CONTEXT_HALF_EVEN_8);
	}

	public LocalDate dueDate(String termType, LocalDate loanStartDate, Integer period) {
		if (termType.equals(Constants.TERM_TYPE_MONTH)) {
			return loanStartDate.plusMonths(period);
		} else {
			return loanStartDate.plusYears(period);
		}
	}

	public BigDecimal interestDecimal(BigDecimal capital, BigDecimal rate) {
		return capital.multiply(rate, Constants.MATH_CONTEXT_HALF_EVEN_4);
	}

	public BigDecimal amoritizationDecimal(BigDecimal due, BigDecimal interest) {
		return due.subtract(interest);
	}

	public BigDecimal totalAmortizedDecimal(BigDecimal totalAmortized, BigDecimal amortization) {
		return totalAmortized.add(amortization, Constants.MATH_CONTEXT_HALF_EVEN_4);
	}

	public BigDecimal outstandingCapitalDecimal(BigDecimal outstandingCapital, BigDecimal amortization) {
		return outstandingCapital.subtract(amortization, Constants.MATH_CONTEXT_HALF_EVEN_8);
	}
}
