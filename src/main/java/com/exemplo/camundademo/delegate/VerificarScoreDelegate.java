package com.exemplo.camundademo.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import static java.lang.Integer.parseInt;
import static java.lang.Math.min;

@Component
public class VerificarScoreDelegate implements JavaDelegate {

    private static final int THRESHOLD_ADULTO = 700;
    private static final int THRESHOLD_IDOSO = 600;

    @Override
    public void execute(DelegateExecution execution) {
        String cpf = (String) execution.getVariable("cpf");
        Long idade = (Long) execution.getVariable("idade");
        String classificacao = (String) execution.getVariable("classificacaoIdade");

        if (cpf == null || cpf.replaceAll("\\D", "").length() != 11) {
            throw new RuntimeException("CPF inválido para consulta de score");
        }
        if (idade == null || idade < 18) {
            throw new RuntimeException("Score indisponível para menores");
        }

        int ultimosDigitos = parseInt(cpf.replaceAll("\\D", "").substring(9));
        int score = min(ultimosDigitos, 999);
        execution.setVariable("score", score);

        int threshold;
        if ("Idoso".equals(classificacao)) {
            threshold = THRESHOLD_IDOSO;
        } else {
            threshold = THRESHOLD_ADULTO;
        }
        boolean aprovado = score > threshold;
        execution.setVariable("scoreAprovado", aprovado);

        System.out.println("=== Verificação de Score ===");
        System.out.println("Classificação: " + classificacao);
        System.out.println("Score calculado: " + score);
        System.out.println("Threshold aplicado: " + threshold);
        System.out.println("Resultado: " + (aprovado ? "ACIMA do esperado" : "ABAIXO do esperado"));
    }
}
