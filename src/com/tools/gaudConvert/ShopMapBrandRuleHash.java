package com.tools.gaudConvert;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ShopMapBrandRuleHash
{
    
    //翻译公式
    private  Map<String,List<ShopMapBrandRuleBean>> shopMapBrandruleMap = new  HashMap<String,List<ShopMapBrandRuleBean>>();
    private  List <ShopMapBrandRuleBean> multiTypeRuleList = new ArrayList<ShopMapBrandRuleBean>();
    
    private String PATH ="E:\\ysh\\高德\\翻译\\翻译规则数据\\";
    private String RULE_PATH = PATH + "brandmapshoprules.csv";
    
    /** 
     * 
     * @param args [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static void main(String[] args)
    {
       //String aa = "\"dfdfdfdf\"过大";
       //aa = aa.replace("\"", "");
       // System.out.println(aa);
        ShopMapBrandRuleHash rule = new ShopMapBrandRuleHash();
        rule.init();
    }
    
    /**
     * 
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private  void clear()
    {
        shopMapBrandruleMap.clear();
    }
    
    public void init()
    {
        clear();
        loadRuleFromFile();
    }
    
    public List<ShopMapBrandRuleBean> getBrandListByKey(String key)
    {
        return shopMapBrandruleMap.get(key);
    }
    
    
    public List<String> getKeys()
    {
        Set<String> set =  shopMapBrandruleMap.keySet();
        List<String> list = new ArrayList<String>();
        list.addAll(set);
        return list;
    }
    
    /**
     * 加入公式
     * @param brandBean [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private void putRule(ShopMapBrandRuleBean shopMapBrandRuleBean)
    {
       
        String key = "";
            
        if(notNullAndNot0(shopMapBrandRuleBean.getCgShop3Name()) )
        {
            key = shopMapBrandRuleBean.getCgShop1Name() + "_"
                    + shopMapBrandRuleBean.getCgShop2Name() + "_"
                    + shopMapBrandRuleBean.getCgShop3Name();
        }
        else if (notNullAndNot0(shopMapBrandRuleBean.getCgShop2Name()) )
        {
            key = shopMapBrandRuleBean.getCgShop1Name() + "_"
            + shopMapBrandRuleBean.getCgShop2Name();
        }
        else if (notNullAndNot0(shopMapBrandRuleBean.getCgShop1Name()) )
        {
            key = shopMapBrandRuleBean.getCgShop1Name();
        }
        
        if(shopMapBrandruleMap.containsKey(key))
        {
            (shopMapBrandruleMap.get(key)).add(shopMapBrandRuleBean);
        }
        else
        {
            List<ShopMapBrandRuleBean> list = new ArrayList<ShopMapBrandRuleBean>();
            list.add(shopMapBrandRuleBean);
            shopMapBrandruleMap.put(key, list);
        }
        
        //如果一个公式是多个二级或多个三级业态，记录在单独的list中， 为后续程序备用
        if(notNullAndNot0(shopMapBrandRuleBean.getCgBrand2Name()) && shopMapBrandRuleBean.getCgBrand2Name().contains("|"))
        {
            multiTypeRuleList.add(shopMapBrandRuleBean);
        }
        
        if(notNullAndNot0(shopMapBrandRuleBean.getCgBrand3Name()) && shopMapBrandRuleBean.getCgBrand3Name().contains("|"))
        {
            multiTypeRuleList.add(shopMapBrandRuleBean);
        }
        //
        
    }
    
    public List<ShopMapBrandRuleBean> getMultiTypeRuleList()
    {
        return multiTypeRuleList;
    }
    
    private boolean notNullAndNot0(String key)
    {
        if(null == key || key.trim().equals("")||key.trim().equals("0"))
        {
            return false;
        }
        
        return true;
    }
    
    /**
     * <一句话功能简述>
     * <功能详细描述>
     * @param str
     * @return [参数说明]
     * 
     * @return RuleBean [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private ShopMapBrandRuleBean createRule(String str)
    {
        String [] strs = str.split(",");
        ShopMapBrandRuleBean rule = new ShopMapBrandRuleBean(strs[0], strs[7], strs[1], strs[2], strs[3],
                strs[4], strs[5], strs[6]);
        
        return rule;
    }
    
    /**
     * <一句话功能简述>
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private  void loadRuleFromFile()
    {

        System.out.println("加载品牌和商家关联公式:"+RULE_PATH);
        
        File file = new File(RULE_PATH);
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
                putRule(createRule(lineTxt));
                
            }
        }
        catch (Exception e)
        {
             
        }
        finally
        {
            closeStream(read);
        }
        
        System.out.println("加载品牌和商家关联公式完毕");
    
    }
    
    private static void closeStream(Object obj)
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
