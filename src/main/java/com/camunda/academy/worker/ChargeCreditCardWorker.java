package com.camunda.academy.worker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.camunda.academy.services.CreditCardService;

import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.Variable;
import lombok.extern.java.Log;

@Component
@Log
public class ChargeCreditCardWorker {

    @Autowired
    private CreditCardService ccs;

    @JobWorker(type = "credit-card-charging", //
            fetchVariables = { //
                    "cardNumber", //
                    "cvc", //
                    "expiryDate", //
                    "openAmount" })
    public void chargeAmount(//
            @Variable String cardNumber, //
            @Variable String cvc, //
            @Variable String expiryDate, //
            @Variable Double openAmount) {
        log.info("Charging credit card");
        ccs.chargeAmount(cardNumber, cvc, expiryDate, openAmount);
    }
}
