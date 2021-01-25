package com.algaworks.algafood.domain.service;

import static com.algaworks.algafood.core.constants.MessageConstants.MSG_ENTITY_IN_USE;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.api.mapper.CityMapper;
import com.algaworks.algafood.domain.exception.BusinessException;
import com.algaworks.algafood.domain.exception.EntityInUseException;
import com.algaworks.algafood.domain.exception.EntityNotFoundException;
import com.algaworks.algafood.domain.model.City;
import com.algaworks.algafood.domain.model.State;
import com.algaworks.algafood.domain.repository.CityRepository;
import com.algaworks.algafood.domain.vo.CityVO;

@Service
public class CityService {

	@Autowired
	private CityRepository cityRepository;
	
	@Autowired
	private StateService stateService;

	@Autowired
	private CityMapper mapper;

	@Transactional
	public City update(Long cityId, CityVO city) {
		try {
			City newCity = findOrFail(cityId);
			mapper.copy(city, newCity);
			return save(newCity);
		} catch (EntityNotFoundException e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Transactional
	public City save(City cidade) {
		try {
			Long stateId = cidade.getState().getId();
			State state = stateService.findOrFail(stateId);
			cidade.setState(state);
			return cityRepository.save(cidade);

		} catch (EntityNotFoundException e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Transactional
	public void delete(Long cityId) {
		try {
			cityRepository.deleteById(cityId);
			cityRepository.flush();
			
		} catch (EmptyResultDataAccessException e) {
			throw new EntityNotFoundException(City.class, cityId);
		
		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(
				String.format(MSG_ENTITY_IN_USE, "Cidade", cityId));
		}
	}
	
	public City findOrFail(Long cidadeId) {
		return cityRepository.findOrFail(cidadeId);
	}

	public List<City> list() {
		return cityRepository.findAll();
	}
	
	
}
