package com.dileep.ecommerce.ms.util;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dileep.ecommerce.ms.entity.UserEntity;
import com.dileep.ecommerce.ms.repository.IUserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final IUserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		UserEntity user = userRepository.findByEmail(email).get();
		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}

		return User.builder()
				   .username(user.getEmail())
				   .password(user.getPassword())
				   .authorities("USER").build();
	}
}