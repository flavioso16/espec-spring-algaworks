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
 * @since 12/20/20 5:57 PM
 */
@Target({ ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { ZeroValueIncludeDescriptionValidator.class })
public @interface ZeroValueIncludeDescription {

    String message() default "Descrição obrigatória inválida";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String fieldValue();

    String fieldDescription();

    String requiredDescription();



}
