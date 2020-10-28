package com.algaworks.algafood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

    MESSAGE_INCOMPREHENSIBLE("/mensagem-imcompreensivel", "Mensagem incompreensível"),
    ENTITY_NOT_FOUND("/entidade-nao-encontrada", "Entidade não encontrada"),
    ENTITY_IN_USE("/entidade-em-uso", "Entidade em uso"),
    BUSINESS_ERROR("/erro-negocio", "Violação de regra de negócio");

    private String title;
    private String uri;

    ProblemType(String path, String title) {
        this.uri = "https://algafood.com.br" + path;
        this.title = title;
    }
}
