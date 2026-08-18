package com.dileep.ecommerce.ms.service;

import java.util.List;

import com.dileep.ecommerce.ms.dto.RoleDTO;

public interface IRoleService {

	public RoleDTO save(RoleDTO dto);

	public List<RoleDTO> getAll();

	public void delete(Long id);

	public RoleDTO getById(Long id);

}
