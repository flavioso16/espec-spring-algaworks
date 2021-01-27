package com.algaworks.algafood.domain.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.User;

@Repository
public interface UserRepository extends CustomJpaRepository<User, Long> {

    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.email = :email and (:id is null or u.id <> :id)")
    boolean existsByEmailAndIdNot(final String email, final Long id);

}
