        <el-row class="operate-btn-group">
            <el-button @click="add" type="primary" icon="el-icon-plus" size="small">添加</el-button>
<#if table.validStatusColumn??>
            <el-button @click="enableSelected" v-if="multipleSelection.length > 0" type="success" icon="el-icon-check" size="small">启用</el-button>
            <el-button @click="disableSelected" v-if="multipleSelection.length > 0" type="warning" icon="el-icon-close" size="small">禁用</el-button>
</#if>
<#if table.hasUniPk>
            <el-button @click="delSelected" v-if="multipleSelection.length > 0" type="danger" icon="el-icon-delete" size="small">删除</el-button>
</#if>
        </el-row>
        <template v-if="pageInfo && pageInfo.list && pageInfo.list.length > 0">
            <el-table ref="multipleTable" :data="pageInfo.list" tooltip-effect="dark" @sort-change="handleSortChange"<#if (table.hasUniPk)> @selection-change="handleSelectionChange"</#if>>
<#if (table.hasUniPk)>
                <el-table-column type="selection" width="55"></el-table-column>
</#if>
<#list table.columns as column>
            <#include "/include/column/properties.ftl">
            <#if (isContent)>
            <#else>
                <el-table-column label="${columnComment}" prop="${fieldName}" size="small"<#if (column.index)> sortable="custom"</#if>>
                <#if (column.validStatus || column.select || column.fkSelect)>
                    <template slot-scope="scope">
                        <span>{{get${propertyName}Text(scope.row.${fieldName})}}</span>
                    </template>
                <#elseif column.imgUrl>
                    <template slot-scope="scope">
                        <div class="ratio ratio-4by3 table-img-container" v-if="scope.row.${fieldName} != ''">
                            <div class="content center">
                                <img :src="scope.row.${fieldName}" alt="${columnComment}" class="content" />
                            </div>
                        </div>
                        <span v-else>-</span>
                    </template>
                <#elseif (isDate)>
                    <template slot-scope="scope">{{scope.row.${fieldName} | formatDate}}</template>
                <#elseif (isTime)>
                    <template slot-scope="scope">{{scope.row.${fieldName} | formatTime}}</template>
                <#elseif (isDateTime)>
                    <template slot-scope="scope">{{scope.row.${fieldName} | formatDateTime}}</template>
                <#else>
                    <template slot-scope="scope">{{scope.row.${fieldName}}}</template>
                </#if>
                </el-table-column>
            </#if>
</#list>
                <el-table-column fixed="right" label="操作" width="<#if table.validStatusColumn??>200<#else>170</#if>">
                    <template slot-scope="scope">
                        <el-tooltip effect="dark" content="编辑" placement="top">
                            <el-button @click="edit(scope.row, false)" type="primary" icon="el-icon-edit" size="mini" circle></el-button>
                        </el-tooltip>
                        <el-tooltip effect="dark" content="复制" placement="top">
                            <el-button @click="edit(scope.row, true)" icon="el-icon-document-copy" size="mini" circle></el-button>
                        </el-tooltip>
                        <el-tooltip effect="dark" content="详情" placement="top">
                            <el-button @click="get(scope.row)" type="info" icon="el-icon-more" size="mini" circle></el-button>
                        </el-tooltip>
<#if table.validStatusColumn??>
                        <el-tooltip effect="dark" content="禁用" placement="top" v-if="scope.row.${table.validStatusColumn.targetName?uncap_first}==${table.validStatusColumn.validStatusOption.valid}">
                            <el-button @click="disable(scope.row)" type="warning" icon="el-icon-close" size="mini" circle></el-button>
                        </el-tooltip>
                        <el-tooltip effect="dark" content="启用" placement="top" v-else>
                            <el-button @click="enable(scope.row)" type="success" icon="el-icon-check" size="mini" circle></el-button>
                        </el-tooltip>
</#if>
                        <el-tooltip effect="dark" content="删除" placement="top">
                            <el-button @click="del(scope.row)" type="danger" icon="el-icon-delete" size="mini" circle></el-button>
                        </el-tooltip>
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
        </template>
        <el-alert title="暂无数据" type="warning" :closable="false" v-else></el-alert>
