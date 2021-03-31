<#assign tableFullComment = table.comment>
<#assign tableComment = (tableFullComment?split("[（ ,，(]", "r"))[0]>
<#assign tableName = table.tableName>
<#assign targetTableName = table.targetTableName>
<#assign tablePath = targetTableName?replace("_", "-")>
<#assign tableShortPath = tablePath?replace(module + "-", "")>
<#assign className = table.className>
<#assign classNameFirstLower = className?uncap_first>
<#assign classNameLower = className?lower_case>
<#assign pks = table.pks>
<#assign classNameLength = className?length>
<#if table.uniPk??>
    <#assign uniPk = table.uniPk>
    <#assign uniPkFullComment = uniPk.columnComment>
    <#assign uniPkComment = (uniPkFullComment?split("[（ ,，(：:]", "r"))[0]>
    <#assign uniPkPropertyName = uniPk.targetName>
    <#assign uniPkFieldName = uniPkPropertyName?uncap_first>
    <#assign uniPkFieldType = uniPk.targetDataType>

    <#assign uniPkCanBeEqual = (uniPk.validStatus || uniPk.dataType?contains("int") || uniPk.dataType == "date" || uniPk.dataType?ends_with("char"))>
    <#assign uniPkCanBeList = (!uniPk.validStatus && (uniPk.dataType?contains("int") || uniPk.dataType?contains("date") || uniPk.dataType?ends_with("char")))>
    <#assign uniPkCanBeRange = (!uniPk.validStatus && (uniPk.dataType?contains("int") || uniPk.dataType == "double" || uniPk.dataType == "float" || uniPk.dataType == "decimal" || uniPk.dataType == "numeric" || uniPk.dataType?contains("date") || uniPk.dataType?contains("time")))>
    <#assign uniPkCanBeNull = uniPk.nullable>

    <#assign uniPkIsContent = (uniPk.content || uniPk.dataType?contains("text"))>
    <#assign uniPkIsInteger = (uniPk.dataType?contains("int"))>
    <#assign uniPkIsDecimal = (uniPk.dataType == "double" || uniPk.dataType == "float" || uniPk.dataType == "decimal" || uniPk.dataType == "numeric")>
    <#assign uniPkIsString = (uniPk.dataType?ends_with("char"))>
    <#assign uniPkIsDate = (uniPk.dataType == "date")>
    <#assign uniPkIsTime = (uniPk.dataType == "time")>
    <#assign uniPkIsDateTime = (uniPk.dataType?contains("date") || uniPk.dataType?contains("time"))>
    <#assign uniPkFieldBasicType = uniPkFieldType>
    <#if (uniPkFieldType=="Boolean")>
        <#assign uniPkFieldBasicType = "boolean">
    <#elseif (uniPkFieldType=="Character")>
        <#assign uniPkFieldBasicType = "char">
    <#elseif (uniPkFieldType=="Byte")>
        <#assign uniPkFieldBasicType = "byte">
    <#elseif (uniPkFieldType=="Short")>
        <#assign uniPkFieldBasicType = "short">
    <#elseif (uniPkFieldType=="Integer")>
        <#assign uniPkFieldBasicType = "int">
    <#elseif (uniPkFieldType=="Long")>
        <#assign uniPkFieldBasicType = "long">
    <#elseif (uniPkFieldType=="Float")>
        <#assign uniPkFieldBasicType = "float">
    <#elseif (uniPkFieldType=="Double")>
        <#assign uniPkFieldBasicType = "double">
    </#if>
</#if>