package com.service.poiservice;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dao.humandao.IHuman;
import com.dao.poiDao.IPoi;
import com.dao.poiDao.PoiDaoImp;
import com.entity.ChenggeShopType;
import com.entity.GaudDataFileBean;
import com.entity.GaudShopType;
import com.entity.RuleBean;
import com.entity.TaskBean;

public class PoiServiceImp implements IPoiService
{
    public static String path = new File("").getAbsolutePath()+"\\..\\gaud\\";
    private IPoi poiDao=null;
    
    
    public IPoi getPoiDao()
    {
        return poiDao;
    }

    public void setPoiDao(IPoi poiDao)
    {
        this.poiDao = poiDao;
    }

    /**
     * 
     * @return
     */
    public List<GaudDataFileBean> getFileList()
    {
        List<GaudDataFileBean> list = new ArrayList<GaudDataFileBean>();
        
        File fold = new File(path);
        
        if (!fold.exists())
        {
            return list;
        }
        
        File [] files = fold.listFiles();
        
        for(int i = 0;i < files.length;i++)
        {
            
            if (files[i].isFile())
            {
                continue;
            }
            
            File [] foldSub = files[i].listFiles();
            
            for(int j = 0;j < foldSub.length ; j++)
            {
                File [] subFiles = foldSub[j].listFiles();
                
                for(int k = 0;k<subFiles.length;k++)
                {
                    GaudDataFileBean bean  = new GaudDataFileBean();
                    bean.setArea(files[i].getName());
                    bean.setCity(foldSub[j].getName());
                    bean.setFileName(subFiles[k].getName());
                    bean.setPath(path+files[i].getName()+"\\"+foldSub[j].getName()+"\\");
                    list.add(bean);
                }
            }
            
        }
        
        return list;
    }
    
    /**
     * <一句话功能简述>
     * <功能详细描述>
     * @param foldPath
     * @return [参数说明]
     * 
     * @return List<String> [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public List<String> getFileList(String foldPath)
    {
        List<String> list = new ArrayList<String>();
        
        File fold = new File(foldPath);
        
        if (!fold.exists())
        {
            return list;
        }
        
        File [] files = fold.listFiles();
        
        for(int i = 0;i < files.length;i++)
        {
            
            if (files[i].isFile())
            {
                continue;
            }
            
            File [] foldSub = files[i].listFiles();
            
            for(int j = 0;j < foldSub.length ; j++)
            {
                File [] subFiles = foldSub[j].listFiles();
                
                for(int k = 0;k<subFiles.length;k++)
                {
                    list.add(files[i].getName()+"_"+foldSub[j].getName()+"_"+subFiles[k]);
                    System.out.println(files[i].getName()+"_"+foldSub[j].getName()+"_"+subFiles[k]);
                }
            }
            
        }
        
        return list;
    }
    
    /**
     * <一句话功能简述>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return Map<Integer,List<GaudShopType>> [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    
    public List<GaudShopType> getGaudLevelOneTypes()
    {
        List<GaudShopType> listShopType = poiDao.getGuadType(0);
        return listShopType;
    }
    
    
    /**
     * <一句话功能简述>
     * <功能详细描述>
     * @param pid
     * @return [参数说明]
     * 
     * @return List<GaudShopType> [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public List<GaudShopType> getGaudChildTypeByTypeId(int pid)
    {
        System.out.println("pid:"+pid);
        List<GaudShopType> chlidTypeList =poiDao.getguadChildTypeByType(pid);
        //System.out.println("chlidTypeList:"+chlidTypeList);
        return chlidTypeList;
    }
    
    public List<ChenggeShopType> getChenggeLevelOneShopTypes()
    {
        List<ChenggeShopType> chenggeTypeList = poiDao.getChenggeLevelOneTypes();
        return chenggeTypeList;
    }
    
    public List<ChenggeShopType> getChenggeLevelTwoChildTypes(int pid)
    {
        List<ChenggeShopType> chenggeTypeList = poiDao.getChenggeLevelTwoChildTypeByType(pid);
        return chenggeTypeList;
    }
    
    public List<ChenggeShopType> getChenggeLevelThreeChildTypes(int pid)
    {
        List<ChenggeShopType> chenggeTypeList = poiDao.getChenggeLevelThreeChildTypeByType(pid);
        return chenggeTypeList;
    }

    public List<RuleBean> getRuleList(int page, int numForOnePage)
    {
        return poiDao.getRuleList(page, numForOnePage);
    }

    public String testAjax()
    {
        return poiDao.testAjax();
    }

    public List<TaskBean> getTaskList(int page, int numForOnePage)
    {
        return poiDao.getTaskList(page, numForOnePage);
    }
    
    
}
