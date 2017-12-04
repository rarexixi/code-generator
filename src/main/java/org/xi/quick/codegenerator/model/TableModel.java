package org.xi.quick.codegenerator.model;

import org.xi.quick.codegenerator.entity.Table;
import org.xi.quick.codegenerator.entity.ValidStatusField;
import org.xi.quick.codegenerator.utils.StringUtil;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TableModel {

    public TableModel() {

    }

    public TableModel(Table table, ValidStatusField validStatusField) {
        this.databaseName = table.getTableSchema();
        this.tableName = table.getTableName();
        this.tableComment = table.getTableComment();
        this.validStatusField = validStatusField;
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

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableComment() {
        return tableComment;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }

    public ValidStatusField getValidStatusField() {
        return validStatusField;
    }

    public void setValidStatusField(ValidStatusField validStatusField) {
        this.validStatusField = validStatusField;
    }

    public List<ColumnModel> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnModel> columns) {
        this.columns = columns;
    }

    //endregion

    //region 扩展

    /**
     * 获取表对应的JAVA类名
     *
     * @return
     */
    public String getTableClassName() {
        return StringUtil.getCamelCaseName(this.tableName);
    }

    /**
     * 获取表对应的JAVA类名首字母小写
     *
     * @return
     */
    public String getTableClassNameFirstLower() {
        return StringUtil.getFirstLower(StringUtil.getCamelCaseName(this.tableName));
    }

    /**
     * 是否有主键
     *
     * @return
     */
    public boolean getHasPrimaryKey() {
        List<ColumnModel> primaryKey = getPrimaryKey();
        return primaryKey != null && !primaryKey.isEmpty();
    }

    /**
     * 获取主键列表
     *
     * @return
     */
    public List<ColumnModel> getPrimaryKey() {
        List<ColumnModel> priKeys =
                this.columns
                        .stream()
                        .filter(column -> column.getColumnKey().equals("PRI"))
                        .collect(Collectors.toList());

        return priKeys;
    }

    /**
     * 主键对应的JAVA参数，单个(Integer id)，多个(Integer userId, Integer userTypeId)
     *
     * @return
     */
    public String getPrimaryKeyParameters() {
        List<ColumnModel> primaryKey = getPrimaryKey();
        if (primaryKey == null || primaryKey.isEmpty()) return "";
        return primaryKey
                .stream()
                .map(column -> column.getColumnFieldType() + " " + column.getColumnFieldNameFirstLower())
                .collect(Collectors.joining(", "));
    }

    /**
     * 主键对应的JAVA参数值，单个(id) ，多个(userId, userTypeId)
     *
     * @return
     */
    public String getPrimaryKeyParameterValues() {
        List<ColumnModel> primaryKey = getPrimaryKey();
        if (primaryKey == null || primaryKey.isEmpty()) return "";
        return primaryKey
                .stream()
                .map(column -> column.getColumnFieldNameFirstLower())
                .collect(Collectors.joining(", "));
    }

    /**
     * 有效性字段列
     *
     * @return
     */
    public ColumnModel getValidStatusColumn() {

        Optional<ColumnModel> columnOptional =
                this.columns
                        .stream()
                        .filter(column -> column.getColumnName().equals(validStatusField.getFieldName()))
                        .findFirst();

        return columnOptional.isPresent() ? columnOptional.get() : null;
    }

    //endregion
}
