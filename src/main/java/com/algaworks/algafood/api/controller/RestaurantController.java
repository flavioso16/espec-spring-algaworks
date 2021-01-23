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

import com.algaworks.algafood.api.mapper.RestaurantMapper;
import com.algaworks.algafood.domain.dto.RestaurantDTO;
import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.service.RestaurantService;
import com.algaworks.algafood.domain.vo.RestaurantVO;

@RestController
@RequestMapping(value = "/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private RestaurantMapper mapper;

    @GetMapping
    public List<RestaurantDTO> list() {
        return mapper.toListDto(restaurantService.list());
    }

    @GetMapping("/{restaurantId}")
    public RestaurantDTO find(@PathVariable Long restaurantId) {
        return mapper.toDto(restaurantService.findOrFail(restaurantId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestaurantDTO save(@RequestBody @Valid RestaurantVO restaurant) {
        return mapper.toDto(restaurantService.save(mapper.toEntity(restaurant)));
    }

    @PutMapping("/{restaurantId}")
    public RestaurantDTO update(@PathVariable Long restaurantId,
            @RequestBody @Valid RestaurantVO restaurant) {
        
        return mapper.toDto(restaurantService.update(restaurantId, restaurant));
    }

    @PatchMapping("/{restaurantId}")
    public RestaurantDTO partialUpdate(@PathVariable Long restaurantId,
            @RequestBody Restaurant restaurant) {
        return mapper.toDto(restaurantService.partialUpdate(restaurantId, restaurant));
    }

}
