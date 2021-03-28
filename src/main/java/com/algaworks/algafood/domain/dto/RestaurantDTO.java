package com.algaworks.algafood.domain.dto;

import java.math.BigDecimal;

import com.algaworks.algafood.domain.model.view.RestaurantView;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;

/**
 * @author flaoliveira
 * @version : $<br/>
 * : $
 * @since 1/12/21 10:30 PM
 */
@Data
public class RestaurantDTO {

    @JsonView({ RestaurantView.Resume.class, RestaurantView.OnlyName.class })
    private Long id;

    @JsonView({ RestaurantView.Resume.class, RestaurantView.OnlyName.class })
    private String name;

    @JsonView(RestaurantView.Resume.class)
    private BigDecimal shippingFee;

    @JsonView(RestaurantView.Resume.class)
    private KitchenDTO kitchen;

    private Boolean active;

    private AddressDTO address;

    private Boolean isOpen;
}
