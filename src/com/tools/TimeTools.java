package com.tools;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.struts2.ServletActionContext;

public class TimeTools 
{
	public static void main(String [] argvs)
	{
		//System.out.println(getDataByFormat("yyyyMMddHHmmss"));
		//ServletActionContext.getServletContext().getRealPath("/");
		//String aa = "住宿服务;宾馆酒店;宾馆酒店|住宿服务;旅馆招待所;旅馆招待所";
		//String [] bb = aa.split("\\|");
		
		//System.out.println(bb[0]);
		
//		Map m = new HashMap();
//		m.put("住宿服务;宾馆酒店;宾馆酒店", null);
//		m.put("住宿服务;宾馆酒店;夜店", null);
//		m.put("住宿服务;宾馆酒店;饭店", null);
//		m.put("商城;宾馆酒店;旅店", null);
//		m.put("商城;宾馆酒店;夜店", null);
//		m.put("住宿服务;宾馆酒店;旅店", null);
		
//		String []tt = new String[6]; 
//		tt[0] = "住宿服务;宾馆酒店;宾馆酒店";
//		tt[1] = "服务;宾馆酒店;宾馆酒店";
//		tt[2] = "住宿服务;宾馆酒店;111";
//		tt[3] = "住宿服务;宾馆酒店;111";
//		tt[4] = "服务;宾馆酒店;111";
//		tt[5] = "服务;宾馆酒店;111";
		
//		Object [] tt = m.keySet().toArray();
//		Arrays.sort(tt);
//		
//		for (int i = 0;i< tt.length;i++)
//		{
//			System.out.println(tt[i]);
//		}
		
//		Iterator<String> iter = m.keySet().iterator();
//		
//		while (iter.hasNext()) 
//		{
//		    String key = iter.next();
//		    System.out.println(key);
//		}
	}
	
	/**
	 * "yyyy-MM-dd"
	 * "yyyy/MM/dd HH:mm:ss"
	 * @param format
	 * @return
	 */
	public static String getDataByFormat(String strFormat)
	{
		SimpleDateFormat sdf=new SimpleDateFormat(strFormat);

		java.util.Date time=null;
		
		try 
		{
		   time= sdf.parse(sdf.format(new Date()));

		} 
		catch (ParseException e) 
		{
		   e.printStackTrace();
		}
		
		return sdf.format(new Date());
	}
	
	public static Map<String, String> sortMapByKey(Map<String, String> oriMap) 
	{
		if (oriMap == null || oriMap.isEmpty()) 
		{
			return null;
		}
		
		Map<String, String> sortedMap = new TreeMap<String, String>(
				new Comparator<String>() {
					public int compare(String key1, String key2) {
						int intKey1 = 0, intKey2 = 0;
						try {
							intKey1 = getInt(key1);
							intKey2 = getInt(key2);
						} catch (Exception e) {
							intKey1 = 0;
							intKey2 = 0;
						}
						return intKey1 - intKey2;
					}
				});
		
		sortedMap.putAll(oriMap);
		
		return sortedMap;
	}

	private static int getInt(String str) 
	{
		int i = 0;
		
		try 
		{
			Pattern p = Pattern.compile("^\\d+");
			Matcher m = p.matcher(str);
			
			if (m.find()) 
			{
				i = Integer.valueOf(m.group());
			}
			
		} catch (NumberFormatException e) 
		{
			e.printStackTrace();
		}
		return i;
	}
	
}
