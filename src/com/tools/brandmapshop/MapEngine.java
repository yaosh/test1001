package com.tools.brandmapshop;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.tools.TimeTools;

public class MapEngine
{
    private static String URL ="jdbc:mysql://61.155.8.203:3306/shop_chengge?useUnicode=true&characterEncoding=UTF-8";
    private static String USER = "zc001";
    private static String PWD ="zcdbBNMhjk0630oo1";
    private static String RULE_PATH ="E:\\ysh\\高德\\品牌和商家映射\\";
    private static String EXE_RECORD_FILE ="EXE_RECORD_FILE.txt";
    private static String RULE_FILE ="brandmapshoprules.csv";
    private static Map<String ,RuleBean> RULE_MAP = new HashMap<String ,RuleBean>();
    
    //南京220 南平 55 厦门 60 江苏 16  福建 4
    private  String province_name = "jiangsu";
    private  String province_code ="16";
    private  String city_name = "nanjing";
    private  String city_code ="220";
    
    private  String cg_sub_table_shop = "cg_shop_" + province_name;
    private  String cg_sub_table_shop_type2 = "cg_shop_type2_" + province_name;
    private  String cg_sub_table_shop_type3 = "cg_shop_type3_" + province_name;
    //private static String CG_SUB_TABLR_SHOP_STAT = "cg_shop_stat_" + PROVINCE_NAME;
    //private static String CG_SHOP_STAT = "cg_shop_stat_" + PROVINCE_NAME;
    
    //1 分表统计关  2 分表统计开
    private int sub_table_switch =1;
    
    private static int NOT_SUB_TABLE =1;
    private static int SUB_TABLE =2;
    
    /** 
     * <功能详细描述>
     * @param args [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static void main(String[] args)
    {
        MapEngine engine = new MapEngine(SUB_TABLE,"jiangsu","16","nanjing","220");
        System.out.println("启动数据关联工厂");
        engine.runLoop();
        System.out.println("关闭数据关联工厂");
    }
    
     public MapEngine(int subTableFlag,String provinceName,String provincecode,String cityname,String cityCode)
     {
         sub_table_switch = subTableFlag;
         province_name = provinceName;
         province_code = provincecode;
         city_name = cityname;
         city_code = cityCode;
         
         cg_sub_table_shop = "cg_shop_" + province_name;
         cg_sub_table_shop_type2 = "cg_shop_type2_" + province_name;
         cg_sub_table_shop_type3 = "cg_shop_type3_" + province_name;
     }
    
    /**
     * 加载公式文件
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public void init()
    {
        loadRuleFromFile(RULE_PATH + RULE_FILE);
    }
    
    /**
     * 循环读取公式 
     * [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private void runLoop()
    {
        Connection conn = null;
        long time1 = System.currentTimeMillis();
        String oldRuleid = "";
        
        try
        {
            System.out.println("加载工厂数据");
            init();
            conn = getConn();
            System.out.println("加载工厂数据完毕");
            
            System.out.println("开始数据关联....");
            
            RuleBean ruleBean = getCurRuleFromFile(RULE_PATH + EXE_RECORD_FILE);
            
            while(null != ruleBean)
            {
                
                //避免死循环
                if(oldRuleid.equals(ruleBean.getRuleid()))
                {
                    break;
                }
                else
                {
                    oldRuleid = ruleBean.getRuleid();
                }
                
                long curRuleBegin = System.currentTimeMillis();
                System.out.println("公式:" + ruleBean.getRuleid()+" 开始时间:"+TimeTools.getDataByFormat("yyyyMMdd HH:mm:ss") );
                run(conn,sub_table_switch);
                long timeCur = System.currentTimeMillis();
                long curRuleperiod = (timeCur - curRuleBegin)/1000/60;
                System.out.println("公式:" + ruleBean.getRuleid() + " 耗时:" + curRuleperiod+"  总耗时："+ (timeCur - time1)/1000/60 + " 分.");
                
                //读取下一个公式
                getNextRule(ruleBean);
                
                ruleBean = getCurRuleFromFile(RULE_PATH + EXE_RECORD_FILE);
            }
           
            
            System.out.println("统一更新品牌商家的业态归属:");
            
            //统一更新品牌商家的业态归属 ，
            if(sub_table_switch == SUB_TABLE)
            {
                String deleteShopTypeSql = getSubTableDeleteShopType(city_code, cg_sub_table_shop, cg_sub_table_shop_type2);
                executesqlForUpdate(conn,deleteShopTypeSql);
                deleteShopTypeSql = getSubTableDeleteShopType(city_code, cg_sub_table_shop, cg_sub_table_shop_type3);
                executesqlForUpdate(conn,deleteShopTypeSql);
                
                String insertShopTypeSql = getSubTableInsertShopType(city_code, cg_sub_table_shop, cg_sub_table_shop_type2);
                executesqlForUpdate(conn,insertShopTypeSql);
                insertShopTypeSql = getSubTableInsertShopType(city_code, cg_sub_table_shop, cg_sub_table_shop_type3);
                executesqlForUpdate(conn,insertShopTypeSql);
            }
            else if (sub_table_switch == NOT_SUB_TABLE)
            {
                String deleteShopTypeSql = getDeleteShopType2();
                executesqlForUpdate(conn,deleteShopTypeSql);
                deleteShopTypeSql = getDeleteShopType3();
                executesqlForUpdate(conn,deleteShopTypeSql);
                
                String insertShopTypeSql = getInsertShopType2();
                executesqlForUpdate(conn,insertShopTypeSql);
                insertShopTypeSql = getInsertShopType3();
                executesqlForUpdate(conn,insertShopTypeSql);
                
            }
            
            
            System.out.println("统一更新品牌商家的业态归属完毕");
            
        }
        finally
        {
            closeStream(conn);
        }
        
        long time2 = System.currentTimeMillis();
        
        System.out.println("运行结束，总耗时:"+ (time2 - time1)/1000/60 + " 分.");
        
    }
    
    /**
     * 运行一个公式
     * @param conn [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private void run(Connection conn,int subTableFlag)
    {
        try
        {
            RuleBean rulebeanFromFile = getCurRuleFromFile(RULE_PATH + EXE_RECORD_FILE);
            
            //根据文件中的配置 读取公式的完整信息
            RuleBean rulebean = RULE_MAP.get(rulebeanFromFile.getRuleindex() + "_" + rulebeanFromFile.getRuleid());
            
            System.out.println("当前运行公式:" + rulebean.toString() );
            
            if(subTableFlag == NOT_SUB_TABLE)
            {
                //中文品牌名
                List<String> brandList = getBrandList(rulebean,conn);
                brandMapShopNotSub(rulebean,brandList,conn);
                
                //性能调优
                brandList.clear();
                //英文名
                brandList = getBrandEngNameList(rulebean,conn);
                brandMapShopNotSub(rulebean,brandList,conn);
                
                //性能调优
                brandList.clear();
                //别名
                brandList = getBrandOtherNameList(rulebean,conn);
                brandMapShopNotSub(rulebean,brandList,conn);
            }
            else
            {
                //中文品牌名
                List<String> brandList = getBrandList(rulebean,conn);
                brandBatchMapShop(rulebean,brandList,conn);
                
                //性能调优
                brandList.clear();
                //英文名
                brandList = getBrandEngNameList(rulebean,conn);
                brandBatchMapShop(rulebean,brandList,conn);
                
                //性能调优
                brandList.clear();
                //别名
                brandList = getBrandOtherNameList(rulebean,conn);
                brandBatchMapShop(rulebean,brandList,conn);
            }
            
        }
        finally
        {
            
        }
        
    }
    
    /**
     * 读取品牌列表 根据业态类别 按照中文名长度降序排列 空的不取
     * 
     * @param ruleBean
     * @param conn
     * @return [参数说明]
     * 
     * @return List<String> [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private List<String> getBrandList(RuleBean ruleBean,Connection conn)
    {
        List<String> brandList = new ArrayList<String>();
        String type2id ="";
        String type3id = "";
        
        
        if(notNullAndNot0(ruleBean.getCgBrand3Name()))
        {
            type3id = getType3idList(ruleBean.getCgBrand1Name(), ruleBean.getCgBrand2Name(), convertStrToList(ruleBean.getCgBrand3Name()),conn);
            typeidIsNullWarning(type3id,ruleBean);            
            brandList = getBrandListByType3List(type3id,conn);
            
        }
        else if(notNullAndNot0(ruleBean.getCgBrand2Name()))
        {
            type2id = getType2idList(ruleBean.getCgBrand1Name(), convertStrToList(ruleBean.getCgBrand2Name()),conn);
            typeidIsNullWarning(type2id,ruleBean);
            brandList = getBrandListByType2List(type2id,conn);
        }
        
        return brandList;
        
    }
    
    public List<String> convertStrToList(String str)
    {
        String [] tempArray = str.split("\\|");
        List<String> list = new ArrayList<String>();
        
        for(String temp : tempArray)
        {
            list.add(temp);
        }
        
        return list;
    }
    
    /**
     * 校验 如果读取业态类别id 失败  系统退出，请检查核对 
     * @param typeid
     * @param ruleBean [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public void typeidIsNullWarning(String typeid,RuleBean ruleBean)
    {
        if(!notNullAndNot0(typeid))
        {
            System.out.println(ruleBean.toString()+"\n typeid is null");
            System.exit(0);
        }
    }
    
    /**
     * 读取品牌列表 根据业态类别 按照英文名长度降序排列 空的不取
     * <功能详细描述>
     * @param ruleBean
     * @param conn
     * @return [参数说明]
     * 
     * @return List<String> [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private List<String> getBrandEngNameList(RuleBean ruleBean,Connection conn)
    {
        List<String> brandList = new ArrayList<String>();
        String type2id ="";
        String type3id = "";
        
        
        if(notNullAndNot0(ruleBean.getCgBrand3Name()))
        {
            type3id = getType3idList(ruleBean.getCgBrand1Name(), ruleBean.getCgBrand2Name(), convertStrToList(ruleBean.getCgBrand3Name()),conn);
            typeidIsNullWarning(type3id,ruleBean);
            brandList = getBrandEngNameListByType3List(type3id,conn);
        }
        else if(notNullAndNot0(ruleBean.getCgBrand2Name()))
        {
            type2id = getType2idList(ruleBean.getCgBrand1Name(), convertStrToList(ruleBean.getCgBrand2Name()),conn);
            typeidIsNullWarning(type2id,ruleBean);
            brandList = getBrandEngNameListByType2List(type2id,conn);
        }
        
        return brandList;
        
    }
    
    /**
     * 读取品牌列表 根据业态类别 按照别名长度降序排列 空的不取
     * @param ruleBean
     * @param conn
     * @return [参数说明]
     * 
     * @return List<String> [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private List<String> getBrandOtherNameList(RuleBean ruleBean,Connection conn)
    {
        List<String> brandList = new ArrayList<String>();
        String type2id ="";
        String type3id = "";
        
        if(notNullAndNot0(ruleBean.getCgBrand3Name()))
        {
            type3id = getType3idList(ruleBean.getCgBrand1Name(), ruleBean.getCgBrand2Name(), convertStrToList(ruleBean.getCgBrand3Name()),conn);
            typeidIsNullWarning(type3id,ruleBean);
            brandList = getBrandOtherNameListByType3List(type3id,conn);
        }
        else if(notNullAndNot0(ruleBean.getCgBrand2Name()))
        {
            type2id = getType2idList(ruleBean.getCgBrand1Name(), convertStrToList(ruleBean.getCgBrand2Name()),conn);
            typeidIsNullWarning(type2id,ruleBean);
            brandList = getBrandOtherNameListByType2List(type2id,conn);
        }
        
        return brandList;
        
    }
    
    
    
    /**
     * 核心程序 根据品牌中文名列表 关联公式中指定的业态下的商家名称
     * @param ruleBean
     * @param brandList
     * @param conn [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private void brandBatchMapShop(RuleBean ruleBean,List<String> brandList,Connection conn)
    {

        String typeTable = "";
        
        if(notNullAndNot0(ruleBean.getCgShop3Name()))
        {
            typeTable = cg_sub_table_shop_type3;
        }
        else if (notNullAndNot0(ruleBean.getCgShop2Name()) || notNullAndNot0(ruleBean.getCgShop1Name()))
        {
            typeTable = cg_sub_table_shop_type2;
        }
        
        List<String>  batchList = new ArrayList<String> ();
        int index = 0;
        
        for(String brand :brandList)
        {
            String sql = "";
            //String typesql = "";
            batchList.add(brand);
            index++;
            
            if(batchList.size() < 20 && index < brandList.size())
            {
                continue;
            }
            else if (batchList.size() == 20 || ( index ==brandList.size() && batchList.size() > 0))
            {
                sql = getSubTableBatchReplaceInsertSqlType(batchList, city_code,cg_sub_table_shop,typeTable);
                //typesql = getSubTableBatchReplaceShopType(batchList, city_code,cg_sub_table_shop,typeTable);
                
                //System.out.println(sql);
                //System.out.println(typesql);
                executesqlForUpdate(conn,sql);
                //executesqlForUpdate(conn,typesql);
                
                batchList.clear();
            }
        }
    }
    
    /**
     * 核心程序 根据品牌中文名列表 关联公式中指定的业态下的商家名称
     * @param ruleBean
     * @param brandList
     * @param conn [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private void brandMapShopNotSub(RuleBean ruleBean,List<String> brandList,Connection conn)
    {
    
        int flag = 0;
        
        //表达了这个公式是到二级业态还是三级业态
        if(notNullAndNot0(ruleBean.getCgShop3Name()))
        {
            flag  = 3;
        }
        else if (notNullAndNot0(ruleBean.getCgShop2Name()))
        {
            flag  = 2;
        }
        
        List<String>  batchList = new ArrayList<String> ();
        
        for(String brand :brandList)
        {
            String [] brandInfo = brand.split(",");
            String brandName = dealBrandName(brandInfo[0]);
            String brandId = brandInfo[1];
            //根据公式的业态等级，就知道这个品牌的业态等级了。
            //因为品牌列表是根据公式的业态等级查询出来的 要么到三级业态，要么到二级业态
            String typeid = brandInfo[2];
            
            String sql = "";
            batchList.add(brand);
            
            if(flag == 3)
            {
                sql = getReplaceShopTablebyType3SqlTpl(brandId, brandName, typeid,city_code);
            }
            else if (flag == 2 )
            {
              sql = getReplaceShopTableForType2SqlTpl(brandId, brandName, typeid,city_code);
            }
            
            executesqlForUpdate(conn,sql);
            
        }
    }
    
    
    private String dealBrandName(String srcBrandName)
    {
        if(null == srcBrandName)
        {
            return "";
        }
        
        String newBrandName = srcBrandName.replaceAll("'", "''");
        newBrandName = newBrandName.replaceAll("\\\\", "\\\\\\\\");
        newBrandName = newBrandName.replaceAll("%", "\\%");
        newBrandName = newBrandName.replaceAll("_", "\\_");
        //去空格匹配
        newBrandName = newBrandName.replaceAll(" ", "");
        newBrandName = newBrandName.trim();
        
        return newBrandName;
        
    }
    
    /**
     * 数据库关联操作 （根据品牌中文名列表 关联公式中指定的业态下的商家名称）
     * <功能详细描述>
     * @param conn
     * @param sql [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private void executesqlForUpdate(Connection conn,String sql)
    {
        Statement statement = null;
        
        try
        {
            statement = conn.createStatement();
            statement.executeUpdate(sql);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            System.out.println(sql);
        }
         
    }
    
    /**
     * 从文件中读取当前运行到哪个公式
     * @param exeRecordFile
     * @return [参数说明]
     * 
     * @return RuleBean [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private RuleBean getCurRuleFromFile(String exeRecordFile)
    {

        File file = new File(exeRecordFile);
        BufferedReader read = null;
        DataInputStream in = null;
        String lineTxt = null;
        RuleBean ruleBean = new RuleBean();
        
        try
        {
            in = new DataInputStream(new FileInputStream(file));
            read= new BufferedReader(new InputStreamReader(in,"utf8"));
            
            while((lineTxt = read.readLine()) != null)
            {
                String[] info = lineTxt.split("=");
                
                if(info.length < 2)
                {
                    System.out.println("配置文件设置有问题");
                    return null;
                }
                
                if(info[0].equals("ruleid"))
                {
                    ruleBean.setRuleid(info[1]);
                }
                
                if(info[0].equals("ruleindex"))
                {
                    ruleBean.setRuleindex(info[1]);
                }
                
            }
            
            if(isNull(ruleBean.getRuleid()) || isNull(ruleBean.getRuleindex()))
            {
                System.out.println("配置文件设置有问题");
                return null;
            }
            
            if (!RULE_MAP.containsKey(ruleBean.getRuleindex() + "_" + ruleBean.getRuleid()))
            {
                System.out.println("配置文件设置有问题");
                return null;
            }
            
            
        }
        catch (Exception e)
        {
             
        }
        finally
        {
            closeStream(read);
        }
        
        return ruleBean;
    
    }
    
    
    /**
     * 写文件 记录下次运行哪个公式
     * @param list
     * @param filePath [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private void writeRuleToFile(String str,String filePath)
    {
        //写文件
        BufferedWriter write = null;
        
        try
        {
            write = new BufferedWriter(new FileWriter(filePath,false));
            write.write(str);
            write.flush();
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
     * 读取下一个公式
     * @param curRuleBean
     * @return [参数说明]
     * 
     * @return RuleBean [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private RuleBean getNextRule(RuleBean curRuleBean)
    {
        //根据当前的rule 读取下一条rule
        String curRuleid = curRuleBean.getRuleid();
        String curRuleIndex  = curRuleBean.getRuleindex();
        RuleBean newRuleBean = new RuleBean();
        
        String nextRuleid = String.valueOf(Integer.valueOf(curRuleid)+1);
        String nextRuleIndex = String.valueOf(Integer.valueOf(curRuleIndex)+1);
        
        
        if (RULE_MAP.containsKey(curRuleIndex + "_" + nextRuleid))
        {
            newRuleBean = RULE_MAP.get(curRuleIndex + "_" + nextRuleid);
        }
        else if (RULE_MAP.containsKey(nextRuleIndex + "_" + nextRuleid))
        {
            newRuleBean = RULE_MAP.get(nextRuleIndex + "_" + nextRuleid);
        }
        else 
        {
            newRuleBean = null;
            System.out.println("没有新的公式了");
            System.exit(0);
        }
       
        String str = "ruleid="+newRuleBean.getRuleid()+"\nruleindex="+newRuleBean.getRuleindex();
        writeRuleToFile(str,RULE_PATH + EXE_RECORD_FILE);
        
        return newRuleBean;
    }
    
    
    /**
     * 空校验
     * @param key
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public boolean isNull(String key)
    {
        if(null == key || key.trim().equals(""))
        {
            return true;
        }
        
        return false;
    }
    
    /**
     * 非空校验
     * @param key
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public boolean notNullAndNot0(String key)
    {
        if(null == key || key.trim().equals("")||key.trim().equals("0"))
        {
            return false;
        }
        
        return true;
    }
    
    /**
     * 将公式加载到内存
     * @param ruleFile [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private  void loadRuleFromFile(String ruleFile)
    {

        File file = new File(ruleFile);
        BufferedReader read = null;
        DataInputStream in = null;
        String lineTxt = null;
        
        try
        {
            in = new DataInputStream(new FileInputStream(file));
            read= new BufferedReader(new InputStreamReader(in,"utf8"));
            
            while((lineTxt = read.readLine()) != null)
            {
                lineTxt = lineTxt.replace("\"", "");
                RuleBean ruleBean = createRule(lineTxt);
                //System.out.println(ruleBean.getRuleindex()+"_"+ruleBean.getRuleid());
                RULE_MAP.put(ruleBean.getRuleindex()+"_"+ruleBean.getRuleid(), ruleBean);
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
     * 构建公式bean
     * <功能详细描述>
     * @param str
     * @return [参数说明]
     * 
     * @return RuleBean [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private RuleBean createRule(String str)
    {
        String [] strs = str.split(",");
        RuleBean rule = new RuleBean(strs[0], strs[7], strs[1], strs[2],
                strs[3], strs[4], strs[5], strs[6]);
        
        return rule;
    }
    
    /**
     * 获取关联的sql  品牌，商家， 二级业态
     * @param brandId
     * @param keyName
     * @param type2id
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    /*
    private String getSubTableReplaceInsertSqlType(String brandId,String keyName,String typeid,String citycode,String shopTable,String shopTypeTable)
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append(" replace into  {shopTable} (id,brand_id,agent_id,base_id,mid,branch_name,pinyin,address,phone,fax,contact,point,state,citycode,areacode,add_user,add_time,update_time)  \n")
                .append(" select s.id,{brand},s.agent_id,s.base_id,s.mid,s.branch_name,s.pinyin,s.address,s.phone,s.fax,'',s.point,s.state,s.citycode,s.areacode,s.add_user,s.add_time,s.update_time      \n")
                .append(" from {shopTable} s join {shopTypeTable} t on ( s.id = t.shop_id and t.type_id = {typeid} and t.citycode = {citycode} ) \n")
                .append(" where s.brand_id = 0 and s.citycode = {citycode} \n")
                .append(" and replace(concat(SUBSTRING_INDEX(UPPER(s.branch_name), '(', 1), SUBSTRING_INDEX(UPPER(s.branch_name), ')', -1)),' ','')  LIKE '%{keyname}%' \n");
        
        String sql = sb.toString();
        sql = sql.replace("{brand}", brandId);
        sql = sql.replace("{keyname}", dealBrandName(keyName));
        sql = sql.replace("{typeid}", typeid);
        sql = sql.replace("{citycode}", citycode);
        sql = sql.replace("{shopTable}", shopTable);
        sql = sql.replace("{shopTypeTable}", shopTypeTable);
        return sql;
        
    }*/
    
    /**
     * 获取关联的sql  品牌，商家， 二级业态
     * @param brandId
     * @param keyName
     * @param type2id
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private String getSubTableBatchReplaceInsertSqlType(List<String> brandList,String citycode,String shopTable,String shopTypeTable)
    {

        if(brandList.size() == 0 )
        {
            return "";
        }
        
        StringBuilder sbAllSql = new StringBuilder();
        StringBuilder sb = new StringBuilder();
        List<String> brandNameList = new ArrayList<String>();
        
        sb.append(" replace into  {shopTable} (id,brand_id,agent_id,base_id,mid,branch_name,pinyin,address,phone,fax,contact,point,state,citycode,areacode,add_user,add_time,update_time)  \n")
        .append(" select s.id,{brand},s.agent_id,s.base_id,s.mid,s.branch_name,s.pinyin,s.address,s.phone,s.fax,'',s.point,s.state,s.citycode,s.areacode,s.add_user,s.add_time,s.update_time      \n")
        .append(" from {shopTable} s join {shopTypeTable} t on ( s.id = t.shop_id and t.type_id = {typeid} and t.citycode = {citycode} ) \n")
        .append(" where s.brand_id = 0 and s.citycode = {citycode} \n")
        .append(" and replace(concat(SUBSTRING_INDEX(UPPER(s.branch_name), '(', 1), SUBSTRING_INDEX(UPPER(s.branch_name), ')', -1)),' ','')  LIKE '%{keyname}%' \n");
        
        String [] brandInfo = brandList.get(0).split(",");
        String firstbrandId = brandInfo[1];
        String firstbrandName = dealBrandName(brandInfo[0]);
        String typeid = brandInfo[2];
        
        String sqlFirst = sb.toString();
        sqlFirst = sqlFirst.replace("{brand}", firstbrandId);
        sqlFirst = sqlFirst.replace("{keyname}", firstbrandName);
        sqlFirst = sqlFirst.replace("{typeid}", typeid);
        sqlFirst = sqlFirst.replace("{citycode}", citycode);
        sqlFirst = sqlFirst.replace("{shopTable}", shopTable);
        sqlFirst = sqlFirst.replace("{shopTypeTable}", shopTypeTable);
        
        sbAllSql.append(sqlFirst);
        brandNameList.add(firstbrandName);
        
        for(int i = 1;i <brandList.size();i++ )
        {
            sb.delete(0, sb.length());
            sb.append(" select s.id,{brand},s.agent_id,s.base_id,s.mid,s.branch_name,s.pinyin,s.address,s.phone,s.fax,'',s.point,s.state,s.citycode,s.areacode,s.add_user,s.add_time,s.update_time      \n")
            .append(" from {shopTable} s join {shopTypeTable} t on ( s.id = t.shop_id and t.type_id = {typeid} and t.citycode = {citycode} ) \n")
            .append(" where s.brand_id = 0 and s.citycode = {citycode} \n")
            .append(" and replace(concat(SUBSTRING_INDEX(UPPER(s.branch_name), '(', 1), SUBSTRING_INDEX(UPPER(s.branch_name), ')', -1)),' ','' ) LIKE '%{keyname}%' \n");
           
            String [] curbrandInfo = brandList.get(i).split(",");
            String curBrandId = curbrandInfo[1];
            String curBrandName = dealBrandName(curbrandInfo[0]);
            String curtypeid = curbrandInfo[2];
            
            for(String brandTempName :brandNameList)
            {
                sb.append(" and replace(concat(SUBSTRING_INDEX(UPPER(s.branch_name), '(', 1), SUBSTRING_INDEX(UPPER(s.branch_name), ')', -1)),' ','') NOT LIKE '%"+brandTempName+"%' \n");
            }
            
            String sqlTemp = sb.toString();
            
            sqlTemp = sqlTemp.replace("{brand}", curBrandId);
            sqlTemp = sqlTemp.replace("{keyname}", curBrandName);
            sqlTemp = sqlTemp.replace("{typeid}", curtypeid);
            sqlTemp = sqlTemp.replace("{citycode}", citycode);
            sqlTemp = sqlTemp.replace("{shopTable}", shopTable);
            sqlTemp = sqlTemp.replace("{shopTypeTable}", shopTypeTable);
            
            sbAllSql.append(" union all  \n");
            sbAllSql.append(sqlTemp);
            
            brandNameList.add(curBrandName);
        }
        
        
        return sbAllSql.toString();
        
    
        
    }
    
    /**
     * 获取关联的sql  品牌，商家， 二级业态
     * @param brandId
     * @param keyName
     * @param type2id
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private String getSubTableBatchReplaceShopType(List<String> brandList,String citycode,String shopTable,String shopTypeTable)
    {

        if(brandList.size() == 0 )
        {
            return "";
        }
        
        StringBuilder sbAllSql = new StringBuilder();
        StringBuilder sb = new StringBuilder();
        
        sb.append(" replace into  {shopTypeTable} (id,citycode,areacode,shop_id,type_id,mid,level,add_time)  \n")
        .append(" select t.id,t.citycode,t.areacode,s.id,{typeid},t.mid,t.level,t.add_time      \n")
        .append(" from {shopTable} s join {shopTypeTable} t on ( s.id = t.shop_id and  t.citycode = {citycode} ) \n")
        .append(" where s.brand_id = {brand} and s.citycode = {citycode} \n");
        
        String [] brandInfo = brandList.get(0).split(",");
        String firstbrandId = brandInfo[1];
        String typeid = brandInfo[2];
        
        String sqlFirst = sb.toString();
        sqlFirst = sqlFirst.replace("{brand}", firstbrandId);
        sqlFirst = sqlFirst.replace("{typeid}", typeid);
        sqlFirst = sqlFirst.replace("{citycode}", citycode);
        sqlFirst = sqlFirst.replace("{shopTable}", shopTable);
        sqlFirst = sqlFirst.replace("{shopTypeTable}", shopTypeTable);
        
        sbAllSql.append(sqlFirst);
        
        for(int i = 1;i <brandList.size();i++ )
        {
            sb.delete(0, sb.length());
            sb.append(" select t.id,t.citycode,t.areacode,s.id,{typeid},t.mid,t.level,t.add_time  \n")
            .append(" from {shopTable} s join {shopTypeTable} t on ( s.id = t.shop_id and t.citycode = {citycode} ) \n")
            .append(" where s.brand_id = {brand} and s.citycode = {citycode} \n");
           
            String [] curBrandInfo = brandList.get(i).split(",");
            String curBrandId = curBrandInfo[1];
            String curTypeid = curBrandInfo[2];
            String sqlTemp = sb.toString();
            
            sqlTemp = sqlTemp.replace("{brand}", curBrandId);
            sqlTemp = sqlTemp.replace("{typeid}", curTypeid);
            sqlTemp = sqlTemp.replace("{citycode}", citycode);
            sqlTemp = sqlTemp.replace("{shopTable}", shopTable);
            sqlTemp = sqlTemp.replace("{shopTypeTable}", shopTypeTable);
            
            sbAllSql.append(" union all  \n");
            sbAllSql.append(sqlTemp);
            
        }
        
        return sbAllSql.toString();
        
    
        
    }
    
    
    /**
     * 调整品牌商家的业态归属和品牌一致
     * @param brandId
     * @param keyName
     * @param type2id
     * @return [参数说明]
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private String getSubTableInsertShopType(String citycode,String shopTable,String shopTypeTable)
    {
        
        StringBuilder sb = new StringBuilder();
        
        sb.append(" insert into {shopTypeTable} (citycode,areacode,shop_id,type_id,mid,level,add_time) \n")
        .append(" select s.citycode,s.areacode,s.id,t.type,s.mid,2,s.add_time  \n")
        .append(" from cg_brand b, \n")
         .append(" cg_brand_type t, \n")
         .append(" {shopTable} s \n ")
         .append(" where b.id = t.brand_id  \n")
         .append(" and s.brand_id = b.id  \n")
         .append(" and s.brand_id > 0  \n")
         .append(" and s.citycode = {citycode}  \n")
         .append(" and b.mark = 1 \n")
         .append(" and t.mark = 1 \n");
         
        String sql = sb.toString();
        sql = sql.replace("{citycode}", citycode);
        sql = sql.replace("{shopTable}", shopTable);
        sql = sql.replace("{shopTypeTable}", shopTypeTable);
        return sql;
        
    }
    
    /**
     * 调整品牌商家的业态归属和品牌一致
     * @param brandId
     * @param keyName
     * @param type2id
     * @return [参数说明]
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private String getSubTableDeleteShopType(String citycode,String shopTable,String shopTypeTable)
    {
        
        StringBuilder sb = new StringBuilder();
        
        sb.append(" delete from {shopTypeTable} \n")
        .append(" where exists  \n")
        .append(" ( \n")
         .append(" select 1 from {shopTable} s where s.id = shop_id and s.brand_id > 0 and s.citycode = {citycode} \n")
         .append(" )  \n ")
         .append(" where citycode = {citycode}  \n");
        
        String sql = sb.toString();
        sql = sql.replace("{citycode}", citycode);
        sql = sql.replace("{shopTable}", shopTable);
        sql = sql.replace("{shopTypeTable}", shopTypeTable);
        return sql;
        
    }
    
    //======================================非分表的商家业态归属更新==========================
    /**
     * 调整品牌商家的业态归属和品牌一致
     * @param brandId
     * @param keyName
     * @param type2id
     * @return [参数说明]
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private String getDeleteShopType2()
    {
        
        StringBuilder sb = new StringBuilder();
        
        sb.append(" delete from cg_mid_poi_typ \n")
        .append(" where exists  \n")
        .append(" ( \n")
         .append(" select 1 from cg_mid_poi_shop s where s.id = brand_id and s.brand_id > 0 \n")
         .append(" )  \n ");
        
        String sql = sb.toString();
        return sql;
        
    }
    
    /**
     * 调整品牌商家的业态归属和品牌一致
     * @param brandId
     * @param keyName
     * @param type2id
     * @return [参数说明]
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private String getDeleteShopType3()
    {
        
        StringBuilder sb = new StringBuilder();
        
        sb.append(" delete from cg_mid_poi_style_relation \n")
        .append(" where exists  \n")
        .append(" ( \n")
         .append(" select 1 from cg_mid_poi_shop s where s.id = brand_id and s.brand_id > 0 \n")
         .append(" )  \n ");
        
        String sql = sb.toString();
        return sql;
        
    }
    
    /**
     * 调整品牌商家的业态归属和品牌一致
     * @param brandId
     * @param keyName
     * @param type2id
     * @return [参数说明]
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private String getInsertShopType2()
    {
        
        StringBuilder sb = new StringBuilder();
        
        sb.append(" insert into cg_mid_poi_type(brand_id,type,mid,level,add_time)  \n")
                .append(" select s.id,t.type,s.mid,2,s.add_time   \n")
                .append(" from cg_brand b, \n")
                .append(" cg_brand_type t, \n")
                .append(" cg_mid_poi_shop s \n")
                .append(" where b.id = t.brand_id  \n")
                .append(" and s.brand_id = b.id  \n")
                .append(" and s.brand_id > 0  \n")
                .append(" and b.mark = 1  \n")
                .append(" and t.mark = 1  \n");
        
        String sql = sb.toString();
        return sql;
        
    }
    
    /**
     * 调整品牌商家的业态归属和品牌一致
     * @param brandId
     * @param keyName
     * @param type2id
     * @return [参数说明]
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private String getInsertShopType3()
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append(" insert into cg_mid_poi_style_relation(brand_id,type,mid,level,add_time)  \n")
                .append(" select s.id,t.style_id,s.mid,2,s.add_time   \n")
                .append(" from cg_brand b,    \n")
                .append(" cg_brand_style_relation t, \n")
                .append(" cg_mid_poi_shop s   \n")
                .append(" where b.id = t.brand_id  \n")
                .append(" and s.brand_id = b.id  \n")
                .append(" and s.brand_id > 0  \n")
                .append(" and b.mark = 1  \n")
                .append(" and t.mark = 1  \n");
        
        String sql = sb.toString();
        return sql;
        
    }
    
    /**
     * 根据公式中的业态名称 读取业态id （2级）
     * @param type1name
     * @param type2name
     * @param conn
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private String getType2idList(String type1name,List<String> type2nameList,Connection conn)
    {
        StringBuilder sb = new StringBuilder();
        String type2names = "";
        int length = 0;
        for (String name : type2nameList)
        {
            length++;
            
            if(length == type2nameList.size() )
            {
                type2names = type2names + "'" +name + "'" ;
            }
            else
            {
                type2names = type2names + "'" +name + "',";
            }
        }
        
        sb.append(" select s.id \n ")
                .append("  from cg_shop_type s ,cg_shop_type_relation r \n")
                .append(" where \n ")
                .append("    ( \n  ")
                .append("         locate(concat(',',s.id,','),r.child_ids) > 0 \n ")
                .append("          or \n ")
                .append("          locate(concat('A',s.id,','),concat('A',r.child_ids)) > 0 \n")
                .append("         or  \n ")
                .append("          locate(concat(',',s.id,'A'),concat(r.child_ids,'A')) > 0  \n")
                .append("    )\n")
                .append("and s.mark = 1 \n ")
                .append(" and r.mark = 1 \n ")
                .append("and r.name = '{type1name}' \n ")
                .append("and s.name in ( {type2nameList} ) \n ");
        
        String sql = sb.toString();
        sql = sql.replace("{type1name}", type1name);
        sql = sql.replace("{type2nameList}", type2names);
        //System.out.println(sql);
        
        ResultSet res = null;
        Statement statement = null;
        
        String type2id = "";
        List <String> type2idList = new ArrayList<String>();
        
        try
        {
            statement = conn.createStatement();
            res = statement.executeQuery(sql);
            
            while (null != res && res.next())
            {
                type2idList.add(res.getString("id"));
            }
            
            int index = 0;
            
            for (String id : type2idList)
            {
                index++;
                
                if(index == type2idList.size() )
                {
                    type2id = type2id + id ;
                }
                else
                {
                    type2id = type2id + id + ",";
                }
            }
            
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            
        }
        
        return type2id;
        
    }
    
    
    
    /**
     * 根据公式中的业态名称 读取业态id （3级）
     * <功能详细描述>
     * @param type1name
     * @param type2name
     * @param type3name
     * @param conn
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private String getType3idList(String type1name,String type2name,List<String> type3nameList,Connection conn)
    {
        StringBuilder sb = new StringBuilder();
        
        String type3names = "";
        int length = 0;
        for (String name : type3nameList)
        {
            length++;
            
            if(length == type3nameList.size() )
            {
                type3names = type3names + "'" +name + "'" ;
            }
            else
            {
                type3names = type3names + "'" +name + "',";
            }
        }
        
        sb.append(" select t1.id id3 from cg_brand_style t1, \n")
                .append(" ( \n")
                .append("   select r.name name1,s.name name2,s.id id2,r.id id1 from cg_shop_type s ,cg_shop_type_relation r \n")
                .append("   where  \n")
                .append("    ( \n")
                .append("         locate(concat(',',s.id,','),r.child_ids) > 0 \n")
                .append("           or  \n")
                .append("          locate(concat('A',s.id,','),concat('A',r.child_ids)) > 0 \n")
                .append("          or \n")
                .append("          locate(concat(',',s.id,'A'),concat(r.child_ids,'A')) > 0   \n")
                .append("     ) \n")
                .append("    and s.mark = 1 \n")
                .append("     order by r.name \n")
                .append(" ) t3 \n")
                .append(" where t1.type = t3.id2 and t3.name1 = '{type1name}'  and t3.name2 = '{type2name}' and t1.name in ( {type3name} )  \n");
        
        
        String sql = sb.toString();
        sql = sql.replace("{type1name}", type1name);
        sql = sql.replace("{type2name}", type2name);
        sql = sql.replace("{type3name}", type3names);
        
        ResultSet res = null;
        Statement statement = null;
        String type3id = "";
        List <String> type3idList = new ArrayList<String>();
        
        try
        {
            statement = conn.createStatement();
            //System.out.println(sql);
            res = statement.executeQuery(sql);
            
            while (null != res && res.next())
            {
                type3idList.add(res.getString("id3"));
            }
            
            int index = 0;
            
            for (String id : type3idList)
            {
                index++;
                
                if(index == type3idList.size() )
                {
                    type3id = type3id + id ;
                }
                else
                {
                    type3id = type3id + id + ",";
                }
            }
            
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            System.out.println(sql);
        }
        finally
        {
            
        }
        
        return type3id;
        
    }
    
    /**
     * 根据某二级业态读取品牌列表 中文名称长度降序
     * @param type2id
     * @param conn
     * @return [参数说明]
     * 
     * @return List<String> [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private List<String> getBrandListByType2List(String type2id, Connection conn )
    {

        String sql ="";
        StringBuffer sb = new StringBuffer();
        ResultSet res = null;
        Statement statement = null;
        
        sb.append(" SELECT  \n")
                .append("  REPLACE(REPLACE(trim(b.name), CHAR(10), ''), CHAR(13), '') name, \n")
                .append("  b.id,t.type \n")
                .append("  FROM cg_brand_type t,  \n")
                .append("       cg_brand b \n")
                .append("  WHERE b.id = t.brand_id  \n")
                .append("  AND t.mark = 1  \n")
                .append("  AND b.mark = 1  \n")
                .append("  and t.type in ( {type2id} ) \n")
                .append("  and char_length(trim(b.name))>= 2  \n")
                .append(" ORDER BY CHAR_LENGTH(TRIM(b.name)) DESC  \n");
        
         sql = sb.toString();
         sql =  sql.replace("{type2id}", type2id);
         List<String> list = new ArrayList<String>();
         //System.out.println(sql);
         
        try
        {
            statement = conn.createStatement();
            res = statement.executeQuery(sql);
            
            while (null != res && res.next())
            {
                StringBuilder sb1 = new StringBuilder();
                sb1.append(dealBrandName(replaceComma(res.getString("name"))))
                        .append(",")
                        .append(res.getString("id"))
                         .append(",")
                         .append(res.getString("type"));
                
                list.add(sb1.toString());
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            System.out.println(sql);
        }
        finally
        {
            
        }
        
        return list;
    }
    
    
    
    /**
     * 根据某三级业态读取品牌列表 中文名称长度降序
     * @param type3id
     * @param conn
     * @return [参数说明]
     * 
     * @return List<String> [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private List<String> getBrandListByType3List(String type3id, Connection conn)
    {

        String sql ="";
        StringBuffer sb = new StringBuffer();
        ResultSet res = null;
        Statement statement = null;
        
        sb.append(" SELECT  \n")
                .append("  REPLACE(REPLACE(trim(b.name), CHAR(10), ''), CHAR(13), '') name, \n")
                .append("  b.id,t.style_id \n")
                .append("  FROM cg_brand_style_relation t,  \n")
                .append("       cg_brand b \n")
                .append("  WHERE b.id = t.brand_id  \n")
                .append("  AND t.mark = 1  \n")
                .append("  AND b.mark = 1  \n")
                .append("  and t.style_id in ( {type3id} ) \n")
                .append("  and char_length(trim(b.name))>= 2  \n")
                .append(" ORDER BY CHAR_LENGTH(TRIM(b.name)) DESC  \n");
        
         sql = sb.toString();
         sql =  sql.replace("{type3id}", type3id);
         List<String> list = new ArrayList<String>();
         //System.out.println(sql);
         
        try
        {
            statement = conn.createStatement();
            res = statement.executeQuery(sql);
            
            while (null != res && res.next())
            {
                StringBuilder sb1 = new StringBuilder();
                sb1.append(dealBrandName(replaceComma(res.getString("name"))))
                        .append(",")
                        .append(res.getString("id"))
                         .append(",")
                         .append(res.getString("style_id"));
                
                list.add(sb1.toString());
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            
        }
        
        return list;
    }
    
    
    
    /**
     * 读取某一级业态下多个二级业态的品牌列表 中文名称长度降序
     * @param type2id
     * @param conn
     * @return [参数说明]
     * 
     * @return List<String> [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private List<String> getBrandEngNameListByType2List(String type2idList, Connection conn )
    {

        String sql = "";
        StringBuffer sb = new StringBuffer();
        ResultSet res = null;
        Statement statement = null;
        
        sb.append(" SELECT  \n")
        .append("  REPLACE(REPLACE(trim(b.eng_name), CHAR(10), ''), CHAR(13), '') name, \n")
        .append("  b.id,t.type \n")
        .append("  FROM cg_brand_type t,  \n")
        .append("       cg_brand b \n")
        .append("  WHERE b.id = t.brand_id  \n")
        .append("  AND t.mark = 1  \n")
        .append("  AND b.mark = 1  \n")
        .append("  and t.type in ( {type2id} ) \n")
        .append("  and char_length(trim(b.eng_name))> 2  \n")
        .append(" ORDER BY CHAR_LENGTH(TRIM(b.eng_name)) DESC  \n");
        
         sql = sb.toString();
         sql =  sql.replace("{type2id}", type2idList);
         List<String> list = new ArrayList<String>();
         //System.out.println(sql);
         
        try
        {
            statement = conn.createStatement();
            res = statement.executeQuery(sql);
            
            while (null != res && res.next())
            {
                StringBuilder sb1 = new StringBuilder();
                sb1.append(dealBrandName(replaceComma(res.getString("name"))))
                        .append(",")
                        .append(res.getString("id"))
                        .append(",")
                        .append(res.getString("type"));
                
                list.add(sb1.toString());
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            System.out.println(sql);
        }
        finally
        {
            
        }
        
        return list;
    }
    
    
    
    
    /**
     * 根据某三级业态读取品牌列表 英文名称长度降序
     * @param type3id
     * @param conn
     * @return [参数说明]
     * 
     * @return List<String> [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private List<String> getBrandEngNameListByType3List(String type3id,Connection conn)
    {

        String sql ="";
        StringBuffer sb = new StringBuffer();
        ResultSet res = null;
        Statement statement = null;
        
        sb.append(" SELECT  \n")
                .append("  REPLACE(REPLACE(trim(b.eng_name), CHAR(10), ''), CHAR(13), '') name, \n")
                .append("  b.id,t.style_id \n")
                .append("  FROM cg_brand_style_relation t,  \n")
                .append("       cg_brand b \n")
                .append("  WHERE b.id = t.brand_id  \n")
                .append("  AND t.mark = 1  \n")
                .append("  AND b.mark = 1  \n")
                .append("  and t.style_id in ( {type3id} ) \n")
                .append("  and char_length(trim(b.eng_name))> 2  \n")
                .append("  ORDER BY CHAR_LENGTH(TRIM(b.eng_name)) DESC  \n");
        
         sql = sb.toString();
         sql =  sql.replace("{type3id}", type3id);
         List<String> list = new ArrayList<String>();
         //System.out.println(sql);
         
        try
        {
            statement = conn.createStatement();
            res = statement.executeQuery(sql);
            
            while (null != res && res.next())
            {
                StringBuilder sb1 = new StringBuilder();
                sb1.append(dealBrandName(replaceComma(res.getString("name"))))
                        .append(",")
                        .append(res.getString("id"))
                        .append(",")
                        .append(res.getString("style_id"));
                
                list.add(sb1.toString());
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            
        }
        
        return list;
    }
    
    /**
     * 根据某二级业态读取品牌列表 别名长度降序
     * @param type2id
     * @param conn
     * @return [参数说明]
     * 
     * @return List<String> [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private List<String> getBrandOtherNameListByType2List(String type2id,Connection conn )
    {

        String sql ="";
        StringBuffer sb = new StringBuffer();
        ResultSet res = null;
        Statement statement = null;
        
        sb.append(" SELECT  \n")
                .append("  REPLACE(REPLACE(trim(b.other_name), CHAR(10), ''), CHAR(13), '') name, \n")
                .append("  b.id,t.type \n")
                .append("  FROM cg_brand_type t,  \n")
                .append("       cg_brand b \n")
                .append("  WHERE b.id = t.brand_id  \n")
                .append("  AND t.mark = 1  \n")
                .append("  AND b.mark = 1  \n")
                .append("  and t.type in ( {type2id} ) \n")
                .append("  and char_length(trim(b.other_name))> 2  \n")
                .append(" ORDER BY CHAR_LENGTH(TRIM(b.other_name)) DESC  \n");
        
         sql = sb.toString();
         sql =  sql.replace("{type2id}", type2id);
         List<String> list = new ArrayList<String>();
         //System.out.println(sql);
         
        try
        {
            statement = conn.createStatement();
            res = statement.executeQuery(sql);
            
            while (null != res && res.next())
            {
                StringBuilder sb1 = new StringBuilder();
                sb1.append(dealBrandName(replaceComma(res.getString("name"))))
                        .append(",")
                        .append(res.getString("id"))
                        .append(",")
                        .append(res.getString("type"));
                
                list.add(sb1.toString());
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            
        }
        
        return list;
    }
    
    /**
     * 根据某三级业态读取品牌列表 别名长度降序
     * <功能详细描述>
     * @param type3id
     * @param conn
     * @return [参数说明]
     * 
     * @return List<String> [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private List<String> getBrandOtherNameListByType3List(String type3id,Connection conn)
    {

        String sql ="";
        StringBuffer sb = new StringBuffer();
        ResultSet res = null;
        Statement statement = null;
        
        sb.append(" SELECT  \n")
                .append("  REPLACE(REPLACE(trim(b.other_name), CHAR(10), ''), CHAR(13), '') name, \n")
                .append("  b.id,t.style_id \n")
                .append("  FROM cg_brand_style_relation t,  \n")
                .append("       cg_brand b \n")
                .append("  WHERE b.id = t.brand_id  \n")
                .append("  AND t.mark = 1  \n")
                .append("  AND b.mark = 1  \n")
                .append("  and t.style_id in ( {type3id} ) \n")
                .append("  and char_length(trim(b.other_name))> 2  \n")
                .append("  ORDER BY CHAR_LENGTH(TRIM(b.other_name)) DESC  \n");
        
         sql = sb.toString();
         sql =  sql.replace("{type3id}", type3id);
         List<String> list = new ArrayList<String>();
         //System.out.println(sql);
         
        try
        {
            statement = conn.createStatement();
            res = statement.executeQuery(sql);
            
            while (null != res && res.next())
            {
                StringBuilder sb1 = new StringBuilder();
                sb1.append(dealBrandName(replaceComma(res.getString("name"))))
                        .append(",")
                        .append(res.getString("id"))
                        .append(",")
                        .append(res.getString("style_id"));
                
                list.add(sb1.toString());
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            
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
    
    public static String replaceComma(String str)
    {
        String dest = "";
        
        if (str != null)
        {
            dest = str.replaceAll(",","");
        }
        
        return dest;
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
            
            if (conn.isClosed())
            {
                System.exit(0);
                System.out.println("连接数据库失败");
            }
                //System.out.println("Succeeded connecting to the Database!");
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
    
    
    //=======================================暂时不用的方法===========================================
    /**
     * 获取关联的sql  品牌，商家， 二级业态
     * @param brandId
     * @param keyName
     * @param type2id
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    /*private String getUpdateSqlType2Tpl(String brandId,String keyName,String type2id,String citycode)
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append(" update cg_mid_poi_shop s set  s.brand_id = {brand} \n")
                .append(" where s.brand_id = 0 and s.citycode = {citycode} \n")
                .append(" and concat(SUBSTRING_INDEX(UPPER(s.branch_name), '(', 1), SUBSTRING_INDEX(UPPER(s.branch_name), ')', -1))  LIKE '%{keyname}%' \n")
                .append("and exists \n")
                .append("( \n")
                .append("    select 1 from cg_mid_poi_type t where s.id = t.brand_id and t.type = {type2id} \n")
                .append(" ) \n");
        
        String sql = sb.toString();
        sql = sql.replace("{brand}", brandId);
        sql = sql.replace("{keyname}", keyName);
        sql = sql.replace("{type2id}", type2id);
        sql = sql.replace("{citycode}", citycode);
        return sql;
        
    }*/
    
    /**
     * 获取关联的sql  品牌，商家， 二级业态
     * @param brandId
     * @param keyName
     * @param type2id
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    /*
    private String getSubTableUpdateSqlType2Tpl(String brandId,String keyName,String type2id,String citycode,String shopTable,String shopType2Table)
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append(" update {shopTable} s set  s.brand_id = {brand} \n")
                .append(" where s.brand_id = 0 and s.citycode = {citycode} \n")
                .append(" and concat(SUBSTRING_INDEX(UPPER(s.branch_name), '(', 1), SUBSTRING_INDEX(UPPER(s.branch_name), ')', -1))  LIKE '%{keyname}%' \n")
                .append("and exists \n")
                .append("( \n")
                .append("    select 1 from {shopType2Table} t where s.id = t.shop_id and t.type_id = {type2id} and t.citycode = {citycode} \n")
                .append(" ) \n");
        
        String sql = sb.toString();
        sql = sql.replace("{brand}", brandId);
        sql = sql.replace("{keyname}", keyName);
        sql = sql.replace("{type2id}", type2id);
        sql = sql.replace("{citycode}", citycode);
        sql = sql.replace("{shopTable}", shopTable);
        sql = sql.replace("{shopType2Table}", shopType2Table);
        return sql;
        
    }
    */
    
    /**
     * 获取关联的sql  品牌，商家， 三级业态
     * @param brandId
     * @param keyName
     * @param type3id
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    /*
    private String getUpdateSqlType3Tpl(String brandId,String keyName,String type3id,String citycode)
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append(" update cg_mid_poi_shop s set  s.brand_id = {brand} \n")
                .append(" where s.brand_id = 0 and s.citycode = {citycode} \n")
                .append(" and concat(SUBSTRING_INDEX(UPPER(s.branch_name), '(', 1), SUBSTRING_INDEX(UPPER(s.branch_name), ')', -1))  LIKE '%{keyname}%' \n")
                .append("and exists \n")
                .append("( \n")
                .append("    select 1 from cg_mid_poi_style_relation t where s.id = t.brand_id and t.style_id = {type3id} \n")
                .append(" ) \n");
        
        String sql = sb.toString();
        sql = sql.replace("{brand}", brandId);
        sql = sql.replace("{keyname}", keyName);
        sql = sql.replace("{type3id}", type3id);
        sql = sql.replace("{citycode}", citycode);
        return sql;
        
    }*/
    
    /**
     * 获取关联的sql  品牌，商家， 三级业态
     * @param brandId
     * @param keyName
     * @param type3id
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    /*
    private String getSubTableUpdateSqlType3Tpl(String brandId,String keyName,String type3id,String citycode,String shopTable,String shopType3Table)
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append(" update {shopTable} s set  s.brand_id = {brand} \n")
                .append(" where s.brand_id = 0 and s.citycode = {citycode} \n")
                .append(" and concat(SUBSTRING_INDEX(UPPER(s.branch_name), '(', 1), SUBSTRING_INDEX(UPPER(s.branch_name), ')', -1))  LIKE '%{keyname}%' \n")
                .append("and exists \n")
                .append("( \n")
                .append("   select 1 from {shopType3Table} t where s.id = t.brand_id and t.type_id = {type3id} and t.citycode = {citycode} \n")
                .append(" ) \n");
        
        String sql = sb.toString();
        sql = sql.replace("{brand}", brandId);
        sql = sql.replace("{keyname}", keyName);
        sql = sql.replace("{type3id}", type3id);
        sql = sql.replace("{citycode}", citycode);
        sql = sql.replace("{shopTable}", shopTable);
        sql = sql.replace("{shopType3Table}", shopType3Table);
        return sql;
        
    }
    */
    
    /**
     * 获取关联的sql  品牌，商家， 三级业态
     * @param brandId
     * @param keyName
     * @param type3id
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    /*
    private String getSubTableReplaceInsertSqlType3Tpl_bak(String brandId,String keyName,String type3id,String citycode,String shopTable,String shopType3Table)
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append(" replace into  {shopTable} (id,brand_id,agent_id,base_id,mid,branch_name,address,phone,fax,contact,point,state,citycode,areacode,add_user,add_time,update_time)  \n")
        .append(" select s.id,{brand},s.agent_id,s.base_id,s.mid,s.branch_name,s.address,s.phone,s.fax,'',s.point,s.state,s.citycode,s.areacode,s.add_user,s.add_time,s.update_time      \n")
        .append(" from {shopTable} s join {shopType3Table} t on ( s.id = t.shop_id and t.type_id = {type3id} and t.citycode = {citycode} ) \n")
        .append(" where s.brand_id = 0 and s.citycode = {citycode} \n")
        .append(" and concat(SUBSTRING_INDEX(UPPER(s.branch_name), '(', 1), SUBSTRING_INDEX(UPPER(s.branch_name), ')', -1))  LIKE '%{keyname}%' \n");
        
        String sql = sb.toString();
        sql = sql.replace("{brand}", brandId);
        sql = sql.replace("{keyname}", keyName);
        sql = sql.replace("{type3id}", type3id);
        sql = sql.replace("{citycode}", citycode);
        sql = sql.replace("{shopTable}", shopTable);
        sql = sql.replace("{shopType3Table}", shopType3Table);
        return sql;
    }
    */
    
    /**
     * 核心程序 根据品牌中文名列表 关联公式中指定的业态下的商家名称
     * @param ruleBean
     * @param brandList
     * @param conn [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    /*
    private void brandMapShop(RuleBean ruleBean,List<String> brandList,Connection conn,int subTableFlag)
    {
        String shoptype2id = "";
        String shoptype3id = "";
        
        if(notNullAndNot0(ruleBean.getCgShop3Name()))
        {
            shoptype3id = getType3id(ruleBean.getCgShop1Name(), ruleBean.getCgShop2Name(), ruleBean.getCgShop3Name(),conn);
        }
        else if (notNullAndNot0(ruleBean.getCgShop2Name()))
        {
            shoptype2id = getType2id(ruleBean.getCgShop1Name(), ruleBean.getCgShop2Name(),conn);
        }
        
        for(String brand :brandList)
        {
            //品牌名称可能存在逗号，取最后一个逗号，逗号后面一定是品牌id
            String brandId = dealBrandName(brand.substring(brand.lastIndexOf(",")+1));
            String brandName = dealBrandName(brand.substring(0,brand.lastIndexOf(",")));
            String sql = "";
            
            if(notNullAndNot0(shoptype3id))
            {
                
                if(subTableFlag == SUB_TABLE)
                {
                    sql = getSubTableReplaceInsertSqlType(brandId, brandName, shoptype3id,city_code,cg_sub_table_shop,cg_sub_table_shop_type3);
                }
                else
                {
                    sql = getReplaceShopTablebyType3SqlTpl(brandId, brandName, shoptype3id,city_code);
                }
                
                //System.out.println(sql);
                executesqlForUpdate(conn,sql);
            }
            else if (notNullAndNot0(shoptype2id))
            {
                
                if(subTableFlag == SUB_TABLE)
                {
                    sql = getSubTableReplaceInsertSqlType(brandId, brandName, shoptype2id,city_code,cg_sub_table_shop,cg_sub_table_shop_type2);
                }
                else
                {
                    sql = getReplaceInsertSqlType2Tpl(brandId, brandName, shoptype2id,city_code);
                }
                
                //System.out.println(sql);
                executesqlForUpdate(conn,sql);
            }
        }
    }
    */
    
    /**
     * 获取关联的sql  品牌，商家， 二级业态
     * @param brandId
     * @param keyName
     * @param type2id
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private String getReplaceShopTableForType2SqlTpl(String brandId,String keyName,String type2id,String citycode)
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append(" replace into cg_mid_poi_shop  (id,brand_id,agent_id,poi_id,base_id,mid,branch_name,address,phone,fax,contact,point,state,city,citycode,areacode,project,floor,floor_img,add_user,add_time,update_time)   \n")
                .append("select s.id,{brand},s.agent_id,s.poi_id,s.base_id,s.mid,s.branch_name,s.address,s.phone,s.fax,'',s.point,s.state,s.city,s.citycode,s.areacode,'','','',s.add_user,s.add_time,s.update_time  \n")
                .append(" from cg_mid_poi_shop s join cg_mid_poi_type t on ( s.id = t.brand_id and t.type = {type2id}  ) \n")
                .append("where s.brand_id = 0 and s.citycode = {citycode}  \n")
                .append(" and concat(SUBSTRING_INDEX(UPPER(s.branch_name), '(', 1), SUBSTRING_INDEX(UPPER(s.branch_name), ')', -1))  LIKE '%{keyname}%'  \n");
        
        String sql = sb.toString();
        sql = sql.replace("{brand}", brandId);
        sql = sql.replace("{keyname}", keyName);
        sql = sql.replace("{type2id}", type2id);
        sql = sql.replace("{citycode}", citycode);
        return sql;
        
    }
    
    /**
     * 获取关联的sql  品牌，商家， 二级业态
     * @param brandId
     * @param keyName
     * @param type2id
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private String getReplaceShopType2TableSqlTpl(String brandId,String type2id,String citycode)
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append(" replace into cg_mid_poi_shop  (id,brand_id,type,level,add_time,mid)   \n")
                .append("select t.id,{brand},{type2id},t.level,t.add_time,t.mid  \n")
                .append(" from cg_mid_poi_shop s join cg_mid_poi_type t on ( s.id = t.brand_id and t.type = {type2id}  ) \n")
                .append("where s.brand_id = {brand} and s.citycode = {citycode}  \n");
        
        String sql = sb.toString();
        sql = sql.replace("{brand}", brandId);
        sql = sql.replace("{type2id}", type2id);
        sql = sql.replace("{citycode}", citycode);
        return sql;
        
    }
    
    /**
     * 获取关联的sql  品牌，商家， 三级业态
     * @param brandId
     * @param keyName
     * @param type3id
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private String getReplaceShopTablebyType3SqlTpl(String brandId,String keyName,String type3id,String citycode)
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append(" replace into cg_mid_poi_shop  (id,brand_id,agent_id,poi_id,base_id,mid,branch_name,address,phone,fax,contact,point,state,city,citycode,areacode,project,floor,floor_img,add_user,add_time,update_time)   \n")
        .append("select s.id,{brand},s.agent_id,s.poi_id,s.base_id,s.mid,s.branch_name,s.address,s.phone,s.fax,'',s.point,s.state,s.city,s.citycode,s.areacode,'','','',s.add_user,s.add_time,s.update_time  \n")
        .append(" from cg_mid_poi_shop s join cg_mid_poi_style_relation t  on ( s.id = t.brand_id and t.style_id = {type3id} ) \n")
        .append("where s.brand_id = 0 and s.citycode = {citycode}  \n")
        .append(" and concat(SUBSTRING_INDEX(UPPER(s.branch_name), '(', 1), SUBSTRING_INDEX(UPPER(s.branch_name), ')', -1))  LIKE '%{keyname}%'  \n");
        
        String sql = sb.toString();
        sql = sql.replace("{brand}", brandId);
        sql = sql.replace("{keyname}", keyName);
        sql = sql.replace("{type3id}", type3id);
        sql = sql.replace("{citycode}", citycode);
        return sql;
        
    }
    
    /**
     * 获取关联的sql  品牌，商家， 三级业态
     * @param brandId
     * @param keyName
     * @param type3id
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private String getReplaceShopType3TableSqlTpl(String brandId,String type3id,String citycode)
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append(" replace into cg_mid_poi_style_relation  (id,brand_id,style_id,add_time,mid) \n")
        .append("select t.id,{brand},{type3id},t.add_time,t.mid  \n")
        .append(" from cg_mid_poi_shop s join cg_mid_poi_style_relation t  on ( s.id = t.brand_id and t.style_id = {type3id} ) \n")
        .append("where s.brand_id = {brand} and s.citycode = {citycode}  \n");
        
        String sql = sb.toString();
        sql = sql.replace("{brand}", brandId);
        sql = sql.replace("{type3id}", type3id);
        sql = sql.replace("{citycode}", citycode);
        return sql;
        
    }
    
    /**
     * 根据某二级业态读取品牌列表 英文名称长度降序
     * <功能详细描述>
     * @param type2id
     * @param conn
     * @return [参数说明]
     * 
     * @return List<String> [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    /*
    private List<String> getBrandEngNameListByType2(String type2id,Connection conn )
    {

        String sql ="";
        StringBuffer sb = new StringBuffer();
        ResultSet res = null;
        Statement statement = null;
        
        sb.append(" SELECT  \n")
                .append("  REPLACE(REPLACE(trim(b.eng_name), CHAR(10), ''), CHAR(13), '') name, \n")
                .append("  b.id \n")
                .append("  FROM cg_brand_type t,  \n")
                .append("       cg_brand b \n")
                .append("  WHERE b.id = t.brand_id  \n")
                .append("  AND t.mark = 1  \n")
                .append("  AND b.mark = 1  \n")
                .append("  and t.type = {type2id}  \n")
                .append("  and char_length(trim(b.eng_name))> 2  \n")
                .append(" ORDER BY CHAR_LENGTH(TRIM(b.eng_name)) DESC  \n");
        
         sql = sb.toString();
         sql =  sql.replace("{type2id}", type2id);
         List<String> list = new ArrayList<String>();
         //System.out.println(sql);
         
        try
        {
            statement = conn.createStatement();
            res = statement.executeQuery(sql);
            
            while (null != res && res.next())
            {
                StringBuilder sb1 = new StringBuilder();
                sb1.append(res.getString("name"))
                        .append(",")
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
            
        }
        
        return list;
    }*/
    
    /**
     * 根据某三级业态读取品牌列表 英文名称长度降序
     * @param type3id
     * @param conn
     * @return [参数说明]
     * 
     * @return List<String> [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    /*
    private List<String> getBrandEngNameListByType3(String type3id,Connection conn)
    {

        String sql ="";
        StringBuffer sb = new StringBuffer();
        ResultSet res = null;
        Statement statement = null;
        
        sb.append(" SELECT  \n")
                .append("  REPLACE(REPLACE(trim(b.eng_name), CHAR(10), ''), CHAR(13), '') name, \n")
                .append("  b.id \n")
                .append("  FROM cg_brand_style_relation t,  \n")
                .append("       cg_brand b \n")
                .append("  WHERE b.id = t.brand_id  \n")
                .append("  AND t.mark = 1  \n")
                .append("  AND b.mark = 1  \n")
                .append("  and t.style_id = {type3id}  \n")
                .append("  and char_length(trim(b.eng_name))> 2  \n")
                .append("  ORDER BY CHAR_LENGTH(TRIM(b.eng_name)) DESC  \n");
        
         sql = sb.toString();
         sql =  sql.replace("{type3id}", type3id);
         List<String> list = new ArrayList<String>();
         //System.out.println(sql);
         
        try
        {
            statement = conn.createStatement();
            res = statement.executeQuery(sql);
            
            while (null != res && res.next())
            {
                StringBuilder sb1 = new StringBuilder();
                sb1.append(res.getString("name"))
                        .append(",")
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
            
        }
        
        return list;
    }
    */
}


