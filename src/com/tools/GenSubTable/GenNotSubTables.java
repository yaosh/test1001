package com.tools.GenSubTable;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;

public class GenNotSubTables
{
    private static String DEST_PATH ="E:\\ysh\\database\\sql集\\商家库分表设计\\";
    private static String GEN_NOT_SUB_TABLES_SCRIPTS ="SHOP_NOT_SUB_TABLES.SQL";
    
    public  static void main(String[] argvs)
    {
        GenNotSubTables gen = new GenNotSubTables();
        gen.genTables();
    }
    
    private void genTables()
    {
         BufferedWriter writeHandle = null;
        
         try
         {
             writeHandle = new BufferedWriter(new FileWriter(DEST_PATH + GEN_NOT_SUB_TABLES_SCRIPTS,false));
             
             String tableScript = getCreateShopTablesSql();
             writeHandle.write(tableScript);
             //System.out.println(tableScript);
             tableScript = getCreateShopType2TablesSql();
             //System.out.println(tableScript);
             writeHandle.write(tableScript);
             tableScript = getCreateShopType3TablesSql();
             //System.out.println(tableScript);
             writeHandle.write(tableScript);
             
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
    private String getCreateShopTablesSql()
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append(" CREATE TABLE `cg_mid_poi_shop` (    \n")
                .append(" `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '编号', \n")
                .append(" `brand_id` INT(11) UNSIGNED NOT NULL DEFAULT '0' COMMENT '品牌', \n")
                .append(" `agent_id` INT(10) UNSIGNED NOT NULL COMMENT '所属代理商编号', \n")
                .append(" `poi_id` INT(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT 'ctrlv 编号', \n")
                .append(" `base_id` INT(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '详细信息', \n")
                .append(" `mid` INT(11) UNSIGNED NOT NULL COMMENT '规则id', \n")
                .append(" `branch_name` VARCHAR(50) NOT NULL COMMENT '分店名', \n")
                .append(" `address` VARCHAR(100) NOT NULL COMMENT '分店地址', \n")
                .append(" `phone` VARCHAR(50) NOT NULL COMMENT '分店电话', \n")
                .append(" `fax` VARCHAR(50) NOT NULL COMMENT '分店传真', \n")
                .append(" `contact` VARCHAR(50) NOT NULL COMMENT '联系人', \n")
                .append(" `point` VARCHAR(50) NOT NULL COMMENT '点坐标', \n")
                .append(" `state` TINYINT(2) UNSIGNED NOT NULL DEFAULT '0' COMMENT '0表示没有导入品牌库，1表示导入了', \n")
                .append(" `city` SMALLINT(20) UNSIGNED NOT NULL COMMENT '所在城市', \n")
                .append(" `citycode` SMALLINT(20) UNSIGNED NOT NULL COMMENT '城市编码', \n")
                .append(" `areacode` VARCHAR(10) NOT NULL COMMENT '地区', \n")
                .append(" `project` VARCHAR(30) NOT NULL, \n")
                .append(" `floor` VARCHAR(10) NOT NULL COMMENT '所在楼层', \n")
                .append(" `floor_img` VARCHAR(50) NOT NULL COMMENT '楼层平面图', \n")
                .append(" `add_user` SMALLINT(6) UNSIGNED NOT NULL COMMENT '添加用户', \n")
                .append(" `add_time` INT(10) UNSIGNED NOT NULL COMMENT '添加时间', \n")
                .append(" `update_time` INT(10) UNSIGNED NOT NULL COMMENT '最后更新时间', \n")
                .append(" `mark` TINYINT(1) UNSIGNED NOT NULL DEFAULT '1' COMMENT '有效标记', \n")
                .append(" `types` VARCHAR(100) NULL DEFAULT NULL, \n")
                .append(" PRIMARY KEY (`id`), \n")
                .append(" INDEX `Indexshopname` (`branch_name`), \n")
                .append(" INDEX `Indexbrandid` (`brand_id`); \n\n\n");
        
        String sql = sb.toString();
        
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
    private String getCreateShopType2TablesSql()
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append(" CREATE TABLE `cg_mid_poi_type` ( \n")
                .append("   `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '编号', \n")
                .append("   `brand_id` INT(11) UNSIGNED NOT NULL COMMENT '品牌编号', \n")
                .append("   `type` SMALLINT(6) UNSIGNED NOT NULL COMMENT '业态', \n")
                .append("   `level` TINYINT(4) UNSIGNED NOT NULL DEFAULT '3' COMMENT '层级', \n")
                .append("    `add_time` INT(10) UNSIGNED NOT NULL COMMENT '添加时间', \n")
                .append("    `mark` TINYINT(1) UNSIGNED NOT NULL DEFAULT '1' COMMENT '有效标记', \n")
                .append("    `mid` INT(11) UNSIGNED NOT NULL COMMENT '规则id', \n")
                .append("     PRIMARY KEY (`id`), \n")
                .append("     INDEX `brand_id` (`brand_id`) \n")
                .append(" ) \n ")
                .append(" COMMENT='品牌对应业态' \n")
                .append(" COLLATE='utf8_general_ci' \n")
                .append(" ENGINE=MyISAM \n")
                .append(" AUTO_INCREMENT=1 ; \n\n\n");
        
        String sql = sb.toString();
        
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
    private String getCreateShopType3TablesSql()
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append(" CREATE TABLE `cg_mid_poi_style_relation`  \n")
                .append("( \n")
                .append("    `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '编号', \n ")
                .append("    `style_id` INT(11) UNSIGNED NOT NULL COMMENT '风格编号', \n ")
                .append("   `brand_id` INT(11) UNSIGNED NOT NULL COMMENT '品牌编号', \n ")
                .append("   `add_time` INT(11) UNSIGNED NOT NULL COMMENT '添加时间', \n ")
                .append("    `mark` TINYINT(1) UNSIGNED NOT NULL DEFAULT '1' COMMENT '有效标记', \n ")
                .append("    `mid` INT(11) UNSIGNED NOT NULL COMMENT '规则id', \n ")
                .append("    PRIMARY KEY (`id`), \n ")
                .append("    INDEX `brand_id` (`brand_id`) \n ")
                .append(") \n ")
                .append(" COMMENT='商家三级业态' \n")
                .append(" COLLATE='utf8_general_ci' \n")
                .append(" ENGINE=MyISAM \n ")
                .append(" AUTO_INCREMENT=1; \n\n\n ");
        
        String sql = sb.toString();
        
        return sql;
        
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
