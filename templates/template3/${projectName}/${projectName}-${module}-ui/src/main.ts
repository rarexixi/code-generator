import { createApp } from 'vue'
import Antd from 'ant-design-vue';
import * as AntdIcons from '@ant-design/icons-vue'
import App from './App.vue'
import router from './router'
import store from './store'

import 'ant-design-vue/dist/antd.less'
import '@/assets/styles/antv-override.less'
import '@/assets/styles/dmp-base.less'
const antdIcons = AntdIcons as any;

const app = createApp(App);
app.use(store);
app.use(router);
app.use(Antd, {size: 'small'});
app.mount('#app');

for (const iconName in antdIcons) {
  if (!iconName.endsWith('Outlined')) {
    continue;
  }
  app.component(iconName, antdIcons[iconName]);
}