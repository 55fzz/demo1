package com.example.web;


import com.alibaba.fastjson.JSONObject;
import com.example.entity.User;
import com.example.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  用户控制器
 * </p>
 *
 * @author 
 * @since 2019-11-12
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userServate;


    @RequestMapping("loginCheck")
    public String loginCheck(String name ,String password){
        SimpleHash hash = new SimpleHash("md5",password,"@vafd-*",2);
        UsernamePasswordToken token = new UsernamePasswordToken(name, hash.toHex());
//        System.out.println(hash.toHex());
        Subject subject = SecurityUtils.getSubject();
        JSONObject json = new JSONObject();
        try {
            subject.login(token);
            json.put("type",1);
            json.put("msg","登录成功");
            json.put("url","/index");
        }catch(Exception e) {
            e.printStackTrace();
            json.put("type",2);
            json.put("msg","登录失败");
        }
        String jsonString = json.toString();

//        System.out.println(jsonString);
        return jsonString;
    }


    @RequestMapping("addUser")
    public String addUser(String name ,String password){
        JSONObject json = new JSONObject();
        SimpleHash hash = new SimpleHash("md5",password,"@vafd-*",2);
        User user = new User();
        user.setName(name);
        user.setPassword(hash.toHex());
        String url = "src\\main\\resources\\document\\"+name;
        File file = new File(url);
        if(file.mkdirs()){
            if(userServate.insert(user)){
                json.put("type",1);
                json.put("msg","注册成功");
                json.put("url","/login");
                String jsonString = json.toString();
                return jsonString;
            }
        }
        json.put("type",2);
        json.put("msg","注册失败");
        String jsonString = json.toString();
        return jsonString;
    }
}
