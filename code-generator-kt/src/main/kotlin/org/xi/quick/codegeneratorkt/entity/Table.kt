package org.xi.quick.codegeneratorkt.entity

data class Table(
        var tableCatalog: String? = null,
        var tableSchema: String? = null,
        var tableName: String? = null,
        var tableType: String? = null,
        var tableRows: String? = null,
        var tableComment: String? = null
)