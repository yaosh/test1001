package com.tools.genName;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenName
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
        //List<Integer> list = new ArrayList<Integer>();
        //list.add(1);
        
        //System.out.println(list.contains(1));
        
        GenName gen = new GenName();
        gen.run();
        
    }
    
    private void run()
    {
        getNameNums();
        
    }
    
    private List<Integer> getGoodNumList()
    {
        List<Integer> list = new ArrayList<Integer>();
        
        String goodnums = "1,3,5,6,7,8,11,13,15,16,17,18,21,23,24,25,29,31,32,33,35,37,39,41,45,47,48,52,55,57,61,63,65,67,68,81";
        String[] goodNumArray = goodnums.split(",");
        
        for(String tempnum :goodNumArray )
        {
            list.add(Integer.valueOf(tempnum));
        }
        
        return list;
        
    }
    
    private void getNameNums()
    {
        
        BufferedWriter writeHandle = null;
        
        
        try
        {
            writeHandle = new BufferedWriter(new FileWriter("d://ysh//个人//起名//names.csv" ,false));
            
            List<Integer> list = getGoodNumList();
            Map <Integer,String> map = getHanzhiMap();
            
            for(int second = 1;second<=81;second++)
            {
                for(int third = 1;third<=81;third++)
                {
                    int tianGe = 9 + 1;
                    int diGe = 0;
                    int renGe = 0;
                    int waiGe = 0;
                    int zongGe = 0;
                    String secondeName = "";
                    String thirdName = "";
                    
                    renGe =  9 + second;
                    diGe = second + third;
                    waiGe = third +1;
                    zongGe = 9 + second + third;
                    
                    if(second > 30 || third > 30 )
                    {
                        continue;
                    }
                    
                    if (!(list.contains(renGe) && list.contains(diGe)
                            && list.contains(waiGe) && list.contains(zongGe) && zongGe <= 81))
                    {
                        continue;
                    }
                    
                    secondeName = map.get(second);
                    thirdName = map.get(third);
                    
                    if(null == secondeName || "".equals(secondeName))
                    {
                        continue;
                    }
                    
                    if(null == thirdName || "".equals(thirdName))
                    {
                        continue;
                    }
                    
                    //System.out.println("9+"+second+"+"+third);
                    //System.out.println("姚+"+secondeName+"+"+thirdName);
                    //计算人格
                    if( !(renGe%10 == 2 && diGe%10 == 2) && !( renGe%10 == 2 && diGe%10 == 6) && !(renGe%10 == 8 && diGe%10 == 6))
                    {
                        continue;
                    }
                    
                    
                    String[] seconds = secondeName.split(",");
                    String [] thirds = thirdName.split(",");
                    
                    for(int a = 0;a <seconds.length;a++ )
                    {
                        for(int b = 0;b <thirds.length;b++ )
                        {
                            //System.out.println("姚+"+seconds[a]+"+"+thirds[b]);
                            writeHandle.write("姚+"+seconds[a]+"+"+thirds[b]+"\n");
                        }
                    }
                }
                
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            closeStream(writeHandle);
        }
        
        
    }
    
    private Map<Integer,String> getHanzhiMap()
    {
        Map <Integer,String> map = new HashMap<Integer,String>();
        //map.put(2, "乃,人");
        map.put(4, "丹,天,中,王,予");
        map.put(5, "叮,冬,令,冉,田,戊,矽,央");
        map.put(6, "安,衣,羽");
        map.put(7, "伶,辰,玙,攸,彤,廷,佟,良,妆,杉,言,贝,伯,池,汐");
        map.put(8, "念,宛,夜,依,咏,昀");
        map.put(9, "帝,玦,昱,昭,贞,祉,怡,禹,音,奏,星,虹,九,柳,怩,昵,芊,彦,奕,弈,羿,禺,飞,红,泓,宦,玫,眉,美,昧,勉,盼,品,屏,香,妍,盈,俐,昭");
        map.put(10, "晋,珏,朗,玲,瓴,朔,唐");
        map.put(12, "采,琅,琉,婼,婷,焱,轶,媛,堤,画,砚");
        map.put(14, "裳,祯,玮");
        map.put(15, "缔,蝶,乐,瑭,瑶,嶓,墀,蝴,叶,逸,影,缘,阅");
        map.put(16, "燔,瑾,璃,骆,陶,蹄,醍,熹,晓,璇,璋,衡,遐,燕,颐,豫,鸳");
        map.put(17, "黛,遥,阳,忆,屿");
        return map;
        
    }
    
    private Map<Integer,String> getHanzhiMapTwo()
    {
        Map <Integer,String> map = new HashMap<Integer,String>();
        map.put(2, "丁,乃,人,力");
        map.put(4, "戈,仁,兮,心,孔,木,元,月,匀,文,方,分,少");
        map.put(5, "册,申,生,仕,仙,司,正,甘,可,玉,札,本,古,卉,加,白,禾,叮,尼,冉,田,召,矽,央,仔");
        map.put(6, "臣,如,州,舟,字,朱,竹,共,吉,曲,戎聿,百,冰,帆,仿,妃,合,名,安,衣,羽");
        map.put(7, "妆,姒,宋,秀,君,吟,杏,言,妤,妘,伶,坊,坩,牡,攸");
        map.put(8, "初,侏,金,青,抒,周,季,佳,宜,枝,汶,非,宓,明,沐,佩,佰,典,昕,知,夜,依,咏");
        map.put(9, "秋,思,星,俞,姿,芏,柑,癸,虹,芃,芊,奕,弈,飞,玢,封,红,泓,狐,皇,眉,美,沫,品,屏,盈,妍,帝,俐,怜,娜,南,贞,祉,籽,怡,舣,姻,音");
        map.put(10, "睬,敇,晁,祠,倪,倩,师,书,纾,芳,芬,粉,芙,哥,格,桂,花,家,珂,倚,芸,祗,芷,娥,洱,肪,纺,舫,珏,玲,瓴,秦,偌,唐,胭,宴,晏");
        map.put(12, "词,钫,舒,傒,犀,象,琇,琇,茱,皓,景,珺,茹,皖,尧,傜,琀,荆,媚,寐,淼,采,琉,硫,岚,砚,堰");
        map.put(14, "铢,慈,粹,翠,铭,瑜,歌,菡,嘉,侨,箐,碧,凤,阁,华,萍,裳,蜿,绾,腕,祯,嫣,瑕");
        map.put(15, "锕,钡,婵,赐,靓,锍,糅,锐,腮,赏,琐,锑,铤,儇,挚,锃,稹,帜,葆,萹,苇,蒍,著,蒂,樊,葑,瑰,荭,篌,葫,槲,篁,蝗,稷,葭,价,稼,俭,槿,儆,葵,醌,楼,模,耦,篇,萩,葶,葳,妩,葸,贤,缃,萱,仪,谊,莹,慧,涟,玛,码,祃,墨,慕,暮,霈,霆,霄,勰,葩,翦,漩,漾,漪,颍,渔,皑,僾,彻,褚,儋,驻,腠,德,缔,蝶,董,缎,乐,黎,鲁,鼐,侬,驽,骀,瑭,腽,瑶,熠,赭,阵,征,诤,禚,辎,墀,纬,卫,蝴,欧,磐,娴,叶,亿,逸,影,慵,缘,阅");
        map.put(16, "璁,璀,雕,辐,锦,静,谖,铮,穆,錡,钱,儒,锡,羲,觎,谕,憎,甑,战,诸,锱,蓓,蓖,篦,橙,桦,隍,憬,橘,梦,蒲,黔,桥,憔,樵,亲,磬,蓉,穑,树,蒴,樨,橡,筱,啸,谐,谚,荫,萦,蓥,颖,筑,篆,醐,寰,涧,洁,霖,默,谋,霓,凝,潘,霈,润,兴,学,鄅,沄,灯,谛,琏,璇,臻,璃,历,陵,龙,卢,陆,陶,蹄,醍,熹,晓,焰,鸯,晔,燚,璋,撰,嫒,谙,壁,遐,燕,颐,壅,豫,鸳,酝");
        map.put(17, "禅,黜,聪,锾,徽,键,骏,锴,链,镅,镁,孺,擅,声,谥,蟀,锶,蟋,戏,鲜,斋,锗,莲,敛,簏,蔓,茑,蹊,擎,檐,荫,岳,澹,璠,鸿,弥,皤,璞,禧,霞,黛,爵,隆,璐,谣,遥,繇,择,鸷,礁,岭,嵘,阳,忆,翳,隅,屿");
        map.put(20, "馨,蓝,篮,栊,骞,琼,薰,橼,瀚,鹕,怀,泷,鹛,宝,郸,龄,胧,曦,耀,赢,邺");
        map.put(22, "鉴,袭,蔼,蔺,茏,笼,苹,权,苏,蕴,骅,欢,霁,叠,读,峦,鸥");
        map.put(23, "兰,椤,鹭,栾,缨");
        map.put(24, "蚕,鑫,霭,蓠,篱,灵,陇,艳,呓");
        map.put(26, "湾,郦");
        map.put(28, "棂,鹦");
        map.put(30, "骉,鹂,鸾");
        return map;
        
    }
    
    public  void closeStream(Object obj)
    {
        if(null == obj)
        {
            return;
        }
        
        if (obj instanceof BufferedReader)
        {
            try
            {
                ((BufferedReader)obj).close();
                
            }
            catch (Exception e)
            {
                
            }
        }
        
        if (obj instanceof HttpURLConnection)
        {
            try
            {
                ((HttpURLConnection)obj).disconnect();
                
            }
            catch (Exception e)
            {
                
            }
        }
        
        if (obj instanceof BufferedWriter)
        {
            try
            {
                ((BufferedWriter)obj).close();
                
            }
            catch (Exception e)
            {
                
            }
        }
        
        
        //
    }
    
}
