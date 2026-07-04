package com.exemplo.camundademo.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
public class GerarContaDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) {
        String numeroConta = String.format("%06d-%d",
                (int) (Math.random() * 1000000),
                (int) (Math.random() * 10));
        execution.setVariable("numeroConta", numeroConta);
    }
}
