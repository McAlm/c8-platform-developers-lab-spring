package com.camunda.academy.worker;

import org.springframework.stereotype.Component;

import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.extern.java.Log;

@Component
@Log
public class ChargeCreditCardWorker {

    @JobWorker(type="credit-card-charging")
    public void chargeCreditCard(){
        log.info("Charging credit card");
    }
}
