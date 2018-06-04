package org.xi.quick.codegenerator.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.xi.quick.codegenerator.entity.SelectField;
import org.xi.quick.codegenerator.properties.FieldProperties;
import org.xi.quick.codegenerator.properties.GeneratorProperties;
import org.xi.quick.codegenerator.staticdata.DataTypeMapping;
import org.xi.quick.codegenerator.staticdata.StaticConfigData;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 初始化配置数据
 *
 * @author 郗世豪（rarexixi@outlook.com）
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
    GeneratorProperties generator;

    @Autowired
    FieldProperties generatorField;

    @Override
    public void run(String... strings) throws Exception {

        StaticConfigData.DATABASE_NAME = getDatabaseNameFromJdbcUrl(dbUrl);

        StaticConfigData.VALID_STATUS_FIELD = generator.getValidStatusField();
        StaticConfigData.FK_SELECT_FIELDS = generator.getFkSelectFields();

        StaticConfigData.NOT_REQUIRED_FIELD_SET = generatorField.getNotRequired();
        StaticConfigData.IMG_URL_FIELD_SET = generatorField.getImgUrl();
        StaticConfigData.VIDEO_URL_FIELD_SET = generatorField.getVideoUrl();
        StaticConfigData.DOC_URL_FIELD_SET = generatorField.getDocUrl();
        StaticConfigData.PAGE_URL_FIELD_SET = generatorField.getPageUrl();
        StaticConfigData.OTHER_URL_FIELD_SET = generatorField.getOtherUrl();
        StaticConfigData.ALL_URL_FIELD_SET = getAllUrlFieldSet();
        StaticConfigData.CONTENT_FIELD_SET = generatorField.getContent();
        StaticConfigData.IGNORE_SEARCH_FIELD_SET = getIgnoreSearchFieldSet();
        StaticConfigData.SELECT_FIELD_ARRAY = generatorField.getSelect();

        StaticConfigData.CODE_ENCODING = generator.getEncoding();

        StaticConfigData.COMMON_PROPERTIES.putAll(generator.getCommonProperties());
        StaticConfigData.COMMON_PROPERTIES.put("now", new Date());
        StaticConfigData.COMMON_PROPERTIES.put("dbUrl", dbUrl);
        StaticConfigData.COMMON_PROPERTIES.put("dbUsername", dbUsername);
        StaticConfigData.COMMON_PROPERTIES.put("dbPassword", dbPassword);
        StaticConfigData.COMMON_PROPERTIES.put("validStatusField", generator.getValidStatusField());

        DataTypeMapping.DATA_TYPE_MAP = generator.getDataTypeMap();
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
     * 获取所有路径的字段集合
     *
     * @return
     */
    public Set<String> getAllUrlFieldSet() {
        Set<String> urlSet = new HashSet<>();
        urlSet.addAll(generatorField.getImgUrl());
        urlSet.addAll(generatorField.getVideoUrl());
        urlSet.addAll(generatorField.getDocUrl());
        urlSet.addAll(generatorField.getPageUrl());
        urlSet.addAll(generatorField.getOtherUrl());
        return urlSet;
    }

    /**
     * 获取忽略查询的字段集合
     *
     * @return
     */
    public Set<String> getIgnoreSearchFieldSet() {
        Set<String> fieldSet = new HashSet<>();
        fieldSet.addAll(getAllUrlFieldSet());
        fieldSet.addAll(generatorField.getContent());
        return fieldSet;
    }

    public Set<String> getSelectFieldNameSet(SelectField[] select) {
        Set<String> nameSet = new HashSet<>();
        for (SelectField selectField : select) {
            nameSet.addAll(selectField.getNameSet());
        }
        return nameSet;
    }
}
