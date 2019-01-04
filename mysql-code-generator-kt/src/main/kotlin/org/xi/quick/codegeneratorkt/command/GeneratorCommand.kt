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
        val args = getArgs(sc)
        when (cmd) {
            "ga" -> {
                generatorService.gen()
                generatorService.genBase()
                generatorService.genAggr()
                return
            }
            "da" -> {
                generatorService.del()
                generatorService.delBase()
                generatorService.delAggr()
                return
            }
            "gb" -> {
                generatorService.genBase()
                return
            }
            "db" -> {
                generatorService.delBase()
                return
            }
            "gag" -> {
                generatorService.genAggr(*args)
                return
            }
            "dag" -> {
                generatorService.delAggr()
                return
            }
            "gat" -> {
                generatorService.gen()
                return
            }
            "dat" -> {
                generatorService.del()
                return
            }
            "gen" -> {
                gen(tableNameSet, args)
                return
            }
            "del" -> {
                del(tableNameSet, args)
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

    private fun gen(tableNameSet: Set<String>, tables: Array<String>) {

        if (tables.isEmpty()) return

        invoke(tableNameSet, tables, false) { tablesToInvoke, tablesNotExist ->
            run {
                generatorService.gen(*tablesToInvoke)
                if (!tablesNotExist.isEmpty()) {
                    logger.warn("表" + tablesNotExist.joinToString(",") + "不存在或者没有配置")
                }
            }
        }
    }

    private fun del(tableNameSet: Set<String>, tables: Array<String>) {

        if (tables.isEmpty()) return

        invoke(tableNameSet, tables, true) { tablesToInvoke, tablesNotExist ->
            run {
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
