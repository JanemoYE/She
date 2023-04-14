package com.aboutlive.aboutlive.Util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JSONTool {
    private static JSONTool jsonTool = null;
    private JSONTool(){

    }
    //JsonTool Java单利模式实现
    public String toJson(Object object){ //Object转Json
        GsonBuilder gsonBuilder = new GsonBuilder();//新建一个GSon转化对象
        gsonBuilder.setPrettyPrinting();
        Gson gson =gsonBuilder.create();
        return gson.toJson(object);
    }
    public static JSONTool getJsonTool (){ //返回单一对象
        if (jsonTool == null){
            jsonTool = new JSONTool();
        }
        return jsonTool;
    }
}

