package com.nbu.ejournalgroupproject.service.serviceImpl;

import com.nbu.ejournalgroupproject.dto.UserDto;
import com.nbu.ejournalgroupproject.mappers.UserMapper;
import com.nbu.ejournalgroupproject.model.user.User;
import com.nbu.ejournalgroupproject.model.user.UserPrinciple;
import com.nbu.ejournalgroupproject.service.UserPrincipleService;
import com.nbu.ejournalgroupproject.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserPrincipleServiceImpl implements UserPrincipleService {

    private final UserService userService;
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto userDto = this.userService.getUserByUsername(username);
        if (userDto == null) {
            throw new UsernameNotFoundException(username);
        }

        // Use UserMapper to map UserDto to User entity
        User user = userMapper.toEntity(userDto);

        return new UserPrinciple(user);
    }

}
