package com.tools;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

public class StringTools
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
         //String aa = "E:\\apache-tomcat-6.0.20\\bin\\..\\gaud\\江苏\\南京\\guadData_20141027173703_1.csv";
        //System.out.println(getFileRealName(aa));
        
    }
    
    public static String getFileRealName(String allPathFileName)
    {
        return allPathFileName.substring(allPathFileName.lastIndexOf("\\")+1);
    }
    
    /**
     * 获取分词列表
     * @param src
     * @return [参数说明]
     * 
     * @return List<String> [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static void getChinaeseWordList(String src)
    {
        List<String> list = new ArrayList<String>();
        //long starttime = System.currentTimeMillis();
        
        try
        {
            //空 不分词
            if(null == src || "".equals(src.trim()))
            {
                return ;
            }
            
            //非中文，不分词
            if(src.getBytes().length == src.length())
            {
                return ;
            }
            
            //去掉空格
            src =  src.replace(" ", "");
            
            StringReader sr = new StringReader(src);
            IKSegmenter ik = new IKSegmenter(sr, true);
            Lexeme lex = null;
            
            while ((lex = ik.next()) != null)
            {
                list.add(lex.getLexemeText());
            }
            
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        
        //long endtime = System.currentTimeMillis();
        //long time = (endtime - starttime) ;
        
        //if (time > 10)
        //{
        //    System.out.println(src + "分词耗费" +time);
        //}
        
        
        return ;
    } 
    
   /**
    * 是否含有中文
    * @param str
    * @return [参数说明]
    * 
    * @return boolean [返回类型说明]
    * @exception throws [违例类型] [违例说明]
    * @see [类、类#方法、类#成员]
    */
    private boolean isChina(String str)
    {
        return str.getBytes().length != str.length();
    }
    
    public static boolean isNum(String msg)
    {
        if (java.lang.Character.isDigit(msg.charAt(0)))
        {
            return true;
        }
        
        return false;
    }
    
    
    
    public static boolean isNumeric(String str)
    {
        
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        
        if (!isNum.matches())
        {
            return false;
        }
        
        return true;
    }
    
}
