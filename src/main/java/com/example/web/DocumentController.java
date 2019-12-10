package com.example.web;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.example.entity.Document;
import com.example.entity.Recycle;
import com.example.entity.User;
import com.example.service.IDocumentService;
import com.example.service.IRecycleService;
import com.example.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 
 * @since 2019-11-20
 */
@RestController
@RequestMapping("/document")
public class DocumentController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IDocumentService documentService;
    @Autowired
    private IRecycleService recycleService;

    /**
     * 文件上传
     * @param file  文件
     * @param title 标题
     * @return
     */
    @RequestMapping("/addDocument")
    public String addDocument(@RequestParam("file") MultipartFile file,@RequestParam("title") String title){
        Subject subject = SecurityUtils.getSubject();
        String name = subject.getPrincipals().toString();
        JSONObject json = new JSONObject();
        //获得当前用户信息
        Wrapper wrapper = new EntityWrapper<User>();
        wrapper.eq("name", name);
        User user = (User) userService.selectOne(wrapper);
        //检查该用户是否已创建该名字的文件
        Wrapper dwrapper = new EntityWrapper<Document>();
        dwrapper.eq("title",title);
        dwrapper.eq("uid",user.getId());
        Document doc = null;
        doc = documentService.selectOne(dwrapper);
        if(doc!=null){
            title = title+"1";
        }

        File foler=new File("src\\main\\resources\\document\\"+name);
        if (file.isEmpty()|| (int)file.getSize()==0) {
            json.put("type",2);
            json.put("msg","上传失败，请选择文件");
            String jsonString = json.toString();
            return jsonString;
        }
        if(!foler.exists()){//如果文件夹不存在
            foler.mkdirs();//创建文件夹
        }
        String filePath = "src\\main\\resources\\document\\"+name;
        String fileName = file.getOriginalFilename();
//        System.out.println(fileName);
//        System.out.println(title);
        String Tail = fileName.substring(fileName.lastIndexOf("."));
        File dest = new File( new File("src\\main\\resources\\document\\"+name).getAbsolutePath()+"\\" + title+Tail);
        try {
            file.transferTo(dest);
            Document document = new Document();
            document.setSize(String.valueOf(file.getSize()));
            document.setTitle(title);
            if(Tail.equals(".doc")||Tail.equals(".dock")) {
                document.setCid(3);
            }else if(Tail.equals(".ppt")||Tail.equals(".pptx")) {
                document.setCid(2);
            }else {
                document.setCid(1);
            }
            document.setAddress(filePath +"\\"+ title+Tail);
            document.setUid(user.getId());
            Date now = new Date();
            document.setCreateDate(now);
            documentService.insert(document);
            json.put("type",1);
            json.put("msg","上传成功");
            json.put("url","/index");
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            json.put("type",2);
            json.put("msg","上传失败");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            json.put("type",2);
            json.put("msg","上传失败");
        }
        String jsonString = json.toString();
        return jsonString;
    }


    /**
     * 文件重命名
     * @param did 文件id
     * @param newTitle 修改后的文件名
     * @return
     */
    @RequestMapping("/updataTitle")
    public String updataTitle(@RequestParam("did") int did,@RequestParam("newTitle") String newTitle){
        JSONObject json = new JSONObject();
        Document document = documentService.selectById(did);
        document.setTitle(newTitle);
        Wrapper dwrapper = new EntityWrapper<Document>();
        dwrapper.eq("title",newTitle);
        dwrapper.eq("uid",document.getUid());
        dwrapper.eq("cid",document.getCid());
        Document doc = null;
        doc = documentService.selectOne(dwrapper);
        if(doc!=null){
            json.put("type",2);
            json.put("msg","已有该名字文件");
        }else if(documentService.updateById(document)){
            json.put("type",1);
            json.put("msg","修改成功");
        }else{
            json.put("type",2);
            json.put("msg","修改失败");
        }
        String jsonString = json.toString();
        return jsonString;
    }

    /**
     * 将文件信息放入回收站
     * @param ids
     * @return
     */
    @RequestMapping("/goToRecycle")
    public String goToRecycle(@RequestParam(value = "ids[]") String[] ids){
        JSONObject json = new JSONObject();
        List<Document> list = new ArrayList<>();
        for (String id : ids) {
            //did = Integer.parseInt(id);
            list.add(documentService.selectById(id));
        }
        Recycle r = null;
        for (Document d : list) {
            r = new Recycle();
            r.setId(d.getId());
            r.setTitle(d.getTitle());
            r.setAuthor(d.getAuthor());
            r.setAddress(d.getAddress());
            r.setSize(d.getSize());
            r.setCreateDate(d.getCreateDate());
            r.setCid(d.getCid());
            r.setUid(d.getUid());
            recycleService.insert(r);
            documentService.deleteById(d.getId());
        }
        json.put("type",1);
        json.put("msg","删除成功");
        String jsonString = json.toString();
        return jsonString;
    }


    /**
     * 文件还原（从回收站放回正常）
     * @param ids
     * @return
     */
    @RequestMapping("/huanyuan")
    public String huanyuan(@RequestParam(value = "ids[]") String[] ids){
        JSONObject json = new JSONObject();
        List<Recycle> list = new ArrayList<>();
        for (String id : ids) {
            //did = Integer.parseInt(id);
            list.add(recycleService.selectById(id));
        }
        Document d = null;
        for(Recycle r:list){
            d = new Document();
            d.setId(r.getId());
            d.setTitle(r.getTitle());
            d.setAuthor(r.getAuthor());
            d.setAddress(r.getAddress());
            d.setSize(r.getSize());
            d.setCreateDate(r.getCreateDate());
            d.setCid(r.getCid());
            d.setUid(r.getUid());
            documentService.insert(d);
            recycleService.deleteById(r.getId());
        }
        json.put("type",1);
        json.put("msg","还原成功");
        String jsonString = json.toString();
        return jsonString;
    }

    /**
     * 删除文件
     * @param ids 文件id数组
     * @return
     */
    @RequestMapping("/deleteTitle")
    public String deleteByIds(@RequestParam(value = "ids[]") String[] ids){
        JSONObject json = new JSONObject();
        int did = 0;
        List<Recycle> list = new ArrayList<>();
        for (String id : ids) {
            //did = Integer.parseInt(id);
            list.add(recycleService.selectById(id));
        }
        boolean flat = true;
        for(Recycle r:list){
            flat = recycleService.deleteById(r.getId());
            if(!flat){
                json.put("type",2);
                json.put("msg","删除失败");
                String jsonString = json.toString();
                return jsonString;
            }
        }
        json.put("type",1);
        json.put("msg","彻底删除成功");
        String jsonString = json.toString();
        return jsonString;
    }



    //文件下载
    @RequestMapping("/downLoadFile")
    public String downLoadFile(@RequestParam(value = "id") String id, HttpServletResponse response){
        JSONObject json = new JSONObject();
        int did = 0;
        Document document = documentService.selectById(id);
        //设置响应
        response.setContentType("application/force-download");
        //设置响应头信息
        response.setHeader("Content-Disposition", "attachment;fileName="+document.getTitle());
        //获得服务器端某个文件的完整路径
        String fullPath = document.getAddress();
        //文件名有中文时设置编码
        try {
            response.setHeader("Content-Disposition", "attachment;filename="+new String(document.getTitle().getBytes("GBK"),"ISO-8859-1"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        File downloadFile = new File(fullPath);
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(downloadFile);
            IOUtils.copy(inputStream, response.getOutputStream());
            response.flushBuffer();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String jsonString = json.toString();
        return jsonString;
//        String fileName = document.getAddress().substring(document.getAddress().lastIndexOf("\\"));// 文件名
//        if (fileName != null) {
//            //设置文件路径
//            File file = new File(document.getAddress());
//            //File file = new File(realPath , fileName);
//            if (file.exists()) {
//                response.setContentType("application/force-download");// 设置强制下载不打开
//                response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
//                byte[] buffer = new byte[1024];
//                FileInputStream fis = null;
//                BufferedInputStream bis = null;
//                try {
//                    fis = new FileInputStream(file);
//                    bis = new BufferedInputStream(fis);
//                    OutputStream os = response.getOutputStream();
//                    int i = bis.read(buffer);
//                    while (i != -1) {
//                        os.write(buffer, 0, i);
//                        i = bis.read(buffer);
//                    }
//                    return "下载成功";
//                } catch (Exception e) {
//                    e.printStackTrace();
//                } finally {
//                    if (bis != null) {
//                        try {
//                            bis.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    if (fis != null) {
//                        try {
//                            fis.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }
//        }
//        return "下载失败";
    }





    /**
     * 批量删除文件
     * @param list 文件集合
     * @return
     */
    public boolean deleteByList(List<Recycle> list){
        File file = null;
        for (Recycle doc : list) {
            file = new File(doc.getAddress());
            if(!file.delete()||recycleService.deleteById(doc.getId())){
                return false;
            }
        }
        return true;
    }



}
