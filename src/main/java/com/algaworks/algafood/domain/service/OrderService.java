package com.algaworks.algafood.domain.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.BusinessException;
import com.algaworks.algafood.domain.exception.EntityNotFoundException;
import com.algaworks.algafood.domain.model.Order;
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

	public Order findOrFail(Long cidadeId) {
		return orderRepository.findOrFail(cidadeId);
	}

	public List<Order> list() {
		return orderRepository.findAll();
	}

	@Transactional
	public Order save(final Order order) {
		try {
			order.setRestaurant(restaurantService.findOrFail(order.getRestaurant().getId()));
			order.setPaymentType(paymentTypeService.findOrFail(order.getPaymentType().getId()));
			validatePaymentType(order.getPaymentType(), order.getRestaurant());
			order.setAddress(addressService.save(order.getAddress()));
			order.setClient(userService.findOrFail(1L));
			order.getItems().forEach(item -> {
				item.setProduct(productService.findOrFail(item.getProduct().getId(),
						order.getRestaurant().getId()));
				item.calculateValues();
			});
			order.defineFrete();
			order.calculateValues();
			order.addItemsToOrder();
			orderRepository.save(order);
		} catch (EntityNotFoundException e) {
			throw new BusinessException(e.getMessage());
		}
		return order;
	}

	private void validatePaymentType(final PaymentType paymentType, final Restaurant restaurant) {
		if(!restaurant.getPaymentTypes().contains(paymentType)) {
			throw new BusinessException(String.format("Payment of type '%s' is not allow to Restaurant '%s'",
					paymentType.getDescription(), restaurant.getName()));
		}
	}
}
