package com.algaworks.algafood.domain.vo;

import java.math.BigDecimal;

import javax.validation.Valid;

import com.algaworks.algafood.core.validation.TaxaFrete;
import com.algaworks.algafood.core.validation.ZeroValueIncludeDescription;

import lombok.Data;

/**
 * @author flaoliveira
 * @version : $<br/>
 * : $
 * @since 1/17/21 7:49 PM
 */
@ZeroValueIncludeDescription(fieldValue = "shippingFee", fieldDescription = "name", requiredDescription = "Frete Gratis")
@Data
public class RestaurantPartialVO {

    private String name;

    @TaxaFrete
    private BigDecimal shippingFee;

    @Valid
    private IdVO kitchen;

    @Valid
    private IdVO address;

}
