package cn.aegiron.security.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * @author panda
 * @version 1.0
 * @ClassName SecurityUser.java
 * @Description 登录用户信息
 * @since 2026/2/8 18:19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SecurityUser implements UserDetails, CredentialsContainer {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 显示名称
     */
    private String displayName;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 是否启用
     */
    private boolean enabled = true;

    /**
     * 权限
     */
    private List<GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void eraseCredentials() {
        this.password = null;
    }
}
