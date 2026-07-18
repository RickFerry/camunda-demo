package com.exemplo.camundademo.delegate;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NotificarRejeicaoDelegate implements JavaDelegate {

    public static final String MOTIVO_REJEICAO = "motivoRejeicao";

    @Override
    public void execute(DelegateExecution execution) {
        String nome = (String) execution.getVariable("nome");
        String motivoExistente = (String) execution.getVariable(MOTIVO_REJEICAO);

        if (motivoExistente != null && !motivoExistente.isBlank()) {
            log.warn("Cliente {} rejeitado: {}", nome, motivoExistente);
            return;
        }

        Object scoreObj = execution.getVariable("scoreCalculado");
        Double score = scoreObj != null ? ((Number) scoreObj).doubleValue() : null;
        boolean aprovado = execution.getVariable("aprovado") != null
                && (boolean) execution.getVariable("aprovado");

        StringBuilder motivo = new StringBuilder();
        if (score != null && score < 600) {
            motivo.append("Score insuficiente (").append(score.intValue()).append("). ");
        }
        if (!aprovado) {
            String motivoAnalise = (String) execution.getVariable(MOTIVO_REJEICAO);
            if (motivoAnalise != null && !motivoAnalise.isBlank()) {
                motivo.append(motivoAnalise);
            } else {
                motivo.append("Reprovado na análise manual.");
            }
        }

        String motivoFinal = motivo.toString().trim();
        log.warn("Cliente {} rejeitado: {}", nome, motivoFinal);
        execution.setVariable(MOTIVO_REJEICAO, motivoFinal);
    }
}
