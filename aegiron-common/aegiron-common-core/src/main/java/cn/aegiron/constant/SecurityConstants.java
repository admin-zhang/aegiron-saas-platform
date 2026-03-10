package cn.aegiron.constant;

/**
 * 安全常量类
 *
 * @author panda
 * @since 2026/2/26
 */
public interface SecurityConstants {

    /**
     * 角色前缀
     */
    String ROLE = "ROLE_";

    /**
     * 项目前缀
     */
    String PROJECT_PREFIX = "aegiron";

    /**
     * 内部调用标识
     */
    String FROM_IN = "Y";

    /**
     * 来源标识
     */
    String FROM = "from";

    /**
     * 请求header - 内部调用
     */
    String HEADER_FROM_IN = FROM + "=" + FROM_IN;

    /**
     * 默认登录URL
     */
    String OAUTH_TOKEN_URL = "/oauth2/token";

    /**
     * grant_type - 刷新Token
     */
    String REFRESH_TOKEN = "refresh_token";

    /**
     * {bcrypt} 加密的特征码
     */
    String BCRYPT = "{bcrypt}";

    /**
     * {noop} 加密的特征码
     */
    String NOOP = "{noop}";

    /**
     * 用户名
     */
    String USERNAME = "username";

    /**
     * 用户信息
     */
    String DETAILS_USER = "user_info";

    /**
     * 验证码有效期,默认 60秒
     */
    long CODE_TIME = 60;

    /**
     * 验证码长度
     */
    String CODE_SIZE = "6";

    /**
     * 客户端模式
     */
    String CLIENT_CREDENTIALS = "client_credentials";

    /**
     * 客户端ID
     */
    String CLIENT_ID = "clientId";

    /**
     * 短信登录 参数名称
     */
    String SMS_PARAMETER_NAME = "phone";

    /**
     * Authorization Header
     */
    String AUTHORIZATION = "Authorization";

    /**
     * Bearer 前缀
     */
    String BEARER_PREFIX = "Bearer ";
}
