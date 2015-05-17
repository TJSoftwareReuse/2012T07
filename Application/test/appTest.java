package test;
import application.Application;
import cgfm.CM;
import com.manager.failure.FailureManager;
import org.apache.log4j.Logger;
import pm.PM;
import src.com.team8.License.License;

import java.io.IOException;

import org.junit.Test;

/**
 * Created by soap on 15/5/17.
 */
public class appTest {
    CM cm = new CM();
    public static final Logger logger = Logger.getLogger("stdout");
    License license = null;
    @Test
    public void URLtest() {
        String oldurl = appTest.class.getClassLoader().getResource("")
                .getPath();
        String url = oldurl.replace("bin/", "log/");
        System.setProperty("LOG_DIR", url);
        System.out.println("url: " + url);
        System.out.println("oldurl: "+ oldurl);
    }

    @Test
    public  void CMtest() throws IOException {

        //配置文件需保存在项目的根目录下
        cm.ProcessFile("./config.txt");

        // CM读取学生分组
        System.out.println("Reading student_group_file...");
        cm.ProcessFile("./studentlist.txt");
        System.out.println(cm.getSize());
        cm.list();
    }

    @Test
    public  void PMtest() throws IOException {
        CMtest();
        // PM开启性能统计
        PM.start();
        String outpath = cm.SearchKey("PMPath");
        if (outpath != null)
            PM.setPath(cm.SearchKey("PMPath"));
        else {
            System.out
                    .println("PM output path is not included in config file.");
            logger.warn("PM output path is not included in config file.");
            FailureManager
                    .logError("PM output path is not included in config file.");
        }
        System.out.println("PMPath: " + outpath);
    }

    @Test
    public void lisenceTest() throws IOException {
        CMtest();
        String li = cm.SearchKey("License");

        if (li != null)
            license = new License(Integer.parseInt(li));
        else {
            System.out
                    .println("License is not included in config file.");
            logger.warn("License is not included in config file.");
            FailureManager
                    .logError("License is not included in config file.");
        }

        System.out.println(license);

    }

    @Test
    public void FMtest() throws IOException {
        CMtest();
        //FM
        String FMoutpath = cm.SearchKey("FMPath");
        if (FMoutpath != "")
            FailureManager.resetOutputFile(FMoutpath);
        else {
            System.out
                    .println("FM output path is not included in config file.");
            logger.warn("FM output path is not included in config file.");
            FailureManager
                    .logError("FM output path is not included in config file.");
        }

        System.out.println(FMoutpath);
    }

}
