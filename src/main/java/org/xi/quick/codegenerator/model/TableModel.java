package org.xi.quick.codegenerator.model;

import org.xi.quick.codegenerator.entity.Table;
import org.xi.quick.codegenerator.utils.StringUtil;

import java.util.List;
import java.util.stream.Collectors;

public class TableModel {

    public TableModel() {

    }

    public TableModel(Table table) {
        this.databaseName = table.getTableSchema();
        this.tableName = table.getTableName();
        this.tableComment = table.getTableComment();
    }

    //region 默认

    private String databaseName;
    private String tableName;
    private String tableComment;

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

    public List<ColumnModel> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnModel> columns) {
        this.columns = columns;
    }

    //endregion

    //region 扩展

    public String getTableClassName() {
        return StringUtil.getCamelCaseName(this.tableName);
    }

    public String getTableClassNameFirstLower() {
        return StringUtil.getFirstLower(StringUtil.getCamelCaseName(this.tableName));
    }

    public ColumnModel getPrimaryKey() {
        List<ColumnModel> priKeys =
                this.columns
                        .stream()
                        .filter(column -> column.getColumnKey().equals("PRI"))
                        .collect(Collectors.toList());

        return priKeys.isEmpty() ? null : priKeys.get(0);
    }

    public Boolean getHasIsActive() {
        List<ColumnModel> isActiveColumns =
                this.columns
                        .stream()
                        .filter(column -> column.getColumnName().equals("is_active"))
                        .collect(Collectors.toList());

        return isActiveColumns != null && isActiveColumns.size() == 1;
    }

    //endregion
}
