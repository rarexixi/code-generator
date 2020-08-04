package org.xi.quick.codegeneratorkt.service.impl

import freemarker.template.TemplateException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.stereotype.Service
import org.xi.quick.codegeneratorkt.extensions.getFirstLower
import org.xi.quick.codegeneratorkt.extensions.getFirstUpper
import org.xi.quick.codegeneratorkt.model.FreemarkerModel
import org.xi.quick.codegeneratorkt.model.TableModel
import org.xi.quick.codegeneratorkt.configuration.properties.GeneratorProperties
import org.xi.quick.codegeneratorkt.extensions.getClassName
import org.xi.quick.codegeneratorkt.extensions.getTargetTableName
import org.xi.quick.codegeneratorkt.service.FreeMarkerService
import org.xi.quick.codegeneratorkt.service.GeneratorService
import org.xi.quick.codegeneratorkt.service.TableService
import org.xi.quick.codegeneratorkt.utils.DirectoryUtils
import org.xi.quick.codegeneratorkt.utils.SystemUtils
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStreamWriter
import java.nio.file.Files
import java.nio.file.Path
import java.util.HashMap

@Service("generatorService")
class GeneratorServiceImpl : GeneratorService {

    internal var logger = LoggerFactory.getLogger(GeneratorServiceImpl::class.java)

    @Autowired
    lateinit var tableService: TableService

    @Autowired
    lateinit var freeMarkerService: FreeMarkerService

    @Autowired
    lateinit var dataSourceProperties: DataSourceProperties

    @Autowired
    lateinit var generatorProperties: GeneratorProperties

    //region 生成

    /**
     * 生成数据类
     *
     * @param tableNames
     */
    override fun gen(vararg tableNames: String) {

        val allTemplates = freeMarkerService.getTableTemplates()

        val tables = tableService.getTables(*tableNames)
        tables.forEach { table -> allTemplates.forEach { template -> generate(template, table) } }
    }

    /**
     * 生成所有生成一次的类
     */
    override fun genBase() {

        val onceTemplates = freeMarkerService.getOnceTemplates()
        val copyTemplates = freeMarkerService.getCopyTemplates()

        val dataModel = HashMap<Any, Any>()
        generateOnce(onceTemplates, dataModel)
        generateCopy(copyTemplates, dataModel)
    }

    /**
     * 生成所有聚合类
     */
    override fun genAggr(vararg tableNames: String) {

        val aggrTemplates = freeMarkerService.getAggrTemplates()

        val tables = tableService.getTables(*tableNames)
        val dataModel = HashMap<Any, Any>()
        dataModel["tableModels"] = tables
        generateOnce(aggrTemplates, dataModel)
    }

    //endregion

    //region 删除

    /**
     * 删除数据类
     *
     * @param tableNames
     */
    override fun del(vararg tableNames: String) {

        val tables = if (tableNames.isEmpty()) tableService.getAllTableNameList().toTypedArray() else tableNames

        val tableTemplates = freeMarkerService.getTableTemplates()

        tables.forEach { tableName -> tableTemplates.forEach { template -> delete(template, tableName) } }
    }

    /**
     * 删除所有生成一次的类
     */
    override fun delBase() {

        val onceTemplates = freeMarkerService.getOnceTemplates()
        val copyTemplates = freeMarkerService.getCopyTemplates()

        deleteOnce(onceTemplates)
        deleteOnce(copyTemplates)
    }

    /**
     * 删除所有聚合类
     */
    override fun delAggr() {

        val aggrTemplates = freeMarkerService.getAggrTemplates()

        deleteOnce(aggrTemplates)
    }

    //endregion

    //region 私有方法

    private fun generate(template: FreemarkerModel, table: TableModel) {

        val dataModel = HashMap<Any, Any>()
        dataModel["table"] = table
        dataModel["tableName"] = table.tableName
        dataModel["targetTableName"] = table.targetTableName
        dataModel["className"] = table.className

        try {
            generate(template, dataModel)
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: TemplateException) {
            e.printStackTrace()
        }
    }

    private fun generateOnce(templates: List<FreemarkerModel>, dataModel: MutableMap<Any, Any>) {

        val baseColumns = tableService.getBaseColumns()
        dataModel["baseColumns"] = baseColumns

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

            putAllCommonProperties(dataModel)

            val relativePath = getActualPath(outModel.relativePath, dataModel)
            logger.info("正在复制$relativePath")

            val targetPath = getAbsoluteFilePath(relativePath)
            val sourcePath = GeneratorProperties.paths!!.template + outModel.relativePath

            try {
                //创建文件路径
                DirectoryUtils.createIfNotExists(getAbsoluteDirectory(targetPath))
                File(targetPath).delete()
                Files.copy(File(sourcePath).toPath(), File(targetPath).toPath())
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }

    private fun delete(template: FreemarkerModel, tableName: String) {

        val dataModel = HashMap<Any, Any>()
        dataModel["tableName"] = tableName
        dataModel["targetTableName"] = tableName.getTargetTableName()
        dataModel["className"] = tableName.getClassName()
        delete(template, dataModel)
    }

    private fun deleteOnce(templates: List<FreemarkerModel>) {

        for (template in templates) {
            delete(template)
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

        putAllCommonProperties(dataModel)

        val relativePath = getActualPath(outModel.relativePath, dataModel)
        logger.info("正在生成$relativePath")

        val absolutePath = getAbsoluteFilePath(relativePath)
        DirectoryUtils.createIfNotExists(getAbsoluteDirectory(absolutePath))

        FileOutputStream(absolutePath).use { stream ->
            OutputStreamWriter(stream, GeneratorProperties.encoding).use { out ->
                outModel.template?.process(dataModel, out)
                out.flush()
            }
            stream.flush()
        }
    }

    private fun delete(outModel: FreemarkerModel, dataModel: MutableMap<Any, Any> = HashMap()) {

        putAllCommonProperties(dataModel)

        val relativePath = getActualPath(outModel.relativePath, dataModel)
        logger.info("正在删除${relativePath}")
        val absolutePath = getAbsoluteFilePath(relativePath)
        File(absolutePath).delete()
    }

    /**
     * 添加公共属性
     */
    private fun putAllCommonProperties(dataModel: MutableMap<Any, Any> = HashMap()) {
        GeneratorProperties.commonProperties.forEach { key, value -> dataModel[key] = value }
        dataModel["dbUrl"] = dataSourceProperties.url
        dataModel["dbUsername"] = dataSourceProperties.username
        dataModel["dbPassword"] = dataSourceProperties.password
        dataModel["paths"] = generatorProperties.paths!!
    }

    /**
     * 获取文件实际路径
     */
    private fun getAbsoluteFilePath(relativePath: String): String {
        val directory = File(GeneratorProperties.paths!!.out)
        return directory.absolutePath + SystemUtils.SYSTEM_SLASH +
                (if (relativePath.endsWith(".ftl")) relativePath.substring(0, relativePath.length - 4) else relativePath)
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

        return Regex("""\$\{[^\}]*\}""").replace(path) {

            val group = it.value
            val tmp = group.substring(2, group.length - 1)
            val index = tmp.indexOf('_')
            val key = if (index > -1) tmp.substring(0, index) else tmp

            val value = properties[key]
            val s = if (value != null) value as String else group

            when {
                tmp.endsWith("_dir") -> s.replace("""\.""".toRegex(), SystemUtils.REGEX_SYSTEM_SLASH)
                tmp.endsWith("_lower") -> s.toLowerCase()
                tmp.endsWith("_upper") -> s.toUpperCase()
                tmp.endsWith("_firstLower") -> s.getFirstLower()
                tmp.endsWith("_firstUpper") -> s.getFirstUpper()
                else -> s
            }
        }
    }

    //endregion
}