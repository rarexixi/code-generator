package org.xi.quick.codegenerator.model;

import org.xi.quick.codegenerator.entity.Column;
import org.xi.quick.codegenerator.entity.FkSelectField;
import org.xi.quick.codegenerator.entity.SelectField;
import org.xi.quick.codegenerator.entity.SelectOption;
import org.xi.quick.codegenerator.staticdata.DataTypeMapping;
import org.xi.quick.codegenerator.staticdata.StaticConfigData;
import org.xi.quick.codegenerator.utils.StringUtil;

import java.util.Arrays;

public class ColumnModel {

    public ColumnModel(Column column) {

        if (column != null) {
            databaseName = column.getTableSchema();
            tableName = column.getTableName();
            columnName = column.getColumnName();
            columnPosition = column.getOrdinalPosition();
            columnDefault = column.getColumnDefault();
            isNullable = column.getIsNullable();
            dataType = column.getDataType();
            charLength = column.getCharacterMaximumLength();
            byteLength = column.getCharacterOctetLength();
            columnType = column.getColumnType();
            columnKey = column.getColumnKey();
            extra = column.getExtra();
            columnComment = column.getColumnComment();
            index = column.getIndex();
        }
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
     * 是否是索引
     */
    private boolean index;

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
        return StringUtil.isNullOrEmpty(columnComment) ? columnFieldName : columnComment;
    }

    public boolean isIndex() {
        return index;
    }

    //endregion

    //region 扩展

    private String columnFieldName;
    private String columnFieldNameFirstLower;
    private String columnFieldType;

    private FkSelectField fkSelectField;
    private boolean fkSelect;

    private boolean autoIncrement;
    private boolean primaryKey;
    private boolean validStatus;

    private boolean select;
    private SelectOption[] selectOptions;
    private boolean notRequired;

    private boolean imgUrl;
    private boolean videoUrl;
    private boolean docUrl;
    private boolean pageUrl;
    private boolean otherUrl;
    private boolean url;
    private boolean content;
    private boolean ignoreSearch;

    public void initExtends() {
        columnFieldName = StringUtil.getCamelCaseName(this.columnName);
        columnFieldNameFirstLower = StringUtil.getFirstLower(columnFieldName);
        columnFieldType = DataTypeMapping.getFieldType(this.dataType);
        autoIncrement = extra.toLowerCase().equals("auto_increment");
        primaryKey = columnKey.equals("PRI");
        validStatus = StaticConfigData.VALID_STATUS_FIELD.getFieldName().equals(this.columnName);

        // 判断是否是逻辑外键选择项字段
        fkSelect = false;
        for (FkSelectField field : StaticConfigData.FK_SELECT_FIELDS) {
            if (field.getNameSet() != null && field.getNameSet().contains(columnName)) {
                fkSelectField = field;
                fkSelect = true;
                break;
            }
        }

        // 判断是否是选择字段
        select = false;
        for (SelectField selectField : StaticConfigData.SELECT_FIELD_ARRAY) {
            if (selectField.getNameSet().contains(columnName)
                    && (StringUtil.isNullOrEmpty(selectField.getTableName()) || selectField.getTableName().equals(tableName))) {
                select = true;
                selectOptions = selectField.getOptions();
            }
        }

        notRequired = StaticConfigData.NOT_REQUIRED_FIELD_SET.contains(columnName);

        imgUrl = StaticConfigData.IMG_URL_FIELD_SET.contains(columnName);
        videoUrl = StaticConfigData.VIDEO_URL_FIELD_SET.contains(columnName);
        docUrl = StaticConfigData.DOC_URL_FIELD_SET.contains(columnName);
        pageUrl = StaticConfigData.PAGE_URL_FIELD_SET.contains(columnName);
        otherUrl = StaticConfigData.OTHER_URL_FIELD_SET.contains(columnName);
        url = StaticConfigData.ALL_URL_FIELD_SET.contains(columnName);
        content = StaticConfigData.CONTENT_FIELD_SET.contains(columnName);
        ignoreSearch = StaticConfigData.IGNORE_SEARCH_FIELD_SET.contains(columnName);
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

    public FkSelectField getFkSelectField() {
        return fkSelectField;
    }

    public boolean isFkSelect() {
        return fkSelect;
    }

    /**
     * 是自增长
     *
     * @return
     */
    public boolean isAutoIncrement() {
        return autoIncrement;
    }

    /**
     * 是主键
     *
     * @return
     */
    public boolean isPrimaryKey() {
        return primaryKey;
    }

    /**
     * 有效性字段
     *
     * @return
     */
    public boolean isValidStatus() {
        return validStatus;
    }

    /**
     * 是选择项
     *
     * @return
     */
    public boolean isSelect() {
        return select;
    }

    /**
     * 选择项列表
     *
     * @return
     */
    public SelectOption[] getSelectOptions() {
        return selectOptions;
    }

    /**
     * 不需要填写
     *
     * @return
     */
    public boolean isNotRequired() {
        return notRequired;
    }

    /**
     * 是图片路径
     *
     * @return
     */
    public boolean isImgUrl() {
        return imgUrl;
    }

    /**
     * 是视频路径
     *
     * @return
     */
    public boolean isVideoUrl() {
        return videoUrl;
    }

    /**
     * 是文档路径
     *
     * @return
     */
    public boolean isDocUrl() {
        return docUrl;
    }

    /**
     * 是页面路径
     *
     * @return
     */
    public boolean isPageUrl() {
        return pageUrl;
    }

    /**
     * 是其他路径
     *
     * @return
     */
    public boolean isOtherUrl() {
        return otherUrl;
    }

    /**
     * 是路径
     *
     * @return
     */
    public boolean isUrl() {
        return url;
    }

    /**
     * 是内容
     *
     * @return
     */
    public boolean isContent() {
        return content;
    }

    /**
     * 忽略查询
     *
     * @return
     */
    public boolean isIgnoreSearch() {
        return ignoreSearch;
    }

    //endregion
}
