package org.xi.quick.codegeneratorkt.command

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import org.xi.quick.codegeneratorkt.service.GeneratorService
import org.xi.quick.codegeneratorkt.service.TableService
import java.util.*

@Component
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
        var introduction = """
操作说明:
    ga/da:                        生成/删除所有文件
    gb/db:                        生成/删除基本类型的相关文件
    gag/dag:                      生成聚合相关文件
    gat/dat:                      生成/删除所有表的相关文件
    gen/del table [table2...]:    根据表名生成/删除相关文件(多个表之间空格分开，支持*通配符)
    s (show):                     显示所有表名
    q (quit):                     退出
请输入命令: """
        print(introduction)
    }

    private fun processLine(sc: Scanner, tableNameSet: Set<String>) {
        val cmd = sc.next().toLowerCase()
        val args = getArgs(sc)
        when (cmd) {
            "ga" -> {
                generatorService.gen()
                generatorService.genBase()
                generatorService.genAggr()
            }
            "da" -> {
                generatorService.del()
                generatorService.delBase()
                generatorService.delAggr()
            }
            "gb" -> generatorService.genBase()
            "db" -> generatorService.delBase()
            "gag" -> generatorService.genAggr(*args)
            "dag" -> generatorService.delAggr()
            "gat" -> generatorService.gen()
            "dat" -> generatorService.del()
            "gen" -> gen(tableNameSet, args)
            "del" -> del(tableNameSet, args)
            "show", "s" -> showTables(tableNameSet)
            "quit", "q" -> System.exit(0)
            else -> logger.error("[错误] 未知命令:$cmd")
        }
    }

    private fun gen(tableNameSet: Set<String>, tables: Array<String>) {

        if (tables.isEmpty()) return

        invoke(tableNameSet, tables, false) { tablesToInvoke, tablesNotExist ->
            apply {
                if (tablesToInvoke.isNotEmpty())
                    generatorService.gen(*tablesToInvoke)
                if (tablesNotExist.isNotEmpty()) {
                    logger.warn("表" + tablesNotExist.joinToString(",") + "不存在或者没有配置")
                }
            }
        }
    }

    private fun del(tableNameSet: Set<String>, tables: Array<String>) {

        if (tables.isEmpty()) return

        invoke(tableNameSet, tables, true) { tablesToInvoke, tablesNotExist ->
            apply {
                generatorService.del(*tablesToInvoke)
            }
        }
    }

    private fun invoke(tableNameSet: Set<String>, tables: Array<String>, isDelete: Boolean, consume: (Array<String>, Array<String>) -> Unit) {

        var tablesToInvoke = HashSet<String>()
        val tablesNotExist = HashSet<String>()

        for (table in tables) {
            if (tableNameSet.contains(table)) {
                tablesToInvoke.add(table)
            } else if (table.contains("*")) {
                var tableRegex = ('^' + table.replace("*", ".*") + '$').toRegex()
                tableNameSet.forEach {
                    if (tableRegex.matches(it)) {
                        tablesToInvoke.add(it)
                    }
                }
            } else if (isDelete) {
                tablesToInvoke.add(table)
            } else {
                tablesNotExist.add(table)
                continue
            }
        }

        consume(tablesToInvoke.toTypedArray(), tablesNotExist.toTypedArray())
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
