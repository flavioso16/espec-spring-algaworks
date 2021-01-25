package com.algaworks.algafood.domain.service;

import static com.algaworks.algafood.core.constants.MessageConstants.MSG_ENTITY_IN_USE;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.api.mapper.StateMapper;
import com.algaworks.algafood.domain.exception.EntityInUseException;
import com.algaworks.algafood.domain.exception.EntityNotFoundException;
import com.algaworks.algafood.domain.model.State;
import com.algaworks.algafood.domain.repository.StateRepository;
import com.algaworks.algafood.domain.vo.StateVO;

@Service
public class StateService {
	
	@Autowired
	private StateRepository stateRepository;

	@Autowired
	private StateMapper mapper;

	@Transactional
	public State update(Long stateId, StateVO state) {
		State newState = findOrFail(stateId);
		mapper.copy(state, newState);
		return stateRepository.save(newState);
	}

	@Transactional
	public State save(State state) {
		return stateRepository.save(state);
	}

	@Transactional
	public void delete(Long stateId) {
		try {
			stateRepository.deleteById(stateId);
			stateRepository.flush();
			
		} catch (EmptyResultDataAccessException e) {
			throw new EntityNotFoundException(State.class, stateId);
		
		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(
				String.format(MSG_ENTITY_IN_USE, "Estado", stateId));
		}
	}

	public State findOrFail(Long stateId) {
		return stateRepository.findOrFail(stateId);
	}

	public List<State> list() {
		return stateRepository.findAll();
	}
	
}
