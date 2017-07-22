package com.tools.shopAnalyse;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelUtilsPoi
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
        
    }
    
    public static void createxls(String file, String sheetNameList)
    {
        FileOutputStream fileOut = null;
        
        try
        {
            //先删除旧的文件
            File file1 = new File(file);
            
            if (file1.exists())
            {
                file1.delete();
            }
            
            HSSFWorkbook wb = new HSSFWorkbook();//建立新HSSFWorkbook对象
            fileOut = new FileOutputStream(file, false);
            
            String[] sheetNames = sheetNameList.split(",");
            for (String sheet : sheetNames)
            {
                wb.createSheet(sheet);// 建立新的sheet对象
            }
            
            wb.write(fileOut);//把Workbook对象输出到文件workbook.xls中
            fileOut.close();
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            closeStream(fileOut);
        }
    }
    
    
    
    /**
     * 增加新的sheet到excel表格 
     * 注意：新excel是通过创建新的文件的方式 运行的。
     * @param file
     * @param sheetName
     * @param list
     * @param append
     * @param deleteNoUseSheet [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static void addSheetToExcel(String file,String sheetName,List <String>list,boolean append,String deleteNoUseSheet)
    {
        try
        {
            File excel = new File(file);  // 读取文件
            String tempFile = excel.getAbsolutePath().replace(".xls", "temp.xls");
            String oldName = excel.getAbsolutePath();
            FileInputStream in = new FileInputStream(excel); // 转换为流
            HSSFWorkbook workbook = new HSSFWorkbook(in); // 加载excel的 工作目录
            
            int sheettempId = workbook.getSheetIndex(deleteNoUseSheet);
            
            if(sheettempId >= 0)
            {
                workbook.removeSheetAt(sheettempId);
            }
            
            HSSFSheet newSheet = workbook.createSheet(sheetName); // 添加一个新的sheet
            newSheet.setColumnWidth(0, 50 * 256);
            newSheet.setColumnWidth(1, 50 * 256);
            
            for(int i = 0; i<list.size();i++ )
            {
                String [] arrays = list.get(i).split(",");
                
                HSSFRow row = newSheet.createRow(i);// 建立新行
                
                for(int j = 0;j < arrays.length;j++)
                {
                    row.createCell(j).setCellValue(arrays[j]);
                }
            }
            
            File newTempFile = new File(tempFile);
            FileOutputStream fileOut = new FileOutputStream(newTempFile,true);
            workbook.write(fileOut);
            fileOut.close();
            in.close();
            
            //先删除原先的旧excel
            excel.delete();
            //把最新的excel 更名为原先旧的excel的名称
            File newExcel = new File(oldName);
            newTempFile.renameTo(newExcel);
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    
    /** 
     * 隐藏指定的Sheet 
     * @param targetFile  目标文件 
     * @param sheetName   Sheet名称 
     */
    public static void hiddenSheet(String targetFile, String sheetName)
    {
        try
        {
            FileInputStream fis = new FileInputStream(targetFile);
            HSSFWorkbook wb = new HSSFWorkbook(fis);
            //隐藏Sheet 
            wb.setSheetHidden(wb.getSheetIndex(sheetName), 1);
            fileWrite(targetFile, wb);
            fis.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    /** 
     * 删除指定的Sheet 
     * @param targetFile  目标文件 
     * @param sheetName   Sheet名称 
     */
    public static void deleteSheet(String targetFile, String sheetName)
    {
        try
        {
            FileInputStream fis = new FileInputStream(targetFile);
            HSSFWorkbook wb = new HSSFWorkbook(fis);
            //删除Sheet 
            wb.removeSheetAt(wb.getSheetIndex(sheetName));
            fileWrite(targetFile, wb);
            fis.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    /** 
     * 写隐藏/删除后的Excel文件 
     * @param targetFile  目标文件 
     * @param wb          Excel对象 
     * @throws Exception 
     */
    public static void fileWrite(String targetFile, HSSFWorkbook wb)
            throws Exception
    {
        FileOutputStream fileOut = new FileOutputStream(targetFile);
        wb.write(fileOut);
        fileOut.flush();
        fileOut.close();
    }
    
    public static void closeStream(Object obj)
    {
        if (null == obj)
        {
            return;
        }
        
        if (obj instanceof BufferedReader)
        {
            try
            {
                ((BufferedReader) obj).close();
                
            }
            catch (Exception e)
            {
                
            }
        }
        
        if (obj instanceof HttpURLConnection)
        {
            try
            {
                ((HttpURLConnection) obj).disconnect();
                
            }
            catch (Exception e)
            {
                
            }
        }
        
        if (obj instanceof BufferedWriter)
        {
            try
            {
                ((BufferedWriter) obj).close();
                
            }
            catch (Exception e)
            {
                
            }
        }
        
        //
        if (obj instanceof FileOutputStream)
        {
            try
            {
                ((BufferedWriter) obj).close();
                
            }
            catch (Exception e)
            {
                
            }
        }
        
        //
    }
    
    /**
     * 暂时不能用 有bug
     * <功能详细描述>
     * @param file
     * @param sheetName
     * @param list
     * @param append [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static void insertSheetToExcel(String file,String sheetName,List <String>list,boolean append )
    {
        try
        {
            
            File excel = new File(file);  // 读取文件
            FileInputStream in = new FileInputStream(excel); // 转换为流
            HSSFWorkbook workbook = new HSSFWorkbook(in); // 加载excel的 工作目录
            HSSFSheet newSheet = workbook.createSheet(sheetName); // 添加一个新的sheet
            
            for(int i = 0; i<list.size();i++ )
            {
                String [] arrays = list.get(i).split(",");
                
                HSSFRow row = newSheet.createRow(i);// 建立新行
                
                for(int j = 0;j < arrays.length;j++)
                {
                    row.createCell(j).setCellValue(arrays[j]);
                }
            }
            
            FileOutputStream fileOut = new FileOutputStream(new File(file),true);
            workbook.write(fileOut);
            fileOut.close();
            in.close();
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
}
