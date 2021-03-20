package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.algaworks.algafood.core.validation.ZeroValueIncludeDescription;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_restaurant")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ZeroValueIncludeDescription(fieldValue = "shippingFee", fieldDescription = "name", requiredDescription = "Frete Gratis")
public class Restaurant {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(name = "shipping_fee", nullable = false)
	private BigDecimal shippingFee;

	@ManyToOne //(fetch = FetchType.LAZY)
	@JoinColumn(name = "kitchen_id", nullable = false)
	private Kitchen kitchen;

	@ManyToOne
	@JoinColumn(name = "address_id")
	private Address address;
	
	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private OffsetDateTime creationDate;
	
	@UpdateTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private OffsetDateTime updateDate;
	
	@ManyToMany
	@JoinTable(name = "tb_restaurant_payment_type",
			joinColumns = @JoinColumn(name = "restaurant_id"),
			inverseJoinColumns = @JoinColumn(name = "payment_type_id"))
	private Set<PaymentType> paymentTypes = new HashSet<>();
	
	@OneToMany(mappedBy = "restaurant")
	private List<Product> products = new ArrayList<>();

	private Boolean active = Boolean.TRUE;

	private Boolean isOpen = Boolean.TRUE;

	@ManyToMany
	@JoinTable(name = "tb_restaurant_user_responsible",
			joinColumns = @JoinColumn(name = "restaurant_id"),
			inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Set<User> responsibles = new HashSet<>();

	public void activate() {
		this.active = Boolean.TRUE;
	}

	public void inactivate() {
		this.active = Boolean.FALSE;
	}

	public boolean bindPaymentType(PaymentType paymentType) {
		return getPaymentTypes().add(paymentType);
	}

	public void unbindPaymentType(Long paymentTypeId) {
		findPaymentType(paymentTypeId)
				.ifPresent(getPaymentTypes()::remove);
	}

	private Optional<PaymentType> findPaymentType(Long paymentTypeId) {
		return getPaymentTypes().stream()
				.filter(paymentType -> paymentType.getId().equals(paymentTypeId))
				.findFirst();
	}

	public boolean isPaymentTypeBonded(Long paymentTypeId) {
		return findPaymentType(paymentTypeId).isEmpty();
	}

	public void includeProduct(final Product product) {
		this.getProducts().add(product);
	}

	public void open() {
		this.isOpen = true;
	}

	public void close() {
		this.isOpen = false;
	}

	public boolean bindResponsible(User responsible) {
		return getResponsibles().add(responsible);
	}

	public void unbindResponsible(Long responsibleId) {
		findResponsible(responsibleId)
				.ifPresent(getResponsibles()::remove);
	}

	private Optional<User> findResponsible(Long responsibleId) {
		return getResponsibles().stream()
				.filter(responsible -> responsible.getId().equals(responsibleId))
				.findFirst();
	}

	public boolean isResponsibleBounded(Long responsibleId) {
		return findResponsible(responsibleId).isEmpty();
	}
}
