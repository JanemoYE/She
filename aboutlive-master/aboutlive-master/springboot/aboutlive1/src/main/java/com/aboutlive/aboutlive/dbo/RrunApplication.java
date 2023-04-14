package com.aboutlive.aboutlive.dbo;

import com.aboutlive.aboutlive.AboutliveApplication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.Key;

public class RrunApplication {
    private static String Key="9FE4BD9CE88085EFBC9AE9A1B9E58589E69DB0EFBC8C62E7AB99E5BC80E6BA907570E4B8BBEFBC9AE8A5BFE6A59AE79BB8E6809DE88BA6";
    @Value("${key.content}")
    private int key;

    @Value("${key.max}")
    private static String max;

    @Value("${key.msg}")
    String msg;

    @Value("${key.min}")
    private static String min;

    @Value("${key.idel}")
    private static String idel;

    public RrunApplication(Class<AboutliveApplication> aboutliveApplicationClass, String[] args) {

        SpringApplication.run(AboutliveApplication.class,args);

        main();
    }

    public void key_Gb() throws IOException {
        File fileDir = new File("Document 1");

        BufferedReader in = new BufferedReader(new InputStreamReader(
                new FileInputStream(fileDir), "UTF-8"));
        System.out.println("--");
        String str="";
        System.out.println(str);
        while ((str = in.readLine()) != null) {
            System.out.println(str);
            String str2 = new String(str.getBytes("GBK"), "GBK");
            System.out.println(str2);
        }

        in.close();
    }

    public static void main() {
        key_Ut();
        key_Ut();
    }



    public static String key_Ut() {
//        System.out.println(Key);
        String urlEncode = "" ;
        try {

            urlEncode = URLEncoder.encode (Key, "UTF-8" );
        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();

        }
        System.out.println("原作者，B站up主，西楚相思苦，项光杰著作，享受全部版权，商用欢迎举报。");
        return urlEncode;

    }


}
