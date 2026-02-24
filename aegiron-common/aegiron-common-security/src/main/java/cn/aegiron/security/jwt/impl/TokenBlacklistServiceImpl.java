package cn.aegiron.security.jwt.impl;

import cn.aegiron.redis.service.RedisService;
import cn.aegiron.security.jwt.TokenBlacklistService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author panda
 * @version 1.0
 * @ClassName TokenBlacklistServiceImpl.java
 * @Description 令牌黑名单服务实现类
 * @since 2026/2/8 18:23
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TokenBlacklistServiceImpl implements TokenBlacklistService {

    private static final String BLACKLIST_KEY_PREFIX = "aegiron:auth:blacklist:";

    private final RedisService redisService;

    @Override
    public void addToBlacklist(String token, long expireSeconds) {
        try {
            String key = BLACKLIST_KEY_PREFIX + token;
            redisService.set(key, "1", expireSeconds);
            log.debug("Token已加入黑名单: {}", token.substring(0, Math.min(20, token.length())) + "...");
        } catch (Exception e) {
            log.error("将Token加入黑名单失败", e);
        }
    }

    @Override
    public boolean isBlacklisted(String token) {
        try {
            String key = BLACKLIST_KEY_PREFIX + token;
            return Boolean.TRUE.equals(redisService.hasKey(key));
        } catch (Exception e) {
            log.error("检查Token黑名单失败", e);
            return false;
        }
    }

    @Override
    public void removeFromBlacklist(String token) {
        try {
            String key = BLACKLIST_KEY_PREFIX + token;
            redisService.del(key);
            log.debug("Token已从黑名单移除: {}", token.substring(0, Math.min(20, token.length())) + "...");
        } catch (Exception e) {
            log.error("从黑名单移除Token失败", e);
        }
    }
}
