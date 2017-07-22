package test;

import com.service.poiservice.PoiServiceImp;

public class Test
{
    
    /**
     * @param args
     */
    public static void main(String[] args)
    {
        // TODO Auto-generated method stu
//        ApplicationContext context = new ClassPathXmlApplicationContext(
//                "applicationContext.xml");
        
        // LoginImp mm1=(LoginImp) context.getBean("LoginImp");
        // System.out.print(mm1.checked("zw", "123"));
//        HumanImp mm1 = (HumanImp) context.getBean("HumanImp");
//        List list = mm1.findall();
//        for (int i = 0; i < list.size(); i++)
//        {
//            if (i > 1)
//            {
//                System.out.println("�б�̫�����ж�����");
//                throw new RuntimeException("�ж������쳣,���б?�ȴ���3��ʱ������׳���������Ƿ�ع�");
//            }
//            Student stu = (Student) list.get(i);
//            mm1.save(stu);
//        }
        
        PoiServiceImp a = new PoiServiceImp();
        a.getFileList("E:\\apache-tomcat-6.0.20\\gaud");
        
    }
    
}
