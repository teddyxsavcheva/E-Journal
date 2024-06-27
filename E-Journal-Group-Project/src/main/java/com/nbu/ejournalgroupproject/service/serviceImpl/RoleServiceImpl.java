package com.nbu.ejournalgroupproject.service.serviceImpl;

import com.nbu.ejournalgroupproject.dto.RoleDto;
import com.nbu.ejournalgroupproject.mappers.RoleMapper;
import com.nbu.ejournalgroupproject.model.user.Role;
import com.nbu.ejournalgroupproject.repository.RoleRepository;
import com.nbu.ejournalgroupproject.repository.UserRepository;
import com.nbu.ejournalgroupproject.service.RoleService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final RoleMapper roleMapper;

    public RoleDto getRoleById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + id));
        return roleMapper.toDto(role);
    }

    public RoleDto getRoleByAuthority(String authority) {
        Role role = roleRepository.findByAuthority(authority);
        if (role == null) {
            throw new RuntimeException("Role not found with authority: " + authority);
        }
        return roleMapper.toDto(role);
    }

    public RoleDto createRole(RoleDto roleDto) {
        Role role = roleMapper.toEntity(roleDto);
        role = roleRepository.save(role);
        return roleMapper.toDto(role);
    }

    public RoleDto updateRole(Long id, RoleDto roleDto) {
        Role existingRole = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + id));

        existingRole.setAuthority(roleDto.getAuthority());
        existingRole.setUsers(roleDto.getUserIds().stream()
                .map(userId -> userRepository.findById(userId)
                        .orElseThrow(() -> new RuntimeException("User not found with id: " + userId)))
                .collect(Collectors.toSet()));

        Role updatedRole = roleRepository.save(existingRole);
        return roleMapper.toDto(updatedRole);
    }

    public void deleteRole(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + id));
        roleRepository.delete(role);
    }

    @Override
    public List<RoleDto> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream()
                .map(roleMapper::toDto)
                .collect(Collectors.toList());
    }

}
