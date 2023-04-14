package com.aboutlive.aboutlive;

import com.aboutlive.aboutlive.dbo.RrunApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AboutliveApplication {

    public static void main(String[] args) {
        RrunApplication run = new RrunApplication(AboutliveApplication.class,args);
    }

}
