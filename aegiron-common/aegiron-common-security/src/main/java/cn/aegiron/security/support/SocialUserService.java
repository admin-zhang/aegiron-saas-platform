package cn.aegiron.security.support;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * 第三方社交用户服务接口，业务模块实现后供微信/GitHub 等登录使用。
 *
 * @author panda
 * @since 2026/2/26
 */
public interface SocialUserService {

    enum Provider { WECHAT, GITHUB, ALIPAY, GOOGLE, QQ }

    UserDetails loadOrCreateUser(Provider provider, String openId, String unionId,
                                 String nickname, String avatarUrl);
}
