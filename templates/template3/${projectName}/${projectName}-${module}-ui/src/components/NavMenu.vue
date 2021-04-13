<template>
    <span style="color: #fff">{{openKeys}}</span><br>
    <span style="color: #fff">{{selectedKeys}}a</span>
    <a-menu v-model:selectedKeys="selectedKeys" v-model:openKeys="openKeys" @click="clickMenu" theme="dark" mode="inline">
        <template v-for="(menu1) in menus">
            <template v-if="menu1.type === 'menu-item'">
                <a-menu-item :key="menu1.path">
                    <user-outlined />
                    <router-link :to="menu1.path">{{menu1.name}}</router-link>
                </a-menu-item>
            </template>
            <template v-else>
                <a-sub-menu :key="menu1.path">
                    <template #title>
                        <span>{{menu1.name}}</span>
                    </template>
                    <template v-for="(menu2) in menu1.children">
                        <template v-if="menu2.type === 'menu-item'">
                            <a-menu-item :key="menu2.path">
                                <router-link :to="menu2.path">{{menu2.name}}</router-link>
                            </a-menu-item>
                        </template>
                        <template v-else>
                            <a-sub-menu :key="menu2.path">
                                <template #title>
                                    <span>
                                        <span>{{menu2.name}}</span>
                                    </span>
                                </template>
                                <template v-for="(menu3) in menu2.children" :key="menu3.path">
                                    <a-menu-item>
                                        <router-link :to="menu3.path">{{menu3.name}}</router-link>
                                    </a-menu-item>
                                </template>
                            </a-sub-menu>
                        </template>
                    </template>
                </a-sub-menu>
            </template>
        </template>
    </a-menu>
</template>

<script lang="ts">
import router from '@/router';
import { defineComponent, reactive, ref, toRefs, VNodeChild } from 'vue'

interface MenuInfo {
    key: string;
    keyPath: string[];
    item: VNodeChild;
    domEvent: MouseEvent;
}

export default defineComponent({
    name: 'NavMenu',
    props: {
        menus: {
            type: Array,
            default: () => []
        }
    },
    setup() {
        const state = reactive({
            rootSubmenuKeys: ['test'],
            openKeys: ['test'],
            selectedKeys: [''],
        });
        return {
            ...toRefs(state),
            // selectedKeys: ref<string[]>(['1']),
            clickMenu: (e: MenuInfo) => {
                console.log(e)
                router.push({ path: e.key });
            }
        }
    },
    watch: {
        '$route'(val) {
            this.selectedKeys = [val.fullPath]
            // let currentRoutePath = this.$router.currentRoute;
        }
    }
})
</script>
