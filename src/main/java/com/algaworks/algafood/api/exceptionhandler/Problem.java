package com.algaworks.algafood.api.exceptionhandler;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Problem {
    private Integer status;
    private String type;
    private String title;
    private String detail;

    private String message;
    private LocalDateTime timestamp;
    private List<Field> fields;
    
}
