package com.algaworks.algafood.domain.vo;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class KitchenVO {

	@NotBlank
	private String name;

}
