package com.challenge.api.loan.application.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanResponse {
	private Integer code;
	private String type;
	private String message;
}
