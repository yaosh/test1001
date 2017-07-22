package com.tools.gaudConvert;

public class GDShopBean
{
    private String name ="";
    private String types ="";
    private String type1 ="";
    private String type2 ="";
    private String type3 ="";
    private String address ="";
    private String x ="";
    private String y ="";
    private String tel ="";
    private String fax ="";
    private String citycode ="";
    private String areacode = "";
    private String newType ="";
    private String brand_id ="0";
    private String brandName ="";
    private String brandEngName ="";
    private String city  ="";
    private String chenggeType1="0";
    private String chenggeType2="0";
    private String chenggeType3="0";
    private String chenggeType1Name="";
    private String chenggeType2Name="";
    private String chenggeType3Name="";
    private String ruleId="0";
    
    public String toString()
    {
       
        StringBuffer sb = new StringBuffer();
        sb.append("商家名称：").append(this.getName()).append(" ")
        .append("高德业态：").append(this.types).append(" ")
        .append("城格业态: ").append(this.chenggeType1Name).append(";")
        .append(this.chenggeType2Name).append(";")
        .append(this.chenggeType3Name).append(" ")
        .append("公式ID: ").append(null == this.ruleId?"":this.ruleId)
        .append("关联品牌: ").append(null == this.brandName?"":this.brandName)
        .append("品牌英文名: ").append(this.brandEngName)
        .append("地址:").append(this.address)
        .append("电话：").append(this.tel)
        .append("传真:").append(this.fax)
        .append("坐标").append(this.x).append(",").append(this.y)
        .append("城市编码:").append(this.citycode)
        .append("地区编码").append(this.areacode)
        .append("城市编号:").append(this.city);
        
        return sb.toString();
    }
    
    public GDShopBean()
    {
        
    }
    
    public GDShopBean(String name,String types,String address,String x,String y,String tel,String citycode,String areacode,String newtype)
    {
        this.name = name;
        this.types = types;
        this.address = address;
        this.x = x;
        this.y = y;
        this.tel = tel;
        this.citycode = citycode;
        this.areacode = areacode;
        this.newType = newtype;
        
        String[] typeArray= types.split("\\|")[0].split(";");
        this.type1 = typeArray[0];
        this.type2 = typeArray[1];
        this.type3 = typeArray[2];
        
    }
    
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public String getTypes()
    {
        return types;
    }
    public void setTypes(String types)
    {
        this.types = types;
    }
    public String getType1()
    {
        return type1;
    }
    public void setType1(String type1)
    {
        this.type1 = type1;
    }
    public String getType2()
    {
        return type2;
    }
    public void setType2(String type2)
    {
        this.type2 = type2;
    }
    public String getType3()
    {
        return type3;
    }
    public void setType3(String type3)
    {
        this.type3 = type3;
    }
    public String getAddress()
    {
        return address;
    }
    public void setAddress(String address)
    {
        this.address = address;
    }
    public String getX()
    {
        return x;
    }
    public void setX(String x)
    {
        this.x = x;
    }
    public String getY()
    {
        return y;
    }
    public void setY(String y)
    {
        this.y = y;
    }
    public String getTel()
    {
        return tel;
    }
    public void setTel(String tel)
    {
        this.tel = tel;
    }
    public String getCitycode()
    {
        return citycode;
    }
    public void setCitycode(String citycode)
    {
        this.citycode = citycode;
    }
    public String getAreacode()
    {
        return areacode;
    }
    public void setAreacode(String areacode)
    {
        this.areacode = areacode;
    }
    public String getNewType()
    {
        return newType;
    }
    public void setNewType(String newType)
    {
        this.newType = newType;
    }

    public String getBrand_id()
    {
        return brand_id;
    }

    public void setBrand_id(String brandId)
    {
        brand_id = brandId;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getChenggeType1()
    {
        return chenggeType1;
    }

    public void setChenggeType1(String chenggeType1)
    {
        this.chenggeType1 = chenggeType1;
    }

    public String getChenggeType2()
    {
        return chenggeType2;
    }

    public void setChenggeType2(String chenggeType2)
    {
        this.chenggeType2 = chenggeType2;
    }

    public String getChenggeType3()
    {
        return chenggeType3;
    }

    public void setChenggeType3(String chenggeType3)
    {
        this.chenggeType3 = chenggeType3;
    }

    public String getChenggeType1Name()
    {
        return chenggeType1Name;
    }

    public void setChenggeType1Name(String chenggeType1Name)
    {
        this.chenggeType1Name = chenggeType1Name;
    }

    public String getChenggeType2Name()
    {
        return chenggeType2Name;
    }

    public void setChenggeType2Name(String chenggeType2Name)
    {
        this.chenggeType2Name = chenggeType2Name;
    }

    public String getChenggeType3Name()
    {
        return chenggeType3Name;
    }

    public void setChenggeType3Name(String chenggeType3Name)
    {
        this.chenggeType3Name = chenggeType3Name;
    }

    public String getRuleId()
    {
        return ruleId;
    }

    public void setRuleId(String ruleId)
    {
        this.ruleId = ruleId;
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

    public String getFax()
    {
        return fax;
    }

    public void setFax(String fax)
    {
        this.fax = fax;
    }
    
    
}
