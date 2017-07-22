package com.service.poiservice;

import java.util.List;

import com.entity.ChenggeShopType;
import com.entity.GaudDataFileBean;
import com.entity.GaudShopType;
import com.entity.RuleBean;
import com.entity.TaskBean;

public interface IPoiService
{
    public List<GaudDataFileBean> getFileList();
    public List<String> getFileList(String foldPath);
    //public Map<Integer,List<GaudShopType>> getGaudType();
    public List<ChenggeShopType> getChenggeLevelOneShopTypes();
    public List<ChenggeShopType> getChenggeLevelTwoChildTypes(int pid);
    public List<ChenggeShopType> getChenggeLevelThreeChildTypes(int pid);
    public List<GaudShopType> getGaudLevelOneTypes();
    public List<RuleBean> getRuleList(int page, int numForOnePage);
    public String testAjax();
    public List<TaskBean> getTaskList(int page, int numForOnePage);
    
}
