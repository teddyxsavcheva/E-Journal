package com.nbu.ejournalgroupproject.security.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
// the request body for registering a new user.
public class RegisterRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String password;

}
