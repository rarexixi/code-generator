package org.xi.quick.codegeneratorkt.entity

data class Statistics(
        var tableCatalog: String? = null,
        var tableSchema: String? = null,
        var tableName: String? = null,
        var nonUnique: Int? = null,
        var indexSchema: String? = null,
        var indexName: String? = null,
        var seqInIndex: Int? = null,
        var columnName: String? = null,
        var collation: String? = null,
        var cardinality: Long? = null,
        var subPart: Int? = null,
        var packed: String? = null,
        var nullable: String? = null,
        var indexType: String? = null,
        var comment: String? = null,
        var indexComment: String? = null
)