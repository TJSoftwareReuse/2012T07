package test;

import com.eva.me.cm.*;

import edu.tongji.FaultManagement;

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
    public static final Logger logger = Logger.getLogger("stdout");
    public static final PM pm = new PM();
    // 定义告警常量
    public static final int pmPathConfig = 1;
    public static final int licenseConfig = 2;
    public static final int fmPathConfig = 3;
    public static final int serviceDenied = 4;
    public static final int servicePermitted = 5;

    public static int fmInfo = 0;
    @SuppressWarnings("unused")
	@Test
    public void URLTest() {
        String oldurl = appTest.class.getClassLoader().getResource("")
                .getPath();
        String url = oldurl.replace("bin/", "/");
        System.setProperty("LOG_DIR", url);
        FaultManagement fm = FaultManagement.getInstance();

        System.out.println("Reading configuration...");
        System.out.println("url: " + url);
        System.out.println("oldurl: "+ oldurl);
    }
    @Test
    public void PMTest() throws IOException {
        FaultManagement fm = FaultManagement.getInstance();

        pm.start();
        String outpath = ConfigUtil.getInstance().getProperty("PMPath");
        if (outpath != null)
            pm.setPath(ConfigUtil.getInstance().getProperty("PMPath"));
        else {
            System.out
                    .println("PM output path is not included in config file.");
            logger.warn("PM output path is not included in config file.");
            if (pmPathConfig != fmInfo) {
                fmInfo = pmPathConfig;
                fm.generateWarningMessage("PM output path is not included in config file.");
            }
        }
        System.out.print(outpath);
    }

    @SuppressWarnings("unused")
	@Test
    public void LicenseTest() throws IOException {
        FaultManagement fm = FaultManagement.getInstance();
        String li = ConfigUtil.getInstance().getProperty("License");
        License license = null;
        if (li != null)
            license = new License(Integer.parseInt(li));
        else {
            System.out.println("License is not included in config file.");
            logger.warn("License is not included in config file.");
            if (licenseConfig != fmInfo) {
                fmInfo = licenseConfig;
                fm.generateWarningMessage("License is not included in config file.");
            }
        }
    }

    @Test
    public void FMTest() throws IOException {
        FaultManagement fm = FaultManagement.getInstance();
        String FMoutpath = ConfigUtil.getInstance().getProperty("FMPath");
        if (FMoutpath != "")
            fm.setLogDirPath(FMoutpath);
        else {
            System.out
                    .println("FM output path is not included in config file.");
            logger.warn("FM output path is not included in config file.");
            if (fmPathConfig != fmInfo) {
                fmInfo = fmPathConfig;
                fm.generateWarningMessage("FM output path is not included in config file.");
            }
        }
    }

    @SuppressWarnings("rawtypes")
	public void getGroup( String groupName) {
        Iterator iterator = ConfigUtil.getInstance().showConfigFileContent().keySet().iterator();
        System.out.println();
        List<String> groupMember = new ArrayList<String>();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            String value = ConfigUtil.getInstance().getProperty(key);
            if (value.equals(groupName)) {
                groupMember.add(key);
            } else {
                continue;
            }
        }
        // 组名查找成功
        if (groupMember.size() != 0) {
            System.out.println(groupName + " contains:");
            Iterator member = groupMember.iterator();
            while (member.hasNext()) {
                System.out.println(member.next().toString());
            }
            pm.addItem("Successful search", 1);
            pm.addItem("Message responsed", 1);
            logger.info("Successful search\n");
        }
        // 组名查找失败
        else {
            System.out.println(groupName + " doesn't exists!");
            logger.info("failed search\n");
            pm.addItem("Failed search", 1);
            pm.addItem("Message responsed", 1);
        }
    }


    public void getStudent(String studentName) {
        String res = ConfigUtil.getInstance().getProperty(studentName);
        // 查询组员成功
        if (res != null) {
            System.out.println(studentName + " is in " + res + ".");
            pm.addItem("Successful search", 1);
            pm.addItem("Message responsed", 1);
            logger.info("Successful search\n");
        }

        // 查询组员失败
        else {
            System.out.println(studentName
                    + " is not in the student list.");
            logger.info("failed search\n");
            pm.addItem("Failed search\n",1);
            pm.addItem("Message responsed", 1);
        }
    }
    @SuppressWarnings("unused")
	@Test
    public void FinalTest() throws IOException {
        String oldurl = appTest.class.getClassLoader().getResource("")
                .getPath();
        String url = oldurl.replace("bin/", "/");
        System.setProperty("LOG_DIR", url);
        FaultManagement fm = FaultManagement.getInstance();

        // CM读取配置信息
        System.out.println("Application running...");
        logger.info("Application running...\n");
        System.out.println("Reading configuration...");


        // PM开启性能统计
        pm.start();
        String outpath = ConfigUtil.getInstance().getProperty("PMPath");
        if (outpath != null)
            pm.setPath(ConfigUtil.getInstance().getProperty("PMPath"));
        else {
            System.out
                    .println("PM output path is not included in config file.");
            logger.warn("PM output path is not included in config file.");
            if (pmPathConfig != fmInfo) {
                fmInfo = pmPathConfig;
                fm.generateWarningMessage("PM output path is not included in config file.");
            }
        }

        // License
        String li =ConfigUtil.getInstance().getProperty("License");
        License license = null;
        if (li != null)
            license = new License(Integer.parseInt(li));
        else {
            System.out.println("License is not included in config file.");
            logger.warn("License is not included in config file.");
            if (licenseConfig != fmInfo) {
                fmInfo = licenseConfig;
                fm.generateWarningMessage("License is not included in config file.");
            }
        }

        // FM
        String FMoutpath = ConfigUtil.getInstance().getProperty("FMPath");
        if (FMoutpath != "")
            fm.setLogDirPath(FMoutpath);
        else {
            System.out
                    .println("FM output path is not included in config file.");
            logger.warn("FM output path is not included in config file.");
            if (fmPathConfig != fmInfo) {
                fmInfo = fmPathConfig;
                fm.generateWarningMessage("FM output path is not included in config file.");
            }
        }

        getGroup("Group2");
        getGroup("Group7");
        getGroup("sdf");
        getGroup("");
        // 主查询功能
        getStudent("赵鹏");
        getStudent("方程");
        getStudent("");
    }

}
