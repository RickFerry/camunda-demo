package com.exemplo.camundademo.delegate;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EnviarCartaoDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) {
        String numeroConta = (String) execution.getVariable("numeroConta");
        log.info("Cartão enviado para conta {}", numeroConta);
    }
}
