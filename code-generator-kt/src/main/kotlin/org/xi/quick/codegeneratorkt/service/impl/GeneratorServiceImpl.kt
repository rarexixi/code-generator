package org.xi.quick.codegeneratorkt.service.impl

import freemarker.template.TemplateException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.xi.quick.codegeneratorkt.StaticConfigData
import org.xi.quick.codegeneratorkt.extensions.getFirstLower
import org.xi.quick.codegeneratorkt.extensions.getFirstUpper
import org.xi.quick.codegeneratorkt.model.FreemarkerModel
import org.xi.quick.codegeneratorkt.model.TableModel
import org.xi.quick.codegeneratorkt.properties.GeneratorProperties
import org.xi.quick.codegeneratorkt.properties.PathProperties
import org.xi.quick.codegeneratorkt.service.GeneratorService
import org.xi.quick.codegeneratorkt.service.TableService
import org.xi.quick.codegeneratorkt.utils.DirectoryUtils
import org.xi.quick.codegeneratorkt.utils.FileUtils
import org.xi.quick.codegeneratorkt.utils.SystemUtils
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStreamWriter
import java.nio.file.Files
import java.util.HashMap
import java.util.regex.Pattern


@Service("generatorService")
class GeneratorServiceImpl : GeneratorService {

    internal var logger = LoggerFactory.getLogger(GeneratorServiceImpl::class.java)

    @Autowired
    lateinit var generator: GeneratorProperties

    @Autowired
    lateinit var generatorPath: PathProperties

    @Autowired
    lateinit var allTemplates: List<FreemarkerModel>

    @Autowired
    lateinit var allOnceTemplates: List<FreemarkerModel>

    @Autowired
    lateinit var allJustCopyTemplates: List<FreemarkerModel>

    @Autowired
    lateinit var allAggregateTemplates: List<FreemarkerModel>

    @Autowired
    lateinit var tableService: TableService

    //region 生成

    /**
     * 生成所有数据类
     */
    override fun generateAll() {

        loopAll({ template, table -> generate(template, table) })
    }

    /**
     * 生成数据类
     *
     * @param tableNames
     */
    override fun generate(vararg tableNames: String) {

        loop({ template, table -> generate(template, table) }, *tableNames)
    }

    /**
     * 生成所有生成一次的类
     */
    override fun generateAllOnce() {

        val dataModel = HashMap<Any, Any>()
        generateOnce(allOnceTemplates, dataModel)
        generateCopy(allJustCopyTemplates, dataModel)
    }

    /**
     * 生成所有聚合类
     */
    override fun generateAllAggregate() {

        val tables = tableService.getAllTables()
        val dataModel = HashMap<Any, Any>()
        dataModel["tableModels"] = tables
        generateOnce(allAggregateTemplates, dataModel)
    }

    //endregion

    //region 删除

    /**
     * 删除所有数据类
     */
    override fun deleteAll() {

        loopAll({ template, table -> delete(template, table) })
    }

    /**
     * 删除数据类
     *
     * @param tableNames
     */
    override fun delete(vararg tableNames: String) {

        loop({ template, table -> delete(template, table) }, *tableNames)
    }

    /**
     * 删除所有生成一次的类
     */
    override fun deleteAllOnce() {

        deleteOnce(allOnceTemplates)
        deleteOnce(allJustCopyTemplates)
    }

    /**
     * 删除所有聚合类
     */
    override fun deleteAllAggregate() {

        deleteOnce(allAggregateTemplates)
    }

    //endregion

    //region 私有方法

    private fun generate(template: FreemarkerModel, table: TableModel) {

        val dataModel = HashMap<Any, Any>()
        dataModel["table"] = table
        dataModel["className"] = table.tableClassName!!

        try {
            generate(template, dataModel)
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: TemplateException) {
            e.printStackTrace()
        }

    }

    private fun generateOnce(templates: List<FreemarkerModel>, dataModel: MutableMap<Any, Any>) {

        for (template in templates) {
            try {
                generate(template, dataModel)
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: TemplateException) {
                e.printStackTrace()
            }

        }
    }

    private fun generateCopy(templates: List<FreemarkerModel>, dataModel: MutableMap<Any, Any>) {

        for (outModel in templates) {

            dataModel.putAll(StaticConfigData.COMMON_PROPERTIES)

            val targetPath = getFilePath(outModel, dataModel)
            val sourcePath = generatorPath.template + outModel.relativePath

            logger.info("正在生成$targetPath")
            try {
                //创建文件路径
                DirectoryUtils.createIfNotExists(getAbsoluteDirectory(targetPath))
                FileUtils.deleteIfExists(File(targetPath))
                Files.copy(File(sourcePath).toPath(), File(targetPath).toPath())
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }

    private fun delete(template: FreemarkerModel, table: TableModel) {

        val dataModel = HashMap<Any, Any>()
        dataModel["className"] = table.tableClassName!!
        delete(template, dataModel)
    }

    private fun deleteOnce(templates: List<FreemarkerModel>) {

        for (template in templates) {
            delete(template)
        }
    }

    /**
     * 执行操作
     *
     * @param consume   消费方法
     * @param tableNames 要操作的表名
     */
    private fun loop(consume: (FreemarkerModel, TableModel) -> Unit, vararg tableNames: String) {

        for (tableName in tableNames) {
            val table = tableService.getTable(tableName)
            if (table != null) {
                allTemplates.forEach { consume(it, table) }
            }
        }
    }

    /**
     * 执行操作
     *
     * @param consume 消费方法
     */
    private fun loopAll(consume: (FreemarkerModel, TableModel) -> Unit) {

        val tables = tableService.getAllTables()

        for (table in tables) {
            allTemplates.forEach { template -> consume(template, table) }
        }
    }

    /**
     * 生成输出
     *
     * @param outModel
     * @param dataModel
     * @throws IOException
     * @throws TemplateException
     */
    @Throws(IOException::class, TemplateException::class)
    private fun generate(outModel: FreemarkerModel, dataModel: MutableMap<Any, Any> = HashMap()) {

        dataModel.putAll(StaticConfigData.COMMON_PROPERTIES)

        val absolutePath = getFilePath(outModel, dataModel)

        logger.info("正在生成$absolutePath")

        //创建文件路径
        DirectoryUtils.createIfNotExists(getAbsoluteDirectory(absolutePath))

        FileOutputStream(absolutePath).use { stream ->
            OutputStreamWriter(stream, generator.encoding).use({ out ->

                outModel.template.process(dataModel, out)
            })
        }
    }

    private fun delete(outModel: FreemarkerModel, dataModel: MutableMap<Any, Any> = HashMap()) {

        dataModel.putAll(StaticConfigData.COMMON_PROPERTIES)

        val absolutePath = getFilePath(outModel, dataModel)

        logger.info("正在删除$absolutePath")

        FileUtils.delete(absolutePath)
    }


    private fun getFilePath(model: FreemarkerModel, dataModel: Map<Any, Any>): String {

        val directory = File(generatorPath.out)
        return directory.absolutePath + SystemUtils.SYSTEM_SLASH + getActualPath(model.relativePath, dataModel)
    }

    private fun getAbsoluteDirectory(absolutePath: String): String {
        return absolutePath.substring(0, absolutePath.lastIndexOf(SystemUtils.SYSTEM_SLASH))
    }


    /**
     * 获取文件输出实际路径
     *
     * @param path
     * @param properties
     * @return
     */
    private fun getActualPath(path: String, properties: Map<Any, Any>): String {
        var actualPath = path

        val pattern = Pattern.compile("\\$\\{[^\\}]*\\}")
        val matcher = pattern.matcher(actualPath)

        Regex("""${'$'}\{[^\}]*\}""").findAll(actualPath).forEach {
            var group = it?.value
            var key = group.substring(2, group.length - 1)

            val isDir = key.endsWith("_dir")
            val isLower = key.endsWith("_lower")
            val isUpper = key.endsWith("_upper")
            val isFirstLower = key.endsWith("_firstLower")
            val isFirstUpper = key.endsWith("_firstUpper")
            if (isDir) {
                key = key.substring(0, key.length - 4)
            } else if (isLower || isUpper) {
                key = key.substring(0, key.length - 6)
            } else if (isFirstLower || isFirstUpper) {
                key = key.substring(0, key.length - 11)
            }

            val value = properties[key]

            if (value != null) {
                val s = value as String
                actualPath = when {
                    isDir -> actualPath.replace(group, s.replace("\\.".toRegex(), SystemUtils.REGEX_SYSTEM_SLASH))
                    isLower -> actualPath.replace(group, s.toLowerCase())
                    isUpper -> actualPath.replace(group, s.toUpperCase())
                    isFirstLower -> actualPath.replace(group, s.getFirstLower())
                    isFirstUpper -> actualPath.replace(group, s.getFirstUpper())
                    else -> actualPath.replace(group, s)
                }
            }
        }

        return actualPath
    }

    //endregion
}