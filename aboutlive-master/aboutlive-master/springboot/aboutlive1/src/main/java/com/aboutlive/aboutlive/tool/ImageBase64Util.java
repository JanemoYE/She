package com.aboutlive.aboutlive.tool;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.util.UUID;

public class ImageBase64Util {
    //base64字符串转化成图片
    public static File base64ToFile(String base64) throws Exception {
        if(base64.contains("data:image")){
            base64 = base64.substring(base64.indexOf(",")+1);
        }
        base64 = base64.toString().replace("\r\n", "");
        //创建文件目录
        String prefix=".jpeg";
        File file = File.createTempFile(UUID.randomUUID().toString(), prefix);
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] bytes =  decoder.decodeBuffer(base64);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
        }finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }

    //图片转化成base64字符串
    public static String GetImageStr(File imgFile) {//将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);//返回Base64编码过的字节数组字符串
    }
}