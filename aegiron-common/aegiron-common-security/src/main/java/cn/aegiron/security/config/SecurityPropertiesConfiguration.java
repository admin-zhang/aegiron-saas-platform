package cn.aegiron.security.config;

import cn.aegiron.security.jwt.JwtProperties;
import cn.aegiron.security.properties.AppScanLoginProperties;
import cn.aegiron.security.properties.EmailLoginProperties;
import cn.aegiron.security.properties.SmsLoginProperties;
import cn.aegiron.security.properties.SocialLoginProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 安全模块配置属性统一启用
 *
 * @author panda
 * @since 2026/2/26
 */
@Configuration
@EnableConfigurationProperties({
        OAuth2ServerProperties.class,
        JwtProperties.class,
        SmsLoginProperties.class,
        EmailLoginProperties.class,
        SocialLoginProperties.class,
        AppScanLoginProperties.class
})
public class SecurityPropertiesConfiguration {
}
