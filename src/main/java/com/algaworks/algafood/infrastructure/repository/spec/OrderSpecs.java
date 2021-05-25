package com.algaworks.algafood.infrastructure.repository.spec;

import java.util.ArrayList;
import java.util.Optional;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.algaworks.algafood.domain.model.Order;
import com.algaworks.algafood.domain.repository.filter.OrderFilter;

public class OrderSpecs {

	public static Specification<Order> withFilter(OrderFilter filter) {
		return (root, query, builder) -> {
		   var predicates = new ArrayList<Predicate>();
		   if(Order.class.equals(query.getResultType())) {
			   root.fetch("restaurant").fetch("kitchen");
			   root.fetch("client");
		   }

		   Optional.ofNullable(filter.getClientId()).ifPresent(clientId ->
				   predicates.add(builder.equal(root.get("client"), clientId)));

			Optional.ofNullable(filter.getRestaurantId()).ifPresent(restaurantId ->
					predicates.add(builder.equal(root.get("restaurant"), restaurantId)));

			Optional.ofNullable(filter.getCreationDateInitial()).ifPresent(clientId ->
					predicates.add(builder.greaterThanOrEqualTo(root.get("creationDate"), clientId)));

			Optional.ofNullable(filter.getCreationDateFinal()).ifPresent(clientId ->
					predicates.add(builder.lessThanOrEqualTo(root.get("creationDate"), clientId)));


		   return builder.and(predicates.toArray(new Predicate[0]));
		};
	}
	
}
