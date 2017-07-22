package com.actions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.entity.Brand;
import com.entity.BrandAgent;
import com.entity.BrandContact;
import com.entity.BrandDetail;
import com.entity.BrandShop;
import com.entity.BrandStat;
import com.entity.Page;
import com.log.LogMgr;
import com.service.brandservice.IBrandService;
import com.web.PagerTag;

public class BrandAction
{
    
    private IBrandService brandservice ;
    private List<Brand> brandList;
    private List<Brand> brandTypeLevelTowList ;
    private Map <String,Brand>outPutMap;
    private String typeOneName;
    private String brandTypeLevelOneId ;
    private String brandTypeLevelTwoId ;
    private Brand brandBaseInfo;
    private String brandId;
    private BrandDetail brandDetail;
    private List<Brand>relationBrandList;
    private List<BrandShop>brandShopList;
    private BrandAgent brandAgent;
    private List<Brand> subBrandList;
    private List<BrandContact> brandContactList;
    private List<BrandStat> brandStatList ;
    private Page page;
    private String pageNo;
    private static int pageCount = 20;
    private String wd;
    private Integer total;
    
    public IBrandService getBrandservice()
    {
        return brandservice;
    }


    public void setBrandservice(IBrandService brandservice)
    {
        this.brandservice = brandservice;
    }



    public List<Brand> getBrandList()
    {
        return brandList;
    }



    public void setBrandList(List<Brand> brandList)
    {
        this.brandList = brandList;
    }

    

    public List<Brand> getBrandTypeLevelTowList()
    {
        return brandTypeLevelTowList;
    }



    public void setBrandTypeLevelTowList(List<Brand> brandTypeLevelTowList)
    {
        this.brandTypeLevelTowList = brandTypeLevelTowList;
    }





    public Map<String, Brand> getOutPutMap()
    {
        return outPutMap;
    }


    public void setOutPutMap(Map<String, Brand> outPutMap)
    {
        this.outPutMap = outPutMap;
    }


    public String getTypeOneName()
    {
        return typeOneName;
    }



    public void setTypeOneName(String typeOneName)
    {
        this.typeOneName = typeOneName;
    }

    
    public String getBrandTypeLevelOneId()
    {
        return brandTypeLevelOneId;
    }


    public void setBrandTypeLevelOneId(String brandTypeLevelOneId)
    {
        this.brandTypeLevelOneId = brandTypeLevelOneId;
    }


    public String getBrandTypeLevelTwoId()
    {
        return brandTypeLevelTwoId;
    }


    public void setBrandTypeLevelTwoId(String brandTypeLevelTwoId)
    {
        this.brandTypeLevelTwoId = brandTypeLevelTwoId;
    }

    


    public Brand getBrandBaseInfo()
    {
        return brandBaseInfo;
    }


    public void setBrandBaseInfo(Brand brandBaseInfo)
    {
        this.brandBaseInfo = brandBaseInfo;
    }

    

    public String getBrandId()
    {
        return brandId;
    }


    public void setBrandId(String brandId)
    {
        this.brandId = brandId;
    }


    
    public BrandDetail getBrandDetail()
    {
        return brandDetail;
    }


    public void setBrandDetail(BrandDetail brandDetail)
    {
        this.brandDetail = brandDetail;
    }


    public List<Brand> getRelationBrandList()
    {
        return relationBrandList;
    }


    public void setRelationBrandList(List<Brand> relationBrandList)
    {
        this.relationBrandList = relationBrandList;
    }

    

    public List<BrandShop> getBrandShopList()
    {
        return brandShopList;
    }


    public void setBrandShopList(List<BrandShop> brandShopList)
    {
        this.brandShopList = brandShopList;
    }

    

    public BrandAgent getBrandAgent()
    {
        return brandAgent;
    }


    public void setBrandAgent(BrandAgent brandAgent)
    {
        this.brandAgent = brandAgent;
    }


    
    public List<Brand> getSubBrandList()
    {
        return subBrandList;
    }


    public void setSubBrandList(List<Brand> subBrandList)
    {
        this.subBrandList = subBrandList;
    }

    
    

    public List<BrandContact> getBrandContactList()
    {
        return brandContactList;
    }


    public void setBrandContactList(List<BrandContact> brandContactList)
    {
        this.brandContactList = brandContactList;
    }


    
    public List<BrandStat> getBrandStatList()
    {
        return brandStatList;
    }


    public void setBrandStatList(List<BrandStat> brandStatList)
    {
        this.brandStatList = brandStatList;
    }


    public Page getPage()
    {
        return page;
    }


    public void setPage(Page page)
    {
        this.page = page;
    }
    
    
    

    public String getWd()
    {
        return wd;
    }


    public void setWd(String wd)
    {
        this.wd = wd;
    }

    

    public Integer getTotal()
    {
        return total;
    }


    public void setTotal(Integer total)
    {
        this.total = total;
    }


    /**
     * 一级业态品牌列表
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public String toBrandListPageByTypeOne()
    {
        //控制层只透传 不校验，放到业务层去处理
        Map<String,String> mapParameter = new HashMap<String,String>();
        pageNo = ServletActionContext.getRequest().getParameter("pageNo");
        brandTypeLevelOneId =  ServletActionContext.getRequest().getParameter("brandTypeLevelOneId");
        mapParameter.put("brandTypeLevelOneId", brandTypeLevelOneId);
        
        if (null == pageNo || pageNo.equals(""))
        {
            pageNo = "1";
        }
        
        mapParameter.put("pageNo", pageNo);
        
        Map<String,Object> resultMap = brandservice.queryBrandListByTypeOne(mapParameter);
        
        //向页面传递结果
        brandList = (List<Brand>)resultMap.get("brandList");
        brandTypeLevelTowList = (List)resultMap.get("brandTypeLevelTowList");
        
        brandBaseInfo = (Brand)resultMap.get("brandTypeLevelOneBaseInfo");
        
        int total = (Integer)resultMap.get("total");
        
        page = new Page(Integer.valueOf(pageNo),pageCount,total,"/brandlistone_" + brandTypeLevelOneId + ".html");
        
        return "brandListTypeOne";
    }
    
    public String toBrandListPageByTypeTwo()
    {
        
        pageNo = ServletActionContext.getRequest().getParameter("pageNo");
        
        if (null == pageNo || pageNo.equals(""))
        {
            pageNo = "1";
        }
        
        brandTypeLevelTwoId =  ServletActionContext.getRequest().getParameter("brandTypeLevelTwoId");
        
        Map<String,String> mapParameter = new HashMap<String,String>();
        mapParameter.put("brandTypeLevelTwoId", brandTypeLevelTwoId);
        mapParameter.put("pageNo", pageNo);
        
        Map<String,Object> resultMap = brandservice.queryBrandListByTypeTwo(mapParameter);
        
        setBrandList((List<Brand>)resultMap.get("brandList"));
        brandBaseInfo = (Brand)resultMap.get("brandTypeLevelTwoBaseInfo");
        int total = (Integer)resultMap.get("total");
        
        page = new Page(Integer.valueOf(pageNo),pageCount,total,"/brandlisttwo_" + brandTypeLevelTwoId + ".html");
        
        return "brandListTypeTwo";
    }
    
    @SuppressWarnings("unchecked")
    public String toBrandDetail()
    {
        
        Map<String,Object> mapResult = brandservice.queryBrandDetail(brandId);
        
        brandBaseInfo = (Brand)mapResult.get("brandBaseInfo");
        
        brandDetail = (BrandDetail)mapResult.get("brandDetail");
        relationBrandList = (List<Brand>)mapResult.get("relationBrandList");
        brandShopList = (List<BrandShop>)mapResult.get("brandShopList");
        brandAgent = (BrandAgent)mapResult.get("brandAgent");
        subBrandList = (List<Brand>)mapResult.get("subBrandList");
        brandContactList = (List<BrandContact>)mapResult.get("brandContactList");
        
        return "brandDetail";
    }
    
    
    public String searchBrandListByWd()
    {
        pageNo = ServletActionContext.getRequest().getParameter("pageNo");
        
        if (null == pageNo || pageNo.trim().equals(""))
        {
            pageNo = "1";
        }
        
        Map<String,String> mapParameter = new HashMap<String,String>();
        String word = ServletActionContext.getRequest().getParameter("wd");
        
        mapParameter.put("word", word);
        mapParameter.put("pageNo", pageNo);
        
        Map<String,Object> mapResult =  brandservice.queryBrandListByWord(mapParameter);
        brandList = (List<Brand>)mapResult.get("brandList");
        
        total = (Integer)mapResult.get("total");
            
        wd = word;
        
        //LogMgr.getInstance().log.info("============brandList:"+brandList.size());
        
        page = new Page(Integer.valueOf(pageNo),pageCount,total,"/queryBrand.html");
        
        return "searchBrandListByWd";
    }
    /**
     * 商家品牌库首页
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public String toMain()
    {
        brandStatList = brandservice.getBrandStatList();
        
        return "main";
    }
}

//<c:forEach items=${brandList}  var="var" >   
//<c:out value="${var.name}" />                
//</c:forEach>

//Map中有bean 标签写法 

//outPutMap = new HashMap();
//outPutMap.put("brandTypeLevelBaseInfo", (Brand)resultMap.get("brandTypeLevelOneBaseInfo"));

//页面：
//<s:property value="outPutMap['brandTypeLevelBaseInfo'].typeLevelOneName"/>
//直接bean标签写法
//<s:bean name="com.entity.Brand"   var="brandBaseInfo" />
//<s:property value="brandBaseInfo.typeLevelOneName"/>
//或者 ${brandBaseInfo.typeLevelOneName} 这种最简单
