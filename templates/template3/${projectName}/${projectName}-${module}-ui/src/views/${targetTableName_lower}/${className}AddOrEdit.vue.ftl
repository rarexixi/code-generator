<#include "/include/table/properties.ftl">
<template>
    <a-drawer :visible="visible" :title="title" @close="closeDrawer" width="600px">
        <a-form ref="formRef" :model="detail" @finish="save" :rules="rules" :label-col="{span: 4}" :wrapper-col="{span: 20}">
            <a-row :gutter="10">
            <#list table.columnsExceptBase as column>
                <#include "/include/column/properties.ftl">
                <#if column.notRequired>
                <#elseif column.autoIncrement>
                <#elseif (column.select)>
                <a-col :span="24">
                    <a-form-item ref="${fieldName}" label="${columnComment}" name="${fieldName}">
                        <a-select v-model:value="detail.${fieldName}" allow-clear placeholder="请选择">
                            <template v-for="(item, index) in ${fieldNameExceptKey}SelectList" :key="index">
                                <a-select-option :value="item.value">{{item.text}}</a-select-option>
                            </template>
                        </a-select>
                    </a-form-item>
                </a-col>
                <#elseif (column.fkSelect)>
                <a-col :span="24">
                    <a-form-item ref="${fieldName}" label="${columnComment}" name="${fieldName}">
                        <a-select v-model:value="detail.${fieldName}" allow-clear placeholder="请选择">
                            <template v-for="(item, index) in ${fieldNameExceptKey}SelectList" :key="index">
                                <a-select-option :value="item.${column.fkSelectColumn.valueName?uncap_first}">{{item.${column.fkSelectColumn.textName?uncap_first}}}</a-select-option>
                            </template>
                        </a-select>
                    </a-form-item>
                </a-col>
                <#elseif (column.validStatus)>
                <#elseif (isInteger)>
                <a-col :span="24">
                    <a-form-item ref="${fieldName}" label="${columnComment}" name="${fieldName}">
                        <a-input-number v-model:value="detail.${fieldName}" />
                    </a-form-item>
                </a-col>
                <#elseif (isDecimal)>
                <a-col :span="24">
                    <a-form-item ref="${fieldName}" label="${columnComment}" name="${fieldName}">
                        <a-input-number v-model:value="detail.${fieldName}" />
                    </a-form-item>
                </a-col>
                <#elseif (isDate)>
                <a-col :span="24">
                    <a-form-item ref="${fieldName}" label="${columnComment}" name="${fieldName}">
                        <a-date-picker v-model:value="detail.${fieldName}" type="date" placeholder="选择日期" format="YYYY-MM-DD" value-format="YYYY-MM-DD" />
                    </a-form-item>
                </a-col>
                <#elseif (isDateTime)>
                <a-col :span="24">
                    <a-form-item ref="${fieldName}" label="${columnComment}" name="${fieldName}">
                        <a-date-picker v-model:value="detail.${fieldName}" type="date" placeholder="选择日期时间" format="YYYY-MM-DD HH:mm:ss" value-format="YYYY-MM-DD HH:mm:ss" />
                    </a-form-item>
                </a-col>
                <#elseif (isContent)>
                <a-col :span="24">
                    <a-form-item ref="${fieldName}" label="${columnComment}" name="${fieldName}">
                        <a-input v-model:value="detail.${fieldName}" type="textarea" :autosize="{ minRows: 5, maxRows: 100}" />
                    </a-form-item>
                </a-col>
                <#elseif (isString)>
                <a-col :span="24">
                    <a-form-item ref="${fieldName}" label="${columnComment}" name="${fieldName}">
                        <a-input v-model:value.trim="detail.${fieldName}" type="text" />
                    </a-form-item>
                </a-col>
                <#else>
                </#if>
            </#list>
            </a-row>
            <a-form-item :wrapper-col="{ span: 14, offset: 4 }">
                <a-button type="primary" html-type="submit">保存</a-button>
                <a-button style="margin-left: 10px" @click="closeDrawer">取消</a-button>
            </a-form-item>
        </a-form>
    </a-drawer>
</template>

<script lang="ts">
import { notification } from 'ant-design-vue'
import { ValidateErrorEntity } from 'ant-design-vue/es/form/interface'
import { AxiosRequestConfig } from 'axios'
import { defineComponent, inject, reactive, ref, toRaw, toRefs, watch } from 'vue'
import common from '@/composables/common'
import { request } from '@/utils/request-utils'

const rules = {
    <#list table.columnsExceptBase as column>
    <#include "/include/column/properties.ftl">
    <#if ((column.pk && !column.autoIncrement) || (!column.notRequired && !column.nullable && !(column.columnDefault??)))>
    ${fieldName}: [
        { <#if (isInteger)>type: 'integer', <#elseif (isDecimal)>type: 'float', </#if>required: true, message: '${columnComment}不能为空', trigger: '<#if (column.select || column.fkSelect)>change<#else>blur</#if>' }
    ],
    </#if>
    </#list>
}

<#list table.selectColumns as column>
<#include "/include/column/properties.ftl">
const ${fieldNameExceptKey}SelectList = [
    <#list column.selectOptions as option>
    { <#if (isInteger)>value: ${option.value}, text: '${option.text}'<#else>value: '${option.value}', text: '${option.text}'</#if> }<#if option?has_next>, </#if>
    </#list>
]
</#list>

export default defineComponent({
    props: {
        pk: {
            type: [Object, Map],
            required: false,
            default: () => undefined
        },
        operateType: {
            type: Number,
            default: () => common.DataOperationType.default
        },
        visible: {
            type: Boolean,
            default: () => false
        },
        <#list table.fkSelectColumns as column>
        <#include "/include/column/properties.ftl">
        ${fieldNameExceptKey}SelectList: {
            type: Array,
            default: () => []
        }
        </#list>
    },
    setup(props, { emit }) {
        const { pk, operateType, visible } = toRefs(props)
        const title = ref<string>('')
        const formRef = ref()
        const detail = reactive<any>({
            <#list table.columnsExceptBase as column>
            <#include "/include/column/properties.ftl">
            <#if !column.notRequired>
            ${fieldName}: <#if (isInteger || isDecimal)>undefined<#else>''</#if>,
            </#if>
            </#list>
        })
        const resetForm = () => {
            formRef.value.resetFields()
            <#list table.columnsExceptBase as column>
            <#include "/include/column/properties.ftl">
            <#if column.autoIncrement>
            detail.${fieldName} = <#if (isInteger || isDecimal)>undefined<#else>''</#if>
            </#if>
            </#list>
        }
        const closeAddOrEditDrawer = inject<(newVal: boolean) => void>('closeAddOrEditDrawer')
        const closeDrawer = () => {
            resetForm()
            if (closeAddOrEditDrawer) closeAddOrEditDrawer(false)
        }

        const getDetail = (newVal: boolean) => {
            if (!newVal) return
            if (operateType.value === common.DataOperationType.default) return
            if (operateType.value === common.DataOperationType.create) {
                title.value = '添加${tableComment}'
            } else {
                if (operateType.value === common.DataOperationType.copy)
                    title.value = '复制${tableComment}'
                else if (operateType.value === common.DataOperationType.update)
                    title.value = '编辑${tableComment}'

                request({ url: '/${tablePath}/detail', method: 'GET', params: pk.value }).then(response => {
                    <#list table.columnsExceptBase as column>
                    <#include "/include/column/properties.ftl">
                    <#if column.notRequired>
                    <#elseif column.autoIncrement>
                    if (operateType.value !== common.DataOperationType.copy) {
                        detail.${fieldName} = response.${fieldName}
                    }
                    <#elseif (column.fkSelect)>
                    detail.${fieldName} = response.${fieldName} + ''
                    <#else>
                    detail.${fieldName} = response.${fieldName}
                    </#if>
                    </#list>
                })
            }
        }

        watch(visible, getDetail)

        const save = () => {
            formRef.value.validate().then(() => {
                const requestConfig: AxiosRequestConfig = operateType.value === common.DataOperationType.update
                    ? { url: '/${tablePath}/update', method: "PATCH", data: toRaw(detail), params: pk.value }
                    : { url: '/${tablePath}/add', method: "POST", data: toRaw(detail) }
                request(requestConfig).then(response => {
                    notification.success({
                        message: "保存成功"
                    })
                    closeDrawer()
                    emit("save", response)
                })
            }).catch((error: ValidateErrorEntity<any>) => {
                console.log(error)
                notification.error({
                    message: "参数验证失败"
                })
            })
        }

        return {
            <#list table.selectColumns as column>
            <#include "/include/column/properties.ftl">
            ${fieldNameExceptKey}SelectList,
            </#list>
            title, detail,
            formRef,
            rules,
            save,
            closeDrawer
        }
    }
})
</script>

<style scoped>
</style>