package cgfm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


//��ȡMYSQL��.ini�����ļ�
public class CM {
	
	private Map<String,String> map;
	
	public CM()
	{
		map=new HashMap<String,String>();
	}
	
	//��ȡ�����ļ�
	public void ProcessFile(String fileaddress) throws IOException
	{
		File file=new File(fileaddress);
		BufferedReader reader=null;
		String tempString=null;
		String field=null;
		try {
			reader = new BufferedReader(new FileReader(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
		while((tempString=reader.readLine()) != null)
		{
			if(tempString.contains("[")&&tempString.contains("]")&&!tempString.contains("#")) field=tempString;
			if(tempString.contains("=")&&!tempString.contains("#")&&!tempString.contains("[")&&!tempString.contains("]"))
				{
				String[] aString=tempString.split("=");
				String[] bString=aString[0].split(" ");
				String keyString=field+bString[0];
				
				map.put(keyString, aString[1]);
				//System.out.println(keyString+"---"+aString[1]);
				}
			else continue;
		}
	}
	
	//���ռ�ֵ�Բ���
	public void SearchKey(String key)
	{
		if(!map.containsKey(key)) System.out.println("�����ļ��в���������");
		else System.out.println(key+"���Ե�����Ϊ:"+map.get(key));
	}
	
	//�������ù�ģ
	public void getSize()
	{
		System.out.println("������"+map.size()+"��������Ϣ");
	}
	
	//����������Ϣ
	public void list()
	{
		Iterator<String> iterator=map.keySet().iterator();
		String key=null;
		String value=null;
		while(iterator.hasNext())
		{
			key=iterator.next();
			value=map.get(key);
			System.out.println(key+"="+value);
		}
	}
	
	
}
