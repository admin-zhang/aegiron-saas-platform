package cn.aegiron.security.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * JWT 配置属性（自定义 JWT 签发/校验，如资源端或兼容场景）
 *
 * @author panda
 * @since 2026/2/26
 */
@Data
@ConfigurationProperties(prefix = "aegiron.security.jwt")
public class JwtProperties {

    /** 签名密钥 */
    private String secretKey = "";

    /** Token 过期时间（秒） */
    private long accessTokenExpireSeconds = 7200;

    /** 请求头名称 */
    private String header = "Authorization";

    /** Token 前缀 */
    private String tokenPrefix = "Bearer ";

    /** 签发人（建议为 issuer URL） */
    private String issuer = "http://localhost:8082";
}
