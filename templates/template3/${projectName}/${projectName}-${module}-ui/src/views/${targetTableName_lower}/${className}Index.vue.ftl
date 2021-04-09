<#include "/include/table/properties.ftl">
<#macro mapperEl$ value>${r"${"}${value}}</#macro>
<template>
    <a-breadcrumb separator="/">
        <a-breadcrumb-item>${tableComment}管理</a-breadcrumb-item>
        <a-breadcrumb-item>${tableComment}列表</a-breadcrumb-item>
    </a-breadcrumb>
    <a-form ref="searchForm" :model="searchParams" @finish="search" layout="inline" class="search-form">
    <#list table.indexes as column>
        <#include "/include/column/properties.ftl">
        <#if column.validStatus>
        <#elseif column.select>
        <a-form-item label="${columnComment}">
            <a-select v-model:value="searchParams.${fieldName}" allow-clear placeholder="全部">
                <template v-for="(item, index) in ${fieldNameExceptKey}SelectList">
                    <a-select-option :value="item.value">{{item.text}}</a-select-option>
                </template>
            </a-select>
        </a-form-item>
        <#elseif column.fkSelect>
        <a-form-item label="${columnComment}">
            <a-select v-model:value="searchParams.${fieldName}" allow-clear placeholder="全部">
                <template v-for="(v, k) in ${fieldNameExceptKey}SelectMap">
                    <a-select-option :value="k">{{v.${column.fkSelectColumn.textName?uncap_first}}}</a-select-option>
                </template>
            </a-select>
        </a-form-item>
        <#elseif column.pk>
        <a-form-item label="${columnComment}">
            <a-input v-model:value.trim="searchParams.${fieldName}" type="text" allow-clear />
        </a-form-item>
        <#elseif (isInteger || isDecimal)>
        <a-form-item label="${columnComment}">
            <div style="display: flex">
                <a-input-number v-model:value="searchParams.${fieldName}Min" style="flex: 1" />
                <span>-</span>
                <a-input-number v-model:value="searchParams.${fieldName}Max" style="flex: 1" />
            </div>
        </a-form-item>
        <#elseif (isString)>
        <a-form-item label="${columnComment}">
            <a-input v-model:value.trim="searchParams.${fieldName}Contains" allow-clear />
        </a-form-item>
        <#elseif (isDate || isDateTime)>
        <a-form-item label="${columnComment}">
            <a-range-picker
                    v-model:value="searchParams.${fieldName}Range"
                    :show-time="{ hideDisabledOptions: true, defaultValue: [moment('00:00:00', 'HH:mm:ss'), moment('23:59:59', 'HH:mm:ss')] }"
                    range-separator="至"
                    :placeholder="['开始日期', '结束日期']"
                    format="YYYY-MM-DD HH:mm:ss"
                    value-format="YYYY-MM-DD HH:mm:ss">
            </a-range-picker>
        </a-form-item>
        </#if>
        </#list>
        <a-form-item>
            <a-button type="primary" html-type="submit">
                <template #icon>
                    <search-outlined />
                </template>搜索
            </a-button>
        </a-form-item>
    </a-form>
<#--    <#if table.validStatusColumn??>-->
<#--    <deleted-tabs v-model="${table.validStatusColumn.targetName?uncap_first}" @change="changeValidSearch"></deleted-tabs>-->
<#--    <#else>-->
<#--    <a-divider></a-divider>-->
<#--    </#if>-->
    <div class="operate-btn-group">
        <a-button @click="add" type="primary">
            <template #icon>
                <plus-outlined />
            </template>添加
        </a-button>
        <#if (table.hasUniPk)>
        <template v-if="selectedRowKeys.length > 0">
            <a-button @click="enableSelected" type="default">
                <template #icon>
                    <check-outlined />
                </template>启用
            </a-button>
            <a-button @click="disableSelected" type="default">
                <template #icon>
                    <stop-outlined />
                </template>禁用
            </a-button>
            <a-button @click="deleteSelected" type="danger">
                <template #icon>
                    <delete-outlined />
                </template>删除
            </a-button>
        </template>
        </#if>
    </div>
    <a-table :columns="columns" :data-source="dataPageList.list" :row-selection="rowSelection" class="list-table" :scroll="{ x: 1300 }" :pagination="false" :rowClassName="(record, index) => (index % 2 === 1 ? 'table-striped' : null)"<#if (table.hasUniPk)><#list pks as column><#include "/include/column/properties.ftl"> row-key="${fieldName}"</#list></#if> size="small">
        <template #action="{record, index}">
            <div class="table-operations">
                <#if table.validStatusColumn??>
                <#assign column = table.validStatusColumn>
                <#include "/include/column/properties.ftl">
                <a-popconfirm :title="`确定<@mapperEl$ "record.${fieldName} === 1 ? '启用' : '禁用'"/>吗？`" ok-text="确定" cancel-text="取消" @confirm="switchDeleted(record)">
                    <a v-if="record.deleted" class="enable">
                        <check-outlined />
                        <stop-outlined />
                    </a>
                    <a v-else class="disable">
                        <check-outlined />
                        <stop-outlined />
                    </a>
                </a-popconfirm>
                </#if>
                <a @click="edit(record, index)" class="text-primary">
                    <edit-outlined />
                </a>
                <a @click="edit(record, index, true)" class="text-primary">
                    <copy-outlined />
                </a>
                <a-popconfirm title="确定删除吗？" ok-text="确定" cancel-text="取消" @confirm="del(record)">
                    <a class="text-danger">
                        <delete-outlined />
                    </a>
                </a-popconfirm>
            </div>
        </template>
    </a-table>
    <a-pagination v-model:current="pageNum" v-model:pageSize="pageSize" :total="dataPageList.total" :page-size-options="pageSizeOptions" show-size-changer show-quick-jumper></a-pagination>
    <${tablePath}-add-or-edit :pk="editPk" :visible="addOrEditDrawerVisible" :operateType="operateType" @save="save" />
</template>

<script lang="ts">
import { defineComponent, reactive, provide, onMounted } from 'vue'
import common from '@/composables/common'
import { pageListSearch, execSelected } from '@/composables/requests'
import ${className}AddOrEdit from './${className}AddOrEdit.vue'
<#if (table.hasUniPk)>
import { getSelection } from './composables/${classNameFirstLower}Select'
</#if>
import { getOperations } from './composables/${classNameFirstLower}Operate'

const columns = [
    <#list table.columns as column>
    <#include "/include/column/properties.ftl">
    <#if (isContent || column.validStatus)>
    <#else>
    { title: '${columnComment}', dataIndex: '${fieldName}', key: '${fieldName}' },
    </#if>
    </#list>
    { title: '操作', key: 'action', fixed: 'right', width: 160, slots: { customRender: 'action' } },
]

const pksField = 'ids'

export default defineComponent({
    components: { ${className}AddOrEdit },
    name: "${className}Index",
    setup() {
        const searchParams = reactive<any>({
        <#list table.indexes as column>
        <#include "/include/column/properties.ftl">
        <#if (column.validStatus)>
        <#elseif (column.select || column.fkSelect || column.pk)>
            ${fieldName}: '',
        <#elseif (isInteger || isDecimal)>
            ${fieldName}Min: '',
            ${fieldName}Max: '',
        <#elseif (isDate || isTime || isDateTime)>
            ${fieldName}Range: [],
        <#elseif (isContent)>
        <#elseif (isString)>
            ${fieldName}Contains: '',
        <#else>
        </#if>
        </#list>
        })

        const { pageNum, pageSize, dataPageList, search } = pageListSearch({ url: '/${tablePath}/page-list', method: 'GET' }, searchParams<#if !(table.hasUniPk)>, (list: any[]) => list.map(item => ({ ...item, key: <#list pks as column><#include "/include/column/properties.ftl">item.${fieldName}<#if column?has_next> + ':' + </#if></#list> }))</#if>)
        <#if (table.hasUniPk)>
        const { rowSelection, selectedRowKeys, selectedRows, emptySelected } = getSelection(dataPageList)
        </#if>

        const { editPk, addOrEditDrawerVisible, operateType, add, del, edit<#if table.validStatusColumn??>, switchDeleted</#if>, save } = getOperations(dataPageList, search)
        provide('closeAddOrEditDrawer', () => addOrEditDrawerVisible.value = false)

        onMounted(() => {
            search()
        })

        return {
            columns,
            <#if (table.hasUniPk)>
            rowSelection,
            selectedRowKeys,
            selectedRows,
            </#if>
            pageSizeOptions: common.PageSizeOptions,
            searchParams, pageNum, pageSize,
            search,
            dataPageList,
            operateType,
            addOrEditDrawerVisible,
            editPk,
            add,
            edit,
            save,
            <#if table.validStatusColumn??>
            switchDeleted,
            </#if>
            del,
            <#if (table.hasUniPk)>
            disableSelected: execSelected({ url: '/${tablePath}/disable', method: 'PATCH' }, selectedRowKeys, pksField, '禁用', () => {
                selectedRows.value.forEach((item: any) => item.deleted = 1)
                emptySelected()
            }),
            enableSelected: execSelected({ url: '/${tablePath}/enable', method: 'PATCH' }, selectedRowKeys, pksField, '启用', () => {
                selectedRows.value.forEach((item: any) => item.deleted = 0)
                emptySelected()
            }),
            deleteSelected: execSelected({ url: '/${tablePath}/delete', method: 'DELETE' }, selectedRowKeys, pksField, '删除', () => {
                search()
                emptySelected()
            })
            </#if>
        }
    }
})
</script>

<style scoped>
</style>
