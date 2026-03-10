package cn.aegiron.security.oauth2.password;

import cn.aegiron.security.support.AegironUserDetailsService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;

/**
 * 密码模式 Grant 配置
 * 当存在 AegironUserDetailsService 且启用密码登录时生效
 *
 * @author panda
 * @since 2026/2/26
 */
@Configuration
@ConditionalOnBean(AegironUserDetailsService.class)
@ConditionalOnProperty(name = "aegiron.security.oauth2.password-grant-enabled", havingValue = "true", matchIfMissing = true)
public class PasswordGrantConfig {

    @Bean
    public PasswordGrantAuthenticationProvider passwordGrantAuthenticationProvider(
            AegironUserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder,
            OAuth2AuthorizationService authorizationService,
            OAuth2TokenGenerator<?> tokenGenerator) {
        return new PasswordGrantAuthenticationProvider(
                userDetailsService,
                passwordEncoder,
                authorizationService,
                tokenGenerator);
    }

    @Bean
    @ConditionalOnMissingBean(PasswordEncoder.class)
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
