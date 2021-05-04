package com.example.demo.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignUpRequest {
    private String login;
    private String password;
    private String repass;
}
