package com.aboutlive.aboutlive.Impl;

import com.aboutlive.aboutlive.Entity.FocusAndFan;
import com.aboutlive.aboutlive.Entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper//表示mybatis的XML对应的接口
public interface UserImpl {
    User login(String UName, String UPwd);
    int delUser(String userKey);//删除账号

    int register(String userKey ,String name, String UName, String UPwd);//账号注册
    int detect(String UName);

    int upDate(@Param("userDate") User user); //修改

    User selectUser(String userKey);//查询用户的所有信息，根据key

    int myFan(String userKey);//关注数量
    int myFocus(String userKey);//粉丝数量

    User selectUserExperience(String userKey);//查询用户经验值

    List<User> selectFan(String userKey);//查询我的粉丝
    List<User> selectFocus(String userKey);//查询我的关注

    int addFocus(String othersUserKey,String myUserKey);//添加关注
    int delFocus(String othersUserKey,String myUserKey);//取消关注

    int selectFocusTrue(String myUserKey,String othersUserKey);//查询我是否关注了人家

    List<User> searchContent(String text);//通过搜索name查询相关用户

    List<User> randTenUser();//随机查询十条用户数据

    List<User> rankUser100();//查询经验排行前100的用户

    User fingerprintLogin(String fingerprintId);//指纹登录

    List<FocusAndFan> mutualRelationFocus(String userKey);//查询我的关注Key
    int mutualRelation(String userKey,String userKey1);//查询是否互关，1互关，0没有
}
