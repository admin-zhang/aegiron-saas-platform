package cn.aegiron.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author panda
 * @version 1.0
 * @ClassName JwtProperties.java
 * @Description JWT配置属性
 * @since 2026/2/8 18:16
 */
@Data
@Component
@ConfigurationProperties(prefix = "security.jwt")
public class JwtProperties {

    /**
     * 签名密钥
     */
    private String secretKey;

    /**
     * token过期时间（秒）
     */
    private long accessTokenExpireSeconds;

    /**
     * 请求头
     */
    private String header;

    /**
     * token 前缀
     */
    private String tokenPrefix;

    /**
     * 签发人（必须是有效的 URL，例如：http://localhost:8090）
     */
    private String issuer;
}
