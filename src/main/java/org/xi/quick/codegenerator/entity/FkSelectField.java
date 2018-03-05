package org.xi.quick.codegenerator.entity;

import org.xi.quick.codegenerator.utils.StringUtil;

import java.util.Set;

public class FkSelectField {

    /**
     * 当前表外键名称
     */
    private Set<String> nameSet;
    /**
     * 关联表名
     */
    private String foreignTable;
    /**
     * 关联表的关联key的字段名
     */
    private String keyField;
    /**
     * 关联表的关联value的字段名
     */
    private String valueField;

    public Set<String> getNameSet() {
        return nameSet;
    }

    public void setNameSet(Set<String> nameSet) {
        this.nameSet = nameSet;
    }

    public void setForeignTable(String foreignTable) {
        this.foreignTable = foreignTable;
    }

    public String getForeignTable() {
        return foreignTable;
    }

    public String getForeignClass() {
        return StringUtil.getCamelCaseName(foreignTable);
    }

    public String getKeyField() {
        return StringUtil.getCamelCaseName(keyField);
    }

    public void setKeyField(String keyField) {
        this.keyField = keyField;
    }

    public String getValueField() {
        return StringUtil.getCamelCaseName(valueField);
    }

    public void setValueField(String valueField) {
        this.valueField = valueField;
    }
}

