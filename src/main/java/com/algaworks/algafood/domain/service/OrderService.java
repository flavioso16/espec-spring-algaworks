package com.algaworks.algafood.domain.service;

import java.time.OffsetDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.BusinessException;
import com.algaworks.algafood.domain.exception.EntityNotFoundException;
import com.algaworks.algafood.domain.model.Order;
import com.algaworks.algafood.domain.model.OrderStatus;
import com.algaworks.algafood.domain.model.PaymentType;
import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.repository.OrderRepository;

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

	public List<Order> list() {
		return orderRepository.findAll();
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
	public void confirm(final Long orderId) {
		Order order = findOrFail(orderId);
		if(!OrderStatus.CREATED.equals(order.getStatus())) {
			throw new BusinessException(String.format("Status of order %d can not set from %s to %s", order.getId(),
					order.getStatus().getDescription(), OrderStatus.CONFIRMED.getDescription()));
		}
		order.setStatus(OrderStatus.CONFIRMED);
		order.setConfirmationDate(OffsetDateTime.now());
	}

	@Transactional
	public void cancel(final Long orderId) {
		Order order = findOrFail(orderId);
		if(!OrderStatus.CREATED.equals(order.getStatus())) {
			throw new BusinessException(String.format("Status of order %d can not set from %s to %s", order.getId(),
					order.getStatus().getDescription(), OrderStatus.CANCELED.getDescription()));
		}
		order.setStatus(OrderStatus.CANCELED);
		order.setCanceledDate(OffsetDateTime.now());
	}

	@Transactional
	public void delivery(final Long orderId) {
		Order order = findOrFail(orderId);
		if(!OrderStatus.CONFIRMED.equals(order.getStatus())) {
			throw new BusinessException(String.format("Status of order %d can not set from %s to %s", order.getId(),
					order.getStatus().getDescription(), OrderStatus.DELIVERED.getDescription()));
		}
		order.setStatus(OrderStatus.DELIVERED);
		order.setDeliveryDate(OffsetDateTime.now());
	}
	
}
