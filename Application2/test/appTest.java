package test;
import cgfm.CM;
import com.manager.failure.FailureManager;
import org.apache.log4j.Logger;
import org.junit.Test;
import pm.PM;
import src.com.team8.License.License;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by soap on 15/5/27.
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

    public void getGroup(String groupname , CM cm) {
        if (groupname.contains("Group") == true) {
            Iterator iterator = cm.keyList();
            System.out.println();
            List<String> groupMember = new ArrayList<String>();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                String value = cm.SearchKey(key);
                if (value.equals(groupname)) {
                    groupMember.add(key);
                } else {
                    continue;
                }
            }
            //组名查找成功
            if (groupMember.size() != 0) {
                System.out.println(groupname + " contains:");
                Iterator member = groupMember.iterator();
                while (member.hasNext()) {
                    System.out.println(member.next().toString());
                }
                PM.addItem("Successful search", 1);
                PM.addItem("Message responsed", 1);
                logger.info("Successful search\n");
            }
            //组名查找失败
            else {
                System.out.println(groupname + " doesn't exists!");
                logger.info("failed search\n");
                PM.addItem("Message responsed", 1);
            }
        }
    }

    public void getStudent(String studentName,CM cm) {
        String res = cm.SearchKey(studentName);
        // 查询组员成功
        if (res != null) {
            System.out.println(studentName + " is in " + res
                    + ".");
            PM.addItem("Successful search", 1);
            PM.addItem("Message responsed", 1);
            logger.info("Successful search\n");
        }

        // 查询组员失败
        else {
            System.out.println(studentName
                    + " is not in the student list.");
            logger.info("failed search\n");
            PM.addItem("Message responsed", 1);
        }
    }
    @Test
    public void QueryTest() throws IOException {
        String oldurl = appTest.class.getClassLoader().getResource("")
                .getPath();
        String url = oldurl.replace("bin/", "log/");
        System.setProperty("LOG_DIR", url);

        // CM读取配置信息
        System.out.println("Application running...");
        logger.info("Application running...\n");
        System.out.println("Reading configuration...");

        CM cm = new CM();
        // 配置文件需保存在项目的根目录下
        cm.ProcessFile("./config.txt");

        // CM读取学生分组
        System.out.println("Reading student_group_file...");
        cm.ProcessFile("./studentlist.txt");

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

        // License
        String li = cm.SearchKey("License");
        License license = null;
        if (li != null)
            license = new License(Integer.parseInt(li));
        else {
            System.out.println("License is not included in config file.");
            logger.warn("License is not included in config file.");
            FailureManager.logError("License is not included in config file.");
        }

        // FM
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
        getGroup("Group1", cm);
        getGroup("Group3",cm);
        getGroup("Group0",cm);
        getGroup("Group100",cm);
        getGroup("xxxx",cm);
        getGroup("",cm);


        getStudent("尹巧", cm);
        getStudent("方程",cm);
        getStudent("李四",cm);
        getStudent("锟斤拷锟斤拷锟斤拷",cm);
        getStudent("",cm);

    }
}
