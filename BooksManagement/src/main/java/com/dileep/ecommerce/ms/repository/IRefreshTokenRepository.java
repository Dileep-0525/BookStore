package com.dileep.ecommerce.ms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dileep.ecommerce.ms.entity.RefreshTokenEntity;

public interface IRefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {

	Optional<RefreshTokenEntity> findByToken(String token);
	
	void deleteByToken(String token);
	
	void deleteByUsername(String username);
}