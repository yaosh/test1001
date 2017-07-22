package com.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * 
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  sihua
 * @version  [版本号, 2014-10-23]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class CfgTools
{
    
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
        Properties p = readCfgFile("E://apache-tomcat-6.0.20//bin//sys.cfg");
        String temp = p.getProperty("area");
//        try
//        {
//            String resultName=new String(temp.getBytes("ISO-8859-1"),"utf8");
//            System.out.println("type:"+resultName); 
//        }
//        catch (UnsupportedEncodingException e)
//        {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        
        String aa = "sss,dd,333,dd,555";
        aa= aa.replace(",", ";");
        System.out.println(aa);
        
    }
    
    /**
     * <一句话功能简述>
     * <功能详细描述>
     * @param fileName
     * @return [参数说明]
     * 
     * @return Properties [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static Properties readCfgFile(String fileName)
    {
        InputStream inputStream = null;
        
        try
        {
            inputStream = new FileInputStream(new File(fileName));
        }
        
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        
        Properties p = new Properties();   
        
        try 
        {   
           p.load(inputStream);   
        } 
        catch (IOException e1) 
        {   
         e1.printStackTrace();   
        }   
        
        return p;
      
    }
    
}
