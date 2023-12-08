package com.challenge.api.loan.adapter.out.persistence.entity;

import java.io.Serializable;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "loan")
public class LoanMongoEntity implements Serializable {

	private static final long serialVersionUID = -2519971415024719217L;

	@Id
	private String id;
	private String amount;
	private String rate;
	private String termType;
	private Integer repaymentTerm;
	private String withGracePeriod;
	private Integer gracePeriod;
	private String disbursementDate;
	private String loanStartDate;
	private String loanEndDate;
	private String url;
	private String email;
	private String feeSum;
	private String interestSum;

	public LoanMongoEntity() {
		this.id = UUID.randomUUID().toString();
	}
}
