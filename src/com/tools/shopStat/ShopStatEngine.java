package com.tools.shopStat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.HttpURLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ShopStatEngine
{
    
    private static String URL ="jdbc:mysql://61.155.8.199:3306/zhaoshang?useUnicode=true&characterEncoding=UTF-8";
    private static String USER = "cg001";
    private static String PWD ="cgdb2015zaqXSW";

    //南京220 南平 55 厦门 60 江苏 16  福建 4 福州 53
    private static String PROVINCE_NAME = "fujian";
    private static String PROVINCE_CODE ="4";
    private static String CITY_NAME = "fuzhou";
    private static String CITY_CODE ="53";
    
    private static String CG_SUB_TABLR_SHOP = "cg_shop_" + PROVINCE_NAME;
    private static String CG_SUB_TABLE_SHOP_TYPE2 = "cg_shop_type2_" + PROVINCE_NAME;
    private static String CG_SUB_TABLE_SHOP_TYPE3 = "cg_shop_type3_" + PROVINCE_NAME;
    private static String CG_SUB_TABLR_SHOP_STAT = "cg_shop_stat_" + PROVINCE_NAME;
    private static String CG_SHOP_STAT = "cg_shop_stat_" + PROVINCE_NAME;
    
    //1 分表统计关  2 分表统计开
    private static int SUB_TABLE_SWITCH =2;
    
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
        ShopStatEngine engine = new ShopStatEngine();
        //engine.exeEngine(SUB_TABLE_SWITCH);
        engine.exeByCity(SUB_TABLE, "jiangsu", "4", "nanjing", "220");
    }
    
    public void exeByCity(int subFlag,String provinceName,String provinceCode,String cityName,String cityCode)
    {
        CG_SUB_TABLR_SHOP = "cg_shop_" + PROVINCE_NAME;
        CG_SUB_TABLE_SHOP_TYPE2 = "cg_shop_type2_" + PROVINCE_NAME;
        CG_SUB_TABLE_SHOP_TYPE3 = "cg_shop_type3_" + PROVINCE_NAME;
        CG_SUB_TABLR_SHOP_STAT = "cg_shop_stat_" + PROVINCE_NAME;
        CG_SHOP_STAT = "cg_shop_stat_" + PROVINCE_NAME;
        SUB_TABLE_SWITCH = subFlag;
        
        exeEngine(SUB_TABLE_SWITCH);
    }
    
    /**
     * 某个城市的统计入口 
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private void exeEngine(int subFlag)
    {
        Connection conn = null;
        long time1 = System.currentTimeMillis();
        
        try
        {
            conn = getConn();
            truncateTables(conn);
            String sql = "";
            
            //读取城市和业态模板数据=======================================
            sql =  getCityType2Tpl(CITY_NAME, CITY_CODE);
            //System.out.println(sql);
            executesqlForUpdate(conn,sql);
            sql =  getCityType3Tpl(CITY_NAME, CITY_CODE);
            //System.out.println(sql);
            executesqlForUpdate(conn,sql);
            System.out.println("finish city type template");
            
            //统计商家数据================================================
            if(subFlag == NOT_SUB_TABLE)
            {
                sql =  getShopType2MidSql(CITY_NAME, CITY_CODE);
                //System.out.println(sql);
                executesqlForUpdate(conn,sql);
                sql =  getShopType3MidSql(CITY_NAME, CITY_CODE);
                //System.out.println(sql);
                executesqlForUpdate(conn,sql);
            }
            else if (subFlag == SUB_TABLE)
            {
                sql =  getSubTableShopType2MidSql(CITY_NAME, CITY_CODE,CG_SUB_TABLR_SHOP,CG_SUB_TABLE_SHOP_TYPE2);
                //System.out.println(sql);
                executesqlForUpdate(conn,sql);
                sql =  getSubTableShopType3MidSql(CITY_NAME, CITY_CODE,CG_SUB_TABLR_SHOP,CG_SUB_TABLE_SHOP_TYPE3);
                //System.out.println(sql);
                executesqlForUpdate(conn,sql);
            }
            
            //和分表无关 临时统计表内部运算
            sql =  getShopType2TempSql(CITY_NAME, CITY_CODE);
            //System.out.println(sql);
            executesqlForUpdate(conn,sql);
            sql =  getShopType3TempSql(CITY_NAME, CITY_CODE);
            //System.out.println(sql);
            executesqlForUpdate(conn,sql);
            System.out.println("finish shop stat");
            
            
            //统计品牌商家数据==================================================
            if(subFlag == NOT_SUB_TABLE)
            {
                sql =  getBrandShopType2MidSql(CITY_NAME, CITY_CODE);
                //System.out.println(sql);
                executesqlForUpdate(conn,sql);
                sql =  getBrandShopType3MidSql(CITY_NAME, CITY_CODE);
                //System.out.println(sql);
                executesqlForUpdate(conn,sql);
            }
            else if (subFlag == SUB_TABLE)
            {
                sql =  getSubTableBrandShopType2MidSql(CITY_NAME, CITY_CODE,CG_SUB_TABLR_SHOP,CG_SUB_TABLE_SHOP_TYPE2);
                //System.out.println(sql);
                executesqlForUpdate(conn,sql);
                sql =  getSubTableBrandShopType3MidSql(CITY_NAME, CITY_CODE,CG_SUB_TABLR_SHOP,CG_SUB_TABLE_SHOP_TYPE3);
                //System.out.println(sql);
                executesqlForUpdate(conn,sql);
            }
            
            //和分表无关 临时统计表内部运算
            sql =  getBrandShopType2TempSql(CITY_NAME, CITY_CODE);
            //System.out.println(sql);
            executesqlForUpdate(conn,sql);
            sql =  getBrandShopType3TempSql(CITY_NAME, CITY_CODE);
            //System.out.println(sql);
            executesqlForUpdate(conn,sql);
            System.out.println("finish brandshop stat");
            
            
            //统计品牌数据========================================================
            if(subFlag == NOT_SUB_TABLE)
            {
                sql =  getBrandType2MidSql(CITY_NAME, CITY_CODE);
                //System.out.println(sql);
                executesqlForUpdate(conn,sql);
                sql =  getBrandType3MidSql(CITY_NAME, CITY_CODE);
                //System.out.println(sql);
                executesqlForUpdate(conn,sql);
            }
            else if (subFlag == SUB_TABLE)
            {
                sql =  getSubTableBrandType2MidSql(CITY_NAME, CITY_CODE,CG_SUB_TABLR_SHOP,CG_SUB_TABLE_SHOP_TYPE2);
                //System.out.println(sql);
                executesqlForUpdate(conn,sql);
                sql =  getSubTableBrandType3MidSql(CITY_NAME, CITY_CODE,CG_SUB_TABLR_SHOP,CG_SUB_TABLE_SHOP_TYPE3);
                //System.out.println(sql);
                executesqlForUpdate(conn,sql);
            }
            
            //和分表无关 临时统计表内部运算
            sql =  getBrandType2TempSql(CITY_NAME, CITY_CODE);
            //System.out.println(sql);
            executesqlForUpdate(conn,sql);
            sql =  getBrandType3TempSql(CITY_NAME, CITY_CODE);
            //System.out.println(sql);
            executesqlForUpdate(conn,sql);
            System.out.println("finish brand stat");
            
            //入统计表
            //先删除表中旧的统计数据 
            if(subFlag == NOT_SUB_TABLE)
            {
                sql = getDelCityOldStatDataSql(PROVINCE_NAME, PROVINCE_CODE,CITY_NAME, CITY_CODE);
                System.out.println(sql);
                executesqlForUpdate(conn,sql);
                //入新的数据
                sql =  getStatType2Sql(PROVINCE_NAME, PROVINCE_CODE,CG_SHOP_STAT);
                executesqlForUpdate(conn,sql);
                sql =  getStatType3Sql(PROVINCE_NAME, PROVINCE_CODE,CG_SHOP_STAT);
                executesqlForUpdate(conn,sql);
            }
            else if (subFlag == SUB_TABLE)
            {
                sql = getSubTableDelCityOldStatDataSql(PROVINCE_NAME, PROVINCE_CODE,CITY_NAME, CITY_CODE,CG_SUB_TABLR_SHOP_STAT);
                System.out.println(sql);
                executesqlForUpdate(conn,sql);
                //入新的数据
                sql =  getSubTableStatType2Sql(CG_SUB_TABLR_SHOP_STAT);
                executesqlForUpdate(conn,sql);
                sql =  getSubTableStatType3Sql(CG_SUB_TABLR_SHOP_STAT);
                executesqlForUpdate(conn,sql);
            }
            
            System.out.println("finish " + PROVINCE_NAME + "->"+CITY_NAME + " stat.");
        }
        catch (Exception e)
        {
            //e.printStackTrace();
        }
        finally
        {
            closeStream(conn);
        }
        
        long time2 = System.currentTimeMillis();
        
        System.out.println("end this stat. time:"+ (time2 - time1)/1000 + " second.");
    }
    
    /**
     * 
     * 城市和业态模板数据 城市和一级，二级业态
     * @param cityname
     * @param citycode
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    
    private String getCityType2Tpl(String cityname,String citycode)
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append(" insert into cg_city_type (citycode,cityname,areacode,areaname,typelevel,type1id,type1name,type2id,type2name) \n")
       .append("  select {citycode},'{cityname}',c.id,c.name,2 typelevel,t.type1id,t.type1name,t.type2id,t.type2name \n")
        .append("  from t_type2Relation t,cg_city c where c.upid = {citycode} \n");
        
        String sql = sb.toString();
        sql = sql.replace("{citycode}", citycode);
        sql = sql.replace("{cityname}", cityname);
        return sql;
        
    }
    
    /**
     * 城市和业态模板数据 城市和三级业态
     * <功能详细描述>
     * @param cityname
     * @param citycode
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    
    private String getCityType3Tpl(String cityname,String citycode)
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append("  insert into cg_city_type (citycode,cityname,areacode,areaname,typelevel,type1id,type1name,type2id,type2name,type3id,type3name)  \n")
                .append(" select {citycode},'{cityname}',c.id,c.name,3 typelevel,t.type1id,t.type1name,t.type2id,t.type2name,t.type3id,t.type3name  \n")
                .append("  from t_type3Relation t,cg_city c where c.upid = {citycode} \n");
        
        String sql = sb.toString();
        sql = sql.replace("{citycode}", citycode);
        sql = sql.replace("{cityname}", cityname);
        return sql;
        
    }
    
    /**
     * 统计商家数据 2级业态 入中间临时存储表
     * <功能详细描述>
     * @param cityname
     * @param citycode
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    
    private String getShopType2MidSql(String cityname,String citycode)
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append(" insert into cg_shop_stat_mid (citycode,cityname,areacode,stattype,statname,typelevel,type2id,num)  \n")
                .append(" select {citycode},'{cityname}',s.city,1,'shop',2,r.type,count(*) \n")
                .append(" from cg_mid_poi_type r,cg_mid_poi_shop s  \n")
                .append(" where r.brand_id = s.id  and s.citycode = {citycode} \n")
                .append(" group by s.citycode,s.areacode,r.type \n");
        
        
        String sql = sb.toString();
        sql = sql.replace("{citycode}", citycode);
        sql = sql.replace("{cityname}", cityname);
        return sql;
        
    }
    
    /**
     * 统计商家数据 2级业态 入中间临时存储表
     * <功能详细描述>
     * @param cityname
     * @param citycode
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    
    private String getSubTableShopType2MidSql(String cityname,String citycode,String shopTable,String shoptype2Table)
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append(" insert into cg_shop_stat_mid (citycode,cityname,areacode,stattype,statname,typelevel,type2id,num)  \n")
                .append(" select {citycode},'{cityname}',s.areacode,1,'shop',2,r.type_id,count(*) \n")
                .append(" from {shoptype2Table} r,{shopTable} s  \n")
                .append(" where r.shop_id = s.id  and s.citycode = {citycode} and r.citycode = {citycode} \n")
                .append(" group by s.citycode,s.areacode,r.type_id \n");
        
        String sql = sb.toString();
        sql = sql.replace("{citycode}", citycode);
        sql = sql.replace("{cityname}", cityname);
        sql = sql.replace("{shopTable}", shopTable);
        sql = sql.replace("{shoptype2Table}", shoptype2Table);
        return sql;
        
    }
    
    /**
     * 统计商家数据 3级业态 入中间临时存储表
     * <功能详细描述>
     * @param cityname
     * @param citycode
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private String getShopType3MidSql(String cityname,String citycode)
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append(" insert into cg_shop_stat_mid (citycode,cityname,areacode,stattype,statname,typelevel,type3id,num)  \n")
                .append(" select {citycode},'{cityname}',s.city,1,'shop',3,r.style_id,count(*) \n")
                .append(" from cg_mid_poi_style_relation r,cg_mid_poi_shop s  \n")
                .append(" where r.brand_id = s.id  and s.citycode = {citycode} \n")
                .append(" group by citycode,areacode,style_id \n");
        
        
        String sql = sb.toString();
        sql = sql.replace("{citycode}", citycode);
        sql = sql.replace("{cityname}", cityname);
        return sql;
        
    }
    
    /**
     * 统计商家数据 3级业态 入中间临时存储表
     * <功能详细描述>
     * @param cityname
     * @param citycode
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private String getSubTableShopType3MidSql(String cityname,String citycode,String shopTable,String shoptype3Table)
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append(" insert into cg_shop_stat_mid (citycode,cityname,areacode,stattype,statname,typelevel,type3id,num)  \n")
                .append(" select {citycode},'{cityname}',s.areacode,1,'shop',3,r.type_id,count(*) \n")
                .append(" from {shoptype3Table} r,{shopTable} s  \n")
                .append(" where r.shop_id = s.id  and s.citycode = {citycode} and r.citycode = {citycode} \n")
                .append(" group by s.citycode,s.areacode,r.type_id \n");
        
        
        String sql = sb.toString();
        sql = sql.replace("{citycode}", citycode);
        sql = sql.replace("{cityname}", cityname);
        sql = sql.replace("{shopTable}", shopTable);
        sql = sql.replace("{shoptype3Table}", shoptype3Table);
        return sql;
        
    }
    
    /**
     * 统计商家数据2级业态 入二级缓存临时表
     * <功能详细描述>
     * @param cityname
     * @param citycode
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    
    private String getShopType2TempSql(String cityname,String citycode)
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append(" insert into cg_shop_stat_temp  \n")
                .append("  (cityName,areaName,statName,type1Name,type2Name,type3Name,num, \n")
                .append("  citycode,areacode,stattype,type1id,type2id,type3id,typelevel) \n")
                .append("  select ct.cityname,ct.areaName,'shop',ct.type1Name,ct.type2Name,'00' type3name,ifnull(stat.num,0), \n")
                .append("         ct.citycode,ct.areacode,1 stattype,ct.type1id,ct.type2id,0 type3id,ct.typelevel \n")
                .append("  from cg_city_type ct left join cg_shop_stat_mid stat \n")
                .append("  on (stat.typelevel = 2  \n")
                .append("       and stat.stattype = 1 \n")
                .append("       and stat.type2id =ct.type2id \n")
                .append("       and stat.areacode = ct.areacode ) \n")
                .append("  where  ct.typelevel = 2 \n")
                .append("  order by ct.citycode,ct.areacode,ct.type1id \n");
        
        String sql = sb.toString();
        return sql;
        
    }
    
    /**
     * 统计商家数据3级业态 入二级缓存临时表
     * <功能详细描述>
     * @param cityname
     * @param citycode
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private String getShopType3TempSql(String cityname,String citycode)
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append(" insert into cg_shop_stat_temp \n")
                .append("   (cityName,areaName,statName,type1Name,type2Name,type3Name,num, \n")
                .append("   citycode,areacode,stattype,type1id,type2id,type3id,typelevel) \n")
                .append("   select ct.cityname,ct.areaName,'shop',ct.type1Name,ct.type2Name,ct.type3Name,ifnull(stat.num,0), \n")
                .append("          ct.citycode,ct.areacode,1 stattype,ct.type1id,ct.type2id,ct.type3id,ct.typelevel \n")
                .append("    from cg_city_type ct left join cg_shop_stat_mid stat \n")
                .append("    on (stat.typelevel = 3 \n")
                .append("        and stat.stattype = 1 \n")
                .append("        and stat.type3id =ct.type3id \n")
                .append("        and stat.areacode = ct.areacode ) \n")
                .append("   where  ct.typelevel = 3 \n")
                .append("   order by ct.citycode,ct.areacode,ct.type1id \n");
        
        String sql = sb.toString();
        return sql;
        
    }
    
    /**
     * 统计品牌商家数据2级业态 入临时表
     * <功能详细描述>
     * @param cityname
     * @param citycode
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private String getBrandShopType2MidSql(String cityname,String citycode)
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append(" insert into cg_shop_stat_mid (citycode,cityname,areacode,stattype,statname,typelevel,type2id,num)  \n")
        .append(" select {citycode},'{cityname}',s.city,2,'brandshop',2,r.type,count(*) \n")
        .append(" from cg_mid_poi_type r,cg_mid_poi_shop s  \n")
        .append(" where r.brand_id = s.id and s.brand_id > 0 and s.citycode = {citycode} \n")
        .append(" group by s.citycode,s.areacode,r.type  \n");
        
        String sql = sb.toString();
        sql = sql.replace("{citycode}", citycode);
        sql = sql.replace("{cityname}", cityname);
        return sql;
        
    }
    
    /**
     * 统计品牌商家数据2级业态 入临时表
     * <功能详细描述>
     * @param cityname
     * @param citycode
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private String getSubTableBrandShopType2MidSql(String cityname,String citycode,String shopTable,String shoptype2Table)
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append(" insert into cg_shop_stat_mid (citycode,cityname,areacode,stattype,statname,typelevel,type2id,num)  \n")
        .append(" select {citycode},'{cityname}',s.areacode,2,'brandshop',2,r.type_id,count(*) \n")
        .append(" from {shoptype2Table} r,{shopTable} s  \n")
        .append(" where r.shop_id = s.id and s.brand_id > 0 and s.citycode = {citycode} and r.citycode = {citycode} \n")
        .append(" group by s.citycode,s.areacode,r.type_id  \n");
        
        String sql = sb.toString();
        sql = sql.replace("{citycode}", citycode);
        sql = sql.replace("{cityname}", cityname);
        sql = sql.replace("{shopTable}", shopTable);
        sql = sql.replace("{shoptype2Table}", shoptype2Table);
        return sql;
        
    }
    
    /**
     * 统计品牌商家数据3级业态 入临时表
     * <功能详细描述>
     * @param cityname
     * @param citycode
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private String getBrandShopType3MidSql(String cityname,String citycode)
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append(" insert into cg_shop_stat_mid (citycode,cityname,areacode,stattype,statname,typelevel,type3id,num) \n")
        .append(" select {citycode},'{cityname}',s.city,2,'brandshop',3,r.style_id,count(*) \n")
        .append(" from cg_mid_poi_style_relation r,cg_mid_poi_shop s  \n")
        .append(" where r.brand_id = s.id and s.brand_id > 0 and s.citycode = {citycode} \n")
        .append(" group by citycode,areacode,style_id  \n");
        
        String sql = sb.toString();
        sql = sql.replace("{citycode}", citycode);
        sql = sql.replace("{cityname}", cityname);
        return sql;
        
    }
    
    /**
     * 统计品牌商家数据3级业态 入临时表
     * <功能详细描述>
     * @param cityname
     * @param citycode
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private String getSubTableBrandShopType3MidSql(String cityname,String citycode,String shopTable,String shoptype3Table)
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append(" insert into cg_shop_stat_mid (citycode,cityname,areacode,stattype,statname,typelevel,type3id,num) \n")
        .append(" select {citycode},'{cityname}',s.areacode,2,'brandshop',3,r.type_id,count(*) \n")
        .append(" from {shoptype3Table} r,{shopTable} s  \n")
        .append(" where r.shop_id = s.id and s.brand_id > 0 and s.citycode = {citycode} and r.citycode = {citycode} \n")
        .append(" group by s.citycode,s.areacode,r.type_id  \n");
        
        String sql = sb.toString();
        sql = sql.replace("{citycode}", citycode);
        sql = sql.replace("{cityname}", cityname);
        sql = sql.replace("{shopTable}", shopTable);
        sql = sql.replace("{shoptype3Table}", shoptype3Table);
        return sql;
        
    }
    
    /**
     * 统计品牌商家数据2级业态 入二级缓存临时表
     * <功能详细描述>
     * @param cityname
     * @param citycode
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private String getBrandShopType2TempSql(String cityname,String citycode)
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append(" insert into cg_shop_stat_temp \n")
                .append("   (cityName,areaName,statName,type1Name,type2Name,type3Name,num, \n")
                .append("     citycode,areacode,stattype,type1id,type2id,type3id,typelevel) \n")
                .append("   select ct.cityname,ct.areaName,'shop',ct.type1Name,ct.type2Name,'00' type3name,ifnull(stat.num,0), \n")
                .append("          ct.citycode,ct.areacode,2 stattype,ct.type1id,ct.type2id,0 type3id,ct.typelevel \n")
                .append("   from cg_city_type ct left join cg_shop_stat_mid stat \n")
                .append("   on (stat.typelevel = 2  \n")
                .append("       and stat.stattype = 2 \n")
                .append("        and stat.type2id =ct.type2id \n")
                .append("        and stat.areacode = ct.areacode ) \n")
                .append("   where  ct.typelevel = 2 \n")
                .append("  order by ct.citycode,ct.areacode,ct.type1id \n");
        
        String sql = sb.toString();
        return sql;
        
    }
    
    /**
     * 统计品牌商家数据3级业态 入二级缓存临时表
     * <功能详细描述>
     * @param cityname
     * @param citycode
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    
    private String getBrandShopType3TempSql(String cityname,String citycode)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("  insert into cg_shop_stat_temp \n")
                .append("  (cityName,areaName,statName,type1Name,type2Name,type3Name,num, \n")
                .append("  citycode,areacode,stattype,type1id,type2id,type3id,typelevel) \n")
                .append("  select ct.cityname,ct.areaName,'shop',ct.type1Name,ct.type2Name,ct.type3Name,ifnull(stat.num,0), \n")
                .append("        ct.citycode,ct.areacode,2 stattype,ct.type1id,ct.type2id,ct.type3id,ct.typelevel \n")
                .append("  from cg_city_type ct left join cg_shop_stat_mid stat \n")
                .append("   on (stat.typelevel = 3 \n")
                .append("       and stat.stattype = 2 \n")
                .append("       and stat.type3id =ct.type3id \n")
                .append("       and stat.areacode = ct.areacode ) \n")
                .append("   where  ct.typelevel = 3 \n")
                .append("  order by ct.citycode,ct.areacode,ct.type1id \n");    
        
        String sql = sb.toString();
        return sql;
        
    }
    
    /**
     * 统计品牌数据2级业态 入临时表
     * <功能详细描述>
     * @param cityname
     * @param citycode
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private String getBrandType2MidSql(String cityname,String citycode)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(" insert into cg_shop_stat_mid (citycode,cityname,areacode,stattype,statname,typelevel,type2id,brand_id,num)  \n")
                .append(" select {citycode},'{cityname}',s.city,3,'brand',2,r.type,s.brand_id,count(*) \n")
                .append(" from cg_mid_poi_type r,cg_mid_poi_shop s  \n")
                .append(" where r.brand_id = s.id and s.brand_id > 0 and s.citycode = {citycode} \n")
                .append(" group by s.citycode,s.areacode,r.type,s.brand_id  \n");
        
        String sql = sb.toString();
        sql = sql.replace("{citycode}", citycode);
        sql = sql.replace("{cityname}", cityname);
        return sql;
        
    }
    
    /**
     * 统计品牌数据2级业态 入临时表
     * <功能详细描述>
     * @param cityname
     * @param citycode
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private String getSubTableBrandType2MidSql(String cityname,String citycode,String shopTable,String shoptype2Table)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(" insert into cg_shop_stat_mid (citycode,cityname,areacode,stattype,statname,typelevel,type2id,brand_id,num)  \n")
                .append(" select {citycode},'{cityname}',s.areacode,3,'brand',2,r.type_id,s.brand_id,count(*) \n")
                .append(" from {shoptype2Table} r,{shopTable} s  \n")
                .append(" where r.shop_id = s.id and s.brand_id > 0 and s.citycode = {citycode}  and r.citycode = {citycode} \n")
                .append(" group by s.citycode,s.areacode,r.type_id,s.brand_id  \n");
        
        String sql = sb.toString();
        sql = sql.replace("{citycode}", citycode);
        sql = sql.replace("{cityname}", cityname);
        sql = sql.replace("{shopTable}", shopTable);
        sql = sql.replace("{shoptype2Table}", shoptype2Table);
        return sql;
        
    }
    
    /**
     * 统计品牌数据3级业态 入临时表
     * <功能详细描述>
     * @param cityname
     * @param citycode
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private String getBrandType3MidSql(String cityname,String citycode)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(" insert into cg_shop_stat_mid (citycode,cityname,areacode,stattype,statname,typelevel,type3id,brand_id,num) \n")
                .append(" select {citycode},'{cityname}',s.city,3,'brand',3,r.style_id,s.brand_id,count(*) \n")
                .append(" from cg_mid_poi_style_relation r,cg_mid_poi_shop s  \n")
                .append(" where r.brand_id = s.id and s.brand_id > 0 and s.citycode = {citycode} \n")
                .append(" group by citycode,areacode,style_id ,s.brand_id \n");
        
        String sql = sb.toString();
        sql = sql.replace("{citycode}", citycode);
        sql = sql.replace("{cityname}", cityname);
        return sql;
        
    }
    
    /**
     * 统计品牌数据3级业态 入临时表
     * <功能详细描述>
     * @param cityname
     * @param citycode
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private String getSubTableBrandType3MidSql(String cityname,String citycode,String shopTable,String shoptype3Table)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(" insert into cg_shop_stat_mid (citycode,cityname,areacode,stattype,statname,typelevel,type3id,brand_id,num) \n")
                .append(" select {citycode},'{cityname}',s.areacode,3,'brand',3,r.type_id,s.brand_id,count(*) \n")
                .append(" from {shoptype3Table} r,{shopTable} s  \n")
                .append(" where r.shop_id = s.id and s.brand_id > 0 and s.citycode = {citycode}  and r.citycode = {citycode} \n")
                .append(" group by s.citycode,s.areacode,r.type_id ,s.brand_id \n");
        
        String sql = sb.toString();
        sql = sql.replace("{citycode}", citycode);
        sql = sql.replace("{cityname}", cityname);
        sql = sql.replace("{shopTable}", shopTable);
        sql = sql.replace("{shoptype3Table}", shoptype3Table);
        return sql;
        
    }
    
    /**
     * 统计品牌数据2级业态 入二级缓存临时表
     * <功能详细描述>
     * @param cityname
     * @param citycode
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private String getBrandType2TempSql(String cityname,String citycode)
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append("  insert into cg_shop_stat_temp \n")
                .append("  (cityName,areaName,statName,type1Name,type2Name,type3Name,num, \n")
                .append("   citycode,areacode,stattype,type1id,type2id,type3id,typelevel) \n")
                .append("   select ct.cityname,ct.areaName,'shop',ct.type1Name,ct.type2Name,'00' type3name,ifnull(stat1.num,0), \n")
                .append("         ct.citycode,ct.areacode,3 stattype,ct.type1id,ct.type2id,0 type3id,ct.typelevel \n")
                .append("   from cg_city_type ct left join \n")
                .append("   ( \n ")
                .append("      select stat.cityname,city.name,stat.statname, \n")
                .append("               type1.type1name,type1.type2name,'00' type3name,count(*) num, \n")
                .append("              stat.citycode, stat.areacode, stat.stattype, type1.type1id,stat.type2id,0 type3id,stat.typelevel \n")
                .append("      from cg_shop_stat_mid stat, \n")
                .append("          t_type2Relation  type1, \n")
                .append("          cg_city city \n")
                .append("      where stat.typelevel = 2  \n")
                .append("      and stat.stattype = 3  \n")
                .append("      and stat.type2id = type1.type2id  \n")
                .append("      and stat.areacode = city.id \n")
                .append("      group by stat.citycode,stat.areacode,type1.type1id,type1.type2id \n")
                .append("      order by stat.citycode,stat.areacode,type1.type1id,type1.type2id \n")
                .append("   ) stat1 \n")
                .append("  on (stat1.typelevel = 2  \n")
                .append("       and stat1.stattype = 3 \n")
                .append("       and stat1.type2id =ct.type2id \n")
                .append("       and stat1.areacode = ct.areacode ) \n")
                .append("   where  ct.typelevel = 2 \n")
                .append("  order by ct.citycode,ct.areacode,ct.type1id \n");
        
        String sql = sb.toString();
        return sql;
        
    }
    
    /**
     * 统计品牌数据3级业态 入二级缓存临时表
     * <功能详细描述>
     * @param cityname
     * @param citycode
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private String getBrandType3TempSql(String cityname,String citycode)
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append("   insert into cg_shop_stat_temp \n")
                .append("  (cityName,areaName,statName,type1Name,type2Name,type3Name,num, \n")
                .append("   citycode,areacode,stattype,type1id,type2id,type3id,typelevel) \n")
                .append("   select ct.cityname,ct.areaName,'shop',ct.type1Name,ct.type2Name,ct.type3Name,ifnull(stat1.num,0), \n")
                .append("         ct.citycode,ct.areacode,3 stattype,ct.type1id,ct.type2id,ct.type3id,ct.typelevel \n")
                .append("   from cg_city_type ct left join \n")
                .append("  ( \n")
                .append("       select stat.cityname,city.name, \n")
                .append("       type1.type1name,type1.type2name,type1.type3name, \n")
                .append("       stat.statname,count(*) num, \n")
                .append("       stat.citycode, stat.areacode,  \n")
                .append("       stat.stattype, type1.type1id,stat.type2id,stat.type3id,stat.typelevel \n")
                .append("     from cg_shop_stat_mid stat, \n")
                .append("          t_type3Relation  type1, \n")
                .append("          cg_city city \n")
                .append("      where stat.typelevel = 3  \n")
                .append("       and stat.stattype = 3  \n")
                .append("      and stat.type3id = type1.type3id  \n")
                .append("       and stat.areacode = city.id \n")
                .append("       group by stat.citycode,stat.areacode,type1.type1id,type1.type2id,type1.type3id \n")
                .append("       order by stat.citycode,stat.areacode,type1.type1id,type1.type2id \n")
                .append("    ) stat1 \n")
                .append("  on (stat1.typelevel = 3 \n")
                .append("       and stat1.stattype = 3 \n")
                .append("        and stat1.type3id =ct.type3id \n")
                .append("        and stat1.areacode = ct.areacode ) \n")
                .append("   where  ct.typelevel = 3 \n")
                .append("  order by ct.citycode,ct.areacode,ct.type1id \n");
        
        String sql = sb.toString();
        return sql;
        
    }
    
    /**
     * 统计结果入统计表 2级业态
     * <功能详细描述>
     * @param provincename
     * @param provincecode
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private String getStatType2Sql(String provincename,String provincecode,String statTable)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("  insert into {statTable} \n")
                .append("  (province,proName,citycode,cityName,areacode,areaName, \n")
                .append("  typelevel,type1id,type1Name,type2id,type2Name,type3id,type3Name, \n")
                .append("   shopnum,brandshopnum,brandnum) \n")
                .append("   select {provincecode},'{provincename}',s1.citycode,s1.cityName,s1.areacode,s1.areaName,2 typelevel, \n")
                .append("         s1.type1id,s1.type1Name,s1.type2id,s1.type2Name,s1.type3id,s1.type3Name, \n")
                .append("         s1.num numshops ,ifnull(s2.num,0) numbrandshops,ifnull(s3.num,0) numbrands \n")
                .append("  from cg_shop_stat_temp s1  \n")
                .append("  left join cg_shop_stat_temp s2  \n")
                .append("  on (s1.citycode = s2.citycode  \n")
                .append("  and s1.areacode = s2.areacode  \n")
                .append("  and s1.type1id= s2.type1id  \n")
                .append("  and s1.type2id = s2.type2id \n")
                .append("  and s1.type3id = s2.type3id \n")
                .append("  and s2.stattype = 2 and s2.typelevel = 2) \n")
                .append("  left join cg_shop_stat_temp s3  \n")
                .append("  on (s1.citycode = s3.citycode  \n")
                .append("  and s1.areacode = s3.areacode  \n")
                .append("  and s1.type1id= s3.type1id  \n")
                .append("  and s1.type2id = s3.type2id \n")
                .append("  and s1.type3id = s3.type3id \n")
                .append("  and s3.stattype = 3 and s3.typelevel = 2) \n")
                .append("  where s1.stattype = 1 and s1.typelevel = 2 \n");
        
        String sql = sb.toString();
        sql = sql.replace("{provincecode}", provincecode);
        sql = sql.replace("{provincename}", provincename);
        sql = sql.replace("{statTable}", statTable);
        return sql;
        
    }
    
    /**
     * 统计结果入统计表 2级业态
     * <功能详细描述>
     * @param provincename
     * @param provincecode
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private String getSubTableStatType2Sql(String statTable)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("  insert into {statTable} \n")
                .append("  (citycode,cityName,areacode,areaName, \n")
                .append("  typelevel,type1id,type1Name,type2id,type2Name,type3id,type3Name, \n")
                .append("   shopnum,brandshopnum,brandnum) \n")
                .append("   select s1.citycode,s1.cityName,s1.areacode,s1.areaName,2 typelevel, \n")
                .append("         s1.type1id,s1.type1Name,s1.type2id,s1.type2Name,s1.type3id,s1.type3Name, \n")
                .append("         s1.num numshops ,ifnull(s2.num,0) numbrandshops,ifnull(s3.num,0) numbrands \n")
                .append("  from cg_shop_stat_temp s1  \n")
                .append("  left join cg_shop_stat_temp s2  \n")
                .append("  on (s1.citycode = s2.citycode  \n")
                .append("  and s1.areacode = s2.areacode  \n")
                .append("  and s1.type1id= s2.type1id  \n")
                .append("  and s1.type2id = s2.type2id \n")
                .append("  and s1.type3id = s2.type3id \n")
                .append("  and s2.stattype = 2 and s2.typelevel = 2) \n")
                .append("  left join cg_shop_stat_temp s3  \n")
                .append("  on (s1.citycode = s3.citycode  \n")
                .append("  and s1.areacode = s3.areacode  \n")
                .append("  and s1.type1id= s3.type1id  \n")
                .append("  and s1.type2id = s3.type2id \n")
                .append("  and s1.type3id = s3.type3id \n")
                .append("  and s3.stattype = 3 and s3.typelevel = 2) \n")
                .append("  where s1.stattype = 1 and s1.typelevel = 2 \n");
        
        String sql = sb.toString();
        sql = sql.replace("{statTable}", statTable);
        return sql;
        
    }
    
    /**
     * 统计结果入统计表 3级业态
     * <功能详细描述>
     * @param provincename
     * @param provincecode
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private String getStatType3Sql(String provincename,String provincecode,String statTable)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(" insert into {statTable} \n")
                .append(" (province,proName,citycode,cityName,areacode,areaName, \n")
                .append(" typelevel,type1id,type1Name,type2id,type2Name,type3id,type3Name, \n")
                .append(" shopnum,brandshopnum,brandnum) \n")
                .append("  select {provincecode},'{provincename}',s1.citycode,s1.cityName,s1.areacode,s1.areaName,3 typelevel, \n")
                .append("        s1.type1id,s1.type1Name,s1.type2id,s1.type2Name,s1.type3id,s1.type3Name, \n")
                .append("       s1.num numshops ,ifnull(s2.num,0) numbrandshops,ifnull(s3.num,0) numbrands \n")
                .append("  from cg_shop_stat_temp s1  \n")
                .append(" left join cg_shop_stat_temp s2  \n")
                .append(" on (s1.citycode = s2.citycode  \n")
                .append(" and s1.areacode = s2.areacode  \n")
                .append(" and s1.type1id= s2.type1id  \n")
                .append(" and s1.type2id = s2.type2id \n")
                .append(" and s1.type3id = s2.type3id \n")
                .append(" and s2.stattype = 2 and s2.typelevel = 3) \n")
                .append(" left join cg_shop_stat_temp s3  \n")
                .append(" on (s1.citycode = s3.citycode  \n")
                .append(" and s1.areacode = s3.areacode  \n")
                .append(" and s1.type1id= s3.type1id  \n")
                .append(" and s1.type2id = s3.type2id \n")
                .append(" and s1.type3id = s3.type3id \n")
                .append(" and s3.stattype = 3 and s3.typelevel = 3) \n")
                .append(" where s1.stattype = 1 and s1.typelevel = 3 \n");
        
        String sql = sb.toString();
        sql = sql.replace("{provincecode}", provincecode);
        sql = sql.replace("{provincename}", provincename);
        sql = sql.replace("{statTable}", statTable);
        return sql;
        
    }
    
    /**
     * 统计结果入统计表 3级业态
     * <功能详细描述>
     * @param provincename
     * @param provincecode
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private String getSubTableStatType3Sql(String statTable)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(" insert into {statTable} \n")
                .append(" (citycode,cityName,areacode,areaName, \n")
                .append(" typelevel,type1id,type1Name,type2id,type2Name,type3id,type3Name, \n")
                .append(" shopnum,brandshopnum,brandnum) \n")
                .append("  select s1.citycode,s1.cityName,s1.areacode,s1.areaName,3 typelevel, \n")
                .append("        s1.type1id,s1.type1Name,s1.type2id,s1.type2Name,s1.type3id,s1.type3Name, \n")
                .append("       s1.num numshops ,ifnull(s2.num,0) numbrandshops,ifnull(s3.num,0) numbrands \n")
                .append("  from cg_shop_stat_temp s1  \n")
                .append(" left join cg_shop_stat_temp s2  \n")
                .append(" on (s1.citycode = s2.citycode  \n")
                .append(" and s1.areacode = s2.areacode  \n")
                .append(" and s1.type1id= s2.type1id  \n")
                .append(" and s1.type2id = s2.type2id \n")
                .append(" and s1.type3id = s2.type3id \n")
                .append(" and s2.stattype = 2 and s2.typelevel = 3) \n")
                .append(" left join cg_shop_stat_temp s3  \n")
                .append(" on (s1.citycode = s3.citycode  \n")
                .append(" and s1.areacode = s3.areacode  \n")
                .append(" and s1.type1id= s3.type1id  \n")
                .append(" and s1.type2id = s3.type2id \n")
                .append(" and s1.type3id = s3.type3id \n")
                .append(" and s3.stattype = 3 and s3.typelevel = 3) \n")
                .append(" where s1.stattype = 1 and s1.typelevel = 3 \n");
        
        String sql = sb.toString();
        sql = sql.replace("{statTable}", statTable);
        return sql;
        
    }
    
    /**
     * <一句话功能简述>
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private String getDelCityOldStatDataSql(String provincename,String provincecode,String cityname,String citycode)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(" delete from cg_shop_stat  \n")
                .append(" where province ={provincecode}  \n")
                .append(" and proName ='{provincename}'  \n")
                .append(" and citycode ={citycode}  \n")
                .append(" and cityName ='{cityname}'  \n");
        
        String sql = sb.toString();
        sql = sql.replace("{provincecode}", provincecode);
        sql = sql.replace("{provincename}", provincename);
        sql = sql.replace("{citycode}", citycode);
        sql = sql.replace("{cityname}", cityname);
        return sql;
    }
    
    /**
     * <一句话功能简述>
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private String getSubTableDelCityOldStatDataSql(String provincename,String provincecode,String cityname,String citycode,String statTable)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(" delete from {statTable}  \n")
                .append(" where citycode ={citycode}  \n")
                .append(" and cityName ='{cityname}'  \n");
        
        String sql = sb.toString();
        sql = sql.replace("{citycode}", citycode);
        sql = sql.replace("{cityname}", cityname);
        sql = sql.replace("{statTable}", statTable);
        return sql;
    }
    
    /**
     * <一句话功能简述>
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private void truncateTables(Connection conn)
    {
        executesqlForUpdate(conn,"truncate table cg_shop_stat_mid");
        executesqlForUpdate(conn,"truncate table cg_shop_stat_temp");
        executesqlForUpdate(conn,"truncate table cg_city_type");
        
    }
    
    /**
     * <一句话功能简述>
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private void dropTables(Connection conn)
    {
        executesqlForUpdate(conn,"drop table cg_shop_stat_mid ");
        executesqlForUpdate(conn,"drop table cg_shop_stat_temp ");
        executesqlForUpdate(conn,"drop table cg_city_type ");
        executesqlForUpdate(conn,"drop table t_type2Relation ");
        executesqlForUpdate(conn,"drop table t_type3Relation ");
        
    }
    
    
    private void createTempTables(Connection conn)
    {
        
        StringBuilder sb = new StringBuilder();
        
        sb.append(" CREATE TABLE `cg_shop_stat_mid` ( \n")
                .append("    `citycode` int(10) NOT NULL DEFAULT 0  COMMENT '城市id', \n")
                .append("    `cityName` VARCHAR(100) NOT NULL DEFAULT '00'  COMMENT '城市名称', \n")
                .append("    `areacode` int(10) NOT NULL DEFAULT 0  COMMENT '地区id', \n")
                .append("     `areaName` VARCHAR(100) NOT NULL DEFAULT '00'  COMMENT '地区名称', \n")
                .append("     `stattype` smallint(2) NOT NULL  COMMENT '统计类别 1 商家数 2 品牌店数 3 品牌个数', \n")
                .append("     `statName` VARCHAR(30) NOT NULL DEFAULT '00'  COMMENT '统计类别描述 内容自定 自己可以区分开即可', \n")
                .append("    `typelevel` smallint(2) NOT NULL  COMMENT '统计级别 2 二级业态 3 三级业态', \n")
                .append("     `type1id` smallint(10) NOT NULL DEFAULT 0  COMMENT '一级业态id', \n")
                .append("     `type1Name` VARCHAR(50) NOT NULL DEFAULT '00' COMMENT  '一级业态名称', \n")
                .append("     `type2id` smallint(10) NOT NULL DEFAULT 0  COMMENT '二级业态id', \n")
                .append("     `type2Name` VARCHAR(50) NOT NULL DEFAULT '00'  COMMENT '二级业态名称', \n")
                .append("     `type3id` smallint(10) NOT NULL DEFAULT 0  COMMENT '三级业态id', \n")
                .append("     `type3Name` VARCHAR(50) NOT NULL DEFAULT '00'  COMMENT '三级业态名称', \n")
                .append("      `brand_id`  int(11) NULL  COMMENT '品牌id', \n")
                .append("     `num` int(11) NOT NULL  COMMENT '统计值', \n")
                .append("      INDEX `shop_stat_mid_index` (`citycode`,`areacode`, `stattype`,`typelevel`) \n")
                .append("  ) \n")
                .append(" COMMENT='商家统计临时缓存表' \n")
                .append(" COLLATE='utf8_general_ci' \n")
                .append(" ENGINE=MyISAM \n");
        
        
        executesqlForUpdate(conn,sb.toString());
        sb.delete(0, sb.length());
        
        sb.append(" CREATE TABLE `cg_shop_stat_temp` ( \n")
                .append("   `citycode` int(10) NOT NULL DEFAULT 0  COMMENT '城市id', \n")
                .append("   `cityName` VARCHAR(100) NOT NULL DEFAULT '00'  COMMENT '城市名称', \n")
                .append("   `areacode` int(10) NOT NULL DEFAULT 0  COMMENT '地区id', \n")
                .append("    `areaName` VARCHAR(100) NOT NULL DEFAULT '00' COMMENT  '地区名称', \n")
                .append("   `stattype` smallint(2) NOT NULL  COMMENT '统计类别 1 商家数 2 品牌店数 3 品牌个数', \n")
                .append("   `statName` VARCHAR(30) NOT NULL DEFAULT '00'  COMMENT '统计类别描述 内容自定 自己可以区分开即可', \n")
                .append("   `typelevel` smallint(2) NOT NULL  COMMENT '统计级别 2 二级业态 3 三级业态', \n")
                .append("    `type1id` smallint(10) NOT NULL DEFAULT 0  COMMENT '一级业态id', \n")
                .append("    `type1Name` VARCHAR(50) NOT NULL DEFAULT '00'  COMMENT '一级业态名称', \n")
                .append("    `type2id` smallint(10) NOT NULL DEFAULT 0  COMMENT '二级业态id', \n")
                .append("   `type2Name` VARCHAR(50) NOT NULL DEFAULT '00'  COMMENT '二级业态名称', \n")
                .append("     `type3id` smallint(10) NOT NULL DEFAULT 0  COMMENT '三级业态id', \n")
                .append("     `type3Name` VARCHAR(50) NOT NULL DEFAULT '00'  COMMENT '三级业态名称', \n")
                .append("     `num` int(11) NOT NULL  COMMENT '统计值', \n")
                .append("     INDEX `stat_temp_index` (`citycode`,`areacode`, `stattype`,`typelevel`) \n")
                .append(" ) \n")
                .append(" COMMENT='商家统计临时二级缓存表' \n")
                .append(" COLLATE='utf8_general_ci' \n")
                .append("  ENGINE=MyISAM  \n");
        
        executesqlForUpdate(conn,sb.toString());
        sb.delete(0, sb.length());
        
        sb.append(" CREATE TABLE `cg_city_type` ( \n")
                .append("     `citycode` VARCHAR(10) NOT NULL DEFAULT '00' COMMENT  '城市id', \n")
                .append("     `cityName` VARCHAR(100) NOT NULL DEFAULT '00'  COMMENT '城市名称', \n")
                .append("     `areacode` VARCHAR(10) NOT NULL DEFAULT '00' COMMENT  '地区id', \n")
                .append("      `areaName` VARCHAR(100) NOT NULL DEFAULT '00' COMMENT  '地区名称', \n")
                .append("     `typelevel` smallint(2) NOT NULL  COMMENT '业态等级 2 二级业态 3 三级业态', \n")
                .append("     `type1id` smallint(10) NOT NULL DEFAULT 0  COMMENT '一级业态id', \n")
                .append("     `type1Name` VARCHAR(50) NOT NULL DEFAULT '00'  COMMENT '一级业态名称', \n")
                .append("      `type2id` smallint(10) NOT NULL DEFAULT 0  COMMENT '二级业态id', \n")
                .append("      `type2Name` VARCHAR(50) NOT NULL DEFAULT '00'  COMMENT '二级业态名称', \n")
                .append("       `type3id` smallint(10) NOT NULL DEFAULT 0  COMMENT '三级业态id', \n")
                .append("      `type3Name` VARCHAR(50) NOT NULL DEFAULT '00' COMMENT  '三级业态名称', \n")
                .append("      INDEX `city_type` (`citycode`,`areacode`, `typelevel`) \n")
                .append("  ) \n")
                .append("  COMMENT='城市和业态模板表' \n")
                .append("  COLLATE='utf8_general_ci' \n")
                .append("  ENGINE=MyISAM   \n");
        
        executesqlForUpdate(conn,sb.toString());
        sb.delete(0, sb.length());
        
        sb.append(" create table t_type2Relation as  \n")
                .append("  select r.id type1id,r.name type1name,s.id type2id, s.name type2name  \n")
                .append("   from cg_shop_type s , \n")
                .append("  cg_shop_type_relation r \n")
                .append("   where  \n")
                .append("  ( \n")
                .append("       locate(concat(',',s.id,','),r.child_ids) > 0 \n")
                .append("        or  \n")
                .append("        locate(concat('A',s.id,','),concat('A',r.child_ids)) > 0 \n")
                .append("        or \n")
                .append("        locate(concat(',',s.id,'A'),concat(r.child_ids,'A')) > 0   \n")
                .append("    ) \n")
                .append("  and s.mark = 1 and r.mark = 1  \n")
                .append("   order by r.id,s.id    \n");
        
        executesqlForUpdate(conn,sb.toString());
        sb.delete(0, sb.length());
        
        sb.append(" create table t_type3Relation as   \n")
                .append("   select r.id type1id,r.name type1name,s.id type2id, s.name type2name, type3.id type3id,type3.name type3name   \n")
                .append("   from cg_shop_type s ,  \n")
                .append("   cg_shop_type_relation r,  \n")
                .append("   cg_brand_style type3  \n")
                .append("   where   \n")
                .append("   (  \n")
                .append("       locate(concat(',',s.id,','),r.child_ids) > 0  \n")
                .append("         or   \n")
                .append("         locate(concat('A',s.id,','),concat('A',r.child_ids)) > 0  \n")
                .append("         or  \n")
                .append("          locate(concat(',',s.id,'A'),concat(r.child_ids,'A')) > 0      \n")
                .append("    )  \n")
                .append("   and s.mark = 1 and r.mark = 1 and type3.mark = 1  \n")
                .append("    and type3.type = s.id  \n")
                .append("   order by r.id,s.id,type3.id     \n");
        
        executesqlForUpdate(conn,sb.toString());
        sb.delete(0, sb.length());
        
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
        }
         
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
