package cn.aegiron.security.jwt;

/**
 * @author panda
 * @version 1.0
 * @ClassName JwtTokenService.java
 * @Description JWT Token服务
 * @since 2026/2/8 18:20
 */
public interface JwtTokenService {


    /**
     * 生成访问令牌
     */
    String generateToken(JwtUserInfo userInfo);

    /**
     * 解析访问令牌
     */
    JwtUserInfo parseToken(String token);

    /**
     * 验证Token是否有效（包括黑名单检查）
     *
     * @param token Token
     * @return true表示有效，false表示无效
     */
    boolean validateToken(String token);

    /**
     * 获取Token剩余过期时间（秒）
     *
     * @param token Token
     * @return 剩余过期时间（秒），如果Token无效返回0
     */
    long getTokenRemainingExpireSeconds(String token);

    /**
     * token 过期时间（秒）
     */
    long getAccessTokenExpireSeconds();
}
