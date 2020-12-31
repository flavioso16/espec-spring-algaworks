package com.algaworks.algafood.core.validation;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;

import org.springframework.beans.BeanUtils;

/**
 * @author flaoliveira
 * @version : $<br/>
 * : $
 * @since 12/20/20 6:01 PM
 */
public class ZeroValueIncludeDescriptionValidator implements ConstraintValidator<ZeroValueIncludeDescription, Object> {

    private String fieldValue;
    private String fieldDescription;
    private String requiredDescription;

    @Override
    public void initialize(final ZeroValueIncludeDescription constraint) {
        this.fieldValue = constraint.fieldValue();
        this.fieldDescription = constraint.fieldDescription();
        this.requiredDescription = constraint.requiredDescription();
    }

    @Override
    public boolean isValid(final Object validateObject, final ConstraintValidatorContext constraint) {
        boolean isValid = true;

        try {
            BigDecimal value = (BigDecimal) BeanUtils.getPropertyDescriptor(validateObject.getClass(), fieldValue)
                    .getReadMethod().invoke(validateObject);

            String description = (String) BeanUtils.getPropertyDescriptor(validateObject.getClass(), fieldDescription)
                    .getReadMethod().invoke(validateObject);

            if(value != null && BigDecimal.ZERO.compareTo(value) == 0 && description != null) {
                isValid = description.toLowerCase().contains(this.requiredDescription.toLowerCase());
            }
        } catch (Exception e) {
            throw new ValidationException(e);
        }

        return isValid;
    }
}
