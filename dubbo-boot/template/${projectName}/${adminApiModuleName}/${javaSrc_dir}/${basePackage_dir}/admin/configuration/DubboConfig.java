package ${basePackage}.admin.configuration;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubboConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConditionalOnProperty(prefix = "dubbo", name = "enabled", matchIfMissing = true, havingValue = "true")
@Configuration
public class DubboConfig {

    @EnableDubboConfig
    @EnableConfigurationProperties(DubboPropertiesConfig.class)
    protected static class DubboPropertiesConfig {
    }
}
