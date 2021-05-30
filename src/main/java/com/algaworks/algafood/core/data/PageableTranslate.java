package com.algaworks.algafood.core.data;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * @author flaoliveira
 * @version : $<br/>
 * : $
 * @since 5/29/21 6:04 PM
 */
public class PageableTranslate {

    public static Pageable translate(Pageable pageable, Map<String, String> fieldsMappings) {
        var orders = pageable.getSort().stream()
                .filter(order -> fieldsMappings.containsKey(order.getProperty()))
                .map(order -> new Sort.Order(order.getDirection(), fieldsMappings.get(order.getProperty())))
                .collect(Collectors.toList());
        System.out.println(orders);
        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(orders));
    }

}
