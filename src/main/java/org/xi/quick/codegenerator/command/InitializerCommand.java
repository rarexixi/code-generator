package org.xi.quick.codegenerator.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.xi.quick.codegenerator.config.GeneratorConfigProperties;
import org.xi.quick.codegenerator.staticdata.DataTypeMapping;
import org.xi.quick.codegenerator.staticdata.StaticConfigData;

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
    private String dbUrl;
    @Value("${spring.datasource.username}")
    private String dbUsername;
    @Value("${spring.datasource.password}")
    private String dbPassword;

    @Autowired
    GeneratorConfigProperties generatorConfigProperties;

    @Override
    public void run(String... strings) throws Exception {

        StaticConfigData.DATABASE_NAME = getDatabaseNameFromJdbcUrl(dbUrl);

        StaticConfigData.VALID_STATUS_FIELD = generatorConfigProperties.getValidStatusField();
        StaticConfigData.FK_SELECT_FIELDS = generatorConfigProperties.getFkSelectFields();

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

        StaticConfigData.COMMON_PROPERTIES.putAll(generatorConfigProperties.getCommonProperties());
        StaticConfigData.COMMON_PROPERTIES.put("now", new Date());
        StaticConfigData.COMMON_PROPERTIES.put("dbUrl", dbUrl);
        StaticConfigData.COMMON_PROPERTIES.put("dbUsername", dbUsername);
        StaticConfigData.COMMON_PROPERTIES.put("dbPassword", dbPassword);
        StaticConfigData.COMMON_PROPERTIES.put("validStatusField", generatorConfigProperties.getValidStatusField());

        DataTypeMapping.DATA_TYPE_MAP = generatorConfigProperties.getDataTypeMap();
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

}
