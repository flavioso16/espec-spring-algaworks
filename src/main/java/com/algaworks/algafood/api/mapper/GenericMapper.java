package com.algaworks.algafood.api.mapper;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.Getter;

/**
 * @author flaoliveira
 * @version : $<br/>
 * : $
 * @since 1/19/21 9:19 PM
 */
@Getter
abstract class GenericMapper <E, VO, DTO>{

    private Class<E> entityClass;
    private Class<VO> requestClass;
    private Class<DTO> responseClass;

    @Autowired
    private ModelMapper mapper;

    @SuppressWarnings("unchecked")
    public GenericMapper() {
        Type[] actualTypeArguments = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments();
        this.entityClass = (Class<E>) actualTypeArguments[0];
        this.requestClass = (Class<VO>) actualTypeArguments[1];
        this.responseClass = (Class<DTO>) actualTypeArguments[2];
    }

    public DTO toDto(E entityClass) {
        return mapper.map(entityClass, getResponseClass());
    }

    public List<DTO> toListDto(Collection<E> requestEntities) {
        return requestEntities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public E toEntity(VO requestClass) {
        return mapper.map(requestClass, getEntityClass());
    }

    public void copy(VO requestClass, E entityClass) {
        mapper.map(requestClass, entityClass);
    }

}
