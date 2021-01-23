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

import com.algaworks.algafood.api.mapper.StateMapper;
import com.algaworks.algafood.domain.dto.StateDTO;
import com.algaworks.algafood.domain.service.StateService;
import com.algaworks.algafood.domain.vo.StateVO;

@RestController
@RequestMapping("/states")
public class StateController {

	@Autowired
	private StateService stateService;

	@Autowired
	private StateMapper mapper;
	
	@GetMapping
	public List<StateDTO> list() {
		return mapper.toListDto(stateService.list());
	}
	
	@GetMapping("/{stateId}")
	public StateDTO find(@PathVariable Long stateId) {
		return mapper.toDto(stateService.findOrFail(stateId));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public StateDTO save(@RequestBody @Valid StateVO state) {
		return mapper.toDto(stateService.save(mapper.toEntity(state)));
	}
	
	@PutMapping("/{stateId}")
	public StateDTO update(@PathVariable Long stateId, @RequestBody @Valid StateVO state) {
		return mapper.toDto(stateService.update(stateId, state));
	}
	
	@DeleteMapping("/{stateId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long stateId) {
		stateService.delete(stateId);
	}

}
