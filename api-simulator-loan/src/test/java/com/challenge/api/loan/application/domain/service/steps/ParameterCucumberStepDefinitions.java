package com.challenge.api.loan.application.domain.service.steps;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

import com.challenge.api.CucumberSpringConfiguration;
import com.challenge.api.loan.application.domain.model.Parameter;
import com.challenge.api.loan.application.domain.service.ParameterService;

import io.cucumber.java.en.Given;

public class ParameterCucumberStepDefinitions extends CucumberSpringConfiguration {

	@Autowired
	private ParameterService parameterService;

	Parameter parameter;

	@Given("I save {string}")
	public void saveParameter(String jsonParameter) throws JSONException {
	JSONObject jsonParam = new JSONObject(jsonParameter);
	Parameter param = new Parameter();
	param.setCode(jsonParam.get("code").toString());
	param.setName(jsonParam.get("name").toString());
	param.setDescription(jsonParam.get("description").toString());
	param.setValue(jsonParam.get("value").toString());
	parameter = parameterService.findByCodeAndName(param);
	if (parameter == null)
		parameter = parameterService.save(param);
	Assertions.assertNotNull(parameter);
	}

}
