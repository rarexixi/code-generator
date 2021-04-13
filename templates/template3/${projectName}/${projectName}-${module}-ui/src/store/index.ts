import { createStore } from 'vuex'

export default createStore({
    state: {
        token: '',
        userInfo: {},
        DeletedMap: { 0: '有效', 1: '无效' },
        BooleanMap: { 0: '否', 1: '是' },
    },
    mutations: {
        setUserInfo(state, userInfo) {
            state.userInfo = userInfo;
        },
    },
    actions: {
    },
    modules: {
    }
})
