package cn.aegiron.util;

import cn.aegiron.constant.CommonConstants;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.experimental.UtilityClass;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

/**
 * Web 工具类
 *
 * @author panda
 * @since 2026/2/26
 */
@UtilityClass
public class WebUtils extends org.springframework.web.util.WebUtils {

    /**
     * 租户ID Header
     */
    private static final String HEADER_TENANT_ID = CommonConstants.TENANT_ID;

    /**
     * 读取 cookie
     *
     * @param name cookie name
     * @return cookie value
     */
    public String getCookieVal(String name) {
        return getRequest()
                .map(req -> getCookieVal(req, name))
                .orElse(null);
    }

    /**
     * 读取 cookie
     *
     * @param request HttpServletRequest
     * @param name    cookie name
     * @return cookie value
     */
    public String getCookieVal(HttpServletRequest request, String name) {
        Cookie cookie = getCookie(request, name);
        return cookie != null ? cookie.getValue() : null;
    }

    /**
     * 清除指定的 cookie
     *
     * @param response HttpServletResponse
     * @param key      cookie key
     */
    public void removeCookie(HttpServletResponse response, String key) {
        setCookie(response, key, null, 0);
    }

    /**
     * 设置 cookie
     *
     * @param response        HttpServletResponse
     * @param name            cookie name
     * @param value           cookie value
     * @param maxAgeInSeconds max age
     */
    public void setCookie(HttpServletResponse response, String name, String value, int maxAgeInSeconds) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAgeInSeconds);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }

    /**
     * 获取 HttpServletRequest
     */
    public Optional<HttpServletRequest> getRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(attributes.getRequest());
    }

    /**
     * 获取 HttpServletResponse
     */
    public Optional<HttpServletResponse> getResponse() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(attributes.getResponse());
    }

    /**
     * 从 request 获取租户ID
     */
    public static String getTenantId(HttpServletRequest request) {
        return request.getHeader(HEADER_TENANT_ID);
    }

    /**
     * 获取当前请求的租户ID
     */
    public static Optional<String> getTenantId() {
        return getRequest().map(WebUtils::getTenantId);
    }
}
