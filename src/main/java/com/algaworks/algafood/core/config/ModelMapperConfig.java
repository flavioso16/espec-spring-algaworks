package com.algaworks.algafood.core.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algaworks.algafood.domain.dto.AddressDTO;
import com.algaworks.algafood.domain.model.Address;

/**
 * @author flaoliveira
 * @version : $<br/>
 * : $
 * @since 1/19/21 9:32 PM
 */
@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();

        var addressTypeMap = modelMapper.createTypeMap(Address.class, AddressDTO.class);
        addressTypeMap.<String>addMapping(
                src -> src.getCity().getState().getName(),
                (dest, value) -> dest.getCity().setState(value)
        );

        return modelMapper;
    }

}
