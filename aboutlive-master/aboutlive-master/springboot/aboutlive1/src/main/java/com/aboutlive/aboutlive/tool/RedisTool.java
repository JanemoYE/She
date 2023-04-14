package com.aboutlive.aboutlive.tool;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

@Component
public class RedisTool {
    public Jedis getJedis(){
        Jedis jedis =null;
        try {
            jedis =new Jedis("xxx.xxx.xxx.xxx",6379);
            jedis.auth("你的密码");
            jedis.select(0);
//            System.out.println("成功");
        }catch (Exception e){
            System.out.println("error:" + e);
//            System.out.println("连接失败");
        }finally {
            if (jedis!=null){
                try {
                    jedis.close();
                }catch (Exception e){
                    System.out.println("关闭失败"+e.getMessage());
                }
            }
        }
        return jedis;
    }
}
