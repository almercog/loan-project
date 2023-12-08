package com.challenge.api.loan.application.port.in;

import com.challenge.api.loan.application.domain.model.LoanRequest;
import com.challenge.api.loan.application.domain.model.LoanResponse;

public interface LoanUseCase {

	public LoanResponse simulate(LoanRequest loanRequest);
}
