import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import ElementPlus from 'element-plus';
import 'element-plus/dist/index.css';
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import VideoPlayer from "vue-video-player";
import 'video.js/dist/video-js.css'
import * as echarts from 'echarts';
import axios from "axios";
import request from "@/utils/request.js";
import '@/assets/style/icon.css'
import locale from "element-plus/es/locale/lang/zh-cn"
import store from './store'
import i18n from "@/i18n/index.js";
import VueLazyload from "vue-lazyload";
import SlideVerify from 'vue-monoplasty-slide-verify';
import VTooltip from 'v-tooltip';

const app = createApp(App)
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}
// app.use(VueLazyload, {
//     preLoad: 1.3,
//     error: require('./assets/images/error.jpg'),
//     loading: require('./assets/images/loading.gif'),
//     attempt: 1,
// })
app.use(i18n)
app.use(ElementPlus, {
    locale: locale
})
app.use(VTooltip)
app.use(SlideVerify)
app.config.globalProperties.request = request
app.use(ElementPlus)
app.use(VideoPlayer)
app.use(router)
app.use(store)
app.mount('#app')
