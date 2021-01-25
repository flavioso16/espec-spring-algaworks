package com.algaworks.algafood.domain.vo;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class UserUpdateVO {

	@NotBlank
	private String name;

	@NotBlank
	private String email;

}
