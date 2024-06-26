package com.nbu.ejournalgroupproject.mappers;

import com.nbu.ejournalgroupproject.dto.UserDto;
import com.nbu.ejournalgroupproject.model.user.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserMapper {

    public UserDto toDto(User user) {

        UserDto userDto = new UserDto();

        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        userDto.setAccountNonExpired(user.isAccountNonExpired());
        userDto.setAccountNonLocked(user.isAccountNonLocked());
        userDto.setCredentialsNonExpired(user.isCredentialsNonExpired());
        userDto.setEnabled(user.isEnabled());
        userDto.setAuthorities(user.getAuthorities());

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
        user.setAuthorities(userDto.getAuthorities());

        return user;
    }

}
