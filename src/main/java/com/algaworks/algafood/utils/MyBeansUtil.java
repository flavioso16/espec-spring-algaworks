package com.algaworks.algafood.utils;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.lang.reflect.Field;
import java.util.Collection;

public class MyBeansUtil {
    public static void copyNonNullProperties(Object target, Object source) {
        if (source == null || target == null) {
            throw new IllegalArgumentException("Target and source class can not be null");
        }

        if (target.getClass() != source.getClass()) {
            throw new IllegalArgumentException("Target class must be the same type of source class.");
        }

        final BeanWrapper src = new BeanWrapperImpl(source);
        final BeanWrapper trg = new BeanWrapperImpl(target);

        for (final Field property : target.getClass().getDeclaredFields()) {
            Object providedObject = src.getPropertyValue(property.getName());
            if (providedObject != null && !(providedObject instanceof Collection<?>)) {
                trg.setPropertyValue(
                        property.getName(),
                        providedObject);
            }
        }
    }
}