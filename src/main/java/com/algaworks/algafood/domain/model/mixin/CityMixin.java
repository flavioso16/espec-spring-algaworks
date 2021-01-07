package com.algaworks.algafood.domain.model.mixin;

import com.algaworks.algafood.domain.model.State;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class CityMixin {

	@JsonIgnoreProperties(value = "name", allowGetters = true)
	private State state;

}