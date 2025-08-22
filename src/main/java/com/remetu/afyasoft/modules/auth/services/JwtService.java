package com.remetu.afyasoft.modules.auth.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;

@Service
public class JwtService {
    @Autowired
    private JwtEncoder jwtEncoder;
    @Autowired
    private JwtDecoder jwtDecoder;
    @Value("${jwt.key}")
    private String jwtKey;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public String generateRefreshToken(String token) {
        return generateAccessToken(getSubject(token));
    }

    public String generateAccessToken(String username) {
        return generateToken(username, Instant.now().plus(30, ChronoUnit.MINUTES));
    }

    public String generateToken(String username, Instant expiredAt) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet
                .builder()
                .issuer("self")
                .subject(username)
                .issuedAt(now)
                .expiresAt(expiredAt)
                .build();
        JwtEncoderParameters encoderParameters = JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS256).build(), claims);
        return jwtEncoder.encode(encoderParameters).getTokenValue();
    }

    public Instant getExpirationTime(String token) {
        return (Instant) getAllClaims(token).get("exp");
    }

    public String getSubject(String token) {
        return (String) getAllClaims(token).get("sub");
    }

    public boolean isExpired(String token) {
        return getExpirationTime(token).isBefore(Instant.now());
    }

    public Map<String, Object> getAllClaims(String token) {
        return jwtDecoder.decode(token).getClaims();
    }

    public void revokeToken(String token) {
        long expire = getExpirationTime(token).toEpochMilli();
        long remains = expire - Instant.now().toEpochMilli();

        redisTemplate.opsForValue().set(token, "blacklistedTokens", Duration.ofMillis(remains));
    }

    public boolean isTokenRevoked(String token) {
        return redisTemplate.hasKey(token);
    }
}
