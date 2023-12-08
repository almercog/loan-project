package com.challenge.api.loan.adapter.out.persistence.mapper;

import org.springframework.stereotype.Component;

import com.challenge.api.loan.adapter.out.persistence.entity.LoanDetailMongoEntity;
import com.challenge.api.loan.application.domain.model.LoanDetail;

@Component
public class LoanDetailMapper {

	public LoanDetail mapToDomainEntity(LoanDetailMongoEntity loanEntity) {
		return new LoanDetail(loanEntity.getId(), loanEntity.getIdLoan(), loanEntity.getPeriod(),
				loanEntity.getInstallmentPaymentDueDate(), loanEntity.getDue(), loanEntity.getInterest(),
				loanEntity.getAmortization(), loanEntity.getTotalAmortized(), loanEntity.getOutstandingCapital());
	}

	public LoanDetailMongoEntity mapToMongoEntity(LoanDetail loanDetail) {
		LoanDetailMongoEntity loanEntity = new LoanDetailMongoEntity();
		loanEntity.setIdLoan(loanDetail.getIdLoan());
		loanEntity.setPeriod(loanDetail.getPeriod());
		loanEntity.setInstallmentPaymentDueDate(loanDetail.getTermPaymentDueDate());
		loanEntity.setDue(loanDetail.getDue());
		loanEntity.setInterest(loanDetail.getInterest());
		loanEntity.setAmortization(loanDetail.getAmortization());
		loanEntity.setTotalAmortized(loanDetail.getTotalAmortized());
		loanEntity.setOutstandingCapital(loanDetail.getOutstandingCapital());
		return loanEntity;
	}
}
