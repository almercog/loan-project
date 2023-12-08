package com.challenge.api.loan.adapter.out.persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.challenge.api.loan.adapter.out.persistence.entity.LoanDetailMongoEntity;
import com.challenge.api.loan.adapter.out.persistence.entity.LoanMongoEntity;
import com.challenge.api.loan.adapter.out.persistence.mapper.LoanDetailMapper;
import com.challenge.api.loan.adapter.out.persistence.mapper.LoanMapper;
import com.challenge.api.loan.application.domain.model.Loan;
import com.challenge.api.loan.application.domain.model.LoanDetail;
import com.challenge.api.loan.application.port.out.LoanDetailPort;
import com.challenge.api.loan.application.port.out.LoanPort;
import com.challenge.api.loan.common.PersistenceAdapter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@PersistenceAdapter
public class LoanPersistenceAdapter implements LoanPort, LoanDetailPort {

	private static final Logger logger = LoggerFactory.getLogger(LoanPersistenceAdapter.class);

	private final SpringDataLoanRepository loanRepository;
	private final SpringDataLoanDetailRepository loanDetailRepository;
	private final LoanMapper loanMapper;
	private final LoanDetailMapper loanDetailMapper;

	@Override
	public Loan saveLoan(Loan loan) {
		LoanMongoEntity loanEntity = loanMapper.mapToMongoEntity(loan);
		logger.debug("loanEntity {}", loanEntity);
		return loanMapper.mapToDomainEntity(loanRepository.insert(loanEntity));
	}

	@Override
	public Loan updateLoan(Loan loan) {
		LoanMongoEntity loanEntity = loanMapper.mapToMongoEntity(loan);
		logger.debug("loanEntity {}", loanEntity);
		return loanMapper.mapToDomainEntity(loanRepository.save(loanEntity));
	}

	@Override
	public LoanDetail saveLoanDetail(LoanDetail loanDetail) {
		LoanDetailMongoEntity loanDetailEntity = loanDetailMapper.mapToMongoEntity(loanDetail);
		logger.debug("loanDetailEntity {}", loanDetailEntity);
		return loanDetailMapper.mapToDomainEntity(loanDetailRepository.save(loanDetailEntity));
	}

}
