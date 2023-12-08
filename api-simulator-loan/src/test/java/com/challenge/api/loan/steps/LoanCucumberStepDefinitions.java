package com.challenge.api.loan.steps;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.challenge.api.CucumberSpringConfiguration;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoanCucumberStepDefinitions extends CucumberSpringConfiguration {

	private HttpHeaders headers;
	private HttpEntity<String> httpRequest;
	private ResponseEntity<String> httpResponse;

	@Given("I set http headers to")
	public void setHttpHeaders(Map<String, String> parameters) {
		headers = new HttpHeaders();
		headers.setAll(parameters);
		Assertions.assertNotNull(headers);
	}

	@When("A loan {string}")
	public void setRequest(String request) throws JSONException {
		JSONObject jsonReq = new JSONObject(request);
		httpRequest = new HttpEntity<>(jsonReq.toString(), headers);
		Assertions.assertNotNull(httpRequest);
	}

	@When("I send POST request to {string}")
	public void sendPostRequest(String endpoint) {
		System.out.println("endpoint " + endpoint);
		httpResponse = testRestTemplate.postForEntity(endpoint, httpRequest, String.class);
		Assertions.assertNotNull(httpResponse);
		Assertions.assertNotNull(httpResponse.getBody());
		Assertions.assertEquals(HttpStatus.OK, httpResponse.getStatusCode());
	}

	@Then("I get {string}")
	public void getResponse(String response) throws JSONException {
		JSONObject jsonRes = new JSONObject(response);
		assertThat(httpResponse.getBody()).isEqualTo(jsonRes.toString());
	}

}
