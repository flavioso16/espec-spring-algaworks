package com.algaworks.algafood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

    INVALID_DATA("/dados-invalidos", "Dados inválidos"),
    MESSAGE_INCOMPREHENSIBLE("/mensagem-imcompreensivel", "Mensagem incompreensível"),
    RESOURCE_NOT_FOUND("/resource-not-found", "Recurso não encontrada"),
    ENTITY_IN_USE("/entidade-em-uso", "Entidade em uso"),
    BUSINESS_ERROR("/erro-negocio", "Violação de regra de negócio"),
    PARAMETRO_INVALIDO("/parametro-invalido", "Parâmetro inválido"),
    SYSTEM_ERROR("/erro-de-sistema", "Erro de sistema");

    private String title;
    private String uri;

    ProblemType(String path, String title) {
        this.uri = "https://algafood.com.br" + path;
        this.title = title;
    }
}
