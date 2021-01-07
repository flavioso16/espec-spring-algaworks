package com.algaworks.algafood.domain.service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;

import com.algaworks.algafood.core.validation.ValidationException;
import com.algaworks.algafood.domain.exception.BusinessException;
import com.algaworks.algafood.domain.exception.EntityNotFoundException;
import com.algaworks.algafood.domain.model.Kitchen;
import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.repository.RestaurantRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private KitchenService kitchenService;

    @Autowired
    private SmartValidator validator;

    @Transactional
    public Restaurant save(Restaurant restaurant) {
        try {
            Long kitchenId = restaurant.getKitchen().getId();
            Kitchen kitchen = kitchenService.findOrFail(kitchenId);
            restaurant.setKitchen(kitchen);
            return restaurantRepository.save(restaurant);

        } catch (EntityNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Transactional
    public Restaurant partialUpdate(Long restaurantId, Restaurant restaurant) {
        Restaurant newRestaurant = findOrFail(restaurantId);
        merge(restaurant, newRestaurant);
        validate(newRestaurant, "restaurante");
        return update(restaurantId, newRestaurant);

    }

    private void validate(final Restaurant restaurant, final String objectName) {
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurant, objectName);
        validator.validate(restaurant, bindingResult);
        if(bindingResult.hasErrors())  {
            throw new ValidationException(bindingResult);
        }
    }
    
    @Transactional
    public Restaurant update(Long restaurantId, Restaurant restaurantParam) {
        try {
            Restaurant restaurant = findOrFail(restaurantId);
            BeanUtils.copyProperties(restaurantParam, restaurant,
                    "id", "paymentType", "address", "creationDate", "products");

            return save(restaurant);
        } catch (EntityNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    public Restaurant findOrFail(Long restauranteId) {
        return restaurantRepository.findOrFail(restauranteId);
    }

    // TODO refactor this
    private void merge(Map<String, Object> dadosOrigem, Restaurant restaurantDestino, HttpServletRequest request) {
        ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

            Restaurant restaurantOrigem = objectMapper.convertValue(dadosOrigem, Restaurant.class);

            dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
                Field field = ReflectionUtils.findField(Restaurant.class, nomePropriedade);
                field.setAccessible(true);
                Object novoValor = ReflectionUtils.getField(field, restaurantOrigem);
                ReflectionUtils.setField(field, restaurantDestino, novoValor);
            });

        } catch (IllegalArgumentException e) {
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);

        }
    }

    // TODO Precisa validar essa implementação, talvez usar a classe ObjectMerger
    private void merge(final Restaurant source, final Restaurant target) {
        ReflectionUtils.doWithFields(Restaurant.class, field -> {
            field.setAccessible(true);
            if (field.get(source) != null) {
                field.set(target, field.get(source));
            }
        }, ReflectionUtils.COPYABLE_FIELDS);
    }

    public List<Restaurant> list() {
        return restaurantRepository.findAll();
    }
}
