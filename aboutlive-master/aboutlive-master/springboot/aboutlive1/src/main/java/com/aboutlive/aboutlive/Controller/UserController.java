package com.aboutlive.aboutlive.Controller;

import com.aboutlive.aboutlive.Entity.ResultEntity;
import com.aboutlive.aboutlive.Entity.User;
import com.aboutlive.aboutlive.Service.UserService;
import com.aboutlive.aboutlive.Util.JSONTool;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin //解决跨域问题
@RestController //控制器注解
@RequestMapping("/User/")  //请求路径
public class UserController {
    @Resource //依赖注入  （需要用到别的方法和类）
    UserService userService;
    ResultEntity resultEntity = null;

    @GetMapping("login")
    private String doLogin(@RequestParam("UName") String UName, @RequestParam("UPwd") String UPwd) {
        resultEntity = new ResultEntity();
//        resultEntity.setMsg(tokenTool.getToken(UName));
        try {
            User user = userService.login(UName, UPwd);
            if (user == null) {
                resultEntity.setCode(1);
                resultEntity.setMsg("账号不存在");
            } else {
                resultEntity.setCode(0);
                List<String> list = new ArrayList<>();
                list.add(user.getUserKey());//0
                list.add(user.getName());//1
                list.add(user.getHeadImage());//2
                list.add(user.getEmail());//3
                list.add(user.getSex() + "");//4
                list.add(user.getExperience() + "");//5
                list.add(user.getEmail());//6
                list.add(user.getBorn());//7
                System.out.println("user.getState(): " + user.getState());
                list.add(user.getState() + "");//8
                resultEntity.setMsg(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setCode(999);
            resultEntity.setMsg("系统错误，请联系管理员");
        }
        return JSONTool.getJsonTool().toJson(resultEntity);
    }

    @GetMapping("delUser")
    private String delUser(@RequestParam("userKey") String userKey) {
        resultEntity = new ResultEntity();
//        resultEntity.setMsg(tokenTool.getToken(UName));
        try {
            if (userService.delUser(userKey) > 0) {
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

    @PostMapping("register")
    private String doRegister(@RequestParam("name") String name, @RequestParam("UName") String UName, @RequestParam("UPwd") String UPwd) {
        resultEntity = new ResultEntity();
        try {
            long time = System.currentTimeMillis();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss");
            String day = dateFormat.format(time);
            String userKey = day.substring(0, day.length() - 3) + (int) Math.random() * 100 + day.substring(day.length() - 3);
            int len = Math.min(UName.length(), UPwd.length());
            for (int i = 0; i < len; i++) {
                userKey += UName.charAt(i) + "" + UPwd.charAt(i);
            }
            userKey += UName.substring(len) + UPwd.substring(len);
            if (userService.register(userKey, name, UName, UPwd) > 0) {
                resultEntity.setCode(0);
                resultEntity.setMsg("注册成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setCode(999);
            resultEntity.setMsg("系统错误，请联系管理员");
        }
        return JSONTool.getJsonTool().toJson(resultEntity);
    }

    @GetMapping("detect")
    private String detect(@RequestParam("UName") String UName) {
        resultEntity = new ResultEntity();
        try {
            if (userService.detect(UName) > 0) {
                resultEntity.setCode(1);
                resultEntity.setMsg("账号不可用");
            } else {
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
     * 用户更新
     *
     * @param headImage
     * @param name
     * @param sex
     * @param email
     * @param born
     * @param userKey
     * @return
     */
    @PutMapping("update")
    private String doUpdate(@RequestParam("headImage") String headImage, @RequestParam("name") String name,
                            @RequestParam("sex") String sex, @RequestParam("email") String email,
                            @RequestParam("born") String born, @RequestParam("userKey") String userKey) {
        resultEntity = new ResultEntity();
        try {
            User user = new User();
            user.setHeadImage(headImage);
            user.setName(name);
            user.setSex(Integer.parseInt(sex));
            user.setEmail(email);
            user.setBorn(born);
            user.setUserKey(userKey);
            if (userService.upDate(user) > 0) {
                resultEntity.setCode(0);
                List<String> list = new ArrayList<>();
                list.add(headImage);//0
                list.add(name);//1
                list.add(sex);//2
                list.add(email);//3
                list.add(born);//4
                resultEntity.setMsg(list);
            } else {
                resultEntity.setCode(1);
                resultEntity.setMsg("修改失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setCode(999);
            resultEntity.setMsg("系统错误，请联系管理员");
        }
        return JSONTool.getJsonTool().toJson(resultEntity);
    }

    @GetMapping("myFiery")
    private String myFiery(@RequestParam("userKey") String userKey) {
        resultEntity = new ResultEntity();
//        resultEntity.setMsg(tokenTool.getToken(UName));
        try {
            resultEntity.setCode(0);
            resultEntity.setMsg("Ac");
            List<Integer> list = new ArrayList<>();
            list.add(userService.myFan(userKey));
            list.add(userService.myFocus(userKey));
            User user = userService.selectUser(userKey);
            if (user != null) {
                if (user.getExperience() < 1000) {
                    list.add(1);
                } else if (user.getExperience() < 2000) {
                    list.add(2);
                } else if (user.getExperience() < 3000) {
                    list.add(3);
                } else if (user.getExperience() < 4000) {
                    list.add(4);
                } else if (user.getExperience() < 5000) {
                    list.add(5);
                } else {
                    list.add(6);
                }
            }
            resultEntity.setData(list);
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setCode(999);
            resultEntity.setMsg("系统错误，请联系管理员");
        }
        return JSONTool.getJsonTool().toJson(resultEntity);
    }

    /**
     * 粉丝list
     *
     * @param myUserKey
     * @return
     */
    @GetMapping("fanList")
    private String fanList(@RequestParam("myUserKey") String myUserKey) {
        resultEntity = new ResultEntity();
//        resultEntity.setMsg(tokenTool.getToken(UName));
        try {
            resultEntity.setCode(0);
            resultEntity.setMsg("Ac");
            List<User> list = userService.selectFan(myUserKey);
            List<List<String>> newList = new ArrayList<>();
            for (User u : list) {
                List<String> l = new ArrayList<>();
                l.add(u.getHeadImage());
                l.add(u.getName());
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
                if (userService.selectFocusTrue(myUserKey, u.getUserKey()) > 0) {
                    l.add("true");
                } else {
                    l.add("false");
                }
                l.add(u.getUserKey());
                newList.add(l);
            }
            resultEntity.setData(newList);
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setCode(999);
            resultEntity.setMsg("系统错误，请联系管理员");
        }
        return JSONTool.getJsonTool().toJson(resultEntity);
    }

    /**
     * 关注集合
     *
     * @param userKey
     * @return
     */
    @GetMapping("focusList")
    private String focusList(@RequestParam("userKey") String userKey) {
        resultEntity = new ResultEntity();
//        resultEntity.setMsg(tokenTool.getToken(UName));
        try {
            resultEntity.setCode(0);
            resultEntity.setMsg("Ac");
            List<User> list = userService.selectFocus(userKey);
            List<List<String>> newList = new ArrayList<>();
            for (User u : list) {
                List<String> l = new ArrayList<>();
                l.add(u.getHeadImage());
                l.add(u.getName());
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
                newList.add(l);
            }
            resultEntity.setData(newList);
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setCode(999);
            resultEntity.setMsg("系统错误，请联系管理员");
        }
        return JSONTool.getJsonTool().toJson(resultEntity);
    }

    /**
     * 查询选定userKey的用户粉丝数量、关注数量、等级、头像、名字、是否关注等
     *
     * @param othersUserKey
     * @return list
     */
    @GetMapping("outhorsFiery")
    private String outhorsFiery(@RequestParam("othersUserKey") String othersUserKey, @RequestParam("myUserKey") String myUserKey) {
        resultEntity = new ResultEntity();
//        resultEntity.setMsg(tokenTool.getToken(UName));
        try {
            resultEntity.setCode(0);
            resultEntity.setMsg("Ac");
            List<String> list = new ArrayList<>();
            list.add(userService.myFan(othersUserKey) + "");
            list.add(userService.myFocus(othersUserKey) + "");
            User user = userService.selectUser(othersUserKey);
            if (user != null) {
                if (user.getExperience() < 1000) {
                    list.add("1");
                } else if (user.getExperience() < 2000) {
                    list.add("2");
                } else if (user.getExperience() < 3000) {
                    list.add("3");
                } else if (user.getExperience() < 4000) {
                    list.add("4");
                } else if (user.getExperience() < 5000) {
                    list.add("5");
                } else {
                    list.add("6");
                }
            }
            list.add(user.getHeadImage());
            list.add(user.getName());
            if (userService.selectFocusTrue(myUserKey, othersUserKey) > 0) {
                list.add("true");
            } else {
                list.add("false");
            }
            resultEntity.setData(list);
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setCode(999);
            resultEntity.setMsg("系统错误，请联系管理员");
        }
        return JSONTool.getJsonTool().toJson(resultEntity);
    }

    /**
     * 关注用户
     *
     * @param othersUserKey
     * @param myUserKey
     * @return json
     */
    @GetMapping("addFocus")
    private String addComment(@RequestParam("othersUserKey") String othersUserKey, @RequestParam("myUserKey") String myUserKey) {
        resultEntity = new ResultEntity();
        try {
            if (userService.addFocus(othersUserKey, myUserKey) > 0) {
                resultEntity.setCode(0);
                resultEntity.setMsg("关注成功！");
            } else {
                resultEntity.setCode(1);
                resultEntity.setMsg("关注失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setCode(999);
            resultEntity.setMsg("系统错误，请联系管理员");
        }
        return JSONTool.getJsonTool().toJson(resultEntity);
    }


    /**
     * 取消关注
     *
     * @param othersUserKey
     * @param myUserKey
     * @return
     */
    @GetMapping("delFocus")
    private String delFocus(@RequestParam("othersUserKey") String othersUserKey, @RequestParam("myUserKey") String myUserKey) {
        resultEntity = new ResultEntity();
        try {
            if (userService.delFocus(othersUserKey, myUserKey) > 0) {
                resultEntity.setCode(0);
                resultEntity.setMsg("取关成功！");
            } else {
                resultEntity.setCode(1);
                resultEntity.setMsg("取关失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setCode(999);
            resultEntity.setMsg("系统错误，请联系管理员");
        }
        return JSONTool.getJsonTool().toJson(resultEntity);
    }

    /**
     * 通过搜索name查询相关用户
     *
     * @param text
     * @return
     */
    @GetMapping("searchContent")
    private String searchContent(@RequestParam("text") String text, @RequestParam("userKey") String userKey) {
        resultEntity = new ResultEntity();
        try {
            List<User> list = userService.searchContent(text);
            List<List<String>> newList = new ArrayList<>();
            for (User u : list) {
                List<String> l = new ArrayList<>();
                l.add(u.getHeadImage());
                l.add(u.getName());
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
                if (userService.selectFocusTrue(userKey, u.getUserKey()) > 0) {
                    l.add("true");
                } else {
                    l.add("false");
                }
                l.add(u.getUserKey());
                newList.add(l);
            }
            resultEntity.setCode(0);
            resultEntity.setData(newList);
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setCode(999);
            resultEntity.setMsg("系统错误，请联系管理员");
        }
        return JSONTool.getJsonTool().toJson(resultEntity);
    }

    /**
     * 获取用户的经验值
     *
     * @param userKey
     * @return int
     */
    @GetMapping("selectUserExperience")
    private String selectUserExperience(@RequestParam("userKey") String userKey) {
        resultEntity = new ResultEntity();
        try {
            User user = userService.selectUserExperience(userKey);
            List<String> l = new ArrayList<>();
            int exp = (int) user.getExperience();
            if (exp < 1000) {
                l.add(exp / 10 + "");
                l.add(1000 - exp + "");
            } else if (exp < 2000) {
                l.add(exp / 10 + "");
                l.add(2000 - exp + "");
            } else if (exp < 3000) {
                l.add(exp / 10 + "");
                l.add(3000 - exp + "");
            } else if (exp < 4000) {
                l.add(exp / 10 + "");
                l.add(4000 - exp + "");
            } else if (exp < 5000) {
                l.add(exp / 10 + "");
                l.add(5000 - exp + "");
            } else {
                l.add("100");
                l.add("0");
            }
            resultEntity.setData(0);
            resultEntity.setData(l);
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setCode(999);
            resultEntity.setMsg("系统错误，请联系管理员");
        }
        return JSONTool.getJsonTool().toJson(resultEntity);
    }

    /**
     * 随机查询十条用户
     *
     * @return
     */
    @GetMapping("randTenUser")
    private String randTenUser(@RequestParam("userKey") String userKey) {
        resultEntity = new ResultEntity();
        try {
            List<User> users = userService.randTenUser();
            List<List<String>> list = new ArrayList<>();
            for (User u : users) {
                List<String> l = new ArrayList<>();
                l.add(u.getHeadImage());
                l.add(u.getName());
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

                if (userService.selectFocusTrue(userKey, u.getUserKey()) > 0) {
                    l.add("true");
                } else {
                    l.add("false");
                }
                list.add(l);
            }
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
     * 前100名经验排行
     *
     * @return
     */
    @GetMapping("rankUser100")
    private String rankUser100() {
        resultEntity = new ResultEntity();
        try {
            List<User> users = userService.rankUser100();
            List<List<String>> list = new ArrayList<>();
            for (User u : users) {
                List<String> l = new ArrayList<>();
                l.add(u.getUserKey());
                l.add(u.getName());
                l.add(u.getHeadImage());
                l.add((int) u.getExperience() + "");
                l.add(userService.myFocus(u.getUserKey()) + "");
                list.add(l);
            }
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
     * 指纹登录
     *
     * @param fingerprintLogin
     * @return
     */
    @GetMapping("fingerprintLogin")
    private String fingerprintLogin(@RequestParam("fingerprintLogin") String fingerprintLogin) {
        resultEntity = new ResultEntity();
        try {
            User user = userService.fingerprintLogin(fingerprintLogin);
            if (user != null) {
                resultEntity.setCode(0);
                resultEntity.setData(user);
            } else {
                resultEntity.setCode(1);
//                resultEntity.setData(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setCode(999);
            resultEntity.setMsg("系统错误，请联系管理员");
        }
        return JSONTool.getJsonTool().toJson(resultEntity);
    }

    /**
     * 根据key查找用户数据
     *
     * @param userKey
     * @return
     */
    @GetMapping("selectUserList")
    private String selectUserList(@RequestParam("userKey") List<String> userKey) {
        resultEntity = new ResultEntity();
        try {
            List<User> list = new ArrayList<>();
            for (int i = 0; i < userKey.size(); i++) {
                list.add(userService.selectUser(userKey.get(i)));
            }
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
     * 根据key查找用户数据
     *
     * @param userKey
     * @return
     */
    @GetMapping("selectUser")
    private String selectUser(@RequestParam("userKey") String userKey) {
        resultEntity = new ResultEntity();
        try {
            resultEntity.setCode(0);
            resultEntity.setData(userService.selectUser(userKey));
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setCode(999);
            resultEntity.setMsg("系统错误，请联系管理员");
        }
        return JSONTool.getJsonTool().toJson(resultEntity);
    }


    // restful接口规范 : getMapping：获取 postMapping：增加 deleteMapping：删除 putMapping：更新
}
