package cn.aegiron.security.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * App 扫码登录配置
 *
 * <p>说明：
 * <ul>
 *   <li>字段在这里给出的值是 <b>默认值</b>（例如 enabled 默认 false）。</li>
 *   <li>当在配置文件中写了同名属性时，例如
 *       {@code aegiron.security.login.app-scan.enabled=true}，
 *       就会 <b>自动覆盖</b> 这里的默认值。</li>
 *   <li>不写配置时，代码中读取到的就是这些默认值。</li>
 * </ul>
 * 这样可以集中管理默认配置，又方便协同开发时查看含义。
 *
 * @author panda
 * @since 2026/2/26
 */
@Data
@ConfigurationProperties(prefix = "aegiron.security.login.app-scan")
public class AppScanLoginProperties {

    /**
     * 是否启用 App 扫码登录
     * <p>默认 false，可通过配置 {@code aegiron.security.login.app-scan.enabled=true} 覆盖。</p>
     */
    private boolean enabled = false;

    /**
     * 扫码票据有效期（秒）
     * <p>默认 300 秒，可在配置中通过 {@code aegiron.security.login.app-scan.ticket-expire-seconds} 覆盖。</p>
     */
    private int ticketExpireSeconds = 300;

    /**
     * 二维码图片尺寸（像素）
     * <p>默认 200，可在配置中通过 {@code aegiron.security.login.app-scan.qr-code-size} 覆盖。</p>
     */
    private int qrCodeSize = 200;
}
