package com.aboutlive.aboutlive.Service;

import com.aboutlive.aboutlive.Entity.FocusAndFan;
import com.aboutlive.aboutlive.Entity.User;
import com.aboutlive.aboutlive.Impl.UserImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service   //表示服务层
public class UserService implements UserImpl {

    @Resource    //依赖的注入
    UserImpl userimpl;
    /**
     * 用户登录
     * @param UName
     * @param UPwd
     * @return 是否登录成功
     */
    @Override
    public User login(String UName, String UPwd) {
        return userimpl.login(UName,UPwd);
    }

    @Override
    public int delUser(String userKey) {
        return userimpl.delUser(userKey);
    }

    @Override
    public int register(String userKey ,String name, String UName, String UPwd) {
        return userimpl.register(userKey,name,  UName,  UPwd);
    }

    @Override
    public int detect(String UName) {
        return userimpl.detect(UName);
    }

    /**
     * 修改用户个人信息
     * @param user
     * @return int
     */
    @Override
    public int upDate(User user) {
        return userimpl.upDate(user);
    }

    @Override
    public User selectUser(String userKey) {
        return userimpl.selectUser(userKey);
    }

    @Override
    public int myFan(String userKey) {
        return userimpl.myFan(userKey);
    }

    @Override
    public int myFocus(String userKey) {
        return userimpl.myFocus(userKey);
    }

    @Override
    public User selectUserExperience(String userKey) {
        return userimpl.selectUserExperience(userKey);
    }

    @Override
    public List<User> selectFan(String userKey) {
        return userimpl.selectFan(userKey);
    }

    @Override
    public List<User> selectFocus(String userKey) {
        return userimpl.selectFocus(userKey);
    }

    @Override
    public int addFocus(String othersUserKey, String myUserKey) {
        return userimpl.addFocus(othersUserKey,myUserKey);
    }

    @Override
    public int delFocus(String othersUserKey, String myUserKey) {
        return userimpl.delFocus(othersUserKey,myUserKey);
    }

    @Override
    public int selectFocusTrue(String myUserKey, String othersUserKey) {
        return userimpl.selectFocusTrue(myUserKey,othersUserKey);
    }

    @Override
    public List<User> searchContent(String text) {
        return userimpl.searchContent(text);
    }

    @Override
    public List<User> randTenUser() {
        return userimpl.randTenUser();
    }

    @Override
    public List<User> rankUser100() {
        return userimpl.rankUser100();
    }

    @Override
    public User fingerprintLogin(String fingerprintId) {
        return userimpl.fingerprintLogin(fingerprintId);
    }

    @Override
    public List<FocusAndFan> mutualRelationFocus(String userKey) {
        return userimpl.mutualRelationFocus(userKey);
    }

    @Override
    public int mutualRelation(String userKey, String userKey1) {
        return userimpl.mutualRelation(userKey, userKey1);
    }


}
