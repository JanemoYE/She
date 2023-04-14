package com.aboutlive.aboutlive.Impl;

import com.aboutlive.aboutlive.Entity.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper//表示mybatis的XML对应的接口,id的名字
public interface ArticleImpl {
    Article article(int id);

    List<Comment> comment(int Start, int Page, int id);//分页查询所有用户
    int delComment(String userKey,int id);//删除评论

    int getCommentCount();//获取评论数量
    int getInformationCount(String userKey);//获取信息数量
    int commentArticleIdCount(int articleId);//获取指定文章的评论数量
    int articleLikeCount(int articleId);//获取指定文章的评论数量

    int addInformation(String userKey,String content,String type,String sendUserKey,int read,String articleId);//添加消息
    int delInformation(String userKey,String articleId);//删除消息
    int addComment(String articleId,String userKey,String userContent,String time);//添加评论
    int addReplyComment(String articleId,String sendUserKey,String userContent,String replyUser,String time);//添加评论
    int addArticle(String userKey,String imagesBase64,String content,String author,String from,String time,String type,String featured);//添加文章

    ArticleLike articleLike(String userKey,String articleId);//提取收藏中的信息
    List<ArticleLike> articleLikeList(String userKey);//获取该用户的所有收藏文章
    int addLikeArticle(String userKey,String articleId);//添加收藏
    int delLikeArticle(String userKey,String articleId);//取消收藏
    int delArticle(String id);//删除文章

    List<Comment> userAllComment(String userKey); //根据userKey查找该用户所有的评论

    Article articleComment(int id);//根据文章id，查询评论页面的作者信息和文章图片

    List<Article> userAllArticle(String userKey);//根据用户的key查询所有文章
    List<Integer> allArticleId();//查询所有文章的id
    int detect(String UName);

    List<Article> randFeatured();//查询随机十条精选文章
    List<Article> randTaken();//查询随机十条摘自文章
    List<Article> randOriginal();//查询随机十条原创文章

    List<Article> searchContent(String text);//查询所有相匹配的文章和作者

    List<Information> getUserInformation(String userKey,String type);//在用户查看页面时的功能,获取该类型未读消息
    List<Information> getUserInformation20(String userKey,String type);//在用户查看页面时的功能,获取该类型已读的最新20条消息
    List<Information> getSystemInformation();//获取所有的系统消息

    int readUpData(String userKey,String type);//将该用户所有该类型消息变为已读
    int featuredUpdate(String featured,int id);//首页推荐
    int readCount(String userKey,String type);//获取指定类型的消息未读数量

    List<ArticleLike100> articleLike100();
    List<Article> articleType10(String type);
    List<Article> newArticle();

    List<Article> featured();//首页推荐
}
