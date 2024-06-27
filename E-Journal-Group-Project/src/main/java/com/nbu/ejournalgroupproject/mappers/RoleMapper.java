package com.nbu.ejournalgroupproject.mappers;

import com.nbu.ejournalgroupproject.dto.RoleDto;
import com.nbu.ejournalgroupproject.model.user.Role;
import com.nbu.ejournalgroupproject.model.user.User;
import com.nbu.ejournalgroupproject.repository.RoleRepository;
import com.nbu.ejournalgroupproject.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class RoleMapper {

    private final UserRepository userRepository;

    public RoleDto toDto(Role role) {

        RoleDto roleDto = new RoleDto();

        roleDto.setId(role.getId());
        roleDto.setAuthority(role.getAuthority());

        if (role.getUsers() != null) {
            roleDto.setUserIds(role.getUsers().stream()
                    .map(User::getId)
                    .collect(Collectors.toSet()));
        }

        return roleDto;
    }

    public Role toEntity(RoleDto roleDto) {
        Role role = new Role();

        role.setAuthority(roleDto.getAuthority());

        if (roleDto.getUserIds() != null) {
            role.setUsers(roleDto.getUserIds().stream()
                    .map(id -> userRepository.findById(id)
                            .orElseThrow(() -> new EntityNotFoundException("No Teacher found with id: " + id)))
                    .collect(Collectors.toSet()));
        }
        return role;
    }
}
