package com.aboutlive.aboutlive.Impl;

import com.aboutlive.aboutlive.Entity.Version;
import org.apache.ibatis.annotations.Mapper;

@Mapper//表示mybatis的XML对应的接口
public interface VersionImpl {
    Version VersionInformation();//查询版本号

    int updateVersion(String version);//修改版本号
}
