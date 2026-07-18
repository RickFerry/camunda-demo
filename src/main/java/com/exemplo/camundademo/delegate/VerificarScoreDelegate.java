package com.exemplo.camundademo.delegate;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class VerificarScoreDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) {
        Object scoreObj = execution.getVariable("scoreCalculado");
        Integer score = scoreObj != null ? ((Number) scoreObj).intValue() : null;
        execution.setVariable("score", score);

        log.info("Score calculado pela DMN: {}", score);
    }
}
