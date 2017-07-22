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

public class GDTypeHash
{
    //业态： 一级业态 ->二级业态
    public static Map<String,GDTypeBean> gdTypeMap = new HashMap<String,GDTypeBean>();
    //业态： 一级业态 ->二级业态 ->三级业态
    public static MultiStringHash gdType123 = new  MultiStringHash();
    
    public static String GD_TYPE_LEVEL_ONE = "1";
    public static String GD_TYPE_LEVEL_TWO = "2";
    public static String GD_TYPE_LEVEL_THREE = "3";
    
    /**
     * 加载城格一级二级业态到内存
     * @param fileName [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static void loadType12FromFile()
    {
        File file = new File("E:\\ysh\\高德\\翻译\\gdtype.csv");
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
                gdType123.put(gdType123.k(str[2],str[1],str[0]), "");
                gdTypeMap.put(str[0], new GDTypeBean(str[0],str[4],GD_TYPE_LEVEL_ONE));
                gdTypeMap.put(str[1], new GDTypeBean(str[1],str[5],GD_TYPE_LEVEL_TWO));
                gdTypeMap.put(str[2], new GDTypeBean(str[1],str[6],GD_TYPE_LEVEL_THREE));
            }
        }
        catch (Exception e)
        {
             
        }
        finally
        {
            closeStream(read);
        }
        
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
