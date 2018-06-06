package org.xi.quick.codegeneratorkt

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.xi.quick.codegeneratorkt.mapper.ColumnsMapper

@SpringBootApplication
class CodeGeneratorKtApplication

@Autowired
lateinit var columnsMapper : ColumnsMapper

fun main(args: Array<String>) {
    runApplication<CodeGeneratorKtApplication>(*args)
}
