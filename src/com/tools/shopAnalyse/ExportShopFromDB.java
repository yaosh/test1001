package com.tools.shopAnalyse;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExportShopFromDB
{
    
    public static String URL ="jdbc:mysql://61.155.8.199:3306/zhaoshang";
    public static String USER = "cg001";
    public static String PWD ="cgdb2015zaqXSW";
    public static String path = "E:\\ysh\\高德\\商家数据分析\\";
    //public static String URL ="jdbc:mysql://127.0.0.1:3306/chengge";
    //public static String USER = "root";
    //public static String PWD ="888888";
    
    //mysql://root:qwer1456ZXC56712@61.155.8.175:3306/chengge_test',
    
    public static void main(String [] argv)
    {
        ExportShopFromDB exp = new ExportShopFromDB();
        exp.run();
        
    }
    
    public void run()
    {
        canyinDingzhi();
        gouwuDingzhi();
        tianpinDingzhi();
    }
    
    /**
     * 林总需求 商家数据从非分表中导出
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private void gouwuDingzhi()
    {
        System.out.println("导出购物商家 生成excel 表格");
        
        //String canyinType2s = "2,9,11,14,15,16,17,230,231,733";
        //String []canyinType2List = canyinType2s.split(",");
        
        //餐饮 7 购物 6
        List<String> typeList = export12Type("6");
        String type1Name = "购物商家";
        
        List<String> list = null;
        
        for(String types:typeList)
        {
            String [] typeinfo = types.split(",");
            String key = typeinfo[1];
            ExcelUtilsPoi.createxls(path + type1Name + "_" + typeinfo[0]+".xls", "11");
            list = exportBrandShopByType2(key,"品牌商家");
            ExcelUtilsPoi.addSheetToExcel(path + type1Name + "_" + typeinfo[0]+".xls","品牌商家",list, false,"11");
            list = exportShopByKeywordAndType2(key,"(,)","","带括号的商家");
            ExcelUtilsPoi.addSheetToExcel( path + type1Name + "_" + typeinfo[0]+".xls","带括号的商家",list,true,"11");
            list = exportShopByKeywordAndType2(key,"直营","(,)","带直营的商家");
            ExcelUtilsPoi.addSheetToExcel(path + type1Name + "_" + typeinfo[0]+".xls","带直营的商家",list, true,"11");
            list = exportShopByKeywordAndType2(key,"加盟","(,),直营","带加盟的商家");
            ExcelUtilsPoi.addSheetToExcel( path + type1Name + "_" + typeinfo[0]+".xls","带加盟的商家",list,true,"11");
            list = exportShopByKeywordAndType2(key,"连锁","(,),直营,加盟","带连锁的商家");
            ExcelUtilsPoi.addSheetToExcel( path + type1Name + "_" + typeinfo[0]+".xls","带连锁的商家",list,true,"11");
            list = exportShopByKeywordAndType2(key,"NO.","(,),直营,加盟,连锁","带NO.的商家");
            ExcelUtilsPoi.addSheetToExcel(path + type1Name + "_" + typeinfo[0]+".xls","带NO.的商家",list, true,"11");
            list = exportShopByKeywordAndType2(key,"店","(,),直营,加盟,连锁,NO.","带店的商家");
            ExcelUtilsPoi.addSheetToExcel(path + type1Name + "_" + typeinfo[0]+".xls","带店的商家",list, true,"11");
            list = exportShopByKeywordAndType2(key,"","(,),直营,加盟,连锁,NO.,店","其他商家");
            ExcelUtilsPoi.addSheetToExcel( path + type1Name + "_" + typeinfo[0]+".xls","其他商家",list, true,"11");
            
        }
        
        
        System.out.println("导出购物商家 生成excel 表格完毕");
    }
    
    /**
     * 林总需求 商家数据从非分表中导出
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private void canyinDingzhi()
    {
        System.out.println("导出餐饮商家 生成excel 表格");
        
        //String canyinType2s = "2,9,11,14,15,16,17,230,231,733";
        //String []canyinType2List = canyinType2s.split(",");
        
        //餐饮 7 购物 6
        List<String> typeList = export12Type("7");
        String type1Name = "餐饮商家";
        
        List<String> list = null;
        
        for(String types:typeList)
        {
            String [] typeinfo = types.split(",");
            String key = typeinfo[1];
            ExcelUtilsPoi.createxls(path + type1Name + "_" + typeinfo[0]+".xls", "11");
            list = exportBrandShopByType2(key,"品牌商家");
            ExcelUtilsPoi.addSheetToExcel(path + type1Name + "_" + typeinfo[0]+".xls","品牌商家",list, false,"11");
            list = exportShopByKeywordAndType2(key,"(,)","","带括号的商家");
            ExcelUtilsPoi.addSheetToExcel( path + type1Name + "_" + typeinfo[0]+".xls","带括号的商家",list,true,"11");
            list = exportShopByKeywordAndType2(key,"直营","(,)","带直营的商家");
            ExcelUtilsPoi.addSheetToExcel(path + type1Name + "_" + typeinfo[0]+".xls","带直营的商家",list, true,"11");
            list = exportShopByKeywordAndType2(key,"加盟","(,),直营","带加盟的商家");
            ExcelUtilsPoi.addSheetToExcel( path + type1Name + "_" + typeinfo[0]+".xls","带加盟的商家",list,true,"11");
            list = exportShopByKeywordAndType2(key,"连锁","(,),直营,加盟","带连锁的商家");
            ExcelUtilsPoi.addSheetToExcel( path + type1Name + "_" + typeinfo[0]+".xls","带连锁的商家",list,true,"11");
            list = exportShopByKeywordAndType2(key,"NO.","(,),直营,加盟,连锁","带NO.的商家");
            ExcelUtilsPoi.addSheetToExcel(path + type1Name + "_" + typeinfo[0]+".xls","带NO.的商家",list, true,"11");
            list = exportShopByKeywordAndType2(key,"店","(,),直营,加盟,连锁,NO.","带店的商家");
            ExcelUtilsPoi.addSheetToExcel(path + type1Name + "_" + typeinfo[0]+".xls","带店的商家",list, true,"11");
            list = exportShopByKeywordAndType2(key,"","(,),直营,加盟,连锁,NO.,店","其他商家");
            ExcelUtilsPoi.addSheetToExcel( path + type1Name + "_" + typeinfo[0]+".xls","其他商家",list, true,"11");
            
        }
        
        
        System.out.println("导出餐饮商家 生成excel 表格完毕");
    }
    
    /**
     * 甜品， 从分表中导出 ，非林总需求
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    
    private void tianpinDingzhi()
    {
        System.out.println("导出餐饮甜品 生成excel 表格");
        
        //String canyinType2s = "2,9,11,14,15,16,17,230,231,733";
        //String []canyinType2List = canyinType2s.split(",");
        
        //餐饮 7 购物 6
        //List<String> typeList = export12Type("7");
        String type1Name = "餐饮甜品饮品商家";
        List<String> list = null;
        String keys = "甜品|饮品|糖品|糖水|饮茶|茶饮|鲜饮|果饮|豆花|奶茶|汤圆|豆沙|奶油|鲜奶|冰淇淋|冰激凌|抹茶|凉茶|果茶|双皮奶|布丁|棉花糖|冰糖|巧克力|星冰乐|拿铁|奶昔|奶酪|柠檬|果汁|冰棍|冰吧|冰食|冰饮|冰品|冰沙|冰粉|绵绵冰|冷饮|鲜榨|现榨|刨|仙芋|酸奶|咖啡|coffee|café";
        String []keyList = keys.split("\\|");
        
        
        String key = "11";
        ExcelUtilsPoi.createxls(path + type1Name +".xls", "11");
        list = exportSubTableBrandShopByType2(key,"品牌商家","cg_shop_anhui","cg_shop_type2_anhui");
        ExcelUtilsPoi.addSheetToExcel(path + type1Name +".xls","品牌商家",list, false,"11");
        
        for(int i = 0; i< keyList.length;i++)
        {
            list = exportSubTableShopByKeywordAndType2(key,keyList[i],"",keyList[i],"cg_shop_anhui","cg_shop_type2_anhui");
            ExcelUtilsPoi.addSheetToExcel( path + type1Name + ".xls",keyList[i],list,true,"11");
        }
        
        System.out.println("导出餐饮甜品 生成excel 表格完毕");
    }
    
    /**
     * 读取连接
     * @return [参数说明]
     * 
     * @return Connection [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public  Connection getConn()
    {
        // 驱动程序名
        String driver = "com.mysql.jdbc.Driver";
        Connection conn = null;
        
        try
        {
            // 加载驱动程序
            Class.forName(driver);
            
            // 连续数据库
            conn = DriverManager.getConnection(URL, USER, PWD);
            
            if (!conn.isClosed())
                System.out.println("Succeeded connecting to the Database!");
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        
        return conn;
    }
    
    public List<String> exportBrandShopByType2(String types,String headName)
    {
        String sql ="";
        StringBuffer sb = new StringBuffer();
        ResultSet res = null;
        Connection conn = null;
        Statement statement = null;
        
        sb.append(" select  substring_index(s.types,'|',1) gdtypes,s.branch_name,r.cgtype1,r.cgtype2,r.cgtype3,b.name,b.eng_name ")
        .append("  from cg_mid_poi_shop s ,cg_mid_poi_type t,cg_zs_mapping_products r,cg_brand b ")
        .append("  where s.id = t.brand_id and s.brand_id = b.id and b.mark = 1 ")
        .append("  and t.`type` in ({types}) ")
        .append("  and s.brand_id > 0   ")
        .append("  and s.mid = r.id ")
        .append("  order by t.type,char_length(trim(s.branch_name)) desc,s.branch_name  ");
        
         sql = sb.toString();
         sql = sql.replace("{types}", types);
         System.out.println(sql);
         List<String> list = new ArrayList<String>();
         
        try
        {
            
            conn = getConn();
            
            statement = conn.createStatement();
            res = statement.executeQuery(sql);
            
            while (null != res && res.next())
            {
                StringBuilder sb1 = new StringBuilder();
                
                sb1.append(res.getString("gdtypes")).append(",")
                .append(res.getString("branch_name")).append(",")
                .append(res.getString("cgtype1")).append(",")
                .append(res.getString("cgtype2")).append(",")
                .append(res.getString("cgtype3")).append(",")
                .append(res.getString("name")).append(",")
                .append(res.getString("eng_name"));
                
                list.add(sb1.toString());
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            closeStream(conn);
        }
        
        return list;
        
    }
    
    public List<String> exportSubTableBrandShopByType2(String types,String headName,String shopSubtablename,String shoptype2SubTableName)
    {
        String sql ="";
        StringBuffer sb = new StringBuffer();
        ResultSet res = null;
        Connection conn = null;
        Statement statement = null;
        
        sb.append(" select b.name,b.eng_name, s.branch_name, s.address")
        .append("  from {shopSubtablename} s ,{shoptype2SubTableName} t,cg_brand b ")
        .append("  where s.id = t.shop_id and s.brand_id = b.id and b.mark = 1 ")
        .append("  and t.type_id in ({types}) ")
        .append("  and s.brand_id > 0   ")
        .append("  order by t.type_id,char_length(trim(b.name)) desc,b.name,char_length(trim(b.eng_name)) desc,b.eng_name  ");
        
         sql = sb.toString();
         sql = sql.replace("{types}", types);
         sql = sql.replace("{shopSubtablename}", shopSubtablename);
         sql = sql.replace("{shoptype2SubTableName}", shoptype2SubTableName);
         System.out.println(sql);
         List<String> list = new ArrayList<String>();
         
        try
        {
            
            conn = getConn();
            
            statement = conn.createStatement();
            res = statement.executeQuery(sql);
            
            while (null != res && res.next())
            {
                StringBuilder sb1 = new StringBuilder();
                
                sb1.append(res.getString("name")).append(",")
                .append(res.getString("eng_name")).append(",")
                .append(res.getString("branch_name")).append(",")
                .append(res.getString("address"));
                
                list.add(sb1.toString());
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            closeStream(conn);
        }
        
        return list;
        
    }
    
    
    public List<String> exportShopByKeywordAndType2(String types,String keyword,String excludekeyword,String headName)
    {
        String sql ="";
        StringBuffer sb = new StringBuffer();
        ResultSet res = null;
        Connection conn = null;
        Statement statement = null;
        
        sb.append(" select substring_index(s.types,'|',1) gdtypes,s.branch_name,r.cgtype1,r.cgtype2,r.cgtype3,'' name,'' eng_name  ")
        .append("  from cg_mid_poi_shop s ,cg_mid_poi_type t,cg_zs_mapping_products r ")
        .append("  where s.id = t.brand_id ")
        .append("  and t.`type` in ({types}) ")
        .append("  and s.brand_id = 0   ")
        .append("  and 1=1   ")
         .append(" and ( 2=2 )  ")
        .append("  and s.mid = r.id ")
        .append("  order by t.type,char_length(trim(s.branch_name)) desc,s.branch_name  ");
        
         sql = sb.toString();
         sql = sql.replace("{types}", types);
         
         //替换like部分
         if(null != keyword && !"".equals(keyword.trim()))
         {
             String [] includekeywords = keyword.split(",");
             String sqlLike = "";
             int i = 0;
             
             for(String str : includekeywords)
             {
                i++;
                
                if(i == 1)
                {
                    sqlLike = " ( s.branch_name  like '%" + keyword + "%' ";
                }
                else
                {
                    sqlLike = sqlLike + " or s.branch_name  like '%" + str + "%' ";
                }
             }
             
             if(null != sqlLike && !"".equals(sqlLike.trim()))
             {
                 sqlLike = sqlLike + ")";
                 
                 sql = sql.replace("2=2", sqlLike);
             }
         }
         
         //替换not like部分
         if(null != excludekeyword && !"".equals(excludekeyword.trim()))
         {
             String [] excludekeywords = excludekeyword.split(",");
             String sqlNotLike = "";
             int i = 0;
             
             for(String str : excludekeywords)
             {
                i++;
                
                if(i == 1)
                {
                    sqlNotLike = " s.branch_name not like '%" + str + "%' ";
                }
                else
                {
                    sqlNotLike = sqlNotLike + " and s.branch_name not like '%" + str + "%' ";
                }
             }
             
             if(null != sqlNotLike && !"".equals(sqlNotLike.trim()))
             {
                 sql = sql.replace("1=1", sqlNotLike);
             }
         }
         
         System.out.println(sql);
         List<String> list = new ArrayList<String>();
         
        try
        {
            conn = getConn();
            
            statement = conn.createStatement();
            res = statement.executeQuery(sql);
            
            while (null != res && res.next())
            {
                StringBuilder sb1 = new StringBuilder();
                
                sb1.append(res.getString("gdtypes")).append(",")
                .append(res.getString("branch_name")).append(",")
                .append(res.getString("cgtype1")).append(",")
                .append(res.getString("cgtype2")).append(",")
                .append(res.getString("cgtype3")).append(",")
                .append(res.getString("name")).append(",")
                .append(res.getString("eng_name"));
                
                list.add(sb1.toString());
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            closeStream(conn);
        }
        
        return list;
        
    }
    
    
    public List<String> exportSubTableShopByKeywordAndType2(String types,String keyword,String excludekeyword,String headName,String shopSubtablename,String shoptype2SubTableName)
    {
        String sql ="";
        StringBuffer sb = new StringBuffer();
        ResultSet res = null;
        Connection conn = null;
        Statement statement = null;
        
        sb.append(" select s.branch_name,s.address  ")
        .append("  from {shopSubtablename} s ,{shoptype2SubTableName} t ")
        .append("  where s.id = t.shop_id ")
        .append("  and t.type_id in ({types}) ")
        .append("  and 1=1   ")
         .append(" and ( 2=2 )  ")
        .append("  order by t.type_id,char_length(trim(s.branch_name)) desc,s.branch_name  ");
        
         sql = sb.toString();
         sql = sql.replace("{types}", types);
         sql = sql.replace("{shopSubtablename}", shopSubtablename);
         sql = sql.replace("{shoptype2SubTableName}", shoptype2SubTableName);
         
         //替换like部分
         if(null != keyword && !"".equals(keyword.trim()))
         {
             String [] includekeywords = keyword.split(",");
             String sqlLike = "";
             int i = 0;
             
             for(String str : includekeywords)
             {
                i++;
                
                if(i == 1)
                {
                    sqlLike = " ( s.branch_name  like '%" + keyword + "%' ";
                }
                else
                {
                    sqlLike = sqlLike + " or s.branch_name  like '%" + str + "%' ";
                }
             }
             
             if(null != sqlLike && !"".equals(sqlLike.trim()))
             {
                 sqlLike = sqlLike + ")";
                 
                 sql = sql.replace("2=2", sqlLike);
             }
         }
         
         //替换not like部分
         if(null != excludekeyword && !"".equals(excludekeyword.trim()))
         {
             String [] excludekeywords = excludekeyword.split(",");
             String sqlNotLike = "";
             int i = 0;
             
             for(String str : excludekeywords)
             {
                i++;
                
                if(i == 1)
                {
                    sqlNotLike = " s.branch_name not like '%" + str + "%' ";
                }
                else
                {
                    sqlNotLike = sqlNotLike + " and s.branch_name not like '%" + str + "%' ";
                }
             }
             
             if(null != sqlNotLike && !"".equals(sqlNotLike.trim()))
             {
                 sql = sql.replace("1=1", sqlNotLike);
             }
         }
         
         System.out.println(sql);
         List<String> list = new ArrayList<String>();
         
        try
        {
            conn = getConn();
            
            statement = conn.createStatement();
            res = statement.executeQuery(sql);
            
            while (null != res && res.next())
            {
                StringBuilder sb1 = new StringBuilder();
                
                sb1.append(res.getString("branch_name")).append(",")
                .append(res.getString("address"));
                
                list.add(sb1.toString());
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            closeStream(conn);
        }
        
        return list;
        
    }
    
    /**
     * 写文件
     * <功能详细描述>
     * @param list
     * @param filePath [参数说明]
     * writeType false 覆盖 true 追加
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public void writeFile(List <String>list,String filePath,boolean writeType)
    {
        //写文件
        BufferedWriter write = null;
        
        try
        {
            write = new BufferedWriter(new FileWriter(filePath,writeType));
            
            for(String typeinfo : list )
            {
                write.write(typeinfo);
                write.write("\n");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            closeStream(write);
        }
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
     * 去除字符串中的空格、回车、换行符、制表符
     * <功能详细描述>
     * @param str
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static String replaceComma(String str)
    {
        String dest = "";
        
        if (str != null && !str.trim().equals(""))
        {
            dest = str.replaceAll(",", "");
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
    public static String replaceSpecial(String str)
    {
        String dest = "";
        
        if (str != null && !str.trim().equals(""))
        {
            dest = str.replaceAll("\\\\", "");
            dest = dest.replaceAll("/", "");
        }
        
        return dest;
    }
    
    
    
    
    public List<String> export12Type(String type1)
    {
        String sql ="";
        StringBuffer sb = new StringBuffer();
        ResultSet res = null;
        Connection conn = null;
        Statement statement = null;
        
        sb.append(" select r.name onename,s.name towname,r.id oneid,s.id twoid from cg_shop_type s ,cg_shop_type_relation r ")
        .append(" where ")
        .append("    (  ")
        .append("   locate(concat(',',s.id,','),r.child_ids) > 0 ")
        .append("         or ")
        .append("         locate(concat('A',s.id,','),concat('A',r.child_ids)) > 0 ")
        .append("         or  ")
        .append("         locate(concat(',',s.id,'A'),concat(r.child_ids,'A')) > 0   ")               
        .append("   )     ")
        .append(" and s.mark = 1 ")
        .append(" and r.id = {type1id} ")
        .append("  order by r.name ");
        
         sql = sb.toString();
         sql = sql.replace("{type1id}", type1);
         System.out.println(sql);
         List<String> list = new ArrayList<String>();
         
        try
        {
            conn = getConn();
            
            statement = conn.createStatement();
            res = statement.executeQuery(sql);
            
            while (null != res && res.next())
            {
                StringBuilder sb1 = new StringBuilder();
                
                sb1.append(replaceSpecial(res.getString("onename"))).append("-")
                .append(replaceSpecial(res.getString("towname"))).append(",")
                .append(res.getString("twoid"));
                
                list.add(sb1.toString());
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            closeStream(conn);
        }
        
        return list;
        
    }
    
    public void closeStream(Object obj)
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
        if (obj instanceof FileOutputStream)
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
