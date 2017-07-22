package com.tools.gaudConvert;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

public class CityCodeHash
{
    private static Map<String,String> citymap = new HashMap<String,String>();
    private String PATH ="E:\\ysh\\高德\\翻译\\翻译规则数据\\";
    
    /** <一句话功能简述>
     * <功能详细描述>
     * @param args [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static void main(String[] args)
    {
        // TODO Auto-generated method stub
        
    }
    
    public void init()
    {
        citymap.clear();
        loadCityCode();
    }
    
    public String getCityIdByCityCode(String code)
    {
        return citymap.get(code);
    }
    
    /**
     * 加载城格一级二级业态到内存
     * @param fileName [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public  void loadCityCode()
    {
        System.out.println("加载城市编码"+PATH + "citycode.csv");
        
        File file = new File(PATH + "citycode.csv");
        BufferedReader read = null;
        DataInputStream in = null;
        String lineTxt = null;
        
        try
        {
            in = new DataInputStream(new FileInputStream(file));
            read= new BufferedReader(new InputStreamReader(in,"utf8"));
            
            while((lineTxt = read.readLine()) != null)
            {
                String [] str = lineTxt.split(",");
                citymap.put(str[3], str[0]);
            }
        }
        catch (Exception e)
        {
             
        }
        finally
        {
            closeStream(read);
        }
        
        System.out.println("加载城市编码完毕");
        
    }
    
    /**
     * <一句话功能简述>
     * <功能详细描述>
     * @param obj [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    
    public static void closeStream(Object obj)
    {
        if(null == obj)
        {
            return;
        }
        
        if (obj instanceof BufferedReader)
        {
            try
            {
                ((BufferedReader)obj).close();
                
            }
            catch (Exception e)
            {
                
            }
        }
        
        if (obj instanceof HttpURLConnection)
        {
            try
            {
                ((HttpURLConnection)obj).disconnect();
                
            }
            catch (Exception e)
            {
                
            }
        }
        
        if (obj instanceof BufferedWriter)
        {
            try
            {
                ((BufferedWriter)obj).close();
                
            }
            catch (Exception e)
            {
                
            }
        }
        
        
        //
    }
    
    
}
