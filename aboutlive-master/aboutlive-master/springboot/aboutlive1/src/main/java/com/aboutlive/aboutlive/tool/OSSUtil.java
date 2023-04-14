package com.aboutlive.aboutlive.tool;

import com.google.gson.Gson;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.stereotype.Component;

@Component
public class OSSUtil {
    String UrlHead=""; //连接头，自定义域名
    String AccessKey = "";//七牛云秘钥
    String Secret = "";//七牛云Secret
    String BucketName = "";//七牛云名称


    /**
     * 文件上传
     *
     * @param FilePath 文件路径
     * @param FileName 文件名称
     * @return boolean：是否上传成功！
     */
    public boolean updateFile(String FilePath, String FileName) {
        boolean flag = false;
        //华北是zone1, [华东z0] [华南z2] [北美na0] [东南亚as0]
        try {
            com.qiniu.storage.Configuration configuration = new com.qiniu.storage.Configuration(Zone.zoneAs0());//七牛云配置
            UploadManager uploadManager = new UploadManager(configuration);//七牛云上传管理
            Auth auth = Auth.create(AccessKey, Secret);//需要传入key和secret
            String token = auth.uploadToken(BucketName);//需要传入oss桶名称、
//            System.out.println(FilePath);
            Response response = uploadManager.put(FilePath, FileName, token);//上传对象
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);//将处理结果转换为JSON
            System.out.println(putRet.key);//输出返回结果
            System.out.println(UrlHead + FileName);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 下载路径URL
     *
     * @param FileName 文件名
     * @return URL
     */
    public String downLoadUrl(String FileName) {
        String Url = "";
        try {
            String tempUrl = UrlHead + FileName;//临时下载地址
            Auth auth = Auth.create(AccessKey, Secret);//需要传入key和secret
            long time = 3600;//链接的有效时间
            Url = auth.privateDownloadUrl(tempUrl, time);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Url;
    }
}
