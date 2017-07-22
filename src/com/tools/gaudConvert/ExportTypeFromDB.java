package com.tools.gaudConvert;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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



public class ExportTypeFromDB
{
    
    public static String URL ="jdbc:mysql://61.155.8.199:3306/zhaoshang";
    public static String USER = "cg001";
    public static String PWD ="cgdb2015zaqXSW";
    public static String path = "E:\\ysh\\高德\\翻译\\翻译规则数据\\";
    //public static String URL ="jdbc:mysql://127.0.0.1:3306/chengge";
    //public static String USER = "root";
    //public static String PWD ="888888";
    
    //mysql://root:qwer1456ZXC56712@61.155.8.175:3306/chengge_test',
    
    public static void main(String [] argv)
    {
        ExportTypeFromDB exp = new ExportTypeFromDB();
        exp.run();
        
        
    }
    
    public void run()
    {
        System.out.println("从线上导出品牌和业态，城市编码数据");
        
        List<String> list = export12Type();
        writeFile(list, path + "cgtype12.csv");
        list = export123Type();
        writeFile(list, path + "cgtype123.csv");
        list = exportBrand12();
        writeFile(list, path + "brand-12.csv");
        list = exportBrand12EngName();
        writeFile(list, path + "brand-12engname.csv");
        list = exportBrand12OtherName();
        writeFile(list, path + "brand-12othername.csv");
        list = exportBrand123();
        writeFile(list, path + "brand-123.csv");
        list = exportBrand123EngName();
        writeFile(list, path + "brand-123engname.csv");
        list = exportBrand123OtherName();
        writeFile(list, path + "brand-123othername.csv");
        list = exportAllRule();
        writeFile(list, path + "trans.rule.old.csv");
        list = exportGdType123();
        writeFile(list, path + "gdtype.csv");
        list = exportcitycode();
        writeFile(list, path + "citycode.csv");
        
        System.out.println("从线上导出品牌和业态，城市编码数据完毕");
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
    
    public List<String> export12Type()
    {
        String sql ="";
        StringBuffer sb = new StringBuffer();
        ResultSet res = null;
        Connection conn = null;
        Statement statement = null;
        
        sb.append(" select r.name onename,s.name towname,r.id oneid,s.id twoid from cg_shop_type s ,cg_shop_type_relation r \n ")
        .append(" where \n ")
        .append("    (  ")
        .append("   locate(concat(',',s.id,','),r.child_ids) > 0 \n ")
        .append("         or \n ")
        .append("         locate(concat('A',s.id,','),concat('A',r.child_ids)) > 0 \n ")
        .append("         or  \n ")
        .append("         locate(concat(',',s.id,'A'),concat(r.child_ids,'A')) > 0   \n ")               
        .append("   )     \n ")
        .append(" and s.mark = 1 \n ")
        .append("  order by r.name \n ");
        
         sql = sb.toString();
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
                
                sb1.append(res.getString("onename")).append(",")
                .append(res.getString("towname")).append(",")
                .append(res.getString("oneid")).append(",")
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
    
    
    public List<String> export123Type()
    {
        String sql ="";
        StringBuffer sb = new StringBuffer();
        ResultSet res = null;
        Connection conn = null;
        Statement statement = null;
        
        sb.append(" select t3.name1,t3.name2,t1.name name3,t3.id1,t3.id2,t1.id id3 from cg_brand_style t1,\n ")
        .append(" ( ")
        .append("     select r.name name1,s.name name2,s.id id2,r.id id1 from cg_shop_type s ,cg_shop_type_relation r \n ")
        .append("    where \n ")
        .append("    ( \n ")
        .append("         locate(concat(',',s.id,','),r.child_ids) > 0 \n ")
        .append("           or  \n ")
        .append("           locate(concat('A',s.id,','),concat('A',r.child_ids)) > 0 \n ")
        .append("           or \n ")
        .append("          locate(concat(',',s.id,'A'),concat(r.child_ids,'A')) > 0     \n ")
        .append("     ) \n ")
        .append("    and s.mark = 1 and r.mark = 1 \n ")
        .append(" order by r.name \n ")
        .append(" ) t3 \n ")
        .append(" where t1.type = t3.id2 \n ")
        .append(" order by id1,id2,id3  \n ");
        
        
         sql = sb.toString();
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
                sb1.append(res.getString("name1")).append(",")
                .append(res.getString("name2")).append(",")
                .append(res.getString("name3")).append(",")
                .append(res.getString("id1")).append(",")
                .append(res.getString("id2")).append(",")
                .append(res.getString("id3"));
                
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
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public void writeFile(List <String>list,String filePath)
    {
        //写文件
        BufferedWriter write = null;
        
        try
        {
            write = new BufferedWriter(new FileWriter(filePath,false));
            
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
    
    
    public List<String> exportBrand12()
    {

        String sql ="";
        StringBuffer sb = new StringBuffer();
        ResultSet res = null;
        Connection conn = null;
        Statement statement = null;
        
        sb.append(" select t1.oneid,t1.twoid,t1.onename,t1.towname,REPLACE(REPLACE(b.name, CHAR(10), '') , CHAR(13), '') name,b.id \n ")
                .append(" from cg_brand_type  t,  \n ")
                .append(" cg_brand b,  \n ")
                .append(" (  \n ")
                .append("    select r.name onename,s.name towname,r.id oneid,s.id twoid from cg_shop_type s ,cg_shop_type_relation r  \n ")
                .append("   where  \n ")
                .append("   (  \n ")
                .append("      locate(concat(',',s.id,','),r.child_ids) > 0  \n")
                .append("         or   \n ")
                .append("        locate(concat('A',s.id,','),concat('A',r.child_ids)) > 0  \n")
                .append("         or  \n ")
                .append("         locate(concat(',',s.id,'A'),concat(r.child_ids,'A')) > 0    \n")
                .append("    )  \n ")
                .append("   and s.mark = 1 and r.mark = 1  \n")
                .append("    order by r.name  \n ")
                .append(" ) t1  \n ")
                .append(" where b.id = t.brand_id  \n")
                .append(" and t.type =t1.twoid  \n ")
                .append(" and t.mark = 1 and b.mark = 1  \n")
                .append(" and char_length(trim(b.name))> 0  \n")
                .append(" order by t1.oneid,t1.twoid,char_length(trim(b.name)) desc  \n ");
        
         sql = sb.toString();
         List<String> list = new ArrayList<String>();
         System.out.println(sql);
         
        try
        {
            conn = getConn();
            
            statement = conn.createStatement();
            res = statement.executeQuery(sql);
            
            while (null != res && res.next())
            {
                StringBuilder sb1 = new StringBuilder();
                sb1.append(res.getString("oneid")).append(",")
                .append(res.getString("twoid")).append(",")
                .append(res.getString("onename")).append(",")
                .append(res.getString("towname")).append(",")
                .append(replaceComma(replaceEnterSpace(res.getString("name")))).append(",")
                .append(res.getString("id"));
                
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
    
    
    public List<String> exportBrand12EngName()
    {

        String sql ="";
        StringBuffer sb = new StringBuffer();
        ResultSet res = null;
        Connection conn = null;
        Statement statement = null;
        
        sb.append(" select t1.oneid,t1.twoid,t1.onename,t1.towname,REPLACE(REPLACE(b.eng_name, CHAR(10), ''), CHAR(13), '') eng_name,b.id \n ")
                .append(" from cg_brand_type  t,  \n ")
                .append(" cg_brand b,  \n ")
                .append(" (  \n ")
                .append("    select r.name onename,s.name towname,r.id oneid,s.id twoid from cg_shop_type s ,cg_shop_type_relation r  \n ")
                .append("   where  \n ")
                .append("   (  \n ")
                .append("      locate(concat(',',s.id,','),r.child_ids) > 0  \n ")
                .append("         or   \n ")
                .append("        locate(concat('A',s.id,','),concat('A',r.child_ids)) > 0  \n ")
                .append("         or  \n ")
                .append("         locate(concat(',',s.id,'A'),concat(r.child_ids,'A')) > 0    \n ")
                .append("    )  \n ")
                .append("   and s.mark = 1 and r.mark = 1  \n ")
                .append("    order by r.name  \n ")
                .append(" ) t1  \n ")
                .append(" where b.id = t.brand_id \n  ")
                .append(" and t.type =t1.twoid  \n ")
                .append(" and t.mark = 1 and b.mark = 1  \n ")
                .append(" and char_length(trim(b.eng_name))> 0  \n")
                .append(" order by t1.oneid,t1.twoid,char_length(trim(b.eng_name)) desc  \n ");
        
         sql = sb.toString();
         List<String> list = new ArrayList<String>();
         System.out.println(sql);
        try
        {
            conn = getConn();
            
            statement = conn.createStatement();
            res = statement.executeQuery(sql);
            
            while (null != res && res.next())
            {
                StringBuilder sb1 = new StringBuilder();
                sb1.append(res.getString("oneid")).append(",")
                .append(res.getString("twoid")).append(",")
                .append(res.getString("onename")).append(",")
                .append(res.getString("towname")).append(",")
                .append(replaceComma(replaceEnter(res.getString("eng_name")))).append(",")
                .append(res.getString("id"));
                
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
    
    public List<String> exportBrand12OtherName()
    {

        String sql ="";
        StringBuffer sb = new StringBuffer();
        ResultSet res = null;
        Connection conn = null;
        Statement statement = null;
        
        sb.append(" select t1.oneid,t1.twoid,t1.onename,t1.towname,REPLACE(REPLACE(b.other_name, CHAR(10), ''), CHAR(13), '') other_name,b.id \n ")
                .append(" from cg_brand_type  t,  \n ")
                .append(" cg_brand b,  \n ")
                .append(" (  \n ")
                .append("    select r.name onename,s.name towname,r.id oneid,s.id twoid from cg_shop_type s ,cg_shop_type_relation r  \n ")
                .append("   where  \n ")
                .append("   (  \n ")
                .append("      locate(concat(',',s.id,','),r.child_ids) > 0  \n ")
                .append("         or   \n ")
                .append("        locate(concat('A',s.id,','),concat('A',r.child_ids)) > 0  \n ")
                .append("         or  \n ")
                .append("         locate(concat(',',s.id,'A'),concat(r.child_ids,'A')) > 0    \n ")
                .append("    )  \n ")
                .append("   and s.mark = 1 and r.mark = 1 \n  ")
                .append("    order by r.name  \n ")
                .append(" ) t1 \n  ")
                .append(" where b.id = t.brand_id  \n ")
                .append(" and t.type =t1.twoid  \n ")
                .append(" and t.mark = 1 and b.mark = 1  \n ")
                .append(" and char_length(trim(b.other_name))> 0  \n")
                .append(" order by t1.oneid,t1.twoid,char_length(trim(b.other_name)) desc \n  ");
        
         sql = sb.toString();
         List<String> list = new ArrayList<String>();
         System.out.println(sql);
        try
        {
            conn = getConn();
            
            statement = conn.createStatement();
            res = statement.executeQuery(sql);
            
            while (null != res && res.next())
            {
                StringBuilder sb1 = new StringBuilder();
                sb1.append(res.getString("oneid")).append(",")
                .append(res.getString("twoid")).append(",")
                .append(res.getString("onename")).append(",")
                .append(res.getString("towname")).append(",")
                .append(replaceComma(replaceEnter(res.getString("other_name")))).append(",")
                .append(res.getString("id"));
                
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
    
    public List<String> exportBrand123()
    {

        String sql ="";
        StringBuffer sb = new StringBuffer();
        ResultSet res = null;
        Connection conn = null;
        Statement statement = null;
        
        sb.append("  select tt.id1 type1id,tt.id2 type2id,tt.id3 type3id,tt.name1 type1name,tt.name2 type2name,tt.name3 type3name, \n " )
                .append(" REPLACE(REPLACE(b.name, CHAR(10), ''), CHAR(13), '')  name,b.id  \n ")
                .append("  from cg_brand_style_relation  t,  \n ")
                .append("  cg_brand b,  \n ")
                .append("  (  \n ")
                .append("      select t3.name1,t3.name2,t1.name name3,t3.id1,t3.id2,t1.id id3 from cg_brand_style t1,  \n ")
                .append("    (  \n ")
                .append("       select r.name name1,s.name name2,s.id id2,r.id id1 from cg_shop_type s ,cg_shop_type_relation r  \n ")
                .append("       where   \n ")
                .append("     (  \n ")
                .append("        locate(concat(',',s.id,','),r.child_ids) > 0  \n ")
                .append("          or  \n  ")
                .append("         locate(concat('A',s.id,','),concat('A',r.child_ids)) > 0  \n ")
                .append("          or  \n ")
                .append("         locate(concat(',',s.id,'A'),concat(r.child_ids,'A')) > 0     \n ")
                .append("     )  \n ")
                .append("     and s.mark = 1 and r.mark = 1  \n ")
                .append("   ) t3  \n ")
                .append("   where t1.type = t3.id2 \n  ")
                .append("   ) tt  \n ")
                .append("  where b.id = t.brand_id  \n ")
                .append("  and t.style_id =tt.id3 \n  ")
                .append("  and t.mark = 1 and b.mark = 1  \n ")
                .append("  and char_length(trim(b.name))> 0  \n")
                .append("  order by tt.id1,tt.id2,tt.id3,char_length(trim(b.name)) desc \n  ");
        
        
         sql = sb.toString();
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
                sb1.append(res.getString("type1id")).append(",")
                .append(res.getString("type2id")).append(",")
                .append(res.getString("type3id")).append(",")
                .append(res.getString("type1name")).append(",")
                .append(res.getString("type2name")).append(",")
                .append(res.getString("type3name")).append(",")
                .append(replaceComma(replaceEnterSpace(res.getString("name")))).append(",")
                .append(res.getString("id"));
                
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
    
    public List<String> exportBrand123EngName()
    {

        String sql ="";
        StringBuffer sb = new StringBuffer();
        ResultSet res = null;
        Connection conn = null;
        Statement statement = null;
        
        sb.append("  select tt.id1 type1id,tt.id2 type2id,tt.id3 type3id,tt.name1 type1name,tt.name2 type2name,tt.name3 type3name, \n " )
                .append(" REPLACE(REPLACE(b.eng_name, CHAR(10), ''), CHAR(13), '') eng_name,b.id  \n ")
                .append("  from cg_brand_style_relation  t,  \n ")
                .append("  cg_brand b,  \n ")
                .append("  (  \n ")
                .append("      select t3.name1,t3.name2,t1.name name3,t3.id1,t3.id2,t1.id id3 from cg_brand_style t1,  \n ")
                .append("    (  \n ")
                .append("       select r.name name1,s.name name2,s.id id2,r.id id1 from cg_shop_type s ,cg_shop_type_relation r  \n ")
                .append("       where   \n ")
                .append("     (  \n ")
                .append("        locate(concat(',',s.id,','),r.child_ids) > 0  \n ")
                .append("          or   \n ")
                .append("         locate(concat('A',s.id,','),concat('A',r.child_ids)) > 0  \n ")
                .append("          or  \n ")
                .append("         locate(concat(',',s.id,'A'),concat(r.child_ids,'A')) > 0    \n  ")
                .append("     )  \n ")
                .append("     and s.mark = 1 and r.mark = 1 \n  ")
                .append("   ) t3 \n  ")
                .append("   where t1.type = t3.id2 \n  ")
                .append("   ) tt  \n ")
                .append("  where b.id = t.brand_id \n  ")
                .append("  and t.style_id =tt.id3  \n ")
                .append("   and t.mark = 1 and b.mark = 1  \n ")
                .append("  and char_length(trim(b.eng_name))> 0  \n")
                .append("  order by tt.id1,tt.id2,tt.id3,char_length(trim(b.eng_name)) desc \n  ");
        
        
         sql = sb.toString();
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
                sb1.append(res.getString("type1id")).append(",")
                .append(res.getString("type2id")).append(",")
                .append(res.getString("type3id")).append(",")
                .append(res.getString("type1name")).append(",")
                .append(res.getString("type2name")).append(",")
                .append(res.getString("type3name")).append(",")
                .append(replaceComma(replaceEnter(res.getString("eng_name")))).append(",")
                .append(res.getString("id"));
                
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
    
    public List<String> exportBrand123OtherName()
    {

        String sql ="";
        StringBuffer sb = new StringBuffer();
        ResultSet res = null;
        Connection conn = null;
        Statement statement = null;
        
        sb.append("  select tt.id1 type1id,tt.id2 type2id,tt.id3 type3id,tt.name1 type1name,tt.name2 type2name,tt.name3 type3name, \n " )
                .append(" REPLACE(REPLACE(b.other_name, CHAR(10), ''), CHAR(13), '') other_name,b.id \n  ")
                .append("  from cg_brand_style_relation  t,  \n ")
                .append("  cg_brand b,  \n ")
                .append("  (  \n ")
                .append("      select t3.name1,t3.name2,t1.name name3,t3.id1,t3.id2,t1.id id3 from cg_brand_style t1, \n  ")
                .append("    (  \n ")
                .append("       select r.name name1,s.name name2,s.id id2,r.id id1 from cg_shop_type s ,cg_shop_type_relation r  \n ")
                .append("       where   \n ")
                .append("     (  \n ")
                .append("        locate(concat(',',s.id,','),r.child_ids) > 0  \n ")
                .append("          or  \n  ")
                .append("         locate(concat('A',s.id,','),concat('A',r.child_ids)) > 0  \n ")
                .append("          or  \n ")
                .append("         locate(concat(',',s.id,'A'),concat(r.child_ids,'A')) > 0    \n  ")
                .append("     )  \n ")
                .append("     and s.mark = 1 and r.mark = 1  \n ")
                .append("   ) t3  \n ")
                .append("   where t1.type = t3.id2  \n ")
                .append("   ) tt  \n ")
                .append("  where b.id = t.brand_id \n  ")
                .append("  and t.style_id =tt.id3  \n ")
                .append("   and t.mark = 1 and b.mark = 1 \n  ")
                .append("  and char_length(trim(b.other_name))> 0  \n")
                .append("  order by tt.id1,tt.id2,tt.id3,char_length(trim(b.other_name)) desc  \n ");
        
        
         sql = sb.toString();
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
                sb1.append(res.getString("type1id")).append(",")
                .append(res.getString("type2id")).append(",")
                .append(res.getString("type3id")).append(",")
                .append(res.getString("type1name")).append(",")
                .append(res.getString("type2name")).append(",")
                .append(res.getString("type3name")).append(",")
                .append(replaceComma(replaceEnterSpace(res.getString("other_name")))).append(",")
                .append(res.getString("id"));
                
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
    
    
    public List<String> exportAllRule()
    {
        String sql ="";
        StringBuffer sb = new StringBuffer();
        ResultSet res = null;
        Connection conn = null;
        Statement statement = null;
        
        sb.append(" select p.id,p.type_1 gdtype1,p.type_2 gdtype2,p.type_3 gdtype3, \n ")
        .append(" p.wd keyword,p.chengge_1_type cgtype1, p.chengge_2_type cgtype2,p.chengge_3_type cgtype3, \n ")
        .append(" p.cgtype1 cgtype1name,p.cgtype2 cgtype2name,p.cgtype3 cgtype3name, \n ")
        .append(" p.gdtype1 gdtype1name,p.gdtype2 gdtype2name,p.gdtype3 gdtype3name,p.mybrand \n ")
        .append(" from cg_zs_mapping_products p order by p.type_1,p.id  \n ");
        
         sql = sb.toString();
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
                
                sb1.append(res.getString("id")).append(",")
                .append(res.getString("gdtype1")).append(",")
                .append(res.getString("gdtype2")).append(",")
                .append(res.getString("gdtype3")).append(",")
                .append(res.getString("keyword")).append(",")
                .append(res.getString("cgtype1")).append(",")
                .append(res.getString("cgtype2")).append(",")
                .append(res.getString("cgtype3")).append(",")
                .append(res.getString("cgtype1name")).append(",")
                .append(res.getString("cgtype2name")).append(",")
                .append(res.getString("cgtype3name")).append(",")
                .append(res.getString("gdtype1name")).append(",")
                .append(res.getString("gdtype2name")).append(",")
                .append(res.getString("gdtype3name")).append(",")
                .append(res.getString("mybrand"));
                
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
    
    public List<String> exportGdType123()
    {
        String sql ="";
        StringBuffer sb = new StringBuffer();
        ResultSet res = null;
        Connection conn = null;
        Statement statement = null;
        
        sb.append("  select id,pid,ppid,allname,ppname,pname,name from  \n ")
                .append("(  \n ")
                .append(" select t3.id,t3.pid,t2.pid ppid,  \n ")
                .append("   concat(t2.name,';', t3.name) allname, \n ")
                .append("   t2.ppname,t2.pname,t3.name,1  \n ")
                .append("    from cg_poi_type t3 left join \n ")
                .append("  (select t.id,t.pid,concat(t1.name,';',t.name) name ,t1.name ppname,t.name pname \n ")
                .append("     from cg_poi_type t left join cg_poi_type t1 on t1.id = t.pid where t.level = 1) t2 \n ")
                .append("   on t2.id = t3.pid \n ")
                .append("   where t3.level = 2 \n ")
                .append("   order by id \n ")
                .append(" ) t4 \n ")
                .append(" order by t4.ppid,t4.pid,t4.id \n ");
        
         sql = sb.toString();
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
                .append(res.getString("pname")).append(",")
                .append(res.getString("ppname")).append(",")
                .append(res.getString("id")).append(",")
                .append(res.getString("pid")).append(",")
                .append(res.getString("ppid"));
                
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
    
    public List<String> exportcitycode()
    {

        String sql ="";
        StringBuffer sb = new StringBuffer();
        ResultSet res = null;
        Connection conn = null;
        Statement statement = null;
        
        sb.append(" select c.id,c.upid,c.name,c.code,c.level,c.telecode from cg_city c order by c.upid,c.level ");
        
         sql = sb.toString();
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
                sb1.append(res.getString("id")).append(",")
                .append(res.getString("upid")).append(",")
                .append(res.getString("name")).append(",")
                .append(res.getString("code")).append(",")
                .append(res.getString("level")).append(",")
                .append(res.getString("telecode"));
                
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
    }
    
    
}
