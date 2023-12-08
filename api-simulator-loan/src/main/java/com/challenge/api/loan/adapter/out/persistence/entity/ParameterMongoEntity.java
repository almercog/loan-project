package com.challenge.api.loan.adapter.out.persistence.entity;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "parameter")
public class ParameterMongoEntity implements Serializable {

	private static final long serialVersionUID = 8245809135672884758L;

	@Id
	private ParameterId parameterId;
	private String description;
	private String value;

}
