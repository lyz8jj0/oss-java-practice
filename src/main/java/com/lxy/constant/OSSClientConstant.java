package com.lxy.constant;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 阿里去oss上传文件常量
 */
@Configuration
public class OSSClientConstant {
    //阿里云API的外网域名
    public static String ENDPOINT;
    //阿里云API的密钥Access Key ID
    public static String ACCESS_KEY_ID;
    //阿里云API的密钥Access Key Secret
    public static String ACCESS_KEY_SECRET;
    //阿里云API的bucket名称
    public static String BUCKET_NAME;
    //阿里云API的文件夹名称
    public static String FOLDER;
    //阿里云OSS根路径
    public static String PUBLIC_PATH;

    @Value("${OSS_ENDPOINT}")
    public void setENDPOINT(String ENDPOINT) {
        OSSClientConstant.ENDPOINT = ENDPOINT;
    }

    @Value("${OSS_ACCESS_KEY_ID}")
    public void setAccessKeyId(String accessKeyId) {
        OSSClientConstant.ACCESS_KEY_ID = accessKeyId;
    }

    @Value("${OSS_ACCESS_KEY_SECRET}")
    public void setAccessKeySecret(String accessKeySecret) {
        OSSClientConstant.ACCESS_KEY_SECRET = accessKeySecret;
    }


    @Value("${OSS_BUCKET_NAME}")
    public void setBucketName(String bucketName) {
        BUCKET_NAME = bucketName;
    }

    @Value("${OSS_FOLDER}")
    public void setFOLDER(String FOLDER) {
        OSSClientConstant.FOLDER = FOLDER;
    }

    @Value("${PUBLIC_PATH_OSS}")
    public void setPublicPath(String PUBLIC_PATH) {
        OSSClientConstant.PUBLIC_PATH = PUBLIC_PATH;
    }
}
