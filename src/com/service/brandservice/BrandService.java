package com.service.brandservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.ServletActionContext;

import com.dao.branddao.IBrandDao;
import com.entity.Brand;
import com.entity.BrandAgent;
import com.entity.BrandContact;
import com.entity.BrandDetail;
import com.entity.BrandShop;
import com.entity.BrandStat;
import com.log.LogMgr;
import com.tools.StringTools;

public class BrandService implements IBrandService
{
    private IBrandDao branddao=null;
    private static int pageCount = 20;

    public IBrandDao getBranddao()
    {
        return branddao;
    }


    public void setBranddao(IBrandDao branddao)
    {
        this.branddao = branddao;
    }


    public List<BrandStat> getBrandStatList()
    {
        HashMap<String,BrandStat> map1 = new HashMap<String,BrandStat>();
        
        List bransList = branddao.getBrandStatList();
        
        for(int i = 0;i < bransList.size();i++)
        {
            //t1.oneid,t1.twoid,t1.onename,t1.towname,count(*) brandNum
            HashMap<Object,Object> map = (HashMap)bransList.get(i);
            String oneId = ""+map.get("oneid");
            String twoid = ""+map.get("twoid");
            String onename = ""+map.get("onename");
            String towname = ""+map.get("towname");
            String brandNum = ""+map.get("brandNum");
            
            if(null == brandNum || !StringTools.isNumeric(brandNum.trim()) )
            {
                brandNum = "0";
            }
            
            brandNum =brandNum.trim();
            
            //System.out.println(oneId + " 1; "+twoid +" 2; "+onename +" 3; "+ towname +" 4; "+ brandNum);
            //LogMgr.getInstance().log.info(oneId + " "+twoid +"  "+onename +" "+ towname +"  "+ brandNum);
            
            if(map1.containsKey(oneId))
            {
                BrandStat brandStat = map1.get(oneId);
                brandStat.setTypeLevelNum(String.valueOf(Integer.valueOf(brandStat.getTypeLevelNum())+Integer.valueOf(brandNum)));
                BrandStat brandSubStat = new BrandStat();
                brandSubStat.setTypeLevelId(twoid);
                brandSubStat.setTypeLevelName(towname);
                brandSubStat.setTypeLevelNum(brandNum);
                brandSubStat.setType("2");
                brandStat.getSubTypeList().add(brandSubStat);
                
            }
            else
            {
                BrandStat brandStat = new BrandStat();
                brandStat.setSubTypeList(new ArrayList<BrandStat>());
                brandStat.setTypeLevelId(oneId);
                brandStat.setTypeLevelName(onename);
                brandStat.setTypeLevelNum(String.valueOf(Integer.valueOf(brandNum)));
                brandStat.setType("1");
                map1.put(oneId, brandStat);
                
                BrandStat brandSubStat = new BrandStat();
                brandSubStat.setTypeLevelId(twoid);
                brandSubStat.setTypeLevelName(towname);
                brandSubStat.setTypeLevelNum(brandNum);
                brandSubStat.setType("2");
                brandStat.getSubTypeList().add(brandSubStat);
                
            }
        }
        
        
        Set<String> set = map1.keySet();
        String[] oneTypeKeysArray = set.toArray(new String[0]);
        List<BrandStat> typeList = new ArrayList<BrandStat>();
        
        for(String key:oneTypeKeysArray)
        {
            typeList.add(map1.get(key));
        }
        
        return typeList;
        
        
//        //统计品牌信息 返回给首页
//        List <BrandStat> listBrand = new  ArrayList<BrandStat>();
//        
//        BrandStat brandChaosi = new BrandStat("1","超市","496","1",new ArrayList<BrandStat>(10));
//        brandChaosi.getSubTypeList().add(new BrandStat("161","综合超市","149","2",new ArrayList<BrandStat>(10)) );
//        brandChaosi.getSubTypeList().add(new BrandStat("162","精品超市","26","2",new ArrayList<BrandStat>(10)) );
//        brandChaosi.getSubTypeList().add(new BrandStat("171","仓储超市","3","2",new ArrayList<BrandStat>(10)) );
//        brandChaosi.getSubTypeList().add(new BrandStat("174","便利店","214","2",new ArrayList<BrandStat>(10)) );
//        brandChaosi.getSubTypeList().add(new BrandStat("240","社区超市","98","2",new ArrayList<BrandStat>(10)) );
//        brandChaosi.getSubTypeList().add(new BrandStat("924","水果超市","2","2",new ArrayList<BrandStat>(10)) );
//        brandChaosi.getSubTypeList().add(new BrandStat("925","食杂店","1","2",new ArrayList<BrandStat>(10)) );
//        brandChaosi.getSubTypeList().add(new BrandStat("928","农贸市场/菜场","3","2",new ArrayList<BrandStat>(10)) );
//
//        
//        BrandStat brandShengHuo = new BrandStat("2","生活服务","1099","1",new ArrayList<BrandStat>(10));
//        BrandStat brandGouWu = new BrandStat("6","购物","12343","1",new ArrayList<BrandStat>(10));
//        BrandStat brandCanYin = new BrandStat("7","餐饮","6949","1",new ArrayList<BrandStat>(10));
//        BrandStat brandYuLe = new BrandStat("8","休闲娱乐","925","1",new ArrayList<BrandStat>(10));
//        BrandStat brandJiuDian = new BrandStat("9","酒店","245","1",new ArrayList<BrandStat>(10));
//        BrandStat brandLiRen = new BrandStat("10","丽人服务","1677","1",new ArrayList<BrandStat>(10));
//        BrandStat brandMuYing = new BrandStat("11","母婴早教","1774","1",new ArrayList<BrandStat>(10));
//        BrandStat brandJinRong = new BrandStat("12","金融保险","2322","1",new ArrayList<BrandStat>(10));
//        BrandStat brandJiaoTong = new BrandStat("13","交通工具","448","1",new ArrayList<BrandStat>(10));
//        BrandStat brandSangCang = new BrandStat("14","商场商街","339","1",new ArrayList<BrandStat>(10));
//        BrandStat brandYiLiao = new BrandStat("15","医疗保健","379","1",new ArrayList<BrandStat>(10));
//        BrandStat brandPeiXun = new BrandStat("16","培训机构","292","1",new ArrayList<BrandStat>(10));
//        BrandStat brandJiaoYu = new BrandStat("17","教育机构","3","1",new ArrayList<BrandStat>(10));
//        
//        listBrand.add(brandChaosi);
//        listBrand.add(brandShengHuo);
//        listBrand.add(brandGouWu);
//        listBrand.add(brandCanYin);
//        listBrand.add(brandYuLe);
//        listBrand.add(brandJiuDian);
//        listBrand.add(brandLiRen);
//        listBrand.add(brandMuYing);
//        listBrand.add(brandJinRong);
//        listBrand.add(brandJiaoTong);
//        listBrand.add(brandSangCang);
//        listBrand.add(brandYiLiao);
//        listBrand.add(brandPeiXun);
//        listBrand.add(brandJiaoYu);
    }


    public Map<String,Object> queryBrandListByTypeOne(Map<String, String> map)
    {
        if(!map.containsKey("brandTypeLevelOneId"))
        {
            return new HashMap<String,Object>();
        }
        
        String brandTypeLevelOneId = map.get("brandTypeLevelOneId");
        
        if (null== brandTypeLevelOneId || brandTypeLevelOneId.equals(""))
        {
            brandTypeLevelOneId = "1";
        }
        
        //如果是分页 则存在重复传参的情况
        if(brandTypeLevelOneId.contains(","))
        {
            brandTypeLevelOneId = brandTypeLevelOneId.substring(0,brandTypeLevelOneId.indexOf(","));
        }
        
        String pageNo = map.get("pageNo");
        
        if (null == pageNo || pageNo.equals(""))
        {
            pageNo = "1";
        }
        
                
        //根据页码运算获取开始和结束
        String startIndex = String.valueOf((Integer.valueOf(pageNo)-1)*pageCount);
        
        
        //默认取超市的品牌
        if (brandTypeLevelOneId.equals(""))
        {
            brandTypeLevelOneId = "1";
        }
        
        if (startIndex.equals(""))
        {
            startIndex = "1";
        }
        
        
        HashMap<String,Integer> mapParameter = new HashMap<String,Integer>();
        mapParameter.put("brandTypeLevelOneId", Integer.valueOf(brandTypeLevelOneId));
        
        //LogMgr.getInstance().log.info("=============brandTypeLevelOneId:"+ brandTypeLevelOneId);
        
        
        mapParameter.put("startIndex", Integer.valueOf(startIndex));
        mapParameter.put("pageCount", Integer.valueOf(pageCount));
        
        List<Brand> brandList = branddao.queryBrandListByTypeOne(mapParameter);
        Integer total = branddao.queryCountBrandListByTypeOne(mapParameter);
        
        HashMap<String,Object> mapResult = new HashMap<String,Object>();
        
        Brand brandFirst = new Brand();
        //获取一级业态的名称和id
        if(!brandList.isEmpty() && brandList.size() > 0)
        {
            brandFirst = brandList.get(0);
        }
        
        String brandTypeLevelOneName = brandFirst.getTypeLevelOneName();
        
        Brand brandTypeLevelOneBaseInfo = new Brand();
        brandTypeLevelOneBaseInfo.setTypeLevelOneName(brandTypeLevelOneName);
        brandTypeLevelOneBaseInfo.setTypeLevelOneId(brandTypeLevelOneId);
        
        
        //获取二级业态列表
        List<Brand> brandTypeTwoList = branddao.queryTwoTypeListByTypeOne(brandTypeLevelOneId);
        
        mapResult.put("brandList", brandList);
        mapResult.put("brandTypeLevelOneBaseInfo", brandTypeLevelOneBaseInfo);
        mapResult.put("brandTypeLevelTowList", brandTypeTwoList);
        mapResult.put("total", total);
        
        return mapResult;
    }


    public Map<String, Object> queryBrandListByTypeTwo(Map<String, String> map)
    {
        if(!map.containsKey("brandTypeLevelTwoId"))
        {
            return new HashMap<String,Object>();
        }
        
        String brandTypeLevelTwoId = map.get("brandTypeLevelTwoId");
        
        if (null== brandTypeLevelTwoId || brandTypeLevelTwoId.equals(""))
        {
            brandTypeLevelTwoId = "729";
        }
        
        //如果是分页 则存在重复传参的情况
        if(brandTypeLevelTwoId.contains(","))
        {
            brandTypeLevelTwoId = brandTypeLevelTwoId.substring(0,brandTypeLevelTwoId.indexOf(","));
        }
        
        String pageNo = map.get("pageNo");
        
        if (null == pageNo || pageNo.equals(""))
        {
            pageNo = "1";
        }
        
      //根据页码运算获取开始和结束
        String startIndex = String.valueOf((Integer.valueOf(pageNo)-1)*pageCount);
        
        Map<String,Integer> mapParameter = new HashMap<String,Integer>();
        mapParameter.put("brandTypeLevelTwoId", Integer.valueOf(brandTypeLevelTwoId));
        mapParameter.put("startIndex", Integer.valueOf(startIndex));
        mapParameter.put("pageCount", Integer.valueOf(pageCount));
        
        List<Brand> brandList = branddao.queryBrandListByTypeTwo(mapParameter);
        
        Integer total = branddao.queryCountBrandListByTypeTwo(mapParameter);
        
        Map<String,Object> mapResult = new HashMap<String,Object>();
        
        //获取一级业态的名称和id 二级业态的名称和id
        Brand brandTypeLevelTwoBaseInfo = new Brand();
        
        if(null != brandList && brandList.size() > 0)
        {
            Brand brandFirst = brandList.get(0);
            brandTypeLevelTwoBaseInfo.setTypeLevelOneName(brandFirst.getTypeLevelOneName());
            brandTypeLevelTwoBaseInfo.setTypeLevelOneId(brandTypeLevelTwoId);
            brandTypeLevelTwoBaseInfo.setTypeLevelTwoId(brandFirst.getTypeLevelTwoId());
            brandTypeLevelTwoBaseInfo.setTypeLevelTwoName(brandFirst.getTypeLevelTwoName());
        }
        
        //获取三级业态列表 待补充
        
        mapResult.put("brandList", brandList);
        mapResult.put("brandTypeLevelTwoBaseInfo", brandTypeLevelTwoBaseInfo);
        mapResult.put("total", total);
        
        return mapResult;
    }


    public Map<String, Object> queryBrandDetail(String brandId)
    {
        
        Map<String,Object> mapResult = new HashMap<String,Object>();
        
        //组织品牌的基本信息
        Brand brandBaseInfo = branddao.queryBrandBaseInfo(brandId);
        
        if(null == brandBaseInfo)
        {
            brandBaseInfo = new Brand();
        }
        
        mapResult.put("brandBaseInfo", brandBaseInfo);
        
        
        //查询某一个品牌的业态列表  一个品牌可以属于多个业态
        List <Brand> brandTypeInfoList = branddao.queryBrandTypeInfoList(brandId);
        
        if(null == brandTypeInfoList)
        {
            brandTypeInfoList = new ArrayList<Brand>();
        }
        
        //把brandTypeInfoList 中的type信息塞到brandBaseInfo 中
        String brandTypeLevelOneNames = "";
        String brandTypeLevelTwoNames = "";
        
        for(int i = 0;i <brandTypeInfoList.size();i++)
        {
            if(null != brandTypeInfoList.get(i).getTypeLevelOneName())
            {
                brandTypeLevelOneNames =  brandTypeLevelOneNames + "," + brandTypeInfoList.get(i).getTypeLevelOneName();
            }
            
            if(null != brandTypeInfoList.get(i).getTypeLevelTwoName())
            {
                brandTypeLevelTwoNames =  brandTypeLevelTwoNames + "," + brandTypeInfoList.get(i).getTypeLevelTwoName();
            }
            
        }
        
        
        if(brandTypeLevelOneNames.startsWith(","))
        {
            brandTypeLevelOneNames = brandTypeLevelOneNames.substring(1);
        }
        
        if(brandTypeLevelTwoNames.startsWith(","))
        {
            brandTypeLevelTwoNames = brandTypeLevelTwoNames.substring(1);
        }
        
        brandBaseInfo.setTypeLevelOneName(brandTypeLevelOneNames);
        brandBaseInfo.setTypeLevelTwoName(brandTypeLevelTwoNames);
        
        
        //组织品牌的详细信息
        BrandDetail brandDetail = branddao.queryBrandDetail(brandId);

        if(null == brandDetail)
        {
            brandDetail = new BrandDetail();
        }
        
        mapResult.put("brandDetail", brandDetail);
        
        //读取品牌的竞争对手品牌列表
        List <Brand> relationBrandList = branddao.queryRelationBrandList(brandId);
        
        if(null == relationBrandList)
        {
            relationBrandList = new ArrayList<Brand>();
        }
        
        mapResult.put("relationBrandList", relationBrandList);
        
        //读取品牌的连锁商家
        List <BrandShop> brandShopList = branddao.queryBrandShopList(brandId);
        
        if(null == brandShopList)
        {
            brandShopList = new ArrayList<BrandShop>();
        }
        
        mapResult.put("brandShopList", brandShopList);
        
        //读取品牌的代理公司
        BrandAgent brandAgent = branddao.queryBrandAgent(brandId);
        
        if(null == brandAgent)
        {
            brandAgent = new BrandAgent();
        }
        
        mapResult.put("brandAgent", brandAgent);
        
        //读取品牌的旗下品牌
        List<Brand> subBrandList = branddao.querySubBrandList(brandId);
        
        if(null == subBrandList)
        {
            subBrandList = new ArrayList<Brand>();
        }
        
        mapResult.put("subBrandList", subBrandList);
        
        //读取品牌的拓展联系人
        List<BrandContact> brandContactList = branddao.queryBrandContactList(brandId);
        
        if(null == brandContactList)
        {
            brandContactList = new ArrayList<BrandContact>();
        }
        
        mapResult.put("brandContactList", brandContactList);
        
        return mapResult;
    }


    public Map<String, Object> queryBrandListByWord(Map<String, String> map)
    {
        Map<String,Object> mapResult = new HashMap<String,Object>();
        String word = map.get("word");
        
        if (null == word )
        {
            word = "";
        }
        
        word = word.trim();
        
        String pageNo = map.get("pageNo");
        
        if (null == pageNo || pageNo.trim().equals(""))
        {
            pageNo = "1";
        }
        
        
        //根据页码运算获取开始和结束
        String startIndex = String.valueOf((Integer.valueOf(pageNo)-1)*pageCount);
        
        Map<String,Object> mapParameter = new HashMap<String,Object>();
        mapParameter.put("word", word);
        mapParameter.put("startIndex", Integer.valueOf(startIndex));
        mapParameter.put("pageCount", Integer.valueOf(pageCount));
        
        List <Brand> brandList = branddao.queryBrandByWd(mapParameter);
        
        if(null == brandList )
        {
            brandList = new ArrayList <Brand>();
        }
        
        mapResult.put("brandList", brandList);
        
        int total = branddao.queryCountBrandByWd(word);
        mapResult.put("total", total);
        
        return mapResult;
    }
    
    
    
}
