package cn.aegiron.security.jwt;

/**
 * @author panda
 * @version 1.0
 * @ClassName TokenBlacklistService.java
 * @Description Token黑名单服务
 * @since 2026/2/8 18:22
 */
public interface TokenBlacklistService {


    /**
     * 将Token加入黑名单
     *
     * @param token Token
     * @param expireSeconds 过期时间（秒）
     */
    void addToBlacklist(String token, long expireSeconds);

    /**
     * 检查Token是否在黑名单中
     *
     * @param token Token
     * @return true表示在黑名单中，false表示不在
     */
    boolean isBlacklisted(String token);

    /**
     * 从黑名单中移除Token
     *
     * @param token Token
     */
    void removeFromBlacklist(String token);
}
