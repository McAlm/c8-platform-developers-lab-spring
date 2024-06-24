package com.camunda.academy.services;

public class CardExpiredException extends Exception{

    public CardExpiredException(String msg) {
        super(msg);
    }

}
