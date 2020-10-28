package com.algaworks.algafood.domain.exception;

public class StateNotFoundException extends EntityNotFound {

	private static final long serialVersionUID = 1L;

	public StateNotFoundException(String message) {
		super(message);
	}
	
	public StateNotFoundException(Long stateId) {
		this(String.format("Não existe um cadastro de estado com código %d", stateId));
	}
	
}
