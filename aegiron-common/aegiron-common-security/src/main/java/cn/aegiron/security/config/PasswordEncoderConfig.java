package cn.aegiron.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author panda
 * @version 1.0
 * @ClassName PasswordEncoderConfig.java
 * @Description 密码编码器配置类
 * @since 2026/2/8 18:17
 */
public class PasswordEncoderConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
