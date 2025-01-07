package com.example.demo.auth;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AuthenticationResponse {

    private String token;

    }

