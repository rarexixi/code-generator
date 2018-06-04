package org.xi.quick.codegenerator.entity;

/**
 * @author 郗世豪（rarexixi@outlook.com）
 * @date 2017/12/04 11:37
 */
public class ValidStatusField {

    /**
     * 列名称
     */
    private String fieldName;

    /**
     * 有效性值
     */
    private String validValue;
    /**
     * 无效性值
     */
    private String invalidValue;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getValidValue() {
        return validValue;
    }

    public void setValidValue(String validValue) {
        this.validValue = validValue;
    }

    public String getInvalidValue() {
        return invalidValue;
    }

    public void setInvalidValue(String invalidValue) {
        this.invalidValue = invalidValue;
    }
}
