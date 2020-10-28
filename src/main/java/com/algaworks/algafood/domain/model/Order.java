package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_order")
public class Order {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private BigDecimal subtotal;

	private BigDecimal shippingFee;

	private BigDecimal totalValue;

	@Embedded
	//TODO Mapear corretamente
	private Address address;
	
	private OrderStatus status;
	
	@CreationTimestamp
	private LocalDateTime creationDate;

	private LocalDateTime confirmationDate;

	private LocalDateTime canceledDate;

	private LocalDateTime deliveryDate;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private PaymentType paymentType;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Restaurant restaurant;
	
	@ManyToOne
	@JoinColumn(name = "client_id", nullable = false)
	private User client;
	
	@OneToMany(mappedBy = "order")
	private List<OrderItem> items = new ArrayList<>();

}
