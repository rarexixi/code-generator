package org.xi.quick.codegenerator.entity;

import java.util.Set;

public class SelectField {

    public SelectField() {
        tableName = null;
        options = new SelectOption[0];
    }

    private String tableName;
    private Set<String> nameSet;
    private SelectOption[] options;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Set<String> getNameSet() {
        return nameSet;
    }

    public void setNameSet(Set<String> nameSet) {
        this.nameSet = nameSet;
    }

    public SelectOption[] getOptions() {
        return options;
    }

    public void setOptions(SelectOption[] options) {
        this.options = options;
    }
}
