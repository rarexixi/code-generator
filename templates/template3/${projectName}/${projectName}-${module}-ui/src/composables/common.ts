import { PageInfo } from "./models"

const SortEnum = { ASC: 'ASC', DESC: 'DESC' }
const PageSizeOptions = ['10', '20', '50', '100']
const DefaultSearchParams = { defaultPageNum: 1, defaultPageSize: 50 }

export declare type OperationType = | 0 | 1 | 2 | 3
const DataOperationType = { default: 0, create: 1, copy: 2, update: 3 }

function getDefaultPageInfo(): PageInfo {
    return {
        total: 0,
        pageNum: 0,
        pageSize: 0,
        list: []
    }
}

export default {
    SortEnum,
    DefaultSearchParams,
    DataOperationType,
    PageSizeOptions,
    getDefaultPageInfo
}