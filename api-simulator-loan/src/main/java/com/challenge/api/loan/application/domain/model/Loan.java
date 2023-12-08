package com.challenge.api.loan.application.domain.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.json.JSONObject;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import com.challenge.api.loan.common.Constants;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Loan {

	private String id;
	@NumberFormat(style = Style.CURRENCY)
	@NotNull(message = "amount shouldn't be null")
	private String amount;
	@NumberFormat(style = Style.PERCENT)
	@Min(value = 1, message = "The number of rate must be greater than 0")
	@Max(value = 100, message = "The number of rate must be greater than 100")
	@NotNull(message = "rate shouldn't be null")
	private String rate;
	@NotNull(message = "termType shouldn't be null")
	private String termType;
	@NumberFormat(style = Style.NUMBER)
	@NotNull(message = "repaymentTerm shouldn't be null")
	@Positive(message = "The repaymentTerm number must be greater than 0")
	private Integer repaymentTerm;
	private String withGracePeriod = Constants.IND_NO;
	@NumberFormat(style = Style.NUMBER)
	@Positive(message = "The gracePeriod number must be greater than 0")
	private Integer gracePeriod = Constants.DEF_GRACE_PERIOD;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@FutureOrPresent(message = "The disbursementDate date must be today or in the future.")
	@NotNull(message = "disbursementDate shouldn't be null")
	private String disbursementDate;
	private LocalDate loanStartDate;
	private LocalDate loanEndDate;
	@Email(message = "invalid email address")
	@NotNull(message = "email shouldn't be null")
	private String email;
	private String feeSum = Constants.INITIAL_SUM;
	private String interestSum = Constants.INITIAL_SUM;

	public BigDecimal amountToDecimal() {
		return new BigDecimal(this.amount);
	}

	/**
	 * Method to calculate equivalent interest (((1 +
	 * rate/repaymentTerm)^repaymentTerm)^effectiveRate)-1
	 *
	 * @return Equivalent interest
	 */
	public BigDecimal rateToDecimal(String effectiveRate) {
		BigDecimal bRate = new BigDecimal(rate).divide(Constants.PERCENTAGE_100).divide(new BigDecimal(repaymentTerm),
				Constants.MATH_CONTEXT_HALF_EVEN_8);
		bRate = bRate.add(Constants.DECIMAL_UNO).pow(repaymentTerm);
		bRate = BigDecimal.valueOf(Math.pow(Double.valueOf(bRate.toString()), Double.valueOf(effectiveRate)));
		return bRate.subtract(Constants.DECIMAL_UNO, Constants.MATH_CONTEXT_HALF_EVEN_8);
	}

	/**
	 * Method to calculate due amount x (((1 + rate)^repaymentTerm) - 1) / (((1 +
	 * rate)^repaymentTerm) x rate)
	 *
	 * @param amount        value amount
	 * @param rate          value equivalent interest
	 * @param repaymentTerm value repayments term
	 * @return
	 */
	public BigDecimal dueToDecimal(BigDecimal amount, BigDecimal rate, Integer repaymentTerm) {
		BigDecimal bRate = rate.add(Constants.DECIMAL_UNO).pow(repaymentTerm, Constants.MATH_CONTEXT_HALF_EVEN_8);
		BigDecimal calcDue = bRate.subtract(Constants.DECIMAL_UNO).divide(rate, Constants.MATH_CONTEXT_HALF_EVEN_8);
		calcDue = calcDue.divide(bRate, Constants.MATH_CONTEXT_HALF_EVEN_8);
		return amount.divide(calcDue, Constants.SCALE_4, RoundingMode.CEILING);
	}

	public void startDate() {
		this.loanStartDate = LocalDate.parse(this.disbursementDate);
	}

	public void endDate() {
		if (this.termType.equals(Constants.TERM_TYPE_MONTH)) {
			int add = this.repaymentTerm + this.gracePeriod;
			this.loanEndDate = this.loanStartDate.plusMonths(add);
		} else {
			this.loanEndDate = this.loanStartDate.plusYears(this.repaymentTerm);
		}
	}

	public JSONObject getJsonObject(BigDecimal bRate, JSONObject jsonPaymentFrecuency) {
		JSONObject jsonLoan = new JSONObject(this);
		jsonLoan.put("withGracePeriod", Question.valueOf(withGracePeriod).toString());
		jsonLoan.put("termType", jsonPaymentFrecuency.get(Constants.PARAMETER_NAME_VAL_DSC));
		jsonLoan.put("amount", new DecimalFormat(Constants.DECIMAL_FORMAT).format(Double.valueOf(amount)));
		jsonLoan.put("feeSum", new DecimalFormat(Constants.DECIMAL_FORMAT).format(Double.valueOf(feeSum)));
		jsonLoan.put("interestSum", new DecimalFormat(Constants.DECIMAL_FORMAT).format(Double.valueOf(interestSum)));
		jsonLoan.put("rate", new DecimalFormat(Constants.DECIMAL_FORMAT)
				.format(Double.valueOf(bRate.multiply(Constants.PERCENTAGE_100).doubleValue())));
		return jsonLoan;
	}
}
