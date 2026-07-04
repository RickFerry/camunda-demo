package com.exemplo.camundademo.delegate;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NotificarRejeicaoDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) {
        String nome = (String) execution.getVariable("nome");
        int score = (int) execution.getVariable("score");
        boolean aprovado = execution.getVariable("aprovado") != null
                ? (boolean) execution.getVariable("aprovado") : false;

        StringBuilder motivo = new StringBuilder();
        if (score <= 700) {
            motivo.append("Score insuficiente (").append(score).append("). ");
        }
        if (!aprovado) {
            motivo.append("Reprovado na análise manual.");
        }

        log.warn("Cliente {} rejeitado: {}", nome, motivo);
        execution.setVariable("motivoRejeicao", motivo.toString());
    }
}
