package com.exemplo.camundademo.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class GerarContaDelegate implements JavaDelegate {

    private static final Random RANDOM = new Random();

    @Override
    public void execute(DelegateExecution execution) {
        String numeroConta = String.format("%06d-%d",
                RANDOM.nextInt(1000000),
                RANDOM.nextInt(10));
        execution.setVariable("numeroConta", numeroConta);
    }
}
