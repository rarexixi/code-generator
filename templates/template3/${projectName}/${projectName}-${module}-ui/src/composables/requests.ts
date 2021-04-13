import { Modal, notification } from 'ant-design-vue'
import { ColumnProps } from 'ant-design-vue/lib/table/interface'
import { AxiosRequestConfig } from 'axios'
import { Ref, UnwrapRef, ref, reactive, createVNode, watch } from 'vue'
import common from './common'
import { PageInfo, SearchPageParams } from '@/composables/models'
import { request } from '@/utils/request-utils'
import { ExclamationCircleOutlined } from '@ant-design/icons-vue'

export function listSearch(listRequestConfig: AxiosRequestConfig, searchParams: UnwrapRef<any>, convertList: (list: any[]) => any[] = (list: any[]) => list) {
    const dataList = ref<any[]>([])
    const search = () => {
        request({ ...listRequestConfig, params: { ...searchParams } }).then(response => {
            dataList.value = convertList(response)
        })
    }
    return {
        dataList,
        search
    }
}

export function pageListSearch(pageListRequestConfig: AxiosRequestConfig, searchParams: UnwrapRef<any>, convertList: (list: any[]) => any[] = (list: any[]) => list) {
    const dataPageList = reactive<PageInfo>(common.getDefaultPageInfo())
    const pageNum = ref(1)
    const pageSize = ref(10)
    const search = () => {
        request({ ...pageListRequestConfig, params: { ...searchParams, pageNum: pageNum.value, pageSize: pageSize.value } }).then(response => {
            dataPageList.list = convertList(response.list)
            dataPageList.total = response.total
            dataPageList.pageNum = response.pageNum
            dataPageList.pageSize = response.pageSize
        })
    }
    watch(pageSize, () => {
        pageNum.value = 1
        search()
    })
    watch(pageNum, search)
    return {
        pageNum,
        pageSize,
        dataPageList,
        search
    }
}

type Key = ColumnProps['key']
export function execSelected(requestConfig: AxiosRequestConfig, selectedRowKeys: Ref<Key[]>, paramName: string, operateMessage: string, successCallback: () => void = () => undefined) {
    return () => {
        if (selectedRowKeys.value.length === 0) {
            notification.warning({ message: `${operateMessage}列表不能为空` })
            return
        }
        Modal.confirm({
            title: `确定${operateMessage}吗？`,
            content: '',
            icon: createVNode(ExclamationCircleOutlined),
            okText: '确定',
            cancelText: '取消',
            onOk() {
                const params: any = {}
                params[paramName] = selectedRowKeys.value
                request({ ...requestConfig, params }).then(response => {
                    if (response > 0) {
                        notification.success({ message: `${operateMessage}成功` })
                    }
                    if (successCallback) successCallback()
                })
            }
        })
    }
}
