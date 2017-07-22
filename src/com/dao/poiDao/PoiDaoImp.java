package com.dao.poiDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.entity.ChenggeShopType;
import com.entity.GaudShopType;
import com.entity.RuleBean;
import com.entity.TaskBean;

public class PoiDaoImp implements IPoi
{
    private SqlMapClientTemplate sqlmapclienttemplate=null;

    public void setSqlmapclienttemplate(SqlMapClientTemplate sqlmapclienttemplate) {
        this.sqlmapclienttemplate = sqlmapclienttemplate;
    }
    
    public boolean delete(Object o)
    {
        return false;
    }

    public List<GaudShopType> getGuadType(int level)
    {
        return sqlmapclienttemplate.queryForList("getGuadType",level);
    }

    public List<GaudShopType> getguadChildTypeByType(int pid)
    {
        return sqlmapclienttemplate.queryForList("getGuadChildTypeByType",pid);
    }
    
    public List<ChenggeShopType> getChenggeLevelOneTypes()
    {
        return sqlmapclienttemplate.queryForList("getChenggeLevelOneTypes");
    }
    
    public List<ChenggeShopType> getChenggeLevelTwoChildTypeByType(int pid)
    {
        return sqlmapclienttemplate.queryForList("getChenggeLevelTwoChildTypeByType",pid);
    }
    
    public List<ChenggeShopType> getChenggeLevelThreeChildTypeByType(int pid)
    {
        return sqlmapclienttemplate.queryForList("getChenggeLevelThreeChildTypeByType",pid);
    }
    
    public boolean queryGaudList(Object o)
    {
        return false;
    }

    public List queryRuleList()
    {
        return null;
    }

    public List<RuleBean> getRuleList(int page, int numForOnePage)
    {
        int offset = numForOnePage*(page - 1);
        Map<String,Integer> map = new HashMap<String,Integer>();
        map.put("offset", offset);
        map.put("numForOnePage", numForOnePage);
        
        return sqlmapclienttemplate.queryForList("queryRuleListPage",map);
        
    }

    public String testAjax()
    {
        return (String)sqlmapclienttemplate.queryForObject("testAjax");
    }

    public List<TaskBean> getTaskList(int page, int numForOnePage)
    {
        
        int offset = numForOnePage*(page - 1);
        Map<String,Integer> map = new HashMap<String,Integer>();
        map.put("offset", offset);
        map.put("numForOnePage", numForOnePage);
        //System.out.println("offset:"+offset);
        //System.out.println("numForOnePage:"+numForOnePage);
        return sqlmapclienttemplate.queryForList("queryTaskListPage",map);
    }
    
    
}
