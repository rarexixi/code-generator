package org.xi.quick.codegeneratorkt.model

import org.xi.quick.codegeneratorkt.entity.Index

class IndexModel(statistics: Index) {

    var tableCatalog: String? = statistics.indexComment
    var tableSchema: String? = statistics.tableSchema
    var tableName: String? = statistics.tableName
    var nonUnique: Int? = statistics.nonUnique
    var indexSchema: String? = statistics.indexSchema
    var indexName: String? = statistics.indexName
    var seqInIndex: Int? = statistics.seqInIndex
    var columnName: String? = statistics.columnName
    var collation: String? = statistics.collation
    var cardinality: Long? = statistics.cardinality
    var subPart: Int? = statistics.subPart
    var packed: String? = statistics.packed
    var nullable: String? = statistics.nullable
    var indexType: String? = statistics.indexType
    var comment: String? = statistics.comment
    var indexComment: String? = statistics.indexComment
}