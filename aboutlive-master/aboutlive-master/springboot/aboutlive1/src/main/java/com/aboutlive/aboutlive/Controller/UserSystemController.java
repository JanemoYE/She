package com.aboutlive.aboutlive.Controller;

import com.aboutlive.aboutlive.Entity.Article;
import com.aboutlive.aboutlive.Entity.Comment;
import com.aboutlive.aboutlive.Entity.ResultEntity;
import com.aboutlive.aboutlive.Entity.User;
import com.aboutlive.aboutlive.Service.UserSystemService;
import com.aboutlive.aboutlive.Util.JSONTool;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin //解决跨域问题
@RestController //控制器注解
@RequestMapping("/UserSystem/")  //请求路径


public class UserSystemController {
    @Resource //依赖注入  （需要用到别的方法和类）
    UserSystemService userSystemService;
    ResultEntity resultEntity = null;

    /**
     * 所有用户
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("AllUser")
    private String AllUser(@RequestParam("page") int page,
                           @RequestParam("limit") int limit) {
        resultEntity = new ResultEntity();
        try {
            int Start=(page-1)*limit;//计算分页起始位置
            List<User> user = userSystemService.AllUser(Start,limit);
            resultEntity.setCode(0);
            resultEntity.setMsg("查询成功");
            resultEntity.setData(user);
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setCode(999);
            resultEntity.setMsg("系统错误，请联系管理员");
        }
        return JSONTool.getJsonTool().toJson(resultEntity);
    }

    /**
     * 所有文章
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("AllArticle")
    private String AllArticle(@RequestParam("page") int page,
                           @RequestParam("limit") int limit) {
        resultEntity = new ResultEntity();
        try {
            int Start=(page-1)*limit;//计算分页起始位置
            List<Article> article = userSystemService.AllArticle(Start,limit);
            resultEntity.setCode(0);
            resultEntity.setMsg("查询成功");
            resultEntity.setData(article);
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setCode(999);
            resultEntity.setMsg("系统错误，请联系管理员");
        }
        return JSONTool.getJsonTool().toJson(resultEntity);
    }

    /**
     * 所有文章
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("articleAudit")
    private String articleAudit(@RequestParam("page") int page,
                           @RequestParam("limit") int limit) {
        resultEntity = new ResultEntity();
        try {
            int Start=(page-1)*limit;//计算分页起始位置
            List<Article> article = userSystemService.articleAudit(Start,limit);
            resultEntity.setCode(0);
            resultEntity.setMsg("查询成功");
            resultEntity.setData(article);
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setCode(999);
            resultEntity.setMsg("系统错误，请联系管理员");
        }
        return JSONTool.getJsonTool().toJson(resultEntity);
    }

    /**
     * 所有评论
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("AllComment")
    private String AllComment(@RequestParam("page") int page,
                           @RequestParam("limit") int limit) {
        resultEntity = new ResultEntity();
        try {
            int Start=(page-1)*limit;//计算分页起始位置
            List<Comment> comment = userSystemService.AllComment(Start,limit);
            List<List<String>> newList=new ArrayList<>();
            for (Comment u:comment) {
                List<String> l=new ArrayList<>();
                l.add(u.getId()+"");
                l.add(u.getArticleId()+"");
                l.add(u.getUserKey());
                l.add(u.getLikeNumber()+"");
                l.add(u.getNextCommentId()+"");
                l.add(u.getHeadImage());
                l.add(u.getName());
                l.add(u.getUserContent());
                l.add(u.getTime());
                if (u.getExperience()<1000){
                    l.add("1");
                }else if (u.getExperience()<2000){
                    l.add("2");
                }else if (u.getExperience()<3000){
                    l.add("3");
                }else if (u.getExperience()<4000){
                    l.add("4");
                }else if (u.getExperience()<5000){
                    l.add("5");
                }else{
                    l.add("6");
                }
                l.add(u.getUserKey());
                newList.add(l);
            }
            resultEntity.setCode(0);
            resultEntity.setMsg("查询成功");
            resultEntity.setData(comment);
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setCode(999);
            resultEntity.setMsg("系统错误，请联系管理员");
        }
        return JSONTool.getJsonTool().toJson(resultEntity);
    }

    @GetMapping("delUser")
    private String delUser() {
        resultEntity = new ResultEntity();
        try {

            resultEntity.setCode(0);
            resultEntity.setMsg("查询成功");
//            resultEntity.setData();
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setCode(999);
            resultEntity.setMsg("系统错误，请联系管理员");
        }
        return JSONTool.getJsonTool().toJson(resultEntity);
    }

    /**
     * 获取所有评论的数量
     * @return
     */
    @GetMapping("getCommentCount")
    private String getCommentCount() {
        resultEntity = new ResultEntity();
        try {
            int count=userSystemService.getCommentCount();
            count=(int)Math.ceil((double)count/20);
            resultEntity.setCode(0);
            resultEntity.setData(count);
            resultEntity.setMsg("查询成功");
//            resultEntity.setData();
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setCode(999);
            resultEntity.setMsg("系统错误，请联系管理员");
        }
        return JSONTool.getJsonTool().toJson(resultEntity);
    }

    /**
     * 获取用户数量
     * @return
     */
    @GetMapping("getUserCount")
    private String getUserCount() {
        resultEntity = new ResultEntity();
        try {
            int count=userSystemService.getUserCount();
            count=(int)Math.ceil((double)count/20);
            resultEntity.setCode(0);
            resultEntity.setData(count);
            resultEntity.setMsg("查询成功");
//            resultEntity.setData();
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setCode(999);
            resultEntity.setMsg("系统错误，请联系管理员");
        }
        return JSONTool.getJsonTool().toJson(resultEntity);
    }

    /**
     * 获取所有文章的数量
     * @return
     */
    @GetMapping("getArticleCount")
    private String getArticleCount() {
        resultEntity = new ResultEntity();
        try {
            int count=userSystemService.getArticleCount();
            count=(int)Math.ceil((double)count/10);
            resultEntity.setCode(0);
            resultEntity.setData(count);
            resultEntity.setMsg("查询成功");
//            resultEntity.setData();
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setCode(999);
            resultEntity.setMsg("系统错误，请联系管理员");
        }
        return JSONTool.getJsonTool().toJson(resultEntity);
    }

    /**
     * 获取所有评论的数量不是分页
     * @return
     */
    @GetMapping("commentCount")
    private String commentCount() {
        resultEntity = new ResultEntity();
        try {
            int count=userSystemService.getCommentCount();
            resultEntity.setCode(0);
            resultEntity.setData(count);
            resultEntity.setMsg("查询成功");
//            resultEntity.setData();
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setCode(999);
            resultEntity.setMsg("系统错误，请联系管理员");
        }
        return JSONTool.getJsonTool().toJson(resultEntity);
    }

    /**
     * 获取用户数量
     * @return
     */
    @GetMapping("userCount")
    private String userCount() {
        resultEntity = new ResultEntity();
        try {
            int count=userSystemService.getUserCount();
            resultEntity.setCode(0);
            List<Object> list=new ArrayList<>();
            Map<Object,Object> map=new HashMap<>();
            map.put("count",count);
            list.add(map);
            resultEntity.setData(list);
            resultEntity.setMsg("查询成功");
//            resultEntity.setData();
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setCode(999);
            resultEntity.setMsg("系统错误，请联系管理员");
        }
        return JSONTool.getJsonTool().toJson(resultEntity);
    }

    /**
     * 查看互动
     * @return
     */
    @GetMapping("interactive")
    private String interactive() {
        resultEntity = new ResultEntity();
        try {
            int count=userSystemService.getInteractive();
            resultEntity.setCode(0);
            resultEntity.setData(count);
            resultEntity.setMsg("查询成功");
//            resultEntity.setData();
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setCode(999);
            resultEntity.setMsg("系统错误，请联系管理员");
        }
        return JSONTool.getJsonTool().toJson(resultEntity);
    }

    /**
     * 查看互动
     * @return
     */
    @GetMapping("articleState")
    private String articleState(@RequestParam("state") int state,@RequestParam("id") int id) {
        resultEntity = new ResultEntity();
        try {
            if (userSystemService.articleState(state, id)>0){
                resultEntity.setCode(0);
                resultEntity.setMsg("成功");
            }else{
                resultEntity.setCode(1);
                resultEntity.setMsg("失败");
            }

//            resultEntity.setData();
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setCode(999);
            resultEntity.setMsg("系统错误，请联系管理员");
        }
        return JSONTool.getJsonTool().toJson(resultEntity);
    }

    /**
     * 获取所有文章的数量
     * @return
     */
    @GetMapping("articleCount")
    private String articleCount() {
        resultEntity = new ResultEntity();
        try {
            int count=userSystemService.getArticleCount();
            resultEntity.setCode(0);
            resultEntity.setData(count);
            resultEntity.setMsg("查询成功");
//            resultEntity.setData();
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setCode(999);
            resultEntity.setMsg("系统错误，请联系管理员");
        }
        return JSONTool.getJsonTool().toJson(resultEntity);
    }

    /**
     * 通过id 查询单个用户的信息
     * @param id
     * @return
     */
    @GetMapping("userInformartion")
    private String userInformartion(@RequestParam("id") int id){
        resultEntity = new ResultEntity();
        try {
            User user=userSystemService.userInformartion(id);
            resultEntity.setCode(0);
            resultEntity.setData(user);
            resultEntity.setMsg("查询成功");
//            resultEntity.setData();
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setCode(999);
            resultEntity.setMsg("系统错误，请联系管理员");
        }
        return JSONTool.getJsonTool().toJson(resultEntity);
    }


    @PutMapping("userUpData")
    private String userUpData(@RequestParam("UName") String UName,@RequestParam("UPwd") String UPwd,
                              @RequestParam("email") String email,@RequestParam("sex") int sex,
                              @RequestParam("born") String born, @RequestParam("experience") int experience){
        resultEntity = new ResultEntity();
        try {
            int u=userSystemService.userUpDate(UName, UPwd, email, sex, born, experience);
            if (u>0){
                resultEntity.setCode(0);
            }else {
                resultEntity.setCode(-1);
            }
//            resultEntity.setData();
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setCode(999);
            resultEntity.setMsg("系统错误，请联系管理员");
        }
        return JSONTool.getJsonTool().toJson(resultEntity);
    }

    /**
     * 删除用户评论
     *
     * @param id
     * @return
     */
    @DeleteMapping("delComment")
    private String delComment(@RequestParam("id") int id) {
        resultEntity = new ResultEntity();
        try {
            if (userSystemService.delComment(id) > 0) {

                resultEntity.setCode(0);
                resultEntity.setMsg("删除成功！");
            } else {
                resultEntity.setCode(1);
                resultEntity.setMsg("删除失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setCode(999);
            resultEntity.setMsg("系统错误，请联系管理员");
        }
        return JSONTool.getJsonTool().toJson(resultEntity);
    }

    /**
     * 账号   解封/封号
     * @param id
     * @return
     */
    @PutMapping("state")
    private String state(@RequestParam("id") int id,@RequestParam("zt") int zt) {
        resultEntity = new ResultEntity();
        try {
            if (zt == 1) {
                if (userSystemService.stateFalse(id)>0){
                    resultEntity.setCode(0);
                    resultEntity.setMsg("封号成功！");
                }else {
                    resultEntity.setCode(0);
                    resultEntity.setMsg("封号失败！");
                }

            } else {
                if (userSystemService.stateTrue(id)>0){
                    resultEntity.setCode(0);
                    resultEntity.setMsg("解封成功！");
                }else {
                    resultEntity.setCode(0);
                    resultEntity.setMsg("解封失败！");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setCode(999);
            resultEntity.setMsg("系统错误，请联系管理员");
        }
        return JSONTool.getJsonTool().toJson(resultEntity);
    }

    /**
     * 用户禁言
     * @param userKey
     * @param muteStart
     * @param muteEnd
     * @return json
     */
    @PostMapping("mute")
    private String mute(@RequestParam("userKey") String userKey,@RequestParam("muteStart") String muteStart,@RequestParam("muteEnd") String muteEnd) {
        resultEntity = new ResultEntity();
        try {
            if (userSystemService.mute(userKey, muteStart, muteEnd)>0){
                resultEntity.setCode(0);
                resultEntity.setMsg("禁言成功！");
            }else{
                resultEntity.setCode(1);
                resultEntity.setMsg("禁言失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
            resultEntity.setCode(999);
            resultEntity.setMsg("系统错误，请联系管理员");
        }
        return JSONTool.getJsonTool().toJson(resultEntity);
    }

    @GetMapping("countMute")
    private String countMute(@RequestParam("userKey") String userKey) {
        resultEntity = new ResultEntity();
        try {

                resultEntity.setCode(0);
                resultEntity.setData(userSystemService.countMute(userKey));

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
            resultEntity.setCode(999);
            resultEntity.setMsg("系统错误，请联系管理员");
        }
        return JSONTool.getJsonTool().toJson(resultEntity);
    }
    // restful接口规范 : getMapping：获取 postMapping：增加 deleteMapping：删除 putMapping：更新
}
