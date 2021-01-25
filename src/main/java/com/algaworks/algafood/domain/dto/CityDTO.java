package com.algaworks.algafood.domain.dto;

import com.algaworks.algafood.domain.model.State;

import lombok.Data;

@Data
public class CityDTO {

	private Long id;
	private String name;
	private State state;

}