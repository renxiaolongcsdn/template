package com.rxl.controller;

import com.rxl.common.R;
import com.rxl.entity.UserEntity;
import com.rxl.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ren.xiaolong
 * @date 2022/7/2
 * @Description user controller 层
 */
@RestController
@RequestMapping("/user")
@Api(tags = {"用户管理"})
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    @ApiOperation(value = "注册用户", notes = "新增")
    public R<Integer> register(@RequestBody UserEntity userEntity) {
        Integer id = userService.saveUser(userEntity);
        return R.OK(id);
    }

    @PostMapping("/login")
    @ApiOperation(value = "登录", notes = "登录")
    public R<UserEntity> login(@RequestBody UserEntity userEntity) {
        UserEntity entity = userService.login(userEntity);
        return R.OK(entity);
    }

}
