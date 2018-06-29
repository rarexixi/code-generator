package org.xi.quick.codegeneratorkt.config

import freemarker.template.TemplateExceptionHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.xi.quick.codegeneratorkt.model.FreemarkerModel
import org.xi.quick.codegeneratorkt.properties.FileProperties
import org.xi.quick.codegeneratorkt.properties.GeneratorProperties
import org.xi.quick.codegeneratorkt.properties.PathProperties
import org.xi.quick.codegeneratorkt.utils.DirectoryUtils
import org.xi.quick.codegeneratorkt.utils.SystemUtils
import java.io.File
import java.io.IOException
import java.util.ArrayList

@Configuration
class AppConfig {

    @Autowired
    lateinit var generator: GeneratorProperties

    @Autowired
    lateinit var generatorPath: PathProperties

    @Autowired
    lateinit var generatorFile: FileProperties

    @Bean(name = ["freeMarkerConfiguration"])
    @Throws(IOException::class)
    fun getConfiguration(): freemarker.template.Configuration {
        val directory = File(generatorPath.template)

        val cfg = freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_28)
        cfg.setDirectoryForTemplateLoading(directory)
        cfg.defaultEncoding = generator.encoding
        cfg.templateExceptionHandler = TemplateExceptionHandler.RETHROW_HANDLER

        return cfg
    }

    /**
     * 所有模版
     *
     * @return
     * @throws IOException
     */
    @Bean(name = ["allTemplates"])
    @Throws(IOException::class)
    fun getAllTemplates(freeMarkerConfiguration: freemarker.template.Configuration): List<FreemarkerModel> {

        return getMatchingTemplates(freeMarkerConfiguration,
                { templateRelativePath ->
                    (isClassFile(templateRelativePath)
                            && !isMatchingFile(templateRelativePath, generatorFile.ignore)
                            && !isMatchingFile(templateRelativePath, generatorFile.aggregate)
                            && !isMatchingFile(templateRelativePath, generatorFile.justCopy))
                })
    }

    /**
     * 所有运行一次的模版
     *
     * @return
     * @throws IOException
     */
    @Bean(name = ["allOnceTemplates"])
    @Throws(IOException::class)
    fun getAllOnceTemplates(freeMarkerConfiguration: freemarker.template.Configuration): List<FreemarkerModel> {

        return getMatchingTemplates(freeMarkerConfiguration,
                { templateRelativePath ->
                    !isClassFile(templateRelativePath)
                            && !isMatchingFile(templateRelativePath, generatorFile.ignore)
                            && !isMatchingFile(templateRelativePath, generatorFile.aggregate)
                            && !isMatchingFile(templateRelativePath, generatorFile.justCopy)
                })
    }

    /**
     * 所有聚合的模版
     *
     * @return
     * @throws IOException
     */
    @Bean(name = ["allAggregateTemplates"])
    @Throws(IOException::class)
    fun getAllAggregateTemplates(freeMarkerConfiguration: freemarker.template.Configuration): List<FreemarkerModel> {

        return getMatchingTemplates(freeMarkerConfiguration,
                { templateRelativePath -> isMatchingFile(templateRelativePath, generatorFile.aggregate) })
    }

    /**
     * 所有仅复制的文件
     *
     * @return
     * @throws IOException
     */
    @Bean(name = ["allJustCopyTemplates"])
    @Throws(IOException::class)
    fun getAllJustCopyTemplates(freeMarkerConfiguration: freemarker.template.Configuration): List<FreemarkerModel> {

        return getMatchingTemplates(freeMarkerConfiguration,
                { templateRelativePath -> isMatchingFile(templateRelativePath, generatorFile.justCopy) })
    }


    /**
     * 获取匹配的模版
     *
     * @param freeMarkerConfiguration
     * @param predicate
     * @return
     * @throws IOException
     */
    @Throws(IOException::class)
    fun getMatchingTemplates(freeMarkerConfiguration: freemarker.template.Configuration,
                             predicate: (String) -> Boolean): List<FreemarkerModel> {

        val directory = File(generatorPath.template)
        val dirAbsolutePath = directory.absolutePath

        val files = DirectoryUtils.getAllFiles(generatorPath.template)
        val result = ArrayList<FreemarkerModel>()

        for (file in files) {

            if (file.isHidden) continue

            val templateRelativePath = file.absolutePath.substring(dirAbsolutePath.length + 1)
            if (!predicate(templateRelativePath)) continue

            val template = freeMarkerConfiguration.getTemplate(templateRelativePath, generator.encoding)

            val outModel = FreemarkerModel(templateRelativePath, template)

            result.add(outModel)
        }

        return result
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
    private fun isClassFile(path: String): Boolean {
        return Regex("""\$\{className(_[^\}]*)?\}""").containsMatchIn(path)
    }

}