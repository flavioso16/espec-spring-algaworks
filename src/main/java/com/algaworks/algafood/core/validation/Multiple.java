package com.algaworks.algafood.core.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * @author flaoliveira
 * @version : $<br/>
 * : $
 * @since 12/20/20 4:29 PM
 */
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { MultipleValidator.class })
public @interface Multiple {

    String message() default "Multiplo inválido";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int number();

}
