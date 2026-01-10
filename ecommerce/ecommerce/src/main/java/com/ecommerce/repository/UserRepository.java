package com.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.entity.User;

public interface UserRepository extends JpaRepository<User,Long> {

	boolean existsByEmail(String email);
	boolean existsById(Long id);
	Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);
}
