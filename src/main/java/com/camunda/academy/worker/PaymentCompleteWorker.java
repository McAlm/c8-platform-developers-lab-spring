package com.camunda.academy.worker;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.Variable;
import lombok.extern.java.Log;

@Component
@Log
public class PaymentCompleteWorker {

    @Autowired
    private ZeebeClient zeebe;

    @JobWorker(type = "payment-completion")
    public void sendPaymentCompleted(@Variable String orderId) {
        log.info("Payment completed for Order " + orderId);
        zeebe.newPublishMessageCommand().messageName("paymentCompletedMessage")//
                .correlationKey(orderId)//
                .variables(Map.of("orderId", orderId))
                .send().join();

    }
}
