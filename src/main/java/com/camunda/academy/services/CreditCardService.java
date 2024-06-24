package com.camunda.academy.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class CreditCardService {

	public void chargeAmount(String cardNumber, String cvc, String expiryDate, Double amount)
			throws CardExpiredException {
		System.out.printf("charging card %s that expires on %s and has cvc %s with amount of %f %s", cardNumber,
				expiryDate, cvc, amount, System.lineSeparator());

		if (cardExpired(expiryDate)) {
			throw new CardExpiredException("Card " + cardNumber + " is expired");
		}

		System.out.println("payment completed");

	}

	private boolean cardExpired(String expiryDate) {

		SimpleDateFormat mmyy = new SimpleDateFormat("MM/yy");
		SimpleDateFormat mmyyyy = new SimpleDateFormat("MM/yyyy");

		Date date = null;
		try {
			date = mmyy.parse(expiryDate);

		} catch (ParseException e) {
			try {
				date = mmyyyy.parse(expiryDate);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return true;
			}
		}
		if (date != null) {
			Calendar expiryCal = Calendar.getInstance();
			expiryCal.setTime(date);
			Calendar today = Calendar.getInstance();
			if (today.after(expiryCal)) {
				return true;
			}
		}
		return false;
	}
}
