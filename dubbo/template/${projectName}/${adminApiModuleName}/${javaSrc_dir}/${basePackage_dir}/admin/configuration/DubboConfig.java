package ${basePackage}.admin.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:spring/dubbo.properties")
@ImportResource({ "classpath:spring/spring-dubbo-consumer.xml" })
public class DubboConfig {

}
