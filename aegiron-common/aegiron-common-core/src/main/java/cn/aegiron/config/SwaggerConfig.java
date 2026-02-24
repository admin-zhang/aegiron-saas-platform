package cn.aegiron.config;

import cn.hutool.core.util.RandomUtil;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.customizers.GlobalOpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author panda
 * @version 1.0
 * @ClassName SwaggerConfig.java
 * @Description Swagger配置类
 * @since 2026/2/8 17:21
 */

@Configuration
public class SwaggerConfig {

    @Bean
    public GlobalOpenApiCustomizer orderGlobalOpenApiCustomizer() {
        return openApi -> {
            if (openApi.getTags() != null) {
                openApi.getTags().forEach(tag -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("x-order", RandomUtil.randomInt(0, 100));
                    tag.setExtensions(map);
                });
            }
            if (openApi.getPaths() != null) {
                openApi.addExtension("x-test123", "333");
                openApi.getPaths().addExtension("x-abb", RandomUtil.randomInt(1, 100));
            }

        };
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("宙斯盾SaaS平台API")
                        .version("1.0.0")
                        .description("宙斯盾SaaS平台API")
                        .contact(new Contact().name("iCoders")
                                .url("http://doc.aegiron.cn")
                                .email("admin@aegiron.cn"))
                        .termsOfService("http://doc.aegiron.cn")
                        .license(new License().name("Apache 2.0")
                                .url("http://doc.aegiron.cn")));
    }
}
