package org.xi.quick.codegeneratorkt.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "generator.path")
class PathProperties {

    /**
     * 模版路径
     */
    lateinit var template: String

    /**
     * 输出路径
     */
    lateinit var out: String
}
