package com.challenge.api.loan.adapter.out.persistence.mapper;

import org.springframework.stereotype.Component;

import com.challenge.api.loan.adapter.out.persistence.entity.ParameterId;
import com.challenge.api.loan.adapter.out.persistence.entity.ParameterMongoEntity;
import com.challenge.api.loan.application.domain.model.Parameter;

@Component
public class ParameterMapper {

	public Parameter mapToDomainEntity(ParameterMongoEntity parameterEntity) {
		return new Parameter(parameterEntity.getParameterId().getCode(), parameterEntity.getParameterId().getName(),
				parameterEntity.getDescription(), parameterEntity.getValue());
	}

	public ParameterMongoEntity mapToMongoEntity(Parameter parameter) {
		ParameterMongoEntity parameterEntity = new ParameterMongoEntity();
		ParameterId parameterId = new ParameterId();
		parameterId.setCode(parameter.getCode());
		parameterId.setName(parameter.getName());
		parameterEntity.setParameterId(parameterId);
		parameterEntity.setDescription(parameter.getDescription());
		parameterEntity.setValue(parameter.getValue());
		return parameterEntity;
	}
}
