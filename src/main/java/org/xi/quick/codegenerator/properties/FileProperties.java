package org.xi.quick.codegenerator.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@ConfigurationProperties(prefix = "generator.file")
public class FileProperties {

    /**
     * 忽略的文件集合
     */
    private Set<String> ignore;

    /**
     * 聚合文件集合
     */
    private Set<String> aggregate;

    /**
     * 仅复制文件集合
     */
    private Set<String> justCopy;

    public Set<String> getIgnore() {
        return ignore;
    }

    public void setIgnore(Set<String> ignore) {
        this.ignore = ignore;
    }

    public Set<String> getAggregate() {
        return aggregate;
    }

    public void setAggregate(Set<String> aggregate) {
        this.aggregate = aggregate;
    }

    public Set<String> getJustCopy() {
        return justCopy;
    }

    public void setJustCopy(Set<String> justCopy) {
        this.justCopy = justCopy;
    }
}
