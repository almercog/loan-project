package com.challenge.api.loan.application.port.in;

import java.io.IOException;

import org.json.JSONObject;

import jakarta.mail.MessagingException;

public interface JavaMailUseCase {

	void sendMessage(JSONObject jsonLoan, byte[] loanPdf) throws MessagingException, IOException;
}
