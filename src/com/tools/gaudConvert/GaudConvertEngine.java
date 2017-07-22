package com.tools.gaudConvert;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 高德转换城格的大流程
 * 1 每次从文件中读取高德数据1000条到内存（每次读取一个分类的）
 * 2 把这一个分类的公式全部运行一遍，如果符合某一公式，就计算，转换，如果所有公式都不符合，就放入到其他中
 * 3 对于每个大类的分类，按照公式的排序进行翻译
 * 
 * 表：
 * cg_poi 高德店铺表
 * cg_poi_type 高德业态
 * cg_poi_type_relation 高德业态和店铺关系表
 * 
 * cg_mid_poi_shop 转换到城格的商家表
 * cg_mid_poi_shop_detail 商家详细表
 * cg_mid_poi_type 商家和城格二级业态关联表
 * cg_mid_poi_style_relation 商家和城格三级业态关联表
 * 
 * cg_zs_mapping_products 高德转换为城格的公式表
 * cg_zs_mapping_brand  具体某个公式下 高德，城格商家关联表 可以知道商家从那个公式导入过来的
 * @author  sihua
 * @version  [版本号, 2015-6-24]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class GaudConvertEngine
{
    private static String GD_PATH ="E:\\ysh\\高德\\翻译\\等待翻译数据\\";
    //private static String GD_PATH = "E:\\ysh\\高德\\抓取\\第一批\\统计\\";
    
    private static String DEST_PATH = "E:\\ysh\\高德\\翻译\\";
    
    //test.csv
    //GD_福建省_南平市_14年12月28日 23时47分40秒_107_总计_68226.csv
    //GD_首批_厦门市_14年12月27日 10时49分32秒_总计_199012single.csv
    //GD_首批_南京市_总计_372091single.csv
    //配置省份 城市 编码 文件
    private static String PROVINCE_FLAG = "guangdong";
    
    private static String CITY_FLAG = "guangzhou";
    
    private static String GD_FILE_NAME = "GD_首批_广州市_14年12月26日 23时06分39秒_总计_633104single.csv";
    
    //南京220 南平 55 厦门 60 福州 53 三明 59 长沙  197 扬州 231 合肥 3401 南昌  233 郑州 149 北京 52 金华  386 淮安  224
    //广州 76 深圳 77
    private static String CITY_CODE = "76";
    
    private int g_id = 1;
    
    private static String GD_SHOP_FILE = GD_PATH + GD_FILE_NAME;
    
    //1 关闭 2 开
    private static int SUB_SWITCH = 2;
    
    private static int SUB_TABLE_FLAG = 2;
    
    private static int NOT_SUB_TABLE_FLAG = 1;
    
    private static String GD_SHOP_TRANS =  DEST_PATH
            + "SHOP_" + PROVINCE_FLAG + "_(" + CITY_FLAG + ").csv";
    
    //(13餐饮服务业态对应的所有关键词.doc)第5条规则：
    //5、将高德一级分类“餐饮服务”的关键词为“宴会|厉家菜|谭家菜|燕鲍翅|鲍鱼|燕窝|鱼翅|蟹宴|酒店|甲鱼|河豚”的全部商家，
    //对应城格一级分类“餐饮”的二级分类“正餐厅”。
    //注意：关键词为“酒店”还有文字尾缀的商家除尾缀名为“中餐厅”的其余均不抓取
    //请注意核对此规则的主键
    private static String RULE_JIUDIAN_ID = "812";
    
    //非分表
    private static String CG_SHOP_TABLE_NAME = "cg_mid_poi_shop";
    
    private static String CG_SHOP_TYPE2_TABLE_NAME = "cg_mid_poi_type";
    
    private static String CG_SHOP_TYPE3_TABLE_NAME = "cg_mid_poi_style_relation";
    
    private static String SQL_INSERT_SHOP_HEAD = "insert into {table} (id,brand_id,mid,branch_name,address,phone,fax,contact,point,city,citycode,areacode,add_user,add_time,update_time,types) VALUES\n ";
    
    private static String SQL_INSERT_SHOP_TYPE2_HEAD = "insert into {table} (brand_id,type,mid,level,add_time) VALUES\n";
    
    private static String SQL_INSERT_SHOP_TYPE3_HEAD = "insert into {table} (style_id,brand_id,mid,add_time) VALUES\n";
    
    private static String SQL_INSERT_SHOP_VALUES = " ({id},{brand_id},{mid},'{branch_name}','{address}','{phone}','{fax}',' ','{point}','{city}','{citycode}','{areacode}',{add_user},{add_time},{update_time},'{types}')";
    
    private static String SQL_INSERT_SHOP_TYPE2_VALUES = "({brand_id},{type},{mid},{level},{add_time})";
    
    private static String SQL_INSERT_SHOP_TYPE3_VALUES = "({style_id},{brand_id},{mid},{add_time})";
    
    //分表
    private static String CG_SHOP_SUB_TABLE_NAME = "cg_shop_"
            + PROVINCE_FLAG.toLowerCase();
    
    private static String CG_SHOP_TYPE2_SUB_TABLE_NAME = "cg_shop_type2_"
            + PROVINCE_FLAG.toLowerCase();
    
    private static String CG_SHOP_TYPE3_SUB_TABLE_NAME = "cg_shop_type3_"
            + PROVINCE_FLAG.toLowerCase();
    
    private static String SQL_INSERT_SUB_TABLE_SHOP_HEAD = "insert into {table} (id,brand_id,mid,branch_name,pinyin,address,phone,fax,contact,point,baidu_lon,baidu_lat,citycode,areacode,add_user,add_time,update_time) VALUES\n ";
    
    private static String SQL_INSERT_SUB_TABLE_SHOP_TYPE2_HEAD = "insert into {table} (shop_id,type_id,citycode,areacode,mid,level,add_time) VALUES\n";
    
    private static String SQL_INSERT_SUB_TABLE_SHOP_TYPE3_HEAD = "insert into {table} (shop_id,type_id,citycode,areacode,mid,add_time) VALUES\n";
    
    private static String SQL_INSERT_SUB_TABLE_SHOP_VALUES = " ({id},{brand_id},{mid},'{branch_name}','{pinyin}','{address}','{phone}','{fax}',' ','{point}',{baidu_lon},{baidu_lat},{citycode},{areacode},{add_user},{add_time},{update_time})";
    
    private static String SQL_INSERT_SUB_TABLE_SHOP_TYPE2_VALUES = "({shop_id},{type_id},{citycode},{areacode},{mid},{level},{add_time})";
    
    private static String SQL_INSERT_SUB_TABLE_SHOP_TYPE3_VALUES = "({shop_id},{type_id},{citycode},{areacode},{mid},{add_time})";
    
    private static BufferedWriter writeHandle = null;
    
    private BrandHash brandHash = new BrandHash();
    
    private RuleHash ruleHash = new RuleHash();
    
    private CityCodeHash cityCodeHash = new CityCodeHash();
    
    private ShopMapBrandRuleHash shopMapBrandRuleHash = new ShopMapBrandRuleHash();
    private ExportTypeFromDB exportTypeFromDB = new ExportTypeFromDB();
    
    private GetFirstLetter getFirstTools = new GetFirstLetter();
    
    private long count = 0;
    
    private long total = 0;
    
    private long brandShopCount = 0;
    
    
    private static final double x_pi = 3.14159265358979324 * 3000.0 / 180.0;
    
    private static final double pi = 3.14159265358979324;
    private static final double a = 6378245.0;
    private static final double ee = 0.00669342162296594323;
    
    //GD_首批_南京市_14年12月26日 20时16分04秒_总计_372091single.csv
    
    /** 
     * 
     * @param args [参数说明]
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static void main(String[] args)
    {
        GaudConvertEngine engine = new GaudConvertEngine();
        
        System.out.println("启动数据工厂======================");
        
        //engine.runCity("FUJIAN","fuzhou","53",SUB_TABLE_FLAG,"GD_首批_福州市_14年12月27日 14时35分40秒_19_总计_199259single.csv",1);
        //engine.runCity("FUJIAN","xiamen","60",SUB_TABLE_FLAG,"GD_首批_厦门市_14年12月27日 10时49分32秒_总计_199012single.csv",200000);
        //engine.runCity("FUJIAN","nanping","55",SUB_TABLE_FLAG,"GD_福建省_南平市_14年12月28日 23时47分40秒_107_总计_68226.csv",400000);
        //engine.runCity("FUJIAN","sanming","59",SUB_TABLE_FLAG,"GD_福建省_三明市_14年12月28日 23时43分47秒_106_总计_57850.csv",470000);
        //engine.runCity("BEIJING","beijing","52",SUB_TABLE_FLAG,"GD_首批_北京市_14年12月26日 21时05分49秒_总计_1029643single.csv",1);
        //engine.runCity("JIANGSU","nanjing","220",SUB_TABLE_FLAG,"GD_首批_南京市_14年12月26日 20时16分04秒_总计_372091single.csv",400000);
        //engine.runCity("JIANGSU","nanjing","220",SUB_TABLE_FLAG,"test.csv",1);
        //
        //义务属于金华
        //engine.runCity("ZHEJIANG","jinhua","386",SUB_TABLE_FLAG,"GD_浙江省_金华市_14年12月28日 20时41分15秒_90_总计_224598.csv",1);
        //engine.runCity("JIANGSU","yangzhou","231",SUB_TABLE_FLAG,"GD_江苏省_扬州市_14年12月28日 09时00分42秒_52_总计_163003.csv",1350000);
        //engine.runCity("HENAN","zhengzhou","149",SUB_TABLE_FLAG,"GD_首批_郑州市_14年12月27日 12时27分01秒_13_总计_325656single.csv",1);
        //engine.runCity("JIANGSU","huaian","224",SUB_TABLE_FLAG,"GD_江苏省_淮安市_14年12月28日 05时32分05秒_48_总计_114084.csv",1520000);
        
        //engine.runCity("JIANGSU","suzhou","221",SUB_TABLE_FLAG,"GD_首批_苏州市_14年12月27日 09时19分16秒_总计_567720single.csv",780000);
        System.out.println("数据工厂停止运行======================");
        
    }
    
    /**
     * 按照城市翻译 定制化入口
     * @param provinceName
     * @param cityname
     * @param citycode
     * @param switchFlag
     * @param dataFile
     * @param tablePrimaryStartIndex [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private void runCity(String provinceName, String cityname, String citycode,
            int switchFlag, String dataFile, int tablePrimaryStartIndex)
    {
        
        PROVINCE_FLAG = provinceName;
        CITY_FLAG = cityname;
        CITY_CODE = citycode;
        SUB_SWITCH = switchFlag;
        GD_FILE_NAME = dataFile;
        GD_SHOP_FILE = GD_PATH + GD_FILE_NAME;
        
        GD_SHOP_TRANS =  DEST_PATH
        + "SHOP_" + PROVINCE_FLAG + "_(" + CITY_FLAG + ").csv";
        
        CG_SHOP_SUB_TABLE_NAME = "cg_shop_" + PROVINCE_FLAG.toLowerCase();
        CG_SHOP_TYPE2_SUB_TABLE_NAME = "cg_shop_type2_"
                + PROVINCE_FLAG.toLowerCase();
        CG_SHOP_TYPE3_SUB_TABLE_NAME = "cg_shop_type3_"
                + PROVINCE_FLAG.toLowerCase();
        
        g_id = tablePrimaryStartIndex;
        
        runMain();
        
        genShopScript(SUB_TABLE_FLAG,"CG_");
        //genShopScript(NOT_SUB_TABLE_FLAG,"CG_MID_POI_");
        
    }
    
    /**
     * <默认构造函数>
     * 构造数据翻译工厂
     */
    
    public GaudConvertEngine()
    {
        //exportTypeFromDB.run();
        System.out.println("构建工厂：===========================");
        ruleHash.init();
        brandHash.init();
        cityCodeHash.init();
        shopMapBrandRuleHash.init();
        System.out.println("工厂搭建完毕：=======================\n\n");
    }
    
    /**
     * 数据翻译后的写出句柄
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    
    public void initWriteHandle()
    {
        System.out.println("初始化翻译后的商家文件句柄："+GD_SHOP_TRANS);
        
        try
        {
            writeHandle = new BufferedWriter(new FileWriter(GD_SHOP_TRANS,
                    false));
            
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * 引擎总入口
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public void runMain()
    {
        
        System.out.println("准备开始翻译的城市为："+PROVINCE_FLAG+"->"+CITY_FLAG+"->"+CITY_CODE);
        System.out.println("原始文件："+GD_SHOP_FILE);
        long starttime = System.currentTimeMillis();
        System.out.println("翻译启动：------------------------------");
        
        initWriteHandle();
        
        File file = new File(GD_SHOP_FILE);
        BufferedReader read = null;
        DataInputStream in = null;
        String lineTxt = null;
        
        try
        {
            in = new DataInputStream(new FileInputStream(file));
            read = new BufferedReader(new InputStreamReader(in, "utf8"));
            
            while ((lineTxt = read.readLine()) != null)
            {
                lineTxt = lineTxt.replace("\\", "\\\\");
                lineTxt = lineTxt.replace("'", "''");
                
                runAllRule(lineTxt);
                
                if(total%5000 == 0)
                {
                    long endtime = System.currentTimeMillis();
                    long time = (endtime - starttime)/1000/60 ;
                    long timeperiod = (endtime - starttime)/1000;
                    long speed = 0;
                    if(timeperiod > 0)
                    {
                        speed = total/timeperiod;
                    }
                    //System.out.println(shopInfo + "  耗费" +time);
                    System.out.println("原始数量: " + total+ "  翻译数量:" + count + "  品牌商家量:" + brandShopCount +"  耗时:"+time + "分" +"  平均速度:"+speed+"/每秒");
                }
            }
            
            long endtime = System.currentTimeMillis();
            long time = (endtime - starttime)/1000/60 ;
            long timeperiod = (endtime - starttime)/1000;
            long speed = 0;
            if(timeperiod > 0)
            {
                speed = total/timeperiod;
            }
            
            System.out.println("原始总数量: " + total+ "  翻译总数量:" + count  + "  品牌商家总量:" + brandShopCount +"  总耗时:"+time + "分" + "  平均速度:"+speed+"/每秒");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            closeStream(read);
            closeStream(writeHandle);
            
            System.out.println("关闭翻译后的商家文件句柄和原始文件句柄");
            
        }
        
        System.out.println("翻译结束-----------------------------------");
        
    }
    
    /**
     * 核心流程
     * <功能详细描述>
     * @param shopInfo [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    
    public void runAllRule(String shopInfo)
    {
        
        long starttime = System.currentTimeMillis();
        
        //根据商家判断，运行哪一批公式
        GDShopBean shopBean = createShopBean(shopInfo);
        
        //运行这批公式，如果这条记录符合某公式了，则按照规则写文件，后续生成脚本 ，同时运行结束 
        String gdType1Name = shopBean.getType1();
        
        List<RuleBean> ruleList = ruleHash.getRuleListByTypeName(gdType1Name);
        boolean flag = false;
        
        if (null != ruleList && ruleList.size() > 0)
        {
            flag = dealShopByRule(ruleList, shopBean, gdType1Name);
        }
        
        //如果没有符合条件的，写入到另外的文件记录
        if (!flag)
        {
            writeFile(shopBean);
            total++;
        }
        else
        {
            count++;
            total++;
        }
        
        long endtime = System.currentTimeMillis();
        long time = (endtime - starttime) ;
        
        if (time > 100)
        {
            System.out.println(shopInfo + "  耗费" +time);
        }
        
    }
    
    public void writeFile(GDShopBean shopBean)
    {
        System.out.println("没有匹配的:" + shopBean.toString());
    }
    
    /**
     * 核心逻辑元
     * @param ruleIds
     * @param gdShopBean
     * @param ruleOneTypeName
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    
    public boolean dealShopByRule(List<RuleBean> ruleList,
            GDShopBean gdShopBean, String ruleOneTypeName)
    {
        
        //System.out.println("begin:dealShopByRule======================");
        long starttime = System.currentTimeMillis();
        
        boolean matchFlag = false;
        
        if (!notNullAndNot0(gdShopBean.getName()))
        {
            return false;
        }
        
        //去括号
        String shopNameExcludeBracket = getStringExcludeBracket(gdShopBean.getName());
        
        for (RuleBean ruleBean : ruleList)
        {
            //System.out.println("begin:for rules======================");
            
            String keyWord = ruleBean.getKeyword();
            boolean keywordIsNotNull = (null != keyWord && !keyWord.trim()
                    .equals(""));
            boolean matchBrandName = "1".equals(ruleBean.getMybrand());
            boolean matchType = shopTypeMatchRuleGDType(gdShopBean, ruleBean);
            
            //如果当前的这个公式的高德业态和商家的高德业态不匹配，说明此商家不匹配此公式，
            //切记这块，不要把这个判断遗漏了 否则运算偏差很大
            if (!matchType)
            {
                continue;
            }
            
            //按照城格品牌匹配 同时商家名称去括号后，剩余部分不为空
            if (matchBrandName && notNullAndNot0(shopNameExcludeBracket))
            {
                //先按照品牌中文名长度降序读取品牌列表
                List<BrandBean> brandList = getBrandList(ruleBean);
                
                //如果列表为空，当前公式结束（按照英文名长度降序读取品牌列表也必然为空， 因为都是按照业态读取的，只是顺序不同）
                if (null == brandList || brandList.isEmpty())
                {
                    continue;
                }
                
                //System.out.println("begin:for match brand======================");
                
                //匹配品牌名称
                for (BrandBean brand : brandList)
                {
                    //性能调优
                    //如果品牌长度大于商家长度，则跳到下一条
                    //if(brand.getBrandName().getBytes().length >shopNameExcludeBracket.getBytes().length)
                    //{
                    //    continue;
                    //}
                    
                    if (notNullAndNot0(brand.getBrandName()) 
                            && shopMinMatchBrand(getStringExcludeBracket(brand.getBrandName()), shopNameExcludeBracket))
                    {
                        //生成待生成脚本的数据到文件
                        syncBrandInfoToShop(gdShopBean, brand);
                        gdShopBean.setRuleId(ruleBean.getId());
                        //shopMapBrand(gdShopBean);
                        genPrepareScript(gdShopBean);
                        matchFlag = true;
                        brandShopCount++;
                        return matchFlag;
                    }
                    
                    
                }
                
                //再按照品牌英文名长度降序读取品牌列表
                List<BrandBean> brandListOrderByEngName = getBrandListOrderByEngName(ruleBean);
                
                //如果列表为空，当前公式结束（按照英文名长度降序读取品牌列表也必然为空， 因为都是按照业态读取的，只是顺序不同）
                if (null == brandListOrderByEngName
                        || brandListOrderByEngName.isEmpty())
                {
                    continue;
                }
                
                //匹配品牌英名称
                for (BrandBean brand : brandListOrderByEngName)
                {
                    
                   //性能调优
                    //如果品牌长度大于商家长度，则跳到下一条
                    //if(brand.getBrandEngName().getBytes().length >shopNameExcludeBracket.getBytes().length)
                    //{
                    //    continue;
                    //}
                    
                    if (notNullAndNot0(brand.getBrandEngName())
                            && shopMinMatchBrand(getStringExcludeBracket(brand.getBrandEngName()), shopNameExcludeBracket))
                    {
                        //生成待生成脚本的数据到文件
                        syncBrandInfoToShop(gdShopBean, brand);
                        gdShopBean.setRuleId(ruleBean.getId());
                        //shopMapBrand(gdShopBean);
                        genPrepareScript(gdShopBean);
                        matchFlag = true;
                        brandShopCount++;
                        return matchFlag;
                    }
                    
                }
                
            }
            
            //按照自定义关键字过滤
            if (!matchBrandName)
            {
                //System.out.println("begin:for match keyword======================");
                //按照自定义关键字匹配 同时商家名称去括号后，剩余部分不为空
                //注意通过关键字匹配，需要商家的业态类别和公式中高德的业态类别匹配才行
                //否则单看关键字，会导致商家满足了关键字，但却不满足业态，但是仍然被翻译过去了。
                if (keywordIsNotNull
                        && notNullAndNot0(shopNameExcludeBracket)
                        && matchKeyWord(keyWord,
                                shopNameExcludeBracket,
                                ruleBean.getId()))
                {
                    //生成待生成脚本的数据到文件
                    syncRuleInfoToGDShop(gdShopBean, ruleBean);
                    
                    //商家和品牌关联
                    shopMapBrand(gdShopBean);
                    
                    genPrepareScript(gdShopBean);
                    
                    matchFlag = true;
                    return matchFlag;
                }
                //不用关键字 直接把高德某业态下数据直接挂到城格某业态下
                else if (!keywordIsNotNull)
                {
                    //生成待生成脚本的数据到文件
                    syncRuleInfoToGDShop(gdShopBean, ruleBean);
                    
                    //商家和品牌关联
                    shopMapBrand(gdShopBean);
                    
                    genPrepareScript(gdShopBean);
                    
                    matchFlag = true;
                    return matchFlag;
                }
            }
        }
        
        return matchFlag;
        
    }
    
    /**
     * 读取要匹配的品牌名称列表
     * <功能详细描述>
     * @param ruleBean
     * @return [参数说明]
     * 
     * @return BrandBean[] [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public List<BrandBean> getBrandList(RuleBean ruleBean)
    {
        
        String brandType = "";
        int flag = 0;
        
        //准备品牌的类型key
        if (notNullAndNot0(ruleBean.getCgtype3()))
        {
            brandType = ruleBean.getCgtype1() + "_" + ruleBean.getCgtype2()
                    + "_" + ruleBean.getCgtype3();
            flag = 3;
        }
        
        else if (notNullAndNot0(ruleBean.getCgtype2()))
        {
            brandType = ruleBean.getCgtype1() + "_" + ruleBean.getCgtype2();
            flag = 2;
        }
        
        else if (notNullAndNot0(ruleBean.getCgtype1()))
        {
            brandType = ruleBean.getCgtype1();
            flag = 1;
        }
        
        
        int multi = 0;
        String multiTypeKey = "";
        
        //判断是否为多二级业态或多三级业态
        if (notNullAndNot0(ruleBean.getCgtype3name())
                && ruleBean.getCgtype3name().contains("|"))
        {
            multi = 1;
            multiTypeKey = ruleBean.getCgtype1name() + "_"
                    + ruleBean.getCgtype2name() + "_"
                    + ruleBean.getCgtype3name();
        }
        
        else if (notNullAndNot0(ruleBean.getCgtype2name())
                && ruleBean.getCgtype2name().contains("|"))
        {
            multi = 1;
            multiTypeKey = ruleBean.getCgtype1name() + "_"
                    + ruleBean.getCgtype2name();
        }
        
        List<BrandBean> brandList = null;
        
        if (flag == 3)
        {
            brandList = brandHash.getBrandListBy123Type(brandType);
        }
        else if (flag == 2)
        {
            brandList = brandHash.getBrandListBy12Type(brandType);
        }
        else if (flag == 1)
        {
            brandList = brandHash.getBrandListBy1Type(brandType);
        }
        
        if(multi == 1)
        {
            brandList = brandHash.getBradnListByMultiTypekeyForTrans(multiTypeKey+ "_" + BrandHash.BRAND_NAME_CHINA);
        }
        
        return brandList;
        
    }
    
    /**
     * 读取要匹配的品牌名称列表
     * <功能详细描述>
     * @param ruleBean
     * @return [参数说明]
     * 
     * @return BrandBean[] [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public List<BrandBean> getBrandListOrderByEngName(RuleBean ruleBean)
    {
        
        String brandType = "";
        int flag = 0;
        
        //准备品牌的类型key
        if (notNullAndNot0(ruleBean.getCgtype3()))
        {
            brandType = ruleBean.getCgtype1() + "_" + ruleBean.getCgtype2()
                    + "_" + ruleBean.getCgtype3();
            flag = 3;
        }
        
        else if (notNullAndNot0(ruleBean.getCgtype2()))
        {
            brandType = ruleBean.getCgtype1() + "_" + ruleBean.getCgtype2();
            flag = 2;
        }
        
        else if (notNullAndNot0(ruleBean.getCgtype1()))
        {
            brandType = ruleBean.getCgtype1();
            flag = 1;
        }
        
        List<BrandBean> brandList = null;
        
        if (flag == 3)
        {
            brandList = brandHash.getBrandListOrderByEngNameBy123Type(brandType);
        }
        else if (flag == 2)
        {
            brandList = brandHash.getBrandListOrderByEngNameBy12Type(brandType);
        }
        else if (flag == 1)
        {
            brandList = brandHash.getBrandListOrderByEngNameBy1Type(brandType);
        }
        
        int multi = 0;
        String multiTypeKey = "";
        
        //判断是否为多二级业态或多三级业态
        if (notNullAndNot0(ruleBean.getCgtype3name())
                && ruleBean.getCgtype3name().contains("|"))
        {
            multi = 1;
            multiTypeKey = ruleBean.getCgtype1name() + "_"
                    + ruleBean.getCgtype2name() + "_"
                    + ruleBean.getCgtype3name();
        }
        
        else if (notNullAndNot0(ruleBean.getCgtype2name())
                && ruleBean.getCgtype2name().contains("|"))
        {
            multi = 1;
            multiTypeKey = ruleBean.getCgtype1name() + "_"
                    + ruleBean.getCgtype2name();
        }
        
        if(multi == 1)
        {
            brandList = brandHash.getBradnListByMultiTypekeyForTrans(multiTypeKey+ "_" + BrandHash.BRAND_NAME_ENG);
        }
        
        return brandList;
        
    }
    
    /**
     * 判断商家是否满足公式中的类别
     * <功能详细描述>
     * @param gdShopBean
     * @param ruleBean
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private boolean shopTypeMatchRuleGDType(GDShopBean gdShopBean,
            RuleBean ruleBean)
    {
        boolean matchType = false;
        
        if (notNullAndNot0(ruleBean.getGdtype3name()))
        {
            matchType = (gdShopBean.getType1()
                    .equals(ruleBean.getGdtype1name())
                    && gdShopBean.getType2().equals(ruleBean.getGdtype2name()) && gdShopBean.getType3()
                    .equals(ruleBean.getGdtype3name()));
        }
        else if (notNullAndNot0(ruleBean.getGdtype2name()))
        {
            matchType = (gdShopBean.getType1()
                    .equals(ruleBean.getGdtype1name()) && gdShopBean.getType2()
                    .equals(ruleBean.getGdtype2name()));
        }
        else if (notNullAndNot0(ruleBean.getGdtype1name()))
        {
            matchType = (gdShopBean.getType1().equals(ruleBean.getGdtype1name()));
        }
        
        return matchType;
        
    }
    
    private void syncRuleInfoToGDShop(GDShopBean gdShopBean, RuleBean ruleBean)
    {
        gdShopBean.setChenggeType1(ruleBean.getCgtype1());
        gdShopBean.setChenggeType2(ruleBean.getCgtype2());
        gdShopBean.setChenggeType3(ruleBean.getCgtype3());
        gdShopBean.setChenggeType1Name(ruleBean.getCgtype1name());
        gdShopBean.setChenggeType2Name(ruleBean.getCgtype2name());
        gdShopBean.setChenggeType3Name(ruleBean.getCgtype3name());
        gdShopBean.setRuleId(ruleBean.getId());
    }
    
    private void syncBrandInfoToShop(GDShopBean gdShopBean,
            BrandBean brandBean)
    {
        gdShopBean.setBrand_id(brandBean.getBrandId());
        gdShopBean.setBrandName(brandBean.getBrandName());
        gdShopBean.setBrandEngName(brandBean.getBrandEngName());
        gdShopBean.setChenggeType1(brandBean.getBrandOneTypeId());
        gdShopBean.setChenggeType2(brandBean.getBrandTwoTypeId());
        gdShopBean.setChenggeType3(brandBean.getBrandThreeTypeId());
        gdShopBean.setChenggeType1Name(brandBean.getBrandOneTypeName());
        gdShopBean.setChenggeType2Name(brandBean.getBrandTwoTypeName());
        gdShopBean.setChenggeType3Name(brandBean.getBrandThreeTypeName());
        
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
     * 去除字符串中的空格、回车、换行符、制表符
     * <功能详细描述>
     * @param str
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static String replaceBlank(String str)
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
    
    public boolean isNull(String key)
    {
        if (null == key || key.trim().equals(""))
        {
            return true;
        }
        
        return false;
    }
    
    public boolean notNullAndNot0(String key)
    {
        if (null == key || key.trim().equals("") || key.trim().equals("0"))
        {
            return false;
        }
        
        return true;
    }
    
    /**
     * 关键字如果匹配商家
     * @param keyWord
     * @param shopName
     * @param ruleId
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public boolean matchKeyWord(String keyWord, String shopName, String ruleId)
    {
        
        //非特殊情况 ，走正常的验证流程 ，商家含有关键字即可导入
        //性能优化 splitOpt
        //String[] keys = keyWord.split("\\|");
        String[] keys = splitOpt(keyWord,"|");
        
        for (String key : keys)
        {
            //避免公式中出现空格key
            if (!notNullAndNot0(key))
            {
                continue;
            }
            
            //如果是公式812 同时商家含有酒店，并且酒店以"中餐厅"结尾，则商家符合此公式，导入
            if (RULE_JIUDIAN_ID.equals(ruleId) && shopName.contains("酒店")
                    && shopName.endsWith("中餐厅") && key.equals("酒店"))
            {
                return true;
            }
            //如果是公式812 同时商家含有酒店，不满足酒店以"中餐厅"结尾，则商家不符合此公式，不导入
            else if (RULE_JIUDIAN_ID.equals(ruleId) && shopName.contains("酒店")
                    && !shopName.endsWith("中餐厅") && key.contains("酒店"))
            {
                continue;
            }
            
            if (shopName.toLowerCase().contains(key.toLowerCase()))
            {
                return true;
            }
        }
        
        return false;
    }
    
    public GDShopBean createShopBean(String shopInfo)
    {
        //性能优化
        //String[] shopinfos = shopInfo.split(",");
        String[] shopinfos = splitOpt(shopInfo,","); 
        
        GDShopBean shopBean = new GDShopBean(shopinfos[0], shopinfos[1],
                shopinfos[2], shopinfos[3], shopinfos[4], shopinfos[5],
                shopinfos[6], shopinfos[7], shopinfos[8]);
        return shopBean;
    }
    
    /**
     * 生成脚本
     * <功能详细描述> [参数说明]
     * 生成脚本需要的字段: 品牌id(默认为0)->brand_id 公式id->mid 
     * branch_name address phone fax contact point state city citycode areacode
     * 
     *  //cg_mid_poi_shop
        //id brand_id agent_id poi_id base_id mid branch_name address phone fax contact point state city citycode areacode project floor floor_img add_user add_time update_time mark
        
        
        //cg_mid_poi_type
        //id brand_id type mid level add_time mark
        
        
        //cg_mid_poi_style_relation
        //id style_id brand_id mid add_time mark
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public void genPrepareScript(GDShopBean bean)
    {
        
        try
        {
            
            //String id = "";
            String type1 = bean.getChenggeType1();
            String type2 = bean.getChenggeType2();
            String type3 = notNullAndNot0(bean.getChenggeType3()) ? bean.getChenggeType3()
                    : "";
            String name = bean.getName();
            String ruleId = bean.getRuleId();
            String brandId = bean.getBrand_id();
            String brandName = bean.getBrandName();
            String address = bean.getAddress();
            String phone = bean.getTel();
            String fax = bean.getFax();
            String contact = "";
            String state = "0";
            String city = cityCodeHash.getCityIdByCityCode(bean.getAreacode());
            String point = bean.getX() + "<br>" + bean.getY();
            
            //这个是电信的区号 ，暂时不用了
            String citycode = bean.getCitycode();
            
            String areacode = bean.getAreacode();
            String addUser = "0";
            String time = String.valueOf(System.currentTimeMillis() / 1000);
            String updatetime = String.valueOf(System.currentTimeMillis() / 1000);
            
            StringBuilder sb = new StringBuilder();
            sb.append(name)
                    .append(",")
                    .append(type1)
                    .append(",")
                    .append(type2)
                    .append(",")
                    .append(type3)
                    .append(",")
                    .append(ruleId)
                    .append(",")
                    .append(brandId)
                    .append(",")
                    .append(brandName)
                    .append(",")
                    .append(address)
                    .append(",")
                    .append(phone)
                    .append(",")
                    .append(fax)
                    .append(",")
                    .append(contact)
                    .append(",")
                    .append(state)
                    .append(",")
                    .append(point)
                    .append(",")
                    .append(city)
                    .append(",")
                    .append(CITY_CODE)
                    .append(",")
                    .append(areacode)
                    .append(",")
                    .append(addUser)
                    .append(",")
                    .append(time)
                    .append(",")
                    .append(updatetime)
                    .append(",")
                    .append(bean.getTypes())
                    .append(",")
                    .append(bean.getX())
                    .append(",")
                    .append(bean.getY())
                    .append("\n");
            
            writeHandle.write(sb.toString());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        
        //System.out.println(bean.toString());
        
    }
    
    public void genShopScript(int flag,String filenamefirst)
    {
        
//        GD_SHOP_TRANS = (flag == SUB_TABLE_FLAG) ? DEST_PATH + "CG_SHOP_"
//                + PROVINCE_FLAG + "_(" + CITY_FLAG + ").csv" : DEST_PATH
//                + "CG_MID_POI_SHOP_" + PROVINCE_FLAG + "_(" + CITY_FLAG
//                + ").csv";
        
        
        System.out.println("开始生产商家数据库脚本============");
        
        File file = new File(GD_SHOP_TRANS);
        DataInputStream in = null;
        String lineTxt = null;
        
        String fileShop = filenamefirst+file.getName();
        String fileForShopType2 = filenamefirst+file.getName();
        String fileForShopType3 = filenamefirst+file.getName();
        
        fileShop = fileShop.replace("csv", "sql");
        fileForShopType2 = fileForShopType2.replace(".csv",".sql");
        fileForShopType2 = fileForShopType2.replace("SHOP_","SHOP_TYPE2_RELATION_");
        
        fileForShopType3 = fileForShopType3.replace(".csv",".sql");
        fileForShopType3 = fileForShopType3.replace("SHOP_","SHOP_TYPE3_RELATION_");
        
        String sqlShopHead = replace(SQL_INSERT_SHOP_HEAD,"{table}",CG_SHOP_TABLE_NAME);
        String sqlShopType2Head = replace(SQL_INSERT_SHOP_TYPE2_HEAD,"{table}",CG_SHOP_TYPE2_TABLE_NAME);
        String sqlShopType3Head = replace(SQL_INSERT_SHOP_TYPE3_HEAD,"{table}",CG_SHOP_TYPE3_TABLE_NAME);
        
        String sqlShopValuesT = SQL_INSERT_SHOP_VALUES;
        String sqlShopType2ValuesT = SQL_INSERT_SHOP_TYPE2_VALUES;
        String sqlShopType3ValuesT = SQL_INSERT_SHOP_TYPE3_VALUES;
        
        //分表
        if (flag == SUB_TABLE_FLAG)
        {
            sqlShopHead = replace(SQL_INSERT_SUB_TABLE_SHOP_HEAD,"{table}",CG_SHOP_SUB_TABLE_NAME);
            sqlShopType2Head = replace(SQL_INSERT_SUB_TABLE_SHOP_TYPE2_HEAD,"{table}",CG_SHOP_TYPE2_SUB_TABLE_NAME);
            sqlShopType3Head = replace(SQL_INSERT_SUB_TABLE_SHOP_TYPE3_HEAD,"{table}",CG_SHOP_TYPE3_SUB_TABLE_NAME);
            
            sqlShopValuesT = SQL_INSERT_SUB_TABLE_SHOP_VALUES;
            sqlShopType2ValuesT = SQL_INSERT_SUB_TABLE_SHOP_TYPE2_VALUES;
            sqlShopType3ValuesT = SQL_INSERT_SUB_TABLE_SHOP_TYPE3_VALUES;
        }
        
        BufferedReader read = null;
        BufferedWriter writeShopHandle = null;
        BufferedWriter writeShopType2Handle = null;
        BufferedWriter writeShopType3Handle = null;
        
        int id = g_id;
        int pageCount = 0;
        int pageCountForType3 = 0;
        int newPage = 1;
        int oldPage = 0;
        int newPageType3 = 1;
        int oldPageType3 = 0;
        
        try
        {
            in = new DataInputStream(new FileInputStream(file));
            read = new BufferedReader(new InputStreamReader(in, "utf8"));
            
            writeShopHandle = new BufferedWriter(new FileWriter(DEST_PATH + "\\" + fileShop, false));
            writeShopType2Handle = new BufferedWriter(new FileWriter(DEST_PATH + "\\" + fileForShopType2, false));
            writeShopType3Handle = new BufferedWriter(new FileWriter(DEST_PATH + "\\" + fileForShopType3, false));
            
            while ((lineTxt = read.readLine()) != null)
            {
                id++;
                
                lineTxt = lineTxt.replace("\\", "\\\\");
                lineTxt = lineTxt.replace("'", "''");
                
                String[] values = lineTxt.split(",");
                String name = values[0];
                String type1 = values[1];
                String type2 = values[2];
                String type3 = values[3];
                String ruleId = values[4];
                String brandId = values[5];
                String brandName = values[6];
                String address = values[7];
                String phone = values[8];
                String fax = values[9];
                String contact = values[10];
                String state = values[11];
                String point = values[12];
                //这里存储的是地区码
                String city = values[13];
                String citycode = values[14];
                String areacode = values[15];
                String addUser = values[16];
                String time = values[17];
                String updatetime = values[18];
                String gdTypes = values[19];
                String x_lon = values[20];
                String y_lat = values[21];
                
                
                //商家表
                //" ({id},{brand_id},{mid},{branch_name},{address},{phone},{fax},' ',{point},{city},{citycode},{areacode},{add_user},{add_time},{update_time},{types})";
                String sqlShopValues = sqlShopValuesT.replace("{id}",String.valueOf(id));
                sqlShopValues = replace(sqlShopValues,"{brand_id}",brandId);
                sqlShopValues = replace(sqlShopValues, "{mid}", ruleId);
                sqlShopValues = replace(sqlShopValues,"{branch_name}",name);
                String pinyin = getFirstTools.String2Alpha(name.substring(0, 1));
                //System.out.println(name + ";" + pinyin);
                sqlShopValues = replace(sqlShopValues,"{pinyin}",pinyin);
                
                sqlShopValues = replace(sqlShopValues,"{address}",address);
                sqlShopValues = replace(sqlShopValues, "{phone}", phone);
                sqlShopValues = replace(sqlShopValues, "{fax}", fax);
                sqlShopValues = replace(sqlShopValues, "{point}", point);
                sqlShopValues = replace(sqlShopValues,"{baidu_lon}",x_lon);
                sqlShopValues = replace(sqlShopValues,"{baidu_lat}",y_lat);
                sqlShopValues = replace(sqlShopValues,"{citycode}",citycode);
                sqlShopValues = replace(sqlShopValues, "{areacode}",city);
                sqlShopValues = replace(sqlShopValues,"{add_user}",addUser);
                sqlShopValues = replace(sqlShopValues,"{add_time}",time);
                sqlShopValues = replace(sqlShopValues,"{update_time}",updatetime);
                
                //非分表 替换
                sqlShopValues = replace(sqlShopValues,"{types}",gdTypes);
                sqlShopValues = replace(sqlShopValues,"{city}",city);
                
                //关联表  
                //"({brand_id},{type},{mid},{level},{add_time})";
                String sqlShopType2Values = "";
                
                //关联表 非分表
                if(flag == NOT_SUB_TABLE_FLAG)
                {
                    sqlShopType2Values = sqlShopType2ValuesT.replace("{brand_id}",String.valueOf(id));
                    sqlShopType2Values = replace(sqlShopType2Values,"{type}",type2);
                    sqlShopType2Values = replace(sqlShopType2Values,"{mid}",ruleId);
                    sqlShopType2Values = replace(sqlShopType2Values,"{level}","2");
                    sqlShopType2Values = replace(sqlShopType2Values,"{add_time}",time);
                }
                else
                {
                    //关联表  分表
                    sqlShopType2Values = sqlShopType2ValuesT.replace("{shop_id}",String.valueOf(id));
                    sqlShopType2Values = replace(sqlShopType2Values,"{type_id}",type2);
                    sqlShopType2Values = replace(sqlShopType2Values,"{mid}",ruleId);
                    sqlShopType2Values = replace(sqlShopType2Values,"{level}","2");
                    sqlShopType2Values = replace(sqlShopType2Values,"{add_time}",time);
                    sqlShopType2Values = replace(sqlShopType2Values,"{citycode}",citycode);
                    sqlShopType2Values = replace(sqlShopType2Values,"{areacode}",city);
                }
                
                
                
                //商家和三级业态关联表
                //"({style_id},{brand_id},{mid},{add_time})";
                if (notNullAndNot0(type3))
                {

                    String sqlShopType3Values = "";
                    
                    //非分表
                    if(flag == NOT_SUB_TABLE_FLAG)
                    {
                        sqlShopType3Values = sqlShopType3ValuesT.replace("{brand_id}",String.valueOf(id));
                        sqlShopType3Values = replace(sqlShopType3Values,"{style_id}", type3);
                        sqlShopType3Values = replace(sqlShopType3Values,"{mid}", ruleId);
                        sqlShopType3Values = replace(sqlShopType3Values,"{add_time}",time);
                    }
                    else
                    {
                        //分表的
                        sqlShopType3Values = sqlShopType3ValuesT.replace("{shop_id}",String.valueOf(id));
                        sqlShopType3Values = replace(sqlShopType3Values,"{type_id}",type3);
                        sqlShopType3Values = replace(sqlShopType3Values,"{mid}",ruleId);
                        sqlShopType3Values = replace(sqlShopType3Values,"{add_time}",time);
                        sqlShopType3Values = replace(sqlShopType3Values,"{citycode}",citycode);
                        sqlShopType3Values = replace(sqlShopType3Values,"{areacode}",city);
                    }
                     
                    
                    //新开启一页 先写表头
                    if (oldPageType3 < newPageType3)
                    {
                        writeShopType3Handle.write(sqlShopType3Head);
                        oldPageType3 = newPageType3;
                    }
                    else
                    {
                        writeShopType3Handle.write(",\n");
                    }
                    
                    writeShopType3Handle.write(sqlShopType3Values);
                    pageCountForType3++;
                }
                
                //新开启一页 先写表头
                if (oldPage < newPage)
                {
                    writeShopHandle.write(sqlShopHead);
                    writeShopType2Handle.write(sqlShopType2Head);
                    oldPage = newPage;
                }
                else
                {
                    writeShopHandle.write(",\n");
                    writeShopType2Handle.write(",\n");
                }
                
                //商家库表写脚本
                writeShopHandle.write(sqlShopValues);
                writeShopType2Handle.write(sqlShopType2Values);
                pageCount++;
                
                //用于分页隔断 商家和二级业态
                if (pageCount % 2000 == 0 && pageCount > 0)
                {
                    writeShopHandle.write(";\n");
                    writeShopType2Handle.write(";\n");
                    newPage++;
                    pageCount = 0;
                    
                }
                
                //用于分页隔断 商家和三级业态
                if (pageCountForType3 % 2000 == 0 && pageCountForType3 > 0)
                {
                    writeShopType3Handle.write(";\n");
                    newPageType3++;
                    pageCountForType3 = 0;
                }
            }
            
            if (pageCount > 0)
            {
                writeShopHandle.write(";");
                writeShopType2Handle.write(";");
            }
            
            if (pageCountForType3 > 0)
            {
                writeShopType3Handle.write(";");
            }
            
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            closeStream(read);
            closeStream(writeShopHandle);
            closeStream(writeShopType2Handle);
            closeStream(writeShopType3Handle);
        }
        
        System.out.println("生产商家数据库脚本完毕======================");
    }
    
    /**
     * 替换
     * @param src
     * @param find
     * @param dest
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public String replace(String src, String find, String dest)
    {
        return src.replace(find, dest);
    }
    
    /**
     * 关闭流
     * @param obj [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public void closeStream(Object obj)
    {
        if (null == obj)
        {
            return;
        }
        
        if (obj instanceof BufferedReader)
        {
            try
            {
                ((BufferedReader) obj).close();
                
            }
            catch (Exception e)
            {
                
            }
        }
        
        if (obj instanceof HttpURLConnection)
        {
            try
            {
                ((HttpURLConnection) obj).disconnect();
                
            }
            catch (Exception e)
            {
                
            }
        }
        
        if (obj instanceof BufferedWriter)
        {
            try
            {
                ((BufferedWriter) obj).close();
                
            }
            catch (Exception e)
            {
                
            }
        }
        
        //
    }
    
    //================================================商家和品牌关联=================
    private void shopMapBrand(GDShopBean gdShopBean)
    {
        
        //long starttime = System.currentTimeMillis();
        
        //shopMapBrandRuleHash
        String key = "";
        
        if (notNullAndNot0(gdShopBean.getChenggeType3Name()))
        {
            key = gdShopBean.getChenggeType1Name() + "_"
                    + gdShopBean.getChenggeType2Name() + "_"
                    + gdShopBean.getChenggeType3Name();
        }
        else if (notNullAndNot0(gdShopBean.getChenggeType2Name()))
        {
            key = gdShopBean.getChenggeType1Name() + "_"
                    + gdShopBean.getChenggeType2Name();
        }
        else if (notNullAndNot0(gdShopBean.getChenggeType1Name()))
        {
            key = gdShopBean.getChenggeType1Name();
        }
        
        List<ShopMapBrandRuleBean> ruleList = shopMapBrandRuleHash.getBrandListByKey(key);
        
        if (null == ruleList || ruleList.isEmpty())
        {
            return;
        }
        
        String shopName = getStringExcludeBracket(gdShopBean.getName());
        
        //循环公式
        for (ShopMapBrandRuleBean shopMapBrandRuleBean : ruleList)
        {
            
            int multi = 0;
            String multiTypeKey = "";
            
            //判断是否为多二级业态或多三级业态
            if (notNullAndNot0(shopMapBrandRuleBean.getCgBrand3Name())
                    && shopMapBrandRuleBean.getCgBrand3Name().contains("|"))
            {
                multi = 1;
                multiTypeKey = shopMapBrandRuleBean.getCgBrand1Name() + "_"
                        + shopMapBrandRuleBean.getCgBrand2Name() + "_"
                        + shopMapBrandRuleBean.getCgBrand3Name();
            }
            
            else if (notNullAndNot0(shopMapBrandRuleBean.getCgBrand2Name())
                    && shopMapBrandRuleBean.getCgBrand2Name().contains("|"))
            {
                multi = 1;
                multiTypeKey = shopMapBrandRuleBean.getCgBrand1Name() + "_"
                        + shopMapBrandRuleBean.getCgBrand2Name();
            }
            
            List<BrandBean> brandList = null;
            
            //品牌中文列表
            if (multi == 1)
            {
                brandList = brandHash.getBradnListByMultiTypekeyForShopMapBrand(multiTypeKey
                        + "_" + BrandHash.BRAND_NAME_CHINA);
            }
            else
            {
                brandList = brandHash.getSortBrandList(shopMapBrandRuleBean.getCgBrand1Name(),
                        shopMapBrandRuleBean.getCgBrand2Name(),
                        shopMapBrandRuleBean.getCgBrand3Name(),
                        BrandHash.BRAND_NAME_CHINA);
            }
            
            if (null != brandList && !brandList.isEmpty())
            {
                //System.out.println(shopMapBrandRuleBean.getCgBrand1Name()+"->" + shopMapBrandRuleBean.getCgBrand2Name()+"->" +shopMapBrandRuleBean.getCgBrand3Name()+";china:"+brandList.size());
                
                for (BrandBean brandBean : brandList)
                {
                    //if (shopMaxMatchBrand(getStringExcludeBracket(brandBean.getBrandName()),shopName,brandBean.getBrandWords(),brandBean.getBrandChars()))
                    if (shopMaxMatchBrand(getStringExcludeBracket(brandBean.getBrandName()),shopName,brandBean.getBrandWords(),brandBean.getBrandChars()))
                    {
                        syncBrandInfoToShop(gdShopBean,brandBean);
                        brandShopCount++;
                        return;
                    }
                }
            }
            
            //品牌英文列表
            if (multi == 1)
            {
                brandList = brandHash.getBradnListByMultiTypekeyForShopMapBrand(multiTypeKey
                        + "_" + BrandHash.BRAND_NAME_ENG);
            }
            else
            {
                brandList = brandHash.getSortBrandList(shopMapBrandRuleBean.getCgBrand1Name(),
                        shopMapBrandRuleBean.getCgBrand2Name(),
                        shopMapBrandRuleBean.getCgBrand3Name(),
                        BrandHash.BRAND_NAME_ENG);
            }
            
            if (null != brandList && !brandList.isEmpty())
            {
                //System.out.println(shopMapBrandRuleBean.getCgBrand1Name()+"->" + shopMapBrandRuleBean.getCgBrand2Name()+"->" +shopMapBrandRuleBean.getCgBrand3Name()+";english:"+brandList.size());
                
                for (BrandBean brandBean : brandList)
                {
                    
                    if (shopMinMatchBrand(getStringExcludeBracket(brandBean.getBrandEngName()),shopName))
                    {
                        syncBrandInfoToShop(gdShopBean,brandBean);
                        brandShopCount++;
                        return;
                    }
                }
            }
            
            //品牌别名列表
            if (multi == 1)
            {
                brandList = brandHash.getBradnListByMultiTypekeyForShopMapBrand(multiTypeKey
                        + "_" + BrandHash.BRAND_NAME_OTHER);
            }
            else
            {
                brandList = brandHash.getSortBrandList(shopMapBrandRuleBean.getCgBrand1Name(),
                        shopMapBrandRuleBean.getCgBrand2Name(),
                        shopMapBrandRuleBean.getCgBrand3Name(),
                        BrandHash.BRAND_NAME_OTHER);
            }
            
            if (null != brandList && !brandList.isEmpty())
            {
                
                //System.out.println(shopMapBrandRuleBean.getCgBrand1Name()+"->" + shopMapBrandRuleBean.getCgBrand2Name()+"->" +shopMapBrandRuleBean.getCgBrand3Name()+";other:"+brandList.size());
                
                for (BrandBean brandBean : brandList)
                {
                    
                    if (shopMinMatchBrand(getStringExcludeBracket(brandBean.getBrandOtherName()),shopName))
                    {
                        syncBrandInfoToShop(gdShopBean,brandBean);
                        brandShopCount++;
                        return;
                    }
                }
            }
        }
        
        long endtime = System.currentTimeMillis();
        //System.out.println("shop:" + gdShopBean.getName() + " time"
        //        + (endtime - starttime) / 1000);
        
    }
    
    
    /**
     * 比较品牌名称（品牌中文名，品牌英文名，品牌别名）和商家名称的相似性
     * 商家最宽松匹配品牌名称 （最宽松匹配算法包含了最严格匹配算法  先严格 后宽松）
     * @param brandName
     * @param shopName
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private boolean shopMaxMatchBrand(String brandName,String shopName,String brandWords,String brandChars)
    {
        
        if(null == brandName || null == shopName||"".equals(brandName.trim())|| "".equals(shopName.trim()))
        {
            return false;
        }
        
        //去空格
        brandName = brandName.toLowerCase().trim();
        shopName = shopName.toLowerCase().trim();
        //性能调优 replaceAll 替换为replace
        brandName = brandName.replace(" ", "");
        shopName = shopName.replace(" ", "");
        
        //性能调优
        if(brandName.length() >shopName.length() )
        {
            return false;
        }
        
        boolean ischina = isChina(brandName);
        boolean isEqual = shopName.equals(brandName);
        boolean isContains = shopName.contains(brandName);
        int  brandLen = brandName.length();
        
        //优先整体匹配====================================
        if(isEqual)
        {
            return true;
        }
        
        //含中文, 不相等，包含关系, 长度大于等于2 则表示匹配
        if(isContains && ischina && brandLen >= 2)
        {
            return true;
        }
        
        //全半角字符 不相等，包含关系, 长度大于等于3 则表示匹配
        if(isContains && !ischina && brandLen >= 3)
        {
            return true;
        }
        
        //分词匹配========================================= 
        //分词匹配 商家含有品牌的所有分词，则匹配 否则不匹配
        //纯中文的品牌名四个字（含）才做分词计算。除此以外的中英文混杂，字符，字母等都不做分词计算
        //按顺序匹配 模拟数据库的like 采用正则表达式
        if(brandName.length() < 6 )
        {
            return false;
        }
        
        if(notNullAndNot0(brandWords))
        {
            String like = brandWords.replace(",", "(.*)");
            like = "(.*)"+like+"(.*)";
            Pattern p = Pattern.compile(like); //（.*）表示%
            Matcher m = p.matcher(shopName);
            
            if(m.find()) 
            {
                return true;
            }
        }
    
        //字符匹配
        //字符分词匹配 商家含有品牌的所有字符分词，则匹配 否则不匹配
        //纯中文的品牌名四个字（含）才做字符分词计算。除此以外的中英文混杂，字符，字母等都不做字符分词计算
        //按顺序匹配 模拟数据库的like 采用正则表达式
        if(notNullAndNot0(brandChars))
        {
            String like = brandChars.replace(",", "(.*)");
            like = "(.*)"+like+"(.*)";
            Pattern p = Pattern.compile(like); //（.*）表示%
            Matcher m = p.matcher(shopName);
            
            if(m.find()) 
            {
                return true;
            }
        }
        
        return false;
       
    }
    
    
    /**
     * 比较品牌名称（品牌中文名，品牌英文名，品牌别名）和商家名称的相似性
     * 最严格匹配 商家必须整体上包含品牌名称
     * @param brandName
     * @param shopName
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private boolean shopMinMatchBrand(String brandName,String shopName)
    {
        
        if(null == brandName || null == shopName||"".equals(brandName.trim())|| "".equals(shopName.trim()))
        {
            return false;
        }
        
        //去空格
        brandName = brandName.toLowerCase().trim();
        shopName = shopName.toLowerCase().trim();
        //性能调优 replaceAll 替换为replace
        brandName = brandName.replace(" ", "");
        shopName = shopName.replace(" ", "");
        
        //性能调优
        if(brandName.length() >shopName.length() )
        {
            return false;
        }
        
        boolean ischina = isChina(brandName);
        boolean isEqual = shopName.equals(brandName);
        boolean isContains = shopName.contains(brandName);
        int  brandLen = brandName.length();
        
        //优先整体匹配====================================
        if(isEqual)
        {
            return true;
        }
        
        //含中文, 不相等，包含关系, 长度大于等于2 则表示匹配
        if(isContains && ischina && brandLen >= 2)
        {
            return true;
        }
        
        //全半角字符 不相等，包含关系, 长度大于等于3 则表示匹配
        if(isContains && !ischina && brandLen >= 3)
        {
            return true;
        }
        
        return false;
       
    }
    
    private boolean isChina(String str)
    {
        return str.getBytes().length != str.length();
    }
    
    public static String zuobiaoConvertGDToBaidu(String gd_lon,String gd_lat)
    {
        double xlon = Double.valueOf(gd_lon);
        double ylat = Double.valueOf(gd_lat);
        
        double z = Math.sqrt(xlon * xlon + ylat * ylat) + 0.00002 * Math.sin(ylat * x_pi);
        double theta = Math.atan2(ylat, xlon) + 0.000003 * Math.cos(xlon * x_pi);
        double bd_lon = z * Math.cos(theta) + 0.0065;
        double bd_lat = z * Math.sin(theta) + 0.006;
        
        return "";
    }
    
    
    private String [] splitOpt(String str,String splitStr)
    {
        List <String>list = new ArrayList<String>();
        
        while(str.indexOf(splitStr)>=0)
        {
            list.add(str.substring(0, str.indexOf(splitStr)));
            //System.out.println(str.substring(0, str.indexOf(splitStr)));
            str = str.substring(str.indexOf(splitStr)+1);
        }
        
        list.add(str);
        
        String[] arr = (String[])list.toArray(new String[list.size()]);
        return arr;
    }
    
}
