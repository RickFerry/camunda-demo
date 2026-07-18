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
        String motivoExistente = (String) execution.getVariable("motivoRejeicao");

        if (motivoExistente != null && !motivoExistente.isBlank()) {
            log.warn("Cliente {} rejeitado: {}", nome, motivoExistente);
            return;
        }

        Object scoreObj = execution.getVariable("score");
        Integer score = scoreObj != null ? (Integer) scoreObj : null;
        boolean aprovado = execution.getVariable("aprovado") != null
                && (boolean) execution.getVariable("aprovado");

        StringBuilder motivo = new StringBuilder();
        if (score != null && score <= 700) {
            motivo.append("Score insuficiente (").append(score).append("). ");
        }
        if (!aprovado) {
            motivo.append("Reprovado na análise manual.");
        }

        String motivoFinal = motivo.toString().trim();
        log.warn("Cliente {} rejeitado: {}", nome, motivoFinal);
        execution.setVariable("motivoRejeicao", motivoFinal);
    }
}
