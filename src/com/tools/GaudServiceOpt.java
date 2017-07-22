package com.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import com.tools.gaud.GaudTypeBean;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 *高德数据
 * 
 * @author  sihua
 * @version  [版本号, 2014-10-22]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class GaudServiceOpt 
{
	private static int index = 1;
	private static String FILENAME ="";
	private static String OLDFILENAME ="";
	private static int switchFileFlag = 0;
	private static String FIRST_PATH  = new File("").getAbsolutePath()+"\\..\\gaud\\";
	private static Map<String,String>  TYPE_MAP = new HashMap<String,String>();
	private static Map<String,Integer>  TYPE_MAP_LEVEL1 = new HashMap<String,Integer>();
	private static Map<String,Integer>  TYPE_MAP_LEVEL2 = new HashMap<String,Integer>();
	private static Map<String,Integer>  TYPE_MAP_LEVEL3 = new HashMap<String,Integer>();
	private static Map<String,Integer>  TYPE_MAP_LEVEL1_ID = new HashMap<String,Integer>();
	private static Map<String,GaudTypeBean>  TYPE_MAP_LEVEL2_ID = new TreeMap<String,GaudTypeBean>();
	private static Map<String,GaudTypeBean> TYPE_MAP_LEVEL3_ID = new TreeMap<String,GaudTypeBean>();
	
	private int test = 0;
	
	//Properties p = new Properties();
	
	private static String area = "default";
	private static String cityname = "default";
	private static String citycode = "";
	private int totalDataNum = 0;
	
	
	public int saveGuadData(String data,int taskFlag,String cityCode,String cityName,String provinceName)
	{
        TYPE_MAP.clear();
        
        area = provinceName;
        cityname = cityName;
        
        if(!OLDFILENAME.equals(cityName))
        {
            switchFileFlag = 1;
            OLDFILENAME = cityName;
        }
        
        citycode = cityCode;
        
        int total = 0;
        
        total = saveGuadDataByCity(data, taskFlag);
        return total;
	}
	
	
	/**
	 * <一句话功能简述>
	 * <功能详细描述>
	 * @param type [参数说明]
	 * 
	 * @return void [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public void fetchtype(String type,String typecode)
	{
		String typeTemp = type.replace(";", ",");
		
		String []types = typeTemp.split("\\|");
		
		//String []typecodeArr = typecode.split("\\|");
		
		for (int i = 0 ;i < types.length;i++ )
		{
			TYPE_MAP.put(types[i], null);
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
	public void dealWithTypes()
	{
		File file = new File(FIRST_PATH + "type.csv");
		BufferedWriter output = null;
		BufferedReader br = null;
		
		try 
		{
			if (file.exists())
			{
			    DataInputStream in = new DataInputStream(new FileInputStream(file));
			    br= new BufferedReader(new InputStreamReader(in,"utf8"));
				//br = new BufferedReader (new FileReader(file));
				
				String lineTxt = null;
				int index = 0;
				
				while((lineTxt = br.readLine()) != null)
				{
					index++;
					
					//过滤表头
					if (index == 1)
					{
						continue;
					}
					
					TYPE_MAP.put(lineTxt.replace(";", ","),null);
				}
				
				try 
				{
					br.close();
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
					br = null;
				}
				
			}
			
			Object [] types = TYPE_MAP.keySet().toArray();
			Arrays.sort(types);
			
			//UTF8 无BOM格式
            FileOutputStream fos = new FileOutputStream(file);
            Writer outW = new OutputStreamWriter(fos, "UTF-8");
            //output = new BufferedWriter(new FileWriter(file,true));
            output = new BufferedWriter(outW);
            
			//output = new BufferedWriter(new FileWriter(file));
			
			output.write("一级业态,二级业态,三级业态"+"\n");
			
			for(int i =0;i<types.length;i++)
			{
				 output.write(types[i]+"\n");
			}
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			try 
			{
				output.close();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
				output = null;
			}
			
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
	public void dealWithTypeForTable()
	{
		Iterator<String> iter = TYPE_MAP.keySet().iterator();
		
		TYPE_MAP_LEVEL1.clear();
		TYPE_MAP_LEVEL2.clear();
		TYPE_MAP_LEVEL3.clear();
		
		//业态名称      业态等级	   父级业态
		while (iter.hasNext()) 
		{
		    String key = iter.next();
		    String []keys = key.split(",");
		    
		    TYPE_MAP_LEVEL1.put( keys[0] + ",一级业态,", null);
		    TYPE_MAP_LEVEL2.put( keys[1] + ",二级业态,"+keys[0], null);
		    
		    if (keys.length >2)
		    {
		    	TYPE_MAP_LEVEL3.put( keys[2] + ",三级业态,"+keys[1], null);
		    }
		}
		
		writeTypesForTables();
	}
	
	/**
	 * <一句话功能简述>
	 * <功能详细描述> [参数说明]
	 * 
	 * @return void [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public void writeTypesForTables()
	{
		File file = new File(FIRST_PATH + "typeTables.csv");
		BufferedWriter output = null;
		
		try 
		{
		    //UTF8 无BOM格式
            FileOutputStream fos = new FileOutputStream(file,true);
            Writer outW = new OutputStreamWriter(fos, "UTF-8");
            //output = new BufferedWriter(new FileWriter(file,true));
            output = new BufferedWriter(outW);
            
			//output = new BufferedWriter(new FileWriter(file));
			
			output.write("业态名称,业态等级,上级业态"+"\n");
			
			Object [] types = TYPE_MAP_LEVEL1.keySet().toArray();
			Arrays.sort(types);
			
			for(int i =0;i<types.length;i++)
			{
				 output.write(types[i]+"\n");
			}
			
			types = TYPE_MAP_LEVEL2.keySet().toArray();
			Arrays.sort(types);
			
			for(int i =0;i<types.length;i++)
			{
				 output.write(types[i]+"\n");
			}
			
			types = TYPE_MAP_LEVEL3.keySet().toArray();
			Arrays.sort(types);
			
			for(int i =0;i<types.length;i++)
			{
				 output.write(types[i]+"\n");
			}
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			try 
			{
				output.close();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
				output = null;
			}
			
		}
	}
	
	//==============================================生成类似表结构的数据=============
	/**
	 * <一句话功能简述>
	 * <功能详细描述> [参数说明]
	 * 
	 * @return void [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public void dealWithTypeForTable_new()
	{
		Iterator<String> iter = TYPE_MAP.keySet().iterator();
		
		TYPE_MAP_LEVEL1_ID.clear();
		TYPE_MAP_LEVEL2_ID.clear();
		TYPE_MAP_LEVEL3_ID.clear();
		
		int id= 1;
		
		//业态名称      业态等级	   父级业态
		while (iter.hasNext()) 
		{
		    String key = iter.next();
		    String []keys = key.split(",");
		    
		    if (!TYPE_MAP_LEVEL1_ID.containsKey(keys[0]))
		    {
		        TYPE_MAP_LEVEL1_ID.put( keys[0], id++);
		    }
		    
		    if (!TYPE_MAP_LEVEL2_ID.containsKey(keys[0]+"_"+keys[1]))
		    {
		        GaudTypeBean gaudTypeBean = new GaudTypeBean();
		        gaudTypeBean.setName(keys[1]);
		        gaudTypeBean.setpName(keys[0]);
		        gaudTypeBean.setId(id++);
		        gaudTypeBean.setPid(TYPE_MAP_LEVEL1_ID.get(keys[0]));
		        TYPE_MAP_LEVEL2_ID.put( keys[0]+"_"+keys[1], gaudTypeBean);
		    }
		    
		    if (keys.length >2)
		    {
		        if (!TYPE_MAP_LEVEL3_ID.containsKey(keys[0]+"_"+keys[1]+"_"+keys[2]))
	            {
	                GaudTypeBean gaudTypeBean = new GaudTypeBean();
	                gaudTypeBean.setName(keys[2]);
	                gaudTypeBean.setpName(keys[1]);
	                gaudTypeBean.setId(id++);
	                gaudTypeBean.setPid(TYPE_MAP_LEVEL2_ID.get(keys[0]+"_"+keys[1]).getId());
	                TYPE_MAP_LEVEL3_ID.put( keys[0]+"_"+keys[1]+"_"+keys[2], gaudTypeBean);
	            }
		    }
		}
		
		writeTypesForTables_new();
	}
	
	/**
	 * <一句话功能简述>
	 * <功能详细描述> [参数说明]
	 * 
	 * @return void [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public void writeTypesForTables_new()
    {
        File file = new File(FIRST_PATH + "typeToTables.csv");
        BufferedWriter output = null;
        
        try 
        {
            
            //UTF8 无BOM格式
            FileOutputStream fos = new FileOutputStream(file);
            Writer outW = new OutputStreamWriter(fos, "UTF-8");
            //output = new BufferedWriter(new FileWriter(file,true));
            output = new BufferedWriter(outW);
            
            //output = new BufferedWriter(new FileWriter(file));
            
            output.write("ID,NAME,PID,LEVEL"+"\n");
            
            Iterator<String> iter = TYPE_MAP_LEVEL1_ID.keySet().iterator();
            
            while(iter.hasNext())
            {
                String key = iter.next();
                int id = TYPE_MAP_LEVEL1_ID.get(key);
                output.write(id+","+key+",0,0"+"\n");
            }
            
           iter = TYPE_MAP_LEVEL2_ID.keySet().iterator();
            
            while(iter.hasNext())
            {
                String key = iter.next();
                GaudTypeBean bean = TYPE_MAP_LEVEL2_ID.get(key);
                output.write(bean.getId()+","+bean.getName()+","+bean.getPid()+",1"+"\n");
            }
            
            iter = TYPE_MAP_LEVEL3_ID.keySet().iterator();
            
            while(iter.hasNext())
            {
                String key = iter.next();
                GaudTypeBean bean = TYPE_MAP_LEVEL3_ID.get(key);
                output.write(bean.getId()+","+bean.getName()+","+bean.getPid()+",2"+"\n");
            }
             
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        finally
        {
            try 
            {
                output.close();
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
                output = null;
            }
            
        }
    }
	
	//======================按照城市抓取数据
    public void createFoldByArea(String folderName) 
    {
        File file = new File(FIRST_PATH + "\\" + folderName);
        
        if (!file.exists())
        {
            file.mkdir();
        }
    }
	
    
    /**
     * 根据规则生成文件
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private String createFileByAreaRule()
    {
         String fileName ="";
        
         if (FILENAME.equals("")||switchFileFlag == 1)
         {
             fileName = FIRST_PATH + area+ "//"+"GD_" + area + "_" + cityname;
             //yyyy年MM月dd日 HH时mm分ss秒
             //yyyyMMddHHmmss
             String timeStamp = TimeTools.getDataByFormat("yy年MM月dd日 HH时mm分ss秒");
             fileName =  fileName + "_" + timeStamp + "_" + (index++) + "_" + "总计_0"  + ".csv";
             
             File file = new File(fileName);
             totalDataNum = 0;
             switchFileFlag = 0;
             
             try 
             {
                file.createNewFile();
             } catch (IOException e) 
             {
                e.printStackTrace();
            }
            
            FILENAME = fileName;
         }
         else
         {
              File fileTemp = new File(FILENAME);
              
              if (!fileTemp.exists()||fileTemp.length() >= 150*1024*1024)
              {
                     fileName = FIRST_PATH + area+ "//"+"GD_" + area + "_" + cityname;
                     //yyyy年MM月dd日 HH时mm分ss秒
                     //yyyyMMddHHmmss
                     String timeStamp = TimeTools.getDataByFormat("yy年MM月dd日 HH时mm分ss秒");
                     fileName =  fileName +"_"+ timeStamp + "_" + (index++) + "_" + "总计_0"  + ".csv";
                     
                     fileTemp = new File(fileName);
                     totalDataNum = 0;
                     switchFileFlag = 0;
                     
                     try 
                     {
                         fileTemp.createNewFile();
                     } 
                     catch (IOException e) 
                     {
                        e.printStackTrace();
                     }
                    
                    FILENAME = fileName;
              }
         }
         
        return FILENAME;
    }
    
    /**
     * <一句话功能简述>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return int [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public int saveGuadDataByCity(String data,int taskFlag)
    {
        
         createFoldByArea(area);
         //createFoldByArea(area+"//" + cityname);
        
         String fileName = createFileByAreaRule();
        
         BufferedWriter output = null;
         File file = null;
         //String aa = data;
         //System.out.println("dara1:"+aa);
          
         JSONArray array = JSONArray.fromObject(data); 
         
        //{"gridcode":"4818066122","typecode":"","tel":"",
        //"newtype":"050100","xml":"","code":"320106",
        //"type":"餐饮服务;中餐厅;中餐厅","imageid":"","url":"",
        //"timestamp":"","citycode":"025","buscode":"","pguid":"B00190B5QD",
        //"address":"金银街20号","name":"阳光餐厅(金银街)","linkid":"","match":"1",
        //"drivedistance":"0","srctype":"basepoi","y":"32.057925","x":"118.774858"}
        int total = 0;
         
        try 
        {
            file = new File(fileName);
            
            if (!file.exists())
            {
                file.createNewFile();
            }
            
            //UTF8 无BOM格式
            Writer outW = new OutputStreamWriter(new FileOutputStream(file,true), "UTF-8");
            //output = new BufferedWriter(new FileWriter(file,true));
            output = new BufferedWriter(outW);
            //System.out.println("array.size():"+array.size());
            for(int i = 0; i < array.size(); i++)
            {
                JSONObject jsonObject = array.getJSONObject(i);
                
                String cityCode = (String)jsonObject.get("citycode");
                String areacode = (String)jsonObject.get("code");
                
                //taskFlag 0 长途区号 1 行政编号
                if (taskFlag == 0)
                {
                  if (!cityCode.equals(citycode))
                    {
                         continue;
                    }
                }
                else if (taskFlag == 1)
                {
                    if (!areacode.startsWith(citycode))
                    {
                        continue;
                    }
                }
                    
                
                String address = (String)jsonObject.get("address");
                address = address.replace(",", ";");
                
                String tel = (String)jsonObject.get("tel");
                tel = tel.replace(",", ";");
                
                String name = (String)jsonObject.get("name");
                name = name.replace(",", ";");
                
                StringBuffer sb = new StringBuffer(1000);
                sb.append(name)
                .append(",")
                .append(jsonObject.get("type"))
                .append(",")
                .append(address)
                .append(",")
                .append(jsonObject.get("x"))
                .append(",")
                .append(jsonObject.get("y"))
                .append(",")
                .append(tel)
                .append(",")
                .append(jsonObject.get("citycode"))
                .append(",")
                .append(jsonObject.get("code"))
                .append(",")
                .append(jsonObject.get("newtype"));
                
                output.write(sb.toString());
                
                output.write("\n");
                total++;
                
                fetchtype((String)jsonObject.get("type"),(String)jsonObject.get("newtype"));
            }
            
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        finally
        {
            try 
            {
                output.close();
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
                output = null;
            }
        }
        
        //更新文件名称中的总记录数
        totalDataNum = totalDataNum + total;
        String newfileName = fileName.substring(0,fileName.indexOf("总计")) + "总计_" + totalDataNum + ".csv";
        File fileForRename = new File(fileName);
        fileForRename.renameTo(new File(newfileName));
        FILENAME = newfileName;
        dealWithTypes();
        
        dealWithTypeForTable_new();
        
        System.out.println(newfileName);
        return total;
        
    }
    
    public static void main(String[] args)
    {

        File file = new File("E:\\type.csv");
        BufferedWriter output = null;
        BufferedReader br = null;
        
        try 
        {
            if (file.exists())
            {
                
                DataInputStream in = new DataInputStream(new FileInputStream(file));
                br= new BufferedReader(new InputStreamReader(in,"utf8"));
                //br = new BufferedReader (new FileReader(file));
                //InputStreamReader isr = new InputStreamReader(
                 //       new FileInputStream(file), "UTF-8");
                 //    br = new BufferedReader(isr);
                    
                String lineTxt = null;
                int index = 0;
                
                while((lineTxt = br.readLine()) != null)
                {
                    index++;
                    
                    //过滤表头
                    if (index == 1)
                    {
                        continue;
                    }
                    
                    TYPE_MAP.put(lineTxt.replace(";", ","),null);
                }
                
                try 
                {
                    br.close();
                } 
                catch (IOException e) 
                {
                    e.printStackTrace();
                    br = null;
                }
                
            }
            
            Object [] types = TYPE_MAP.keySet().toArray();
            Arrays.sort(types);
            
            //UTF8 无BOM格式
            FileOutputStream fos = new FileOutputStream(file);
            Writer outW = new OutputStreamWriter(fos, "UTF-8");
            //output = new BufferedWriter(new FileWriter(file,true));
            output = new BufferedWriter(outW);
            
            //output = new BufferedWriter(new FileWriter(file));
            
            output.write("一级业态,二级业态,三级业态"+"\n");
            
            for(int i =0;i<types.length;i++)
            {
                 output.write(types[i]+"\n");
            }
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        finally
        {
            try 
            {
                output.close();
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
                output = null;
            }
            
        }
    
    }
    
}

//数据格式说明
//sb.append(",")
//.append(jsonObject.get("newtype"))
//.append(",")
//.append(jsonObject.get("xml"))
//.append(",")
//.append(jsonObject.get("code"))
//.append(",")
//.append(jsonObject.get("typecode"))
//.append(",")
//.append(jsonObject.get("imageid"))
//.append(",")
//.append(jsonObject.get("url"))
//.append(",")
//.append(jsonObject.get("timestamp"))
//.append(",")
//.append(jsonObject.get("citycode"))
//.append(",")
//.append(jsonObject.get("buscode"))
//.append(",")
//.append(jsonObject.get("pguid"))
//.append(",")
//.append(jsonObject.get("gridcode"))
//.append(",")
//.append(jsonObject.get("linkid"))
//.append(",")
//.append(jsonObject.get("match"))
//.append(",")
//.append(jsonObject.get("drivedistance"))
//.append(",")
//.append(jsonObject.get("srctype"))
//.append(",")
//.append(jsonObject.get("basepoi"));