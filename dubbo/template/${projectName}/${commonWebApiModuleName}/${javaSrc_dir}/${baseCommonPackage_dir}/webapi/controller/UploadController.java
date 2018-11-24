package ${baseCommonPackage}.webapi.controller;

import ${baseCommonPackage}.model.ResponseVo;
import ${baseCommonPackage}.model.UploadResultVo;
import ${baseCommonPackage}.utils.LogUtils;
import ${baseCommonPackage}.webapi.service.UploadService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.regex.Pattern;

<#include "/include/java_copyright.ftl">
@CrossOrigin
@RestController
@RequestMapping("/upload")
@Validated
public class UploadController {

    private static LogUtils logger = LogUtils.build(UploadController.class);

    final static Pattern pattern = Pattern.compile("^data:image/[^;]*;base64,");

    @Autowired
    UploadService uploadService;

    @RequestMapping("base64")
    public ResponseVo<UploadResultVo> base64(@NotBlank String base64, String fileName) {

        // 获取实际内容
        final String base64F = pattern.matcher(base64).replaceFirst("");

        UploadResultVo uploadResult = uploadService.upload(fileName, entity -> {
            //Base64解码
            byte[] bytes = Base64.getDecoder().decode(base64F);
            // 设置大小
            entity.setFileSize((long) bytes.length);
            for (int i = 0; i < bytes.length; ++i) {
                //调整异常数据
                if (bytes[i] < 0) bytes[i] += 256;
            }
            return new ByteArrayInputStream(bytes);
        });

        return new ResponseVo<>(uploadResult);
    }

    @RequestMapping("file")
    public ResponseVo<UploadResultVo> file(@NotNull MultipartFile file) {

        UploadResultVo uploadResult = upload(file);
        return new ResponseVo<>(uploadResult);
    }

    @RequestMapping("files")
    public ResponseVo<UploadResultVo[]> files(@NotNull MultipartFile[] files) {

        UploadResultVo[] uploadResults = new UploadResultVo[files.length];
        for (int i = 0; i < files.length; i++) {
            uploadResults[i] = upload(files[i]);
        }
        return new ResponseVo<>(uploadResults);
    }

    private UploadResultVo upload(MultipartFile file) {
        return uploadService.upload(file.getOriginalFilename(), uploadResult -> {
            // 设置大小
            uploadResult.setFileSize(file.getSize());
            try {
                return file.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        });
    }
}
