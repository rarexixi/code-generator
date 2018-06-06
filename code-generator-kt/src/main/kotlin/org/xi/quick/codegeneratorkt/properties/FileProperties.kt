package org.xi.quick.codegeneratorkt.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import java.util.HashSet

@Component
@ConfigurationProperties(prefix = "generator.file")
class FileProperties {

    /**
     * 忽略的文件集合
     */
    var ignore: Set<String>? = HashSet()

    /**
     * 聚合文件集合
     */
    var aggregate: Set<String>? = HashSet()

    /**
     * 仅复制文件集合
     */
    var justCopy: Set<String>? = HashSet()
}
