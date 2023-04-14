package com.aboutlive.aboutlive.Impl;

import com.aboutlive.aboutlive.Entity.Article;
import com.aboutlive.aboutlive.Entity.Comment;
import com.aboutlive.aboutlive.Entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper//表示mybatis的XML对应的接口
public interface UserSystemImpl {
    List<User> AllUser(int Start, int Page);

    List<Article> AllArticle(int Start, int Page);
    List<Article> articleAudit(int Start, int Page);

    List<Comment> AllComment(int Start, int Page);

    int getCommentCount();//获取评论的总数
    int getArticleCount();//获取文章的总数
    int getUserCount();//获取用户的总数
    int getInteractive();//获取互动的总数

    User userInformartion(int id);

    int userUpDate(String UName ,String UPwd, String email,int sex,String born,int experience);

    int delComment(int id);//删除评论

    int stateTrue(int id);//账号state，解封
    int stateFalse(int id);//账号state，/封号
    int articleState(int state,int id);//账号state，/封号

    int mute(String userKey,String muteStart,String muteEnd);//用户禁言
    int countMute(String userKey);//用户禁言次数
}
