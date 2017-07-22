package com.tools.brandmapshop;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 商家和品牌关联完毕 处理关联过程出现的问题
 * 
 * @author  sihua
 * @version  [版本号, 2015-9-14]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */

public class AfterShopMapBrand
{
    
    public static List<String> list = new ArrayList<String>();
    
    static
    {
        list.add("cg_shop_beijing");
        list.add("cg_shop_anhui");
        list.add("cg_shop_fujian");
        list.add("cg_shop_gansu");
        list.add("cg_shop_guangdong");
        list.add("cg_shop_guangxi");
        list.add("cg_shop_guizhou");
        list.add("cg_shop_hainan");
        list.add("cg_shop_hebei");
        list.add("cg_shop_henan");
        list.add("cg_shop_heilongjiang");
        list.add("cg_shop_hubei");
        list.add("cg_shop_hunan");
        list.add("cg_shop_jilin");
        list.add("cg_shop_jiangsu");
        list.add("cg_shop_jiangxi");
        list.add("cg_shop_liaoning");
        list.add("cg_shop_neimenggu");
        list.add("cg_shop_ningxia");
        list.add("cg_shop_qinghai");
        list.add("cg_shop_shandong");
        list.add("cg_shop_shanxi1");
        list.add("cg_shop_shanxi3");
        list.add("cg_shop_shanghai");
        list.add("cg_shop_sicuan");
        list.add("cg_shop_tianjin");
        list.add("cg_shop_xizang");
        list.add("cg_shop_xinjiang");
        list.add("cg_shop_yunnan");
        list.add("cg_shop_zhejiang");
        list.add("cg_shop_chongqing");
        list.add("cg_shop_xianggang");
        list.add("cg_shop_aomen");
        list.add("cg_shop_taiwan");
        
    }
    
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
        
        AfterShopMapBrand after = new AfterShopMapBrand();
        after.genSql();
        
    }
    
    private  void genSql()
    {
        String sql = "";
        
        for(String tableName:list)
        {
            sql = sql + deal(tableName);
        }
        
        System.out.println(sql);
    }
    
    public static String deal(String subTableName)
    {
        
        StringBuilder sb = new StringBuilder();
        
        //可爱猫和接吻猫的处理
        //select * from cg_brand b where b.name like '%可爱猫%' or b.name like '%接吻猫%';
        
        //更正可爱猫的关联
        //select * from  cg_shop_jiangsu 
        //where branch_name like '%scat%' and  branch_name not like '%kiss%cat%';
        
        //update cg_shop_jiangsu set brand_id = 27134
        //where branch_name like '%scat%' and  branch_name not like '%kiss%cat%';
        sb.append("\n\n update {shopTable} set brand_id = 27134 where branch_name like '%scat%' and  branch_name not like '%kiss%cat%'; \n ");
        
        //更正接吻猫的关联
        //select * from cg_shop_jiangsu  
        //where   branch_name like '%kiss%cat%';
        
        //update cg_shop_jiangsu  set brand_id = 27056
        //where   branch_name like '%kiss%cat%';
        sb.append(" update {shopTable}  set brand_id = 27056 where   branch_name like '%kiss%cat%'; \n ");
        
        //"空间"品牌 
        sb.append(" update {shopTable} set brand_id = 0 where brand_id = 34155 and char_length(trim(branch_name)) >=3 ; \n ");
        
        //将属于ZARA的商家调整为属于ARA
        //ZARA 飒拉 28311
        //ARA 鹦鹉 40083
        //select * from cg_shop_jiangsu s where s.brand_id = 40083 and s.branch_name like '%ZARA%';
        sb.append("update {shopTable} set brand_id = 28311 where brand_id = 40083 and branch_name like '%ZARA%'; \n ");
        
        String sql = sb.toString();
        sql = sql.replace("{shopTable}", subTableName);
        
        return sql;
        
    }
    
    
    //truncate table cg_shop_fujian;
    //truncate table cg_shop_type2_fujian;
    //truncate table cg_shop_type3_fujian;
    
    
}
