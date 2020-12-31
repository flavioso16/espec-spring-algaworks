package com.algaworks.algafood.core.validation;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author flaoliveira
 * @version : $<br/>
 * : $
 * @since 12/20/20 4:32 PM
 */
public class MultipleValidator implements ConstraintValidator<Multiple, Number> {

    private int number;

    @Override
    public void initialize(final Multiple constraintAnnotation) {
         this.number = constraintAnnotation.number();
    }

    @Override
    public boolean isValid(final Number number, final ConstraintValidatorContext constraintValidatorContext) {
        boolean isValid = true;

        if(number != null) {
            var valorDecimal = BigDecimal.valueOf(number.doubleValue());
            var multipleDecimal = BigDecimal.valueOf(this.number);
            var remainder = valorDecimal.remainder(multipleDecimal);
            isValid = BigDecimal.ZERO.compareTo(remainder) == 0;
        }

        return isValid;
    }
}
