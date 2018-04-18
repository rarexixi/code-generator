package ${basePackage}.admin.properties;

import ${baseCommonPackage}.model.dubbo.DubboApplicationProperties;
import ${baseCommonPackage}.model.dubbo.DubboConsumerProperties;
import ${baseCommonPackage}.model.dubbo.DubboProtocolProperties;
import ${baseCommonPackage}.model.dubbo.DubboRegistryProperties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "dubbo")
public class DubboProperties {

    private DubboApplicationProperties application = new DubboApplicationProperties();
    private DubboProtocolProperties protocol = new DubboProtocolProperties();
    private DubboConsumerProperties consumer = new DubboConsumerProperties();
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

    public DubboConsumerProperties getConsumer() {
        return consumer;
    }

    public void setConsumer(DubboConsumerProperties consumer) {
        this.consumer = consumer;
    }

    public DubboRegistryProperties getRegistry() {
        return registry;
    }

    public void setRegistry(DubboRegistryProperties registry) {
        this.registry = registry;
    }
}
