package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.algaworks.algafood.api.validation.Groups;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_restaurant")
public class Restaurant {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

//	@NotEmpty
	@NotBlank(groups = Groups.RestaurantRegister.class)
	@Column(nullable = false)
	private String name;

	@PositiveOrZero(groups = Groups.RestaurantRegister.class)
	@DecimalMax(value="20", groups = Groups.RestaurantRegister.class)
	@Column(name = "shipping_fee", nullable = false)
	private BigDecimal shippingFee;

	@Valid
	@NotNull(groups = Groups.RestaurantRegister.class)
	@ManyToOne //(fetch = FetchType.LAZY)
	@JoinColumn(name = "kitchen_id", nullable = false)
	private Kitchen kitchen;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "address_id")
	private Address address;
	
//	@JsonIgnore
	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private LocalDateTime creationDate;
	
//	@JsonIgnore
	@UpdateTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private LocalDateTime updateDate;
	
	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "tb_restaurant_payment_type",
			joinColumns = @JoinColumn(name = "restaurant_id"),
			inverseJoinColumns = @JoinColumn(name = "payment_type_id"))
	private List<PaymentType> paymentTypes = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "restaurant")
	private List<Product> products = new ArrayList<>();
	
}
