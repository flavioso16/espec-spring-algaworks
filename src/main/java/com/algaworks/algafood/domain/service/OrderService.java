package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.model.Order;
import com.algaworks.algafood.domain.repository.OrderRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	public Order findOrFail(Long cidadeId) {
		return orderRepository.findOrFail(cidadeId);
	}

	public List<Order> list() {
		return orderRepository.findAll();
	}
	
	
}
