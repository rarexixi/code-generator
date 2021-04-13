module.exports = {
    css: {
        loaderOptions: {
            less: {
                modifyVars: {
                    // 可以替换变量 https://github.com/vueComponent/ant-design-vue/blob/master/components/style/themes/default.less
                    'primary-color': '#1890ff',
                },
                javascriptEnabled: true
            }
        }
    }
}