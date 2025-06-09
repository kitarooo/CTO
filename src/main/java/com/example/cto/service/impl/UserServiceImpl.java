package backend.microservices.testproject.service.impl;

import backend.microservices.testproject.dto.request.LoginRequest;
import backend.microservices.testproject.dto.request.RegistrationRequest;
import backend.microservices.testproject.dto.response.AuthenticationResponse;
import backend.microservices.testproject.entity.User;
import backend.microservices.testproject.entity.enums.Role;
import backend.microservices.testproject.exception.IncorrectDataException;
import backend.microservices.testproject.exception.IncorrectPasswordsException;
import backend.microservices.testproject.exception.NotFoundException;
import backend.microservices.testproject.exception.UserAlreadyExistException;
import backend.microservices.testproject.repository.UserRepository;
import backend.microservices.testproject.security.jwt.JwtService;
import backend.microservices.testproject.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    JwtService jwtService;
    ImageUploadServiceImpl imageUploadService;

    @Override
    public AuthenticationResponse login(LoginRequest request) {
        User user = userRepository.findUserByUsername(request.getUsername())
                .orElseThrow(() -> new NotFoundException("Пользователь не найден!"));

        if (passwordEncoder.matches(request.getPassword(), user.getPassword()) && user.getUsername().equals(request.getUsername())) {
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
        if (userRepository.findUserByUsername(request.getUsername()).isPresent()) {
            throw new UserAlreadyExistException("Пользователь с username: " + request.getUsername() + " уже существует!");
        }

        User user = User.builder()
                .username(request.getUsername())
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .password(passwordEncoder.encode(request.getPassword()))
                .confirmPassword(request.getConfirmPassword())
                .role(Role.ROLE_USER)
                .build();

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new IncorrectPasswordsException("Пароли не совпадают!");
        } else {
            userRepository.save(user);
            return "Регистрация прошла успешно!";
        }
    }

    @Override
    public String updateProfilePhoto(MultipartFile multipartFile, User user) {
        user.setImageUrl(imageUploadService.saveImage(multipartFile));
        userRepository.save(user);
        return "Фото профиля успешно добавлено";
    }
}
