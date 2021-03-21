package com.algaworks.algafood.domain.vo;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class OrderItemVO {

	@Valid
	@NotNull
	private IdVO product;

	@Min(1)
	@NotNull
	private Long amount;

	private String description;

}
