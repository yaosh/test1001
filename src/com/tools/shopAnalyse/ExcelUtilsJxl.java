package com.tools.shopAnalyse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import jxl.Workbook;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableWorkbook;

public class ExcelUtilsJxl
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
    
    /**
     * 创建一个空的excel
     * <功能详细描述>
     * @param list
     * @param filePath
     * @param writeType
     * @param defaultsheetName [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static void createNewNullExcelFile(List <String>list,String filePath,boolean writeType,String defaultsheetName)
    {
        //写文件
        OutputStream os=null;
        WritableWorkbook wwb = null;
        
        try
        {
            os = new FileOutputStream(filePath,false);
            wwb = Workbook.createWorkbook(os);
            wwb.createSheet(defaultsheetName,0);
            wwb.write();
            os.close();
            wwb.close();
             
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            
        }
    }
    
    /**
     * 创建一个excel并写入一个sheet数据
     * <功能详细描述>
     * @param list
     * @param filePath
     * @param writeType
     * @param sheetName [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static void CreateExcelFileAndwrite(List <String>list,String filePath,boolean writeType,String sheetName)
    {
        //写文件
        OutputStream os=null;
        WritableWorkbook wwb = null;
        
        try
        {
            jxl.write.WritableSheet ws = null;
            
            os = new FileOutputStream(filePath,false);
            
            //首先要使用Workbook类的工厂方法创建一个可写入的工作薄(Workbook)对象
            wwb = Workbook.createWorkbook(os);
            ws = wwb.createSheet(sheetName, 1);
            
            WritableFont wfc = new WritableFont(WritableFont.ARIAL,20,WritableFont.NO_BOLD,false,UnderlineStyle.NO_UNDERLINE,jxl.format.Colour.GREEN);
            
            WritableCellFormat wcfFC = new WritableCellFormat(wfc);
            wcfFC.setBackground(Colour.DEFAULT_BACKGROUND);//设置单元格的颜色为红色 
            
            for(int i = 0; i<list.size();i++ )
            {
                String [] arrays = list.get(i).split(",");
                
                for(int j = 0;j < arrays.length;j++)
                {
                    Label label = new Label(j, i, arrays[j],wcfFC);
                    ws.addCell(label);
                }
            }
            
            wwb.write();
            os.close();
            wwb.close();
             
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            
        }
    }
    
    /**
     * 向已经存在的excel中插入新的sheet
     * @param list
     * @param filePath
     * @param writeType
     * @param sheetName [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static void addSheetToExcelFile(List <String>list,String filePath,boolean writeType,String sheetName)
    {
        //写文件
        //FileInputStream in = null;
        WritableWorkbook wwb = null;
        Workbook rwb = null;
        
        
        try
        {
            
            File excel = new File(filePath);  // 读取文件
            String tempFile = excel.getAbsolutePath().replace(".xls", "temp.xls");
            String oldName = excel.getAbsolutePath();
            
            rwb = Workbook.getWorkbook(new File(filePath));
            wwb = Workbook.createWorkbook(new File(tempFile), rwb);
            
            jxl.write.WritableSheet ws = null;
            ws = wwb.createSheet(sheetName, 1);
            
            WritableFont wfc = new WritableFont(WritableFont.ARIAL,20,WritableFont.NO_BOLD,false,UnderlineStyle.NO_UNDERLINE,jxl.format.Colour.GREEN);
            
            WritableCellFormat wcfFC = new WritableCellFormat(wfc);
            wcfFC.setBackground(Colour.DEFAULT_BACKGROUND);//设置单元格的颜色为红色 
            
            for(int i = 0; i<list.size();i++ )
            {
                String [] arrays = list.get(i).split(",");
                
                for(int j = 0;j < arrays.length;j++)
                {
                    Label label = new Label(j, i, arrays[j],wcfFC);
                    ws.addCell(label);
                }
            }
            
            wwb.write();
            wwb.close();
            rwb.close();
            
            //先删除原先的旧excel
            excel.delete();
            //把最新的excel 更名为原先旧的excel的名称
            File newTempFile = new File(tempFile);
            File newExcel = new File(oldName);
            newTempFile.renameTo(newExcel);
             
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            
        }
        
    }
    
}
