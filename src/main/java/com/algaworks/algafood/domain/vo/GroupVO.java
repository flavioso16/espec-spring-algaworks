package com.algaworks.algafood.domain.vo;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * @author flaoliveira
 * @version : $<br/>
 * : $
 * @since 1/24/21 11:09 AM
 */
@Data
public class GroupVO {

    @NotBlank
    private String name;

}
