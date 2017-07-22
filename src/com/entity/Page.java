package com.entity;

public class Page
{
    private String url;
    
    private int pageSize = 10;
    
    private int pageNo = 1;
    
    private int recordCount;

    public Page(int pageNo,int pageSize, int recordCount,String url)
    {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.recordCount = recordCount;
        this.url = url;
    }
    
    public Page()
    {
        
    }
    
    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public int getPageSize()
    {
        return pageSize;
    }

    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }

    public int getPageNo()
    {
        return pageNo;
    }

    public void setPageNo(int pageNo)
    {
        this.pageNo = pageNo;
    }

    public int getRecordCount()
    {
        return recordCount;
    }

    public void setRecordCount(int recordCount)
    {
        this.recordCount = recordCount;
    }
    
    
    
}
