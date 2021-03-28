package com.algaworks.algafood.domain.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.algaworks.algafood.domain.model.OrderStatus;

import lombok.Data;

@Data
public class OrderResumeDTO {
	private String code;
	private BigDecimal subtotal;
	private BigDecimal shippingFee;
	private BigDecimal totalValue;
	private OrderStatus status = OrderStatus.CREATED;
	private OffsetDateTime creationDate;
	private RestaurantResumeDTO restaurant;
	private UserDTO client;
}
