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

public class RuleHash
{
    
    //翻译公式
    private  Map<String,List<RuleBean>> ruleNameMap = new  HashMap<String,List<RuleBean>>();
    private  List <RuleBean> multiTypeRuleList = new ArrayList<RuleBean>();
    //公式 名称为key
    //private Map<String,List<RuleBean>> ruleNameHash = new  HashMap<String,List<RuleBean>>();
    //多key公式
    //private MultiRuleHash rulesidHash = new MultiRuleHash();
    private MultiRuleHash ruleName2ruleidsHash = new MultiRuleHash();
    
    private String PATH ="E:\\ysh\\高德\\翻译\\翻译规则数据\\";
    
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
        RuleHash rule = new RuleHash();
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
    public  void clear()
    {
        ruleNameMap.clear();
    }
    
    public void init()
    {
        clear();
        loadRuleFromFile();
    }
    
    /**
     * 加入公式
     * @param brandBean [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private void putRule(RuleBean ruleBean)
    {
        //String key = ruleBean.getType_1();
        String keyGDOneTypeName = ruleBean.getGdtype1name();
        
        ruleName2ruleidsHash.put(ruleName2ruleidsHash.k(keyGDOneTypeName,ruleBean.getId()), new RuleBean());
        
        if(ruleNameMap.containsKey(keyGDOneTypeName))
        {
            (ruleNameMap.get(keyGDOneTypeName)).add(ruleBean);
            
            //(ruleNameHash.get(keyName)).add(ruleBean);
        }
        else
        {
            List<RuleBean> list = new ArrayList<RuleBean>();
            list.add(ruleBean);
            ruleNameMap.put(keyGDOneTypeName, list);
            //ruleHash.put(keyName, list);
            //ruleNameHash.put(keyName, list);
        }
        
        //如果一个公式是多个二级或多个三级业态，记录在单独的list中， 为后续程序备用
        if(notNullAndNot0(ruleBean.getCgtype2name()) 
                && ruleBean.getCgtype2name().contains("|") 
                && "1".equals(ruleBean.getMybrand()))
        {
            multiTypeRuleList.add(ruleBean);
        }
        
        if(notNullAndNot0(ruleBean.getCgtype3name()) 
                && ruleBean.getCgtype3name().contains("|") 
                && "1".equals(ruleBean.getMybrand()))
        {
            multiTypeRuleList.add(ruleBean);
        }
        
    }
    
    public List<RuleBean> getMultiTypeRuleList()
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
     * @param ruleType
     * @return [参数说明]
     * 
     * @return List<RuleBean> [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public String[] getRuleIdsByTypeName(String ruleOneTypeName)
    {
        return ruleName2ruleidsHash.sonKeys(ruleOneTypeName);
    }
    
    
    /**
     * <一句话功能简述>
     * <功能详细描述>
     * @param ruleType
     * @return [参数说明]
     * 
     * @return List<RuleBean> [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public List<RuleBean> getRuleListByTypeName(String ruleOneTypeName)
    {
        return ruleNameMap.get(ruleOneTypeName);
    }
    
    
    /**
     * 
     * <功能详细描述>
     * @param ruleId
     * @param ruleType
     * @return [参数说明]
     * 
     * @return RuleBean [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public RuleBean getRulesById(String ruleId,String ruleOneTypeName)
    {
        List<RuleBean> list = ruleNameMap.get(ruleOneTypeName);
        
        for(RuleBean rule : list)
        {
            if(rule.getId().equals(ruleId))
            {
                return rule;
            }
        }
        
        return new RuleBean();
        
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
    private RuleBean createRule(String str)
    {
        String [] strs = str.split(",");
        RuleBean rule = new RuleBean(strs[0], strs[1], strs[2], strs[3],
                strs[4], strs[5], strs[6], strs[7], strs[8], strs[9], strs[10],
                strs[11], strs[12], strs[13], strs[14]);
        
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

        System.out.println("加载翻译公式:"+PATH + "rule.csv");
        
        File file = new File(PATH + "trans.rule.new.csv");
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
        
        System.out.println("加载翻译结束");
    
    }
    
    public static void closeStream(Object obj)
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
