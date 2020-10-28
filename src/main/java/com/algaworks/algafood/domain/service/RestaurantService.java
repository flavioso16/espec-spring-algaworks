package com.algaworks.algafood.domain.service;

import java.lang.reflect.Field;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.algaworks.algafood.domain.exception.BusinessException;
import com.algaworks.algafood.domain.exception.KitchenNotFoundException;
import com.algaworks.algafood.domain.exception.RestaurantNotFoundException;
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

    public Restaurant save(Restaurant restaurant) {
        try {
            Long kitchenId = restaurant.getKitchen().getId();
            Kitchen kitchen = kitchenService.findOrFail(kitchenId);
            restaurant.setKitchen(kitchen);
            return restaurantRepository.save(restaurant);

        } catch (KitchenNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    public Restaurant partialUpdate(Long restaurantId, Restaurant restaurant) {
        Restaurant newRestaurant = findOrFail(restaurantId);
        merge(restaurant, newRestaurant);
        return update(restaurantId, newRestaurant);

    }

    public Restaurant update(Long restaurantId, Restaurant restaurant) {
        try {
            Restaurant newRestaurant = findOrFail(restaurantId);
            BeanUtils.copyProperties(restaurant, newRestaurant,
                    "id", "paymentType", "address", "creationDate", "products");

            return save(newRestaurant);
        } catch (KitchenNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    public Restaurant findOrFail(Long restauranteId) {
        return restaurantRepository.findById(restauranteId)
                .orElseThrow(() -> new RestaurantNotFoundException(restauranteId));
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

        ReflectionUtils.doWithFields(Restaurant.class, new ReflectionUtils.FieldCallback() {
            @Override
            public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {

                field.setAccessible(true);
                if (field.get(source) != null) {
                    field.set(target, field.get(source));
                }
            }
        }, ReflectionUtils.COPYABLE_FIELDS);
    }

}
