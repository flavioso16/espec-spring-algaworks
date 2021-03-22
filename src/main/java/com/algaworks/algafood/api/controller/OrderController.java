package com.algaworks.algafood.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.dto.OrderDTO;
import com.algaworks.algafood.domain.dto.OrderResumeDTO;
import com.algaworks.algafood.domain.model.Order;
import com.algaworks.algafood.domain.service.OrderService;
import com.algaworks.algafood.domain.vo.OrderVO;

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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDTO save(@RequestBody @Valid OrderVO order) {
        final Order entity = mapper.map(order, Order.class);
        return mapper.map(orderService.save(entity), OrderDTO.class);
    }

    @PutMapping("/{orderId}/confirm")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void confirm(@PathVariable Long orderId) {
        orderService.confirm(orderId);
    }

    @PutMapping("/{orderId}/cancel")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancel(@PathVariable Long orderId) {
        orderService.cancel(orderId);
    }

    @PutMapping("/{orderId}/delivery")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delivery(@PathVariable Long orderId) {
        orderService.delivery(orderId);
    }

}
