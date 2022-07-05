package com.rxl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rxl.dao.*;
import com.rxl.entity.*;
import com.rxl.enums.ExceptionEnum;
import com.rxl.exception.GlobalException;
import com.rxl.service.UserService;
import com.rxl.utils.SaltUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author ren.xiaolong
 * @date 2022/7/2
 * @Description
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {


    @Autowired
    UserRoleDao userRoleDao;

    @Autowired
    RoleDao roleDao;

    @Autowired
    RolePermsDao rolePermsDao;

    @Autowired
    PermsDao permsDao;

    /**
     * 根据用户名查询 用户
     *
     * @param userName
     * @return
     */
    @Override
    public UserEntity queryByName(String userName) {
        LambdaQueryWrapper<UserEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserEntity::getUsername, userName);
        UserEntity userEntity = getOne(queryWrapper);
        return ObjectUtils.isEmpty(userEntity) ? null : userEntity;
    }


    /**
     * 根据用户名查询所有角色
     *
     * @param username
     * @return
     */
    @Override
    public UserEntity findRolesByUserName(String username) {
        UserEntity userEntity = queryByName(username);
        LambdaQueryWrapper<UserRoleEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRoleEntity::getUserid, userEntity.getId());
        List<UserRoleEntity> userRoleEntities = userRoleDao.selectList(queryWrapper);
        List<RoleEntity> roles = new ArrayList<>();
        userRoleEntities.forEach(ite -> {
            roles.add(roleDao.selectById(ite.getRoleid()));
        });
        userEntity.setRoles(roles);
        return ObjectUtils.isEmpty(userEntity) ? null : userEntity;
    }

    /**
     * 根据角色id查询权限集合
     *
     * @param id
     * @return
     */
    @Override
    public List<PermsEntity> findPermsByRoleId(String id) {
        LambdaQueryWrapper<RolePermsEntity> query=new LambdaQueryWrapper<>();
        query.eq(RolePermsEntity::getRoleid,id);
        List<RolePermsEntity> rolePermsEntities = rolePermsDao.selectList(query);
        CopyOnWriteArrayList<Integer> permsId=new CopyOnWriteArrayList<>();
        rolePermsEntities.forEach(ite->{
            permsId.add(ite.getPermsid());
        });
        LambdaQueryWrapper<PermsEntity> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.in(PermsEntity::getId,permsId);
        List<PermsEntity> permsEntities = permsDao.selectList(queryWrapper);
        return permsEntities;
    }


    @Override
    public Integer saveUser(UserEntity userEntity) {
        /*密码进行 md5 加密*/
        String salt = SaltUtils.getSalt(8);
        userEntity.setSalt(salt);
        //存储加密后的密码
        Md5Hash md5Hash = new Md5Hash(userEntity.getPassword(), salt, 1024);
        userEntity.setPassword(md5Hash.toHex());
        LambdaQueryWrapper<UserEntity> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(UserEntity::getUsername,userEntity.getUsername());
        UserEntity one = getOne(queryWrapper);
        if(ObjectUtils.isEmpty(one)){
            boolean save = save(userEntity);
        }else{
            throw new GlobalException("该用户已存在！");
        }
        return userEntity.getId();
    }

    /**
     * 登录
     * @param userEntity
     * @return
     */
    @Override
    public UserEntity login(UserEntity userEntity) {
        String username= ObjectUtils.isEmpty(userEntity.getUsername()) ? null : userEntity.getUsername();
        String password= ObjectUtils.isEmpty(userEntity.getClass()) ? null : userEntity.getPassword();
        UserEntity entity = queryByName(username);
        if(ObjectUtils.isEmpty(entity)){throw new GlobalException("该用户不存在！");}
        entity.setSalt("已忽略返回!!!");
        //创建shiro subject 主体
        Subject subject = SecurityUtils.getSubject();
        //验证登录
        try {
            subject.login(new UsernamePasswordToken(username,password));
        }catch (UnknownAccountException e){
            e.printStackTrace();
            throw new GlobalException("用户名错误!");
        }catch (IncorrectCredentialsException e){
            e.printStackTrace();
            throw new GlobalException("密码错误!");
        }
        try {
            // 获取 Request
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
            final HttpSession session = request.getSession();
            //像session 中保存 username  审计日志中需要获取
            session.setAttribute("username",username);
        }catch (Exception e){
            log.info("登录后像session中存储username失败！影响到审计日志中用户字段");
        }
        return entity;
    }
}
