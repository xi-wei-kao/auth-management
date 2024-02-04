import {
    RouteRecordRaw,
    createRouter,
    createWebHistory
} from 'vue-router';

const routes: RouteRecordRaw[] = [
    {
        'name': 'login',
        'path': '/login',
        component: import("../views/pages/LoginPage.vue")
    },
    {
        'name': 'home',
        'path': '/home',
        component: import("../views/pages/HomePage.vue")
    },
]

export const router = createRouter({
    'routes': routes,
    'history': createWebHistory(),
})