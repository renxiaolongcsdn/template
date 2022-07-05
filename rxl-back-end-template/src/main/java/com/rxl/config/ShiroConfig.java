package com.rxl.config;

import com.rxl.cache.RedisCacheManager;
import com.rxl.realm.CustomerRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

/**
 * @author ren.xiaolong
 * @date 2022/7/2
 * @Description 注入shiro 相关配置
 */
@Configuration
public class ShiroConfig {


    /**
     * 注入shiro filter 配置 可以配置系统的公共资源和授权资源
     *
     * @param defaultWebSecurityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager) {
        //创建shiro的filter
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //注入到安全管理器中
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("/user/login", "anon");//anon 请求这个资源需要认证和授权 不需要授权和认证的的资源路径
        hashMap.put("/user/register", "anon");//anon 请求这个资源需要认证和授权 不需要授权和认证的的资源路径
        //swagger -begin
        hashMap.put("/doc.html/**", "anon");
        hashMap.put("/swagger-ui.html/**", "anon");
        hashMap.put("/swagger-resources/**", "anon");
        hashMap.put("/v2/api-docs/**", "anon");
        hashMap.put("/webjars/**", "anon");
        hashMap.put("/configuration/security/**", "anon");
        hashMap.put("/configuration/ui/**", "anon");
        //swagger -end
        hashMap.put("/**", "authc");//authc 请求这个资源需要认证和授权 **所有资源需要认证
        shiroFilterFactoryBean.setFilterChainDefinitionMap(hashMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 注入 安全管理器 和 配置
     * @param realm
     * @return
     */
    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("getRealm") Realm realm){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(realm);
        return defaultWebSecurityManager;
    }



    /**
     * 注入 自定义 realm
     * @return
     */
    @Bean
    public Realm getRealm(){
        //创建自定义realm对象
        CustomerRealm customerRealm = new CustomerRealm();
        //设置hashed凭证匹配器
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        /*此处加密+散列次数需要和登录方法对密码做的加密处理所对应 否则无法对应解密*/
        //设置md5加密
        credentialsMatcher.setHashAlgorithmName("md5");
        //设置散列次数
        credentialsMatcher.setHashIterations(1024);
        customerRealm.setCredentialsMatcher(credentialsMatcher);
        //是否开启缓存管理
        customerRealm.setCacheManager(new RedisCacheManager());
        customerRealm.setCachingEnabled(true);//开启全局缓存
        customerRealm.setAuthenticationCachingEnabled(true);//开启认证缓存
        customerRealm.setAuthenticationCacheName("authenticationCache");
        customerRealm.setAuthorizationCachingEnabled(true);//开启授权缓存
        customerRealm.setAuthorizationCacheName("authorizationCache");
        return customerRealm;
    }


    /**
     *  注入 shiro 注解
     * 不注入 加上 @RequiresRoles 可能会造成访问api 出现404状态
     * @return
     */
    @Bean
    public static DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator=new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setUsePrefix(true);
        return defaultAdvisorAutoProxyCreator;
    }

}
