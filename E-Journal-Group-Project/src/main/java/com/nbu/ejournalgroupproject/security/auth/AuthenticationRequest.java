package com.nbu.ejournalgroupproject.security.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
// The request body for authentication.
public class AuthenticationRequest {

    private String email;
    private String password;

}
