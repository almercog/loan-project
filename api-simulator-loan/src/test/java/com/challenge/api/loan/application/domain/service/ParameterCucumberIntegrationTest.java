package com.challenge.api.loan.application.domain.service;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features/parameter")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.challenge.api")
public class ParameterCucumberIntegrationTest {

}
