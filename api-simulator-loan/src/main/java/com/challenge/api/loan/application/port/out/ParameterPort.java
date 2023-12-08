package com.challenge.api.loan.application.port.out;

import java.util.List;

import com.challenge.api.loan.application.domain.model.Parameter;

public interface ParameterPort {

	Parameter save(Parameter parameter);

	List<Parameter> listParameter(Parameter parameter);

	Parameter findByCodeAndName(Parameter parameter);

	Parameter findById(Parameter parameter);
}
