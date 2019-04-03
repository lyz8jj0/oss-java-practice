
package com.lxy.utils;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.*;
import com.lxy.constant.OSSClientConstant;
import com.lxy.exception.ProgramException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


/**
 * 阿里云OSS相关java API 工具类
 *
 * @author lixinyu
 */
@Slf4j
public class AliyunOSSClientUtil {


    //阿里云API的内或外网域名
    private static String ENDPOINT;
    //阿里云API的密钥Access Key ID
    private static String ACCESS_KEY_ID;
    //阿里云API的密钥Access Key Secret
    private static String ACCESS_KEY_SECRET;
    //阿里云API的bucket名称
    private static String BUCKET_NAME;
    //阿里云API的文件夹名称
    private static String FOLDER;
    //阿里云OSS根路径
    private static String PUBLIC_PATH;


    // 初始化属性
    static {
        ENDPOINT = OSSClientConstant.ENDPOINT;
        ACCESS_KEY_ID = OSSClientConstant.ACCESS_KEY_ID;
        ACCESS_KEY_SECRET = OSSClientConstant.ACCESS_KEY_SECRET;
        BUCKET_NAME = OSSClientConstant.BUCKET_NAME;
        FOLDER = OSSClientConstant.FOLDER;
        PUBLIC_PATH = OSSClientConstant.PUBLIC_PATH;
    }

    /**
     * 获取阿里云OSS客户端对象
     *
     * @return ossClient
     */
    private static OSSClient getOSSClient() {
        return new OSSClient(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
    }

    /**
     * 通过文件名判断并获取OSS服务文件上传时文件的contentType
     *
     * @param fileName 文件名
     * @return 文件的contentType
     */
    private static String getContentType(String fileName) {
        //文件的后缀名
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));
        if (".bmp".equalsIgnoreCase(fileExtension)) {
            return "image/bmp";
        }
        if (".gif".equalsIgnoreCase(fileExtension)) {
            return "image/gif";
        }
        if (".jpeg".equalsIgnoreCase(fileExtension) || ".jpg".equalsIgnoreCase(fileExtension) || ".png".equalsIgnoreCase(fileExtension)) {
            return "image/jpeg";
        }
        if (".html".equalsIgnoreCase(fileExtension)) {
            return "text/html";
        }
        if (".txt".equalsIgnoreCase(fileExtension)) {
            return "text/plain";
        }
        if (".vsd".equalsIgnoreCase(fileExtension)) {
            return "application/vnd.visio";
        }
        if (".ppt".equalsIgnoreCase(fileExtension) || "pptx".equalsIgnoreCase(fileExtension)) {
            return "application/vnd.ms-powerpoint";
        }
        if (".doc".equalsIgnoreCase(fileExtension) || "docx".equalsIgnoreCase(fileExtension)) {
            return "application/msword";
        }
        if (".xml".equalsIgnoreCase(fileExtension)) {
            return "text/xml";
        }
        //默认返回类型
        return "image/jpeg";
    }


    /**
     * 获取文件后缀名
     *
     * @param path 图片名称
     * @return
     */
    private static String getSuffixName(String path) {
        String newFileName = path.substring(path.lastIndexOf(".")).
                trim().toLowerCase();
        return newFileName;
    }


    /**
     * 封装上传到OSS服务器方法 如果同名文件会覆盖服务器上的
     *
     * @param inputStream 文件流
     * @param folderPath  OSS上存储图片的文件目录
     * @param fileName    文件名称 包括后缀名
     * @return
     */
    public static String uploadFile2Oss(InputStream inputStream, String folderPath, String fileName) throws Exception {
        String result = "";
        // 创建上传Object的Metadata
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(inputStream.available());
        //指定该Object被下载时的网页的缓存行为
        metadata.setCacheControl("no-cache");
        //指定该Object下设置Header
        metadata.setHeader("Pragma", "no-cache");
        //文件的MIME，定义文件的类型及网页编码，决定浏览器将以什么形式、什么编码读取文件。如果用户没有指定则根据Key或文件名的扩展名生成，
        //如果没有扩展名则填默认值application/octet-stream
        metadata.setContentType(URLConnection.guessContentTypeFromName(fileName));
        //指定该Object被下载时的名称（指示MINME用户代理如何显示附加的文件，打开或下载，及文件名称）
        metadata.setContentDisposition("inline;filename=" + fileName);
        String resultUrl = FOLDER + folderPath + fileName;
        // 上传文件
        PutObjectResult putResult = getOSSClient().putObject(BUCKET_NAME, resultUrl, inputStream, metadata);
        // 设置文件的访问权限为公共读。
//        getOSSClient().setObjectAcl(BUCKET_NAME, resultUrl, CannedAccessControlList.PublicRead);
        if (!"".equals(putResult.getETag())) {
            result = resultUrl;
            log.info("上传后的文件MD5数字唯一签名:" + putResult.getETag()); //可以用来验证上传的资源是否为同一个(暂时没用到)
            log.info("上传阿里云OSS服务器成功");
        } else {
            log.error("上传阿里云OSS服务器异常");
        }
        inputStream.close();
        getOSSClient().shutdown();
        return PUBLIC_PATH + result;
    }


    /**
     * 删除oss一个文件夹中的无用图片,保留一个图片一本图书(未用)
     *
     * @param picturePath 有用的图片路径
     * @return
     */
    public static boolean deletePictureUseless(String picturePath) throws Exception {

        if (picturePath == null) {
            throw new ProgramException("删除oss无用图片参数不合法");
        }

        //文件夹路径(bookService/bookUpload/0c6fd6fbac33466e8b26e73115f80edd)
        String filePath = picturePath.substring(0, picturePath.lastIndexOf("/"));

        //列举所有的图片
        ObjectListing objectListing = getOSSClient().listObjects(new ListObjectsRequest(BUCKET_NAME).withPrefix(filePath));
        List<String> keys = new ArrayList<>();
        List<OSSObjectSummary> sums = objectListing.getObjectSummaries();
        for (OSSObjectSummary s : sums) {
            //判断是否是图片,只删无用图片, 不删图书
            if ("jpg".equals(s.getKey().substring(s.getKey().lastIndexOf(".") + 1))) {
                if (!picturePath.equals(s.getKey())) {
                    keys.add(s.getKey());
                }
            }
        }
        log.info("要删除的图片为===============" + keys.toString());
        // 删除无用图片
        if (keys.size() > 0) {
            DeleteObjectsResult deleteObjectsResult = getOSSClient().deleteObjects(new DeleteObjectsRequest(BUCKET_NAME).withKeys(keys));
            deleteObjectsResult.getDeletedObjects();
        }
        return true;
    }

    /**
     * 删除oss上的文件
     *
     * @param folderPath 设置OSS上要删除的的二级文件目录(例:(client/45e233d07c664b93b7bb35331285a8d8))
     * @return
     */
    public static boolean deleteFile2Oss(String folderPath) {

        //oss项目名+图书位置(bookService/bookUpload/45e233d07c664b93b7bb35331285a8d8)
        String prefix = FOLDER + folderPath;

        // 列举文件。 如果不设置KeyPrefix，则列举存储空间下所有的文件。KeyPrefix，则列举包含指定前缀的文件。
        ObjectListing objectListing = getOSSClient().listObjects(new ListObjectsRequest(BUCKET_NAME).withPrefix(prefix));
        List<String> keys = new ArrayList<>();
        List<OSSObjectSummary> sums = objectListing.getObjectSummaries();
        for (OSSObjectSummary s : sums) {
            keys.add(s.getKey());
        }

        //如果OSS文件上有文件的话删除,没有直接跳过
        if (keys.size() > 0) {
            // 删除文件夹内的文件。
            DeleteObjectsResult deleteObjectsResult = getOSSClient().deleteObjects(new DeleteObjectsRequest(BUCKET_NAME).withKeys(keys));
            deleteObjectsResult.getDeletedObjects();

            //删除文件夹
            List<String> key = new ArrayList<>();
            key.add(prefix);
            getOSSClient().deleteObjects(new DeleteObjectsRequest(BUCKET_NAME).withKeys(key));

        }
        // 关闭OSSClient。
        getOSSClient().shutdown();

        return true;
    }
}
