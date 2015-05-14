package pm;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class PM {

	private static Map<String, Integer> map = new HashMap<String, Integer>();
	public static String path;
	private static Thread th;
	private static myThread mt = new myThread(map);

	// �������ָ��
	public static void addItem(String _name, int _num) {
		// �Ƿ��Ѿ���������ͳ��
		if (th != null) {
			if (!map.containsKey(_name))
				map.put(_name, _num);
			else
				map.put(_name, map.get(_name) + _num);
		}
	}
	// ��������ͳ��
	public static void start() {
		th = new Thread(mt);
		th.start();
	}
	// �ر�����ͳ��
	public static void stop() {
		mt.stop();
		th = null;
	}

	public static void setPath(String _path){
		path = _path;
	}
	
	static class myThread implements Runnable {
		private static Map<String, Integer> map = new HashMap<String, Integer>();
		Timer t = new Timer();

		public myThread(Map<String, Integer> _map) {
			myThread.map = _map;
		}

		public void run() {

			t.scheduleAtFixedRate(new TimerTask() {
				@Override
				public void run() {
					try {
						Calendar cal = Calendar.getInstance();
						java.util.Date date = cal.getTime();
						SimpleDateFormat sdFormat = new SimpleDateFormat(
								"yyyy-MM-dd-hh-mm-ss");
						String myTime = sdFormat.format(date);

						String fileName = path +"/pm"+ myTime + ".txt";
						File file = new File(fileName);
						file.createNewFile();

						FileWriter fw = new FileWriter(file);
						for (String keyWriter : map.keySet()) {
							fw.write(keyWriter + ":");
							fw.write(map.get(keyWriter).toString());
							fw.write('\n');
							fw.flush();
						}
						fw.close();
						map.clear();
					} catch (Exception e) {
						
					}
				}
			}, 60000, 60000);
		}

		public void stop() {
			t.cancel();
		}
	}
	

	// public static void main(String[] args) {
	// 	PM.start();
	// 	PM.setPath("D:/pm");
	// 	PM.addItem("power", 100);
	// 	PM.addItem("power", 100);
	// 	PM.addItem("power", 500);
	// 	PM.addItem("product", 50);
	// 	PM.addItem("product", 100);

	}

}