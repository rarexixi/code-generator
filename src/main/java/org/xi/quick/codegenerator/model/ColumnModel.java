package org.xi.quick.codegenerator.model;

import org.xi.quick.codegenerator.entity.Column;
import org.xi.quick.codegenerator.utils.ColumnUtil;
import org.xi.quick.codegenerator.utils.StringUtil;

public class ColumnModel {

    public ColumnModel() {

    }

    public ColumnModel(Column column) {

        this.databaseName = column.getTableSchema();
        this.tableName = column.getTableName();
        this.columnName = column.getColumnName();
        this.columnPosition = column.getOrdinalPosition();
        this.columnDefault = column.getColumnDefault();
        this.isNullable = column.getIsNullable();
        this.dataType = column.getDataType();
        this.charLength = column.getCharacterMaximumLength();
        this.byteLength = column.getCharacterOctetLength();
        this.columnType = column.getColumnType();
        this.columnKey = column.getColumnKey();
        this.extra = column.getExtra();
        this.columnComment = column.getColumnComment();
    }

    //region 默认

    private String databaseName;
    private String tableName;
    private String columnName;
    private Long columnPosition;
    private String columnDefault;
    private String isNullable;
    private String dataType;
    private Long charLength;
    private Long byteLength;
    private String columnType;
    private String columnKey;
    private String extra;
    private String columnComment;

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public Long getColumnPosition() {
        return columnPosition;
    }

    public void setColumnPosition(Long columnPosition) {
        this.columnPosition = columnPosition;
    }

    public String getColumnDefault() {
        return columnDefault;
    }

    public void setColumnDefault(String columnDefault) {
        this.columnDefault = columnDefault;
    }

    public String getIsNullable() {
        return isNullable;
    }

    public void setIsNullable(String isNullable) {
        this.isNullable = isNullable;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public Long getCharLength() {
        return charLength;
    }

    public void setCharLength(Long charLength) {
        this.charLength = charLength;
    }

    public Long getByteLength() {
        return byteLength;
    }

    public void setByteLength(Long byteLength) {
        this.byteLength = byteLength;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getColumnKey() {
        return columnKey;
    }

    public void setColumnKey(String columnKey) {
        this.columnKey = columnKey;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getColumnComment() {
        return columnComment;
    }

    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment;
    }

    //endregion

    //region 扩展

    public String getColumnFieldName() {
        return StringUtil.getCamelCaseName(this.columnName);
    }

    public String getColumnFieldNameFirstLower() {
        return StringUtil.getFirstLower(StringUtil.getCamelCaseName(this.columnName));
    }

    public String getColumnFieldType() {
        return ColumnUtil.getFieldType(this.dataType);
    }

    public boolean isAutoIncrement() {
        return this.extra.toLowerCase().equals("auto_increment");
    }

    public boolean isPrimaryKey() {
        return this.columnKey.equals("PRI");
    }

    //endregion
}
