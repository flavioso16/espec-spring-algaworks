package com.algaworks.algafood.domain.dto;

import com.algaworks.algafood.domain.model.view.RestaurantView;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;

/**
 * @author flaoliveira
 * @version : $<br/>
 * : $
 * @since 1/12/21 10:34 PM
 */
@Data
public class KitchenDTO {

    @JsonView(RestaurantView.Resume.class)
    private Long id;
    
    @JsonView(RestaurantView.Resume.class)
    private String name;

}
