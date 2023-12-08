package com.challenge.api.loan.adapter.out.persistence.entity;

import java.io.Serializable;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "loanDetail")
public class LoanDetailMongoEntity implements Serializable {
	private static final long serialVersionUID = 58573617701251116L;

	@Id
	private String id;
	private String idLoan;
	private Integer period;
	private String installmentPaymentDueDate;
	private String due;
	private String interest;
	private String amortization;
	private String totalAmortized;
	private String outstandingCapital;

	public LoanDetailMongoEntity() {
		this.id = UUID.randomUUID().toString();
	}
}
