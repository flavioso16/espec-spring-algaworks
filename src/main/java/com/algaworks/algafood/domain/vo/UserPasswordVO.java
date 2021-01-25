package com.algaworks.algafood.domain.vo;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class UserPasswordVO {

	@NotBlank
	private String password;

	@NotBlank
	private String newPassword;

}
