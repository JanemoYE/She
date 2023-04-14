package com.aboutlive.aboutlive.Service;

import com.aboutlive.aboutlive.Entity.*;
import com.aboutlive.aboutlive.Impl.ArticleImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service   //表示服务层
public class ArticleService implements ArticleImpl {
    @Resource    //依赖的注入
    ArticleImpl articleImpl;
    /**
     * 根据id获取文章的所有信息
     * @param id
     * @return Article
     */
    @Override
    public Article article(int id) {
        return articleImpl.article(id);
    }

    @Override
    public List<Comment> comment(int Start, int Page, int id) {
        return articleImpl.comment(Start,Page,id);
    }

    @Override
    public int delComment(String userKey, int id) {
        return articleImpl.delComment(userKey,id);
    }

    @Override
    public int getCommentCount() {
        return articleImpl.getCommentCount();
    }

    @Override
    public int getInformationCount(String userKey) {
        return articleImpl.getInformationCount(userKey);
    }

    @Override
    public int commentArticleIdCount(int articleId) {
        return articleImpl.commentArticleIdCount(articleId);
    }

    @Override
    public int articleLikeCount(int articleId) {
        return articleImpl.articleLikeCount(articleId);
    }

    @Override
    public int addInformation(String userKey, String content, String type, String sendUserKey, int read ,String articleId) {
        return articleImpl.addInformation(userKey, content, type, sendUserKey, read ,articleId);
    }

    @Override
    public int delInformation(String userKey, String articleId) {
        return articleImpl.delInformation(userKey,articleId);
    }

    @Override
    public int addComment(String articleId, String userKey, String userContent, String time) {
        return articleImpl.addComment(articleId,userKey,userContent,time);
    }

    @Override
    public int addReplyComment(String articleId, String sendUserKey, String userContent, String replyUser, String time) {
        return articleImpl.addReplyComment(articleId, sendUserKey, userContent, replyUser, time);
    }

    @Override
    public int addArticle(String userKey, String imagesBase64, String content, String author, String from, String time,String type,String featured) {
        return articleImpl.addArticle(userKey,imagesBase64,content,author,from,time,type,featured);
    }

    @Override
    public ArticleLike articleLike(String userKey, String articleId) {
        return articleImpl.articleLike(userKey,articleId);
    }

    @Override
    public List<ArticleLike> articleLikeList(String userKey) {
        return articleImpl.articleLikeList(userKey);
    }

    @Override
    public int addLikeArticle(String userKey, String articleId) {
        return articleImpl.addLikeArticle(userKey,articleId);
    }

    @Override
    public int delLikeArticle(String userKey, String articleId) {
        return articleImpl.delLikeArticle(userKey,articleId);
    }

    @Override
    public int delArticle(String id) {
        return articleImpl.delArticle(id);
    }

    @Override
    public List<Comment> userAllComment(String userKey) {
        return articleImpl.userAllComment(userKey);
    }

    @Override
    public Article articleComment(int id) {
        return articleImpl.articleComment(id);
    }

    @Override
    public List<Article> userAllArticle(String userKey) {
        return articleImpl.userAllArticle(userKey);
    }

    @Override
    public List<Integer> allArticleId() {
        return articleImpl.allArticleId();
    }

    @Override
    public int detect(String UName) {
        return articleImpl.detect(UName);
    }

    @Override
    public List<Article> randFeatured() {
        return articleImpl.randFeatured();
    }

    @Override
    public List<Article> randTaken() {
        return articleImpl.randTaken();
    }

    @Override
    public List<Article> randOriginal() {
        return articleImpl.randOriginal();
    }

    @Override
    public List<Article> searchContent(String text) {
        return articleImpl.searchContent(text);
    }

    @Override
    public List<Information> getUserInformation(String userKey,String type) {
        return articleImpl.getUserInformation(userKey,type);
    }

    @Override
    public List<Information> getUserInformation20(String userKey, String type) {
        return articleImpl.getUserInformation20(userKey,type);
    }

    @Override
    public List<Information> getSystemInformation() {
        return articleImpl.getSystemInformation();
    }

    @Override
    public int readUpData(String userKey,String type) {
        return articleImpl.readUpData(userKey,type);
    }

    @Override
    public int featuredUpdate(String featured, int id) {
        return articleImpl.featuredUpdate(featured, id);
    }


    @Override
    public int readCount(String userKey, String type) {
        return articleImpl.readCount(userKey,type);
    }

    @Override
    public List<ArticleLike100> articleLike100() {
        return articleImpl.articleLike100();
    }

    @Override
    public List<Article> articleType10(String type) {
        return articleImpl.articleType10(type);
    }

    @Override
    public List<Article> newArticle() {
        return articleImpl.newArticle();
    }

    @Override
    public List<Article> featured() {
        return articleImpl.featured();
    }

}
