package com.algaworks.algafood.domain.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.BusinessException;
import com.algaworks.algafood.domain.exception.EntityInUseException;
import com.algaworks.algafood.domain.exception.EntityNotFoundException;
import com.algaworks.algafood.domain.model.Address;
import com.algaworks.algafood.domain.model.City;
import com.algaworks.algafood.domain.model.Kitchen;
import com.algaworks.algafood.domain.repository.AddressRepository;
import com.algaworks.algafood.domain.repository.CityRepository;
import com.algaworks.algafood.domain.vo.AddressVO;

@Service
public class AddressService {

    private static final String MSG_ADDRESS_IN_USE = "Endereço de código %d não pode ser removido, pois está em uso";

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private ModelMapper mapper;

    @Transactional
    public Address save(Address address) {
        try {
            City city =  cityRepository.findOrFail(address.getCity().getId());
            address.setCity(city);
            return addressRepository.save(address);
        } catch (EntityNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Transactional
    public Address update(Long addressId, AddressVO addressVO) {
        try {
            Address address = findOrFail(addressId);
            address.setCity(new City());
            mapper.map(addressVO, address);
            return save(address);
        } catch (EntityNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Transactional
    public void delete(Long addressId) {
        try {
            addressRepository.deleteById(addressId);
            addressRepository.flush();

        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(Kitchen.class, addressId);

        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(
                    String.format(MSG_ADDRESS_IN_USE, addressId));
        }  catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public Address findOrFail(Long addressId) {
        return addressRepository.findOrFail(addressId);
    }

    public List<Address> list() {
        return addressRepository.findAll();
    }


}
