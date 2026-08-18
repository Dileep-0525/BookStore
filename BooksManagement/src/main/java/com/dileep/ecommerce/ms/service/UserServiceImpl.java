package com.dileep.ecommerce.ms.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dileep.ecommerce.ms.dto.ChangePasswordDTO;
import com.dileep.ecommerce.ms.dto.UserDTO;
import com.dileep.ecommerce.ms.entity.RoleEntity;
import com.dileep.ecommerce.ms.entity.UserEntity;
import com.dileep.ecommerce.ms.exceptions.GlobalException;
import com.dileep.ecommerce.ms.repository.IRoleRepository;
import com.dileep.ecommerce.ms.repository.IUserRepository;

import tools.jackson.databind.ObjectMapper;
@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private IRoleRepository roleRepository;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDTO save(UserDTO dto) {
		try {
			if (dto != null) {
				UserEntity userEntity = mapper.convertValue(dto, UserEntity.class);
				String encodedPassword = passwordEncoder.encode(dto.getPassword());
				userEntity.setPassword(encodedPassword);
				Optional<RoleEntity> optionalRoleEntity = roleRepository.findById(dto.getRoleId());
				if (optionalRoleEntity.isPresent())
					userEntity.setRole(optionalRoleEntity.get());
				else {
					throw new GlobalException("");
				}
				userEntity = userRepository.save(userEntity);
				dto = null;// mapper.toDto(userEntity);
			}
		} catch (Exception e) {
			throw new GlobalException("");
		}
		return dto;
	}

	@Override
	public List<UserDTO> getAll() {
		List<UserDTO> list = null;
		try {
			List<UserEntity> users = userRepository.findAll();
//			list = users.stream().map(mapper::toDto).collect(Collectors.toList());
		} catch (Exception e) {
			throw new GlobalException("");
		}
		return list;
	}

	@Override
	public UserDTO getById(Long id) {
		UserDTO dto = null;
		try {
			Optional<UserEntity> optional = userRepository.findById(id);
			if (optional.isPresent()) {
				UserEntity entity = optional.get();
				dto = null;// mapper.toDto(entity);
			} else {
				throw new GlobalException("");
			}
		} catch (Exception e) {
			throw new GlobalException("");
		}
		return dto;
	}

	@Override
	public void delete(Long id) {
		try {
			userRepository.deleteById(id);
		} catch (Exception e) {
			throw new GlobalException("");
		}
	}

	 @Transactional
	 @Override
	 public void changePassword(ChangePasswordDTO request) {

			UserEntity user = userRepository.findByEmail(request.getUsername()).get();
			if(user == null)
				throw new GlobalException("User not found ");

			// Validate old password
			if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {

				throw new RuntimeException("Old Password Incorrect");
			}

			// Update password
			user.setPassword(passwordEncoder.encode(request.getNewPassword()));

			// Increment token version
//			user.setTokenVersion(user.getTokenVersion() + 1);

			userRepository.save(user);

			// Delete all refresh 
			//
//			refreshTokenRepository.deleteByUsername(user.getUsername());
		}
	
}