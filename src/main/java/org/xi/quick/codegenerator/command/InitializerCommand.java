package org.xi.quick.codegenerator.command;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.xi.quick.codegenerator.entity.ValidStatusField;
import org.xi.quick.codegenerator.staticdata.DataTypeMapping;
import org.xi.quick.codegenerator.staticdata.StaticConfigData;
import org.xi.quick.codegenerator.utils.PropertiesUtil;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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

    @Value("${validStatusField.fieldName}")
    private String validStatusField;

    @Value("${validStatusField.validValue}")
    private String statusFieldValidValue;

    @Value("${validStatusField.invalidValue}")
    private String statusFieldInvalidValue;

    @Value("${field.not_required}")
    private String notRequiredField;

    @Value("${codeEncoding}")
    String codeEncoding;

    @Value("${path.datatype.properties}")
    String dataTypePropertiesPath;

    @Override
    public void run(String... strings) throws Exception {

        StaticConfigData.DATABASE_NAME = getDatabaseNameFromJdbcUrl(datasourceUrl);
        StaticConfigData.VALID_STATUS_FIELD = getValidStatusField();
        StaticConfigData.NOT_REQUIRED_FIELD_SET = getNotRequiredFieldSets();
        StaticConfigData.CODE_ENCODING = codeEncoding;

        DataTypeMapping.TYPE_MAP = getTypeMap();
    }

    /**
     * 获取数据有效性字段
     *
     * @return
     */
    private ValidStatusField getValidStatusField() {

        ValidStatusField field = new ValidStatusField();
        field.setFieldName(validStatusField);
        field.setValidValue(statusFieldValidValue);
        field.setInvalidValue(statusFieldInvalidValue);

        return field;
    }

    /**
     * 获取不需要的字段集合
     *
     * @return
     */
    private Set<String> getNotRequiredFieldSets() {
        return Arrays.asList(notRequiredField.split(",")).stream()
                .collect(Collectors.toSet());
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

        Map<Object, Object> commonPropertiesMap = PropertiesUtil.getProperties(dataTypePropertiesPath, codeEncoding);
        return commonPropertiesMap;
    }
}
