package com.algaworks.algafood.domain.repository;

import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Address;

@Repository
public interface AddressRepository extends CustomJpaRepository<Address, Long> {

}
