package com.algaworks.algafood.domain.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class OrderItemDTO {
	private Long id;
	private BigDecimal priceUnit;
	private BigDecimal totalPrice;
	private Integer amount;
	private String description;
	private Long productId;
	private String productName;
}
