package com.algaworks.algafood.domain.vo;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class CityVO {

	@NotBlank
	private String name;

	@Valid
	private StateIdVO state;

}