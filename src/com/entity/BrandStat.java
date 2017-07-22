package com.entity;

import java.util.List;

public class BrandStat
{
    private String typeLevelId;
    private String typeLevelName;
    private String typeLevelNum;
    private String type;// 1 一级业态   2 二级业态   3 三级业态
    
    private List <BrandStat> subTypeList;

    public String getTypeLevelId()
    {
        return typeLevelId;
    }

    public void setTypeLevelId(String typeLevelId)
    {
        this.typeLevelId = typeLevelId;
    }

    public String getTypeLevelName()
    {
        return typeLevelName;
    }

    public void setTypeLevelName(String typeLevelName)
    {
        this.typeLevelName = typeLevelName;
    }

    public String getTypeLevelNum()
    {
        return typeLevelNum;
    }

    public void setTypeLevelNum(String typeLevelNum)
    {
        this.typeLevelNum = typeLevelNum;
    }

    public List<BrandStat> getSubTypeList()
    {
        return subTypeList;
    }

    public void setSubTypeList(List<BrandStat> subTypeList)
    {
        this.subTypeList = subTypeList;
    }
    
    
    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public BrandStat(String id,String name,String typeLevelNum,String type,List <BrandStat>subList)
    {
        this.typeLevelId = id;
        this.typeLevelName = name;
        this.typeLevelNum = typeLevelNum;
        this.type = type;
        this.subTypeList = subList;
    }
    
    public BrandStat()
    {
        
    }
    
}
