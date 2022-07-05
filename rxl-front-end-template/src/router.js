import Vue from 'vue'
import Router from 'vue-router'
import layout from './layout/layout' /* 平台页面整体布局页面 header 侧边栏  主题内容*/

Vue.use(Router)

export default new Router({
    mode: 'history',
    base: process.env.BASE_URL,
    routes: [
        {
            path: '/',
            name: 'layout',
            component: layout,
            redirect: '/index',
            meta: {
                requireIsAuth: true
            },
            children: [
                {
                    path: 'index',
                    name: 'Index',
                    component: () => import('@/views/Home'),
                    meta: {
                        requireIsAuth: true
                    },
                },
                {
                    path: 'navigation1',
                    name: 'Navigation1',
                    component: () => import('@/views/navigation1'),
                    meta: {
                        requireIsAuth: true
                    },
                },
                {
                    path: 'navigation2',
                    name: 'Navigation2',
                    component: () => import('@/views/navigation2'),
                    meta: {
                        requireIsAuth: true
                    },
                },

            ]
        },
        {
            path: '/login',
            name: 'login',
            component: () => import('@/views/login/login'),
        },
    ]
})
