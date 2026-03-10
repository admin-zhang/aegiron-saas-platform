package cn.aegiron.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * OAuth2 认证服务器配置属性
 *
 * @author panda
 * @since 2026/2/26
 */
@Data
@ConfigurationProperties(prefix = "aegiron.security.oauth2")
public class OAuth2ServerProperties {

    /**
     * Issuer URI，如 http://localhost:8082
     */
    private String issuerUri = "http://localhost:8082";

    /**
     * 默认客户端 ID
     */
    private String defaultClientId = "aegiron-web";

    /**
     * 默认客户端密钥
     */
    private String defaultClientSecret = "secret";

    /**
     * 默认重定向 URI
     */
    private String defaultRedirectUri = "http://localhost:8888/login/oauth2/code/aegiron";

    /**
     * Access Token 过期时间（秒）
     */
    private long accessTokenExpireSeconds = 7200;

    /**
     * Refresh Token 过期时间（秒）
     */
    private long refreshTokenExpireSeconds = 86400 * 7;

    /**
     * 密码登录是否启用
     * 配置项: aegiron.security.oauth2.password-grant-enabled
     */
    private boolean passwordGrantEnabled = true;
}
