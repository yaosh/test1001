package com.service.Loginservice;

import com.dao.logindao.ILogin;

public class LoginserviceImp implements ILoginservice
{
    
    private ILogin login = null;
    
    public void setLogin(ILogin login)
    {
        this.login = login;
    }
    
    public String checked(String name, String password)
    {
        String falg = login.checked(name, password);
        return falg;
    }
    
}
