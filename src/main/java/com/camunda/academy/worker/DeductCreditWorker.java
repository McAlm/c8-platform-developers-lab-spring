package com.camunda.academy.worker;

import org.springframework.stereotype.Component;

import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.extern.java.Log;

@Component
@Log
public class DeductCreditWorker {

    @JobWorker(type="credit-deduction")
    public void creditDeduction(){
        log.info("Deducting Customer Credit");
    }
}
