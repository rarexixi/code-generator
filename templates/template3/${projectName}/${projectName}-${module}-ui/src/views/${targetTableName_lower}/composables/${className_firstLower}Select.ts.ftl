<#include "/include/table/properties.ftl">
<#if (table.hasUniPk)>
import { ColumnProps } from "ant-design-vue/lib/table/interface"
import { computed, ref, unref, UnwrapRef } from "vue"
import { PageInfo } from "@/composables/models";

type Key = ColumnProps['key']
export function getSelection(dataPageList: UnwrapRef<PageInfo>) {

    const selectedRowKeys = ref<Key[]>([])
    const selectedRows = ref<any[]>([])
    const onSelectChange = (changableRowKeys: Key[], changableRows: any[]) => {
        selectedRowKeys.value = changableRowKeys
        selectedRows.value = changableRows
    }

    const emptySelected = () => {
        selectedRowKeys.value = []
        selectedRows.value = []
    }

    const rowSelection = computed(() => {
        return {
            selectedRowKeys: unref(selectedRowKeys),
            onChange: onSelectChange,
            hideDefaultSelections: true,
            selections: [
                {
                    key: 'odd',
                    text: '选中奇数行数据',
                    onSelect: (changableRowKeys: Key[]) => {
                        selectedRowKeys.value = changableRowKeys.filter((key, index) => index % 2 === 0);
                        selectedRows.value = dataPageList.list.filter((item, index) => index % 2 === 0);
                    },
                },
                {
                    key: 'even',
                    text: '选中偶数行数据',
                    onSelect: (changableRowKeys: Key[]) => {
                        selectedRowKeys.value = changableRowKeys.filter((key, index) => index % 2 !== 0);
                        selectedRows.value = dataPageList.list.filter((item, index) => index % 2 !== 0);
                    },
                },
            ],
        }
    })

    return {
        rowSelection,
        selectedRowKeys,
        selectedRows,
        emptySelected
    }
}
</#if>