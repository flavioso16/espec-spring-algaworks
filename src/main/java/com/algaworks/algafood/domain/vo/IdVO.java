package com.algaworks.algafood.domain.vo;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * @author flaoliveira
 * @version : $<br/>
 * : $
 * @since 1/24/21 5:00 PM
 */
@Data
public class IdVO<T> {

    @NotNull
    private T id;

}
