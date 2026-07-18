package com.exemplo.camundademo.delegate;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProcessarClassificacaoIdadeDelegate implements JavaDelegate {

    public static final String MENOR_DE_IDADE = "menorDeIdade";

    @Override
    public void execute(DelegateExecution execution) {
        String classificacao = (String) execution.getVariable("classificacaoIdade");
        Long idade = (Long) execution.getVariable("idade");
        String nome = (String) execution.getVariable("nome");

        log.info("Cliente: {}", nome);
        log.info("Idade: {}", idade);
        log.info("Classificação DMN: {}", classificacao);

        switch (classificacao) {
            case "Criança", "Adolescente":
                execution.setVariable(MENOR_DE_IDADE, true);
                execution.setVariable("motivoRejeicao", "Menor de idade (" + classificacao + ")");
                break;
            case "Adulto":
                log.info("→ Maior de idade: prosseguindo com verificação de score");
                execution.setVariable(MENOR_DE_IDADE, false);
                execution.setVariable("tratamentoEspecial", false);
                break;
            case "Idoso":
                System.out.println("→ Idoso: score diferenciado (threshold 600). Prosseguindo com verificação de score.");
                execution.setVariable("menorDeIdade", false);
                execution.setVariable("tratamentoEspecial", true);
                break;
            default:
                log.info("→ Classificação desconhecida: {}", classificacao);
                execution.setVariable(MENOR_DE_IDADE, false);
                break;
        }
    }
}
