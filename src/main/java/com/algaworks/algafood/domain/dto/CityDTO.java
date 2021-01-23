package com.algaworks.algafood.domain.dto;

import com.algaworks.algafood.domain.model.State;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityDTO {

	private Long id;
	private String name;
	private State state;

}