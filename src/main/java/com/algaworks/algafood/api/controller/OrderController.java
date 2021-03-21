package com.algaworks.algafood.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.dto.OrderDTO;
import com.algaworks.algafood.domain.dto.OrderResumeDTO;
import com.algaworks.algafood.domain.service.OrderService;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping
    public List<OrderResumeDTO> list() {
        return orderService.list().stream()
                .map(o -> mapper.map(o, OrderResumeDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{orderId}")
    public OrderDTO find(@PathVariable Long orderId) {
        return mapper.map(orderService.findOrFail(orderId), OrderDTO.class);
    }

}
