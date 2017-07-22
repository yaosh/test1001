package com.tools.gaudConvert;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class Test
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
        Test t = new Test();
        //System.out.println(t.getChinaeseWordList("卡尔文克莱恩手表"));
        //System.out.println(t.getChinaeseWordList("中ddd民共和国"));
        //System.out.println(t.getChinaeseWordList("ｙｕｄｌｄｄ"));
        //t.sqlLike("");
        //t.shopMaxMatchBrand("卡尔文克莱恩手表", "Calvin Klein WATCHES", "卡尔文,克莱,恩,手表", "卡,尔,文,克,莱,恩,手,表");
        String aa [] = t.splitOpt("|uuu|", "|");
        
        System.out.println(aa.length);
        
        for(String t1 : aa)
        {
            System.out.println(t1);
        }
        
    }
    
    
    private String [] splitOpt(String str,String splitStr)
    {
        List <String>list = new ArrayList<String>();
        
        while(str.indexOf(splitStr)>=0)
        {
            list.add(str.substring(0, str.indexOf(splitStr)));
            System.out.println(str.substring(0, str.indexOf(splitStr)));
            str = str.substring(str.indexOf(splitStr)+1);
        }
        
        list.add(str);
        String[] arr = (String[])list.toArray(new String[list.size()]);
        return arr;
    }
    
    /**
     * 比较品牌名称（品牌中文名，品牌英文名，品牌别名）和商家名称的相似性
     * 商家最宽松匹配品牌名称 （最宽松匹配算法包含了最严格匹配算法  先严格 后宽松）
     * @param brandName
     * @param shopName
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private boolean shopMaxMatchBrand(String brandName,String shopName,String brandWords,String brandChars)
    {
        
        if(null == brandName || null == shopName||"".equals(brandName.trim())|| "".equals(shopName.trim()))
        {
            return false;
        }
        
        //去空格
        brandName = brandName.toLowerCase().trim();
        shopName = shopName.toLowerCase().trim();
        brandName = brandName.replaceAll(" ", "");
        shopName = shopName.replaceAll(" ", "");
        
        //性能调优
        if(brandName.length() >shopName.length() )
        {
            return false;
        }
        
        boolean ischina = isChina(brandName);
        boolean isEqual = shopName.equals(brandName);
        boolean isContains = shopName.contains(brandName);
        int  brandLen = brandName.length();
        
        //优先整体匹配====================================
        if(isEqual)
        {
            return true;
        }
        
        //含中文, 不相等，包含关系, 长度大于等于2 则表示匹配
        if(isContains && ischina && brandLen >= 2)
        {
            return true;
        }
        
        //全半角字符 不相等，包含关系, 长度大于等于3 则表示匹配
        if(isContains && !ischina && brandLen >= 3)
        {
            return true;
        }
        
        //分词匹配========================================= 
        //分词匹配 商家含有品牌的所有分词，则匹配 否则不匹配
        //纯中文的品牌名四个字（含）才做分词计算。除此以外的中英文混杂，字符，字母等都不做分词计算
        //按顺序匹配 模拟数据库的like 采用正则表达式
        if(brandName.length() < 4 )
        {
            return false;
        }
        
        if(notNullAndNot0(brandWords))
        {
            String like = brandWords.replace(",", "(.*)");
            Pattern p = Pattern.compile(like); //（.*）表示%
            Matcher m = p.matcher(shopName);
            
            if(m.find()) 
            {
                return true;
            }
        }
    
        //字符匹配
        //字符分词匹配 商家含有品牌的所有字符分词，则匹配 否则不匹配
        //纯中文的品牌名四个字（含）才做字符分词计算。除此以外的中英文混杂，字符，字母等都不做字符分词计算
        //按顺序匹配 模拟数据库的like 采用正则表达式
        if(notNullAndNot0(brandChars))
        {
            String like = brandChars.replace(",", "(.*)");
            Pattern p = Pattern.compile(like); //（.*）表示%
            Matcher m = p.matcher(shopName);
            
            if(m.find()) 
            {
                return true;
            }
        }
        
        return false;
       
    }
    
    
    public boolean notNullAndNot0(String key)
    {
        if (null == key || key.trim().equals("") || key.trim().equals("0"))
        {
            return false;
        }
        
        return true;
    }
    
    //
    private boolean sqlLike(String str)
    {
        //(.+)(abc)(.+)
        Pattern p = Pattern.compile(" (.*)(肯)(.*)(德基)(.*)"); //（.+）是表示%吧？
        Matcher m = p.matcher("ddddfd 肯ff德基 dfdfd");
        if(m.find()) 
        {
            System.out.println(m.group());
        }
        
        return false;
        
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
    private  String getChinaeseWordList(String src)
    {
        //long starttime = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();
        
        try
        {
            //空 不分词
            if(null == src || "".equals(src.trim()))
            {
                return "";
            }
            
            //非中文，不分词
            if(src.getBytes().length == src.length())
            {
                return "";
            }
            
            //去掉空格
            src =  src.replaceAll(" ", "");
            src =  src.replaceAll(",", "");
            
            //含有非中文字符也不分词
            if(!isAllChinaese(src))
            {
                return "";
            }
            
            
            StringReader sr = new StringReader(src);
            IKSegmenter ik = new IKSegmenter(sr, true);
            Lexeme lex = null;
            int i = 0;
            
            while ((lex = ik.next()) != null)
            {
                sb.append(lex.getLexemeText()).append(",");
            }
            
            //删除最后一个逗号
            sb.delete(sb.length()-1, sb.length());
            
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
        
        
        return sb.toString();
    }
    
    private boolean isAllChinaese(String str)
    {
       //去掉空格
        str =  str.replaceAll(" ", "");
        
        //性能调优 长度上 说明是纯非中文
        //但是长度不等 不代表是纯中文
        if(str.getBytes().length == str.length())
        {
            return false;
        }
        
        char[] charArr = str.toCharArray();
        
        //单个字符判断 是否为纯中文串
        for(int i = 0;i < charArr.length;i++)
        {
            if(!isChinese(charArr[i]))
            {
                return false;
            }
        }
        
        return true;
    }
    
    
    private static boolean isChinese(char c)
    {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION)
        {
            return true;
        }
        
        return false;
    }
    
    
    public static void matchTest()
    {
        String shop = "江南人民公社";
        String brand = "江南公社";
        
        for(int i =0;i <brand.length()-1;i++ )
        {
            String first = brand.substring(0, i+1);
            String last = brand.substring(i+1, brand.length());
            
            System.out.println("first:" + first + "   last:" + last);
            
            if(shop.contains(first) && shop.contains(last))
            {
                System.out.println("商家:" + shop + " 可以匹配:   " + brand);
            }
            
        }
        
        System.out.println(getWordList("基于java语言开发的轻量级的中文分词工具包"));
        System.out.println(getWordList("江南人民公社"));
        
    }
    
    
    public static List<String> getWordList(String src)
    {
        List<String> list = new ArrayList<String>();
        
        try
        {
            //空 不分词
            if(null == src || "".equals(src.trim()))
            {
                return list;
            }
            
            //非中文，不分词
            if(src.getBytes().length == src.length())
            {
                return list;
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
        
        return list;
    }
    
    public static void createxls(String file,String sheetName)
    {
        try
        {
            HSSFWorkbook wb = new HSSFWorkbook();//建立新HSSFWorkbook对象
            
            FileOutputStream fileOut = new FileOutputStream(file,false);
            HSSFSheet sheet = wb.createSheet(sheetName);// 建立新的sheet对象
            
            wb.write(fileOut);//把Workbook对象输出到文件workbook.xls中
            fileOut.close();
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    
    public static void addSheetToExcel(String file,String sheetName)
    {
        try
        {
            
            File excel = new File(file);  // 读取文件
            String tempFile = excel.getAbsolutePath().replace(".xls", "temp.xls");
            String oldName = excel.getAbsolutePath();
            FileInputStream in = new FileInputStream(excel); // 转换为流
            HSSFWorkbook workbook = new HSSFWorkbook(in); // 加载excel的 工作目录
            workbook.removeSheetAt(0);
            HSSFSheet newSheet = workbook.createSheet(sheetName); // 添加一个新的sheet
            
            FileOutputStream fileOut = new FileOutputStream(new File(tempFile),true);
            workbook.write(fileOut);
            fileOut.close();
            in.close();
            
            File newFile = new File(tempFile);
            excel.delete();
            File excel1 = new File(oldName);
            newFile.renameTo(excel1);
            
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public boolean isChina(String str)
    {
        return str.getBytes().length != str.length();
    }
    
    /**
     * 去除字符串中的空格、回车、换行符、制表符
     * <功能详细描述>
     * @param str
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static String replaceEnterSpace(String str)
    {
        String dest = "";
        
        if (str != null)
        {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        
        return dest;
    }
    /**
     * 去除字符串中的空格、回车、换行符、制表符
     * <功能详细描述>
     * @param str
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static String replaceEnter(String str)
    {
        String dest = "";
        
        if (str != null)
        {
            Pattern p = Pattern.compile("\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        
        return dest;
    }
    
    /**
     * 去括号
     * @param src
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static String getStringExcludeBracket(String src)
    {
        if (null == src||src.trim().equals(""))
        {
            return src;
        }
        
        //几种可能性
        //有左括号和右括号，括号靠最左边，括号靠最右边 ，括号在中间 ，
        //只有左括号（括号靠最左边，括号靠最右边 ，括号在中间），
        //只有右括号（括号靠最左边，括号靠最右边 ，括号在中间）
        String shopName = src.trim();
        String result = "";
        
        int left = shopName.indexOf("(");
        int right = shopName.lastIndexOf(")");
        
        //括号反转的情况不考虑 例如:ddd)dffd(
        //单括号的情况不考虑
        if(left >=0 && right >= 0)
        {
            if(left <= right )
            {
                result = shopName.substring(0,left) + shopName.substring(right+1);
            }
            else if (left > right )
            {
                result = shopName;
            }
        }
        else
        {
            result = shopName;
        }
        
        return result;
        
    }
    
    
    /**
     * 比较品牌名称（品牌中文名，品牌英文名，品牌别名）和商家名称的相似性
     * <功能详细描述>
     * @param brandName
     * @param shopName
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public boolean strLike(String brandName,String shopName)
    {
        
        if(null == brandName || null == shopName||"".equals(brandName.trim())|| "".equals(shopName.trim()))
        {
            return false;
        }
        
        brandName = brandName.toLowerCase().trim();
        shopName = shopName.toLowerCase().trim();
        
        //如果含有汉字，则按照大于等于2含有，等于1，相等的匹配原则
        if(isChina(brandName))
        {
            if ((shopName.contains(brandName) && brandName.length() >= 2)
                    ||(shopName.equals(brandName) && brandName.length() <=1 && brandName.length() > 0))
            {
                return true;
            }
        }
        //如果为全英文，则按照大于等于3含有，(2,1)范围相等的匹配原则
        else
        {
            if ((shopName.contains(brandName)&&brandName.length() >= 3)
                    ||(shopName.equals(brandName) && brandName.length() <=2 && brandName.length() > 0))
            {
                return true;
            }
        }
        
        
        
        return false;
        
       
    }
    
    /**
     * 生成多个退格符
     * @param count
     * @return
     */
    private static String getBackspaces(int count) {
        char[] chs = new char[count];
        for(int i = 0; i < count; i++) {
            chs[i] = '\b';
        }
        return new String(chs);
    }
    
    
}
