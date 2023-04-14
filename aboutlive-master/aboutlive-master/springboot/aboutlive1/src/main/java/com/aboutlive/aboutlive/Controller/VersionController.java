package com.aboutlive.aboutlive.Controller;

import com.aboutlive.aboutlive.Entity.ResultEntity;
import com.aboutlive.aboutlive.Entity.User;
import com.aboutlive.aboutlive.Entity.Version;
import com.aboutlive.aboutlive.Service.UserSystemService;
import com.aboutlive.aboutlive.Service.VersionService;
import com.aboutlive.aboutlive.Util.JSONTool;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.*;
import java.util.List;

@CrossOrigin //解决跨域问题
@RestController //控制器注解
@RequestMapping("/Version/")  //请求路径
public class VersionController {
    @Resource //依赖注入  （需要用到别的方法和类）
    VersionService versionService;
    ResultEntity resultEntity = null;

    /**
     * 查询当前版本号
     * @return
     */
    @GetMapping("VersionInformation")
    private String VersionInformation() {
        resultEntity = new ResultEntity();
        try {
            Version ver=versionService.VersionInformation();
            resultEntity.setCode(0);
            resultEntity.setMsg("查询成功");
            resultEntity.setData(ver.getVersion());
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setCode(999);
            resultEntity.setMsg("系统错误，请联系管理员");
        }
        return JSONTool.getJsonTool().toJson(resultEntity);
    }

    /**
     * 修改版本号
     * @return
     */
    @GetMapping("updateVersion")
    private String VersionInformation(@RequestParam("version") String version) {
        resultEntity = new ResultEntity();
        try {
            if (versionService.updateVersion(version)>0){
                resultEntity.setCode(0);
                resultEntity.setMsg("修改成功");
            }else {
                resultEntity.setCode(1);
                resultEntity.setMsg("修改失败");
            }

//            resultEntity.setData(ver.getVersion());
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setCode(999);
            resultEntity.setMsg("系统错误，请联系管理员");
        }
        return JSONTool.getJsonTool().toJson(resultEntity);
    }

    /**
     * 关键词检索/添加/获取
     * @param keyWord
     * @return
     */
    @GetMapping("keyWords")
    private String keyWords(@RequestParam("id") int id,@RequestParam("keyWord") String keyWord) {
        resultEntity = new ResultEntity();
        try {
            /* 读入TXT文件 */
            String pathname = "C:\\Users\\sorryIbelieve\\Desktop\\keyWord.txt"; // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径
            File filename = new File(pathname); // 要读取以上路径的input。txt文件
            InputStreamReader reader = new InputStreamReader(
                    new FileInputStream(filename)); // 建立一个输入流对象reader
            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
            String line = "";
            line = br.readLine();
            if (id==0) {
                resultEntity.setCode(0);
                resultEntity.setData(line);
            }else if (id==1){
                /* 写入Txt文件 */
                File writename = new File("C:\\Users\\sorryIbelieve\\Desktop\\keyWord.txt"); // 相对路径，如果没有则要建立一个新的output。txt文件
                writename.createNewFile(); // 创建新文件
                BufferedWriter out = new BufferedWriter(new FileWriter(writename));
                out.write(line+keyWord); // \r\n即为换行
                out.flush(); // 把缓存区内容压入文件
                out.close(); // 最后记得关闭文件
                resultEntity.setCode(0);
                resultEntity.setMsg("修改成功");
            }else{
                String[] str=line.split("，");
                boolean f=true;
                for (String s:str) {
                    if (keyWord.indexOf(s)!=-1){
                        resultEntity.setCode(0);
                        f=false;
                        break;
                    }
                }
                resultEntity.setCode(0);
                resultEntity.setData(f);
            }


        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setCode(999);
            resultEntity.setMsg("系统错误，请联系管理员");
        }
        return JSONTool.getJsonTool().toJson(resultEntity);
    }
}
