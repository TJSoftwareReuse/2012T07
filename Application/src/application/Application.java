package application;

import org.apache.log4j.Logger;

<<<<<<< HEAD
import java.io.IOException;
import java.util.Scanner;

import com.manager.failure.FailureManager;

import cgfm.CM;
import pm.PM;
import src.com.team8.License.License;

public class Application {
	public static final Logger logger = Logger.getLogger("stdout");

	public static void main(String[] args) throws IOException {
		String oldurl = Application.class.getClassLoader().getResource("")
				.getPath();
		String url = oldurl.replace("bin/", "log/");
		System.setProperty("LOG_DIR", url);

		// CM读取配置信息
		System.out.println("Application running...");
		logger.info("Application running...\n");
		System.out.println("Reading configuration...");

		CM cm = new CM();
		//配置文件需保存在项目的根目录下
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
			System.out
					.println("License is not included in config file.");
			logger.warn("License is not included in config file.");
			FailureManager
					.logError("License is not included in config file.");
		}
		
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

		// 主查询功能
		Scanner scan = new Scanner(System.in);

		while (true) {
			System.out.println("Student name to search('q' to quit):");
			String studentName = scan.nextLine();

			// quit是否退出
			if (studentName.equals("q"))
				break;
			else {
				System.out.println(Integer.parseInt(studentName));
				PM.addItem("Message received", 1);
				logger.info("search \"" + studentName +"\"" );

				// License是否可以提供服务
				if (!license.inLicense()) {
					
					System.out.println("Search service unabled.");
					logger.info("Search service unabled.");
					FailureManager.logWarn("Service denied");
					PM.addItem("Service denied", 1);
					PM.addItem("Message responsed", 1);
					
				} else {
					FailureManager.logInfo("Service permitted");
					PM.addItem("Service permitted", 1);

					String res = cm.SearchKey(studentName);
					// 查询成功
					if (res != null) {
						System.out.println(studentName + " is in " + res
								+ ".");
						PM.addItem("Successful search", 1);
						PM.addItem("Message responsed", 1);
						logger.info("Successful search\n");
					}

					// 查询失败
					else {
						System.out.println(studentName
								+ " is not in the student list.");
						logger.info("failed search\n");
						PM.addItem("Message responsed", 1);
					}
				}
			}
		}
		scan.close();
		logger.info("Application exits.\n");
		PM.stop();
	}
=======
public class Application {
	public static final Logger logger=Logger.getLogger("stdout");
	
	public static void main(String[] args) {
		String message="this is a message";
		String url=Application.class.getClassLoader().getResource("").getPath();
		System.out.println(url);
		System.setProperty("LOG_DIR", url);
		logger.debug(message);
		logger.info(message);
		logger.warn(message);
		logger.error(message);
		logger.fatal(message);
	}

>>>>>>> d308bf1f0794cf353959be44323546137e0518ec
}
