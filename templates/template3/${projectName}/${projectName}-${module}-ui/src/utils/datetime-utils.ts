import moment from "moment";

moment.locale('zh-cn');

type datetimeFormatter =
    | 'YYYY-MM-DD HH:mm:ss'
    | 'YYYY/MM/DD HH:mm:ss'
    | 'MM/DD/YYYY HH:mm:ss'

type dateFormatter =
    | 'YYYY-MM-DD'
    | 'YYYY/MM/DD'
    | 'MM/DD/YYYY'

type timeFormatter =
    | 'HH:mm:ss'
    | 'HH:mm'

export function formatDatetime(timestamp: number, formatter: datetimeFormatter = "YYYY-MM-DD HH:mm:ss", isMilliseconds = true) {
    if (timestamp < 0) return "-";
    return isMilliseconds
        ? moment.unix(timestamp / 1000).format(formatter)
        : moment.unix(timestamp).format(formatter)
}

export function formatDate(timestamp: number, formatter: dateFormatter = "YYYY-MM-DD", isMilliseconds = true) {
    if (timestamp < 0) return "-";
    return isMilliseconds
        ? moment.unix(timestamp / 1000).format(formatter)
        : moment.unix(timestamp).format(formatter)
}

export function formatTime(timestamp: number, formatter: timeFormatter = "HH:mm:ss", isMilliseconds = true) {
    if (timestamp < 0) return "-";
    return isMilliseconds
        ? moment.unix(timestamp / 1000).format(formatter)
        : moment.unix(timestamp).format(formatter)
}