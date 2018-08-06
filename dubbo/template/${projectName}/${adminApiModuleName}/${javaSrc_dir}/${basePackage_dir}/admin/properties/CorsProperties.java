package ${basePackage}.admin.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "cors")
public class CorsProperties {

    private CorsRegistrationProperty[] corsRegistrations;
    private CorsFilterProperty filter;

    public CorsRegistrationProperty[] getCorsRegistrations() {
        return corsRegistrations;
    }

    public void setCorsRegistrations(CorsRegistrationProperty[] corsRegistrations) {
        this.corsRegistrations = corsRegistrations;
    }

    public CorsFilterProperty getFilter() {
        return filter;
    }

    public void setFilter(CorsFilterProperty filter) {
        this.filter = filter;
    }

    public static class CorsFilterProperty {

        private String pathPattern;

        @Nullable
        private String allowedOrigins;
        @Nullable
        private String allowedMethods;
        @Nullable
        private String allowedHeaders;
        @Nullable
        private Boolean allowCredentials;
        @Nullable
        private Long maxAge;

        public String getPathPattern() {
            return pathPattern;
        }

        public void setPathPattern(String pathPattern) {
            this.pathPattern = pathPattern;
        }

        public String getAllowedOrigins() {
            return allowedOrigins;
        }

        public void setAllowedOrigins(String allowedOrigins) {
            this.allowedOrigins = allowedOrigins;
        }

        public String getAllowedMethods() {
            return allowedMethods;
        }

        public void setAllowedMethods(String allowedMethods) {
            this.allowedMethods = allowedMethods;
        }

        public String getAllowedHeaders() {
            return allowedHeaders;
        }

        public void setAllowedHeaders(String allowedHeaders) {
            this.allowedHeaders = allowedHeaders;
        }

        public Boolean getAllowCredentials() {
            return allowCredentials;
        }

        public void setAllowCredentials(Boolean allowCredentials) {
            this.allowCredentials = allowCredentials;
        }

        public Long getMaxAge() {
            return maxAge;
        }

        public void setMaxAge(Long maxAge) {
            this.maxAge = maxAge;
        }
    }

    public static class CorsRegistrationProperty {

        private String pathPattern;

        @Nullable
        private String[] allowedOrigins;
        @Nullable
        private String[] allowedMethods;
        @Nullable
        private String[] allowedHeaders;
        @Nullable
        private String[] exposedHeaders;
        @Nullable
        private Boolean allowCredentials;
        @Nullable
        private Long maxAge;

        public String getPathPattern() {
            return pathPattern;
        }

        public void setPathPattern(String pathPattern) {
            this.pathPattern = pathPattern;
        }

        @Nullable
        public String[] getAllowedOrigins() {
            return allowedOrigins;
        }

        public void setAllowedOrigins(@Nullable String[] allowedOrigins) {
            this.allowedOrigins = allowedOrigins;
        }

        @Nullable
        public String[] getAllowedMethods() {
            return allowedMethods;
        }

        public void setAllowedMethods(@Nullable String[] allowedMethods) {
            this.allowedMethods = allowedMethods;
        }

        @Nullable
        public String[] getAllowedHeaders() {
            return allowedHeaders;
        }

        public void setAllowedHeaders(@Nullable String[] allowedHeaders) {
            this.allowedHeaders = allowedHeaders;
        }

        @Nullable
        public String[] getExposedHeaders() {
            return exposedHeaders;
        }

        public void setExposedHeaders(@Nullable String[] exposedHeaders) {
            this.exposedHeaders = exposedHeaders;
        }

        @Nullable
        public Boolean getAllowCredentials() {
            return allowCredentials;
        }

        public void setAllowCredentials(@Nullable Boolean allowCredentials) {
            this.allowCredentials = allowCredentials;
        }

        @Nullable
        public Long getMaxAge() {
            return maxAge;
        }

        public void setMaxAge(@Nullable Long maxAge) {
            this.maxAge = maxAge;
        }
    }
}
