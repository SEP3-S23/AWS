package com.awsServer.security.auth;

import com.awsServer.security.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String fullName;
    private String email;
    private String userName;
    private String password;
    private String birthDate;
    private Role role;
}
