package com.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.entity.Student;
import com.service.Humanservice.IHumanservice;

public class UserAction
{
    
    private IHumanservice humanservice = null;
    
    private String id;
    
    private String name;
    
    private Integer score;
    
    private String bak;
    
    private String check;
    
    private String type;
    
    List<Student> list = null;
    
    public void setHumanservice(IHumanservice humanservice)
    {
        this.humanservice = humanservice;
    }
    
    public String getType()
    {
        return type;
    }
    
    public void setType(String type)
    {
        this.type = type;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public Integer getScore()
    {
        return score;
    }
    
    public void setScore(Integer score)
    {
        this.score = score;
    }
    
    public String getBak()
    {
        return bak;
    }
    
    public void setBak(String bak)
    {
        this.bak = bak;
    }
    
    public String getCheck()
    {
        return check;
    }
    
    public void setCheck(String check)
    {
        this.check = check;
    }
    
    public List<Student> getList()
    {
        return list;
    }
    
    public void setList(List<Student> list)
    {
        this.list = list;
    }
    
    public String execute() throws Exception
    {
        if("manageStudent".equals(type))
        {
            setList(humanservice.findall());
            
            return "manageStudent";
        }
        else if ("toAdd".equals(type))
        {
            setId("");
            setName("");
            setScore(0);
            setBak("");
            
            return "addStudent";
        }
        else if ("add".equals(type))
        {
            humanservice.save(id, name, score, bak);
            
            setList(humanservice.findall());
            return "manageStudent";
        }
        else if ("edit".equals(type))
        {
            humanservice.update(id, name, score, bak);
            
            setList(humanservice.findall());
            return "manageStudent";
        }
        else if ("del".equals(type))
        {
            humanservice.delete(id);
            
            setList(humanservice.findall());
            return "manageStudent";
        }
        if ("edit1".equals(type))
        {
            Student stu = (Student) humanservice.findhuman(id);
            setId(stu.getId());
            setName(stu.getName());
            setScore(stu.getScore());
            setBak(stu.getBak());
            return "update";
        }
        else
        {
            setList(humanservice.findall());
            return "user";
        }
    }
    
    
    public String toAdd()
    {
        return "addStudent";
    }
}
