import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import Home from '@/views/Home.vue'

const routes: Array<RouteRecordRaw> = [
    {
        path: '/',
        name: 'Home',
        component: Home
    },
<#list tableModels as table>
<#include "/include/table/properties.ftl">
    {
        path: '/${tableShortPath}',
        name: '${classNameFirstLower}',
        component: () => import('@/views/${targetTableName}/${classNameFirstLower}Index.vue')
    },
</#list>
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

export default router