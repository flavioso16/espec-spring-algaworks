package com.algaworks.algafood.domain.vo;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class StateIdVO {

	@NotNull
	private Long id;

}