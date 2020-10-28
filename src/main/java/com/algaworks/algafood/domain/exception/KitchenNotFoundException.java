package com.algaworks.algafood.domain.exception;

public class KitchenNotFoundException extends EntityNotFound {

	private static final long serialVersionUID = 1L;

	public KitchenNotFoundException(String message) {
		super(message);
	}
	
	public KitchenNotFoundException(Long kitchenId) {
		this(String.format("Não existe um cadastro de cozinha com código %d", kitchenId));
	}
	
}
