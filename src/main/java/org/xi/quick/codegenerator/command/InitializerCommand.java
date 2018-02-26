package org.xi.quick.codegenerator.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.xi.quick.codegenerator.config.GeneratorConfigProperties;
import org.xi.quick.codegenerator.staticdata.DataTypeMapping;
import org.xi.quick.codegenerator.staticdata.StaticConfigData;
import org.xi.quick.codegenerator.utils.PropertiesUtil;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 初始化配置数据
 *
 * @author 郗世豪（xish@cloud-young.com）
 * @date 2018/01/01 10:58
 */
@Component
@Order(1)
public class InitializerCommand implements CommandLineRunner {

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    @Autowired
    GeneratorConfigProperties generatorConfigProperties;

    @Override
    public void run(String... strings) throws Exception {

        StaticConfigData.DATABASE_NAME = getDatabaseNameFromJdbcUrl(datasourceUrl);

        StaticConfigData.VALID_STATUS_FIELD = generatorConfigProperties.getValidStatusField();

        StaticConfigData.NOT_REQUIRED_FIELD_SET = generatorConfigProperties.getNotRequiredFieldSet();
        StaticConfigData.SELECT_FIELD_SET = generatorConfigProperties.getSelectFieldSet();
        StaticConfigData.IMG_URL_FIELD_SET = generatorConfigProperties.getImgUrlFieldSet();
        StaticConfigData.VIDEO_URL_FIELD_SET = generatorConfigProperties.getVideoUrlFieldSet();
        StaticConfigData.DOC_URL_FIELD_SET = generatorConfigProperties.getDocUrlFieldSet();
        StaticConfigData.PAGE_URL_FIELD_SET = generatorConfigProperties.getPageUrlFieldSet();
        StaticConfigData.OTHER_URL_FIELD_SET = generatorConfigProperties.getOtherUrlFieldSet();
        StaticConfigData.ALL_URL_FIELD_SET = generatorConfigProperties.getAllUrlFieldSet();
        StaticConfigData.CONTENT_FIELD_SET = generatorConfigProperties.getContentFieldSet();
        StaticConfigData.IGNORE_SEARCH_FIELD_SET = generatorConfigProperties.getIgnoreSearchFieldSet();

        StaticConfigData.CODE_ENCODING = generatorConfigProperties.getEncoding();

        DataTypeMapping.TYPE_MAP = getTypeMap();
    }

    /**
     * 从数据库连接串获取数据库名
     *
     * @param url
     * @return
     */
    private String getDatabaseNameFromJdbcUrl(String url) {

        Pattern pattern = Pattern.compile("/[^(/|\\?)]*\\?");
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            String group = matcher.group();
            return group.substring(1, group.length() - 1);
        }

        return "";
    }

    /**
     * 获取数据映射关系
     *
     * @return
     */
    private Map<Object, Object> getTypeMap() {

        Map<Object, Object> commonPropertiesMap = PropertiesUtil.getProperties(
                generatorConfigProperties.getDataTypePropertiesPath(), generatorConfigProperties.getEncoding());
        return commonPropertiesMap;
    }
}
