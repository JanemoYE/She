package com.aboutlive.aboutlive.Controller;

import com.aboutlive.aboutlive.Entity.ResultEntity;
import com.aboutlive.aboutlive.Entity.User;
import com.aboutlive.aboutlive.Util.JSONTool;
import com.aboutlive.aboutlive.tool.OSSUtil;
import com.aboutlive.aboutlive.tool.RedisTool;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@CrossOrigin //解决跨域问题
@RestController //控制器注解
@RequestMapping("/Message/")  //请求路径
public class MessageController {
    //    @Resource //依赖注入  （需要用到别的方法和类）

    @Resource
    RedisTool redisTool;

    ResultEntity resultEntity = null;

    /**
     * 判断是否存在
     *
     * @return
     */
    @PostMapping("message")
    private String message(@RequestParam("userKey") String userKey, @RequestParam("userKey1") String userKey1, @RequestParam("content") String content) {
        resultEntity = new ResultEntity();
        try {
            String myUserKey=userKey;
            Jedis jedis = redisTool.getJedis();

            if (jedis.exists(userKey + "<->" + userKey1+"read")==false){
                jedis.set(userKey + "<->" + userKey1+"read","read");
            }

            if (userKey.length() == userKey1.length()) {
                int x = 0, minLen = Math.min(userKey.length(), userKey1.length());
                boolean f = true;
                while (x < minLen) {
                    if (userKey.charAt(x) > userKey1.charAt(x)) {
                        f = false;
                        break;
                    }
                    x++;
                }
                if (!f) {
                    String s = userKey;
                    userKey = userKey1;
                    userKey1 = s;
                }
            } else if (userKey.length() < userKey1.length()) {
                String s = userKey;
                userKey = userKey1;
                userKey1 = s;
            }


            //        序列化开始
            Jackson2JsonRedisSerializer jack2Seri = new Jackson2JsonRedisSerializer(Object.class);
            byte[] keyObject = jack2Seri.serialize(userKey + "<->" + userKey1);

            if (jedis.exists(keyObject)) {
                byte[] serialize2 = jedis.get(keyObject);
                List<List<Object>> noReadList = (List<List<Object>>) jack2Seri.deserialize(serialize2);
                List<Object> list = new ArrayList<>();
                list.add(myUserKey);
                list.add(content);
                noReadList.add(list);
                byte[] serialize3 = jack2Seri.serialize(noReadList);
                jedis.del(keyObject);
                jedis.set(keyObject, serialize3);
            } else {
                List<Object> noReadList = new ArrayList<>();
                List<Object> list = new ArrayList<>();
                list.add(myUserKey);
                list.add(content);
                noReadList.add(list);
                byte[] serialize3 = jack2Seri.serialize(noReadList);
                jedis.set(keyObject, serialize3);
            }
            resultEntity.setCode(0);
            resultEntity.setMsg("成功发送：" + content);

        } catch (Exception e) {
            resultEntity.setCode(999);
            resultEntity.setMsg("错误");
        }

        return JSONTool.getJsonTool().toJson(resultEntity);
    }

    /**
     * 用户未读消息数量，用户输入
     *
     * @param list
     * @param userKey
     * @return
     */
    @GetMapping("userListNoReadMessage")
    private String userListNoReadMessage(@RequestParam("key") List<String> list, @RequestParam("userKey") String userKey) {
        resultEntity = new ResultEntity();
        try {
            List<List<String>> list1=new ArrayList<>();

            for (int i = 0; i < list.size(); i++) {
                List<String> l=new ArrayList<>();
                l.add(list.get(i));
                l.add(noReadMessageLength(userKey,list.get(i)));
                list1.add(l);
//                System.out.println("userKey:"+userKey);
            }

            resultEntity.setCode(0);
            resultEntity.setData(list1);
        } catch (Exception e) {
            resultEntity.setCode(1);
            resultEntity.setData("无数据");
            e.printStackTrace();
        }


        return JSONTool.getJsonTool().toJson(resultEntity);
    }

    /**
     * 阅读信息
     *
     * @return
     */
    @GetMapping("read")
    private String read(@RequestParam("userKey") String userKey, @RequestParam("userKey1") String userKey1) {
        resultEntity = new ResultEntity();
        try {
            Jedis jedis = redisTool.getJedis();
            if (!jedis.exists(userKey + "<->" + userKey1)){
                if (jedis.exists(userKey + "<->" + userKey1+"read")==false){
                    jedis.set(userKey + "<->" + userKey1+"read","read");
                    System.out.println(userKey + "<->" + userKey1+"read");
                }else{
                    resultEntity.setCode(1);
                    resultEntity.setData("已读");
                    return JSONTool.getJsonTool().toJson(resultEntity);
                }
                String read=userKey + "<->" + userKey1+"read";

                if (userKey.length() == userKey1.length()) {
                    int x = 0, minLen = Math.min(userKey.length(), userKey1.length());
                    boolean f = true;
                    while (x < minLen) {
                        if (userKey.charAt(x) > userKey1.charAt(x)) {
                            f = false;
                            break;
                        }
                        x++;
                    }
                    if (!f) {
                        String s = userKey;
                        userKey = userKey1;
                        userKey1 = s;
                    }
                } else if (userKey.length() < userKey1.length()) {
                    String s = userKey;
                    userKey = userKey1;
                    userKey1 = s;
                }


                //        序列化开始
                Jackson2JsonRedisSerializer jack2Seri = new Jackson2JsonRedisSerializer(Object.class);
                byte[] keyObject = jack2Seri.serialize(userKey + "<->" + userKey1);

                if (jedis.exists(keyObject)) {
                    byte[] serialize2 = jedis.get(keyObject);
                    List<List<Object>> noReadList = (List<List<Object>>) jack2Seri.deserialize(serialize2);

                    if (jedis.exists(userKey + "<->" + userKey1+"read") && jedis.exists(userKey1 + "<->" + userKey+"read")){
                        jedis.del(keyObject);
                        System.out.println("del");
                        jedis.del(userKey + "<->" + userKey1+"read");
                        jedis.del(userKey1 + "<->" + userKey+"read");
                    }


                    resultEntity.setCode(0);
                    resultEntity.setData(noReadList);

                }else{
                    jedis.del(read);
                    resultEntity.setCode(1);
                    resultEntity.setData("无消息");
                }
                return JSONTool.getJsonTool().toJson(resultEntity);

            }else{

                resultEntity.setCode(1);
                resultEntity.setData("无消息");
            }

        } catch (Exception e) {
            resultEntity.setCode(999);
            resultEntity.setMsg("错误");
        }

        return JSONTool.getJsonTool().toJson(resultEntity);
    }

    /**
     * 查看未读消息数量
     *
     * @return
     */
    @GetMapping("noReadMessageLength")
    private String noReadMessageLength(@RequestParam("userKey") String userKey, @RequestParam("userKey1") String userKey1) {
        resultEntity = new ResultEntity();
        try {
            String myUserKey = userKey;

            if (userKey.length() == userKey1.length()) {
                int x = 0, minLen = Math.min(userKey.length(), userKey1.length());
                boolean f = true;
                while (x < minLen) {
                    if (userKey.charAt(x) > userKey1.charAt(x)) {
                        f = false;
                        break;
                    }
                    x++;
                }
                if (!f) {
                    String s = userKey;
                    userKey = userKey1;
                    userKey1 = s;
                }
            } else if (userKey.length() < userKey1.length()) {
                String s = userKey;
                userKey = userKey1;
                userKey1 = s;
            }


            Jedis jedis = redisTool.getJedis();
            jedis.set(myUserKey,"online");//如果用户查看了当前页面，发送在线消息
            jedis.expire(myUserKey,60);
            Jackson2JsonRedisSerializer jack2Seri = new Jackson2JsonRedisSerializer(Object.class);
            byte[] keyObject = jack2Seri.serialize(userKey + "<->" + userKey1);
            int count = 0;

            if (jedis.exists(keyObject)) {
                byte[] serialize2 = jedis.get(keyObject);
                List<List<Object>> noReadList = (List<List<Object>>) jack2Seri.deserialize(serialize2);

                for (List l : noReadList) {
                    if (!l.get(0).equals(myUserKey)) {
                        count++;
                    }
                }


            }
            return count+"";


        } catch (Exception e) {
            resultEntity.setCode(999);
            resultEntity.setMsg("错误");
        }

        return JSONTool.getJsonTool().toJson(resultEntity);
    }

//    @GetMapping("match")
//    private String match(@RequestParam("userKey") String userKey) {
//
//    }
}
