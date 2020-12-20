package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.service.RestaurantService;

@RestController
@RequestMapping(value = "/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping
    public List<Restaurant> list() {
        return restaurantService.list();
    }

    @GetMapping("/{restaurantId}")
    public Restaurant find(@PathVariable Long restaurantId) {
        return restaurantService.findOrFail(restaurantId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Restaurant save(@RequestBody @Valid Restaurant restaurant) {
        return restaurantService.save(restaurant);
    }

    @PutMapping("/{restaurantId}")
    public Restaurant update(@PathVariable Long restaurantId,
            @RequestBody @Valid Restaurant restaurant) {
        return restaurantService.update(restaurantId, restaurant);
    }

    @PatchMapping("/{restaurantId}")
    public Restaurant partialUpdate(@PathVariable Long restaurantId,
            @RequestBody Restaurant restaurant) {
        return restaurantService.partialUpdate(restaurantId, restaurant);
    }

}
