package com.tools.gaudConvert;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.util.Set;

/**
 * 
* @ClassName: MultiOptHashMap 
* @Description: MULTI HASHMAP
* @author YAOSIHUA  
* @date 2014-4-9 下午9:02:01 
*
 */
public class MultiRuleHash
{
    private Map<String,Object> myMap = new ConcurrentHashMap<String,Object>();
    
	public static void main(String[] argvs)
	{
		MultiRuleHash multiMap = new MultiRuleHash();
		
		multiMap.put(multiMap.k("101","20140101","10000000"), new RuleBean());
		
		multiMap.put(new RuleBean(),"101","20140101","10100000");
		multiMap.put(new RuleBean(),"101","20140101","10200000");
		multiMap.put(new RuleBean(),"101","20140102","10000000");
		multiMap.put(new RuleBean(),"101","20140102","11000000");
		
		multiMap.put(new RuleBean(),"201","20140102","11000000");
		multiMap.put(new RuleBean(),"201","20140103","11000000");
		multiMap.put(new RuleBean(),"201","20140104","11000000");
		multiMap.put(new RuleBean(),"201","20140104","12000000");
		
	    System.out.println(multiMap.v("101","20140101","10000000"));
	    System.out.println(multiMap.v("101","20140101","10200000"));
	    System.out.println(multiMap.v("201","20140102","11000000"));
	    System.out.println(multiMap.v("201","20140103","11000000"));
	    
		String[] keys = multiMap.sonKeys("101","20140101");
		
		for(String key :keys)
		{
		    System.out.println(key);
		}
		
		keys = multiMap.sonKeys("101");
		
		for(String key :keys)
		{
		    System.out.println(key);
		}
		
		
	}
	
	/**
	 * <一句话功能简述>
	 * <功能详细描述> [参数说明]
	 * 
	 * @return void [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public  void clear()
	{
	    myMap.clear();
	}
	/**
	 * 
	* @Title: put 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param key
	* @param @param value    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public synchronized void put(String []keys,RuleBean value)	
	{
		if (isNullArr( keys))
		{
			return;
		}
		
		put(keys,value,myMap);
	}
	
	/**
	 * 
	* @Title: put 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param key
	* @param @param value    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public synchronized void put(RuleBean value,String ...keys)	
	{
		if (isNullArr( keys))
		{
			return;
		}
		
		put(keys,value,myMap);
	}
	
	
	/**
	 * 
	* @Title: v 
	* @param @param key
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws
	 */
	@SuppressWarnings("unchecked")
	public RuleBean v(String... keys)
	{
		if (isNullArr(keys))
		{
			return null;
		}
		
		Map<String,Object> map = myMap;
		
		for(int i = 0;i < keys.length -1; i++)
		{
			if (null != map && map.get(keys[i]) instanceof ConcurrentHashMap)
			{
				map = (ConcurrentHashMap<String,Object>)map.get(keys[i]);
			}
			else
			{
				map = null;
				break;
			}
			
		}
		
		if (null == map ||!map.containsKey(keys[keys.length - 1]))
		{
			return null;
		}
		
		return (RuleBean)map.get(keys[keys.length - 1]);
		
	}
	
	
	/**
	 * 
	* @Title: exist 
	* @param @param key
	* @param @return    设定文件 
	* @return boolean    返回类型 
	* @throws
	 */
	@SuppressWarnings("unchecked")
	public boolean exist(String ...keys)
	{
		if (isNullArr(keys))
		{
			return false;
		}
		
		Map<String,Object> map = myMap;
		
		boolean result = true;
		
		for(int i = 0;i < keys.length -1; i++)
		{
			if (null != map && map.get(keys[i]) instanceof ConcurrentHashMap)
			{
				map = (ConcurrentHashMap<String,Object>)map.get(keys[i]);
			}
			else
			{
				map = null;
				result = false;
				break;
			}
			
		}
		
		if (null != map && map.containsKey(keys[keys.length - 1]))
		{
		   result = true;
		}
		
		return result;
		
	}
	
	/**
	 * 
	* @Title: getMap 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param key
	* @param @return    设定文件 
	* @return Map    返回类型 
	* @throws
	 */
	 @SuppressWarnings("unchecked")
	public Map<String,Object> getMap(String... keys)
	 {
		if (isNullArr(keys))
		{
			return new ConcurrentHashMap<String,Object>(0);
		}
		
		Map<String,Object> map = myMap;
		
		for(int i = 0;i < keys.length; i++)
		{
			if (null != map && map.get(keys[i]) instanceof ConcurrentHashMap)
			{
				map = (ConcurrentHashMap<String,Object>)map.get(keys[i]);
			}
			else
			{
				map = null;
				break;
			}			
		}
		
		return null == map? new ConcurrentHashMap<String,Object>(0):map;	
	}
	
	 /**
	  * 
	 * @Title: sortKeys 
	 * @param @param key
	 * @param @return    设定文件 
	 * @return String[]    返回类型 
	 * @throws
	  */
	@SuppressWarnings("unchecked")
	public String[] sonKeys(String... keys)
	 {
		if (isNullArr(keys))
		{
			return new String[0];
		}
		
		Map<String,Object> map = myMap;
		
		for(int i = 0;i < keys.length; i++)
		{
			if (null != map && map.get(keys[i]) instanceof ConcurrentHashMap)
			{
				map = (ConcurrentHashMap<String, Object>)map.get(keys[i]);
			}
			else
			{
				map = null;	
				break;
			
			}			
		}
		
		if (null == map)
		{
			return new String[0];
		}
		
		Set<String> set = map.keySet();
		String[] keyArray = set.toArray(new String[0]);
		Arrays.sort(keyArray);
		
		return keyArray;	
	}
	
	/**
	  * 
	 * @Title: getsortKeys 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @param key
	 * @param @return    设定文件 
	 * @return String[]    返回类型 
	 * @throws
	  */
	public String[] getRoot()
	 {
		Set<String> set = myMap.keySet();
		String[] keyArray = set.toArray(new String[0]);
		Arrays.sort(keyArray);
		
		return keyArray;	
	}
	
	/**
	 * 
	* @Title: put 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param keys
	* @param @param value
	* @param @param map    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@SuppressWarnings("unchecked")
	private void put(String[] keys,RuleBean value,Map<String,Object> map)
	{
	     Map<String,Object> sonMap = null;
		  
	     if (keys.length >= 2)
		 {
		     if (null != map.get(keys[0]) && map.get(keys[0]) instanceof ConcurrentHashMap)
			 {
				 sonMap = (ConcurrentHashMap<String, Object>)map.get(keys[0]);
			 }
			 else
			 {
				 sonMap = new ConcurrentHashMap<String,Object>();
				 map.put(keys[0],sonMap);
			 }
			 
			 String [] dests = new String[keys.length - 1];
			 System.arraycopy(keys,1,dests,0,keys.length - 1);			 
			 
			 put(dests,value,sonMap);
		 }
		 else
		 {
			 map.put(keys[0],value);
		 }		 
		 
	}
	
	
	
	/**
	 * 
	* @Title: isNullArr 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param keys
	* @param @return    设定文件 
	* @return boolean    返回类型 
	* @throws
	 */
	private boolean isNullArr(String [] keys)
	{
		if (null == keys || keys.length == 0)
		{
			return true;
		}
		
		return false;
	}
	
	/**
	 * 
	* @Title: put 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param key
	* @param @param value    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public String[] k(String ...keys)	
	{
		if (isNullArr(keys))
		{
			return new String[0];
		}
		
		return keys;
	}

}

