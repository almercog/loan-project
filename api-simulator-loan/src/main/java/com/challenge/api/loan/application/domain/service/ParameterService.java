package com.challenge.api.loan.application.domain.service;

import java.util.List;
import java.util.stream.StreamSupport;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.challenge.api.loan.application.domain.model.Parameter;
import com.challenge.api.loan.application.port.in.ParameterUseCase;
import com.challenge.api.loan.application.port.out.ParameterPort;
import com.challenge.api.loan.common.Constants;
import com.challenge.api.loan.common.UseCase;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@UseCase
@Transactional
public class ParameterService implements ParameterUseCase {

	private static final Logger logger = LoggerFactory.getLogger(ParameterService.class);

	private final ParameterPort parameterPort;

	@Override
	public Parameter findByCodeAndName(Parameter parameter) {
		logger.debug("findByCodeAndName {}", parameter);
		return parameterPort.findByCodeAndName(parameter);
	}

	@Override
	public Parameter save(Parameter parameter) {
		logger.debug("save {}", parameter);
		return parameterPort.save(parameter);
	}

	@Override
	public Parameter findParameterByName(List<Parameter> lstParameter, String name) {
		return lstParameter.stream().filter(param -> name.equals(param.getName())).findFirst().orElse(null);
	}

	@Override
	public List<Parameter> findAllByCode() {
		return this.findAllByCode(new Parameter(Constants.PARAMETER_CODE, null, null, null));
	}

	@Override
	public List<Parameter> findAllByCode(Parameter parameter) {
		return parameterPort.listParameter(parameter);
	}

	@Override
	public JSONObject findByPaymentFrecuency(Parameter parameter, String paymentFrecuency) {
		logger.debug("findByCodeAndNameAndFrecuency {}", paymentFrecuency);
		JSONArray jsonArray = new JSONArray(parameter.getValue());
		return findByCode(jsonArray, paymentFrecuency);
	}

	private static JSONObject findByCode(JSONArray jsonArray, String code) {
		return StreamSupport.stream(jsonArray.spliterator(), false).map(JSONObject.class::cast)
				.filter(obj -> code.equals(obj.getString("code"))).findFirst().orElse(null);
	}
}
