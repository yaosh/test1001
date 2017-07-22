package com.log;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LogMgr
{
    
    /** <一句话功能简述>
     * <功能详细描述>
     * @param args [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static void main(String[] args)
    {
         
        LogMgr.getInstance().log.debug("ddd");
    }
    
    private static LogMgr instance = null;
    
    public final Log log = LogFactory.getLog(getClass());
    
    public static LogMgr getInstance()
    {
        synchronized(LogMgr.class)
        {
            if(instance==null)
            {
                instance=new LogMgr();
            }
        }
        
        return instance;
    }
    
    
}
