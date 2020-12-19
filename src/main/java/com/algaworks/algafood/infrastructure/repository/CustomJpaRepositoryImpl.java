package com.algaworks.algafood.infrastructure.repository;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.algaworks.algafood.domain.exception.EntityNotFoundException;
import com.algaworks.algafood.domain.repository.CustomJpaRepository;

public class CustomJpaRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID>
	implements CustomJpaRepository<T, ID> {

	private EntityManager entityManager;
	
	public CustomJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, 
			EntityManager entityManager) {
		super(entityInformation, entityManager);
		
		this.entityManager = entityManager;
	}

	@Override
	public Optional<T> findFirst() {
		var jpql = "from " + getDomainClass().getName();
		
		T entity = entityManager.createQuery(jpql, getDomainClass())
			.setMaxResults(1)
			.getSingleResult();
		
		return Optional.ofNullable(entity);
	}

	@Override
	public T findOrFail(final Long id) {
		var jpql = String.format("FROM %s WHERE id = :id", getDomainClass().getName());

		try {
			T entity = entityManager
					.createQuery(jpql, getDomainClass())
					.setParameter("id", id)
					.getSingleResult();

			return entity;
		} catch (NoResultException e) {
			throw new EntityNotFoundException(String.format("Resource type %s of ID %d not found.", getDomainClass().getName(), id));
		}
	}

}
