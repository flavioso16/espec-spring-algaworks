package com.algaworks.algafood.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Product;
import com.algaworks.algafood.domain.model.Restaurant;

@Repository
public interface ProductRepository extends CustomJpaRepository<Product, Long> {

    Optional<Product> findByIdAndRestaurantId(final Long productId, final Long restaurantId);

    @Query("from Product p where p.active = true and p.restaurant = :restaurant")
    List<Product> findActiveProductsByRestaurant(final Restaurant restaurant);

    @Query("from Product p where p.restaurant = :restaurant")
    List<Product> findProductsByRestaurant(final Restaurant restaurant);

}
