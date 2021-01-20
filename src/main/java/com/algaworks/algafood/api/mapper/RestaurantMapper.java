package com.algaworks.algafood.api;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.domain.dto.RestaurantDTO;
import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.vo.RestaurantVO;

/**
 * @author flaoliveira
 * @version : $<br/>
 * : $
 * @since 1/19/21 9:19 PM
 */
@Component
public class RestaurantMapper {

    @Autowired
    private ModelMapper modelMapper;

    public RestaurantDTO toDTO(Restaurant restaurant) {
        return modelMapper.map(restaurant, RestaurantDTO.class);
    }

    public Restaurant toEntity(RestaurantVO restaurantVO) {
        return modelMapper.map(restaurantVO, Restaurant.class);
    }

}
