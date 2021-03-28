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

//    @GetMapping
//    public MappingJacksonValue list(@RequestParam(required = false) String fields) {
//        final List<OrderResumeDTO> orders = orderService.list().stream()
//                .map(o -> mapper.map(o, OrderResumeDTO.class))
//                .collect(Collectors.toList());
//        MappingJacksonValue ordersWrapper = new MappingJacksonValue(orders);
//
//        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
//
//        if(StringUtils.isNotBlank(fields)) {
//            filterProvider.addFilter("orderFilter",
//                    SimpleBeanPropertyFilter.filterOutAllExcept(fields.split(",")));
//        } else {
//            filterProvider.addFilter("orderFilter",
//                    SimpleBeanPropertyFilter.serializeAll());
//        }
//
//        ordersWrapper.setFilters(filterProvider);
//
//        return ordersWrapper;
//    }

//    @GetMapping
//    public List<OrderResumeDTO> list() {
//        return orderService.list().stream()
//                .map(o -> mapper.map(o, OrderResumeDTO.class))
//                .collect(Collectors.toList());
//    }

    @GetMapping("/{orderCode}")
    public OrderDTO find(@PathVariable String orderCode) {
        return mapper.map(orderService.findOrFail(orderCode), OrderDTO.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDTO save(@RequestBody @Valid OrderVO order) {
        final Order entity = mapper.map(order, Order.class);
        return mapper.map(orderService.save(entity), OrderDTO.class);
    }

    @PutMapping("/{orderCode}/confirm")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void confirm(@PathVariable String orderCode) {
        orderService.confirm(orderCode);
    }

    @PutMapping("/{orderCode}/cancel")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancel(@PathVariable String orderCode) {
        orderService.cancel(orderCode);
    }

    @PutMapping("/{orderCode}/delivery")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delivery(@PathVariable String orderCode) {
        orderService.delivery(orderCode);
    }

}
