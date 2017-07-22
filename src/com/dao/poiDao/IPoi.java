package com.dao.poiDao;

import java.util.List;

import com.entity.ChenggeShopType;
import com.entity.GaudShopType;
import com.entity.RuleBean;
import com.entity.TaskBean;

public interface IPoi
{
    public List<GaudShopType> getGuadType(int level);
    
    public boolean queryGaudList(Object o);
    
    public List queryRuleList();
    
    public boolean delete(Object o);
    
    public List<GaudShopType> getguadChildTypeByType(int pid);
    
    public List<ChenggeShopType> getChenggeLevelOneTypes();
    
    public List<ChenggeShopType> getChenggeLevelTwoChildTypeByType(int pid);
    
    public List<ChenggeShopType> getChenggeLevelThreeChildTypeByType(int pid);
    
    public List<RuleBean> getRuleList(int page,int numForOnePage);
    
    public String testAjax();
    
    public List<TaskBean> getTaskList(int page,int numForOnePage);
    
}
