package org.xi.quick.codegenerator.model;

import org.xi.quick.codegenerator.entity.Statistics;

/**
 * @author 郗世豪（xish@cloud-young.com）
 * @date 2017/12/13 11:34
 */
public class StatisticsModel {

    public StatisticsModel(Statistics statistics) {

        if (statistics != null) {
            tableCatalog = statistics.getIndexComment();
            tableSchema = statistics.getTableSchema();
            tableName = statistics.getTableName();
            nonUnique = statistics.getNonUnique();
            indexSchema = statistics.getIndexSchema();
            indexName = statistics.getIndexName();
            seqInIndex = statistics.getSeqInIndex();
            columnName = statistics.getColumnName();
            collation = statistics.getCollation();
            cardinality = statistics.getCardinality();
            subPart = statistics.getSubPart();
            packed = statistics.getPacked();
            nullable = statistics.getNullable();
            indexType = statistics.getIndexType();
            comment = statistics.getComment();
            indexComment = statistics.getIndexComment();
        }
    }

    private String tableCatalog;
    private String tableSchema;
    private String tableName;
    private Integer nonUnique;
    private String indexSchema;
    private String indexName;
    private Integer seqInIndex;
    private String columnName;
    private String collation;
    private Long cardinality;
    private Integer subPart;
    private String packed;
    private String nullable;
    private String indexType;
    private String comment;
    private String indexComment;

    public String getTableCatalog() {
        return tableCatalog;
    }

    public void setTableCatalog(String tableCatalog) {
        this.tableCatalog = tableCatalog;
    }

    public String getTableSchema() {
        return tableSchema;
    }

    public void setTableSchema(String tableSchema) {
        this.tableSchema = tableSchema;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Integer getNonUnique() {
        return nonUnique;
    }

    public void setNonUnique(Integer nonUnique) {
        this.nonUnique = nonUnique;
    }

    public String getIndexSchema() {
        return indexSchema;
    }

    public void setIndexSchema(String indexSchema) {
        this.indexSchema = indexSchema;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public Integer getSeqInIndex() {
        return seqInIndex;
    }

    public void setSeqInIndex(Integer seqInIndex) {
        this.seqInIndex = seqInIndex;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getCollation() {
        return collation;
    }

    public void setCollation(String collation) {
        this.collation = collation;
    }

    public Long getCardinality() {
        return cardinality;
    }

    public void setCardinality(Long cardinality) {
        this.cardinality = cardinality;
    }

    public Integer getSubPart() {
        return subPart;
    }

    public void setSubPart(Integer subPart) {
        this.subPart = subPart;
    }

    public String getPacked() {
        return packed;
    }

    public void setPacked(String packed) {
        this.packed = packed;
    }

    public String getNullable() {
        return nullable;
    }

    public void setNullable(String nullable) {
        this.nullable = nullable;
    }

    public String getIndexType() {
        return indexType;
    }

    public void setIndexType(String indexType) {
        this.indexType = indexType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getIndexComment() {
        return indexComment;
    }

    public void setIndexComment(String indexComment) {
        this.indexComment = indexComment;
    }
}
