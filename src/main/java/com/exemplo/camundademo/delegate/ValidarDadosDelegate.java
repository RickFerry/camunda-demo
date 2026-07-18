package com.exemplo.camundademo.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
public class ValidarDadosDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) {
        String cpf = (String) execution.getVariable("cpf");
        String nome = (String) execution.getVariable("nome");
        Object idade = execution.getVariable("idade");
        Object renda = execution.getVariable("renda");

        if (nome == null || nome.isBlank()) {
            throw new RuntimeException("Nome obrigatório");
        }
        if (cpf == null || cpf.replaceAll("\\D", "").length() != 11) {
            throw new RuntimeException("CPF inválido");
        }
        if (idade == null) {
            throw new RuntimeException("Idade é obrigatória");
        }
        if (renda == null) {
            throw new RuntimeException("Renda comprovada é obrigatória");
        }

        execution.setVariable("dadosValidos", true);
    }
}
