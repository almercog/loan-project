package com.challenge.api.loan.adapter.out.persistence.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class ParameterId implements Serializable {

	private static final long serialVersionUID = -7883101419845936982L;
	private String code;
	private String name;
}
