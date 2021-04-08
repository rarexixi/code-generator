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
                            <template v-for="(item, index) in ${fieldNameExceptKey}SelectList">
                                <a-select-option :value="item.value">{{item.text}}</a-select-option>
                            </template>
                        </a-select>
                    </a-form-item>
                </a-col>
                <#elseif (column.fkSelect)>
                <a-col :span="24">
                    <a-form-item ref="${fieldName}" label="${columnComment}" name="${fieldName}">
                        <a-select v-model:value="detail.${fieldName}" allow-clear placeholder="请选择">
                            <template v-for="(v, k) in ${fieldNameExceptKey}SelectMap">
                                <a-select-option :value="k">{{v.${column.fkSelectColumn.textName?uncap_first}}}</a-select-option>
                            </template>
                        </a-select>
                    </a-form-item>
                </a-col>
                <#elseif (column.validStatus)>
                <#elseif (isInteger)>
                <a-col :span="24">
                    <a-form-item ref="${fieldName}" label="${columnComment}" name="${fieldName}">
                        <a-input-number v-model:value.trim="detail.${fieldName}" />
                    </a-form-item>
                </a-col>
                <#elseif (isDecimal)>
                <a-col :span="24">
                    <a-form-item ref="${fieldName}" label="${columnComment}" name="${fieldName}">
                        <a-input-number v-model:value.trim="detail.${fieldName}" />
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

export default defineComponent({
    props: {
        <#list pks as column>
        <#include "/include/column/properties.ftl">
        <#if (isInteger || isDecimal)>
        ${fieldName}: {
            type: Number,
            required: false,
            default: () => undefined
        },
        <#else>
        ${fieldName}: {
            type: String,
            required: false,
            default: () => ''
        },
        </#if>
        </#list>
        operateType: {
            type: Number,
            default: () => common.DataOperationType.default
        },
        visible: {
            type: Boolean,
            default: () => false
        }
    },
    setup(props, { attrs, slots, emit }) {
        const { <#list pks as column><#include "/include/column/properties.ftl">${fieldName}, </#list>operateType, visible } = toRefs(props)
        const title = ref<string>('')
        const formRef = ref()
        const detail = reactive<any>({
            <#list table.columns as column>
            <#include "/include/column/properties.ftl">
            <#if (column.validStatus)>
            <#else>
            ${fieldName}: <#if (isInteger || isDecimal)>undefined<#else>''</#if>,
            </#if>
            </#list>
        })
        const resetForm = () => formRef.value.resetFields()
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

                request({ url: '/${tablePath}/detail', method: 'GET', params: { <#list pks as column><#include "/include/column/properties.ftl">${fieldName}: ${fieldName}.value<#if column?has_next>, </#if></#list> } }).then(response => {
                    <#list table.columns as column>
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

        const rules = {
            <#list table.columnsExceptBase as column>
            <#include "/include/column/properties.ftl">
            <#if ((column.pk && !column.autoIncrement) && (!column.notRequired && !column.nullable && !(column.columnDefault??)))>
            ${fieldName}: [
                { required: true, message: '${columnComment}不能为空', trigger: '<#if (column.select || column.fkSelect)>change<#else>blur</#if>' }
            ],
            </#if>
            </#list>
        }
        const save = () => {
            formRef.value.validate().then(() => {
                const requestConfig: AxiosRequestConfig = operateType.value === common.DataOperationType.update
                    ? { url: '/${tablePath}/update', method: "PATCH", data: toRaw(detail), params: { <#list pks as column><#include "/include/column/properties.ftl">${fieldName}: ${fieldName}.value<#if column?has_next>, </#if></#list> } }
                    : { url: '/${tablePath}/add', method: "POST", data: toRaw(detail) }
                request(requestConfig).then(response => {
                    notification.success({
                        message: "保存成功"
                    })
                    closeDrawer()
                    emit("save", response)
                })
            }).catch((error: ValidateErrorEntity<any>) => {
                notification.error({
                    message: "参数验证失败"
                })
            })
        }

        return {
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