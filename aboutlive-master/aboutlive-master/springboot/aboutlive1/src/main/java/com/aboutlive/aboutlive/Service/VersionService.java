package com.aboutlive.aboutlive.Service;

import com.aboutlive.aboutlive.Entity.Version;
import com.aboutlive.aboutlive.Impl.VersionImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service   //表示服务层
public class VersionService implements VersionImpl {

    @Resource    //依赖的注入
    VersionImpl version;

    @Override
    public Version VersionInformation() {
        return version.VersionInformation();
    }

    @Override
    public int updateVersion(String version) {
        return this.version.updateVersion(version);
    }
}
