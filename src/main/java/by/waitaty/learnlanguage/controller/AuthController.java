package by.waitaty.learnlanguage.controller;

import by.waitaty.learnlanguage.dto.request.AuthenticationRequest;
import by.waitaty.learnlanguage.dto.request.RefreshTokenRequest;
import by.waitaty.learnlanguage.dto.request.RegisterRequest;
import by.waitaty.learnlanguage.dto.response.AuthenticationResponse;
import by.waitaty.learnlanguage.service.impl.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthenticationResponse> signup(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(authService.signup(registerRequest));
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthenticationResponse> signIn(@RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(authService.signIn(authenticationRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return ResponseEntity.ok(authService.refreshToken(refreshTokenRequest));
    }
}
