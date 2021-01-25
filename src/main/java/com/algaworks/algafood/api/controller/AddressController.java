package com.algaworks.algafood.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.dto.AddressDTO;
import com.algaworks.algafood.domain.model.Address;
import com.algaworks.algafood.domain.service.AddressService;
import com.algaworks.algafood.domain.vo.AddressVO;

@RestController
@RequestMapping(value = "/adresses")
public class AddressController {

	@Autowired
	private AddressService addressService;

	@Autowired
	private ModelMapper mapper;

	@GetMapping
	public List<AddressDTO> list() {
		return addressService.list().stream()
				.map(address -> mapper.map(address, AddressDTO.class))
				.collect(Collectors.toList());
	}

	@GetMapping("/{addressId}")
	public AddressDTO find(@PathVariable Long addressId) {
		return mapper.map(addressService.findOrFail(addressId), AddressDTO.class);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public AddressDTO save(@RequestBody @Valid AddressVO addressVO) {
		Address address = addressService.save(mapper.map(addressVO, Address.class));
		return mapper.map(address, AddressDTO.class);
	}

	@PutMapping("/{addressId}")
	public AddressDTO update(@PathVariable Long addressId, @RequestBody @Valid AddressVO addressVO) {
		return mapper.map(addressService.update(addressId, addressVO), AddressDTO.class);
	}
	
	@DeleteMapping("/{addressId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long addressId) {
		addressService.delete(addressId);
	}
	
}
