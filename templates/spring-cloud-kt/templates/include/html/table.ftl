
        <el-row>
            <el-button @click="add" type="primary" icon="el-icon-plus">添加</el-button>
<#if table.validStatusColumn??>
            <el-button @click="enableSelected" v-if="multipleSelection.length > 0" type="success" icon="el-icon-check">启用</el-button>
            <el-button @click="disableSelected" v-if="multipleSelection.length > 0" type="warning" icon="el-icon-close">禁用</el-button>
</#if>
<#if table.hasUniPk>
            <el-button @click="delSelected" v-if="multipleSelection.length > 0" type="danger" icon="el-icon-delete">删除</el-button>
</#if>
        </el-row>
        <el-table ref="multipleTable" :data="pageInfo.list" tooltip-effect="dark"<#if (table.hasUniPk)> @selection-change="handleSelectionChange"</#if>>
<#if (table.hasUniPk)>
            <el-table-column type="selection" width="55"></el-table-column>
</#if>
<#list table.columns as column>
            <#include "/include/column/properties.ftl">
            <#if (column.validStatus || column.select || column.fkSelect)>
            <el-table-column label="${columnComment}">
                <template slot-scope="scope">
                    <span>{{get${propertyName}Text(scope.row.${fieldName})}}</span>
                </template>
            </el-table-column>
            <#elseif column.imgUrl>
            <el-table-column label="${columnComment}">
                <template slot-scope="scope">
                    <div class="ratio ratio-4by3 table-img-container">
                        <div class="content center">
                            <img :src="scope.row.${fieldName}" alt="${columnComment}" class="content" />
                        </div>
                    </div>
                </template>
            </el-table-column>
            <#elseif (isDate)>
            <el-table-column label="${columnComment}">
                <template slot-scope="scope">{{scope.row.${fieldName} | formatDate}}</template>
            </el-table-column>
            <#elseif (isTime)>
            <el-table-column label="${columnComment}">
                <template slot-scope="scope">{{scope.row.${fieldName} | formatTime}}</template>
            </el-table-column>
            <#elseif (isDateTime)>
            <el-table-column label="${columnComment}">
                <template slot-scope="scope">{{scope.row.${fieldName} | formatDateTime}}</template>
            </el-table-column>
            <#else>
            <el-table-column label="${columnComment}">
                <template slot-scope="scope">{{scope.row.${fieldName}}}</template>
            </el-table-column>
            </#if>
</#list>
            <el-table-column fixed="right" label="操作" width="<#if table.validStatusColumn??>200<#else>160</#if>">
                <template slot-scope="scope">
                    <el-button @click="edit(scope.row)" type="primary" icon="el-icon-edit" size="small" circle></el-button>
                    <el-button @click="get(scope.row)" type="info" icon="el-icon-more" size="small" circle></el-button>
<#if table.validStatusColumn??>
                    <el-button @click="disable(scope.row)" v-if="scope.row.${table.validStatusColumn.targetName?uncap_first}==${table.validStatusColumn.validStatusOption.valid}" type="warning" icon="el-icon-close" size="small" circle></el-button>
                    <el-button @click="enable(scope.row)" v-else type="success" icon="el-icon-check" size="small" circle></el-button>
</#if>
                    <el-button @click="del(scope.row)" type="danger" icon="el-icon-delete" size="small" circle></el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-pagination
                background
                @size-change="changePageSize"
                @current-change="changePage"
                :current-page="pageInfo.pageNum"
                :page-sizes="[10, 20, 50, 100]"
                :page-size="pageInfo.pageSize"
                layout="total, sizes, prev, pager, next, jumper"
                :total="pageInfo.total">
        </el-pagination>
