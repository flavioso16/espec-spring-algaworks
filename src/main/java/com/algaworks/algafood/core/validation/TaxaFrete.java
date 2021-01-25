package com.algaworks.algafood.core.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.OverridesAttribute;
import javax.validation.Payload;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.PositiveOrZero;

/**
 * @author flaoliveira
 * @version : $<br/>
 * : $
 * @since 12/20/20 3:45 PM
 */
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(
        validatedBy = {}
)
@PositiveOrZero
@DecimalMax(value="20")
public @interface TaxaFrete {

    @OverridesAttribute(constraint = PositiveOrZero.class, name = "message")
    String message() default "{invalid.shippingFee}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
