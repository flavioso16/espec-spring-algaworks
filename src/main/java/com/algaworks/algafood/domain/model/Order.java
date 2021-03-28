package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.algaworks.algafood.domain.exception.BusinessException;

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

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "address_id")
	private Address address;

	@Enumerated(EnumType.STRING)
	private OrderStatus status = OrderStatus.CREATED;
	
	@CreationTimestamp
	private OffsetDateTime creationDate;

	private OffsetDateTime confirmationDate;

	private OffsetDateTime canceledDate;

	private OffsetDateTime deliveryDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
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

	public void calculateValues() {
		this.subtotal = getItems().stream()
				.map(OrderItem::getTotalPrice)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		this.totalValue = this.subtotal.add(this.shippingFee);
	}

	public void defineFrete() {
		setShippingFee(getRestaurant().getShippingFee());
	}

	public void addItemsToOrder() {
		getItems().forEach(item -> item.setOrder(this));
	}

	public void confirm() {
		setStatus(OrderStatus.CONFIRMED);
		setConfirmationDate(OffsetDateTime.now());
	}

	public void cancel() {
		setStatus(OrderStatus.CANCELED);
		setCanceledDate(OffsetDateTime.now());
	}

	public void delivery() {
		setStatus(OrderStatus.DELIVERED);
		setDeliveryDate(OffsetDateTime.now());
	}

	private void setStatus(OrderStatus newStatus) {
		if(getStatus().canNotUpdateTo(newStatus)) {
			throw new BusinessException(String.format("Status of order %d can not set from %s to %s", getId(),
					getStatus().getDescription(), newStatus.getDescription()));
		}
		this.status = newStatus;
	}

}
