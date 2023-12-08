package com.challenge.api.loan.adapter.out.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.challenge.api.loan.adapter.out.persistence.entity.LoanMongoEntity;

public interface SpringDataLoanRepository extends MongoRepository<LoanMongoEntity, String> {

}
