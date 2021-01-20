package com.algaworks.algafood.api.controller;

import java.util.List;
import java.util.stream.Collectors;

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

import com.algaworks.algafood.domain.dto.KitchenDTO;
import com.algaworks.algafood.domain.dto.RestaurantDTO;
import com.algaworks.algafood.domain.model.Kitchen;
import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.service.RestaurantService;
import com.algaworks.algafood.domain.vo.RestaurantVO;

@RestController
@RequestMapping(value = "/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping
    public List<RestaurantDTO> list() {
        return restaurantService.list().stream()
                .map(this::toDTO).collect(Collectors.toList());
    }

    @GetMapping("/{restaurantId}")
    public RestaurantDTO find(@PathVariable Long restaurantId) {
        return toDTO(restaurantService.findOrFail(restaurantId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestaurantDTO save(@RequestBody @Valid RestaurantVO restaurant) {
        return toDTO(restaurantService.save(toEntity(restaurant)));
    }

    @PutMapping("/{restaurantId}")
    public RestaurantDTO update(@PathVariable Long restaurantId,
            @RequestBody @Valid RestaurantVO restaurant) {
        return toDTO(restaurantService.update(restaurantId, toEntity(restaurant)));
    }

    @PatchMapping("/{restaurantId}")
    public RestaurantDTO partialUpdate(@PathVariable Long restaurantId,
            @RequestBody Restaurant restaurant) {
        return toDTO(restaurantService.partialUpdate(restaurantId, restaurant));
    }

    private RestaurantDTO toDTO(final Restaurant restaurant) {
        final RestaurantDTO restaurantDTO = new RestaurantDTO();
        restaurantDTO.setId(restaurant.getId());
        restaurantDTO.setName(restaurant.getName());
        restaurantDTO.setShippingFee(restaurant.getShippingFee());
        KitchenDTO kitchenDTO = new KitchenDTO();
        kitchenDTO.setId(restaurant.getKitchen().getId());
        kitchenDTO.setName(restaurant.getKitchen().getName());
        restaurantDTO.setKitchen(kitchenDTO);
        return restaurantDTO;
    }

    private Restaurant toEntity(final RestaurantVO restaurantVO) {
        final Restaurant restaurant = new Restaurant();
        restaurant.setName(restaurantVO.getName());
        restaurant.setShippingFee(restaurantVO.getShippingFee());
        restaurant.setKitchen(new Kitchen());
        restaurant.getKitchen().setId(restaurantVO.getKitchen().getId());
        return restaurant;
    }

}
