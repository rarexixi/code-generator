package org.xi.quick.codegenerator.model;

import org.xi.quick.codegenerator.entity.Table;
import org.xi.quick.codegenerator.entity.ValidStatusField;
import org.xi.quick.codegenerator.staticdata.StaticConfigData;
import org.xi.quick.codegenerator.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class TableModel {

    public TableModel(Table table, List<ColumnModel> columns, List<StatisticsModel> statistics) {

        databaseName = table.getTableSchema();
        tableName = table.getTableName();
        tableClassName = StringUtils.getCamelCaseName(tableName);
        String comment = table.getTableComment();
        tableComment = StringUtils.isEmpty(comment) ? tableClassName : comment;
        this.columns = columns;
        this.statistics = statistics;

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
     * 表对应的JAVA类名
     */
    private String tableClassName;
    /**
     * 表说明
     */
    private String tableComment;

    /**
     * 表字段列表
     */
    private List<ColumnModel> columns;

    /**
     * 索引列表
     */
    private List<StatisticsModel> statistics;

    public String getDatabaseName() {
        return databaseName;
    }

    public String getTableName() {
        return tableName;
    }

    public String getTableClassName() {
        return tableClassName;
    }

    public String getTableComment() {
        return tableComment;
    }

    public ValidStatusField getValidStatusField() {
        return StaticConfigData.VALID_STATUS_FIELD;
    }

    public List<ColumnModel> getColumns() {
        return columns;
    }

    public List<StatisticsModel> getStatistics() {
        return statistics;
    }

    //endregion

    //region 扩展

    private List<ColumnModel> primaryKey;
    private boolean hasPrimaryKey;
    private boolean hasAutoIncrementUniquePrimaryKey;
    private ColumnModel uniquePrimaryKey;
    private String primaryKeyParameters;
    private String primaryKeyParameterValues;
    private String primaryKeyOldParameters;
    private String primaryKeyOldParameterValues;
    private ColumnModel validStatusColumn;

    public void initColumns() {

        primaryKey = new ArrayList<>();
        for (ColumnModel column : columns) {
            if (column.getColumnKey().equals("PRI")) {
                primaryKey.add(column);
            }
        }

        hasPrimaryKey = primaryKey != null && !primaryKey.isEmpty();
        if (hasPrimaryKey) {
            uniquePrimaryKey = primaryKey.size() == 1 ? primaryKey.get(0) : null;
            hasAutoIncrementUniquePrimaryKey = uniquePrimaryKey != null && uniquePrimaryKey.isAutoIncrement();
            primaryKeyParameters = primaryKey
                    .stream()
                    .map(column -> column.getColumnFieldType() + " " + column.getColumnFieldNameFirstLower())
                    .collect(Collectors.joining(", "));
            primaryKeyParameterValues = primaryKey
                    .stream()
                    .map(column -> column.getColumnFieldNameFirstLower())
                    .collect(Collectors.joining(", "));
            primaryKeyOldParameters = primaryKey
                    .stream()
                    .map(column -> column.getColumnFieldType() + " old" + column.getColumnFieldName())
                    .collect(Collectors.joining(", "));
            primaryKeyOldParameterValues = primaryKey
                    .stream()
                    .map(column -> "old" + column.getColumnFieldName())
                    .collect(Collectors.joining(", "));
        }
        Optional<ColumnModel> columnOptional =
                columns
                        .stream()
                        .filter(column -> column.isValidStatus())
                        .findFirst();

        validStatusColumn = columnOptional.isPresent() ? columnOptional.get() : null;

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

    public String getPrimaryKeyOldParameters() {
        return primaryKeyOldParameters;
    }

    public String getPrimaryKeyOldParameterValues() {
        return primaryKeyOldParameterValues;
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
