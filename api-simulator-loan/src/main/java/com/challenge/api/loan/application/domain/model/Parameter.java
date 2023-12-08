package com.challenge.api.loan.application.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Parameter {

	private String code;
	private String name;
	private String description;
	private String value;

}
