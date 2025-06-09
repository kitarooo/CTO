package com.example.cto.service.impl;


import com.example.cto.dto.request.LoginRequest;
import com.example.cto.dto.request.RegistrationRequest;
import com.example.cto.dto.response.AuthenticationResponse;
import com.example.cto.error.exceptions.IncorrectDataException;
import com.example.cto.error.exceptions.NotFoundException;
import com.example.cto.error.exceptions.UserAlreadyExistException;
import com.example.cto.model.entity.User;
import com.example.cto.model.enums.Role;
import com.example.cto.repository.UserRepository;
import com.example.cto.security.jwt.JwtService;
import com.example.cto.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public AuthenticationResponse login(LoginRequest request) {
        User user = userRepository.findUserByEmail(request.getEmail())
                .orElseThrow(() -> new NotFoundException("Пользователь не найден!"));

        if (passwordEncoder.matches(request.getPassword(), user.getPasswordHash()) && user.getEmail().equals(request.getEmail())) {
            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                    .accessToken(jwtToken)
                    .build();
        } else {
            throw new IncorrectDataException("Данные введены неправильно!");
        }
    }

    @Override
    public String registration(RegistrationRequest request) {
        if (userRepository.findUserByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistException("Пользователь с email: " + request.getEmail() + " уже существует!");
        }

        User user = User.builder()
                .email(request.getEmail())
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .password(request.getPassword().toCharArray())
                .role(Role.ROLE_USER)
                .build();
        userRepository.save(user);

        return "Регистрация прошла успешно!";
    }

}
