package ${basePackage}.admin.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "cors")
public class CorsProperties {

    private CorsRegistrationProperty[] corsRegistrations;

    public CorsRegistrationProperty[] getCorsRegistrations() {
        return corsRegistrations;
    }

    public void setCorsRegistrations(CorsRegistrationProperty[] corsRegistrations) {
        this.corsRegistrations = corsRegistrations;
    }
}

