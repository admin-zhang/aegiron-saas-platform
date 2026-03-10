package cn.aegiron.seata.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

import io.seata.spring.annotation.datasource.EnableAutoDataSourceProxy;

/**
 * Seata 分布式事务自动配置
 * 通过 aegiron.seata.enabled=true 启用
 *
 * @author panda
 * @since 2026/2/26
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(name = "aegiron.seata.enabled", havingValue = "true")
@EnableAutoDataSourceProxy(useJdkProxy = true)
public class SeataAutoConfiguration {
}
