package cn.aegiron.security.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * 第三方登录配置
 *
 * <p>默认值说明：</p>
 * <ul>
 *   <li>{@code enabled=false}：不开启统一的第三方登录能力。</li>
 *   <li>{@code providers} 中每个平台也有自己的 {@code enabled} 标志和 client 配置。</li>
 *   <li>配置前缀为 {@code aegiron.security.login.social.*}，写在配置文件中的值会覆盖这里的默认值。</li>
 * </ul>
 *
 * @author panda
 * @since 2026/2/26
 */
@Data
@ConfigurationProperties(prefix = "aegiron.security.login.social")
public class SocialLoginProperties {

    private boolean enabled = false;
    private Map<String, ProviderConfig> providers = new HashMap<>();

    @Data
    public static class ProviderConfig {
        /**
         * 是否启用该第三方平台（例如 wechat、github 等）
         */
        private boolean enabled = false;
        private String clientId;
        private String clientSecret;
        private String redirectUri;
    }
}
