package com.lxy.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by 李新宇
 * 2019-04-03 14:18
 */
public interface UploadService {

    /**
     * 上传图片
     *
     * @param file 文件(单文件上传)
     * @param path oss上的路径(例:aaa/bbb/)
     * @return
     * @throws Exception
     */
    String uploadPicture(MultipartFile file, String path) throws Exception;

    /**
     * 上传文件(文件上传和图片上传的差别就是不需压缩)
     *
     * @param file 文件(单文件上传)
     * @param path oss上的路径(例:aaa/bbb/)
     * @return
     * @throws Exception
     */
    String uploadFile(MultipartFile file, String path) throws Exception;
}
