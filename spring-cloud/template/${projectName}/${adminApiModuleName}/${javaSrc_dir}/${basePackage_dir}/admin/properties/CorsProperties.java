package ${basePackage}.admin.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;

@Component
@ConfigurationProperties(prefix = "access.control")
public class CorsProperties {

    private String allowOrigin;
    private String allowMethods;
    private String maxAge;
    private String allowHeaders;
    private Set<String> urlPatterns;

    public CorsProperties() {
        this.allowOrigin = "*";
        this.allowMethods = "GET,POST,UPDATE,DELETE,PUT,TRACE,OPTIONS";
        this.maxAge = "3600";
        this.allowHeaders = "Origin, X-Requested-With, Content-Type, Accept";
        this.urlPatterns = new LinkedHashSet<>();
        this.urlPatterns.add("/*");
    }

    public String getAllowOrigin() {
        return allowOrigin;
    }

    public void setAllowOrigin(String allowOrigin) {
        this.allowOrigin = allowOrigin;
    }

    public String getAllowMethods() {
        return allowMethods;
    }

    public void setAllowMethods(String allowMethods) {
        this.allowMethods = allowMethods;
    }

    public String getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(String maxAge) {
        this.maxAge = maxAge;
    }

    public String getAllowHeaders() {
        return allowHeaders;
    }

    public void setAllowHeaders(String allowHeaders) {
        this.allowHeaders = allowHeaders;
    }

    public Set<String> getUrlPatterns() {
        return urlPatterns;
    }

    public void setUrlPatterns(Set<String> urlPatterns) {
        this.urlPatterns = urlPatterns;
    }
}
