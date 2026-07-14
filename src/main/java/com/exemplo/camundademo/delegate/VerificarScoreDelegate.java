package com.exemplo.camundademo.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import static java.lang.Integer.parseInt;
import static java.lang.Math.min;

@Component
public class VerificarScoreDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) {
        String cpf = (String) execution.getVariable("cpf");
        int ultimosDigitos = parseInt(cpf.replaceAll("\\D", "").substring(9));
        int score = min(ultimosDigitos, 999);
        execution.setVariable("score", score);
    }
}
