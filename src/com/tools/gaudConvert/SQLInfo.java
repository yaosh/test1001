package com.tools.gaudConvert;

public class SQLInfo
{
    
    /**
     #高德数据 获取高德一级，二级 三级业态的关系
      select id,pid,ppid,allname,ppname,pname,name,1 from
     (
        select t3.id,t3.pid,t2.pid ppid,concat(t2.name,';', t3.name) allname,t2.ppname,t2.pname,t3.name,1 from cg_poi_type t3 left join
        (select t.id,t.pid,concat(t1.name,';',t.name) name ,t1.name ppname,t.name pname
           from cg_poi_type t left join cg_poi_type t1 on t1.id = t.pid where t.level = 1) t2
        on t2.id = t3.pid
        where t3.level = 2
        order by id
      ) t4
      order by t4.ppid,t4.pid,t4.id;
  
     ############查询品牌 按照所有三级业态和一级，二级排序
 select tt.id1,tt.id2,tt.id3,tt.name1,tt.name2,tt.name3,REPLACE(REPLACE(b.name, CHAR(10), ''), CHAR(13), '')  brandName,REPLACE(REPLACE(b.eng_name, CHAR(10), ''), CHAR(13), '') eng_name,b.id 
  from cg_brand_type  t,
  cg_brand b,
  (
      select t3.name1,t3.name2,t1.name name3,t3.id1,t3.id2,t1.id id3 from cg_brand_style t1,
    (
        select r.name name1,s.name name2,s.id id2,r.id id1 from cg_shop_type s ,cg_shop_type_relation r
        where 
      (
         locate(concat(',',s.id,','),r.child_ids) > 0
           or 
           locate(concat('A',s.id,','),concat('A',r.child_ids)) > 0
           or
           locate(concat(',',s.id,'A'),concat(r.child_ids,'A')) > 0                 
      )
       and s.mark = 1 and r.mark = 1
    ) t3
    where t1.type = t3.id2
    ) tt
  
  where b.id = t.brand_id
  and t.type =tt.id3
  and t.mark = 1 and b.mark = 1
  order by tt.id1,tt.id2,tt.id3; 
  
  
  #含有三级的情况下 所有三级业态和一级，二级的关系 
 select t3.name1,t3.name2,t1.name name3,t3.id1,t3.id2,t1.id id3 from cg_brand_style t1,
(
    select r.name name1,s.name name2,s.id id2,r.id id1 from cg_shop_type s ,cg_shop_type_relation r
    where 
    (
         locate(concat(',',s.id,','),r.child_ids) > 0
           or 
           locate(concat('A',s.id,','),concat('A',r.child_ids)) > 0
           or
           locate(concat(',',s.id,'A'),concat(r.child_ids,'A')) > 0                 
     )
     and s.mark = 1
     order by r.name
 ) t3
 where t1.type = t3.id2
 order by id1,id2,id3;
 
 #查询品牌 按照所有一级，二级排序
 select t1.oneid,t1.twoid,t1.onename,t1.towname,REPLACE(REPLACE(b.name, CHAR(10), '') , CHAR(13), '') name,REPLACE(REPLACE(b.eng_name, CHAR(10), ''), CHAR(13), '') eng_name,b.id
  from cg_brand_type  t,
  cg_brand b,
  (
      select r.name onename,s.name towname,r.id oneid,s.id twoid from cg_shop_type s ,cg_shop_type_relation r
      where 
      (
         locate(concat(',',s.id,','),r.child_ids) > 0
           or 
           locate(concat('A',s.id,','),concat('A',r.child_ids)) > 0
           or
           locate(concat(',',s.id,'A'),concat(r.child_ids,'A')) > 0                 
      )
      and s.mark = 1 and r.mark = 1
      order by r.name
   ) t1
  
  where b.id = t.brand_id
  and t.type =t1.twoid
  and t.mark = 1 and b.mark = 1
  order by t1.oneid,t1.twoid,char_length(trim(b.name)) desc; 
  
  #所有一级，二级的关系
 select r.name onename,s.name towname,r.id oneid,s.id twoid from cg_shop_type s ,cg_shop_type_relation r
 where 
    (
         locate(concat(',',s.id,','),r.child_ids) > 0
           or 
           locate(concat('A',s.id,','),concat('A',r.child_ids)) > 0
           or
           locate(concat(',',s.id,'A'),concat(r.child_ids,'A')) > 0                 
     )
 and s.mark = 1
 order by r.name;
  
  
  查询所有公式
   select p.id,p.type_1 gdtype1,p.type_2 gdtype2,p.type_3 gdtype3,
         p.wd keyword,p.chengge_1_type cgtype1, p.chengge_2_type cgtype2,p.chengge_3_type cgtype3,
         p.mybrand,p.cgtype1 cgtype1name,p.cgtype2 cgtype2name,p.cgtype3 cgtype3name,
            p.gdtype1 gdtype1name,p.gdtype2 gdtype2name,p.gdtype3 gdtype3name
  from cg_zs_mapping_products p group by p.type_1,p.id ;
  
     */
    
    
    
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
    
}
