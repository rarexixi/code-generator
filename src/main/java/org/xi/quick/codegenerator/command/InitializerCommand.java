package org.xi.quick.codegenerator.command;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.xi.quick.codegenerator.entity.ValidStatusField;
import org.xi.quick.codegenerator.staticdata.StaticConfigData;

import java.io.*;
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
    String datasourceUrl;

    @Value("${field.status.valid}")
    private String validStatusField;

    @Value("${field.status.valid.value}")
    private String statusFieldValidValue;

    @Value("${field.status.invalid.value}")
    private String statusFieldInvalidValue;

    @Value("${field.not_required}")
    private String notRequiredField;

    @Value("${codeEncoding}")
    String codeEncoding;

    @Value("${path.out}")
    String outPath;

    @Value("${path.common.properties}")
    String commonPropertiesPath;

    @Override
    public void run(String... strings) throws Exception {

        StaticConfigData.DATABASE_NAME = getDatabaseNameFromJdbcUrl(datasourceUrl);
        StaticConfigData.VALID_STATUS_FIELD = getValidStatusField();
        StaticConfigData.NOT_REQUIRED_FIELD_SET = getNotRequiredFieldSets();
        StaticConfigData.CODE_ENCODING = codeEncoding;
        StaticConfigData.OUT_DIRECTORY = outPath;
        StaticConfigData.COMMON_PROPERTIES = getCommonProperties();
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
     * 获取公共配置
     *
     * @return
     */
    private Map<Object, Object> getCommonProperties() {

        Map<Object, Object> commonPropertiesMap = new HashMap<>();
        Properties properties = new Properties();
        try (InputStream inputStream = new FileInputStream(commonPropertiesPath);
             Reader reader = new InputStreamReader(inputStream, codeEncoding)) {
            properties.load(reader);
            properties.forEach((key, value) -> commonPropertiesMap.put(key, value));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        commonPropertiesMap.put("now", new Date());

        return commonPropertiesMap;
    }
}
