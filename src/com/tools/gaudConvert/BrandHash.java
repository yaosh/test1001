package com.tools.gaudConvert;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

public class BrandHash
{
    
    private String PATH ="E:\\ysh\\高德\\翻译\\翻译规则数据\\";
    
    //===================品牌信息=================
    //品牌： 一级业态 ->二级业态 ->品牌列表  按照名称长度降序排列
    private  Map<String,List<BrandBean>> brand12 = new  HashMap<String,List<BrandBean>>();
    
    //品牌： 一级业态 ->二级业态 ->三级业态->品牌列表  按照名称长度降序排列
    private Map<String,List<BrandBean>> brand123 = new  HashMap<String,List<BrandBean>>();
    
    //品牌： 一级业态 ->二级业态 ->品牌列表 按照英文名称长度降序排列
    private  Map<String,List<BrandBean>> brand12EngName = new  HashMap<String,List<BrandBean>>();
    
    //品牌： 一级业态 ->二级业态 ->三级业态->品牌列表 按照英文名称长度降序排列
    private Map<String,List<BrandBean>> brand123EngName = new  HashMap<String,List<BrandBean>>();
    
    //品牌： 一级业态 ->二级业态 ->品牌列表 按照别名长度降序排列
    private  Map<String,List<BrandBean>> brand12OtherName = new  HashMap<String,List<BrandBean>>();
    
    //品牌： 一级业态 ->二级业态 ->三级业态->品牌列表 按照别名长度降序排列
    private Map<String,List<BrandBean>> brand123OtherName = new  HashMap<String,List<BrandBean>>();
    
    //此多业态品牌Map用于关联程序
    //品牌： 一级业态 ->二级业态,...,二级业态 _1->中文品牌列表 按照别名长度降序排列
    //      一级业态 ->二级业态 ->三级业态,...,三级业态_1->中文品牌列表
    //品牌： 一级业态 ->二级业态,...,二级业态 _2->英文品牌列表 按照别名长度降序排列
    //      一级业态 ->二级业态 ->三级业态,...,三级业态_2->英文品牌列表
    //品牌： 一级业态 ->二级业态,...,二级业态 _3->别名品牌列表 按照别名长度降序排列
    //      一级业态 ->二级业态 ->三级业态,...,三级业态_3->别名品牌列表
    private Map<String,List<BrandBean>> brandMultiForShopMapBrand = new  HashMap<String,List<BrandBean>>();
    
    //此多业态品牌Map用于翻译程序
   //品牌： 一级业态 ->二级业态,...,二级业态 _1->中文品牌列表 按照别名长度降序排列
    //      一级业态 ->二级业态 ->三级业态,...,三级业态_1->中文品牌列表
    //品牌： 一级业态 ->二级业态,...,二级业态 _2->英文品牌列表 按照别名长度降序排列
    //      一级业态 ->二级业态 ->三级业态,...,三级业态_2->英文品牌列表
    //品牌： 一级业态 ->二级业态,...,二级业态 _3->别名品牌列表 按照别名长度降序排列
    //      一级业态 ->二级业态 ->三级业态,...,三级业态_3->别名品牌列表
    private Map<String,List<BrandBean>> brandMultiForTrans = new  HashMap<String,List<BrandBean>>();
    
    //映射公式
    private ShopMapBrandRuleHash shopMapBrandRuleHash = new ShopMapBrandRuleHash();
    
    //翻译公式
    private RuleHash transRuleHash = new RuleHash();
    
    //业态： 一级业态 ->二级业态
    //public static Map<String,List<String>> type12 = new  HashMap<String,List<String>>();
    private MultiStringHash cgType12 = new  MultiStringHash();
    private MultiStringHash cgTypeName12 = new  MultiStringHash();
    
    //private Map<String,CGTypeBean> cgType12Map = new HashMap<String,CGTypeBean>();
    //存储三级业态的业态信息
    private Map<String,CGTypeBean> cgType3Map = new HashMap<String,CGTypeBean>();
    
    //业态： 一级业态 ->二级业态 ->三级业态
    //public static Map<String,List<String>> type123 = new  HashMap<String,List<String>>();
    public MultiStringHash cgType123 = new  MultiStringHash();
    public MultiStringHash cgTypeName123 = new  MultiStringHash();
    
    public static String CG_TYPE_LEVEL_ONE = "1";
    public static String CG_TYPE_LEVEL_TWO = "2";
    public static String CG_TYPE_LEVEL_THREE = "3";
    
    public static String BRAND_NAME_CHINA = "1";
    public static String BRAND_NAME_ENG = "2";
    public static String BRAND_NAME_OTHER = "3";
    
    
    
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
        BrandHash brand = new BrandHash();
        brand.init();
        
        
    }
    
    /**
     * 初始化品牌hash内存集合
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public void init()
    {
        System.out.println("加载品牌类对象--------------------");
        
        clear();
        System.out.println("加载目录:");
        
        System.out.println(PATH + "brand-12.csv");
        loadBrandsFromFile(PATH + "brand-12.csv",brand12,2,1);
        
        System.out.println(PATH + "brand-123.csv");
        loadBrandsFromFile(PATH + "brand-123.csv",brand123,3,1);
        
        System.out.println(PATH + "brand-12engname.csv");
        loadBrandsFromFile(PATH + "brand-12engname.csv",brand12EngName,2,2);
        
        System.out.println(PATH + "brand-123engname.csv");
        loadBrandsFromFile(PATH + "brand-123engname.csv",brand123EngName,3,2);
        
        System.out.println(PATH + "brand-12othername.csv");
        loadBrandsFromFile(PATH + "brand-12othername.csv",brand12OtherName,2,3);
        
        System.out.println(PATH + "brand-123othername.csv");
        loadBrandsFromFile(PATH + "brand-123othername.csv",brand123OtherName,3,3);
        
        System.out.println(PATH + "cgtype12.csv");
        loadType12FromFile(PATH + "cgtype12.csv");
        
        System.out.println(PATH + "cgtype123.csv");
        loadType123FromFile(PATH + "cgtype123.csv");
        
        //解析商家和品牌映射公式
        shopMapBrandRuleHash.init();
        //加载商家和品牌映射公式多二级或多三级业态品牌列表
        loadBrandListByMultiTypeForShopMapBrand();
        
        
        ///解析翻译公式
        transRuleHash.init();
        //加载翻译公式多二级或多三级业态品牌列表
        loadBrandListByMultiTypeForTrans();
        System.out.println("品牌类加载完毕--------------------");
    }
    
    
    /**
     * 清理缓存
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public  void clear()
    {
        brand12.clear();
        brand123.clear();
        brand12EngName.clear();
        brand123EngName.clear();
        cgType12.clear();
        cgType123.clear();
    }
    
    /**
     * 根据12级业态类别 读取商家列表 按照品牌名长度降序排列
     * <功能详细描述>
     * @param key
     * @return [参数说明]
     * 
     * @return List<BrandBean> [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public  List<BrandBean> getBrandListBy12Type(String key)
    {
        return brand12.get(key);
    }
    
    /**
     * 根据123级业态类别 读取商家列表 按照品牌名长度降序排列
     * <功能详细描述>
     * @param key
     * @return [参数说明]
     * 
     * @return List<BrandBean> [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public  List<BrandBean> getBrandListBy123Type(String key)
    {
        return brand123.get(key);
    }
    
    /**
     * 根据12级业态类别 读取商家列表 按照品牌英文名长度降序排列
     * <功能详细描述>
     * @param key
     * @return [参数说明]
     * 
     * @return List<BrandBean> [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public  List<BrandBean> getBrandListOrderByEngNameBy12Type(String key)
    {
        return brand12EngName.get(key);
    }
    
    /**
     * 根据123级业态类别 读取商家列表 按照品牌英文名长度降序排列
     * <功能详细描述>
     * @param key
     * @return [参数说明]
     * 
     * @return List<BrandBean> [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public  List<BrandBean> getBrandListOrderByEngNameBy123Type(String key)
    {
        return brand123EngName.get(key);
    }
    
    /**
     * 根据12级业态类别 读取商家列表 按照品牌英文名长度降序排列
     * <功能详细描述>
     * @param key
     * @return [参数说明]
     * 
     * @return List<BrandBean> [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public  List<BrandBean> getBrandListOrderByOtherNameBy12Type(String key)
    {
        return brand12OtherName.get(key);
    }
    
    /**
     * 根据123级业态类别 读取商家列表 按照品牌英文名长度降序排列
     * <功能详细描述>
     * @param key
     * @return [参数说明]
     * 
     * @return List<BrandBean> [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public  List<BrandBean> getBrandListOrderByOtherNameBy123Type(String key)
    {
        return brand123OtherName.get(key);
    }
    
    /**
     * 根据1级业态类别 读取商家列表  
     * <功能详细描述>
     * @param key
     * @return [参数说明]
     * 
     * @return List<BrandBean> [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public  List<BrandBean> getBrandListBy1Type(String key)
    {
        String [] type2Array = cgType12.sonKeys(key);
        
        List<BrandBean> list = new ArrayList<BrandBean>();
        
        for(String type2 : type2Array)
        {
            String type12 = key+"_"+type2;
            List<BrandBean> tempList = getBrandListBy12Type(type12);
            
            if(null != tempList)
            {
                list.addAll(getBrandListBy12Type(type12));
            }
        }
        
        return list;
    }
    
    /**
     * 根据1级业态类别 读取商家列表  
     * <功能详细描述>
     * @param key
     * @return [参数说明]
     * 
     * @return List<BrandBean> [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public  List<BrandBean> getBrandListOrderByEngNameBy1Type(String key)
    {
        String [] type2Array = cgType12.sonKeys(key);
        
        List<BrandBean> list = new ArrayList<BrandBean>();
        
        for(String type2 : type2Array)
        {
            String type12 = key+"_"+type2;
            List<BrandBean> tempList = getBrandListOrderByEngNameBy12Type(type12);
            
            if(null != tempList)
            {
                list.addAll(getBrandListOrderByEngNameBy12Type(type12));
            }
        }
        
        return list;
    }
    
    
    /**
     * 加入品牌  品牌需要排序
     * @param brandBean [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    
    public  void putBrand(Map<String,List<BrandBean>> brandList,BrandBean brandBean,int level)
    {
        String key = "";
        
        if(level == 2)
        {
            key = brandBean.getBrandOneTypeId()+"_" +brandBean.getBrandTwoTypeId();
        }
        else if (level == 3)
        {
            key = brandBean.getBrandOneTypeId()+"_" +brandBean.getBrandTwoTypeId()+"_" +brandBean.getBrandThreeTypeId();
        }
       
        
        if(brandList.containsKey(key))
        {
            (brandList.get(key)).add(brandBean);
        }
        else
        {
            List<BrandBean> list = new ArrayList<BrandBean>();
            list.add(brandBean);
            brandList.put(key, list);
        }
        
    }
    
    /**
     * 加入品牌类别12级
     * @param brandBean [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public  void putType(String type1,String type2)
    {
        cgType12.put(cgType12.k(type1,type2), "");
    }
    
    
    /**
     * 加入品牌类别123级
     * @param brandBean [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public  void putType(String type1,String type2,String type3)
    {
        cgType123.put(cgType123.k(type1,type2,type3), "");
        
    }
    
    /**
     * 加入品牌类别12级
     * @param brandBean [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private  void putTypeName(String type1name,String type2name,String type1,String type2)
    {
        cgType12.put(cgType12.k(type1name,type2name), type1+"_"+type2);
    }
    
    /**
     * 根据中文的key 读取英文的key 业态1 2 级  
     * @param brandBean [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public  String getTypeIdKeyByTypeNameKey(String type1name,String type2name)
    {
        return cgType12.v(type1name,type2name);
    }
    
    
    /**
     * 加入品牌类别123级
     * @param brandBean [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private  void putTypeName(String type1name,String type2name,String type3name,String type1,String type2,String type3)
    {
        cgType123.put(cgType123.k(type1name,type2name,type3name), type1+"_"+type2+"_"+type3);
    }
    
    /**
     * 根据中文的key 读取英文的key 业态1 2 3级  
     * @param brandBean [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public  String getTypeIdKeyByTypeNameKey(String type1name,String type2name,String type3name)
    {
         return cgType123.v(type1name,type2name,type3name);
    }
    
    /**
     * 根据一级分类获取二级分类
     * <功能详细描述>
     * @param oneTypeId
     * @return [参数说明]
     * 
     * @return List<String> [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public  List<String> get2TypeBy1Type(String type1)
    {
        List<String> list = Arrays.asList(cgType12.sonKeys(type1));
        return list;
    }
    
    /**
     * 根据一级分类和二级分类获取三级分类
     * <功能详细描述>
     * @param oneTypeId
     * @return [参数说明]
     * 
     * @return List<String> [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public List<String> get3TypeBy12Type(String type1,String type2)
    {
        List<String> list = Arrays.asList(cgType12.sonKeys(type1,type2));
        return list;
    }
    
    /**
     * 加载品牌到内存
     * @param fileName [参数说明]
     * nameType 1 中文名 2 英文名 3 别名
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public  void loadBrandsFromFile(String filename,Map<String,List<BrandBean>> brandList,int level,int nameType)
    {
        File file = new File(filename);
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
                BrandBean brandBean  = null;
                
                if(level == 2)
                {
                    brandBean = createBrandWithType12(lineTxt,nameType);
                }
                else if (level == 3)
                {
                    brandBean = createBrandWithType123(lineTxt,nameType);
                }
                 
                putBrand(brandList,brandBean,level);
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
     * 加载城格一级二级业态到内存
     * @param fileName [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public void loadType12FromFile(String filename)
    {
        File file = new File(filename);
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
                
                String [] str = lineTxt.split(",");
                putType(str[2],str[3]);
                putTypeName(str[0],str[1],str[2],str[3]);
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
     * brandNameType 1 中文品牌  2 英文品牌  3 别名品牌
     * @param onetype
     * @param twotype
     * @param threetype
     * @param type
     * @return [参数说明]
     * 
     * @return List<BrandBean> [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public List<BrandBean> getSortBrandList(String onetypeName,String twotypeName,String threetypeName,String brandNameType)
    {
        List<BrandBean> brandList = new ArrayList<BrandBean> ();
        String typekey = "";
        int type = 3;
        
        if(notNullAndNot0(threetypeName))
        {
            typekey = getTypeIdKeyByTypeNameKey(onetypeName, twotypeName,threetypeName);
            type = 3;
        }
        else if (notNullAndNot0(twotypeName))
        {
            typekey = getTypeIdKeyByTypeNameKey(onetypeName, twotypeName);
            type = 2;
            
        }
        
        if(BRAND_NAME_CHINA.equals(brandNameType))
        {
            brandList = (type==3)?getBrandListBy123Type(typekey):getBrandListBy12Type(typekey);
        }
        else if(BRAND_NAME_ENG.equals(brandNameType))
        {
            brandList = (type==3)?getBrandListOrderByEngNameBy123Type(typekey):getBrandListOrderByEngNameBy12Type(typekey);
        }
        else if (BRAND_NAME_OTHER.equals(brandNameType))
        {
            brandList = (type==3)?getBrandListOrderByOtherNameBy123Type(typekey):getBrandListOrderByOtherNameBy12Type(typekey);
        }
        
        return brandList;
    }
    
    /**
     * 此方法是对类内部使用的，用于初始化构造数据格局
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private void loadBrandListByMultiTypeForShopMapBrand()
    {
        System.out.println("加载多二级或多三级业态品牌列表到内存");
        
        List<ShopMapBrandRuleBean> list = shopMapBrandRuleHash.getMultiTypeRuleList();
        
        for(ShopMapBrandRuleBean mapRule : list)
        {
            List <BrandBean> brandNameList = getSortBrandListMultiTypes(mapRule.getCgBrand1Name(),mapRule.getCgBrand2Name(),mapRule.getCgBrand3Name(),BRAND_NAME_CHINA);
            List <BrandBean> brandEngNameList = getSortBrandListMultiTypes(mapRule.getCgBrand1Name(),mapRule.getCgBrand2Name(),mapRule.getCgBrand3Name(),BRAND_NAME_ENG);
            List <BrandBean> brandOtherNameList = getSortBrandListMultiTypes(mapRule.getCgBrand1Name(),mapRule.getCgBrand2Name(),mapRule.getCgBrand3Name(),BRAND_NAME_OTHER);
            String basekey =  "";
            
            if(null != mapRule.getCgBrand3Name() && !"".equals(mapRule.getCgBrand3Name().trim()))
            {
                basekey =  mapRule.getCgBrand1Name()+"_"+mapRule.getCgBrand2Name()+"_"+mapRule.getCgBrand3Name();
            }
            else if (null != mapRule.getCgBrand2Name() && !"".equals(mapRule.getCgBrand2Name().trim()))
            {
                basekey =  mapRule.getCgBrand1Name()+"_"+mapRule.getCgBrand2Name();
            }
            else if(null != mapRule.getCgBrand1Name() && !"".equals(mapRule.getCgBrand1Name().trim()))
            {
                basekey =  mapRule.getCgBrand1Name();
            }
            
            String namekey = basekey + "_" + BRAND_NAME_CHINA;
            String engNameKey = basekey + "_" + BRAND_NAME_ENG;
            String otherNameKey = basekey + "_" + BRAND_NAME_OTHER;
            
            brandMultiForShopMapBrand.put(namekey, brandNameList);
            brandMultiForShopMapBrand.put(engNameKey, brandEngNameList);
            brandMultiForShopMapBrand.put(otherNameKey, brandOtherNameList);
            
//            System.out.println("namekey:"+namekey+";brandNameList:"+brandNameList.size());
//            System.out.println("engNameKey:"+engNameKey+";brandEngNameList:"+brandEngNameList.size());
//            System.out.println("otherNameKey:"+otherNameKey+";brandOtherNameList:"+brandOtherNameList.size());
            
        }
        
        System.out.println("加载多二级或多三级业态品牌列表到内存完毕");
    }
    
    
    
    /**
     * 此方法对外，用于根据多个二级业态或多个三级业态的key 读取相应的品牌列表
     * <功能详细描述>
     * @param key
     * @return [参数说明]
     * 
     * @return List<BrandBean> [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public List<BrandBean> getBradnListByMultiTypekeyForShopMapBrand(String key)
    {
        return brandMultiForShopMapBrand.get(key);
    }
    
    
    /**
     * 此方法是对类内部使用的，用于初始化构造数据格局 加载多业态下品牌 用于翻译
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private void loadBrandListByMultiTypeForTrans()
    {
        System.out.println("加载多二级或多三级业态品牌列表到内存");
        
        List<RuleBean> list = transRuleHash.getMultiTypeRuleList();
        
        for(RuleBean mapRule : list)
        {
            List <BrandBean> brandNameList = getSortBrandListMultiTypes(mapRule.getCgtype1name(),mapRule.getCgtype2name(),mapRule.getCgtype3name(),BRAND_NAME_CHINA);
            List <BrandBean> brandEngNameList = getSortBrandListMultiTypes(mapRule.getCgtype1name(),mapRule.getCgtype2name(),mapRule.getCgtype3name(),BRAND_NAME_ENG);
            List <BrandBean> brandOtherNameList = getSortBrandListMultiTypes(mapRule.getCgtype1name(),mapRule.getCgtype2name(),mapRule.getCgtype3name(),BRAND_NAME_OTHER);
            String basekey =  "";
            
            if(null != mapRule.getCgtype3name() && !"".equals(mapRule.getCgtype3name().trim()))
            {
                basekey =  mapRule.getCgtype1name()+"_"+mapRule.getCgtype2name()+"_"+mapRule.getCgtype3name();
            }
            else if (null != mapRule.getCgtype2name() && !"".equals(mapRule.getCgtype2name().trim()))
            {
                basekey =  mapRule.getCgtype1name()+"_"+mapRule.getCgtype2name();
            }
            else if(null != mapRule.getCgtype1name() && !"".equals(mapRule.getCgtype1name().trim()))
            {
                basekey =  mapRule.getCgtype1name();
            }
            
            String namekey = basekey + "_" + BRAND_NAME_CHINA;
            String engNameKey = basekey + "_" + BRAND_NAME_ENG;
            String otherNameKey = basekey + "_" + BRAND_NAME_OTHER;
            
            brandMultiForTrans.put(namekey, brandNameList);
            brandMultiForTrans.put(engNameKey, brandEngNameList);
            brandMultiForTrans.put(otherNameKey, brandOtherNameList);
            
//            System.out.println("namekey:"+namekey+";brandNameList:"+brandNameList.size());
//            System.out.println("engNameKey:"+engNameKey+";brandEngNameList:"+brandEngNameList.size());
//            System.out.println("otherNameKey:"+otherNameKey+";brandOtherNameList:"+brandOtherNameList.size());
            
        }
        
        System.out.println("加载多二级或多三级业态品牌列表到内存完毕");
    }
    
    
    /**
     * 此方法对外，用于根据多个二级业态或多个三级业态的key 读取相应的品牌列表
     * <功能详细描述>
     * @param key
     * @return [参数说明]
     * 
     * @return List<BrandBean> [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public List<BrandBean> getBradnListByMultiTypekeyForTrans(String key)
    {
        return brandMultiForTrans.get(key);
    }
    
    /**
     * brandNameType 1 中文品牌  2 英文品牌  3 别名品牌
     * 此方法是对类内部使用的，用于初始化构造数据格局
     * @param onetype
     * @param twotype
     * @param threetype
     * @param type
     * @return [参数说明]
     * 
     * @return List<BrandBean> [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private List<BrandBean> getSortBrandListMultiTypes(String onetypeName,String twotypeName,String threetypeName,String brandNameType)
    {
        List<BrandBean> brandList = new ArrayList<BrandBean> ();
        
        if(notNullAndNot0(threetypeName))
        {
            String[] threetypeNameArray = threetypeName.split("\\|");
            
            for(int i = 0;i < threetypeNameArray.length;i++)
            {
                String typekey = getTypeIdKeyByTypeNameKey(onetypeName, twotypeName,threetypeNameArray[i]);
                
                List<BrandBean> tempbrandList = null;
                
                if(BRAND_NAME_CHINA.equals(brandNameType))
                {
                    tempbrandList = getBrandListBy123Type(typekey);
                }
                else if(BRAND_NAME_ENG.equals(brandNameType))
                {
                    tempbrandList = getBrandListOrderByEngNameBy123Type(typekey);
                }
                else if (BRAND_NAME_OTHER.equals(brandNameType))
                {
                    tempbrandList = getBrandListOrderByOtherNameBy123Type(typekey);
                }
                 
                if(null != tempbrandList)
                {
                    brandList.addAll(tempbrandList);
                }
            }
        }
        else if (notNullAndNot0(twotypeName))
        {

            String[] twotypeNames = twotypeName.split("\\|");
            
            for(int i = 0;i < twotypeNames.length;i++)
            {
                String typekey = getTypeIdKeyByTypeNameKey(onetypeName, twotypeNames[i]);
                
                List<BrandBean> tempbrandList = null;
                
                if(BRAND_NAME_CHINA.equals(brandNameType))
                {
                    tempbrandList = getBrandListBy12Type(typekey);
                }
                else if(BRAND_NAME_ENG.equals(brandNameType))
                {
                    tempbrandList = getBrandListOrderByEngNameBy12Type(typekey);
                }
                else if (BRAND_NAME_OTHER.equals(brandNameType))
                {
                    tempbrandList = getBrandListOrderByOtherNameBy12Type(typekey);
                }
                
                if(null != tempbrandList)
                {
                    brandList.addAll(tempbrandList);
                }
            }
        }
        
        sortByLength(brandList,brandNameType);
        
        return brandList;
    }
    
    /**
     * 非空校验
     * <功能详细描述>
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
     * 品牌列表按照品牌名长度排序
     * brandNameType 1 中文品牌  2 英文品牌  3 别名品牌
     * <功能详细描述>
     * @param brandNameList [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private void sortByLength(List<BrandBean> brandNameList,String brandNameType)
    {
        
        BrandBean oneTempBrandBean =null;
        BrandBean twoTempBrandBean = null;
         
         for(int i = 0;i <brandNameList.size();i++ )
         {
             for(int j = i+1;j < brandNameList.size();j++)
             {
                 oneTempBrandBean = brandNameList.get(i);
                 twoTempBrandBean = brandNameList.get(j);
                 
                 if(BRAND_NAME_CHINA.equals(brandNameType))
                 {
                     if(oneTempBrandBean.getBrandName().trim().length() <twoTempBrandBean.getBrandName().trim().length() )
                     {
                         brandNameList.set(i, twoTempBrandBean);
                         brandNameList.set(j, oneTempBrandBean);
                     }
                 }
                 else if(BRAND_NAME_ENG.equals(brandNameType))
                 {
                     if(oneTempBrandBean.getBrandEngName().trim().length() <twoTempBrandBean.getBrandEngName().trim().length() )
                     {
                         brandNameList.set(i, twoTempBrandBean);
                         brandNameList.set(j, oneTempBrandBean);
                     }
                 }
                 else if (BRAND_NAME_OTHER.equals(brandNameType))
                 {
                     if(oneTempBrandBean.getBrandOtherName().trim().length() <twoTempBrandBean.getBrandOtherName().trim().length() )
                     {
                         brandNameList.set(i, twoTempBrandBean);
                         brandNameList.set(j, oneTempBrandBean);
                     }
                 }
             }
         }
         
//         if(BRAND_NAME_CHINA.equals(brandNameType))
//         {
//             System.out.println("==================================");
//             
//             for(int i = 0;i <brandNameList.size();i++)
//             {
//                 System.out.println(brandNameList.get(i).getBrandName());
//             }
//             
//             System.out.println("==================================");
//         }
         
    }
    
    
    /**
     * 加载城格一级二级业态到内存
     * @param fileName [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public void loadType123FromFile(String filename)
    {
        File file = new File(filename);
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
                
                String [] str = lineTxt.split(",");
                putType(str[3],str[4],str[5]);
                putTypeName(str[0],str[1],str[2],str[3],str[4],str[5]);
                cgType3Map.put(str[5], new CGTypeBean(str[2],str[5],CG_TYPE_LEVEL_THREE));
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
     * 
     * <功能详细描述>
     * @param brandInfo
     * @return [参数说明]
     * nametype 1 中文   2 英文   3 别名
     * @return BrandBean [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public BrandBean createBrandWithType12(String brandInfo,int nametype)
    {
        
        String[] values = brandInfo.split(",");
        
        BrandBean brandBean1 = new BrandBean();
        
        if(nametype == 1)
        {
            brandBean1.setBrandName(values[4]);
        }
        else if (nametype == 2)
        {
            brandBean1.setBrandEngName(values[4]);
        }
        else if (nametype == 3)
        {
            brandBean1.setBrandOtherName(values[4]);
        }
       
        brandBean1.setBrandId(values[5]);
        brandBean1.setBrandOneTypeId(values[0]);
        brandBean1.setBrandTwoTypeId(values[1]);
        brandBean1.setBrandOneTypeName(values[2]);
        brandBean1.setBrandTwoTypeName(values[3]);
        
        //计算品牌分词
        brandBean1.setBrandWords(getChinaeseWordList(brandBean1.getBrandName()));
        
        //计算品牌字符分词
        brandBean1.setBrandChars(getChinaeseCharList(brandBean1.getBrandName()));
        
        //System.out.println("品牌名："+brandBean1.getBrandName()+" 分词:"+getChinaeseWordList(brandBean1.getBrandName()));
        return brandBean1;
    }
    
    /**
     * 
     * <功能详细描述>
     * @param brandInfo
     * @return [参数说明]
     * nametype 1 中文   2 英文   3 别名
     * @return BrandBean [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public BrandBean createBrandWithType123(String brandInfo,int nametype)
    {
        
        String[] values = brandInfo.split(",");
        
        BrandBean brandBean1 = new BrandBean();
        
        if(nametype == 1)
        {
            brandBean1.setBrandName(values[6]);
        }
        else if (nametype == 2)
        {
            brandBean1.setBrandEngName(values[6]);
        }
        else if (nametype == 3)
        {
            brandBean1.setBrandOtherName(values[6]);
        }
        
        brandBean1.setBrandId(values[7]);
        brandBean1.setBrandOneTypeId(values[0]);
        brandBean1.setBrandTwoTypeId(values[1]);
        brandBean1.setBrandThreeTypeId(values[2]);
        brandBean1.setBrandOneTypeName(values[3]);
        brandBean1.setBrandTwoTypeName(values[4]);
        brandBean1.setBrandThreeTypeName(values[5]);
        
        //计算品牌分词
        brandBean1.setBrandWords(getChinaeseWordList(brandBean1.getBrandName()));
        
        //计算品牌字符分词
        brandBean1.setBrandChars(getChinaeseCharList(brandBean1.getBrandName()));
        
        //System.out.println("品牌名："+brandBean1.getBrandName()+" 分词:"+getChinaeseWordList(brandBean1.getBrandName()));
        
        return brandBean1;
    }
    
    
    /**
     * 获取分词列表 分词算法 来自开源
     * @param src
     * @return [参数说明]
     * 
     * @return List<String> [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private  String getChinaeseWordList(String src)
    {
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
            
            if(src.length() <=3)
            {
                return "";
            }
            
            StringReader sr = new StringReader(src);
            IKSegmenter ik = new IKSegmenter(sr, true);
            Lexeme lex = null;
            
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
        
        return sb.toString();
    }
    
    /**
     * 获取分词列表 按照单个字符进行分词
     * @param src
     * @return [参数说明]
     * 
     * @return List<String> [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private  String getChinaeseCharList(String src)
    {
        StringBuilder sb = new StringBuilder();
        
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
        src =  src.replace(" ", "");
        
        if(!isChina(src))
        {
            return "";
        }
        
        //含有非中文字符也不分词
        if(!isAllChinaese(src))
        {
            return "";
        }
        
        int len = src.length();
        
        //只有纯中文 才按照分词匹配
        for(int i = 0;i < len;i++)
        {
            sb.append(src.substring(i, i+1)).append(",");
        }
        
        //删除最后一个逗号
        sb.delete(sb.length()-1, sb.length());
        
        return sb.toString();
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
    public String getStringExcludeBracket(String src)
    {
        if (null == src || src.trim().equals(""))
        {
            return "";
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
        if (left >= 0 && right >= 0)
        {
            if (left <= right)
            {
                result = shopName.substring(0, left)
                        + shopName.substring(right + 1);
            }
            else if (left > right)
            {
                result = shopName;
            }
        }
        else
        {
            result = shopName;
        }
        
        result = result.replace(" ", "");
        
        return result;
        
    }
    /**
     * 
     * <功能详细描述>
     * @param str
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
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
            if(!isChinaChar(charArr[i]))
            {
                return false;
            }
        }
        
        return true;
    }
    
    
    private static boolean isChinaChar(char c)
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
    
    public static String CHAR_LIST = "｀～１２３４５６７８９００－＝、‘；／。，ｑｗｅｒｔｙｕｉｏｐａｓｄｆｇｈｊｋｌｚｘｃｖｂｎｍＱＷＥＲＴＹＵＩＯＰＡＳＤＦＧＨＪＫＬＺＸＣＶＢＮＭ！＠＃＄％＾＆＊（）＿＋｛｝【】｜＼＂：？＞＜";
    
    
    private boolean isChinaChar(String str)
    {
        if(CHAR_LIST.contains(str))
        {
            return false;
        }
        
        return str.getBytes().length != str.length();
        
    }
    
    private boolean isChina(String str)
    {
        return str.getBytes().length != str.length();
    }
    
    
    /**
     * <一句话功能简述>
     * <功能详细描述>
     * @param obj [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    
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
