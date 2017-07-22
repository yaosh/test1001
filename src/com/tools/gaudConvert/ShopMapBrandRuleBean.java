package com.tools.gaudConvert;

public class ShopMapBrandRuleBean
{
    private String ruleid;
    private String ruleindex;
    private String finish;
    private String cgBrand1Name;
    private String cgBrand2Name;
    private String cgBrand3Name;
    private String cgShop1Name;
    private String cgShop2Name;
    private String cgShop3Name;
    
    public ShopMapBrandRuleBean()
    {
        
    }
    
    
    
    public ShopMapBrandRuleBean(String ruleid, String ruleindex, String cgBrand1Name,
            String cgBrand2Name, String cgBrand3Name, String cgShop1Name,
            String cgShop2Name, String cgShop3Name)
    {
        this.ruleid = ruleid;
        this.ruleindex = ruleindex;
        this.cgBrand1Name = cgBrand1Name;
        this.cgBrand2Name = cgBrand2Name;
        this.cgBrand3Name = cgBrand3Name;
        this.cgShop1Name = cgShop1Name;
        this.cgShop2Name = cgShop2Name;
        this.cgShop3Name = cgShop3Name;
    }
    
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("brand1:").append(cgBrand1Name)
        .append("brand2:").append(cgBrand2Name)
        .append("brand3:").append(cgBrand3Name)
        .append("shop1:").append(cgShop1Name)
        .append("shop2:").append(cgShop2Name)
        .append("shop3:").append(cgShop3Name);
        
        return sb.toString();
    }
    
    public String getRuleid()
    {
        return ruleid;
    }
    public void setRuleid(String ruleid)
    {
        this.ruleid = ruleid;
    }
    public String getRuleindex()
    {
        return ruleindex;
    }
    public void setRuleindex(String ruleindex)
    {
        this.ruleindex = ruleindex;
    }
    
    public String getFinish()
    {
        return finish;
    }

    public void setFinish(String finish)
    {
        this.finish = finish;
    }

    public String getCgBrand1Name()
    {
        return cgBrand1Name;
    }
    public void setCgBrand1Name(String cgBrand1Name)
    {
        this.cgBrand1Name = cgBrand1Name;
    }
    public String getCgBrand2Name()
    {
        return cgBrand2Name;
    }
    public void setCgBrand2Name(String cgBrand2Name)
    {
        this.cgBrand2Name = cgBrand2Name;
    }
    public String getCgBrand3Name()
    {
        return cgBrand3Name;
    }
    public void setCgBrand3Name(String cgBrand3Name)
    {
        this.cgBrand3Name = cgBrand3Name;
    }
    public String getCgShop1Name()
    {
        return cgShop1Name;
    }
    public void setCgShop1Name(String cgShop1Name)
    {
        this.cgShop1Name = cgShop1Name;
    }
    public String getCgShop2Name()
    {
        return cgShop2Name;
    }
    public void setCgShop2Name(String cgShop2Name)
    {
        this.cgShop2Name = cgShop2Name;
    }
    public String getCgShop3Name()
    {
        return cgShop3Name;
    }
    public void setCgShop3Name(String cgShop3Name)
    {
        this.cgShop3Name = cgShop3Name;
    }
    
    
    
}
