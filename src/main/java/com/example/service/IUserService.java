package com.example.service;

import com.example.entity.User;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2019-11-12
 */
public interface IUserService extends IService<User> {


    public User login(String name,String password);
}
