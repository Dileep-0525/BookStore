package com.dileep.ecommerce.ms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dileep.ecommerce.ms.dto.LoginRequestDTO;
import com.dileep.ecommerce.ms.dto.LoginResponseDTO;
import com.dileep.ecommerce.ms.entity.UserEntity;
import com.dileep.ecommerce.ms.exceptions.AuthenticationException;
import com.dileep.ecommerce.ms.exceptions.GlobalException;
import com.dileep.ecommerce.ms.repository.IUserRepository;
import com.dileep.ecommerce.ms.util.JwtUtil;

@Service
public class LoginServiceImpl implements ILoginService {

	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Override
	public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
		LoginResponseDTO responseDTO = new LoginResponseDTO();
		try {
			UserEntity userEntity = userRepository.findByEmail(loginRequestDTO.getUsername()).get();
			if(userEntity==null) {
				throw new AuthenticationException("Invalid username or password");
			}else {
				if(encoder.matches(loginRequestDTO.getPassword(), userEntity.getPassword())) {
					String token = jwtUtil.generateToken(userEntity);
					responseDTO.setEmail(loginRequestDTO.getUsername());
					responseDTO.setToken(token);
					responseDTO.setId(userEntity.getRole().getId());
//					responseDTO.setOrganizationId(userEntity.getOrganization().getId());
//					responseDTO.setOrganizationName(userEntity.getOrganization().getName());
				}else {
					throw new AuthenticationException("Invalid username or password");
				}
			}
		} catch (AuthenticationException e) {
			throw new AuthenticationException(e.getMessage());
		}catch (Exception e) {
			throw new GlobalException(e.getMessage());
		}
		return responseDTO;
	}

	
}
