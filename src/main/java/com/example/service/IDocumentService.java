package com.example.service;

import com.example.entity.Document;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2019-11-20
 */
public interface IDocumentService extends IService<Document> {

    public List<Document> getListByCreateDate();


}
