package org.xi.quick.codegeneratorkt.command

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.xi.quick.codegeneratorkt.service.GeneratorService
import org.xi.quick.codegeneratorkt.service.TableService
import java.util.*

@Component
@Order(100)
class GeneratorCommand : CommandLineRunner {

    internal var logger = LoggerFactory.getLogger(GeneratorCommand::class.java)

    @Autowired
    lateinit var tableService: TableService

    @Autowired
    lateinit var generatorService: GeneratorService

    @Throws(Exception::class)
    override fun run(vararg strings: String) {

        val tableNameSet = tableService.getAllTableNameList()

        val sc = Scanner(System.`in`)

        printUsages()
        while (sc.hasNextLine()) {
            try {
                processLine(sc, tableNameSet)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                printUsages()
            }
        }
    }

    private fun printUsages() {
        println("操作说明:")
        println("\tga/da:                        生成/删除所有文件(gen/del all)")
        println("\tgb/db:                        生成/删除基本类型的相关文件(gen/del base)")
        println("\tgag/dag:                      生成聚合相关文件(gen/del aggregate)")
        println("\tgat/dat:                      生成/删除所有表的相关文件(gen/del all table)")
        println("\tgen/del table [table2...]:    根据表名生成/删除相关文件")
        println("\ts (show):                     显示所有表名")
        println("\tq (quit):                     退出")
        print("请输入命令 : ")
    }

    private fun processLine(sc: Scanner, tableNameSet: Set<String>) {
        val cmd = sc.next().toLowerCase()
        when (cmd) {
            "ga" -> {
                generatorService.generateAll()
                generatorService.generateAllOnce()
                generatorService.generateAllAggregate()
                return
            }
            "da" -> {
                generatorService.deleteAll()
                generatorService.deleteAllOnce()
                generatorService.deleteAllAggregate()
                return
            }
            "gb" -> {
                generatorService.generateAllOnce()
                return
            }
            "db" -> {
                generatorService.deleteAllOnce()
                return
            }
            "gag" -> {
                generatorService.generateAllOnce()
                return
            }
            "dag" -> {
                generatorService.deleteAllAggregate()
                return
            }
            "gat" -> {
                generatorService.generateAll()
                return
            }
            "dat" -> {
                generatorService.deleteAll()
                return
            }
            "gen" -> {
                generate(tableNameSet, getArgs(sc))
                return
            }
            "del" -> {
                delete(tableNameSet, getArgs(sc))
                return
            }
            "show", "s" -> {
                showTables(tableNameSet)
                return
            }
            "quit", "q" -> {
                System.exit(0)
                return
            }
            else -> {
                logger.error("[错误] 未知命令:$cmd")
                return
            }
        }
    }

    private fun generate(tableNameSet: Set<String>, tables: Array<String>) {

        if (tables.isEmpty()) return

        invoke(tableNameSet, tables) { tablesToGen, tablesNotExist ->
            run {
                tablesToGen.forEach { generatorService.generate(it) }
                if (!tablesNotExist.isEmpty()) {
                    logger.warn("表" + tablesNotExist.joinToString(",") + "不存在或者没有配置")
                }
            }
        }
    }

    private fun delete(tableNameSet: Set<String>, tables: Array<String>) {

        if (tables.isEmpty()) return
        invoke(tableNameSet, tables) { tablesToGen, tablesNotExist ->
            run {
                tablesToGen.forEach { generatorService.delete(it) }
            }
        }
    }

    private fun invoke(tableNameSet: Set<String>, tables: Array<String>, consume: (Set<String>, Set<String>) -> Unit) {

        var tablesToGen = HashSet<String>()
        val tablesNotExist = HashSet<String>()

        for (table in tables) {
            if (tableNameSet.contains(table)) {
                tablesToGen.add(table)
            } else if (table.contains("*")) {
                var tableRegex = ('^' + table.replace("*", ".*") + '$').toRegex()
                tableNameSet.forEach {
                    if (tableRegex.matches(it)) {
                        tablesToGen.add(it)
                    }
                }
            } else {
                tablesNotExist.add(table)
                continue
            }
        }

        consume(tablesToGen, tablesNotExist)
    }

    private fun showTables(tableNameSet: Set<String>) {
        var maxLength = tableNameSet.map { it.length }.max()?.plus(4)
        var formatString = "%-" + maxLength + "s"
        var index = 0
        tableNameSet.forEach {
            print(String.format(formatString, it))
            if (++index % 4 == 0) {
                println()
            }
        }
    }

    internal fun getArgs(sc: Scanner): Array<String> {
        val line = sc.nextLine() ?: return arrayOf()
        val tokenizer = StringTokenizer(line, " ")
        val result = ArrayList<String>()

        while (tokenizer.hasMoreElements()) {
            val s = tokenizer.nextElement()
            result.add(s.toString())
        }
        return result.toTypedArray()
    }
}
