package com.dileep.ecommerce.ms.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dileep.ecommerce.ms.dto.RoleDTO;
import com.dileep.ecommerce.ms.entity.RoleEntity;
import com.dileep.ecommerce.ms.exceptions.GlobalException;
import com.dileep.ecommerce.ms.exceptions.NoDataFoundException;
import com.dileep.ecommerce.ms.repository.IRoleRepository;

import tools.jackson.databind.ObjectMapper;

@Service
public class RoleServiceImpl implements IRoleService{

	@Autowired
	private IRoleRepository roleRepository;
	
	@Autowired
	private ObjectMapper mapper;
	
	@Override
	public RoleDTO save(RoleDTO dto) {
		try {
				RoleEntity entity = mapper.convertValue(dto,RoleEntity.class);
				entity = roleRepository.save(entity);
				dto = mapper.convertValue(entity,RoleDTO.class);
				entity = null;
		} catch (Exception e) {
			throw new GlobalException();
		}
		return dto;
	}

	@Override
	public List<RoleDTO> getAll() {
		List<RoleDTO> list = null;
		try {
			list = roleRepository.findAll().stream().map(obj-> {
			return mapper.convertValue(obj, RoleDTO.class);
			}).collect(Collectors.toList());
		} catch (Exception e) {
			throw new GlobalException();
		}
		return list;
	}

	@Override
	public void delete(Long id) {
		try {
			Optional<RoleEntity> optional = roleRepository.findById(id);
			if(!optional.isPresent()) {
				throw new NoDataFoundException("Role doesn't exists with the given Id");
			}
				String name  = optional.get().getName();
				name = name+LocalDateTime.now();
				roleRepository.deleteById(id);
		} catch (Exception e) {
			throw new GlobalException();
		}
	}

	@Override
	public RoleDTO getById(Long id) {
		RoleDTO roleDTO = null;
		try {
			RoleEntity roleEntity = roleRepository.findById(id).orElseThrow(()-> new NoDataFoundException("Role not found with given id"));
			roleDTO = mapper.convertValue(roleEntity,RoleDTO.class);
		} catch (Exception e) {
			throw new GlobalException();
		}
		return roleDTO;
	}
	
}
