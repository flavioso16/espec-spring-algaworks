package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.BusinessException;
import com.algaworks.algafood.domain.exception.EntityInUseException;
import com.algaworks.algafood.domain.exception.EntityNotFoundException;
import com.algaworks.algafood.domain.model.City;
import com.algaworks.algafood.domain.model.State;
import com.algaworks.algafood.domain.repository.CityRepository;

@Service
public class CityService {

	private static final String MSG_CITY_IN_USE = "Cidade de código %d não pode ser removida, pois está em uso";

	@Autowired
	private CityRepository cityRepository;
	
	@Autowired
	private StateService stateService;

	public City update(Long cityId, City city) {
		try {
			City newCity = findOrFail(cityId);
			BeanUtils.copyProperties(city, newCity, "id");
			return save(newCity);
		} catch (EntityNotFoundException e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
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
	
	public void delete(Long cityId) {
		try {
			cityRepository.deleteById(cityId);
			
		} catch (EmptyResultDataAccessException e) {
			throw new EntityNotFoundException(City.class, cityId);
		
		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(
				String.format(MSG_CITY_IN_USE, cityId));
		}
	}
	
	public City findOrFail(Long cidadeId) {
		return cityRepository.findOrFail(cidadeId);
	}

	public List<City> list() {
		return cityRepository.findAll();
	}
	
	
}
