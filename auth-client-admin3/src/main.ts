import { createApp } from 'vue'
import './style.css'

import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

import App from './App.vue'

import { router } from './router';
import { createPinia } from 'pinia'
import { createPersistedState } from "pinia-plugin-persistedstate";

// pinia, persistent
const pinia = createPinia();
pinia.use(createPersistedState({
    'auto': true,
    'storage': localStorage
}));


createApp(App)
    .use(ElementPlus)
    .use(pinia)
    .use(router)
    .mount('#app')
