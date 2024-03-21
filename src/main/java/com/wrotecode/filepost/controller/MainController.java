package com.wrotecode.filepost.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

@Controller
public class MainController implements InitializingBean {
    private static final Logger log = LoggerFactory.getLogger(MainController.class);
    private String uploadDir;

    @GetMapping("/list")
    @ResponseBody
    public String[] list() {
        log.info("查询文件");
        File file = new File(uploadDir);
        return file.list();
    }

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) throws Exception {
        InputStream is = file.getInputStream();
        log.info("上传文件:{}", file.getOriginalFilename());
        OutputStream os = new FileOutputStream(uploadDir + "/" + file.getOriginalFilename());
        byte[] bytes = new byte[1024];
        int length = is.read(bytes);
        while (length > 0) {
            os.write(bytes, 0, length);
            length = is.read(bytes);
        }
        is.close();
        os.close();
        log.info("上传完成");
        return "redirect:/index.html";
    }

    @RequestMapping("/download/{filename}")
    public void download(@PathVariable("filename") String filename, HttpServletResponse response) throws Exception {
        log.info("下载文件:{}", filename);
        InputStream inputStream = new FileInputStream(uploadDir + "/" + filename);
        byte[] bytes = new byte[1024];
        int length = inputStream.read(bytes);
        ServletOutputStream outputStream = response.getOutputStream();
        while (length > 0) {
            outputStream.write(bytes, 0, length);
            length = inputStream.read(bytes);
        }
        outputStream.close();
        inputStream.close();
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("获取上传目录");
        uploadDir = System.getProperty("upload.dir");
        if (uploadDir == null || uploadDir.trim().length() == 0) {
            throw new Exception("缺少参数");
        }
        File file = new File(uploadDir);
        if (!file.exists()) {
            throw new Exception("目录不存在");
        }
        uploadDir = file.getAbsolutePath();
    }
}
