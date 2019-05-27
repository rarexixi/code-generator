        <el-form label-position="left" label-width="120px" size="small">
<#list table.columns as column>
            <#include "/include/column/properties.ftl">
            <el-form-item label="${columnComment}">
                <#if (column.validStatus || column.select)>
                <span>{{get${propertyExceptKey}Text(detail.${fieldName})}}</span>
                <#elseif (column.fkSelect)>
                <span>{{detail.${fieldNameExceptKey}Text}}</span>
                <#elseif column.imgUrl>
                <img :src="detail.${fieldName}" alt="${columnComment}" v-if="detail.${fieldName} != ''">
                <span v-else>-</span>
                <#elseif column.content>
                <pre>{{detail.${fieldName}}}</pre>
                <#elseif (isDate)>
                <span>{{detail.${fieldName} | formatDate}}</span>
                <#elseif (isTime)>
                <span>{{detail.${fieldName} | formatTime}}</span>
                <#elseif (isDateTime)>
                <span>{{detail.${fieldName} | formatDateTime}}</span>
                <#else>
                <span>{{detail.${fieldName}}}</span>
                </#if>
            </el-form-item>
</#list>
        </el-form>
