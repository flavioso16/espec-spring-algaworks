package com.algaworks.algafood.api.controller;

import java.util.List;

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

import com.algaworks.algafood.domain.model.State;
import com.algaworks.algafood.domain.repository.StateRepository;
import com.algaworks.algafood.domain.service.StateService;

@RestController
@RequestMapping("/states")
public class StateController {

	@Autowired
	private StateRepository stateRepository;

	@Autowired
	private StateService stateService;
	
	@GetMapping
	public List<State> list() {
		return stateRepository.findAll();
	}
	
	@GetMapping("/{stateId}")
	public State find(@PathVariable Long stateId) {
		return stateService.findOrFail(stateId);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public State save(@RequestBody State state) {
		return stateService.save(state);
	}
	
	@PutMapping("/{stateId}")
	public State atualizar(@PathVariable Long stateId, @RequestBody State state) {
		return stateService.update(stateId, state);
	}
	
	@DeleteMapping("/{stateId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long stateId) {
		stateService.delete(stateId);
	}
	
}
