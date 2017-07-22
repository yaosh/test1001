package com.dao.humandao;

import java.util.List;

public interface IHuman
{
    
    public List findall();
    
    public Object findhuman(Object o);
    
    public boolean save(Object o);
    
    public boolean update(Object o);
    
    public boolean delete(Object o);
}
