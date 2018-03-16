package org.xi.quick.codegenerator.properties;

import com.sun.istack.internal.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@ConfigurationProperties(prefix = "generator.path")
@Validated
public class PathProperties {

    /**
     * 模版路径
     */
    @NotNull
    private String template;

    /**
     * 输出路径
     */
    @NotNull
    private String out;

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getOut() {
        return out;
    }

    public void setOut(String out) {
        this.out = out;
    }
}
