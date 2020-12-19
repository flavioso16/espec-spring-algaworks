package com.algaworks.algafood.domain.exception;

public class EntityNotFoundException extends BusinessException {

	private static final long serialVersionUID = 1L;

	public EntityNotFoundException(String message) {
		super(message);
	}

	public EntityNotFoundException(Class clazz, Long id) {
		super(String.format("Resource type %s of ID %d not found.", clazz.getName(), id));
	}
	
}
