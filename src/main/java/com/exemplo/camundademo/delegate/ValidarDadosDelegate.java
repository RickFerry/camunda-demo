package com.exemplo.camundademo.delegate;

import org.camunda.bpm.engine.delegate.BpmnError;
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
            throw new BpmnError("VALIDACAO_DADOS", "Nome obrigatório");
        }
        if (!cpfValido(cpf)) {
            throw new BpmnError("VALIDACAO_DADOS", "CPF inválido");
        }
        if (idade == null) {
            throw new BpmnError("VALIDACAO_DADOS", "Idade é obrigatória");
        }
        if (renda == null) {
            throw new BpmnError("VALIDACAO_DADOS", "Renda comprovada é obrigatória");
        }
    }

    static boolean cpfValido(String cpf) {
        if (cpf == null) {
            return false;
        }
        String digitos = cpf.replaceAll("\\D", "");
        if (digitos.length() != 11 || digitos.chars().distinct().count() == 1) {
            return false;
        }
        for (int posicao = 9; posicao < 11; posicao++) {
            int soma = 0;
            for (int i = 0; i < posicao; i++) {
                soma += (digitos.charAt(i) - '0') * (posicao + 1 - i);
            }
            int digito = 11 - (soma % 11);
            if (digito >= 10) {
                digito = 0;
            }
            if (digito != digitos.charAt(posicao) - '0') {
                return false;
            }
        }
        return true;
    }
}
