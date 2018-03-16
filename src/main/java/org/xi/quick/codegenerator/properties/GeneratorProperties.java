package org.xi.quick.codegenerator.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.xi.quick.codegenerator.entity.FkSelectField;
import org.xi.quick.codegenerator.entity.ValidStatusField;

import java.util.*;

@Component
@ConfigurationProperties(prefix = "generator")
public class GeneratorProperties {

    public GeneratorProperties() {
        commonProperties = new HashMap<>();
        dataTypeMap = new HashMap<>();
    }

    /**
     * 模版输出编码
     */
    private String encoding;

    /**
     * 有效性字段
     */
    private ValidStatusField validStatusField;
    /**
     * 外键选择字段
     */
    private FkSelectField[] fkSelectFields;

    /**
     * 其他公共属性，用于生成到页面
     */
    private Map<String, String> commonProperties;
    /**
     * 数据对应关系
     */
    private Map<String, String> dataTypeMap;

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public ValidStatusField getValidStatusField() {
        return validStatusField;
    }

    public void setValidStatusField(ValidStatusField validStatusField) {
        this.validStatusField = validStatusField;
    }

    public FkSelectField[] getFkSelectFields() {
        return fkSelectFields;
    }

    public void setFkSelectFields(FkSelectField[] fkSelectFields) {
        this.fkSelectFields = fkSelectFields;
    }

    public Map<String, String> getCommonProperties() {
        return commonProperties;
    }

    public void setCommonProperties(Map<String, String> commonProperties) {
        this.commonProperties = commonProperties;
    }

    public Map<String, String> getDataTypeMap() {
        return dataTypeMap;
    }

    public void setDataTypeMap(Map<String, String> dataTypeMap) {
        this.dataTypeMap = dataTypeMap;
    }
}
