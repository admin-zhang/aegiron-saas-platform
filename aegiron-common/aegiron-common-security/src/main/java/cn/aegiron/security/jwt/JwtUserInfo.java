package cn.aegiron.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

/**
 * @author panda
 * @version 1.0
 * @ClassName JwtUserInfo.java
 * @Description JWT用户信息
 * @since 2026/2/8 18:21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtUserInfo {

    private Long userId;

    private String username;

    private String displayName;

    private List<String> roles;

    private String tenantId;

    private Instant expiresAt;
}
