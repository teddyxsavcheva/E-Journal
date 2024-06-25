package com.nbu.ejournalgroupproject.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
// the response body for authentication, containing a JWT token.
public class AuthenticationResponse {

    private String token;

}
