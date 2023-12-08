package com.challenge.api.loan.adapter.out.persistence.mapper;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.challenge.api.loan.adapter.out.persistence.entity.LoanMongoEntity;
import com.challenge.api.loan.application.domain.model.Loan;

@Component
public class LoanMapper {

	public Loan mapToDomainEntity(LoanMongoEntity loanEntity) {
		return new Loan(loanEntity.getId(), loanEntity.getAmount(), loanEntity.getRate(), loanEntity.getTermType(),
				loanEntity.getRepaymentTerm(), loanEntity.getWithGracePeriod(), loanEntity.getGracePeriod(),
				loanEntity.getDisbursementDate(), LocalDate.parse(loanEntity.getLoanStartDate()),
				LocalDate.parse(loanEntity.getLoanEndDate()), loanEntity.getEmail(), loanEntity.getFeeSum(),
				loanEntity.getInterestSum());
	}

	public LoanMongoEntity mapToMongoEntity(Loan loan) {
		LoanMongoEntity loanEntity = new LoanMongoEntity();
		loanEntity.setAmount(loan.getAmount());
		loanEntity.setRate(loan.getRate());
		loanEntity.setTermType(loan.getTermType());
		loanEntity.setRepaymentTerm(loan.getRepaymentTerm());
		loanEntity.setWithGracePeriod(loan.getWithGracePeriod());
		loanEntity.setGracePeriod(loan.getGracePeriod());
		loanEntity.setDisbursementDate(loan.getDisbursementDate());
		loanEntity.setLoanStartDate(loan.getLoanStartDate().toString());
		loanEntity.setLoanEndDate(loan.getLoanEndDate().toString());
		loanEntity.setEmail(loan.getEmail());
		loanEntity.setFeeSum(loan.getFeeSum());
		loanEntity.setInterestSum(loan.getInterestSum());
		return loanEntity;
	}
}
