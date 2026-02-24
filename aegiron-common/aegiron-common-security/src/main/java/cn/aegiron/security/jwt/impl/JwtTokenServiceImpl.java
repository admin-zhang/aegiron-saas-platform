package cn.aegiron.security.jwt.impl;

import cn.aegiron.security.config.JwtProperties;
import cn.aegiron.security.jwt.JwtTokenService;
import cn.aegiron.security.jwt.JwtUserInfo;
import cn.aegiron.security.jwt.TokenBlacklistService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.*;

/**
 * @author panda
 * @version 1.0
 * @ClassName JwtTokenServiceImpl.java
 * @Description JWT Token服务实现类
 * @since 2026/2/8 18:22
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class JwtTokenServiceImpl implements JwtTokenService {

    private final JwtProperties jwtProperties;

    private final TokenBlacklistService tokenBlacklistService;

    @Override
    public String generateToken(JwtUserInfo userInfo) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("uid", userInfo.getUserId());
        claims.put("displayName", userInfo.getDisplayName());
        claims.put("roles", userInfo.getRoles());
        if (userInfo.getTenantId() != null) {
            claims.put("tenantId", userInfo.getTenantId());
        }

        Instant now = Instant.now();
        Instant expireAt = now.plusSeconds(jwtProperties.getAccessTokenExpireSeconds());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userInfo.getUsername())
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expireAt))
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public JwtUserInfo parseToken(String token) {
        // 先检查黑名单
        if (tokenBlacklistService.isBlacklisted(token)) {
            throw new RuntimeException("Token已被强制下线");
        }

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        List<String> roles = Optional.ofNullable((List<?>) claims.get("roles"))
                .map(list -> list.stream().map(Object::toString).toList())
                .orElse(List.of());

        Number userId = claims.get("uid", Number.class);
        String tenantId = claims.get("tenantId", String.class);

        return JwtUserInfo.builder()
                .userId(userId == null ? null : userId.longValue())
                .username(claims.getSubject())
                .displayName(claims.get("displayName", String.class))
                .roles(roles)
                .tenantId(tenantId)
                .expiresAt(claims.getExpiration().toInstant())
                .build();
    }

    @Override
    public boolean validateToken(String token) {
        try {
            // 检查黑名单
            if (tokenBlacklistService.isBlacklisted(token)) {
                return false;
            }

            // 验证Token签名和过期时间
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return claims.getExpiration().after(new Date());
        } catch (ExpiredJwtException e) {
            log.debug("Token已过期: {}", e.getMessage());
            return false;
        } catch (Exception e) {
            log.debug("Token验证失败: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public long getTokenRemainingExpireSeconds(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            Date expiration = claims.getExpiration();
            long remainingSeconds = (expiration.getTime() - System.currentTimeMillis()) / 1000;
            return Math.max(0, remainingSeconds);
        } catch (Exception e) {
            log.debug("解析Token过期时间失败: {}", e.getMessage());
            return 0;
        }
    }

    @Override
    public long getAccessTokenExpireSeconds() {
        return jwtProperties.getAccessTokenExpireSeconds();
    }

    private SecretKey getSecretKey() {
        byte[] keyBytes;
        if (jwtProperties.getSecretKey().length() > 44) {
            // assume plain text secret
            keyBytes = jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8);
        } else {
            keyBytes = Decoders.BASE64.decode(jwtProperties.getSecretKey());
        }
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
