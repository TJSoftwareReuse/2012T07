package application;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import com.manager.failure.FailureManager;

import cgfm.CM;
import pm.PM;
import src.com.team8.License.License;

//FM第二组，License第八组
public class Application {
	public static final Logger logger1 = Logger.getLogger("stdou");
	
	
	
	@SuppressWarnings("rawtypes")
	public static void main(String[] args) throws IOException {
		String oldurl = Application.class.getClassLoader().getResource("")
				.getPath();
		String url = oldurl.replace("bin/", "log/");
		System.setProperty("LOG_DIR", url);

		// CM读取配置信息
		System.out.println("Application running...");
		logger1.info("Application running...\n");
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
			logger1.warn("PM output path is not included in config file.");
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
			logger1.warn("License is not included in config file.");
			FailureManager.logError("License is not included in config file.");
		}

		// FM
//		

		// 主查询功能
		Scanner scan = new Scanner(System.in);

		while (true) {

			System.out.println("Welcome,please input");
			System.out.println("'q' to quit,'c' to change configuration,'s' to search student,'g' to search group");
			String order = scan.nextLine();

			// quit是否退出
			if (order.equals("q"))
				break;
			
			//更改配置
			else if(order.equals("c"))
			{
				System.out.println("Here are all the configuration you can change:");
				System.out.println("PMInterval");
				System.out.println("FMPath");
				System.out.println("PMPath");
				System.out.println("License");
				System.out.println("Please use the format like License:10");
				String changeItem=scan.nextLine();
				String key=null;
				String value=null;
				try {
					String[] temp=changeItem.split(":");
					key=temp[0];
					value=temp[1];
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				
				if(key.equals("FMPath")) 
					{
					try {
						cm.changeItem(key, value);
					} catch (Exception e) {
						FailureManager.resetOutputFile(cm.SearchKey("FMPath"));
					}
					System.out.println(key+"更新成功");
					logger1.info(key+"更新成功");
					}
				else if (key.equals("PMPath")) {
					cm.changeItem(key, value);
					PM.setPath(cm.SearchKey("PMPath"));
					System.out.println(key+"更新成功");
					logger1.info(key+"更新成功");
				}
				else if (key.equals("License")) {
					try {
						cm.changeItem(key, value);
					} catch (Exception e) {
						license=new License(Integer.parseInt(value));
					}
					System.out.println(key+"更新成功");
					logger1.info(key+"更新成功");
				}
				else if (key.equals("PMInterval")) {
					//缺时间间隔更新
					try {
						
					} catch (Exception e) {
						// TODO: handle exception
					}
					System.out.println(key+"更新成功");
					logger1.info(key+"更新成功");
				}
				else {
					try {
						
					} catch (Exception e) {
						// TODO: handle exception
					}
					System.out.println("更新项不存在");
					logger1.info("更新项不存在");
				}
			}
			
			//查询组名
			else if(order.equals("g"))
			{
				System.out.println("Please input the group name");
				String groupName=scan.nextLine();
				PM.addItem("Message received", 1);
				logger1.info("search \"" + groupName + "\"");

				// License是否可以提供服务
				if (!license.inLicense()) {

					System.out.println("Search service unabled.");
					logger1.info("Search service unabled.");
					FailureManager.logWarn("Service denied");
					PM.addItem("Service denied", 1);
					PM.addItem("Message responsed", 1);
					} 
				else {
					FailureManager.logInfo("Service permitted");
					PM.addItem("Service permitted", 1);
			
					//查询组名
					Iterator iterator = cm.keyList();
					System.out.println();
					List<String> groupMember = new ArrayList<String>();
					while (iterator.hasNext()) {
						String key = (String) iterator.next();
						String value = cm.SearchKey(key);
						if (value.equals(groupName)) {
							groupMember.add(key);
						} else {
							continue;
						}
					}
					//组名查找成功
					if (groupMember.size() != 0) {
						System.out.println(groupName + " contains:");
						Iterator member = groupMember.iterator();
						while (member.hasNext()) {
							System.out.println(member.next().toString());
						}
						PM.addItem("Successful search", 1);
						PM.addItem("Message responsed", 1);
						logger1.info("Successful search\n");
					} 
					//组名查找失败
					else {
						System.out.println(groupName + " doesn't exists!");
						logger1.info("failed search\n");
						PM.addItem("Message responsed", 1);
					}
				}
			}
			
			//查询组员
			else if(order.equals("s"))
			{
				System.out.println("Please input the student name");
				String studentName=scan.nextLine();
				PM.addItem("Message received", 1);
				logger1.info("search \"" + studentName + "\"");
				String res = cm.SearchKey(studentName);
				
				// License是否可以提供服务
				if (!license.inLicense()) {

					System.out.println("Search service unabled.");
					logger1.info("Search service unabled.");
					FailureManager.logWarn("Service denied");
					PM.addItem("Service denied", 1);
					PM.addItem("Message responsed", 1);
					} 
				else {
					FailureManager.logInfo("Service permitted");
					PM.addItem("Service permitted", 1);
					
					// 查询组员成功
					if (res != null) {
						System.out.println(studentName + " is in " + res
								+ ".");
						PM.addItem("Successful search", 1);
						PM.addItem("Message responsed", 1);
						logger1.info("Successful search\n");
					}

					// 查询组员失败
					else {
						System.out.println(studentName
								+ " is not in the student list.");
						logger1.info("failed search\n");
						PM.addItem("Message responsed", 1);
						}
					}
				
				}
			
			//处理无效命令
			else {
				System.out.println("invalid order!");
				logger1.warn("invalid order:"+order);
			}

		}
			
		scan.close();
		logger1.info("Application exits.\n");
		PM.stop();
	}

}