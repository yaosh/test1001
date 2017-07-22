package com.tools.gaudConvert;

public class BrandBean
{
    
    private String brandName;
    private String brandOneTypeName ;
    private String brandTwoTypeName ;
    private String brandThreeTypeName ;
    private String brandEngName;
    private String brandOtherName;
    private String brandOneTypeId;
    private String brandTwoTypeId;
    private String brandThreeTypeId;
    private String brandId;
    //分词，目前只计算品牌中文名足矣
    private String brandWords;
    //按照字符分词
    private String brandChars;
    
    public BrandBean()
    {
        
    }
    
    public BrandBean(String name,String id,String engName,String type1id,String type1name,String type2id,String type2name)
    {
        this.brandName = name;
        this.brandId = id;
        this.brandEngName = engName;
        this.brandOneTypeId = type1id;
        this.brandOneTypeName = type1name;
        this.brandTwoTypeId = type2id;
        this.brandTwoTypeName = type2name;
        
    }
    
    public BrandBean(String name,String id,String engName,String type1id,String type1name,String type2id,String type2name,String type3id,String type3name)
    {
        this.brandName = name;
        this.brandId = id;
        this.brandEngName = engName;
        this.brandOneTypeId = type1id;
        this.brandOneTypeName = type1name;
        this.brandTwoTypeId = type2id;
        this.brandTwoTypeName = type2name;
        this.brandThreeTypeId = type3id;
        this.brandThreeTypeName = type3name;
        
    }
    
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(brandName+","+brandEngName+","+brandOneTypeName+","+brandTwoTypeName+","+brandThreeTypeName);
        
        return sb.toString();
    }
    
    public String getBrandName()
    {
        return brandName;
    }
    public void setBrandName(String brandName)
    {
        this.brandName = brandName;
    }
     
    public String getBrandEngName()
    {
        return brandEngName;
    }
    public void setBrandEngName(String brandEngName)
    {
        this.brandEngName = brandEngName;
    }
    
    public String getBrandId()
    {
        return brandId;
    }
    public void setBrandId(String brandId)
    {
        this.brandId = brandId;
    }
    public String getBrandOneTypeName()
    {
        return brandOneTypeName;
    }
    public void setBrandOneTypeName(String brandOneTypeName)
    {
        this.brandOneTypeName = brandOneTypeName;
    }
    public String getBrandTwoTypeName()
    {
        return brandTwoTypeName;
    }
    public void setBrandTwoTypeName(String brandTwoTypeName)
    {
        this.brandTwoTypeName = brandTwoTypeName;
    }
    public String getBrandThreeTypeName()
    {
        return brandThreeTypeName;
    }
    public void setBrandThreeTypeName(String brandThreeTypeName)
    {
        this.brandThreeTypeName = brandThreeTypeName;
    }
    public String getBrandOneTypeId()
    {
        return brandOneTypeId;
    }
    public void setBrandOneTypeId(String brandOneTypeId)
    {
        this.brandOneTypeId = brandOneTypeId;
    }
    public String getBrandTwoTypeId()
    {
        return brandTwoTypeId;
    }
    public void setBrandTwoTypeId(String brandTwoTypeId)
    {
        this.brandTwoTypeId = brandTwoTypeId;
    }
    public String getBrandThreeTypeId()
    {
        return brandThreeTypeId;
    }
    public void setBrandThreeTypeId(String brandThreeTypeId)
    {
        this.brandThreeTypeId = brandThreeTypeId;
    }

    public String getBrandOtherName()
    {
        return brandOtherName;
    }

    public void setBrandOtherName(String brandOtherName)
    {
        this.brandOtherName = brandOtherName;
    }

    public String getBrandWords()
    {
        return brandWords;
    }

    public void setBrandWords(String brandWords)
    {
        this.brandWords = brandWords;
    }

    public String getBrandChars()
    {
        return brandChars;
    }

    public void setBrandChars(String brandChars)
    {
        this.brandChars = brandChars;
    }
    
}
