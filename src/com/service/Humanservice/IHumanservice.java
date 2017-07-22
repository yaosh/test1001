package com.service.Humanservice;

import java.util.List;

public interface IHumanservice {
    public List findall();
	
	public Object findhuman(String id);
	
	public boolean save(String id,String name,int score,String bak);
	
	public boolean update(String id,String name,int score,String bak);
	
	public boolean delete(String id);

}
