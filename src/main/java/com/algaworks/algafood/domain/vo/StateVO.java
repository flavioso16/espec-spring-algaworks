package com.algaworks.algafood.domain.vo;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class StateVO {

	@NotBlank
	private String name;
	
}