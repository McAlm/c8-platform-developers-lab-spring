package com.camunda.academy.worker;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.camunda.academy.services.CardExpiredException;
import com.camunda.academy.services.CreditCardService;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.Variable;
import io.camunda.zeebe.spring.client.exception.ZeebeBpmnError;
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
                    "openAmount" }//
                    , autoComplete = false)
    public void chargeAmount(//
            @Variable String cardNumber, //
            @Variable String cvc, //
            @Variable String expiryDate, //
            @Variable Double openAmount,
            JobClient jobClient,
            ActivatedJob job) {
        log.info("Charging credit card");

        try {
            ccs.chargeAmount(cardNumber, cvc, expiryDate, openAmount);
            jobClient.newCompleteCommand(job);
        } catch (CardExpiredException e) {
            String errorMessage ="Payment failed, Credit Card has expired!";
            throw new ZeebeBpmnError("creditCardChargeError"//
                                    , errorMessage//
                                    , Map.of("errorMessage",errorMessage));
        }
    }
}
