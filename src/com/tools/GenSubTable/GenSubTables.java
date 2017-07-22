package com.tools.GenSubTable;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

public class GenSubTables
{
    private static String DEST_PATH ="E:\\ysh\\database\\sql集\\商家库分表设计\\";
    private static String GEN_SUB_TABLES_SCRIPTS ="SHOP_SUB_TABLES.SQL";
    
    public  static void main(String[] argvs)
    {
        GenSubTables gen = new GenSubTables();
        gen.genTables();
    }
    
    private void genTables()
    {
        List<String> list = getProvinceName();
         BufferedWriter writeHandle = null;
        
         try
         {
             writeHandle = new BufferedWriter(new FileWriter(DEST_PATH + GEN_SUB_TABLES_SCRIPTS,false));
             
             for(String name :list)
             {
                 String[] names = name.split(",");
                 String tableScript = getCreateSubShopTablesSql(names[0],names[1]);
                 writeHandle.write(tableScript);
                 //System.out.println(tableScript);
                 tableScript = getCreateSubShopType2TablesSql(names[0],names[1]);
                 //System.out.println(tableScript);
                 writeHandle.write(tableScript);
                 tableScript = getCreateSubShopType3TablesSql(names[0],names[1]);
                 //System.out.println(tableScript);
                 writeHandle.write(tableScript);
                 tableScript = getCreateSubShopStaticTablesSql(names[0],names[1]);
                 //System.out.println(tableScript);
                 writeHandle.write(tableScript);
                 
             }
             
         }catch (IOException e)
         {
             e.printStackTrace();
         }
         finally
         {
             closeStream(writeHandle);
         }
         
        
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
    private String getCreateSubShopTablesSql(String provinceName,String provinceEngName)
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append("CREATE TABLE `cg_shop_{provinceEngName}`   \n")
                .append("(  \n")
                .append("`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '编号',  \n")
                .append("`brand_id` INT(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '品牌',  \n")
                .append("`agent_id` INT(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '所属代理商编号',  \n")
                .append("`base_id` INT(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '详细信息',  \n")
                .append("`mid` INT(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '规则id',  \n")
                .append("`branch_name` VARCHAR(50) NOT NULL COMMENT '分店名', \n")
                .append("`pinyin` CHAR(1) NOT NULL COMMENT '首字母拼音', \n")
                .append("`address` VARCHAR(200) NOT NULL COMMENT '分店地址',  \n")
                .append("`phone` VARCHAR(50) NOT NULL COMMENT '分店电话',  \n")
                .append("`fax` VARCHAR(50) NOT NULL COMMENT '分店传真',  \n")
                .append("`contact` VARCHAR(100) NOT NULL COMMENT '联系人',  \n")
                .append("`point` VARCHAR(50) NOT NULL COMMENT '点坐标',  \n")
                .append("`baidu_lon`  FLOAT(11,6) UNSIGNED NOT NULL DEFAULT 0 COMMENT '经度(x * 1e6)',  \n")
                .append("`baidu_lat`  FLOAT(11,6) UNSIGNED NOT NULL DEFAULT 0 COMMENT '纬度(y * 1e6)',  \n")
                .append("`state` TINYINT(2) UNSIGNED NOT NULL DEFAULT 0 COMMENT '0表示没有导入品牌库，1表示导入了',  \n")
                .append("`citycode` SMALLINT(20) UNSIGNED NOT NULL COMMENT '城市编码', \n")
                .append("`areacode` SMALLINT(20) UNSIGNED NOT NULL COMMENT '地区编码', \n")
                .append("`add_user` SMALLINT(6) UNSIGNED NOT NULL DEFAULT 0 COMMENT '添加用户', \n")
                .append("`add_time` INT(10) UNSIGNED NOT NULL DEFAULT 0  COMMENT '添加时间', \n")
                .append("`update_time` INT(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '最后更新时间', \n")
                .append("`mark` TINYINT(1) UNSIGNED NOT NULL DEFAULT '1' COMMENT '有效标记', \n")
                .append("PRIMARY KEY (`id`,`citycode`),  \n")
                .append("INDEX index_areacode(areacode) , \n")
                .append("INDEX index_brandid(brand_id)  \n")
                .append(")\n")
                .append("COMMENT='商家表{provinceName}分表'  \n")
                .append("COLLATE='utf8_general_ci' \n")
                .append("ENGINE=MyISAM \n")
                .append("AUTO_INCREMENT=1 \n")
                .append("PARTITION BY HASH(citycode) \n")
                .append("PARTITIONS 20;\n\n\n ");
        
        String sql = sb.toString();
        sql = sql.replace("{provinceEngName}", provinceEngName);
        sql = sql.replace("{provinceName}", provinceName);
        
        if(provinceEngName.contains("beijing") 
                || provinceEngName.contains("chongqing")
                || provinceEngName.contains("tianjin")
                || provinceEngName.contains("shanghai")
                || provinceEngName.contains("xianggang")
                || provinceEngName.contains("aomen"))
        {
            sql = sql.replace("(`id`,`citycode`)", "(`id`,`citycode`,`areacode`)");
            sql = sql.replace("HASH(citycode)", "HASH(areacode)");
        }
        
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
    private String getCreateSubShopType2TablesSql(String provinceName,String provinceEngName)
    {
        StringBuilder sb = new StringBuilder();
        
        
        sb.append(" CREATE TABLE `cg_shop_type2_{provinceEngName}`  \n")
                .append(" ( \n")
                .append("  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT, \n")
                .append("  `citycode` SMALLINT(20) NOT NULL COMMENT '城市编码', \n")
                .append("  `areacode` SMALLINT(20) NOT NULL COMMENT '地区编码', \n")
                .append("  `shop_id` INT(11) NOT NULL COMMENT '商家id', \n")
                .append("  `type_id` SMALLINT(6) UNSIGNED NOT NULL COMMENT '二级业态id', \n")
                .append("  `mid` INT(11) NOT NULL COMMENT '规则id', \n")
                .append("  `level` TINYINT(4) NOT NULL DEFAULT '2' COMMENT '等级 2 二级业态关联', \n")
                .append("  `add_time` INT(10) UNSIGNED NOT NULL, \n")
                .append("  `mark` TINYINT(1) NOT NULL DEFAULT '1', \n")
                .append("  PRIMARY KEY (`id`,`citycode`), \n")
                .append("  INDEX index_areacode (`areacode`) \n")
                .append(" ) \n")
                .append("COMMENT='商家和2级业态关联表{provinceName}分表' \n")
                .append("COLLATE='utf8_general_ci' \n")
                .append("ENGINE=MyISAM \n")
                .append("AUTO_INCREMENT=1 \n")
                .append("PARTITION BY HASH(citycode) \n")
                .append("PARTITIONS 20; \n\n\n");
        
        String sql = sb.toString();
        sql = sql.replace("{provinceEngName}", provinceEngName);
        sql = sql.replace("{provinceName}", provinceName);
        
        if(provinceEngName.contains("beijing") 
                || provinceEngName.contains("chongqing")
                || provinceEngName.contains("tianjin")
                || provinceEngName.contains("shanghai")
                || provinceEngName.contains("xianggang")
                || provinceEngName.contains("aomen"))
        {
            sql = sql.replace("(`id`,`citycode`)", "(`id`,`citycode`,`areacode`)");
            sql = sql.replace("HASH(citycode)", "HASH(areacode)");
        }
        
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
    private String getCreateSubShopType3TablesSql(String provinceName,String provinceEngName)
    {
        StringBuilder sb = new StringBuilder();
        
        
        sb.append(" CREATE TABLE `cg_shop_type3_{provinceEngName}`  \n")
                .append(" ( \n")
                .append("   `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT, \n")
                .append("   `citycode` SMALLINT(20) NOT NULL COMMENT '城市编码', \n")
                .append("   `areacode` SMALLINT(20) NOT NULL COMMENT '地区编码', \n")
                .append("   `shop_id` INT(11) NOT NULL COMMENT '商家id', \n")
                .append("   `type_id` SMALLINT(6) UNSIGNED NOT NULL COMMENT '三级业态id', \n")
                .append("   `mid` INT(11) NOT NULL COMMENT  '规则id', \n")
                .append("   `level` TINYINT(4) NOT NULL DEFAULT '3' COMMENT '等级 3 三级关联', \n")
                .append("   `add_time` INT(10) UNSIGNED NOT NULL, \n")
                .append("   `mark` TINYINT(1) NOT NULL DEFAULT '1', \n")
                .append("   PRIMARY KEY (`id`,`citycode`), \n")
                .append("   INDEX index_areacode (`areacode`) \n")
                .append(" ) \n")
                .append("COMMENT='商家和3级业态关联表{provinceName}分表' \n")
                .append("COLLATE='utf8_general_ci' \n")
                .append("ENGINE=MyISAM \n")
                .append("AUTO_INCREMENT=1 \n")
                .append("PARTITION BY HASH(citycode) \n")
                .append("PARTITIONS 20; \n\n\n");
        
        String sql = sb.toString();
        sql = sql.replace("{provinceEngName}", provinceEngName);
        sql = sql.replace("{provinceName}", provinceName);
        
        if(provinceEngName.contains("beijing") 
                || provinceEngName.contains("chongqing")
                || provinceEngName.contains("tianjin")
                || provinceEngName.contains("shanghai")
                || provinceEngName.contains("xianggang")
                || provinceEngName.contains("aomen"))
        {
            sql = sql.replace("(`id`,`citycode`)", "(`id`,`citycode`,`areacode`)");
            sql = sql.replace("HASH(citycode)", "HASH(areacode)");
        }
        
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
    private String getCreateSubShopStaticTablesSql(String provinceName,String provinceEngName)
    {
        StringBuilder sb = new StringBuilder();
        
        //typelevel 二级业态 2 三级业态 3
        sb.append(" CREATE TABLE `cg_shop_stat_{provinceEngName}` \n")
                .append(" ( \n")
                .append("  `citycode` int(10) NOT NULL DEFAULT 0 COMMENT '城市id', \n")
                .append("  `cityName` VARCHAR(100) NOT NULL DEFAULT '00'  COMMENT  '城市名称', \n")
                .append("  `areacode` int(10) NOT NULL DEFAULT 0  COMMENT  '地区id', \n")
                .append("  `areaName` VARCHAR(100) NOT NULL DEFAULT '00' COMMENT  '地区名称', \n")
                .append("  `typelevel` smallint(2) NOT NULL  COMMENT '业态类别： 2 二级业态 3 三级业态', \n")
                .append("  `type1id` smallint(10) NOT NULL DEFAULT 0  COMMENT '一级业态id', \n")
                .append("  `type1Name` VARCHAR(50) NOT NULL DEFAULT '00'  COMMENT '一级业态名称', \n")
                .append("  `type2id` smallint(10) NOT NULL DEFAULT 0  COMMENT '二级业态id', \n")
                .append("  `type2Name` VARCHAR(50) NOT NULL DEFAULT '00'  COMMENT '二级业态名称', \n")
                .append("  `type3id` smallint(10) NOT NULL DEFAULT 0  COMMENT '三级业态id', \n")
                .append("  `type3Name` VARCHAR(50) NOT NULL DEFAULT '00'  COMMENT '三级业态名称', \n")
                .append("  `shopnum` int(11) NOT NULL DEFAULT 0  COMMENT '商家数', \n")
                .append("  `brandshopnum` int(11) NOT NULL DEFAULT 0  COMMENT '品牌商家数', \n")
                .append("  `brandnum` int(11) NOT NULL DEFAULT 0  COMMENT '品牌数', \n")
                .append("  INDEX `stat_index` (`citycode`,`areacode`, `typelevel`) \n")
                .append(" ) \n")
                .append("COMMENT='商家统计表{provinceName}分表' \n")
                .append("COLLATE='utf8_general_ci' \n")
                .append("ENGINE=MyISAM \n")
                .append("PARTITION BY HASH(citycode) \n")
                .append("PARTITIONS 20; \n\n\n");
        
        String sql = sb.toString();
        sql = sql.replace("{provinceEngName}", provinceEngName);
        sql = sql.replace("{provinceName}", provinceName);
        
        if(provinceEngName.contains("beijing") 
                || provinceEngName.contains("chongqing")
                || provinceEngName.contains("tianjin")
                || provinceEngName.contains("shanghai")
                || provinceEngName.contains("xianggang")
                || provinceEngName.contains("aomen"))
        {
            sql = sql.replace("HASH(citycode)", "HASH(areacode)");
        }
        
        return sql;
        
    }
    
    /**
     * <一句话功能简述>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return List<String> [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private List<String> getProvinceName()
    {
        List<String> list = new ArrayList<String>();
        list.add("北京,beijing");
        list.add("上海,shanghai");
        list.add("天津,tianjin");
        list.add("重庆,chongqing");
        list.add("香港,xianggang");
        list.add("澳门,aomen");
        list.add("台湾,taiwan");
        list.add("安徽,anhui");
        list.add("福建,fujian");
        list.add("甘肃,gansu");
        list.add("广东,guangdong");
        list.add("广西,guangxi");
        list.add("贵州,guizhou");
        list.add("海南,hainan");
        list.add("河北,hebei");
        list.add("河南,henan");
        list.add("黑龙江,heilongjiang");
        list.add("湖北,hubei");
        list.add("湖南,hunan");
        list.add("吉林,jilin");
        list.add("江苏,jiangsu");
        list.add("江西,jiangxi");
        list.add("辽宁,liaoning");
        list.add("内蒙古,neimenggu");
        list.add("宁夏,ningxia");
        list.add("青海,qinghai");
        list.add("山东,shandong");
        list.add("山西,shanxi1");
        list.add("陕西,shanxi3");
        list.add("四川,sicuan");
        list.add("西藏,xizang");
        list.add("新疆,xinjiang");
        list.add("云南,yunnan");
        list.add("浙江,zhejiang");

        return list;
        
    }
    
    public  void closeStream(Object obj)
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
