package com.nbu.ejournalgroupproject.service.serviceImpl;

import com.nbu.ejournalgroupproject.dto.UserDto;
import com.nbu.ejournalgroupproject.mappers.UserMapper;
import com.nbu.ejournalgroupproject.repository.UserRepository;
import com.nbu.ejournalgroupproject.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto getUserByUsername(String username) {
        return userMapper.toDto(userRepository.getUserByUsername(username));
    }

}
