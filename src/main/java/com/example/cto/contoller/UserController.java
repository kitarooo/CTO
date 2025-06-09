package com.example.cto.contoller;


import com.example.cto.dto.request.LoginRequest;
import com.example.cto.dto.request.RegistrationRequest;
import com.example.cto.dto.response.AuthenticationResponse;
import com.example.cto.error.handler.ExceptionResponse;
import com.example.cto.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    @Operation(summary = "Регистрация для клиента", description = "Ендпоинт для регистрации!",
            responses = {
                    @ApiResponse(
                            content = @Content(mediaType = "string"),
                            responseCode = "200", description = "Good"),
                    @ApiResponse(
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponse.class)),
                            responseCode = "400", description = "User already exist exception!"
                    )
            })
    public String registration(@RequestBody RegistrationRequest request) {
        return userService.registration(request);
    }

    @PostMapping("/login")
    @Operation(summary = "Авторизация для всех пользователей", description = "Ендпоинт для авторизации и выдачи токена!",
            responses = {
                    @ApiResponse(
                            content = @Content(mediaType = "string"),
                            responseCode = "200", description = "Good"),
                    @ApiResponse(
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponse.class)),
                            responseCode = "400", description = "Incorrect Data Exception!"
                    )
            })
    public AuthenticationResponse login(@RequestBody LoginRequest request) {
        return userService.login(request);
    }
}
