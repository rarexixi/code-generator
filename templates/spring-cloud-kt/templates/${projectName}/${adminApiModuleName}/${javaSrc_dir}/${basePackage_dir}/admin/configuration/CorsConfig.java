package ${basePackage}.admin.configuration;

import ${basePackage}.admin.properties.CorsProperties;
import ${basePackage}.admin.properties.CorsRegistrationProperty;

import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Autowired
    CorsProperties corsProperties;

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        CorsRegistrationProperty[] corsRegistrations = corsProperties.getCorsRegistrations();

        if (corsRegistrations == null || corsRegistrations.length == 0) return;
        for (CorsRegistrationProperty property : corsRegistrations) {
            String pathPattern = property.getPathPattern();
            if (pathPattern == null || pathPattern.isEmpty()) continue;
            CorsRegistration corsRegistration = registry.addMapping(pathPattern);

            if (property.getAllowedOrigins() != null)
                corsRegistration.allowedOrigins(property.getAllowedOrigins());
            if (property.getAllowedMethods() != null)
                corsRegistration.allowedMethods(property.getAllowedMethods());
            if (property.getAllowedHeaders() != null)
                corsRegistration.allowedHeaders(property.getAllowedHeaders());
            if (property.getExposedHeaders() != null)
                corsRegistration.exposedHeaders(property.getExposedHeaders());
            if (property.getAllowCredentials() != null)
                corsRegistration.allowCredentials(property.getAllowCredentials());
            if (property.getMaxAge() != null)
                corsRegistration.maxAge(property.getMaxAge());
        }
    }
}
