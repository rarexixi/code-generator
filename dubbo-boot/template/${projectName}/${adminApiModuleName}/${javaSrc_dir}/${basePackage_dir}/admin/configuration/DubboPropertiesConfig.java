package ${basePackage}.admin.configuration;

import com.alibaba.dubbo.config.*;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "dubbo")
public class DubboPropertiesConfig {

    @NestedConfigurationProperty
    private ApplicationConfig application;

    @NestedConfigurationProperty
    private ModuleConfig module;

    @NestedConfigurationProperty
    private RegistryConfig registry;

    @NestedConfigurationProperty
    private ProtocolConfig protocol;

    @NestedConfigurationProperty
    private MonitorConfig monitor;

    @NestedConfigurationProperty
    private ProviderConfig provider;

    @NestedConfigurationProperty
    private ConsumerConfig consumer;

    public ApplicationConfig getApplication() {
        return application;
    }

    public void setApplication(ApplicationConfig application) {
        this.application = application;
    }

    public ModuleConfig getModule() {
        return module;
    }

    public void setModule(ModuleConfig module) {
        this.module = module;
    }

    public RegistryConfig getRegistry() {
        return registry;
    }

    public void setRegistry(RegistryConfig registry) {
        this.registry = registry;
    }

    public ProtocolConfig getProtocol() {
        return protocol;
    }

    public void setProtocol(ProtocolConfig protocol) {
        this.protocol = protocol;
    }

    public MonitorConfig getMonitor() {
        return monitor;
    }

    public void setMonitor(MonitorConfig monitor) {
        this.monitor = monitor;
    }

    public ProviderConfig getProvider() {
        return provider;
    }

    public void setProvider(ProviderConfig provider) {
        this.provider = provider;
    }

    public ConsumerConfig getConsumer() {
        return consumer;
    }

    public void setConsumer(ConsumerConfig consumer) {
        this.consumer = consumer;
    }
}
