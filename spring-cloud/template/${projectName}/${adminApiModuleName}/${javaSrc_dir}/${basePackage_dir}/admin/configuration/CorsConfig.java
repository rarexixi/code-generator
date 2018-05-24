package ${basePackage}.admin.configuration;

import ${basePackage}.admin.filter.CorsFilter;
import ${basePackage}.admin.properties.CorsProperties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CorsConfig {

    @Autowired
    CorsProperties corsProperties;

    @Bean
    public FilterRegistrationBean filterRegistrationBean(CorsFilter corsFilter) {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(corsFilter);
        if (corsProperties.getUrlPatterns() != null && !corsProperties.getUrlPatterns().isEmpty()) {
            registrationBean.setUrlPatterns(corsProperties.getUrlPatterns());
        }
        return registrationBean;
    }
}
