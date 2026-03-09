package com.sky.controller.admin;



import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.utils.AliOssUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/admin/common")
@Api(tags = "通用接口")
@Slf4j
public class CommonController {


    @Autowired
    private AliOssUtil aliOssUtil;

    @PostMapping("/upload")
    @ApiOperation("文件上传")
    public Result<String> upload(MultipartFile file){
        log.info("当前上传文件为{}",file);
        try{
            String originFileName = file.getOriginalFilename();

            String fileExtention = originFileName.substring(originFileName.lastIndexOf("."));

            String newFileName = UUID.randomUUID().toString() + fileExtention;
            String filePath = aliOssUtil.upload(file.getBytes(), newFileName);

            return Result.success(filePath);

        }catch( IOException e){
            log.error("文件上传失败{}",e);
        }
        
        return  Result.error(MessageConstant.UPLOAD_FAILED);

    }
    
}
