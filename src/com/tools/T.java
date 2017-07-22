package com.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

public class T 
{

	private static Map  TYPE_MAP = new HashMap();
	
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
//		String [] ss = new String[10];
//		ss[0]= "餐饮服务;冷饮店;冷饮店|餐饮服务;甜品店;甜品店";
//		ss[1]= "餐饮服务;咖啡厅;咖啡厅";
//		ss[2]= "餐饮服务;咖啡厅;星巴克咖啡";
//		ss[3]= "餐饮服务;外国餐厅;其它亚洲菜";
//		ss[4]= "餐饮服务;外国餐厅;印度风味";
//		ss[5]= "餐饮服务;外国餐厅;地中海风格菜品";
//		ss[6]= "餐饮服务;外国餐厅;外国餐厅";
//		ss[7]= "餐饮服务;快餐厅;麦当劳";
//		ss[8]= "餐饮服务;快餐厅;麦当劳|餐饮服务;外国餐厅;法式菜品餐厅";
//		ss[9]= "餐饮服务;餐饮相关场所;餐饮相关";
//
//		for (int i = 0 ;i < ss.length;i++ )
//		{
//			dealWithtype(ss[i]);
//		}
//		
//		Iterator<String> iter = TYPE_MAP.keySet().iterator();
//		
//		while (iter.hasNext()) 
//		{
//		    System.out.println(iter.next());
//		}
		
		//System.out.println("餐饮服务;餐饮相关场所;餐饮相关".replace(";", ","));
	    String ss = "[{\"gridcode\":\"4818067101\",\"typecode\":\"\",\"tel\":\"\",\"newtype\":\"050118\",\"xml\":\"\",\"code\":\"320106\",\"type\":\"餐饮服务;中餐厅;特色/地方风味餐厅\",\"imageid\":\"\",\"url\":\"\",\"timestamp\":\"\",\"citycode\":\"025\",\"buscode\":\"\",\"pguid\":\"B0FFF6Q8SV\",\"address\":\"宁海路(宁海中学对面)\",\"name\":\"三羊餐厅\",\"linkid\":\"\",\"match\":\"1\",\"drivedistance\":\"0\",\"srctype\":\"basepoi\",\"y\":\"32.058520\",\"x\":\"118.770600\"},{\"gridcode\":\"4818067102\",\"typecode\":\"\",\"tel\":\"025-83243895\",\"newtype\":\"050100\",\"xml\":\"\",\"code\":\"320106\",\"type\":\"餐饮服务;中餐厅;中餐厅\",\"imageid\":\"\",\"url\":\"\",\"timestamp\":\"\",\"citycode\":\"025\",\"buscode\":\"\",\"pguid\":\"B00190ZHON\",\"address\":\"三省里11号\",\"name\":\"民国红公馆(北京西路店)\",\"linkid\":\"\",\"match\":\"1\",\"drivedistance\":\"0\",\"srctype\":\"basepoi\",\"y\":\"32.058711\",\"x\":\"118.771626\"},{\"gridcode\":\"4818067102\",\"typecode\":\"\",\"tel\":\"025-83323118;025-63318350;025-83318350\",\"newtype\":\"050101\",\"xml\":\"\",\"code\":\"320106\",\"type\":\"餐饮服务;中餐厅;综合酒楼\",\"imageid\":\"\",\"url\":\"\",\"timestamp\":\"\",\"citycode\":\"025\",\"buscode\":\"\",\"pguid\":\"B00190CKOG\",\"address\":\"北京西路17号江苏化工大厦\",\"name\":\"新万豪大酒店\",\"linkid\":\"\",\"match\":\"1\",\"drivedistance\":\"0\",\"srctype\":\"basepoi\",\"y\":\"32.058937\",\"x\":\"118.771463\"},{\"gridcode\":\"4818067101\",\"typecode\":\"\",\"tel\":\"\",\"newtype\":\"061210\",\"xml\":\"\",\"code\":\"320106\",\"type\":\"购物服务;专卖店;烟酒专卖店\",\"imageid\":\"\",\"url\":\"\",\"timestamp\":\"\",\"citycode\":\"025\",\"buscode\":\"\",\"pguid\":\"B001909VFI\",\"address\":\"宁海路54\",\"name\":\"鸣杰烟酒店\",\"linkid\":\"\",\"match\":\"1\",\"drivedistance\":\"0\",\"srctype\":\"basepoi\",\"y\":\"32.060403\",\"x\":\"118.769562\"},{\"gridcode\":\"4818067101\",\"typecode\":\"\",\"tel\":\"025-83751306;13611505995\",\"newtype\":\"050200\",\"xml\":\"\",\"code\":\"320106\",\"type\":\"餐饮服务;外国餐厅;外国餐厅\",\"imageid\":\"\",\"url\":\"\",\"timestamp\":\"\",\"citycode\":\"025\",\"buscode\":\"\",\"pguid\":\"B0FFF4W5QD\",\"address\":\"宁海路50号小院内(宁海中学对面)\",\"name\":\"法国小栈\",\"linkid\":\"\",\"match\":\"1\",\"drivedistance\":\"0\",\"srctype\":\"basepoi\",\"y\":\"32.060616\",\"x\":\"118.769536\"},{\"gridcode\":\"4818067101\",\"typecode\":\"\",\"tel\":\"\",\"newtype\":\"050100\",\"xml\":\"\",\"code\":\"320106\",\"type\":\"餐饮服务;中餐厅;中餐厅\",\"imageid\":\"\",\"url\":\"\",\"timestamp\":\"\",\"citycode\":\"025\",\"buscode\":\"\",\"pguid\":\"B001905OCE\",\"address\":\"宁海路(近南阴阳营)\",\"name\":\"飘香鸭血粉丝\",\"linkid\":\"\",\"match\":\"1\",\"drivedistance\":\"0\",\"srctype\":\"basepoi\",\"y\":\"32.058618\",\"x\":\"118.770398\"},{\"gridcode\":\"4818067101\",\"typecode\":\"\",\"tel\":\"\",\"newtype\":\"061205\",\"xml\":\"\",\"code\":\"320106\",\"type\":\"购物服务;专卖店;书店\",\"imageid\":\"\",\"url\":\"\",\"timestamp\":\"\",\"citycode\":\"025\",\"buscode\":\"\",\"pguid\":\"B001909GI7\",\"address\":\"天目路2号\",\"name\":\"衡平法律书店\",\"linkid\":\"\",\"match\":\"1\",\"drivedistance\":\"0\",\"srctype\":\"basepoi\",\"y\":\"32.058693\",\"x\":\"118.770184\"},{\"gridcode\":\"4818067101\",\"typecode\":\"\",\"tel\":\"025-83717539;025-83767539\",\"newtype\":\"050105\",\"xml\":\"\",\"code\":\"320106\",\"type\":\"餐饮服务;中餐厅;江苏菜\",\"imageid\":\"\",\"url\":\"\",\"timestamp\":\"\",\"citycode\":\"025\",\"buscode\":\"\",\"pguid\":\"B001911UUH\",\"address\":\"北京西路21号宁海路口\",\"name\":\"好灶头私房菜馆\",\"linkid\":\"\",\"match\":\"1\",\"drivedistance\":\"0\",\"srctype\":\"basepoi\",\"y\":\"32.059213\",\"x\":\"118.769473\"},{\"gridcode\":\"4818067101\",\"typecode\":\"\",\"tel\":\"025-83751306\",\"newtype\":\"071300\",\"xml\":\"\",\"code\":\"320106\",\"type\":\"生活服务;摄影冲印店;摄影冲印\",\"imageid\":\"\",\"url\":\"\",\"timestamp\":\"\",\"citycode\":\"025\",\"buscode\":\"\",\"pguid\":\"B0019171C5\",\"address\":\"宁海路50号(近宁海中学)\",\"name\":\"曼达造型摄影\",\"linkid\":\"\",\"match\":\"1\",\"drivedistance\":\"0\",\"srctype\":\"basepoi\",\"y\":\"32.060622\",\"x\":\"118.769536\"},{\"gridcode\":\"4818067101\",\"typecode\":\"\",\"tel\":\"025-83751306\",\"newtype\":\"050500\",\"xml\":\"\",\"code\":\"320106\",\"type\":\"餐饮服务;咖啡厅;咖啡厅\",\"imageid\":\"\",\"url\":\"\",\"timestamp\":\"\",\"citycode\":\"025\",\"buscode\":\"\",\"pguid\":\"B001912YWN\",\"address\":\"宁海路50号(近宁海中学)\",\"name\":\"EnRoute Cafe\",\"linkid\":\"\",\"match\":\"1\",\"drivedistance\":\"0\",\"srctype\":\"basepoi\",\"y\":\"32.060616\",\"x\":\"118.769536\"},{\"gridcode\":\"4818067101\",\"typecode\":\"\",\"tel\":\"025-83705892\",\"newtype\":\"050118\",\"xml\":\"\",\"code\":\"320106\",\"type\":\"餐饮服务;中餐厅;特色/地方风味餐厅\",\"imageid\":\"\",\"url\":\"\",\"timestamp\":\"\",\"citycode\":\"025\",\"buscode\":\"\",\"pguid\":\"B0FFF6Q94G\",\"address\":\"宁海路52号\",\"name\":\"全家福皮肚面王\",\"linkid\":\"\",\"match\":\"1\",\"drivedistance\":\"0\",\"srctype\":\"basepoi\",\"y\":\"32.060460\",\"x\":\"118.769650\"},{\"gridcode\":\"4818067101\",\"typecode\":\"\",\"tel\":\"4008205681\",\"newtype\":\"050105\",\"xml\":\"\",\"code\":\"320106\",\"type\":\"餐饮服务;中餐厅;江苏菜\",\"imageid\":\"\",\"url\":\"\",\"timestamp\":\"\",\"citycode\":\"025\",\"buscode\":\"\",\"pguid\":\"B0FFFCNXY5\",\"address\":\"广州路321号\",\"name\":\"南京不孕不育医院\",\"linkid\":\"\",\"match\":\"1\",\"drivedistance\":\"0\",\"srctype\":\"basepoi\",\"y\":\"32.060220\",\"x\":\"118.769880\"},{\"gridcode\":\"4818066121\",\"typecode\":\"\",\"tel\":\"\",\"newtype\":\"050100\",\"xml\":\"\",\"code\":\"320106\",\"type\":\"餐饮服务;中餐厅;中餐厅\",\"imageid\":\"\",\"url\":\"\",\"timestamp\":\"\",\"citycode\":\"025\",\"buscode\":\"\",\"pguid\":\"B001912Y70\",\"address\":\"宁海路\",\"name\":\"老城南牛肉面馆\",\"linkid\":\"\",\"match\":\"1\",\"drivedistance\":\"0\",\"srctype\":\"basepoi\",\"y\":\"32.058095\",\"x\":\"118.770635\"},{\"gridcode\":\"4818067101\",\"typecode\":\"\",\"tel\":\"\",\"newtype\":\"050500\",\"xml\":\"\",\"code\":\"320106\",\"type\":\"餐饮服务;咖啡厅;咖啡厅\",\"imageid\":\"\",\"url\":\"\",\"timestamp\":\"\",\"citycode\":\"025\",\"buscode\":\"\",\"pguid\":\"B0FFF6QB9D\",\"address\":\"宁海路50号\",\"name\":\"Encoute Cafe\",\"linkid\":\"\",\"match\":\"1\",\"drivedistance\":\"0\",\"srctype\":\"basepoi\",\"y\":\"32.060680\",\"x\":\"118.769570\"},{\"gridcode\":\"4818067101\",\"typecode\":\"\",\"tel\":\"025-81504853\",\"newtype\":\"050100\",\"xml\":\"\",\"code\":\"320106\",\"type\":\"餐饮服务;中餐厅;中餐厅\",\"imageid\":\"\",\"url\":\"\",\"timestamp\":\"\",\"citycode\":\"025\",\"buscode\":\"\",\"pguid\":\"B00190AEGP\",\"address\":\"宁海路50\",\"name\":\"西部特色面馆\",\"linkid\":\"\",\"match\":\"1\",\"drivedistance\":\"0\",\"srctype\":\"basepoi\",\"y\":\"32.060658\",\"x\":\"118.769450\"},{\"gridcode\":\"4818067102\",\"typecode\":\"\",\"tel\":\"\",\"newtype\":\"170200|061205\",\"xml\":\"\",\"code\":\"320106\",\"type\":\"公司企业;公司;公司|购物服务;专卖店;书店\",\"imageid\":\"\",\"url\":\"\",\"timestamp\":\"\",\"citycode\":\"025\",\"buscode\":\"\",\"pguid\":\"B001905JE1\",\"address\":\"北京西路17号江苏化工大厦9层\",\"name\":\"江苏省化工信息中心有限公司\",\"linkid\":\"\",\"match\":\"1\",\"drivedistance\":\"0\",\"srctype\":\"basepoi\",\"y\":\"32.058937\",\"x\":\"118.771463\"},{\"gridcode\":\"4818067101\",\"typecode\":\"\",\"tel\":\"13951813604\",\"newtype\":\"050300\",\"xml\":\"\",\"code\":\"320106\",\"type\":\"餐饮服务;快餐厅;快餐厅\",\"imageid\":\"\",\"url\":\"\",\"timestamp\":\"\",\"citycode\":\"025\",\"buscode\":\"\",\"pguid\":\"B00191212T\",\"address\":\"宁海路39号宁海中学旁(近宁海大厦)\",\"name\":\"味乐谷(宁海路店)\",\"linkid\":\"\",\"match\":\"1\",\"drivedistance\":\"0\",\"srctype\":\"basepoi\",\"y\":\"32.060400\",\"x\":\"118.769840\"},{\"gridcode\":\"4818067101\",\"typecode\":\"\",\"tel\":\"025-83751306\",\"newtype\":\"050500\",\"xml\":\"\",\"code\":\"320106\",\"type\":\"餐饮服务;咖啡厅;咖啡厅\",\"imageid\":\"\",\"url\":\"\",\"timestamp\":\"\",\"citycode\":\"025\",\"buscode\":\"\",\"pguid\":\"B00190ZO2T\",\"address\":\"宁海路50\",\"name\":\"C澈咖啡馆\",\"linkid\":\"\",\"match\":\"1\",\"drivedistance\":\"0\",\"srctype\":\"basepoi\",\"y\":\"32.060607\",\"x\":\"118.769263\"},{\"gridcode\":\"4818067101\",\"typecode\":\"\",\"tel\":\"\",\"newtype\":\"050300\",\"xml\":\"\",\"code\":\"320106\",\"type\":\"餐饮服务;快餐厅;快餐厅\",\"imageid\":\"\",\"url\":\"\",\"timestamp\":\"\",\"citycode\":\"025\",\"buscode\":\"\",\"pguid\":\"B0FFF46JZD\",\"address\":\"宁海路50\",\"name\":\"绵羊餐厅\",\"linkid\":\"\",\"match\":\"1\",\"drivedistance\":\"0\",\"srctype\":\"basepoi\",\"y\":\"32.060616\",\"x\":\"118.769540\"},{\"gridcode\":\"4818067101\",\"typecode\":\"\",\"tel\":\"\",\"newtype\":\"071100\",\"xml\":\"\",\"code\":\"320106\",\"type\":\"生活服务;美容美发店;美容美发店\",\"imageid\":\"\",\"url\":\"\",\"timestamp\":\"\",\"citycode\":\"025\",\"buscode\":\"\",\"pguid\":\"B001914SD7\",\"address\":\"宁海路43号\",\"name\":\"菲利斯美容\",\"linkid\":\"\",\"match\":\"1\",\"drivedistance\":\"0\",\"srctype\":\"basepoi\",\"y\":\"32.060233\",\"x\":\"118.770122\"},{\"gridcode\":\"4818067101\",\"typecode\":\"\",\"tel\":\"\",\"newtype\":\"071800\",\"xml\":\"\",\"code\":\"320106\",\"type\":\"生活服务;彩票彩券销售点;彩票彩券销售点\",\"imageid\":\"\",\"url\":\"\",\"timestamp\":\"\",\"citycode\":\"025\",\"buscode\":\"\",\"pguid\":\"B0FFF0847Z\",\"address\":\"苏州路6附近\",\"name\":\"彩票销售店\",\"linkid\":\"\",\"match\":\"1\",\"drivedistance\":\"0\",\"srctype\":\"basepoi\",\"y\":\"32.060723\",\"x\":\"118.769540\"}]";
	    String ss1 = "[{\"gridcode\":\"111111111111\"},{\"gridcode\":\"222222222\"}]";
	    
	    JSONArray array = null;
        
        try
        {
            array = JSONArray.fromObject(ss);           
             
        }
        catch(Exception e)
        {
            System.out.println("Exception:"+e);
        }
       
        System.out.println("array:"+array);
		 
	}
	
	public static void dealWithtype(String type)
	{
		String []types = type.split("\\|");
		
		for (int i = 0 ;i < types.length;i++ )
		{
			TYPE_MAP.put(types[i], null);
		}
	}

}
