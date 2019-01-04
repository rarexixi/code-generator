package org.xi.quick.codegeneratorkt.service.impl

import freemarker.template.TemplateExceptionHandler
import org.springframework.stereotype.Service
import org.xi.quick.codegeneratorkt.configuration.properties.GeneratorProperties
import org.xi.quick.codegeneratorkt.model.FreemarkerModel
import org.xi.quick.codegeneratorkt.service.FreeMarkerService
import org.xi.quick.codegeneratorkt.utils.DirectoryUtils
import org.xi.quick.codegeneratorkt.utils.SystemUtils
import java.io.File
import java.io.IOException
import java.util.ArrayList

@Service("freeMarkerService")
class FreeMarkerServiceImpl : FreeMarkerService {

    /**
     * 所有模版
     *
     * @return
     * @throws IOException
     */
    @Throws(IOException::class)
    override fun getTableTemplates(): List<FreemarkerModel> {

        return getMatchingTemplates { templateRelativePath ->
            isTableFile(templateRelativePath)
                    && !isMatchingFile(templateRelativePath, GeneratorProperties.files!!.ignore)
                    && !isMatchingFile(templateRelativePath, GeneratorProperties.files!!.aggregate)
                    && !isMatchingFile(templateRelativePath, GeneratorProperties.files!!.copy)
        }
    }

    /**
     * 所有运行一次的模版
     *
     * @return
     * @throws IOException
     */
    @Throws(IOException::class)
    override fun getOnceTemplates(): List<FreemarkerModel> {

        return getMatchingTemplates { templateRelativePath ->
            !isTableFile(templateRelativePath)
                    && !isMatchingFile(templateRelativePath, GeneratorProperties.files!!.ignore)
                    && !isMatchingFile(templateRelativePath, GeneratorProperties.files!!.aggregate)
                    && !isMatchingFile(templateRelativePath, GeneratorProperties.files!!.copy)
        }
    }

    /**
     * 所有聚合的模版
     *
     * @return
     * @throws IOException
     */
    @Throws(IOException::class)
    override fun getAggrTemplates(): List<FreemarkerModel> {

        return getMatchingTemplates { templateRelativePath ->
            isMatchingFile(templateRelativePath, GeneratorProperties.files!!.aggregate)
        }
    }

    /**
     * 所有仅复制的文件
     *
     * @return
     * @throws IOException
     */
    @Throws(IOException::class)
    override fun getCopyTemplates(): List<FreemarkerModel> {

        return getMatchingTemplates { templateRelativePath ->
            isMatchingFile(templateRelativePath, GeneratorProperties.files!!.copy)
        }
    }


    /**
     * 获取匹配的模版
     *
     * @param predicate
     * @return
     * @throws IOException
     */
    @Throws(IOException::class)
    fun getMatchingTemplates(predicate: (String) -> Boolean): List<FreemarkerModel> {

        var freeMarkerConfiguration = getConfiguration()

        var templatePath = GeneratorProperties.paths!!.template

        val directory = File(templatePath)
        val dirAbsolutePath = directory.absolutePath

        val files = DirectoryUtils.getAllFiles(templatePath!!)
        val result = ArrayList<FreemarkerModel>()

        for (file in files) {

            if (file.isHidden) continue

            val templateRelativePath = file.absolutePath.substring(dirAbsolutePath.length + 1)
            if (!predicate(templateRelativePath)) continue

            val template = freeMarkerConfiguration.getTemplate(templateRelativePath, GeneratorProperties.encoding)

            val outModel = FreemarkerModel(templateRelativePath, template)

            result.add(outModel)
        }

        return result
    }


    @Throws(IOException::class)
    private fun getConfiguration(): freemarker.template.Configuration {
        val directory = File(GeneratorProperties.paths?.template)
        val cfg = freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_28)
        cfg.setDirectoryForTemplateLoading(directory)
        cfg.defaultEncoding = GeneratorProperties.encoding
        cfg.templateExceptionHandler = TemplateExceptionHandler.RETHROW_HANDLER

        return cfg
    }

    /**
     * 是否是匹配模版
     *
     * @param templateRelativePath
     * @return
     */
    private fun isMatchingFile(templateRelativePath: String, files: Set<String>?): Boolean {

        if (files != null) {
            for (file in files) {
                if (templateRelativePath.startsWith(file) || templateRelativePath.contains(SystemUtils.SYSTEM_SLASH + file))
                    return true
            }
        }

        return false
    }

    /**
     * 是否是类相关文件
     *
     * @param path
     * @return
     */
    private fun isTableFile(path: String): Boolean {

        return Regex("""\$\{(className|tableName|targetTableName)(_[^\}]*)?\}""").containsMatchIn(path)
    }

}