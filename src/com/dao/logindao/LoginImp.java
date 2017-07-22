package com.dao.logindao;


import org.springframework.orm.ibatis.SqlMapClientTemplate;
import com.entity.Login;

public class LoginImp implements ILogin
{
    private SqlMapClientTemplate sqlmapclienttemplate = null;
    
    public void setSqlmapclienttemplate(
            SqlMapClientTemplate sqlmapclienttemplate)
    {
        this.sqlmapclienttemplate = sqlmapclienttemplate;
    }
    
    public SqlMapClientTemplate getSqlmapclienttemplate()
    {
        return sqlmapclienttemplate;
    }
    
    public String checked(String name, String password)
    {
        Integer exists = 0;
        Login loginParameter = new Login(name, password);
        try
        {
            exists  = (Integer)sqlmapclienttemplate.queryForObject("getlogin", loginParameter);
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        if (null!= exists &&  exists == 1)
        {
            return "true";
        }
        else
        {
            return "false";
        }
    }
    
}
