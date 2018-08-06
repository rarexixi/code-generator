package org.xi.quick.webapicodegenerator

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CodeGeneratorApplication

fun main(args: Array<String>) {
    runApplication<CodeGeneratorApplication>(*args)
}
