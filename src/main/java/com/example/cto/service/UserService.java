package com.example.cto.service;


import com.example.cto.dto.request.LoginRequest;
import com.example.cto.dto.request.RegistrationRequest;
import com.example.cto.dto.response.AuthenticationResponse;

public interface UserService {

    AuthenticationResponse login(LoginRequest request);
    String registration(RegistrationRequest request);

}
