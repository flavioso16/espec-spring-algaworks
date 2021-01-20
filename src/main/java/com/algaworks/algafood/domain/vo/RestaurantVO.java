package com.algaworks.algafood.domain.vo;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.algaworks.algafood.core.validation.TaxaFrete;

import lombok.Data;

/**
 * @author flaoliveira
 * @version : $<br/>
 * : $
 * @since 1/17/21 7:49 PM
 */
@Data
public class RestaurantVO {

    //	@NotEmpty
    @NotBlank
    private String name;

    //	@PositiveOrZero //(message = "{restaurant.shippingFee.positiveOrZero}")
    //	@Multiple(number = 5)
    @DecimalMax(value="20")
    @NotNull
    @TaxaFrete
    private BigDecimal shippingFee;

    @Valid
    @NotNull
    private KitchenIdVO kitchen;

}
