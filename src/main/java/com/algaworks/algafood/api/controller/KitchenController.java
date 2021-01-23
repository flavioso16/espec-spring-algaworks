package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

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

import com.algaworks.algafood.api.mapper.KitchenMapper;
import com.algaworks.algafood.domain.dto.KitchenDTO;
import com.algaworks.algafood.domain.service.KitchenService;
import com.algaworks.algafood.domain.vo.KitchenVO;

@RestController
@RequestMapping(value = "/kitchens")
public class KitchenController {

	@Autowired
	private KitchenService kitchenService;

	@Autowired
	private KitchenMapper mapper;

	@GetMapping
	public List<KitchenDTO> list() {
		return mapper.toListDto(kitchenService.list());
	}

	@GetMapping("/{kitchenId}")
	public KitchenDTO find(@PathVariable Long kitchenId) {
		return mapper.toDto(kitchenService.findOrFail(kitchenId));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public KitchenDTO save(@RequestBody @Valid KitchenVO kitchen) {
		return mapper.toDto(kitchenService.save(mapper.toEntity(kitchen)));
	}

	@PutMapping("/{kitchenId}")
	public KitchenDTO update(@PathVariable Long kitchenId, @RequestBody @Valid KitchenVO kitchen) {
		return mapper.toDto(kitchenService.update(kitchenId, kitchen));
	}
	
	@DeleteMapping("/{kitchenId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long kitchenId) {
		kitchenService.delete(kitchenId);
	}
	
}
