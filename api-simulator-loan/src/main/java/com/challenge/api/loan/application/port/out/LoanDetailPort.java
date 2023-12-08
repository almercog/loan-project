package com.challenge.api.loan.application.port.out;

import com.challenge.api.loan.application.domain.model.LoanDetail;

public interface LoanDetailPort {

	LoanDetail saveLoanDetail(LoanDetail loanDetail);
}
