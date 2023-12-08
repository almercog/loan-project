package com.challenge.api.loan.adapter.out.persistence;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.challenge.api.loan.adapter.out.persistence.entity.ParameterId;
import com.challenge.api.loan.adapter.out.persistence.entity.ParameterMongoEntity;
import com.challenge.api.loan.adapter.out.persistence.mapper.ParameterMapper;
import com.challenge.api.loan.application.domain.model.Parameter;
import com.challenge.api.loan.application.port.out.ParameterPort;
import com.challenge.api.loan.common.PersistenceAdapter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@PersistenceAdapter
public class ParameterPersistenceAdapter implements ParameterPort {

	private static final Logger logger = LoggerFactory.getLogger(ParameterPersistenceAdapter.class);

	private final ParameterMapper parameterMapper;
	private final SpringDataParameterRepository parameterRepository;

	@Override
	public Parameter findById(Parameter parameter) {
		logger.debug("findById {}", parameter);
		ParameterId parameterId = new ParameterId();
		parameterId.setCode(parameter.getCode());
		parameterId.setName(parameter.getName());
		Optional<ParameterMongoEntity> param = parameterRepository.findById(parameterId);
		return param.isPresent() ? parameterMapper.mapToDomainEntity(param.get()) : null;
	}

	@Override
	public Parameter findByCodeAndName(Parameter parameter) {
		logger.debug("findByCodeAndName {}", parameter);
		ParameterMongoEntity param = parameterRepository.findByCodeAndName(parameter.getCode(), parameter.getName());
		return param != null ? parameterMapper.mapToDomainEntity(param) : null;
	}

	@Override
	public Parameter save(Parameter parameter) {
		ParameterMongoEntity parameterEntity = parameterMapper.mapToMongoEntity(parameter);
		return parameterMapper.mapToDomainEntity(parameterRepository.insert(parameterEntity));
	}

	@Override
	public List<Parameter> listParameter(Parameter parameter) {
		logger.debug("listParameter {}", parameter);
		return parameterRepository.findAllByCode(parameter.getCode()).stream().filter(Objects::nonNull)
				.map(parameterMapper::mapToDomainEntity).toList();
	}

}
