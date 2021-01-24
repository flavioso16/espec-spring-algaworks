package com.algaworks.algafood.domain.dto;

import lombok.Data;

@Data
public class AddressDTO {

	private Long id;
	private String postalCode;
	private String address;
	private String number;
	private String complement;
	private String district;
	private CityResumeDTO city;
	
}
