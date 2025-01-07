package com.example.demo.auth;

import lombok.*;

@Setter
@Getter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

    private String email;

    private String password;


}
