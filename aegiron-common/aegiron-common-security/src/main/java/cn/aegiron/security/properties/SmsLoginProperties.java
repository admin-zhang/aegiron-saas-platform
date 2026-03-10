package cn.aegiron.security.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 手机验证码登录配置
 *
 * <p>默认值说明：</p>
 * <ul>
 *   <li>{@code enabled=false}：不开启手机验证码登录。</li>
 *   <li>{@code codeExpireSeconds=300}、{@code codeLength=6}：验证码默认 5 分钟、6 位。</li>
 *   <li>在配置文件中通过 {@code aegiron.security.login.sms.*} 设置时，会自动覆盖这些默认值。</li>
 * </ul>
 *
 * @author panda
 * @since 2026/2/26
 */
@Data
@ConfigurationProperties(prefix = "aegiron.security.login.sms")
public class SmsLoginProperties {

    /**
     * 是否启用手机验证码登录
     */
    private boolean enabled = false;

    /**
     * 验证码有效期（秒）
     */
    private int codeExpireSeconds = 300;

    /**
     * 验证码长度
     */
    private int codeLength = 6;
}
