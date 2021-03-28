package com.algaworks.algafood.domain.model;

import java.util.Arrays;
import java.util.List;

public enum OrderStatus {
	CREATED("Created"),
	CONFIRMED("Confirmed", CREATED),
	DELIVERED("Delivered", CONFIRMED),
	CANCELED("Canceled", CREATED);

	private String description;
	private List<OrderStatus> previousStatusAllowed;

	OrderStatus(String description, OrderStatus... previousStatusAllowed) {
		this.description = description;
		this.previousStatusAllowed = Arrays.asList(previousStatusAllowed);
	}

	public String getDescription() {
		return this.description;
	}

	public boolean canUpdateTo(OrderStatus newStatus) {
		return newStatus.previousStatusAllowed.contains(this);
	}

	public boolean canNotUpdateTo(OrderStatus newStatus) {
		return !canUpdateTo(newStatus);
	}
}