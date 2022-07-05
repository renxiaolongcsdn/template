import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
Vue.config.productionTip = false
Vue.use(ElementUI);



/**
 * 引入路由守卫
 * 在进行路由时会进入到此方法
 *
 *
 to 表示将要跳转到的组件 (目标组件)
 console.log(from); //(源组件)
 next();
 next 是一个函数
 next() 进入下一个组件的钩子函数
 next(false) 阻止跳转 中断导航
 next("/login") 进入指定的组件的钩子函数
 */
// 路由守卫
router.beforeEach((to,from,next)=>{
  console.log(to.name+"[是否需要认证=》]"+to.matched.some(res=>res.meta.requireIsAuth))
  if(to.matched.some(res=>res.meta.requireIsAuth)){//判断是否需要登录  true 则需要登录 false 则不需要登录
    if (null!=JSON.parse(sessionStorage.getItem("userInfo"))) {
      next();     // 代表放行  ，如果session 中含有用户名 则代表已经登录，可以从session中获取用户名
    }else{         //否则需要跳转到登录界面
      next({
        path:"/login",
        query:{
          redirect:to.fullPath
        }
      });
    }
  }else{
    next()
  }
});




new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
