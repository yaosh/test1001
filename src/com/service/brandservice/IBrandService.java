package com.service.brandservice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.entity.Brand;
import com.entity.BrandStat;

public interface IBrandService
{
    public List<BrandStat> getBrandStatList();
    
    public Map<String,Object> queryBrandListByTypeOne(Map<String,String> map);
    public Map<String,Object> queryBrandListByTypeTwo(Map<String,String> map);
    public Map<String,Object> queryBrandListByWord(Map<String,String> map);
    public Map<String,Object> queryBrandDetail(String brandId);
}
