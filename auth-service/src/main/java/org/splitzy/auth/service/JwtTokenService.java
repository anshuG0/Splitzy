package org.splitzy.auth.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.splitzy.auth.entity.AuthUser;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class JwtTokenService {

    private final SecretKey secretKey;
    private final long accessTokenValiditySeconds;
    private final long refreshTokenValiditySeconds;
    private final RedisTemplate<String, String> redisTemplate;
    private static final String BLACKLIST_PREFIX = "BLACKLIST:";
    private static final String USER_ID_CLAIM = "USER_ID";
    private static final String USERNAME_CLAIM = "USERNAME";
    private static final String EMAIL_CLAIM = "EMAIL";
    private static final String ROLE_CLAIM = "ROLE";
    private static final String TOKEN_TYPE_CLAIM = "token_type";

    public JwtTokenService(RedisTemplate<String, String> redisTemplate, @Value("${jwt.secret}") String secret, @Value("${jwt.access-token.validity-ms:3600000}") long accessTokenValiditySeconds, @Value("${jwt.refresh-token.validity-ms:604800000}") long refreshTokenValiditySeconds, long refreshTokenValiditySeconds) {
        this.redisTemplate = redisTemplate;
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
        this.accessTokenValiditySeconds = accessTokenValiditySeconds;
        this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
    }

    public String generateAcessToken(AuthUser authUser) {
        return generateToken(authUser, accessTokenValiditySeconds, "ACCESS");
    }

    public String generateRefreshToken(AuthUser user) {
        return generateToken(user, refreshTokenValiditySeconds, "REFRESH");
    }

    //Generate JWT token with specified validity and type
    private String generateToken(AuthUser authUser, long validityMs, String tokenType) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + validityMs);

        Map<String, Object> claims = new HashMap<>();
        claims.put(USER_ID_CLAIM, authUser.getId());
        claims.put(USERNAME_CLAIM, authUser.getUsername());
        claims.put(EMAIL_CLAIM, authUser.getEmail());
        claims.put(TOKEN_TYPE_CLAIM, tokenType);
        claims.put(ROLE_CLAIM, authUser.getRole());

        return Jwts.builder().setClaims(claims).setSubject(authUser.getEmail()).setIssuedAt(now).setExpiration(expiryDate).signWith(SignatureAlgorithm.HS512, secretKey).compact();
    }

    public Long getUserIdFromToken(String token) {

    }
}