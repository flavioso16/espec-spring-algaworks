package com.algaworks.algafood.domain.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Product;

@Repository
public interface ProductRepository extends CustomJpaRepository<Product, Long> {

    Optional<Product> findByIdAndRestaurantId(final Long productId, final Long restaurantId);


}
