package com.example.mapper;

import com.example.entity.User;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author 
 * @since 2019-11-12
 */

@Mapper
public interface UserMapper extends BaseMapper<User> {

}