<#include "/include/table/properties.ftl">
import common from "@/composables/common"
import { PageInfo } from "@/composables/models"
import { request } from "@/utils/request-utils"
import { AxiosRequestConfig } from "axios"
import { reactive, ref, UnwrapRef } from "vue"

export function getOperations(dataPageList: UnwrapRef<PageInfo>, operateCallback: () => void) {
    const operateType = ref(common.DataOperationType.default)
    const visible = ref(false)
    const editPk = reactive({
        <#list pks as column>
        <#include "/include/column/properties.ftl">
        ${fieldName}: <#if (isInteger || isDecimal)>undefined<#else>''</#if>,
        </#list>
    })
    const editIndex = ref(-1)

    const add = () => {
        <#list pks as column>
        <#include "/include/column/properties.ftl">
        <#if (isInteger || isDecimal)>
        editPk.${fieldName} = undefined
        <#else>
        editPk.${fieldName} = ''
        </#if>
        </#list>
        editIndex.value = -1
        operateType.value = common.DataOperationType.create
        visible.value = true
    }

    const edit = (item: any, index: number, copy = false) => {
        <#list pks as column>
        <#include "/include/column/properties.ftl">
        editPk.${fieldName} = item.${fieldName}
        </#list>
        if (copy) {
            editIndex.value = -1
            operateType.value = common.DataOperationType.copy
        } else {
            editIndex.value = index
            operateType.value = common.DataOperationType.update
        }
        visible.value = true
    }

    const save = (detail: any) => {
        if (editIndex.value >= 0) {
            dataPageList.list[editIndex.value] = detail
        } else {
            if (operateCallback) operateCallback()
        }
        editIndex.value = -1
    }

    const getPkParams = (item: any) => {
        const params: any = {
        <#list pks as column>
        <#include "/include/column/properties.ftl">
            ${fieldName}: item.${fieldName},
        </#list>
        }
        return params
    }
    <#if table.validStatusColumn??>

    const switchDeleted = (item: any) => {
        const params: any = getPkParams(item)
        const requestConfig: AxiosRequestConfig = item.deleted === 1
            ? { url: '/${tablePath}/enable', method: 'PATCH', params }
            : { url: '/${tablePath}/disable', method: 'PATCH', params }
        request(requestConfig).then(response => {
            if (response > 0) item.deleted = item.deleted === 1 ? 0 : 1
        })
    }
    </#if>

    const del = (item: any) => {
        const params: any = getPkParams(item)
        const requestConfig: AxiosRequestConfig = { url: '/${tablePath}/delete', method: 'DELETE', params }
        request(requestConfig).then(response => {
            if (response > 0 && operateCallback) operateCallback()
        })
    }

    return { editPk, addOrEditDrawerVisible: visible, operateType, add, del, edit<#if table.validStatusColumn??>, switchDeleted</#if>, save }
}