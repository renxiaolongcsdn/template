package com.rxl.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author ren.xiaolong
 * @date 2022/4/18
 * @Description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("rxl_user")
public class UserEntity implements Serializable {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    private String username;

    private String password;

    private String salt;

    private Integer age;

    private Integer sex;   //1 男  0 女

    private String address;

    @TableField(exist = false)   // exist =false 表示该属性不是数据库字段
    List<RoleEntity> roles;
}
