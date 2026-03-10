package cn.aegiron.security.support;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 用户详情服务接口，业务模块实现后供密码/验证码等登录方式使用。
 *
 * @author panda
 * @since 2026/2/26
 */
public interface AegironUserDetailsService extends UserDetailsService {

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    /** 按手机号加载用户（验证码登录时实现） */
    default UserDetails loadUserByPhone(String phone) {
        throw new UnsupportedOperationException("请实现 loadUserByPhone 并配置 aegiron.security.login.sms.enabled=true");
    }

    /** 按邮箱加载用户（验证码登录时实现） */
    default UserDetails loadUserByEmail(String email) {
        throw new UnsupportedOperationException("请实现 loadUserByEmail 并配置 aegiron.security.login.email.enabled=true");
    }
}
