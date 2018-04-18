package ${basePackage}.properties;

import ${baseCommonPackage}.model.dubbo.*;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "dubbo")
public class DubboProperties {

    private DubboApplicationProperties application = new DubboApplicationProperties();
    private DubboProtocolProperties protocol = new DubboProtocolProperties();
    private DubboProviderProperties provider = new DubboProviderProperties();
    private DubboRegistryProperties registry = new DubboRegistryProperties();

    public DubboApplicationProperties getApplication() {
        return application;
    }

    public void setApplication(DubboApplicationProperties application) {
        this.application = application;
    }

    public DubboProtocolProperties getProtocol() {
        return protocol;
    }

    public void setProtocol(DubboProtocolProperties protocol) {
        this.protocol = protocol;
    }

    public DubboProviderProperties getProvider() {
        return provider;
    }

    public void setProvider(DubboProviderProperties provider) {
        this.provider = provider;
    }

    public DubboRegistryProperties getRegistry() {
        return registry;
    }

    public void setRegistry(DubboRegistryProperties registry) {
        this.registry = registry;
    }
}
