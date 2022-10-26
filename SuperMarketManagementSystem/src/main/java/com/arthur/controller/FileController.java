package com.arthur.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.arthur.Mapper.FileMapper;
import com.arthur.entity.FileLoad;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * @program: vueblog
 * @description
 * @author: Arthur
 * @create: 2022-09-30 12:01
 **/
@RestController
@RequestMapping("/files")
public class FileController {
    @Resource
    private FileMapper fileMapper;

    @Value("${files.upload.path}")
    private String fileUploadPath;

    @CrossOrigin
    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) throws IOException {
        String OriginalFilename = file.getOriginalFilename();
        String type = FileUtil.extName(OriginalFilename);
        long size = file.getSize();
        //先存储到磁盘
        File uploadParentFile = new File(fileUploadPath);
        //判断文件是否存在，不存在创建
        if (!uploadParentFile.exists()){
            uploadParentFile.mkdirs();
        }
        //定义一个文件唯一的标识码
        String uuid = IdUtil.fastSimpleUUID();
        String fileUUID = uuid+StrUtil.DOT+type;
        File uploadFile = new File(fileUploadPath+fileUUID);
        //把获取到的文件储存到磁盘目录
        file.transferTo(uploadFile);

        String url = "http://localhost:9090/files/goods/"+fileUUID;
        //存入数据库
        FileLoad saveFile = new FileLoad();
        saveFile.setName(OriginalFilename);
        saveFile.setType(type);
        saveFile.setSize(size/1024);
        saveFile.setUrl(url);
        fileMapper.insert(saveFile);
        return url;
    }

    @GetMapping("/goods/{fileUUID}")
    public void download(@PathVariable String fileUUID, HttpServletResponse response) throws IOException {
        //根据文件的唯一标识码获取文件
        File uploadFile = new File(fileUploadPath+fileUUID);
        //设置输出流格式
        ServletOutputStream os = response.getOutputStream();
        response.addHeader("Content-Disposition","attachment;filename"+ URLEncoder.encode(fileUUID,"UTF-8"));
        response.setContentType("application/octet-stream");
        //读取文件字节流
        os.write(FileUtil.readBytes(uploadFile));
        os.flush();
        os.close();
    }
}
