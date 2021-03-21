package com.algaworks.algafood.domain.vo;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class OrderVO {

	@Valid
	@NotNull
	private IdVO restaurant;

	@Valid
	@NotNull
	private IdVO paymentType;

	@Valid
	@NotNull
	private AddressVO address;

	@Valid
	@Size(min = 1)
	@NotNull
	private List<OrderItemVO> items;
}
