package com.algaworks.algafood.domain.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

/**
 * @author flaoliveira
 * @version : $<br/>
 * : $
 * @since 1/12/21 10:30 PM
 */
@Getter
@Setter
public class RestaurantDTO {

    private Long id;
    private String name;
    private BigDecimal shippingFee;
    private KitchenDTO kitchen;
    private Boolean active;

}
