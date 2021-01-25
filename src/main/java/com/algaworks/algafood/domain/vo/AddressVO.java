package com.algaworks.algafood.domain.vo;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class AddressVO {

	@NotBlank
	private String postalCode;

	@NotBlank
	private String address;

	@NotBlank
	private String number;

	private String complement;

	@NotBlank
	private String district;

	@Valid
	@NotNull
	private IdVO city;
	
}
