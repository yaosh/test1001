package com.actions;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.service.Humanservice.IHumanservice;
import com.service.Loginservice.ILoginservice;
import com.service.brandservice.IBrandService;
import com.entity.BrandStat;

public class LoginAction
{
    //所有属性都是注入方式 
    private ILoginservice login = null;
    
    private IHumanservice humanservice = null;
    
    private IBrandService brandservice = null;
    
    private String username;
    
    private String password;
    
    //List<BrandStat> list = null;
    
//    public List<BrandStat> getList()
//    {
//        return list;
//    }
//    
//    public void setList(List<BrandStat> list)
//    {
//        this.list = list;
//    }
    
    public void setHumanservice(IHumanservice humanservice)
    {
        this.humanservice = humanservice;
    }
    
    public void setLogin(ILoginservice login)
    {
        this.login = login;
    }
    
    
    public IBrandService getBrandservice()
    {
        return brandservice;
    }

    public void setBrandservice(IBrandService brandservice)
    {
        this.brandservice = brandservice;
    }

    public String getUsername()
    {
        return username;
    }
    
    public void setUsername(String username)
    {
        this.username = username;
    }
    
    public String getPassword()
    {
        return password;
    }
    
    public void setPassword(String password)
    {
        this.password = password;
    }
    
    /*HttpServletRequest request = ServletActionContext.getRequest();
    HttpServletResponse response = ServletActionContext.getResponse();
    HttpSession session = request.getSession();
    */

    public String execute() throws Exception
    {
        if (login.checked(username, password).equals("true"))
        {
            //setList(brandservice.getBrandStatList());
            return "brand";
        }
        else
        {
            return "error";
        }
    }
    
    public String exit() throws Exception
    {
        ServletActionContext.getContext().getSession().clear();
        ServletActionContext.getRequest().getSession().invalidate();
        
        return "login";
    }
    
}
