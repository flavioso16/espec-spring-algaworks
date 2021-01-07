package com.algaworks.algafood.core.jackson;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.domain.model.City;
import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.model.mixin.CityMixin;
import com.algaworks.algafood.domain.model.mixin.RestaurantMixin;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * @author flaoliveira
 * @version : $<br/>
 * : $
 * @since 1/6/21 10:06 PM
 */
@Component
public class JacksonMixinModule extends SimpleModule {

    public JacksonMixinModule() {
        setMixInAnnotation(Restaurant.class, RestaurantMixin.class);
        setMixInAnnotation(City.class, CityMixin.class);
    }
}
