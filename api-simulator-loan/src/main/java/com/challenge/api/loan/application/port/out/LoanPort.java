package com.challenge.api.loan.application.port.out;

import com.challenge.api.loan.application.domain.model.Loan;

public interface LoanPort {

	Loan saveLoan(Loan loan);

	Loan updateLoan(Loan loan);
}
