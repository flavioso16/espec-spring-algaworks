package com.algaworks.algafood.utils;

import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

@Component
public class ModelMergeUtil {

    public <T> void merge(final T source, final T target) {
        ReflectionUtils.doWithFields(source.getClass(), field -> {
            field.setAccessible(true);
            if (field.get(source) != null) {
                field.set(target, field.get(source));
            }
        }, ReflectionUtils.COPYABLE_FIELDS);
    }

//    private void merge(final Restaurant source, final Restaurant target) {
//        ReflectionUtils.doWithFields(Restaurant.class, field -> {
//            field.setAccessible(true);
//            if (field.get(source) != null) {
//                field.set(target, field.get(source));
//            }
//        }, ReflectionUtils.COPYABLE_FIELDS);
//    }

}