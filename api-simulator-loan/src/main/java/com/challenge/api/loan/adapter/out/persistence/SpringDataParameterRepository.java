package com.challenge.api.loan.adapter.out.persistence;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.challenge.api.loan.adapter.out.persistence.entity.ParameterId;
import com.challenge.api.loan.adapter.out.persistence.entity.ParameterMongoEntity;

public interface SpringDataParameterRepository extends MongoRepository<ParameterMongoEntity, ParameterId> {

	@Query("{ '_id.code': ?0 }")
	List<ParameterMongoEntity> findAllByCode(String code);

	@Query("{ '_id.code': ?0, '_id.name': ?1 }")
	ParameterMongoEntity findByCodeAndName(String code, String name);
}
