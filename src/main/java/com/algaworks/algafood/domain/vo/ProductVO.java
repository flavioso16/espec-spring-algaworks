package com.algaworks.algafood.domain.vo;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import lombok.Data;

@Data
public class ProductVO {

	@NotBlank
	private String name;
	
	@NotBlank
	private String description;
	
	@NotNull
	@PositiveOrZero
	private BigDecimal price;

	@NotNull
	private Boolean active;

}