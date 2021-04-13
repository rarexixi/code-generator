export interface Cookie {
    name: string;
    value: string;
    domain?: string;
    path?: string;
    expires?: number;
}

export function setCookie(cookie: Cookie) {
    document.cookie = `${cookie.name}=${cookie.value}; domain=${cookie.domain || ''}; path=${cookie.path || ''}; expires=${new Date(Date.now() + (cookie.expires || 0)).toUTCString()}`;
}

export function getCookie(name: string) {
    const reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
    const arr = document.cookie.match(reg);
    return arr ? unescape(arr[2]) : null;
}

export function delCookie(name: string, domain = '', path = '/') {
    const cookieValue = getCookie(name);
    document.cookie = `${name}=${cookieValue}; domain=${domain}; path=${path}; expires=${new Date(Date.now() - 1).toUTCString()}`;
}

