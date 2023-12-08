package com.challenge.api.loan.application.domain.model;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payload {

	@NotNull(message = "loan shouldn't be null")
	private Loan loan;

}
