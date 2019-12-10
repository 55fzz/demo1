package com.example.service.impl;

import com.example.entity.Document;
import com.example.mapper.DocumentMapper;
import com.example.service.IDocumentService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 2019-11-20
 */
@Service
public class DocumentServiceImpl extends ServiceImpl<DocumentMapper, Document> implements IDocumentService {

    @Autowired
    private DocumentMapper documentMapper;

    @Override
    public List<Document> getListByCreateDate() {
        return documentMapper.getListByCreateDate();
    }


}
