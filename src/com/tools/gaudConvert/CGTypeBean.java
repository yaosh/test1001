package com.tools.gaudConvert;

public class CGTypeBean
{
    private String typeName ;
    
    private String typeId;
    
    private String typeLevel;
    
    public CGTypeBean()
    {
        
    }
    
    public CGTypeBean(String name,String id,String level)
    {
        this.typeName = name;
        this.typeId = id;
        this.typeLevel = level;
    }
    
    public String getTypeName()
    {
        return typeName;
    }
    public void setTypeName(String typeName)
    {
        this.typeName = typeName;
    }
    public String getTypeId()
    {
        return typeId;
    }
    public void setTypeId(String typeId)
    {
        this.typeId = typeId;
    }
    public String getTypeLevel()
    {
        return typeLevel;
    }
    public void setTypeLevel(String typeLevel)
    {
        this.typeLevel = typeLevel;
    }
    
    
    
}
