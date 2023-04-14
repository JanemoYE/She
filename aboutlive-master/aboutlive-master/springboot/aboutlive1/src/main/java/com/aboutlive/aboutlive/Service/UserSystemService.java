package com.aboutlive.aboutlive.Service;

import com.aboutlive.aboutlive.Entity.Article;
import com.aboutlive.aboutlive.Entity.Comment;
import com.aboutlive.aboutlive.Entity.User;
import com.aboutlive.aboutlive.Impl.UserSystemImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service   //表示服务层
public class UserSystemService implements UserSystemImpl {

    @Resource    //依赖的注入
    UserSystemImpl userSystem;

    @Override
    public List<User> AllUser(int Start, int Page) {
        return userSystem.AllUser(Start,Page);
    }

    @Override
    public List<Article> AllArticle(int Start, int Page) {
        return userSystem.AllArticle(Start,Page);
    }

    @Override
    public List<Article> articleAudit(int Start, int Page) {
        return userSystem.articleAudit(Start, Page);
    }

    @Override
    public List<Comment> AllComment(int Start, int Page) {
        return userSystem.AllComment(Start,Page);
    }

    @Override
    public int getCommentCount() {
        return userSystem.getCommentCount();
    }

    @Override
    public int getArticleCount() {
        return userSystem.getArticleCount();
    }

    @Override
    public int getUserCount() {
        return userSystem.getUserCount();
    }

    @Override
    public int getInteractive() {
        return userSystem.getInteractive();
    }

    @Override
    public User userInformartion(int id) {
        return userSystem.userInformartion(id);
    }

    @Override
    public int userUpDate(String UName, String UPwd, String email, int sex, String born, int experience) {
        return userSystem.userUpDate(UName, UPwd, email, sex, born, experience);
    }

    @Override
    public int delComment(int id) {
        return userSystem.delComment(id);
    }

    @Override
    public int stateTrue(int id) {
        return userSystem.stateTrue(id);
    }

    @Override
    public int stateFalse(int id) {
        return userSystem.stateFalse(id);
    }

    @Override
    public int articleState(int state, int id) {
        return userSystem.articleState(state, id);
    }

    @Override
    public int mute(String userKey, String muteStart, String muteEnd) {
        return userSystem.mute(userKey, muteStart, muteEnd);
    }

    @Override
    public int countMute(String userKey) {
        return userSystem.countMute(userKey);
    }

}
