package com.algaworks.algafood.domain.exception;

public class RestaurantNotFoundException extends EntityNotFound {

	private static final long serialVersionUID = 1L;

	public RestaurantNotFoundException(String message) {
		super(message);
	}
	
	public RestaurantNotFoundException(Long restaurantId) {
		this(String.format("Não existe um cadastro de restaurante com código %d", restaurantId));
	}
	
}
