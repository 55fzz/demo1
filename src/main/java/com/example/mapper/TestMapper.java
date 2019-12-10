package com.example.mapper;

import com.example.entity.Test;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author 
 * @since 2019-11-13
 */

@Mapper
public interface TestMapper extends BaseMapper<Test> {

}