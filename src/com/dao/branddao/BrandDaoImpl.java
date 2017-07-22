package com.dao.branddao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.entity.Brand;
import com.entity.BrandAgent;
import com.entity.BrandContact;
import com.entity.BrandDetail;
import com.entity.BrandShop;
import com.log.LogMgr;

public class BrandDaoImpl implements IBrandDao
{
    private SqlMapClientTemplate sqlmapclienttemplate=null;
    
    public void setSqlmapclienttemplate(SqlMapClientTemplate sqlmapclienttemplate) {
        this.sqlmapclienttemplate = sqlmapclienttemplate;
    }
    
    public List<Object> getBrandStatList()
    {
        return (List)sqlmapclienttemplate.queryForList("getBrandStatList");
    }

    public List<Brand> queryBrandListByTypeOne(Map<String, Integer> map)
    {
        return (List)sqlmapclienttemplate.queryForList("queryBrandListByTypeOne",map);
    }
    
    public Integer queryCountBrandListByTypeOne(Map<String, Integer> map)
    {
        return (Integer)sqlmapclienttemplate.queryForObject("queryCountBrandListByTypeOne",map);
    }

    public List<Brand> queryTwoTypeListByTypeOne(String typeOneId)
    {
        return (List)sqlmapclienttemplate.queryForList("queryTwoTypeListByTypeOne",typeOneId);
    }

    public List<Brand> queryBrandListByTypeTwo(Map<String, Integer> map)
    {
        return (List)sqlmapclienttemplate.queryForList("queryBrandListByTypeTwo",map);
    }

    public Brand queryBrandBaseInfo(String brandId)
    {
        return (Brand)sqlmapclienttemplate.queryForObject("queryBrandBaseInfo",brandId);
    }

    public BrandDetail queryBrandDetail(String brandId)
    {
        return (BrandDetail)sqlmapclienttemplate.queryForObject("queryBrandDetail",brandId);
    }

    @SuppressWarnings("unchecked")
    public List<Brand> queryRelationBrandList(String brandId)
    {
        return (List<Brand>)sqlmapclienttemplate.queryForList("queryRelationBrandList",brandId);
    }

    @SuppressWarnings("unchecked")
    public List<BrandShop> queryBrandShopList(String brandId)
    {
        return (List<BrandShop>)sqlmapclienttemplate.queryForList("queryBrandShopList",brandId);
    }

    public BrandAgent queryBrandAgent(String brandId)
    {
        return (BrandAgent)sqlmapclienttemplate.queryForObject("queryBrandAgent",brandId);
    }

    public List<Brand> querySubBrandList(String brandId)
    {
        return (List<Brand>)sqlmapclienttemplate.queryForList("querySubBrandList",brandId);
    }

    public List<BrandContact> queryBrandContactList(String brandId)
    {
        return (List<BrandContact>)sqlmapclienttemplate.queryForList("queryBrandContactList",brandId);
    }

    public List<Brand> queryBrandTypeInfoList(String brandId)
    {
        return (List<Brand>)sqlmapclienttemplate.queryForList("queryBrandTypeInfoList",brandId);
    }

    public Integer queryCountBrandListByTypeTwo(Map<String, Integer> map)
    {
        return (Integer)sqlmapclienttemplate.queryForObject("queryCountBrandListByTypeTwo",map);
    }

    public List<Brand> queryBrandByWd(Map<String,Object> map)
    {
        return (List<Brand>)sqlmapclienttemplate.queryForList("queryBrandByWd",map);
    }

    public Integer queryCountBrandByWd(String word)
    {
        return (Integer)sqlmapclienttemplate.queryForObject("queryCountBrandByWd",word);
    }

    
    
}
