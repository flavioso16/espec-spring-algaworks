package com.algaworks.algafood.core.validation;

import org.springframework.validation.BindingResult;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author flaoliveira
 * @version : $<br/>
 * : $
 * @since 12/29/20 9:59 PM
 */
@AllArgsConstructor
@Getter
public class ValidationException extends RuntimeException {

    private BindingResult bindingResult;

}
