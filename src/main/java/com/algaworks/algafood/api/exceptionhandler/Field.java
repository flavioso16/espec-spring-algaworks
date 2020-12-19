package com.algaworks.algafood.api.exceptionhandler;

import lombok.Builder;
import lombok.Getter;

/**
 * @author flaoliveira
 * @version : $<br/>
 * : $
 * @since 12/19/20 12:04 PM
 */
@Getter
@Builder
public class Field {
    private String name;
    private String message;
}