package org.xi.quick.codegeneratorkt.entity

data class Column(
        var tableCatalog: String? = null,
        var tableSchema: String? = null,
        var tableName: String? = null,
        var columnName: String? = null,
        var ordinalPosition: Long? = null,
        var columnDefault: String? = null,
        var isNullable: String? = null,
        var dataType: String? = null,
        var characterMaximumLength: Long? = null,
        var characterOctetLength: Long? = null,
        var numericPrecision: Long? = null,
        var numericScale: Long? = null,
        var datetimePrecision: Long? = null,
        var characterSetName: String? = null,
        var collationName: String? = null,
        var columnType: String? = null,
        var columnKey: String? = null,
        var extra: String? = null,
        var privileges: String? = null,
        var columnComment: String? = null,
        var generationExpression: String? = null,
        var index: Boolean? = null
)