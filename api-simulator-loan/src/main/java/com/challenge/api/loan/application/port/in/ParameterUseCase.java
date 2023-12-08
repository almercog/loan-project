package com.challenge.api.loan.application.port.in;

import java.util.List;

import org.json.JSONObject;

import com.challenge.api.loan.application.domain.model.Parameter;

public interface ParameterUseCase {

	Parameter findByCodeAndName(Parameter parameter);

	Parameter findParameterByName(List<Parameter> lstParameter, String name);

	Parameter save(Parameter parameter);

	List<Parameter> findAllByCode();

	List<Parameter> findAllByCode(Parameter parameter);

	JSONObject findByPaymentFrecuency(Parameter parameter, String paymentFrecuency);

}
