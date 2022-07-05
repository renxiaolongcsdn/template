package com.rxl.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rxl.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author ren.xiaolong
 * @date 2022/7/2
 * @Description
 */
@Mapper
public interface UserDao extends BaseMapper<UserEntity> {
}
