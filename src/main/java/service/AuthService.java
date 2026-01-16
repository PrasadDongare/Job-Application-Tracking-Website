package service;

import domain.dto.auth.JwtResponse;
import domain.dto.auth.LoginRequest;
import domain.dto.auth.RegisterRequest;

public interface AuthService {
    JwtResponse login(LoginRequest request);
    void register(RegisterRequest request);
}