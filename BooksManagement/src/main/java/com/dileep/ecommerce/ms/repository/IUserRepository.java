package com.dileep.ecommerce.ms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dileep.ecommerce.ms.entity.UserEntity;

public interface IUserRepository extends JpaRepository<UserEntity, Long>{

	Optional<UserEntity> findByEmail(String email);
	
}
