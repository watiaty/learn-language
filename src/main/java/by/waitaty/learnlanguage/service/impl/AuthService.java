package by.waitaty.learnlanguage.service.impl;

import by.waitaty.learnlanguage.dto.Mapper;
import by.waitaty.learnlanguage.dto.request.AuthenticationRequest;
import by.waitaty.learnlanguage.dto.request.RefreshTokenRequest;
import by.waitaty.learnlanguage.dto.request.RegisterRequest;
import by.waitaty.learnlanguage.dto.response.AuthenticationResponse;
import by.waitaty.learnlanguage.entity.Language;
import by.waitaty.learnlanguage.entity.Role;
import by.waitaty.learnlanguage.entity.User;
import by.waitaty.learnlanguage.exception.NotValidAccessTokenException;
import by.waitaty.learnlanguage.repository.UserRepository;
import by.waitaty.learnlanguage.security.JWTUtils;
import by.waitaty.learnlanguage.token.Token;
import by.waitaty.learnlanguage.token.TokenRepository;
import by.waitaty.learnlanguage.token.TokenType;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final JWTUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private Mapper mapper;

    public AuthenticationResponse signUp(RegisterRequest registerRequest) {
        List<Language> learningLang = new ArrayList<>();
        learningLang.add(Language.EN);
        learningLang.add(Language.PL);
        User user = User.builder()
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .firstName(registerRequest.getFirstname())
                .lastName(registerRequest.getLastname())
                .learningLang(learningLang)
                .nativeLang(Language.RU)
                .role(Role.USER)
                .build();
        var savedUser = userRepository.save(user);
        var jwtToken = jwtUtils.generateToken(user);
        var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);

        saveUserToken(savedUser, jwtToken);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse signIn(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(),
                authenticationRequest.getPassword()
        ));

        var user = userRepository.findByUsername(authenticationRequest.getUsername()).orElseThrow();
        var jwtToken = jwtUtils.generateToken(user);
        var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);

        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .user(mapper.userToUserDtoResponse(user))
                .build();
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String username = jwtUtils.extractUsername(refreshTokenRequest.getToken());
        User user = userRepository.findByUsername(username).orElseThrow();

        if (!jwtUtils.isTokenValid(refreshTokenRequest.getToken(), user)) throw new NotValidAccessTokenException();

        var jwtToken = jwtUtils.generateToken(user);

        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshTokenRequest.getRefreshToken())
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
}
