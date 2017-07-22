package com.entity;

public class TaskBean
{
    private int id;
    private int ruletype;
    private int taskstatus;
    private String keyword;
    private String memo;
    private String inname;
    private String outname;
    private int intype;
    private int outtype;
    public int getId()
    {
        return id;
    }
    public void setId(int id)
    {
        this.id = id;
    }
    public int getRuletype()
    {
        return ruletype;
    }
    public void setRuletype(int ruletype)
    {
        this.ruletype = ruletype;
    }
    public int getTaskstatus()
    {
        return taskstatus;
    }
    public void setTaskstatus(int taskstatus)
    {
        this.taskstatus = taskstatus;
    }
    public String getKeyword()
    {
        return keyword;
    }
    public void setKeyword(String keyword)
    {
        this.keyword = keyword;
    }
    public String getMemo()
    {
        return memo;
    }
    public void setMemo(String memo)
    {
        this.memo = memo;
    }
    public String getInname()
    {
        return inname;
    }
    public void setInname(String inname)
    {
        this.inname = inname;
    }
    public String getOutname()
    {
        return outname;
    }
    public void setOutname(String outname)
    {
        this.outname = outname;
    }
    public int getIntype()
    {
        return intype;
    }
    public void setIntype(int intype)
    {
        this.intype = intype;
    }
    public int getOuttype()
    {
        return outtype;
    }
    public void setOuttype(int outtype)
    {
        this.outtype = outtype;
    }
    
}
