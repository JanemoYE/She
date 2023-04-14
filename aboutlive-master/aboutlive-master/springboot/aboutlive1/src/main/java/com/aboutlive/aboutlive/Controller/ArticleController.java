package com.aboutlive.aboutlive.Controller;

import com.aboutlive.aboutlive.Entity.*;
import com.aboutlive.aboutlive.Service.ArticleService;
import com.aboutlive.aboutlive.Service.UserService;
import com.aboutlive.aboutlive.Util.JSONTool;
import com.aboutlive.aboutlive.tool.OSSUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.aboutlive.aboutlive.tool.ImageBase64Util.base64ToFile;

@CrossOrigin //解决跨域问题
@RestController //控制器注解
@RequestMapping("/Article/")  //请求路径
public class ArticleController {
    @Resource //依赖注入  （需要用到别的方法和类）
    ArticleService articleService;
    @Resource //依赖注入  （需要用到别的方法和类）
    UserService userService;
    ResultEntity resultEntity = null;

    @Resource
    OSSUtil ossUtil;

    /**
     * 获取文章内容，下一个文章和上一个文章来回切换
     *
     * @param id
     * @return JSONTool
     */
    @GetMapping("content")
    private String selectArticle(@RequestParam("id") int id) {
        resultEntity = new ResultEntity();
        try {
            Article article = articleService.article(id);
            if (article != null) {
                List<String> list = new ArrayList<>();
                list.add(article.getId() + "");
                list.add(article.getUserKey());
                list.add(article.getImagesBase64());
                list.add(article.getContent());
                list.add(article.getAuthor());
                list.add(article.getFrom() + "");
                list.add(article.getTime() + "");
                resultEntity.setCode(0);
                resultEntity.setMsg("查询成功！");
                resultEntity.setData(list);
            } else {
                resultEntity.setCode(1);
                resultEntity.setMsg("错误，未获取到文章信息！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setCode(999);
            resultEntity.setMsg("系统错误，请联系管理员");
        }
        return JSONTool.getJsonTool().toJson(resultEntity);
    }

    /**
     * 获取当前文章下的评论
     *
     * @param page
     * @param limit
     * @param id
     * @return List
     */

    @GetMapping("comment")
    private String selectArticleComment(@RequestParam("page") int page,
                                        @RequestParam("limit") int limit,
                                        @RequestParam("id") int id
    ) {
        resultEntity = new ResultEntity();
        try {
            int Start = (page - 1) * limit;//计算分页起始位置
            List<Comment> list = articleService.comment(Start, limit, id);
            List<List<String>> newList = new ArrayList<>();
            for (Comment u : list) {
                List<String> l = new ArrayList<>();
                l.add(u.getHeadImage());
                l.add(u.getName());
                l.add(u.getUserContent());
                l.add(u.getTime());
                if (u.getExperience() < 1000) {
                    l.add("1");
                } else if (u.getExperience() < 2000) {
                    l.add("2");
                } else if (u.getExperience() < 3000) {
                    l.add("3");
                } else if (u.getExperience() < 4000) {
                    l.add("4");
                } else if (u.getExperience() < 5000) {
                    l.add("5");
                } else {
                    l.add("6");
                }
                l.add(u.getUserKey());
                l.add(u.getId()+"");
                l.add(u.getNextCommentId()+"");
                newList.add(l);
            }
            if (id != -1) {//有查询条件，返回长度
                resultEntity.setCount(String.valueOf(list.size()));
            } else {
                resultEntity.setCount(articleService.getCommentCount() + "");
            }
            resultEntity.setCode(0);
            resultEntity.setData(newList);
            resultEntity.setMsg("查询成功");
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setCode(999);
            resultEntity.setMsg("错误" + e.getMessage());
        }


        return JSONTool.getJsonTool().toJson(resultEntity);
    }

    /**
     * 删除用户评论
     *
     * @param id
     * @param userKey
     * @return
     */
    @DeleteMapping("delComment")
    private String delComment(@RequestParam("userKey") String userKey, @RequestParam("id") int id) {
        resultEntity = new ResultEntity();
        try {
            if (articleService.delComment(userKey, id) > 0) {

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
     * 主页评论功能
     *
     * @param articleId
     * @param userKey
     * @param userContent
     * @param time
     * @return JSONTool T or F
     */
    @GetMapping("addComment")
    private String addComment(@RequestParam("articleId") String articleId, @RequestParam("userKey") String userKey,
                              @RequestParam("userContent") String userContent, @RequestParam("time") String time) {
        resultEntity = new ResultEntity();
        try {
            if (articleService.addComment(articleId, userKey, userContent, time) > 0) {
                Article article = articleService.article(Integer.parseInt(articleId));
                articleService.addInformation(article.getUserKey(), "评论了你。", "评论", userKey, 1, articleId);
                resultEntity.setCode(0);
                resultEntity.setMsg("评论成功！");
            } else {
                resultEntity.setCode(1);
                resultEntity.setMsg("评论失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setCode(999);
            resultEntity.setMsg("系统错误，请联系管理员");
        }
        return JSONTool.getJsonTool().toJson(resultEntity);
    }

    /**
     * 回复
     * @param articleId
     * @param
     * @param userContent
     * @param userKey
     * @param replyUser
     * @param time
     * @return
     */
    @GetMapping("addReplyComment")
    private String addReplyComment(@RequestParam("articleId") String articleId,
                              @RequestParam("userContent") String userContent,@RequestParam("userKey") String userKey,
                                   @RequestParam("replyUser") String replyUser,@RequestParam("time") String time) {
        resultEntity = new ResultEntity();
        try {
            if (articleService.addReplyComment(articleId, userKey, userContent, replyUser,time) > 0) {
                Article article= articleService.article(Integer.parseInt(articleId));
                articleService.addInformation(article.getUserKey(), "回复了你。", "评论", userKey, 1, articleId);
                resultEntity.setCode(0);
                resultEntity.setMsg("评论成功！");
            } else {
                resultEntity.setCode(1);
                resultEntity.setMsg("评论失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setCode(999);
            resultEntity.setMsg("系统错误，请联系管理员");
        }
        return JSONTool.getJsonTool().toJson(resultEntity);
    }

    /**
     * 文章发布功能，add页面
     *
     * @param userKey
     * @param imagesBase64
     * @param content
     * @param author
     * @param from
     * @param time
     * @return JSON
     */
    @PostMapping("addArticle")
    private String addArticle(@RequestParam("userKey") String userKey, @RequestParam("imagesBase64") String imagesBase64,
                              @RequestParam("content") String content, @RequestParam("author") String author,
                              @RequestParam("from") String from, @RequestParam("time") String time,
                              @RequestParam("type") String type, @RequestParam("featured") String featured) {
        resultEntity = new ResultEntity();
        try {
            System.out.println("--------------------1111--------------------");
            File file = base64ToFile(imagesBase64);
            System.err.println("getFinishPicFile: " + file.getPath());
            System.out.println("--------------------1111--------------------");

            ossUtil.updateFile(file.getPath(), file.getName().substring(0, file.getName().length() - 5));//图片地址+图片名字（用作者名字代替）

            if (articleService.addArticle(userKey, "http://" + ossUtil.downLoadUrl(file.getName().substring(0, file.getName().length() - 5)), content, author, from, time, type, featured) > 0) {
                resultEntity.setCode(0);
                resultEntity.setMsg("评论成功！");
            } else {
                resultEntity.setCode(1);
                resultEntity.setMsg("评论失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setCode(999);
            resultEntity.setMsg("系统错误，请联系管理员");
        }
        return JSONTool.getJsonTool().toJson(resultEntity);
    }

    /**
     * 查询指定用户的所有评论
     *
     * @param userKey
     * @return list
     */
    @GetMapping("userAllComment")
    private String userAllComment(@RequestParam("userKey") String userKey) {
        resultEntity = new ResultEntity();
        try {
            List<Comment> list = articleService.userAllComment(userKey);
            List<List<String>> newList = new ArrayList<>();
            for (Comment u : list) {
                List<String> l = new ArrayList<>();
                l.add(u.getHeadImage());
                l.add(u.getName());
                l.add(u.getUserContent());
                l.add(u.getTime());
                l.add(u.getArticleId() + "");
                newList.add(l);
            }
            resultEntity.setCode(0);
            resultEntity.setData(newList);
            resultEntity.setMsg("查询成功");
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setCode(999);
            resultEntity.setMsg("错误" + e.getMessage());
        }

        return JSONTool.getJsonTool().toJson(resultEntity);
    }

    /**
     * 首页推荐
     * @param featured
     * @param id
     * @return
     */
    @GetMapping("featuredUpdate")
    private String featuredUpdate(@RequestParam("featured") String featured,@RequestParam("id") int id) {
        resultEntity = new ResultEntity();
        try {
            if (articleService.featuredUpdate(featured,id)>0){
                resultEntity.setCode(0);
                resultEntity.setMsg("成功");
            }else {
                resultEntity.setCode(1);
                resultEntity.setMsg("失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setCode(999);
            resultEntity.setMsg("错误" + e.getMessage());
        }

        return JSONTool.getJsonTool().toJson(resultEntity);
    }

    /**
     * 获取评论详细页面的图片和发布人
     *
     * @param id
     * @return JSONTool
     */
    @GetMapping("articleComment")
    private String articleComment(@RequestParam("id") int id) {
        resultEntity = new ResultEntity();
        try {
            Article article = articleService.articleComment(id);
            if (article != null) {
                List<String> list = new ArrayList<>();
                list.add(article.getImagesBase64());
                list.add(article.getTime());
                list.add(article.getUserKey());
                User user = userService.selectUser(article.getUserKey());
                list.add(user.getName());
                list.add(user.getHeadImage());
                resultEntity.setCode(0);
                resultEntity.setMsg("查询成功！");
                resultEntity.setData(list);
            } else {
                resultEntity.setCode(1);
                resultEntity.setMsg("错误，未获取到文章信息！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setCode(999);
            resultEntity.setMsg("系统错误，请联系管理员");
        }
        return JSONTool.getJsonTool().toJson(resultEntity);
    }

    /**
     * 获取该用户发布的所有文章
     *
     * @param userKey
     * @return list
     */
    @GetMapping("userAllArticle")
    private String userAllArticle(@RequestParam("userKey") String userKey) {
        resultEntity = new ResultEntity();
        try {
            List<Article> article = articleService.userAllArticle(userKey);
            List<List<String>> list = new ArrayList<>();
            for (Article a : article) {
                List<String> l = new ArrayList<>();
                l.add(a.getImagesBase64());
                l.add(a.getAuthor());
                l.add(a.getContent());
                l.add(a.getId() + "");
                int likeCount = articleService.articleLikeCount(a.getId());
                if (likeCount > 100000) {
                    l.add(String.format("%.2f", ((double) likeCount) / (double) 100000) + "w");
                } else if (likeCount > 1000) {
                    l.add(String.format("%.1f", ((double) likeCount) / (double) 1000) + "k");
                } else {
                    l.add(likeCount + "");
                }

                int commentCount = articleService.commentArticleIdCount(a.getId());
                if (commentCount > 100000) {
                    l.add(String.format("%.2f", ((double) commentCount) / (double) 100000) + "w");
                } else if (commentCount > 1000) {
                    l.add(String.format("%.1f", ((double) commentCount) / (double) 1000) + "k");
                } else {
                    l.add(commentCount + "");
                }
                list.add(l);
            }
            resultEntity.setCode(0);
            resultEntity.setMsg("查询成功！");
            resultEntity.setData(list);
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setCode(999);
            resultEntity.setMsg("系统错误，请联系管理员");
        }
        return JSONTool.getJsonTool().toJson(resultEntity);
    }

    /**
     * 添加收藏
     *
     * @param userKey
     * @param articleId
     * @return json
     */
    @PostMapping("addLikeArticle")
    private String addLikeArticle(@RequestParam("userKey") String userKey, @RequestParam("articleId") String articleId) {
        resultEntity = new ResultEntity();
        try {
            if (articleService.addLikeArticle(userKey, articleId) > 0) {
                Article article = articleService.article(Integer.parseInt(articleId));
                articleService.addInformation(article.getUserKey(), "给你的文章点了赞", "点赞", userKey, 1, articleId);
                resultEntity.setCode(0);
                resultEntity.setMsg("收藏成功！");
            } else {
                resultEntity.setCode(1);
                resultEntity.setMsg("收藏失败！");
            }

        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setCode(999);
            resultEntity.setMsg("系统错误，请联系管理员");
        }
        return JSONTool.getJsonTool().toJson(resultEntity);
    }

    /**
     * 取消收藏
     *
     * @param userKey
     * @param articleId
     * @return json
     */
    @GetMapping("delLikeArticle")
    private String delLikeArticle(@RequestParam("userKey") String userKey, @RequestParam("articleId") String articleId) {
        resultEntity = new ResultEntity();
        try {
            if (articleService.delLikeArticle(userKey, articleId) > 0) {
                articleService.delInformation(userKey, articleId);
                resultEntity.setCode(0);
                resultEntity.setMsg("取消收藏成功！");
            } else {
                resultEntity.setCode(1);
                resultEntity.setMsg("取消收藏失败！");
            }

        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setCode(999);
            resultEntity.setMsg("系统错误，请联系管理员");
        }
        return JSONTool.getJsonTool().toJson(resultEntity);
    }

    /**
     * 删除文章
     *
     * @param id
     * @return json
     */
    @DeleteMapping("delArticle")
    private String delArticle(@RequestParam("id") String id) {
        resultEntity = new ResultEntity();
        try {
            if (articleService.delArticle(id) > 0) {
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
     * 收藏用户的userKey / 查找用户是否已经收藏，list.size==0的时候是未收藏
     *
     * @param userKey
     * @param articleId
     * @return json
     */
    @GetMapping("articleLikeTrue")
    private String articleLikeTrue(@RequestParam("userKey") String userKey, @RequestParam("articleId") String articleId) {
        resultEntity = new ResultEntity();
        try {
            ArticleLike articleLike = articleService.articleLike(userKey, articleId);
            if (articleLike != null) {
                resultEntity.setCode(0);
                resultEntity.setMsg("已收藏！");
            } else {
                resultEntity.setCode(1);
                resultEntity.setMsg("未收藏！");
            }

        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setCode(999);
            resultEntity.setMsg("系统错误，请联系管理员");
        }
        return JSONTool.getJsonTool().toJson(resultEntity);
    }

    /**
     * 获取收藏的文章id，通过id获取文章内容
     *
     * @param userKey
     * @return list
     */
    @GetMapping("articleLikeList")
    private String articleLikeList(@RequestParam("userKey") String userKey) {
        resultEntity = new ResultEntity();
        try {
            List<ArticleLike> article = articleService.articleLikeList(userKey);
            List<List<String>> list = new ArrayList<>();
            for (ArticleLike a : article) {
                try{
                    List<String> l = new ArrayList<>();
                    Article article1 = articleService.article(a.getArticleId());
                    l.add(article1.getId() + "");
                    l.add(article1.getImagesBase64());
                    l.add(article1.getContent());
                    l.add(article1.getAuthor());
                    l.add(articleService.articleLikeCount(a.getArticleId()) + "");
                    l.add(articleService.commentArticleIdCount(a.getArticleId()) + "");
                    list.add(l);
                }catch (Exception e){
                    System.out.println(e);
                }
            }
            resultEntity.setCode(0);
            resultEntity.setMsg("已收藏！");
            resultEntity.setData(list);

        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setCode(999);
            resultEntity.setMsg("系统错误，请联系管理员");
        }
        return JSONTool.getJsonTool().toJson(resultEntity);
    }

    /**
     * 获取文章评论的总数量
     * @param id
     * @return
     */
    @GetMapping("commentArticleIdCount")
    private String commentArticleIdCount(@RequestParam("id") int id) {
        resultEntity = new ResultEntity();
        try {
            resultEntity.setCode(0);
            resultEntity.setMsg("已收藏！");
            resultEntity.setData(articleService.commentArticleIdCount(id));
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setCode(999);
            resultEntity.setMsg("系统错误，请联系管理员");
        }
        return JSONTool.getJsonTool().toJson(resultEntity);
    }

    /**
     * 获取所有文章的id
     *
     * @param
     * @return list
     */
    @GetMapping("allArticleId")
    private String allArticleId() {
        resultEntity = new ResultEntity();
        try {
            List<Integer> list = articleService.allArticleId();
            resultEntity.setCode(0);
            resultEntity.setMsg("已收藏！");
            resultEntity.setData(list);
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setCode(999);
            resultEntity.setMsg("系统错误，请联系管理员");
        }
        return JSONTool.getJsonTool().toJson(resultEntity);
    }

    /**
     * 随机获取十条精选文章
     *
     * @param
     * @return list
     */
    @GetMapping("randFeatured")
    private String randFeatured() {
        resultEntity = new ResultEntity();
        try {
            List<Article> list = articleService.randFeatured();
            resultEntity.setCode(0);
            resultEntity.setData(list);
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setCode(999);
            resultEntity.setMsg("系统错误，请联系管理员");
        }
        return JSONTool.getJsonTool().toJson(resultEntity);
    }

    /**
     * 随机获取十条摘自文章
     *
     * @param
     * @return list
     */
    @GetMapping("randTaken")
    private String randTaken() {
        resultEntity = new ResultEntity();
        try {
            List<Article> list = articleService.randTaken();
            resultEntity.setCode(0);
            resultEntity.setData(list);
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setCode(999);
            resultEntity.setMsg("系统错误，请联系管理员");
        }
        return JSONTool.getJsonTool().toJson(resultEntity);
    }

    /**
     * 随机获取十条原创文章
     *
     * @param
     * @return list
     */
    @GetMapping("randOriginal")
    private String randOriginal() {
        resultEntity = new ResultEntity();
        try {
            List<Article> list = articleService.randOriginal();
            resultEntity.setCode(0);
            resultEntity.setData(list);
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setCode(999);
            resultEntity.setMsg("系统错误，请联系管理员");
        }
        return JSONTool.getJsonTool().toJson(resultEntity);
    }

    /**
     * 查询关键字
     *
     * @return
     */
    @GetMapping("searchContent")
    private String searchContent(@RequestParam("text") String text) {
        resultEntity = new ResultEntity();
        try {
            List<Article> list = articleService.searchContent(text);
            resultEntity.setCode(0);
            resultEntity.setData(list);
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setCode(999);
            resultEntity.setMsg("系统错误，请联系管理员");
        }
        return JSONTool.getJsonTool().toJson(resultEntity);
    }

    /**
     * 发送信息
     *
     * @param userKey
     * @param content
     * @param type
     * @param sendUserKey
     * @param read
     * @return
     */
    @PostMapping("addInformation")
    private String addInformation(@RequestParam("userKey") String userKey, @RequestParam("content") String content,
                                  @RequestParam("type") String type, @RequestParam("sendUserKey") String sendUserKey,
                                  @RequestParam("read") int read, @RequestParam("articleId") String articleId) {
        resultEntity = new ResultEntity();
        try {
            if (articleService.addInformation(userKey, content, type, sendUserKey, read, articleId) > 0) {
                resultEntity.setCode(0);
                resultEntity.setMsg("成功");
            } else {
                resultEntity.setCode(1);
                resultEntity.setMsg("失败");
            }

        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setCode(999);
            resultEntity.setMsg("系统错误，请联系管理员");
        }
        return JSONTool.getJsonTool().toJson(resultEntity);
    }

    /**
     * 删除消息
     * @param userKey
     * @param articleId
     * @return
     */
    @DeleteMapping("delInformation")
    private String addInformation(@RequestParam("userKey") String userKey, @RequestParam("articleId") String articleId) {
        resultEntity = new ResultEntity();
        try {
            if (articleService.delInformation(userKey, articleId) > 0) {
                resultEntity.setCode(0);
                resultEntity.setMsg("成功");
            } else {
                resultEntity.setCode(1);
                resultEntity.setMsg("失败");
            }

        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setCode(999);
            resultEntity.setMsg("系统错误，请联系管理员");
        }
        return JSONTool.getJsonTool().toJson(resultEntity);
    }

    /**
     * 用户主页一共多少条新消息
     * @param userKey
     * @return
     */
    @GetMapping("getInformationCount")
    private String getInformationCount(@RequestParam("userKey") String userKey) {
        resultEntity = new ResultEntity();
        try {
            resultEntity.setCode(0);
            resultEntity.setMsg("成功");
            resultEntity.setData(articleService.getInformationCount(userKey));

        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setCode(999);
            resultEntity.setMsg("系统错误，请联系管理员");
        }
        return JSONTool.getJsonTool().toJson(resultEntity);
    }

    /**
     * 用户查看目标类型页面时的详细信息获取
     * @param userKey
     * @return
     */
    @GetMapping("getUserInformation")
    private String getUserInformation(@RequestParam("userKey") String userKey,@RequestParam("type") String type) {
        resultEntity = new ResultEntity();
        try {
            List<List<String>> list=new ArrayList<>();
            List<Information> infor=articleService.getUserInformation(userKey,type);
            for (Information i:infor){
                List<String> l=new ArrayList<>();
                l.add(i.getContent());
                l.add(i.getArticleId());
                User user=userService.selectUser(i.getSendUserKey());
                l.add(user.getHeadImage());
                l.add(user.getName());
                l.add(i.getSendUserKey());
                Article article=articleService.article(Integer.parseInt(i.getArticleId()));
                l.add(article.getImagesBase64());
                list.add(l);
            }
            articleService.readUpData(userKey,type);
            resultEntity.setCode(0);
            resultEntity.setMsg("成功");
            resultEntity.setData(list);

        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setCode(999);
            resultEntity.setMsg("系统错误，请联系管理员");
        }
        return JSONTool.getJsonTool().toJson(resultEntity);
    }

    /**
     * 查询最新20条该类型的已读数据
     * @param userKey
     * @param type
     * @return
     */
    @GetMapping("getUserInformation20")
    private String getUserInformation20(@RequestParam("userKey") String userKey,@RequestParam("type") String type) {
        resultEntity = new ResultEntity();
        try {
            List<List<String>> list=new ArrayList<>();
            List<Information> infor=articleService.getUserInformation20(userKey,type);
            for (Information i:infor){
                List<String> l=new ArrayList<>();
                try {
                    l.add(i.getContent());
                    l.add(i.getArticleId());
                    User user=userService.selectUser(i.getSendUserKey());
                    l.add(user.getHeadImage());
                    l.add(user.getName());
                    l.add(i.getSendUserKey());
                    Article article=articleService.article(Integer.parseInt(i.getArticleId()));
                    l.add(article.getImagesBase64());
                    list.add(l);
                }catch (Exception e){
                    System.out.println(e);
                }

            }
            resultEntity.setCode(0);
            resultEntity.setMsg("成功");
            resultEntity.setData(list);

        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setCode(999);
            resultEntity.setMsg("系统错误，请联系管理员");
        }
        return JSONTool.getJsonTool().toJson(resultEntity);
    }

    @GetMapping("detect")
    private String detect(@RequestParam("UName") String UName){
        resultEntity = new ResultEntity();
        try {
            if (userService.detect(UName)>0){
                resultEntity.setCode(1);
                resultEntity.setMsg("账号不可用");
            }else {
                resultEntity.setCode(0);
                resultEntity.setMsg("账号可用");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setCode(999);
            resultEntity.setMsg("系统错误，请联系管理员");
        }
        return JSONTool.getJsonTool().toJson(resultEntity);
    }

    /**
     * 将用户指定的类型的所有未读消息变为已读
     * @param userKey
     * @param type
     * @return
     */
    @PutMapping("readUpData")
    private String readUpData(@RequestParam("userKey") String userKey,@RequestParam("type") String type) {
        resultEntity = new ResultEntity();
        try {
            articleService.readUpData(userKey,type);
            resultEntity.setCode(0);
            resultEntity.setMsg("成功");
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setCode(999);
            resultEntity.setMsg("系统错误，请联系管理员");
        }
        return JSONTool.getJsonTool().toJson(resultEntity);
    }

    /**
     * 获取指定类型的消息未读数量
     * @param userKey
     * @param type
     * @return
     */
    @GetMapping("readCount")
    private String readCount(@RequestParam("userKey") String userKey,@RequestParam("type") String type) {
        resultEntity = new ResultEntity();
        try {
            resultEntity.setCode(0);
            resultEntity.setMsg("成功");
            resultEntity.setData(articleService.readCount(userKey,type));
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setCode(999);
            resultEntity.setMsg("系统错误，请联系管理员");
        }
        return JSONTool.getJsonTool().toJson(resultEntity);
    }

    /**
     * 获取所有的系统消息
     * @return
     */
    @GetMapping("getSystemInformation")
    private String getSystemInformation() {
        resultEntity = new ResultEntity();
        try {
            List<Information> information=articleService.getSystemInformation();
            articleService.readUpData("all","系统");
            resultEntity.setCode(0);
            resultEntity.setMsg("成功");
            resultEntity.setData(information);
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setCode(999);
            resultEntity.setMsg("系统错误，请联系管理员");
        }
        return JSONTool.getJsonTool().toJson(resultEntity);
    }

    /**
     * 我关注的用户所发布的文章
     * @return
     */
    @GetMapping("myFocusArticle")
    private String myFocusArticle() {
        resultEntity = new ResultEntity();
        try {
            List<Information> information=articleService.getSystemInformation();
            articleService.readUpData("all","系统");
            resultEntity.setCode(0);
            resultEntity.setMsg("成功");
            resultEntity.setData(information);
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setCode(999);
            resultEntity.setMsg("系统错误，请联系管理员");
        }
        return JSONTool.getJsonTool().toJson(resultEntity);
    }

    /**
     * 查询点赞前100的文章
     * @return
     */
    @GetMapping("articleLike100")
    private String articleLike100() {
        resultEntity = new ResultEntity();
        try {
            List<ArticleLike100> articleLike100s=articleService.articleLike100();
            List<List<String>> list=new ArrayList<>();
            for (ArticleLike100 a:articleLike100s){
                List<String> l=new ArrayList<>();
                l.add(a.getId()+"");
                l.add(a.getContent());
                User user=userService.selectUser(a.getUserKey());
                l.add(user.getHeadImage());
                l.add(user.getName());
                double like=Double.parseDouble(a.getLikeCount());
                if (like>1000){
                    l.add(String.format(".2f",like/1000)+"K");
                }else if (like>10000){
                    l.add(String.format(".2f",like/10000)+"W");
                }else{
                    l.add(a.getLikeCount());
                }

                list.add(l);
            }
            resultEntity.setCode(0);
            resultEntity.setMsg("成功");
            resultEntity.setData(list);
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setCode(999);
            resultEntity.setMsg("系统错误，请联系管理员");
        }
        return JSONTool.getJsonTool().toJson(resultEntity);
    }

    /**
     * 随机查询十条该类型的数据
     * @return
     */
    @GetMapping("articleType10")
    private String articleType10(@RequestParam("type") String type) {
        resultEntity = new ResultEntity();
        try {
            List<Article> list = articleService.articleType10(type);
            resultEntity.setCode(0);
            resultEntity.setMsg("成功");
            resultEntity.setData(list);
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setCode(999);
            resultEntity.setMsg("系统错误，请联系管理员");
        }
        return JSONTool.getJsonTool().toJson(resultEntity);
    }

    /**
     * 查询最近的十条文章
     * @return
     */
    @GetMapping("newArticle")
    private String newArticle() {
        resultEntity = new ResultEntity();
        try {
            List<Article> list = articleService.newArticle();
            resultEntity.setCode(0);
            resultEntity.setMsg("成功");
            resultEntity.setData(list);
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setCode(999);
            resultEntity.setMsg("系统错误，请联系管理员");
        }
        return JSONTool.getJsonTool().toJson(resultEntity);
    }

    /**
     * 首页推荐图片
     * @return
     */
    @GetMapping("featured")
    private String featured() {
        resultEntity = new ResultEntity();
        try {
            List<Article> list = articleService.featured();
            resultEntity.setCode(0);
            resultEntity.setMsg("成功");
            resultEntity.setData(list);
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setCode(999);
            resultEntity.setMsg("系统错误，请联系管理员");
        }
        return JSONTool.getJsonTool().toJson(resultEntity);
    }

    // restful接口规范 : getMapping：获取 postMapping：增加 deleteMapping：删除 putMapping：更新
}
