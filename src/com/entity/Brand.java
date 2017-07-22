package com.entity;

public class Brand implements java.io.Serializable
{
    
        /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;
    
    private String name;
    private String id;
    private String eng_name;
    private String pinyin;
    private String logo;
    private String progress;
    
    private String typeLevelOneId ;
    private String typeLevelTwoId;
    private String typeLevelOneName ;
    private String typeLevelTwoName;
    
    
    public Brand()
    {
        
    }
    
    public Brand(String name,String id,String eng_name,
            String pinyin,String logo,String typeLevelOneId,
            String typeLevelTwoId,String typeLevelOneName,String typeLevelTwoName)
    {
        this.name = name;
        this.id = id;
        this.eng_name = eng_name;
        this.pinyin = pinyin;
        this.logo = logo;
        this.typeLevelOneId = typeLevelOneId;
        this.typeLevelTwoId = typeLevelTwoId;
        this.typeLevelOneName = typeLevelOneName;
        this.typeLevelTwoName = typeLevelTwoName;
        
    }
    
    
    
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public String getId()
    {
        return id;
    }
    public void setId(String id)
    {
        this.id = id;
    }
    public String getEng_name()
    {
        return eng_name;
    }
    public void setEng_name(String engName)
    {
        eng_name = engName;
    }
    public String getPinyin()
    {
        return pinyin;
    }
    public void setPinyin(String pinyin)
    {
        this.pinyin = pinyin;
    }
    public String getLogo()
    {
        return logo;
    }
    public void setLogo(String logo)
    {
        this.logo = logo;
    }
    public String getTypeLevelOneId()
    {
        return typeLevelOneId;
    }
    public void setTypeLevelOneId(String typeLevelOneId)
    {
        this.typeLevelOneId = typeLevelOneId;
    }
    public String getTypeLevelTwoId()
    {
        return typeLevelTwoId;
    }
    public void setTypeLevelTwoId(String typeLevelTwoId)
    {
        this.typeLevelTwoId = typeLevelTwoId;
    }
    public String getTypeLevelOneName()
    {
        return typeLevelOneName;
    }
    public void setTypeLevelOneName(String typeLevelOneName)
    {
        this.typeLevelOneName = typeLevelOneName;
    }
    public String getTypeLevelTwoName()
    {
        return typeLevelTwoName;
    }
    public void setTypeLevelTwoName(String typeLevelTwoName)
    {
        this.typeLevelTwoName = typeLevelTwoName;
    }

    public String getProgress()
    {
        return progress;
    }

    public void setProgress(String progress)
    {
        this.progress = progress;
    }
    
    
    
}
