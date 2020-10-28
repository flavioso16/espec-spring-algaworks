package com.algaworks.algafood.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.ReflectionUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author flaoliveira
 * @version : $<br/>
 * : $
 * @since 10/26/20 10:53 AM
 */
public class ObjectMerger {

    private static boolean cacheEnabled = true;
    private static Map<Object, ObjectMerger> objectMergerCache = new HashMap<>();

    public static void mergeRequestBodyToGenericObject(Map<String, Object> objectMap, Object objectToUpdate, Class type) {
        ObjectMapper objectMapper = new ObjectMapper();
        Object newObject = objectMapper.convertValue(objectMap, type);

        objectMap.forEach((fieldProp, valueProp) -> {
            Field field = ReflectionUtils.findField(type, fieldProp);
            field.setAccessible(true);

            Object newValue = ReflectionUtils.getField(field, newObject);

            ReflectionUtils.setField(field, objectToUpdate, newValue);
        });
    }

}
