package com.nbu.ejournalgroupproject.mappers;

import com.nbu.ejournalgroupproject.dto.UserDto;
import com.nbu.ejournalgroupproject.model.user.Role;
import com.nbu.ejournalgroupproject.model.user.User;
import com.nbu.ejournalgroupproject.repository.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserMapper {

    private final RoleRepository roleRepository;

    public UserDto toDto(User user) {

        UserDto userDto = new UserDto();

        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        userDto.setAccountNonExpired(user.isAccountNonExpired());
        userDto.setAccountNonLocked(user.isAccountNonLocked());
        userDto.setCredentialsNonExpired(user.isCredentialsNonExpired());
        userDto.setEnabled(user.isEnabled());

        if (user.getAuthorities() != null) {
            userDto.setRoleIds(user.getAuthorities().stream()
                    .map(Role::getId)
                    .collect(Collectors.toSet()));
        }

        return userDto;
    }

    public User toEntity(UserDto userDto) {

        User user = new User();

        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setAccountNonExpired(userDto.isAccountNonExpired());
        user.setAccountNonLocked(userDto.isAccountNonLocked());
        user.setCredentialsNonExpired(userDto.isCredentialsNonExpired());
        user.setEnabled(userDto.isEnabled());

        if (userDto.getRoleIds() != null) {
            user.setAuthorities(userDto.getRoleIds().stream()
                    .map(id -> roleRepository.findById(id)
                            .orElseThrow(() -> new EntityNotFoundException("No Teacher found with id: " + id)))
                    .collect(Collectors.toSet()));
        }

        return user;
    }

}
