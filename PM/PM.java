package pm;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Timer;
import java.util.TimerTask;

public class PM extends TimerTask {

	private static LinkedHashMap<String, Integer> map = new LinkedHashMap<String, Integer>();
	public static String path;
	Timer timer = null;
	private static long interval = 60000;

	// �������ָ��
	public void addItem(String _name, int _num) {
		// �Ƿ��Ѿ���������ͳ��
		if (timer != null) {
			if (!map.containsKey(_name))
				map.put(_name, _num);
			else
				map.put(_name, map.get(_name) + _num);
		}
	}

	// ��������ͳ��
	public void start() {
		timer = new Timer();
		timer.scheduleAtFixedRate(this, interval, interval);
	}

	// �ر�����ͳ��
	public void stop() {
		timer.cancel();
		timer = null;
	}

	public void setPath(String _path) {
		path = _path;
	}

	public void resetInterval(long _interval) {
		interval = _interval;
		setDeclaredField(TimerTask.class, this, "period", interval);
	}

		boolean setDeclaredField(Class<?> clazz, Object obj, String name,
				Object value) {
			try {
				Field field = clazz.getDeclaredField(name);
				field.setAccessible(true);
				field.set(obj, value);
				return true;
			} catch (Exception ex) {
				ex.printStackTrace();
				return false;
			}
		}
	@Override
	public void run() {
		try {
			Calendar cal = Calendar.getInstance();
			//��ʼʱ��
			Date date = cal.getTime();
			SimpleDateFormat sdFormat = new SimpleDateFormat(
					"HHʱ-mm��-ss��");
			String myTime = sdFormat.format(date);
			//����ʱ��
			long end = System.currentTimeMillis() + interval;
			java.util.Date endDate = new Date(end);
			String endTime = sdFormat.format(endDate);
			
			String fileName = path + "/pm" + myTime + "��" + endTime + ".txt";
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
		


}