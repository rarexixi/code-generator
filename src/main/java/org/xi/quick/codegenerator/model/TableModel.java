package org.xi.quick.codegenerator.model;

import org.xi.quick.codegenerator.entity.Table;
import org.xi.quick.codegenerator.entity.ValidStatusField;
import org.xi.quick.codegenerator.utils.StringUtil;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TableModel {

    public TableModel(Table table, ValidStatusField validStatusField, List<ColumnModel> columns) {
        this.databaseName = table.getTableSchema();
        this.tableName = table.getTableName();
        this.tableComment = table.getTableComment();
        this.validStatusField = validStatusField;
        this.columns = columns;

        initTableClassName();
        initColumns();
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
     * 表说明
     */
    private String tableComment;

    /**
     * 有效性字段
     */
    private ValidStatusField validStatusField;

    /**
     * 表字段列表
     */
    private List<ColumnModel> columns;

    public String getDatabaseName() {
        return databaseName;
    }

    public String getTableName() {
        return tableName;
    }

    public String getTableComment() {
        return tableComment;
    }

    public ValidStatusField getValidStatusField() {
        return validStatusField;
    }

    public List<ColumnModel> getColumns() {
        return columns;
    }

    //endregion

    //region 扩展

    private String tableClassName;
    private String tableClassNameFirstLower;

    private List<ColumnModel> primaryKey;
    private boolean hasPrimaryKey;
    private boolean hasAutoIncrementUniquePrimaryKey;
    private ColumnModel uniquePrimaryKey;
    private String primaryKeyParameters;
    private String primaryKeyParameterValues;
    private ColumnModel validStatusColumn;


    public void initTableClassName() {
        tableClassName = StringUtil.getCamelCaseName(this.tableName);
        tableClassNameFirstLower = StringUtil.getFirstLower(tableClassName);
    }

    public void initColumns() {
        this.primaryKey =
                this.columns
                        .stream()
                        .filter(column -> column.getColumnKey().equals("PRI"))
                        .collect(Collectors.toList());
        this.hasPrimaryKey = this.primaryKey != null && !primaryKey.isEmpty();
        if (hasPrimaryKey) {
            this.uniquePrimaryKey = this.primaryKey.size() == 1 ? this.primaryKey.get(0) : null;
            this.hasAutoIncrementUniquePrimaryKey = this.uniquePrimaryKey != null && this.uniquePrimaryKey.isAutoIncrement();
            this.primaryKeyParameters = primaryKey
                    .stream()
                    .map(column -> column.getColumnFieldType() + " " + column.getColumnFieldNameFirstLower())
                    .collect(Collectors.joining(", "));
            this.primaryKeyParameterValues = primaryKey
                    .stream()
                    .map(column -> column.getColumnFieldNameFirstLower())
                    .collect(Collectors.joining(", "));
        }
        Optional<ColumnModel> columnOptional =
                this.columns
                        .stream()
                        .filter(column -> column.getColumnName().equals(validStatusField.getFieldName()))
                        .findFirst();

        this.validStatusColumn = columnOptional.isPresent() ? columnOptional.get() : null;

    }

    /**
     * 获取表对应的JAVA类名
     *
     * @return
     */
    public String getTableClassName() {
        return tableClassName;
    }


    /**
     * 获取表对应的JAVA类名首字母小写
     *
     * @return
     */
    public String getTableClassNameFirstLower() {
        return tableClassNameFirstLower;
    }

    /**
     * 获取主键列表
     *
     * @return
     */
    public List<ColumnModel> getPrimaryKey() {
        return primaryKey;
    }

    /**
     * 是否有主键
     *
     * @return
     */
    public boolean isHasPrimaryKey() {
        return hasPrimaryKey;
    }

    /**
     * 是否有唯一自增主键
     *
     * @return
     */
    public boolean isHasAutoIncrementUniquePrimaryKey() {
        return hasAutoIncrementUniquePrimaryKey;
    }

    /**
     * 获取唯一主键
     *
     * @return
     */
    public ColumnModel getUniquePrimaryKey() {
        return uniquePrimaryKey;
    }

    /**
     * 主键对应的JAVA参数，单个(Integer id)，多个(Integer userId, Integer userTypeId)
     *
     * @return
     */
    public String getPrimaryKeyParameters() {
        return primaryKeyParameters;
    }


    /**
     * 主键对应的JAVA参数值，单个(id) ，多个(userId, userTypeId)
     *
     * @return
     */
    public String getPrimaryKeyParameterValues() {
        return primaryKeyParameterValues;
    }

    /**
     * 有效性字段列
     *
     * @return
     */
    public ColumnModel getValidStatusColumn() {
        return validStatusColumn;
    }

    //endregion
}
