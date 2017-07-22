package com.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class GaudFileDeal
{
//    public static String POI_CITY ="shenzhen";
//    public static String POI_FILENAME ="GD_首批_南京市_14年12月26日 09时38分54秒(后六种类型)_总计_100872.csv";
//    
//    public static String POI_TABLE = "cg_poi_" + POI_CITY;
//    public static String POI_TABLE_OPT = "cg_poi_" + POI_CITY + "_OPT";
//    public static String POI_TABLE_TYPE = "cg_poi_type_" + POI_CITY;
//    public static String POI_TYPE_RELATION_TABLE = "cg_poi_type_relation_" + POI_CITY;
    
    
    public static String POI_FILENAME ="GD_江苏省_扬州市_14年12月28日 09时00分42秒_52_总计_163003.csv";
    
    public static String POI_TABLE = "cg_poi";
    public static String POI_TABLE_OPT = "cg_poi_opt";
    public static String POI_TABLE_TYPE = "cg_poi_type";
    public static String POI_TYPE_RELATION_TABLE = "cg_poi_type_relation";
    
    
    private static Map<String,GDTypeBean> type1map = new HashMap<String,GDTypeBean>();
    private static Map<String,GDTypeBean> type2map = new HashMap<String,GDTypeBean>();
    private static Map<String,GDTypeBean> type3map = new HashMap<String,GDTypeBean>();
    private static Map<String,GDTypeBean> typemap = new HashMap<String,GDTypeBean>();
    
    private static Map<String,String> cityCodeMap = new HashMap<String,String>();
    //不带省
    private static Map<String,String> cityTeleCodeMap = new HashMap<String,String>();
    //带省
    private static Map<String,String> cityProTeleCodeMap = new HashMap<String,String>();
    
    private static String[] levelOneKeys = new String[21];
    
    
    private static Integer index = 1;
    private static Integer indexForRelation = 1;
    
    
    static 
    {
        levelOneKeys[0] = "总计";
        levelOneKeys[1] = "餐饮服务";
        levelOneKeys[2] = "购物服务";
        levelOneKeys[3] = "生活服务";
        levelOneKeys[4] = "医疗保健服务";
        levelOneKeys[5] = "公司企业";
        levelOneKeys[6] = "体育休闲服务";
        levelOneKeys[7] = "住宿服务";
        levelOneKeys[8] = "汽车服务";
        levelOneKeys[9] = "科教文化服务";
        levelOneKeys[10] = "商务住宅";
        levelOneKeys[11] = "金融保险服务";
        levelOneKeys[12] = "汽车销售";
        levelOneKeys[13] = "汽车维修";
        levelOneKeys[14] = "摩托车服务";
        levelOneKeys[15] = "地名地址信息";
        levelOneKeys[16] = "交通设施服务";
        levelOneKeys[17] = "政府机构及社会团体";
        levelOneKeys[18] = "公共设施";
        levelOneKeys[19] = "风景名胜";
        levelOneKeys[20] = "道路附属设施";

    }
    
    /** <一句话功能简述>
     * <功能详细描述>
     * @param args [参数说明]
     * 
     * @return void [返回类型说明]
     * @throws InterruptedException 
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static void main(String[] args) throws InterruptedException
    {
        //stat("E:\\ysh\\高德\\抓取\\第二批\\山西省");
        //SimpleDateFormat myFmt=new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        //System.out.println(myFmt.format(new Date()));
        //System.out.print("11111");
        //System.out.print("\bA");
        //System.out.print("\b\b\b\b\b\b\b\b\b\b\b\b\b"+"11111");
        //System.out.print("\b\b\b\b\b\b\b\b\b\b\b\b\b"+"11111");
        //System.out.println("qqqqq测试好的".substring(0,"qqqqq测试好的".indexOf("测试")));
        //loadCityTeleCode();
        //runBatch("E:\\ysh\\高德\\抓取\\第一批\\typeToTables.csv","E:\\ysh\\高德\\抓取\\第一批\\原始文件","e:\\dest");
        
        //runBatch("E:\\typeToTables.csv","E:\\扬州","e:\\dest");
        
        runSingle();
        //System.out.println("风景名胜;风景名胜;风景名胜|生活服务;生活服务场所;生活服务场所".contains("|生活服务;"));
        //deal.genTypeOpt("e:\\guadData_20141214_dongguan_103702.csv");
        //deal.delDoubleDataForFile("e:\\guadData_20141216_bengbu_27158.csv");
        //System.out.println(subTypeOne("汽车服务;加油站;加德士"));
        
    }
    
    //===================================================统计=========================
    /**
     * 统计
     * <功能详细描述>
     * @param filePath [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static void stat(String filePath)
    {
        MultiOptHashMap multiMap = new MultiOptHashMap();
        
        File file = new File(filePath);
        BufferedReader read = null;
        BufferedWriter write = null;
        DataInputStream in = null;
        
        for(int i = 0;i < file.list().length;i++)
        {
            if (!(file.listFiles()[i]).isFile())
            {
                continue;
            }
            
            File file1 = (file.listFiles()[i]);
            
            try
            {
                in = new DataInputStream(new FileInputStream(file1));
                read= new BufferedReader(new InputStreamReader(in,"utf8"));
                
                String lineTxt = null;
                
               while((lineTxt = read.readLine()) != null)
               {
                   String[] values = lineTxt.split(",");
                   String types = subTypeOne(values[1]);
                   String citycode = values[6];
                   String areacode = values[7];
                   
                   //multiMap.put(multiMap.k("aaa",areacode,types), multiMap.v("aaa",areacode,types) + 1);
                   //multiMap.put(multiMap.k("aaa",areacode,"总计"), multiMap.v("aaa",areacode,"总计") + 1);
                   
                   //multiMap.put(multiMap.k(citycode,areacode,types), multiMap.v(citycode,areacode,types) + 1);
                   //multiMap.put(multiMap.k(citycode,areacode,"000000"), multiMap.v(citycode,areacode,"000000") + 1);
                   multiMap.put(multiMap.k(citycode,"000000",types), multiMap.v(citycode,"000000",types) + 1);
                   multiMap.put(multiMap.k(citycode,"000000","总计"), multiMap.v(citycode,"000000","总计") + 1);
               }
                
            }catch(Exception e)
            {
                
            }
            finally
            {
                try
                {
                    read.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                
            }
            
            System.out.println(file1.getAbsolutePath() + " finish sum.");
            
        }
        
        
        loadCityCode();
        loadCityTeleCode();
        
        try
        {
            write = new BufferedWriter(new FileWriter("E:\\stat.csv",false));
            
            System.out.print("城市,地区");
            write.write("城市,地区");
            
            for(String head : levelOneKeys )
            {
                System.out.print(","+head);
                write.write(","+head);
            }
            
            System.out.println();
            write.write("\n");
            
            String[] rootkeys = multiMap.getRoot();
            
            for(String key0 :rootkeys )
            {
                String[] keyArray1 =multiMap.sonKeys(key0);
                
                for(String key1 :keyArray1 )
                {
                    String areaName = "";
                    
                    if (key1.equals("000000"))
                    {
                        areaName = "total";
                    }
                    else
                    {
                        areaName = cityCodeMap.get(key1);
                    }
                    
                    System.out.print(cityTeleCodeMap.get(key0)+","+areaName);
                    write.write(cityTeleCodeMap.get(key0)+","+areaName);
                    
                    for(String key2 :levelOneKeys )
                    {
                        System.out.print(","+multiMap.v(key0,key1,key2));
                        write.write(","+multiMap.v(key0,key1,key2));
                    }
                    
                    System.out.println();
                    write.write("\n");
                }
            }
            
        }
        catch(Exception e)
        {
            
        }
        finally
        {
            try
            {
                write.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        
        
        
        
        
    }
    
    /**
     * 加载城市编码
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static void loadCityCode()
    {
        File file = new File("E://ysh//高德//设计//cityCode.csv");
        
        BufferedReader read = null;
        int index = 0;
        
        try
        {
            DataInputStream in = new DataInputStream(new FileInputStream(file));
            read = new BufferedReader(new InputStreamReader(in,"utf8"));
            String lineTxt = null;
            
            while((lineTxt = read.readLine()) != null)
            {
                index++;
                
                //跳过首行
                if (index == 1)
                {
                    continue;
                }
                
                String[] values = lineTxt.split(",");
                
                cityCodeMap.put(values[0], values[1]);
                
            }
        }catch(Exception e)
        {
            
        }
        finally
        {
            try
            {
                read.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        //cityCodeMap;
        
        
    }
    
    /**
     * 加载城市编码
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static void loadCityTeleCode()
    {
        File file = new File("E://ysh//高德//设计//cityCodePhone.csv");
        
        BufferedReader read = null;
        
        try
        {
            DataInputStream in = new DataInputStream(new FileInputStream(file));
            read = new BufferedReader(new InputStreamReader(in,"utf8"));
            String lineTxt = null;
            
            while((lineTxt = read.readLine()) != null)
            {
                //System.out.println(lineTxt);
                
                String[] values = lineTxt.split(",");
                String telecode = values[2].trim();
                
                if (telecode.equals("886"))
                {
                    telecode = "00"+telecode;
                }
                else if (telecode.length() == 3 )
                {
                    telecode = "0"+telecode;
                }
                else if (telecode.length() == 2 )
                {
                    telecode = "0"+telecode;
                }
                
                //if (telecode.equals("010"))
                //{
                //    System.out.println(telecode+":"+ values[0]+"省"+values[1]);
                //}
                
                cityTeleCodeMap.put(telecode, values[1]);
                cityProTeleCodeMap.put(telecode, values[0]+"省"+values[1]);
                
            }
        }catch(Exception e)
        {
            
        }
        finally
        {
            try
            {
                read.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        //cityCodeMap;
        
        
    }
    
    //cityTeleCodeMap
    
    
    public static String subTypeOne(String types)
    {
        return types.substring(0,types.indexOf(";"));
    }
    
    //==========================================批量生产脚本===========================================================
    
    /**
     * 批量生产脚本
     * <功能详细描述>
     * @param typeFileName
     * @param filePath
     * @throws InterruptedException [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    
    public static void runBatch(String typeFileName,String filePath,String destFilePath)throws InterruptedException
    {
        POI_TABLE = "cg_poi";
        POI_TABLE_OPT = "cg_poi_OPT";
        POI_TABLE_TYPE = "cg_poi_type";
        POI_TYPE_RELATION_TABLE = "cg_poi_type_relation";
        
        GaudFileDeal deal = new GaudFileDeal();
        
        deal.genScriptFileForType(typeFileName,destFilePath);
        
        deal.typeMapping(typeFileName);
        
        File file = new File(filePath);
        
        for(int i = 0;i < file.list().length;i++)
        {
            
//            if ((file.listFiles()[i]).isFile())
//            {
//                continue;
//            }
            
            File file1 = file.listFiles()[i];
            
            String srcFilename = file1.getAbsolutePath();
            
            deal.delDoubleDataForFile(srcFilename,destFilePath);
            
            Thread.sleep(2000);
            
            deal.genScriptFile(destFilePath+"\\"+file1.getName().replace(".csv", "single.csv"),destFilePath);
            
            //deal.genScriptFileOpt(destFilePath+"\\"+file1.getName().replace(".csv", "single.csv"),destFilePath);
            
            System.out.println(srcFilename +" finish.");
            
        }
        
        System.out.println("finish.");
    }
    
    /**
     * 单文件生成脚本
     * @throws InterruptedException [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static void runSingle() throws InterruptedException
    {
        
        GaudFileDeal deal = new GaudFileDeal();
        
        deal.genScriptFileForType("e:\\typeToTables.csv","e:");
        
        deal.typeMapping("e:\\typeToTables.csv");
        
        deal.delDoubleDataForFile("e:\\" + POI_FILENAME ,"e:");
        
        Thread.sleep(2000);
        
        deal.genScriptFile("e:\\" + POI_FILENAME.replace(".csv", "single.csv"),"e:");
        
        deal.genScriptFileOpt("e:\\" + POI_FILENAME.replace(".csv", "single.csv"),"e:");
        
        System.out.println("finish.");
    }
    
    public int genScriptFileForType(String gaudFileTypeName,String destFilePath)
    {
        File file = new File(gaudFileTypeName);
        String fileTemp = file.getName();
        fileTemp = fileTemp.replace("csv", "sql");
        String sqlHead = "INSERT INTO " + POI_TABLE_TYPE + " (id, name, pid, level, add_time, add_user, mark) VALUES\n";
        String sqlValues = "('{1}','{2}','{3}','{4}',1406011467,0,1)";
        BufferedReader read = null;
        BufferedWriter write = null;
        int index = 0;
        
        try
        {
            DataInputStream in = new DataInputStream(new FileInputStream(file));
            read= new BufferedReader(new InputStreamReader(in,"utf8"));
            //read = new BufferedReader(new FileReader(file));
            write = new BufferedWriter(new FileWriter(destFilePath+"\\"+fileTemp,false));
            
            String lineTxt = null;
            
             
             write.write(sqlHead);
             
            while((lineTxt = read.readLine()) != null)
            {
                index++;
                
                //跳过首行
                if (index == 1)
                {
                    continue;
                }
                
                //非首次 增加逗号和换行符号
                if (index > 2)
                {
                    write.write(",\n");
                }
                
                //String tempLine = new String(lineTxt.getBytes("ISO-8859-1"),"utf8");
                
                String[] values = lineTxt.split(",");
                
                String sqlvaluesTemp = sqlValues.replace("{1}", values[0]);
                sqlvaluesTemp = sqlvaluesTemp.replace("{2}", values[1]);
                sqlvaluesTemp = sqlvaluesTemp.replace("{3}", values[2]);
                sqlvaluesTemp = sqlvaluesTemp.replace("{4}", values[3]);
                
                write.write(sqlvaluesTemp);
            }
            
            write.write(";");
            
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                read.close();
            }
            catch(Exception e)
            {
                
            }
            
            try
            {
                write.close();
            }
            catch(Exception e)
            {
                
            }
        }
        
        return index;
        
    }
    
    /**
     * 生成脚本文件
     * @param fileName
     * @return [参数说明]
     * 
     * @return int [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public int genScriptFile(String fileName,String destFilePath)
    {
        File file = new File(fileName);
        String fileTemp = file.getName();
        String fileRelationType = file.getName();
        
        fileTemp = fileTemp.replace("csv", "sql");
        fileRelationType =fileRelationType.replace(".csv", "relationType.sql");
        
        String sqlHead = "INSERT INTO " + POI_TABLE + " (id,name, type, types, address, point, tel, citycode,areacode,add_user, add_time, state, mark) VALUES\n";
        String sqlValues = "({id},'{name}',0,'{types}','{address}','{point}','{tel}','{citycode}','{areacode}','0',0,0,1)";
        
        String sqlHeadTypeRelation = "INSERT INTO " + POI_TYPE_RELATION_TABLE + " (id,poi_id, type,add_user, add_time, mark) VALUES\n";
        String sqlValuesTypeRelation = "({id},{poi_id},{type},0,0,1)";
        
        BufferedReader read = null;
        BufferedWriter write = null;
        
        BufferedWriter writeRelationType = null;
        
        //index = 1;
        
        //int indexForRelation = 1;
        
        try
        {
            DataInputStream in = new DataInputStream(new FileInputStream(file));
            read= new BufferedReader(new InputStreamReader(in,"utf8"));
            //read = new BufferedReader(new FileReader(file));
            
            write = new BufferedWriter(new FileWriter(destFilePath+"\\"+fileTemp,false));
            
            writeRelationType = new BufferedWriter(new FileWriter(destFilePath+"\\"+fileRelationType,false));
            
            String lineTxt = null;
            int flag = 0;
            int oldPage = 0;
            int newPage = 0;
            int id =0;
            int countForPage = 0;
            
             write.write(sqlHead);
             
             writeRelationType.write(sqlHeadTypeRelation);
             
            while((lineTxt = read.readLine()) != null)
            {
                id++;
                //String tempLine = new String(lineTxt.getBytes("ISO-8859-1"),"utf8");
                lineTxt =lineTxt.replace("\\", "\\\\");
                lineTxt =lineTxt.replace("'", "''");
                
                String[] values = lineTxt.split(",");
                String nameDeal = values[0];
                String addressDeal = values[2];
                
                String sqlvaluesTemp = sqlValues.replace("{id}", String.valueOf(id));
                sqlvaluesTemp = sqlvaluesTemp.replace("{name}", nameDeal);
                sqlvaluesTemp = sqlvaluesTemp.replace("{types}", values[1]);
                sqlvaluesTemp = sqlvaluesTemp.replace("{address}", addressDeal);
                sqlvaluesTemp = sqlvaluesTemp.replace("{point}", values[3]+","+values[4]);
                sqlvaluesTemp = sqlvaluesTemp.replace("{tel}", values[5]);
                sqlvaluesTemp = sqlvaluesTemp.replace("{citycode}", values[6]);
                
                if (values.length >= 9)
                {
                    sqlvaluesTemp = sqlvaluesTemp.replace("{areacode}", values[7]);
                    //sqlvaluesTemp = sqlvaluesTemp.replace("{typecode}", values[8]);
                }
                else
                {
                    sqlvaluesTemp = sqlvaluesTemp.replace("{areacode}", "0");
                    //sqlvaluesTemp = sqlvaluesTemp.replace("{typecode}", "0");
                }
               
                //处理业态类型的对应关系表
                String types =values[1];
                GDTypeBean bean = getTypeInfoByTypes(types);
                String sqlvaluesTempRelation = sqlValuesTypeRelation.replace("{id}", String.valueOf(indexForRelation++));
                sqlvaluesTempRelation = sqlvaluesTempRelation.replace("{poi_id}", String.valueOf(id));
                sqlvaluesTempRelation = sqlvaluesTempRelation.replace("{type}", bean.getTypeId3());
                
                
                //新开启一页 先写表头
                if(oldPage < newPage)
                {
                    write.write(sqlHead);
                    writeRelationType.write(sqlHeadTypeRelation);
                    oldPage = newPage;
                }
                else
                {
                    write.write(",\n");
                    writeRelationType.write(",\n");
                }
                
                writeRelationType.write(sqlvaluesTempRelation);
                write.write(sqlvaluesTemp);
                countForPage++;
                
                if (countForPage % 4000 == 0 && countForPage > 0)
                {
                    write.write(";\n");
                    writeRelationType.write(";\n");
                    countForPage = 0;
                    newPage++;
                }
            }
            
            write.write(";");
            writeRelationType.write(";");
            
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                read.close();
            }
            catch(Exception e)
            {
                
            }
            
            try
            {
                write.close();
                writeRelationType.close();
            }
            catch(Exception e)
            {
                
            }
        }
        
        return index;
    }
    
    public GDTypeBean getTypeInfoByTypes(String types)
    {
        GDTypeBean typebean = null;
        String []typesArr = types.split("\\|");
        
        for (int i = 0 ;i < typesArr.length;i++ )
        {
            if (typemap.containsKey(typesArr[i]))
            {
                typebean=  typemap.get(typesArr[i]);
                break;
            }
        }
        
        return typebean;
        
    }
    
    /**
     * 
     */
    public boolean judgeType(String types)
    {
        
        //购物服务|餐饮服务|生活服务|住宿服务|医疗保健服务|汽车维修|体育休闲服务|汽车销售|科教文化服务|汽车服务|公司企业|金融保险服务|摩托车服务|商务住宅
        if (types.contains("|购物服务;")
                ||types.contains("|餐饮服务;")
                ||types.contains("|生活服务;")
                ||types.contains("|住宿服务;")
                ||types.contains("|医疗保健服务;")
                ||types.contains("|汽车维修;")
                ||types.contains("|体育休闲服务;")
                ||types.contains("|汽车销售;")
                ||types.contains("|科教文化服务;")
                ||types.contains("|汽车服务;")
                ||types.contains("|公司企业;")
                ||types.contains("|金融保险服务;")
                ||types.contains("|摩托车服务;")
                ||types.contains("|商务住宅;")
                )
        {
            return false;
        }
        
        if (types.startsWith("风景名胜;")
                ||types.startsWith("公共设施;")
                ||types.startsWith("政府机构及社会团体;")
                ||types.startsWith("地名地址信息;")
                ||types.startsWith("道路附属设施;")
                ||types.startsWith("交通设施服务;"))
        {
                               
                return true;
        }
        
        return false;
    }
    
    /**
     * 生成脚本文件
     * @param fileName
     * @return [参数说明]
     * 
     * @return int [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public int delDoubleDataForFile(String fileName,String destFilePath)
    {
        File file = new File(fileName);
        String fileTemp = file.getName();
        fileTemp = fileTemp.replace(".csv", "single.csv");
        BufferedReader read = null;
        BufferedWriter write = null;
        int index = 0;
        Map<String,String> map = new HashMap<String,String>();
        
        try
        {
            DataInputStream in = new DataInputStream(new FileInputStream(file));
            read= new BufferedReader(new InputStreamReader(in,"utf8"));
            
            Writer outW = new OutputStreamWriter(new FileOutputStream(destFilePath+"\\"+fileTemp,false), "UTF-8");
            write = new BufferedWriter(outW);
            
            String lineTxt = null;
             
            while((lineTxt = read.readLine()) != null)
            {
                String[] values = lineTxt.split(",");
                map.put(values[0]+","+values[3]+","+values[4], lineTxt);
            }
            
            Iterator it= map.keySet().iterator();
            
            while(it.hasNext())  
            {  
               String point=(String)it.next();  
               write.write(map.get(point)+"\n");
            }  
            
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                read.close();
            }
            catch(Exception e)
            {
                
            }
            
            try
            {
                write.close();
            }
            catch(Exception e)
            {
                
            }
        }
        
        return index;
    }
    
    
    public void typeMapping(String gaudFileTypeName)
    {
        type1map.clear();
        type2map.clear();
        type3map.clear();
        
        File file = new File(gaudFileTypeName);
       
        BufferedReader read = null;
        int index = 0;
        
        try
        {
            DataInputStream in = new DataInputStream(new FileInputStream(file));
            read= new BufferedReader(new InputStreamReader(in,"utf8"));
            
            String lineTxt = null;
             
            while((lineTxt = read.readLine()) != null)
            {
                index++;
                
                //跳过首行
                if (index == 1)
                {
                    continue;
                }
                
                String[] values = lineTxt.split(",");
                
                if (values[3].equals("0"))
                {
                    GDTypeBean bean = new GDTypeBean();
                    bean.setTypeId1(values[0]);
                    bean.setTypeName1(values[1]);
                    bean.setPid(values[2]);
                    type1map.put(values[0], bean);
                    //System.out.println(values[0]+" "+values[0]+"  "+values[1]+"  "+values[2]);
                }
                
                if (values[3].equals("1"))
                {
                    GDTypeBean bean = new GDTypeBean();
                    bean.setTypeId2(values[0]);
                    bean.setTypeName2(values[1]);
                    bean.setPid(values[2]);
                    type2map.put(values[0], bean);
                    //System.out.println(values[0]+" "+values[0]+"  "+values[1]+"  "+values[2]);
                }
                
                if (values[3].equals("2"))
                {
                    GDTypeBean bean = new GDTypeBean();
                    bean.setTypeId3(values[0]);
                    bean.setTypeName3(values[1]);
                    bean.setPid(values[2]);
                    type3map.put(values[0], bean);
                    //System.out.println(values[0]+" "+values[0]+"  "+values[1]+"  "+values[2]);
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                read.close();
            }
            catch(Exception e)
            {
                
            }
        }
        
        dealTypeMapping();
        
    }
    
    
    private void dealTypeMapping()
    {
        typemap.clear();
        
        Iterator type1It= type1map.keySet().iterator();
        
        while(type1It.hasNext())  
        {  
           String key1=(String)type1It.next();  
           GDTypeBean bean1 = type1map.get(key1);
           
           Iterator type2It= type2map.keySet().iterator();
           
           while(type2It.hasNext())  
           { 
               String key2=(String)type2It.next();  
               GDTypeBean bean2 = type2map.get(key2);
               
               if (bean1.getTypeId1().equals(bean2.getPid()))
               {
                   Iterator type3It= type3map.keySet().iterator();
                   
                   while(type3It.hasNext())  
                   { 
                       String key3=(String)type3It.next();  
                       GDTypeBean bean3 = type3map.get(key3);
                       
                       if(bean2.getTypeId2().equals(bean3.getPid()))
                       {
                           GDTypeBean bean = new GDTypeBean();
                           bean.setTypeId1(bean1.getTypeId1());
                           bean.setTypeId2(bean2.getTypeId2());
                           bean.setTypeId3(bean3.getTypeId3());
                           bean.setTypeName1(bean1.getTypeName1());
                           bean.setTypeName2(bean2.getTypeName2());
                           bean.setTypeName3(bean3.getTypeName3());
                           String typekey = bean1.getTypeName1()+";"+bean2.getTypeName2() +";"+bean3.getTypeName3();
                           
                           typemap.put(typekey, bean);
                           //System.out.println(typekey+" "+bean.getTypeId3()+"  "+bean.getTypeName3());
                       }
                   }
               }
               
           }
           
        }  
        
    }
    
    
    public void genTypeOpt(String fileName)
    {
        File file = new File(fileName);
        String fileTemp = fileName;
        fileTemp = fileTemp.replace(".csv", "_POTtype.sql");
        String sqlHead = "INSERT INTO cg_poitypes (id,typeId, typesname,mark) VALUES\n";
        String sqlValues = "({id},'{1}','{2}',1)";
        BufferedReader read = null;
        BufferedWriter write = null;
        int index = 0;
        HashMap<String,String> map = new HashMap<String,String>();
        
        
        try
        {
            DataInputStream in = new DataInputStream(new FileInputStream(file));
            read= new BufferedReader(new InputStreamReader(in,"utf8"));
            //read = new BufferedReader(new FileReader(file));
            write = new BufferedWriter(new FileWriter(fileTemp,false));
            
            String lineTxt = null;
            int flag = 0;
             
             write.write(sqlHead);
             
            while((lineTxt = read.readLine()) != null)
            {
                 
                lineTxt =lineTxt.replace("\\", "\\\\");
                lineTxt =lineTxt.replace("'", "''");
                
                String[] values = lineTxt.split(",");
                String typescode = values[8];
                
                String types =values[1];
                
                String []typesArr = types.split("\\|");
                String []typescodeArr = typescode.split("\\|");
                
                for (int i = 0 ;i < typesArr.length;i++ )
                {
                    map.put(typescodeArr[i],typesArr[i]);
                }
                
            }
            
            Set<String> set = map.keySet();
            Object[] objects = set.toArray();
            Arrays.sort(objects);
            
            for(int j = 0;j<objects.length;j++)
            {
                String key = (String)objects[j];
                String value = map.get(key);
                
                write.write(key + "," + value+"\n");
            }
            
            
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                read.close();
            }
            catch(Exception e)
            {
                
            }
            
            try
            {
                write.close();
            }
            catch(Exception e)
            {
                
            }
        }
        
        return ;
    }
    
    public void genTypeOpt1(String fileName)
    {
        File file = new File(fileName);
        String fileTemp = fileName;
        fileTemp = fileTemp.replace(".txt", "_1.txt");
        BufferedReader read = null;
        BufferedWriter write = null;
        int index = 0;
        HashMap<String,String> map = new HashMap<String,String>();
        
        
        try
        {
            DataInputStream in = new DataInputStream(new FileInputStream(file));
            read= new BufferedReader(new InputStreamReader(in,"utf8"));
            //read = new BufferedReader(new FileReader(file));
            write = new BufferedWriter(new FileWriter(fileTemp,false));
            
            String lineTxt = null;
            int flag = 0;
             
            while((lineTxt = read.readLine()) != null)
            {
                 
                
                //map.put();
                
            }
            
            Set<String> set = map.keySet();
            Object[] objects = set.toArray();
            Arrays.sort(objects);
            
            for(int j = 0;j<objects.length;j++)
            {
                String key = (String)objects[j];
                String value = map.get(key);
                
                write.write(key + "," + value+"\n");
            }
            
            
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                read.close();
            }
            catch(Exception e)
            {
                
            }
            
            try
            {
                write.close();
            }
            catch(Exception e)
            {
                
            }
        }
        
        return ;
    }
    
    /**
     * 生成脚本文件
     * @param fileName
     * @return [参数说明]
     * 
     * @return int [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public int genScriptFileOpt(String fileName,String destFilePath)
    {
        File file = new File(fileName);
        String fileTemp = file.getName();
        fileTemp = fileTemp.replace(".csv", "_POT.sql");
        String sqlHead = "INSERT INTO " + POI_TABLE_OPT + " (id,name, type, types, address, point, tel, citycode , add_user, add_time, state,type1,type1name,type2,type2name,type3,type3name,mark) VALUES\n";
        String sqlValues = "({id},'{1}',0,'{2}','{3}','{4}','{5}','{6}','0',0,0,{11},'{12}',{13},'{14}',{15},'{16}',1)";
        BufferedReader read = null;
        BufferedWriter write = null;
        int index = 0;
        
        try
        {
            DataInputStream in = new DataInputStream(new FileInputStream(file));
            read= new BufferedReader(new InputStreamReader(in,"utf8"));
            //read = new BufferedReader(new FileReader(file));
            write = new BufferedWriter(new FileWriter(destFilePath+"\\"+fileTemp,false));
            
            String lineTxt = null;
            int flag = 0;
             
             write.write(sqlHead);
             
            while((lineTxt = read.readLine()) != null)
            {
                index++;
                flag++;
                
                //非首次 增加逗号和换行符号
                if (flag > 1)
                {
                    write.write(",\n");
                }
                
                //String tempLine = new String(lineTxt.getBytes("ISO-8859-1"),"utf8");
                lineTxt =lineTxt.replace("\\", "\\\\");
                lineTxt =lineTxt.replace("'", "''");
                
                String[] values = lineTxt.split(",");
                String nameDeal = values[0];
                String addressDeal = values[2];
                
                String sqlvaluesTemp = sqlValues.replace("{id}", String.valueOf(index));
                sqlvaluesTemp = sqlvaluesTemp.replace("{1}", nameDeal);
                sqlvaluesTemp = sqlvaluesTemp.replace("{2}", values[1]);
                sqlvaluesTemp = sqlvaluesTemp.replace("{3}", addressDeal);
                sqlvaluesTemp = sqlvaluesTemp.replace("{4}", values[3]+","+values[4]);
                sqlvaluesTemp = sqlvaluesTemp.replace("{5}", values[5]);
                sqlvaluesTemp = sqlvaluesTemp.replace("{6}", values[6]);
                
                String types =values[1];
                
                String []typesArr = types.split("\\|");
                
                for (int i = 0 ;i < typesArr.length;i++ )
                {
                    if (typemap.containsKey(typesArr[i]))
                    {
                        GDTypeBean bean = typemap.get(typesArr[i]);
                        
                        sqlvaluesTemp = sqlvaluesTemp.replace("{11}", bean.getTypeId1());
                        sqlvaluesTemp = sqlvaluesTemp.replace("{12}", bean.getTypeName1());
                        sqlvaluesTemp = sqlvaluesTemp.replace("{13}", bean.getTypeId2());
                        sqlvaluesTemp = sqlvaluesTemp.replace("{14}", bean.getTypeName2());
                        sqlvaluesTemp = sqlvaluesTemp.replace("{15}", bean.getTypeId3());
                        sqlvaluesTemp = sqlvaluesTemp.replace("{16}", bean.getTypeName3());
                        
                        break;
                    }
                }
                 
                write.write(sqlvaluesTemp);
                
                if (index % 2400 == 0)
                {
                    write.write(";\n");
                    write.write(sqlHead);
                    flag = 0;
                }
            }
            
            write.write(";");
            
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                read.close();
            }
            catch(Exception e)
            {
                
            }
            
            try
            {
                write.close();
            }
            catch(Exception e)
            {
                
            }
        }
        
        return index;
    }
    
    
    class GDTypeBean
    {
        private String typeId1;
        private String typeName1;
        private String typeId2;
        private String typeName2;
        private String typeId3;
        private String typeName3;
        private String pid;
        
        public String getTypeId1()
        {
            return typeId1;
        }
        public void setTypeId1(String typeId1)
        {
            this.typeId1 = typeId1;
        }
        public String getTypeName1()
        {
            return typeName1;
        }
        public void setTypeName1(String typeName1)
        {
            this.typeName1 = typeName1;
        }
        public String getTypeId2()
        {
            return typeId2;
        }
        public void setTypeId2(String typeId2)
        {
            this.typeId2 = typeId2;
        }
        public String getTypeName2()
        {
            return typeName2;
        }
        public void setTypeName2(String typeName2)
        {
            this.typeName2 = typeName2;
        }
        public String getTypeId3()
        {
            return typeId3;
        }
        public void setTypeId3(String typeId3)
        {
            this.typeId3 = typeId3;
        }
        public String getTypeName3()
        {
            return typeName3;
        }
        public void setTypeName3(String typeName3)
        {
            this.typeName3 = typeName3;
        }
        public String getPid()
        {
            return pid;
        }
        public void setPid(String pid)
        {
            this.pid = pid;
        }
        
    }
    
}
