package com.example.web;


import com.example.entity.Test;
import com.example.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 
 * @since 2019-11-13
 */
@Controller
@RequestMapping("/test")
public class TestController {

//    @Autowired
//    private HttpServletResponse response;

    @Autowired
    private ITestService testService;

    @RequestMapping("/show")
    public void show(String phone,String pwd,HttpServletResponse response ) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        /* 设置响应头允许ajax跨域访问 */
        response.setHeader("Access-Control-Allow-Origin", "*");
        /* 星号表示所有的异域请求都可以接受， */
        response.setHeader("Access-Control-Allow-Methods", "GET,POST");
        System.out.println("手机号"+phone+"密码"+pwd);
        Writer out = response.getWriter();
        out.write("进入后台");
        out.flush();
    }

    @RequestMapping("addUser")
    public void addUser(String phone,String pwd,HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        /* 设置响应头允许ajax跨域访问 */
        response.setHeader("Access-Control-Allow-Origin", "*");
        /* 星号表示所有的异域请求都可以接受， */
        response.setHeader("Access-Control-Allow-Methods", "GET,POST");
        Test test = new Test();
        test.setPhone(phone);
        test.setPwd(pwd);
        System.out.println(test);
        Writer out = response.getWriter();
        testService.insert(test);
        out.write("添加用户信息成功");
        out.flush();

    }

    @RequestMapping("/index")
    public String index(){
        return "index";
    }
}
