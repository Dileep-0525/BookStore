package com.dileep.ecommerce.ms.service;

import java.util.List;

import com.dileep.ecommerce.ms.dto.ChangePasswordDTO;
import com.dileep.ecommerce.ms.dto.UserDTO;

public interface IUserService {

	public UserDTO save(UserDTO dto);

	public List<UserDTO> getAll();

	public UserDTO getById(Long id);

	public void delete(Long id);

	public void changePassword(ChangePasswordDTO changePasswordDTO);
	
}
