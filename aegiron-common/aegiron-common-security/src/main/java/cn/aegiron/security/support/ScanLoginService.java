package cn.aegiron.security.support;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * App 扫码登录服务接口，业务模块实现后供 PC 扫码登录使用。
 *
 * @author panda
 * @since 2026/2/26
 */
public interface ScanLoginService {

    enum ScanStatus { PENDING, SCANNED, CONFIRMED, EXPIRED, CANCELLED }

    ScanTicket createScanTicket();

    ScanStatus getScanStatus(String ticket);

    boolean confirmScan(String ticket, UserDetails user);

    void cancelScan(String ticket);

    record ScanTicket(String ticket, String qrCodeUrl, long expireSeconds) {}
}
