package com.algaworks.algafood.domain.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import com.algaworks.algafood.domain.model.OrderStatus;

import lombok.Data;

@Data
public class OrderDTO {

	private Long id;
	private BigDecimal subtotal;
	private BigDecimal shippingFee;
	private BigDecimal totalValue;
	private AddressDTO address;
	private OrderStatus status = OrderStatus.CREATED;
	private OffsetDateTime creationDate;
	private OffsetDateTime confirmationDate;
	private OffsetDateTime canceledDate;
	private OffsetDateTime deliveryDate;
	private PaymentTypeDTO paymentType;
	private RestaurantResumeDTO restaurant;
	private UserDTO client;
	private List<OrderItemDTO> items = new ArrayList<>();

}
