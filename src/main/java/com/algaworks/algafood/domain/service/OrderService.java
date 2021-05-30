package com.algaworks.algafood.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.BusinessException;
import com.algaworks.algafood.domain.exception.EntityNotFoundException;
import com.algaworks.algafood.domain.filter.OrderFilter;
import com.algaworks.algafood.domain.model.Order;
import com.algaworks.algafood.domain.model.PaymentType;
import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.repository.OrderRepository;
import com.algaworks.algafood.infrastructure.repository.spec.OrderSpecs;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private RestaurantService restaurantService;

	@Autowired
	private PaymentTypeService paymentTypeService;

	@Autowired
	private AddressService addressService;

	@Autowired
	private ProductService productService;

	@Autowired
	private UserService userService;

	public Order findOrFail(Long orderId) {
		return orderRepository.findOrFail(orderId);
	}

	public Page<Order> list(OrderFilter filter, Pageable page) {
		return orderRepository.findAll(OrderSpecs.withFilter(filter), page);
	}

	@Transactional
	public Order save(final Order order) {
		try {
			order.setRestaurant(restaurantService.findOrFail(order.getRestaurant().getId()));
			order.setPaymentType(paymentTypeService.findOrFail(order.getPaymentType().getId()));
			order.setClient(userService.findOrFail(1L));
			order.setAddress(addressService.save(order.getAddress()));
			validatePaymentType(order.getPaymentType(), order.getRestaurant());
			validateItems(order);
			order.defineFrete();
			order.calculateValues();
			order.addItemsToOrder();
			orderRepository.save(order);
		} catch (EntityNotFoundException e) {
			throw new BusinessException(e.getMessage());
		}
		return order;
	}

	private void validateItems(final Order order) {
		order.getItems().forEach(item -> {
			item.setProduct(productService.findOrFail(item.getProduct().getId(),
					order.getRestaurant().getId()));
			item.calculateValues();
		});
	}

	private void validatePaymentType(final PaymentType paymentType, final Restaurant restaurant) {
		if(!restaurant.getPaymentTypes().contains(paymentType)) {
			throw new BusinessException(String.format("Payment of type '%s' is not allow to Restaurant '%s'",
					paymentType.getDescription(), restaurant.getName()));
		}
	}

	@Transactional
	public void confirm(final String orderCode) {
		Order order = findOrFail(orderCode);
		order.confirm();
	}

	@Transactional
	public void cancel(final String orderCode) {
		Order order = findOrFail(orderCode);
		order.cancel();
	}

	@Transactional
	public void delivery(final String orderCode) {
		Order order = findOrFail(orderCode);
		order.delivery();
	}

	public Order findOrFail(String orderCode) {
		return orderRepository.findByCode(orderCode)
				.orElseThrow(() -> new EntityNotFoundException(
						String.format("Order with code %s not found.", orderCode)));
	}
	
}
