package com.dileep.ecommerce.ms.service;

import com.dileep.ecommerce.ms.dto.LoginRequestDTO;
import com.dileep.ecommerce.ms.dto.LoginResponseDTO;

public interface ILoginService {

	public LoginResponseDTO login(LoginRequestDTO loginRequestDTO);

}
