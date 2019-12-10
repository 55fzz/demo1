package com.example.web;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.example.entity.*;
import com.example.service.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
public class jumpController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IDocumentService documentService;
    @Autowired
    private IClassificationService classificationService;
    @Autowired
    private IUserroleService userRoleServate;
    @Autowired
    private IRecycleService recycleService;

    @RequestMapping("login")
    public String login(){
        return "login";
    }

    @RequestMapping("zhuce")
    public String zhuce(){ return "addUser";}


    @RequestMapping("/index")
    public String index(Map<String, Object> map) {
        User user = getUser();
        int line = getCont(user);
        int usercont = getUsecont(user,line);
        map.put("cont",line/1024);
        map.put("name", user.getName());
        map.put("usecont", usercont/1024);
        map.put("use", 0);
        return "index";
    }


    @RequestMapping("/addDocument")
    public String addDocument(Map<String, Object> map) {
        User user = getUser();
        int line = getCont(user);
        int usercont = getUsecont(user,line);
        map.put("cont",line/1024);
        map.put("name", user.getName());
        map.put("usecont", usercont/1024);
        map.put("use", 0);
        return "addDocument";
    }

    @RequestMapping("/my")
    public String my(Map<String, Object> map){
        User user = getUser();
        int line = getCont(user);
        int usercont = getUsecont(user,line);
        Wrapper dwrapper = new EntityWrapper<Document>();
        dwrapper.eq("uid",user.getId());
        List<Document> mds = documentService.selectList(dwrapper);
        map.put("ds",mds);
        map.put("cont",line/1024);
        map.put("name",user.getName());
        map.put("usecont", usercont/1024);
        return "my";
    }

    @RequestMapping("/zuijin")
    public String zuijin(Map<String, Object> map){
        User user = getUser();
        int line = getCont(user);
        int usercont = getUsecont(user,line);
//        Wrapper dwrapper = new EntityWrapper<Document>();
//        dwrapper.eq("uid",user.getId());
        List<Document> mds = documentService.getListByCreateDate();
        map.put("ds",mds);
        map.put("cont",line/1024);
        map.put("name",user.getName());
        map.put("usecont", usercont/1024);
        return "zuijin";
    }

    @RequestMapping("excel")
    public String excel(Map<String, Object> map){
        User user = getUser();
        int line = getCont(user);
        int usercont = getUsecont(user,line);
        List<Document> list = getListByCid(1,user.getId());
        map.put("list",list);
        map.put("cont",line/1024);
        map.put("name",user.getName());
        map.put("usecont", usercont/1024);
        return "excel";
    }

    @RequestMapping("word")
    public String word(Map<String, Object> map){
        User user = getUser();
        int line = getCont(user);
        int usercont = getUsecont(user,line);
        List<Document> list = getListByCid(3,user.getId());
        map.put("list",list);
        map.put("cont",line/1024);
        map.put("name",user.getName());
        map.put("usecont", usercont/1024);
        return "word";
    }

    @RequestMapping("ppt")
    public String ppt(Map<String, Object> map){
        User user = getUser();
        int line = getCont(user);
        int usercont = getUsecont(user,line);
        List<Document> list = getListByCid(2,user.getId());
        map.put("list",list);
        map.put("cont",line/1024);
        map.put("name",user.getName());
        map.put("usecont", usercont/1024);
        return "ppt";
    }


    @RequestMapping("lajixiang")
    public String lajixiang(Map<String, Object> map){
        User user = getUser();
        int line = getCont(user);
        int usercont = getUsecont(user,line);
        Wrapper wrapper = new EntityWrapper<Document>();
        wrapper.eq("uid",user.getId());
        List<Recycle> list = recycleService.selectList(wrapper);
        map.put("list",list);
        map.put("cont",line/1024);
        map.put("name",user.getName());
        map.put("usecont", usercont/1024);
        return "lajixiang";
    }

    private List<Document> getListByCid(Integer cid,Integer uid){
        Wrapper dwrapper = new EntityWrapper<Document>();
        dwrapper.eq("uid",uid);
        dwrapper.eq("cid",cid);
        List<Document> list = documentService.selectList(dwrapper);
        return list;
    }


    /**
     * 获取用户容量
     * @param user 用户
     * @return
     */
    public int getCont(User user){
        Wrapper urwrapper = new EntityWrapper<Userrole>();
        urwrapper.eq("uid", user.getId());
        Userrole userRole = userRoleServate.selectOne(urwrapper);
        Wrapper rwrapper = new EntityWrapper<Role>();
        rwrapper.eq("id", userRole.getRid());
        Role role = roleService.selectOne(rwrapper);
        int line = Integer.parseInt(role.getLine());
        return line;
    }




    /**
     * 公共方法获取用户可用的容量
     * @param user  用户
     * @param cont  用户共可用多少容量
     * @return
     */
    public int getUsecont(User user,int cont){
        Wrapper dwrapper = new EntityWrapper<Document>();
        dwrapper.eq("uid",user.getId());
        List<Document> dList = documentService.selectList(dwrapper);
        int usercont = 0;
        for(Document d : dList){
            usercont += Integer.parseInt(d.getSize())/1024;
        }
        usercont = cont-(usercont/1024);
        return usercont;
    }

    /**
     * 获取当前登录用户
     * @return
     */
    public User getUser(){
        Subject subject = SecurityUtils.getSubject();
        String name = subject.getPrincipals().toString();
        Wrapper wrapper = new EntityWrapper<User>();
        wrapper.eq("name", name);
        User user = (User) userService.selectOne(wrapper);
        return user;
    }
}
