package ${basePackage}.admin.properties;

import org.springframework.lang.Nullable;

public class CorsRegistrationProperty {

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
