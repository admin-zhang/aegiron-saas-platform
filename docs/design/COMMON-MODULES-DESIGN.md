## Aegiron Common 模块设计说明

本设计文档用于约定 `aegiron-common` 各子模块的职责、依赖关系以及在业务模块中的使用方式，方便后续维护与协同开发。

---

## 一、总体原则

- **按需依赖**：业务模块根据需要引入对应的 common 子模块，而不是“一把梭”全引。
- **core 不被下游强依赖**：`common-database`、`common-excel`、`common-redis`、`common-security`、`common-seata` 本身都不依赖 `common-core`，避免隐式传递；需要统一返回体、异常等时，由业务模块自己显式引入 `common-core`。
- **可配置启用**：安全模块的各类登录方式、多租户 / Seata 等都通过配置开关控制，默认关闭，按需开启。
- **清晰边界**：每个模块只做“一件事”，避免出现跨层级、跨领域的耦合。

---

## 二、模块清单与职责

```text
aegiron-common/
├── aegiron-common-core        # 核心通用：返回结果、错误码、全局异常、通用工具
├── aegiron-common-database    # 数据库：MyBatis-Plus、分页、审计字段填充等
├── aegiron-common-excel       # Excel：基于 EasyExcel 的导入导出封装
├── aegiron-common-redis       # Redis：统一 RedisTemplate、RedisService 封装
├── aegiron-common-security    # 安全：OAuth2 认证服务器、多种登录方式扩展、JWT/黑名单
└── aegiron-common-seata       # 分布式事务：Seata 自动配置
```

### 2.1 aegiron-common-core（核心模块）

- 统一返回模型：`CommonResult<T>`、`IErrorCode`、`ResultCode`。
- 全局异常处理：`GlobalExceptionHandler`。
- 通用常量：`CommonConstants`、`SecurityConstants` 等。
- Web / Spring 工具：`WebUtils`、`SpringContextHolder`、`RetOps` 等。

> 建议：业务模块（auth、system 等）视需要显式依赖 `aegiron-common-core`，其他 common 子模块不强制传递 core。

### 2.2 aegiron-common-database

- 基于 MyBatis-Plus：
  - 分页插件（MySQL）。
  - 公共字段自动填充：`createTime`、`updateTime` 等。
- 数据源与连接池（Druid）基础配置。
- Mapper 扫描：`cn.aegiron.**.mapper`。

### 2.3 aegiron-common-excel

- 基于 EasyExcel 的导入导出能力：
  - `@ResponseExcel` 导出注解。
  - `@RequestExcel` 导入注解。
  - 监听器与全局异常处理。
- 支持：
  - 动态表头。
  - LocalDate/LocalDateTime 等常用类型转换。

### 2.4 aegiron-common-redis

- 统一 `RedisTemplate` 序列化策略（key 使用 String，value 使用 JSON）。
- `RedisCacheManager` 默认配置（1 天过期，可自定义）。
- `RedisService` 封装常用操作：`set/get/del/expire/incr` 等。

### 2.5 aegiron-common-security

安全模块分为几层：

- `config`：安全总配置
  - `OAuth2AuthorizationServerConfig`：Spring Authorization Server 配置，暴露 `/oauth2/token`、`/oauth2/authorize` 等端点。
  - `OAuth2ServerProperties`：OAuth2 服务端参数（issuerUri、默认 client 等）。
  - `SecurityPropertiesConfiguration`：集中启用所有 `@ConfigurationProperties`。

- `oauth2.password`：密码模式 Grant（可选启用）
  - `PasswordGrantAuthenticationToken` / `Converter` / `Provider` / `Config`。
  - 条件：
    - 存在 `AegironUserDetailsService` 的 Bean。
    - `aegiron.security.oauth2.password-grant-enabled=true`（默认 true）。

- `jwt`：自定义 JWT 签发 / 校验（用于兼容场景或资源服务）
  - `JwtTokenService`、`JwtUserInfo`、`JwtProperties`（前缀：`aegiron.security.jwt`）。
  - `TokenBlacklistService` + `TokenBlacklistServiceImpl`（依赖 Redis）。

- `support`：需要业务实现的扩展接口
  - `AegironUserDetailsService`：加载登录用户（用户名 / 手机 / 邮箱）。
  - `VerificationCodeService`：验证码发送与校验。
  - `SocialUserService`：第三方用户绑定 / 创建。
  - `ScanLoginService`：扫码登录流程。

- `properties`：登录方式配置
  - `SmsLoginProperties`：前缀 `aegiron.security.login.sms`。
  - `EmailLoginProperties`：前缀 `aegiron.security.login.email`。
  - `SocialLoginProperties`：前缀 `aegiron.security.login.social`。
  - `AppScanLoginProperties`：前缀 `aegiron.security.login.app-scan`。

#### 2.5.1 支持的登录方式与配置

| 登录方式       | Grant Type / 流程                                | 开关配置 Key                                   |
|----------------|--------------------------------------------------|-----------------------------------------------|
| 用户名 + 密码  | 自定义 `password` Grant                          | `aegiron.security.oauth2.password-grant-enabled` |
| 手机验证码     | 自定义 `sms_code` Grant（预留）                   | `aegiron.security.login.sms.enabled`          |
| 邮箱验证码     | 自定义 `email_code` Grant（预留）                 | `aegiron.security.login.email.enabled`        |
| 授权码模式     | 标准 `authorization_code`                         | 默认开启                                      |
| 第三方 OAuth2  | 标准 `authorization_code` + social 适配           | `aegiron.security.login.social.enabled`       |
| App 扫码登录   | 授权码 + 设备码轮询流程（预留接口）               | `aegiron.security.login.app-scan.enabled`     |

> 目前已落地的是 **密码模式**，验证码 / 第三方 / 扫码 的接口和配置已经就位，后续可以按需逐步实现对应的 Grant 与业务逻辑。

#### 2.5.2 在业务模块中的典型用法

- `aegiron-auth`（认证中心）：
  - 依赖：`aegiron-common-core`、`aegiron-common-security`、`aegiron-common-redis` 等。
  - 实现 `AegironUserDetailsService`，提供用户/密码校验逻辑。
  - 根据需要，实现 `VerificationCodeService` / `SocialUserService` / `ScanLoginService`。

- 资源服务（gateway / system 等）：
  - 依赖：`aegiron-common-security`（只使用资源端 JWT 校验 / 安全配置）。

---

## 三、Seata 模块设计（aegiron-common-seata）

### 3.1 目标

为需要分布式事务的服务提供统一的 Seata 集成能力，避免每个业务重复配置。

### 3.2 依赖与自动配置

- 依赖：

```xml
<dependency>
    <groupId>cn.aegiron</groupId>
    <artifactId>aegiron-common-seata</artifactId>
</dependency>
```

- 内部依赖：`spring-cloud-starter-alibaba-seata`。
- 自动配置：
  - 当 `aegiron.seata.enabled=true` 时启用。
  - 统一加载 `seata.*` 相关配置（TC 地址、事务组等）。

### 3.3 示例配置

```yaml
aegiron:
  seata:
    enabled: true

seata:
  tx-service-group: aegiron_tx_group
  service:
    vgroup-mapping:
      aegiron_tx_group: default
  # 其他 Seata 原生配置 ...
```

---

## 四、统一配置示例

```yaml
aegiron:
  security:
    oauth2:
      issuer-uri: http://localhost:8082
      password-grant-enabled: true      # 启用密码模式
    login:
      sms:
        enabled: true                   # 启用手机验证码登录
      email:
        enabled: false                  # 默认关闭邮箱验证码
      social:
        enabled: true
        providers:
          wechat:
            enabled: true
            client-id: xxx
            client-secret: xxx
      app-scan:
        enabled: false                  # 扫码登录预留

  seata:
    enabled: false                      # 不需要分布式事务时保持 false
```

---

## 五、实施与演进建议

1. **当前已完成**
   - `aegiron-common-core`、`database`、`excel`、`redis` 模块基础能力。
   - `aegiron-common-security`：OAuth2 + 密码登录、JWT/黑名单封装、扩展接口与配置。
   - `aegiron-common-seata`：基础集成与开关设计。

2. **后续可以逐步推进的工作**
   - 在 auth 中实现验证码登录（基于 `VerificationCodeService` + 自定义 Grant）。
   - 在 auth 中接入至少一个实际的第三方平台（如微信或 GitHub），填充 `SocialUserService`。
   - 完善扫码登录的实际落地流程（`ScanLoginService`）。

3. **协同开发约定**
   - 新增通用能力时，优先考虑是否应放入某个 common 子模块。
   - 修改配置项时，务必同步更新对应的 `*Properties` 注释和本文档中的示例。
   - 避免在业务代码中硬编码“魔法常量”，应放入 `common-core` 常量或各模块的 `*Properties` 中。
