<template>
    <a-config-provider :locale="zhCN">
        <a-layout>
            <a-layout-sider breakpoint="lg" collapsed-width="0" @collapse="onCollapse" @breakpoint="onBreakpoint" :style="{ height: '100vh', overflow: 'auto' }">
                <div class="logo" />
                <nav-menu :menus="menus"></nav-menu>
            </a-layout-sider>
            <a-layout :style="{ height: '100vh', overflow: 'auto' }">
                <!-- <a-layout-header :style="{ marginBottom: '10px', background: '#fff', padding: 0 }"></a-layout-header> -->
                <a-layout-content :style="{ overflow: 'auto' }">
                    <router-view />
                </a-layout-content>
            </a-layout>
        </a-layout>
    </a-config-provider>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
import zhCN from 'ant-design-vue/es/locale/zh_CN';
import NavMenu from './components/NavMenu.vue';

const menus = [
    {
        name: 'test', path: 'test', type: 'sub-menu',
        children: [
            { name: 'list-slot-test', path: '/list-slot-test', type: 'menu-item' },
            { name: 'provide-test', path: '/provide-test', type: 'menu-item' },
            { name: 'setup-test', path: '/setup-test', type: 'menu-item' },
        ]
    },
    { name: 'sys-dept', path: '/sys-dept', type: 'menu-item' },
    { name: 'sys-user', path: '/sys-user', type: 'menu-item' },
    { name: 'sys-config', path: '/sys-config', type: 'menu-item' },
    { name: 'sys-role', path: '/sys-role', type: 'menu-item' },
    { name: 'sys-operator-menu', path: '/sys-operator-menu', type: 'menu-item' },
]

export default defineComponent({
    components: { NavMenu },
    setup() {
        return {
            zhCN,
            menus
        }
    },
})
</script>

<style lang="less">
.logo {
    height: 32px;
    background: rgba(255, 255, 255, 0.3);
    margin: 16px;
}
</style>
