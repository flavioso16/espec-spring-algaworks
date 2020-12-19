package com.algaworks.algafood.domain.repository;

import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.PaymentType;

@Repository
public interface PaymentTypeRepository extends CustomJpaRepository<PaymentType, Long> {

}
