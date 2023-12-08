package com.challenge.api.loan.application.domain.model;

public enum Question {
	S("Si"), N("No");

	private final String displayValue;

	Question(String displayValue) {
		this.displayValue = displayValue;
	}

	@Override
	public String toString() {
		return displayValue;
	}
}