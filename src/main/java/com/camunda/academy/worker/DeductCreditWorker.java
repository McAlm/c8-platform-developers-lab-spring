package com.camunda.academy.worker;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.camunda.academy.services.CustomerService;

import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.Variable;
import lombok.extern.java.Log;

@Component
@Log
public class DeductCreditWorker {

    @Autowired
    private CustomerService cs;

    @JobWorker(type="credit-deduction", fetchAllVariables = false)
    public Map<String, Object> creditDeduction(@Variable String customerId, @Variable Double orderTotal){
        log.info("Deducting Customer Credit for Customer " + customerId + " and OrderTotal " + orderTotal) ;
        Double openAmount = cs.deductCredit(customerId, orderTotal);
        return Map.of("openAmount", openAmount);
    }
}
