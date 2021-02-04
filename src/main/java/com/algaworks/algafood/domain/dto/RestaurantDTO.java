package com.algaworks.algafood.domain.dto;

import java.math.BigDecimal;

import lombok.Data;

/**
 * @author flaoliveira
 * @version : $<br/>
 * : $
 * @since 1/12/21 10:30 PM
 */
@Data
public class RestaurantDTO {

    private Long id;
    private String name;
    private BigDecimal shippingFee;
    private KitchenDTO kitchen;
    private Boolean active;
    private AddressDTO address;
    private Boolean isOpen;
}
