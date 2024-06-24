package com.nbu.ejournalgroupproject.auth;

import com.nbu.ejournalgroupproject.user.Role;
import com.nbu.ejournalgroupproject.user.User;
import com.nbu.ejournalgroupproject.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationResponse registerAdmin(RegisterRequest request) {

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ADMINISTRATOR)
                .build();

    }


    public AuthenticationResponse authenticateAdmin(AuthenticationRequest request) {



    }


}
