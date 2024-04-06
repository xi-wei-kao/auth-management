import {
    RouteRecordRaw,
    createRouter,
    createWebHistory
} from 'vue-router';

const routes: RouteRecordRaw[] = [
    {
        'name': 'entry',
        'path': '/',
        'redirect': {
            'name': 'login'
        }
    },
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



const router = createRouter({
    'routes': routes,
    'history': createWebHistory(),
})


// 路由守衛
router.beforeEach((to, from, next) => {
    // next();

    const storeData = localStorage.getItem("myStore");
    let token = "";
    // [Step]: 解析 accessToken
    try { 
        if (storeData) {
            const storeDataJson = JSON.parse(storeData);
            token = storeDataJson['accessToken'];
        }
    } catch (error) {
        console.error("localStorage is not a valid JSON! ");
    }

    // 白名單
    const whiteList = [
        "/login"
    ];

    if (!!token) {
        next();
    } else {
        if (whiteList.includes(to.path)) {
            next();
        } else {
            next({
                'name': 'login'
            })
        }
    }

})

export { router };