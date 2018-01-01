package org.xi.quick.codegenerator.model;

import org.xi.quick.codegenerator.entity.Column;
import org.xi.quick.codegenerator.staticdata.StaticConfigData;
import org.xi.quick.codegenerator.utils.ColumnUtil;
import org.xi.quick.codegenerator.utils.StringUtil;

public class ColumnModel {

    public ColumnModel(Column column) {

        if (column != null) {
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
        this.notRequired = StaticConfigData.NOT_REQUIRED_FIELD_SET.contains(this.columnName);
        this.validStatus = StaticConfigData.VALID_STATUS_FIELD.getFieldName().equals(this.columnName);
        initExtends();
    }

    //region 默认

    /**
     * 数据库名
     */
    private String databaseName;
    /**
     * 表名
     */
    private String tableName;
    /**
     * 列名
     */
    private String columnName;
    /**
     * 列位置
     */
    private Long columnPosition;
    /**
     * 默认值
     */
    private String columnDefault;
    /**
     * 是否为空
     */
    private String isNullable;
    /**
     * 数据类型，int，varchar
     */
    private String dataType;
    /**
     * 字符长度
     */
    private Long charLength;
    /**
     * 字节长度
     */
    private Long byteLength;
    /**
     * 列类型，int(11)，varchar(50)
     */
    private String columnType;
    /**
     *
     */
    private String columnKey;
    /**
     *
     */
    private String extra;
    /**
     * 列说明
     */
    private String columnComment;
    /**
     * 是否不需要填写
     */
    private boolean notRequired;
    /**
     * 是否有效性字段
     */
    private boolean validStatus;

    public String getDatabaseName() {
        return databaseName;
    }

    public String getTableName() {
        return tableName;
    }

    public String getColumnName() {
        return columnName;
    }

    public Long getColumnPosition() {
        return columnPosition;
    }

    public String getColumnDefault() {
        return columnDefault;
    }

    public String getIsNullable() {
        return isNullable;
    }

    public String getDataType() {
        return dataType;
    }

    public Long getCharLength() {
        return charLength;
    }

    public Long getByteLength() {
        return byteLength;
    }

    public String getColumnType() {
        return columnType;
    }

    public String getColumnKey() {
        return columnKey;
    }

    public String getExtra() {
        return extra;
    }

    public String getColumnComment() {
        return columnComment;
    }

    public boolean isNotRequired() {
        return notRequired;
    }

    public boolean isValidStatus() {
        return validStatus;
    }

    //endregion

    //region 扩展

    private String columnFieldName;
    private String columnFieldNameFirstLower;
    private String columnFieldType;
    private boolean autoIncrement;
    private boolean primaryKey;
    private boolean index;
    private boolean endWithId;

    public void initExtends() {
        columnFieldName = StringUtil.getCamelCaseName(this.columnName);
        columnFieldNameFirstLower = StringUtil.getFirstLower(columnFieldName);
        columnFieldType = ColumnUtil.getFieldType(this.dataType);
        autoIncrement = extra.toLowerCase().equals("auto_increment");
        primaryKey = columnKey.equals("PRI");
        endWithId = columnName.endsWith("_id");
    }

    /**
     * 获取列对应的JAVA字段名
     *
     * @return
     */
    public String getColumnFieldName() {
        return columnFieldName;
    }

    /**
     * 获取列对应的JAVA字段名首字母小写
     *
     * @return
     */
    public String getColumnFieldNameFirstLower() {
        return columnFieldNameFirstLower;
    }

    /**
     * 获取列对应的JAVA字段类型
     *
     * @return
     */
    public String getColumnFieldType() {
        return columnFieldType;
    }

    /**
     * 是否自增长
     *
     * @return
     */
    public boolean isAutoIncrement() {
        return autoIncrement;
    }

    /**
     * 是否是主键
     *
     * @return
     */
    public boolean isPrimaryKey() {
        return primaryKey;
    }

    /**
     * 是否以_id结尾
     *
     * @return
     */
    public boolean isEndWithId() {
        return endWithId;
    }

    /**
     * 是否是索引
     *
     * @return
     */
    public boolean isIndex() {
        return index;
    }

    public void setIndex(boolean index) {
        this.index = index;
    }

    //endregion
}
