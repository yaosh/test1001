package com.actions;

import java.util.List;

import com.entity.ChenggeShopType;
import com.entity.GaudDataFileBean;
import com.entity.GaudShopType;
import com.entity.RuleBean;
import com.entity.TaskBean;
import com.service.poiservice.IPoiService;

public class PoiAction 
{
	
    private List<GaudDataFileBean> list = null;
    private List<GaudShopType> listGaudLevelOneShopTypes = null;
    private List<ChenggeShopType> listChenggeLevelOneShopTypes = null;
    private List<RuleBean> ruleList = null;
    private List<TaskBean> taskList = null;
    private int page ;
    private int numForOnePage;
    private Integer taskPage ;
    private Integer taskNumForOnePage;
    private String hello;
    private String test1;
    private int result = 1;
    private int id ;
    private int taskType;
     
    private IPoiService poiservice = null;
    
    public int getTaskType()
    {
        return taskType;
    }

    public void setTaskType(int taskType)
    {
        this.taskType = taskType;
    }

    public void setTaskPage(Integer taskPage)
    {
        this.taskPage = taskPage;
    }

    public void setTaskNumForOnePage(Integer taskNumForOnePage)
    {
        this.taskNumForOnePage = taskNumForOnePage;
    }

    public String getTest1()
    {
        return test1;
    }

    public void setTest1(String test1)
    {
        this.test1 = test1;
    }

    public int getTaskPage()
    {
        return taskPage;
    }

    public void setTaskPage(int taskPage)
    {
        this.taskPage = taskPage;
    }

    public int getTaskNumForOnePage()
    {
        return taskNumForOnePage;
    }

    public void setTaskNumForOnePage(int taskNumForOnePage)
    {
        this.taskNumForOnePage = taskNumForOnePage;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getResult()
    {
        return result;
    }

    public void setResult(int result)
    {
        this.result = result;
    }

    public List<TaskBean> getTaskList()
    {
        return taskList;
    }

    public void setTaskList(List<TaskBean> taskList)
    {
        this.taskList = taskList;
    }

    public String getHello()
    {
        return hello;
    }

    public void setHello(String hello)
    {
        this.hello = hello;
    }

    public int getPage()
    {
        return page;
    }

    public void setPage(int page)
    {
        this.page = page;
    }

    public int getNumForOnePage()
    {
        return numForOnePage;
    }

    public void setNumForOnePage(int numForOnePage)
    {
        this.numForOnePage = numForOnePage;
    }

    public List<RuleBean> getRuleList()
    {
        return ruleList;
    }

    public void setRuleList(List<RuleBean> ruleList)
    {
        this.ruleList = ruleList;
    }

    public List<GaudShopType> getListGaudLevelOneShopTypes()
    {
        return listGaudLevelOneShopTypes;
    }

    public void setListGaudLevelOneShopTypes(
            List<GaudShopType> listGaudLevelOneShopTypes)
    {
        this.listGaudLevelOneShopTypes = listGaudLevelOneShopTypes;
    }


    public List<ChenggeShopType> getListChenggeLevelOneShopTypes()
    {
        return listChenggeLevelOneShopTypes;
    }

    public void setListChenggeLevelOneShopTypes(
            List<ChenggeShopType> listChenggeLevelOneShopTypes)
    {
        this.listChenggeLevelOneShopTypes = listChenggeLevelOneShopTypes;
    }

    public List<GaudDataFileBean> getList()
    {
        return list;
    }

    public void setList(List<GaudDataFileBean> list)
    {
        this.list = list;
    }

    public IPoiService getPoiservice()
    {
        return poiservice;
    }

    public void setPoiservice(IPoiService poiservice)
    {
        this.poiservice = poiservice;
    }

	public String toFind()
   {
	   return "gaudFindMap";
   }
	
	/**
	 * 功能和toFind  一样 页面体验更好些
	 * <功能详细描述>
	 * @return [参数说明]
	 * 
	 * @return String [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
    public String toFindData()
    {
        return "gaudFindMapData";
    }
    
    public String toAutoFindData()
    {
        return "autoFindData";
    }
	
    /**
     * 功能和toFind  一样 页面体验更好些
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public String toFindDataByCityAndKey()
    {
        return "gaudFindMapDataByCityAndKey";
    }
    
	
	//
	
	
	public String importShop()
	{
		return "gaudFindMap";
	}
	
	public String fileConvert()
    {
	    setList(poiservice.getFileList());
	    
        return "fileConvert";
    }
	
	public String mapRule()
    {
        this.setListGaudLevelOneShopTypes(poiservice.getGaudLevelOneTypes());
        this.setListChenggeLevelOneShopTypes(poiservice.getChenggeLevelOneShopTypes());
        
        return "mapRule";
    }
	
	public String toRuleList()
	{
	    setRuleList(poiservice.getRuleList(page,numForOnePage));
	    
	    return "ruleList";
	}
	
	public String testJQueryAjax()
	{
	    setHello(poiservice.testAjax());
	    System.out.println(hello);
	    return "testAjax";
	}
	
	public String toTaskList()
	{
	    setTaskList(poiservice.getTaskList(taskPage, taskNumForOnePage));
	    return "toTaskList";
	}
	
	public String startTask()
	{
	    System.out.println("id:"+id+",taskType:"+taskType);
	    setResult(1);
	    return "startTaskResult";
	}
	
}
