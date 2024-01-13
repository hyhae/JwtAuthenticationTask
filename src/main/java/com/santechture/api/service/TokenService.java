package com.santechture.api.service;

import com.santechture.api.configuration.JwtService;
import com.santechture.api.entity.Admin;
import com.santechture.api.entity.Token;
import com.santechture.api.enums.TokenType;
import com.santechture.api.repository.TokenRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TokenService {
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;

    public TokenService(TokenRepository tokenRepository, JwtService jwtService) {
        this.tokenRepository = tokenRepository;
        this.jwtService = jwtService;
    }

    public Token saveNewTokenForAdmin(Admin admin) {
        revokeAllAdminTokens(admin);
        Token token = new Token();
        token.setAdmin(admin);
        token.setTokenType(TokenType.BEARER);
        token.setExpired(false);
        token.setRevoked(false);
        token.setToken(jwtService.generateToken(admin));
        return tokenRepository.save(token);
    }

    public void revokeAllAdminTokens(Admin admin) {
        List<Token> validUserTokens = tokenRepository.findAllValidTokensByAdmin(admin.getAdminId());
        if (validUserTokens.isEmpty()) {
            return;
        }
        for (Token token : validUserTokens
        ) {
            token.setRevoked(true);
            token.setExpired(true);
        }
        tokenRepository.saveAll(validUserTokens);
    }

    public List<Token> findAllValidTokensByAdmin(Admin admin) {
        return tokenRepository.findAllValidTokensByAdmin(admin.getAdminId());
    }
}
