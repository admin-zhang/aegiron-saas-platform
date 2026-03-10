package cn.aegiron.constant;

/**
 * 缓存KEY常量类
 *
 * @author panda
 * @since 2026/2/26
 */
public interface CacheConstants {

    /**
     * Token 缓存前缀
     */
    String PROJECT_ACCESS_TOKEN = "token:access_token";

    /**
     * Token 缓存令牌前缀
     */
    String PROJECT_TOKEN_PREFIX = "aegiron:token:";

    /**
     * 验证码前缀
     */
    String DEFAULT_CODE_KEY = "DEFAULT_CODE_KEY:";

    /**
     * 菜单信息缓存
     */
    String MENU_DETAILS = "menu_details";

    /**
     * 用户信息缓存
     */
    String USER_DETAILS = "user_details";

    /**
     * 字典信息缓存
     */
    String DICT_DETAILS = "dict_details";

    /**
     * 客户端信息
     */
    String CLIENT_DETAILS_KEY = "registered_client:details";

    /**
     * 参数缓存
     */
    String PARAMS_DETAILS = "params_details";

    /**
     * Token 黑名单前缀
     */
    String TOKEN_BLACKLIST_PREFIX = "token:blacklist:";
}
