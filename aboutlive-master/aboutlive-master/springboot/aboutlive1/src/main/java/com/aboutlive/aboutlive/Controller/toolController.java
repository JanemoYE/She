package com.aboutlive.aboutlive.Controller;

import com.aboutlive.aboutlive.Entity.FocusAndFan;
import com.aboutlive.aboutlive.Entity.ResultEntity;
import com.aboutlive.aboutlive.Entity.User;
import com.aboutlive.aboutlive.Impl.UserImpl;
import com.aboutlive.aboutlive.Service.UserService;
import com.aboutlive.aboutlive.Service.UserSystemService;
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
@RequestMapping("/Tool/")  //请求路径
public class toolController {
    //    @Resource //依赖注入  （需要用到别的方法和类）

    @Resource
    RedisTool redisTool;

    @Resource
    UserService userService;

    @Resource
    OSSUtil ossUtil;
//    @Resource
    ResultEntity resultEntity = null;

    /**
     * 图片上传oss
     *
     * @param FilePath
     * @param FileName
     * @return
     */
    @GetMapping("ossUtil")
    private String ossUtil(@RequestParam("FilePath") String FilePath, @RequestParam("FileName") String FileName) {
        resultEntity = new ResultEntity();
        try {
            ossUtil.updateFile(FilePath, FileName);
            resultEntity.setCode(0);
            resultEntity.setData(ossUtil.downLoadUrl(FileName));
            resultEntity.setMsg("图片已上传");
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setCode(1);
            resultEntity.setMsg(e.getMessage());
        }
        return JSONTool.getJsonTool().toJson(resultEntity);
    }

    /**
     * 输入set数据
     *
     * @param key
     * @param valString
     * @param valObject
     * @return
     */
    @GetMapping("setRedis")
    private String setRedis(@RequestParam("key") String key, @RequestParam("valString") String valString, @RequestParam("valObject") List<Object> valObject,
                            @RequestParam("type") int type) {
        Jedis jedis = redisTool.getJedis();

        if (type==1 && !jedis.exists(key) && valObject != null) {
            //        序列化开始
            Jackson2JsonRedisSerializer jack2Seri = new Jackson2JsonRedisSerializer(Object.class);
            List<Object> list = new ArrayList<>();
            for (Object o : valObject) {
                list.add(o);
            }
            byte[] serialize2 = jack2Seri.serialize(list);
            byte[] keyObject = jack2Seri.serialize(key);
            jedis.set(keyObject, serialize2);
            jedis.expire(keyObject, 500);
            return "成功发送：" + valObject;

        } else if (type==0 && !jedis.exists(key) && valString != null) {
            jedis.set(key, valString);
            jedis.expire(key, 500);
            return "成功发送：" + valString;
        } else if (valObject == null && valString == null || valString.equals("")) {
            return "失败：" + valString;
        }
        return "失败";
    }

    /**
     * Get获取redis数据
     *
     * @param key
     * @param type 0是String 1是Object
     * @return
     */
    @GetMapping("getRedis")
    private String getRedis(@RequestParam("key") String key, @RequestParam("type") int type) {
        resultEntity = new ResultEntity();
        Jedis jedis = redisTool.getJedis();
        String val = "";
        if (type==0) {
            if (jedis.exists(key)){
                val = jedis.get(key);
                resultEntity.setCode(0);
                resultEntity.setData(val);
            }else {
                resultEntity.setCode(1);
            }
        } else if (type==1) {
            //        反序列化
            try {
                Jackson2JsonRedisSerializer jack2Seri = new Jackson2JsonRedisSerializer(Object.class);
                byte[] keyObject = jack2Seri.serialize(key);
                byte[] serialize2 = jedis.get(keyObject);
                List<Object> list = (List<Object>) jack2Seri.deserialize(serialize2);
                resultEntity.setCode(0);
                resultEntity.setData(list);
            }catch (Exception e){
                resultEntity.setCode(1);
                resultEntity.setData("无数据");
                e.printStackTrace();
            }
        }

        return JSONTool.getJsonTool().toJson(resultEntity);
    }

    /**
     * 判断是否存在
     * @param key
     * @param type
     * @return
     */
    @GetMapping("getBool")
    private String getBool(@RequestParam("key") String key, @RequestParam("type") int type) {
        resultEntity = new ResultEntity();
        Jedis jedis = redisTool.getJedis();
        if (type==0) {
            resultEntity.setCode(0);
            resultEntity.setData(jedis.exists(key));
        } else if (type==1) {
            //        反序列化
            try {
                Jackson2JsonRedisSerializer jack2Seri = new Jackson2JsonRedisSerializer(Object.class);
                byte[] keyObject = jack2Seri.serialize(key);
                resultEntity.setCode(0);
                resultEntity.setData(jedis.exists(keyObject));
            }catch (Exception e){
                resultEntity.setCode(1);
                resultEntity.setData("无数据");
                e.printStackTrace();
            }
        }

        return JSONTool.getJsonTool().toJson(resultEntity);
    }

    /**
     * 判断是否存在
     * @param key
     * @param type
     * @return
     */
    @GetMapping("updateTimeRedis")
    private String updateTimeRedis(@RequestParam("key") String key, @RequestParam("type") int type) {
        resultEntity = new ResultEntity();
        Jedis jedis = redisTool.getJedis();
        if (type==0) {
            jedis.expire(key, 500);
            resultEntity.setCode(0);
            resultEntity.setData("修改成功");
        } else if (type==1) {
            //        反序列化
            try {
                Jackson2JsonRedisSerializer jack2Seri = new Jackson2JsonRedisSerializer(Object.class);
                byte[] keyObject = jack2Seri.serialize(key);
                jedis.expire(keyObject, 500);
                resultEntity.setCode(0);
                resultEntity.setData("修改成功");
            }catch (Exception e){
                resultEntity.setCode(1);
                resultEntity.setData("无数据");
                e.printStackTrace();
            }
        }

        return JSONTool.getJsonTool().toJson(resultEntity);
    }

    /**
     * 判断是否存在
     * @return
     */
    @GetMapping("online")
    private String online(@RequestParam("key") List<String> list) {
        resultEntity = new ResultEntity();
        try {
            Jedis jedis = redisTool.getJedis();
            List<String> l=new ArrayList<>();
            for (String s:list
            ) {
                if (jedis.exists(s)){
                    l.add(s);
                }
            }
            resultEntity.setCode(0);
            resultEntity.setData(l);
        }catch (Exception e){
            resultEntity.setCode(999);
            resultEntity.setMsg("错误");
        }

        return JSONTool.getJsonTool().toJson(resultEntity);
    }

    /**
     * 判断互关的人，开启聊天室
     * @return
     */
    @GetMapping("match")
    private String match(@RequestParam("userKey") String userKey) {
        resultEntity = new ResultEntity();
        try {
            List<FocusAndFan> list=userService.mutualRelationFocus(userKey);
//            System.out.println(list);
            List<String> u=new ArrayList<>();
            for (FocusAndFan focusAndFan:list) {
                if (userService.mutualRelation(userKey,focusAndFan.getFocus())>0){
                    u.add(focusAndFan.getFocus());
                }
            }
            resultEntity.setCode(0);
            resultEntity.setData(u);
        }catch (Exception e){
            System.out.println("error:"+e);
            resultEntity.setCode(999);
            resultEntity.setMsg("错误");
        }

        return JSONTool.getJsonTool().toJson(resultEntity);
    }


    /**
     * 匹配
     * @return
     */
    @GetMapping("matching")
    private String matching(@RequestParam("userKey") String userKey) {
        resultEntity = new ResultEntity();
        Jedis jedis = redisTool.getJedis();
        try {
            if (jedis.exists("matching")){
                if (userService.addFocus(userKey,jedis.get("matching"))>0 && userService.addFocus(jedis.get("matching"),userKey)>0){
                    resultEntity.setCode(0);
                    jedis.del("matching");
                }else{
                    resultEntity.setCode(1);
                }
            }else{
                jedis.set("matching",userKey);
                jedis.expire("matching",20000);
                resultEntity.setCode(2);
            }

        }catch (Exception e){
            System.out.println("error:"+e);
            resultEntity.setCode(999);
            resultEntity.setMsg("错误");
        }

        return JSONTool.getJsonTool().toJson(resultEntity);
    }

    /**
     * 创建房间者判断
     * @return
     */
    @GetMapping("matchingBoolean")
    private String matchingBoolean(@RequestParam("userKey") String userKey) {
        resultEntity = new ResultEntity();
        Jedis jedis = redisTool.getJedis();
        try {
            if (jedis.exists("matching")){
                if (jedis.get("matching").equals(userKey)){
                    resultEntity.setCode(1);
                }else{
                    resultEntity.setCode(0);
                }
            }else{
                resultEntity.setCode(0);
            }

        }catch (Exception e){
            System.out.println("error:"+e);
            resultEntity.setCode(999);
            resultEntity.setMsg("错误");
        }

        return JSONTool.getJsonTool().toJson(resultEntity);
    }
}
