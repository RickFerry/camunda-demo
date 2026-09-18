package com.exemplo.camundademo.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Component
public class GerarContaDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) {
        String numeroConta = String.format("%06d-%d",
                ThreadLocalRandom.current().nextInt(1000000),
                ThreadLocalRandom.current().nextInt(10));
        execution.setVariable("numeroConta", numeroConta);
    }
}
