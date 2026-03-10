package cn.aegiron.constant;

/**
 * 通用常量类
 *
 * @author panda
 * @since 2026/2/26
 */
public interface CommonConstants {

    /**
     * 删除
     */
    String STATUS_DEL = "1";

    /**
     * 未删除
     */
    String STATUS_UN_DEL = "0";

    /**
     * 正常
     */
    String STATUS_NORMAL = "1";

    String DEL_STATUS_NORMAL = "0";

    /**
     * 锁定
     */
    String STATUS_LOCK = "2";

    /**
     * 菜单树根节点
     */
    Long MENU_TREE_ROOT_ID = -1L;

    Long DEPT_TREE_ROOT_ID = 0L;

    /**
     * 菜单
     */
    String MENU = "0";

    /**
     * 编码
     */
    String UTF8 = "UTF-8";

    /**
     * JSON 资源
     */
    String CONTENT_TYPE = "application/json; charset=utf-8";

    /**
     * 成功标记
     */
    String SUCCESS = "200";

    /**
     * 失败标记
     */
    String FAIL = "500";

    /**
     * 当前页
     */
    String CURRENT = "current";

    /**
     * size
     */
    String SIZE = "size";

    /**
     * 请求开始时间
     */
    String REQUEST_START_TIME = "REQUEST-START-TIME";

    /**
     * 租户ID Header
     */
    String TENANT_ID = "Tenant-Id";

    /**
     * 默认租户ID
     */
    String DEFAULT_TENANT_ID = "default_tenant";
}
