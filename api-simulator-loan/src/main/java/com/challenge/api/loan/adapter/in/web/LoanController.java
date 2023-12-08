package com.challenge.api.loan.adapter.in.web;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.api.loan.application.domain.model.LoanRequest;
import com.challenge.api.loan.application.port.in.LoanUseCase;

@RestController
@RequestMapping("/api")
public class LoanController {

	private static final Logger logger = LoggerFactory.getLogger(LoanController.class);

	@Autowired
	private LoanUseCase loanUseCase;

    @PostMapping("/loan")
    public ResponseEntity<Object> simulate(@Valid @RequestBody LoanRequest loanRequest) {
    	logger.info("simulate {}", loanRequest);
    	return new ResponseEntity<>(loanUseCase.simulate(loanRequest), HttpStatus.OK);
    }
}
