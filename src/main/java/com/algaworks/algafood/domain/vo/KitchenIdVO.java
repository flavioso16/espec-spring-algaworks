package com.algaworks.algafood.domain.vo;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * @author flaoliveira
 * @version : $<br/>
 * : $
 * @since 1/17/21 7:50 PM
 */
@Data
public class KitchenIdVO {

    @NotNull
    private Long id;
    
}
