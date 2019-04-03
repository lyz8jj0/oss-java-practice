package com.lxy.controller;

import com.lxy.common.BaseController;
import com.lxy.common.JsonResult;
import com.lxy.exception.ProgramException;
import com.lxy.service.UploadService;
import com.lxy.utils.Base64DecodedMultipartFileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by 李新宇
 * 2019-04-03 09:36
 */
@RestController
@Slf4j
@CrossOrigin
@RequestMapping("/upload")
public class UploadController extends BaseController {

    @Autowired
    private UploadService uploadService;

    /**
     * 上传图片
     *
     * @param file 文件(单文件上传)
     * @param path oss上的路径(例:aaa/bbb/)
     * @return
     * @throws Exception
     */
    @PostMapping(value = "picture")
    public JsonResult uploadPicture(MultipartFile file, String path) throws Exception {
        String pictureUrl = uploadService.uploadPicture(file, path);
        log.info("上传后的图片地址:" + pictureUrl);
        return renderSuccess("上传文件成功", pictureUrl);
    }

    /**
     * 上传图片(base64)
     *
     * @param fileBase64 base64文件流(单文件上传)
     * @param path       oss上的路径(例:aaa/bbb/)
     * @return
     * @throws Exception
     */
    @PostMapping(value = "pictureBase64")
    public JsonResult uploadPictureBase64(String fileBase64, String path) throws Exception {
        MultipartFile file = Base64DecodedMultipartFileUtil.base64ToMultipart(fileBase64);
        String pictureUrl = uploadService.uploadPicture(file, path);
        log.info("上传后的图片地址:" + pictureUrl);
        return renderSuccess("上传图片成功", pictureUrl);
    }


    /**
     * 文件上传
     *
     * @param file 文件(单文件上传)
     * @param path oss上的路径(例:aaa/bbb/)
     * @return
     * @throws Exception
     */
    @PostMapping("/file")
    public JsonResult uploadFile(MultipartFile file, String path) throws Exception {
        String fileUrl = uploadService.uploadFile(file, path);
        log.info("上传后的文件地址:" + fileUrl);
        return renderSuccess("上传文件成功", fileUrl);
    }

// TODO: 2019/4/3 视频上传

}
