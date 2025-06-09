package backend.microservices.testproject.service;

import backend.microservices.testproject.dto.request.LoginRequest;
import backend.microservices.testproject.dto.request.RegistrationRequest;
import backend.microservices.testproject.dto.response.AuthenticationResponse;
import backend.microservices.testproject.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {
    AuthenticationResponse login(LoginRequest request);
    String registration(RegistrationRequest request);
    String updateProfilePhoto(MultipartFile multipartFile, User user) throws IOException;
}
