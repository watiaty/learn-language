package by.waitaty.learnlanguage.controller;

import by.waitaty.learnlanguage.dto.request.AuthenticationRequest;
import by.waitaty.learnlanguage.dto.request.RefreshTokenRequest;
import by.waitaty.learnlanguage.dto.request.RegisterRequest;
import by.waitaty.learnlanguage.dto.response.AuthenticationResponse;
import by.waitaty.learnlanguage.service.impl.AuthService;
import by.waitaty.learnlanguage.service.impl.LogoutService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {
    private final AuthService authService;
    private final LogoutService logoutService;

    @PostMapping("/signup")
    public ResponseEntity<AuthenticationResponse> signUp(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(authService.signUp(registerRequest));
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthenticationResponse> signIn(@RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(authService.signIn(authenticationRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return ResponseEntity.ok(authService.refreshToken(refreshTokenRequest));
    }

    @GetMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        logoutService.logout(request, response, authentication);
    }
}
