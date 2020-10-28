package com.algaworks.algafood.infrastructure.repository;

import static com.algaworks.algafood.infrastructure.repository.spec.RestaurantSpecs.nameLike;
import static com.algaworks.algafood.infrastructure.repository.spec.RestaurantSpecs.withShippingFee;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.repository.RestaurantRepository;
import com.algaworks.algafood.domain.repository.RestaurantRepositoryQueries;

@Repository
public class RestaurantRepositoryImpl implements RestaurantRepositoryQueries {

	@PersistenceContext
	private EntityManager manager;
	
	@Autowired @Lazy
	private RestaurantRepository restaurantRepository;
	
	@Override
	public List<Restaurant> find(String name,
			BigDecimal initialShippingFee, BigDecimal finalShippingFee) {
		var builder = manager.getCriteriaBuilder();
		
		var criteria = builder.createQuery(Restaurant.class);
		var root = criteria.from(Restaurant.class);

		var predicates = new ArrayList<Predicate>();
		
		if (StringUtils.hasText(name)) {
			predicates.add(builder.like(root.get("name"), "%" + name + "%"));
		}
		
		if (initialShippingFee != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("shippingFee"), initialShippingFee));
		}
		
		if (finalShippingFee != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get("shippingFee"), finalShippingFee));
		}
		
		criteria.where(predicates.toArray(new Predicate[0]));
		
		var query = manager.createQuery(criteria);
		return query.getResultList();
	}

	@Override
	public List<Restaurant> findShippingFee(String name) {
		return restaurantRepository.findAll(withShippingFee()
				.and(nameLike(name)));
	}
	
}
