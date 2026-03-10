package cn.aegiron.util;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * Spring 上下文持有者
 * 用于在非 Spring 管理的类中获取 Bean
 *
 * @author panda
 * @since 2026/2/26
 */
@Slf4j
@Component
@Lazy(false)
public class SpringContextHolder implements ApplicationContextAware, DisposableBean {

    private static ApplicationContext applicationContext;

    /**
     * 取得存储在静态变量中的 ApplicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext context) {
        SpringContextHolder.applicationContext = context;
    }

    /**
     * 从静态变量 applicationContext 中取得 Bean
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        return (T) applicationContext.getBean(name);
    }

    /**
     * 从静态变量 applicationContext 中取得 Bean
     */
    public static <T> T getBean(Class<T> requiredType) {
        return applicationContext.getBean(requiredType);
    }

    /**
     * 清除 SpringContextHolder 中的 ApplicationContext
     */
    public static void clearHolder() {
        if (log.isDebugEnabled()) {
            log.debug("清除 SpringContextHolder 中的 ApplicationContext: {}", applicationContext);
        }
        applicationContext = null;
    }

    /**
     * 发布事件
     */
    public static void publishEvent(ApplicationEvent event) {
        if (applicationContext != null) {
            applicationContext.publishEvent(event);
        }
    }

    @Override
    @SneakyThrows
    public void destroy() {
        SpringContextHolder.clearHolder();
    }
}
