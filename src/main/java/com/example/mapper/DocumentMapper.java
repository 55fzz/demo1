package com.example.mapper;

import com.example.entity.Document;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author 
 * @since 2019-11-20
 */

@Mapper
public interface DocumentMapper extends BaseMapper<Document> {


    @Select({"<script> SELECT * FROM document GROUP BY createDate ORDER BY createDate DESC LIMIT 0, 10; </script>"})
    public List<Document> getListByCreateDate();
}