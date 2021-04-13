import axios, { AxiosInstance, AxiosRequestConfig, AxiosResponse } from 'axios'
import { notification } from 'ant-design-vue';
import * as qs from "qs";
import * as cookieUtils from './cookie-utils'

function getSysApiOrigin() {
    return location.hostname.includes('.prod.') ? 'http://dip-dmp-sys-service.chj.cloud' : 'http://172.21.206.7:10927';
}

export function getApiOrigin() {
    return 'http://localhost:8080'
}

export function getSysRedirectUrl() {
    return getSysApiOrigin() + '/redirect?redirect=';
}

export function getSysApiGetUserUrl() {
    return getSysApiOrigin() + '/account/get-user';
}

export function getSysApiLogoutUrl() {
    return getSysApiOrigin() + '/account/logout';
}

export function getSysApiGetModulesUrl() {
    return getSysApiOrigin() + '/account/get-modules';
}

export function getSysApiGetNavMenusUrl() {
    return getSysApiOrigin() + '/account/get-nav-menus';
}

const axiosRequest: AxiosInstance = (() => {
    const axiosRequest = axios.create({
        headers: {
            'Content-Type': 'application/json',
        },
        timeout: 30000
    });

    axiosRequest.interceptors.request.use((config: AxiosRequestConfig) => {
        config.paramsSerializer = params => qs.stringify(params, { skipNulls: true, arrayFormat: 'comma' })
        config.headers['Authorization'] = cookieUtils.getCookie('Authorization');
        return config;
    });

    axiosRequest.interceptors.response.use(response => response, error => {
        if (error && error.response && error.response.data && error.response.data.message) {
            notification.error({ message: error.response.data.message });
        } else {
            notification.error({ message: error.message });
        }
        return Promise.reject(error);
    });

    return axiosRequest;
})()

export function request<T = any, R = any>(config: AxiosRequestConfig, direct = false): Promise<R> {
    if (config.url && !direct) {
        config = { ...config, url: getApiOrigin() + config.url }
    }
    return new Promise((resolve, reject) => {
        axiosRequest.request(config).then((res: AxiosResponse) => {
            resolve(res.data)
        }).catch(error => {
            reject(error);
        });
    })
}

export function getQueryParam(name: string, queryString = '') {
    const reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    const r = (queryString ? queryString : window.location.search).substr(1).match(reg);
    if (r != null) return decodeURI(r[2]);
    return '';
}