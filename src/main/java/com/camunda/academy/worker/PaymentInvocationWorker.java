package com.camunda.academy.worker;

import java.time.Duration;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.Variable;
import lombok.extern.java.Log;

@Component
@Log
public class PaymentInvocationWorker {

    @Autowired
    private ZeebeClient zeebe;

    @JobWorker(type = "payment-invocation")
    public Map<String, Object> invokePayment(
            @Variable String customerId, //
            @Variable Double orderTotal, //
            @Variable String cardNumber, //
            @Variable String cvc, //
            @Variable String expiryDate) {

        String orderId = UUID.randomUUID().toString();
        log.info("request payment for new Order with Id " + orderId);
        zeebe.newPublishMessageCommand().messageName("paymentRequestMessage")//
                .correlationKey(orderId)//
                .timeToLive(Duration.ZERO)
                .variables(Map.of("orderId", orderId//
                        , "customerId", customerId//
                        , "orderTotal", orderTotal//
                        , "cardNumber", cardNumber//
                        , "cvc", cvc//
                        , "expiryDate", expiryDate))
                .send().join();

                return Map.of("orderId", orderId);
    }
}
