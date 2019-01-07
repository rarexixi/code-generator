<#assign tableFullComment = table.comment>
<#assign tableComment = (tableFullComment?split("[（ ,，(]", "r"))[0]>
<#assign tableName = table.tableName>
<#assign targetTableName = table.targetTableName>
<#assign className = table.className>
<#assign classNameFirstLower = className?uncap_first>
<#assign pks = table.pks>