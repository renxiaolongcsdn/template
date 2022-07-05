package com.rxl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rxl.entity.PermsEntity;
import com.rxl.entity.UserEntity;

import java.util.List;

/**
 * @author ren.xiaolong
 * @date 2022/7/2
 * @Description
 */
public interface UserService extends IService<UserEntity> {

    /**
     * 根据 用户名查询用户
     *
     * @param userName
     * @return
     */
    UserEntity queryByName(String userName);

    /**
     * 根据用户名查询所有角色
     *
     * @param username
     * @return
     */
    UserEntity findRolesByUserName(String username);

    /**
     * 根据角色id查询权限集合
     *
     * @param id
     * @return
     */
    List<PermsEntity> findPermsByRoleId(String id);


    /**
     * 新增用户
     * @param userEntity
     * @return
     */
    Integer saveUser(UserEntity userEntity);


    /**
     * 登录
     * @param userEntity
     * @return
     */
    public UserEntity login(UserEntity userEntity);

}
