package com.nbu.ejournalgroupproject.service;

import com.nbu.ejournalgroupproject.dto.RoleDto;

import java.util.List;

public interface RoleService {

    RoleDto getRoleById(Long id);

    RoleDto getRoleByAuthority(String authority);

    RoleDto createRole(RoleDto roleDto);

    RoleDto updateRole(Long id, RoleDto roleDto);

    void deleteRole(Long id);

    List<RoleDto> getAllRoles();

}
