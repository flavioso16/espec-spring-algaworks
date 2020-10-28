package com.algaworks.algafood.domain.exception;

public abstract class EntityNotFound extends BusinessException {

	private static final long serialVersionUID = 1L;

	public EntityNotFound(String message) {
		super(message);
	}
	
}
