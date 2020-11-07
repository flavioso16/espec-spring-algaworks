package com.algaworks.algafood.domain.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.repository.RestaurantRepository;

/**
 * @author flaoliveira
 * @version : $<br/>
 * : $
 * @since 11/2/20 2:00 PM
 */
@Service
public class TestService {

    public RestaurantRepository restaurantRepository;

    public TestService(final RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public void testUpdateWithTransaction() {

        final Optional<Restaurant> restaurantOp = restaurantRepository.findById(6L);

        final Restaurant restaurant = restaurantOp.get();
        restaurant.setName("Bar da Maria");

        restaurantRepository.save(restaurant);
    }

}
