package com.dao.branddao;

import java.util.List;
import java.util.Map;

import com.entity.Brand;
import com.entity.BrandAgent;
import com.entity.BrandContact;
import com.entity.BrandDetail;
import com.entity.BrandShop;

public interface IBrandDao 
{
    public List<Object> getBrandStatList();
    
    public List <Brand> queryBrandListByTypeOne(Map<String,Integer> map);
    public Integer queryCountBrandListByTypeOne(Map<String,Integer> map);
    public List <Brand> queryBrandListByTypeTwo(Map<String,Integer> map);
    public Integer queryCountBrandListByTypeTwo(Map<String,Integer> map);
    
    public List <Brand> queryTwoTypeListByTypeOne(String typeOneId);
    public Brand queryBrandBaseInfo(String brandId);
    public List <Brand> queryBrandTypeInfoList(String brandId);
    public BrandDetail queryBrandDetail(String brandId);
    public List <Brand> queryRelationBrandList(String brandId);
    public List <BrandShop> queryBrandShopList(String brandId);
    public BrandAgent queryBrandAgent(String brandId);
    public List <Brand> querySubBrandList(String brandId);
    public List <BrandContact> queryBrandContactList(String brandId);
    
    public List <Brand> queryBrandByWd(Map<String,Object> map);
    public Integer queryCountBrandByWd(String word);
    
}
