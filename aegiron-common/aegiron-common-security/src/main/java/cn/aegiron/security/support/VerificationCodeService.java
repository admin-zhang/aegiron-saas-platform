package cn.aegiron.security.support;

/**
 * 验证码服务接口，业务模块实现后供手机/邮箱验证码登录使用。
 *
 * @author panda
 * @since 2026/2/26
 */
public interface VerificationCodeService {

    boolean sendSmsCode(String phone, String code);

    boolean sendEmailCode(String email, String code);

    boolean verifySmsCode(String phone, String code);

    boolean verifyEmailCode(String email, String code);

    default String generateCode() {
        return String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
    }
}
