package com.help;

public class HelpMe
{
    /**
     * bean 标签 写法 
     * 
     * 
     * 第一种 
     * <s:bean name="com.entity.Brand"   var="brandBaseInfo" />
     * <s:property value="brandBaseInfo.typeLevelOneName"/>
     * 
     * 第二种
     * ${brandBaseInfo.typeLevelOneName}
     * 
     * =============================================================================
     * =============================================================================
     * urlrewrite 配置注意事项：
     * 
     * 请参考 WebRoot/WEB-INF下的urlrewrite.xml
     * 
     * 
     *  help
  
          rlrewrite(伪静态技术运用2之outbound-rule)
        
        问题：页面通过"/servlet/detail?magid=520&categoryId=205“来访问一个servlet，
        这个链接在IE地址栏上给人家看起来很不舒服，想把它伪装一下变成别的地址，
        例如想把它伪装成：/servlet/detail/520_205.html 或者其他的url地址那该怎么实现了？
        
        【注意：页面连接不是“/servlet/detail/520_205.html
        “而是“/servlet/detail?magid=520&categoryId=205”】
        
        urlrewrite.xml配置：
        
        错误代码：
        
        <urlrewrite>
        
         <rule>
            <from>/servlet/detail/([0-9]+)_([0-9]+).html</from>
             <to>/servlet/detail?magid=$1&amp;categoryId=$2</to>
         </rule>
        
        </urlrewrite> 
        
        有很多新手刚开始就认为这样配置就能解决了上面提问的问题。其实错误，这样页面只能通过：”/servelt/detail/24_42.html ” 来访问，访问这个地址时它会forward到/servlet/detail?magid=24&categoryid=42这个servlet。如果我们页面通过“/servlet/detail?magid=24&categoryid=42”这样来访问的话，是不会被重写的。这是新手的常遇到的一个问题。
        
        正确代码：
        
        <urlrewrite>
        
         <rule>
          <from>/servelt/detail/([0-9]+)_([0-9]+).html</from>
          <to>/servelt/detail?magid=$1&amp;categoryId=$2</to>
         </rule>
        
        
         <outbound-rule>
          <from>/servlet/detail\?magid=([0-9]+)&amp;categoryId=([0-9]+)</from>
          <to>/servlet/detail/$1_$2.html</to>
         </outbound-rule>
        </urlrewrite>
        
        通过上面的配置基本上实现了上面的提出的问题。
        
        1：通过：“/servlet/detail?magid=24&categoryid=42”来访问，得在<outbound-rule>标签中配置，同时还得在<rule>中配置相关信息。如果rule中没有配置，会找不到相关的资源信息，抱404错误
        
        2：上面的配置文件得注意的地方：在<outbound-rule>中的特殊符号“？”“&”等得转义。不然的话也会出错，不能被重写。像上面的“outbound-rule>from标签中路径中如果出现“？”问号的话，记得要转义，“\？”。
        
        3：jsp中，上面提出的问题中，在页面如果直接写<a href="/servlet/detail?magid=24&categoryid=42"/>这样也是错误的，同样也没办法被重写。
        
        它只能用在response.encodeURL（） 或者jstl中c:url中，才会被重写。这个也是容易犯错的地方。
        
        jsp:页面关键代码：
        
          <a href="<c:url value='/servlet/detail?magid=3934&categoryId=3'/>">JSTl c:URL</a><hr/>
            <a href="<%=response.encodeUrl("/servlet/detail?magid=3934&categoryId=3") %>">response EncodeURL</a>
        
        4:如果想在<outbound-rule>中定义多个from to标签这个是不可以的，<outbound-rule>中只能定义一个from to 标签。这个是我在程序中测试得出来的结论。只能定义一对from to标签是在个人测试得出来的结论，如果您测试出来不是这样的话，请给我指正，评判。
        
        5：通过上面的urlrewrite.xml的配置，  <a href=”servlet/detail?44_77.html">测试</a>这样子来访问也是可以被重写的。也就是相当于双向。
        
        这样便很好的隐藏了真实地址 
        这其中有点是需要注意的，引用官网中的一段话： 
        Using the example above JSP's with the code 
        <a href="<%= response.encodeURL("/world.jsp?country=usa&amp;city=nyc") %>">nyc</a> 
        will output 
        <a href="/world/usa/nyc">nyc</a> 
        
        Or JSTL 
        <a href="<c:url value="/world.jsp?country=${country}&amp;city=${city}" />">nyc</a> 
        will output 
        <a href="/world/usa/nyc">nyc</a> 
        
                   需要转化的链接不能直接写在a标签中，需要写在c:url或其他服务器需要解析的变量中，这样才能对其重新显示
        
                   注意：<rule>标签可以单独的存在。而<outbound-rule>必须要和<rule>标签成对出现。而且在页面的时候需要<c:url>标签来转换成<rule>所对应的规则。
        -->
        
        ================================================================
        ================================================================
         struts2 urlrewrite 之forward跳转 页面404原理分析
         
                     解决方式:
         1.首先看下他们的顺序：urlrewrite struts cleanup struts  （如下code.2）
         
         2.因为struts的filter没有处理来自forward的请求，在web.xml里修改一下map参数（如下code.1 .2）
         
         <filter>  
                  <filter-name>struts2</filter-name>  
                  <filter-class>org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter</filter-class>  
                </filter>  
                <filter-mapping>  
                     <filter-name>struts2</filter-name>  
                     <url-pattern>*.shtml</url-pattern>   
                     <dispatcher>REQUEST</dispatcher> code.1- 
                    <dispatcher>FORWARD</dispatcher> code.2-  
                </filter-mapping>
     * 
     * =============================================================================
     * =============================================================================
     * 
     * 自定义标签：
     * 
     * 写mypage.tld 放在WebRoot下 ，自己建立文件夹 mytags
     * 
     * 定义类 PagerTag 继承 标签类 TagSupport
     * 
     * 里面的写法 请参考 PagerTag
     * 
     * jsp页面引入 方式
     * <%@taglib uri="/mytags" prefix="myPageTag" %>
     * 
     * <myPageTag:pager pageSize='${page.pageSize}' pageNo='${page.pageNo}' url="/brandlistone_17.html" recordCount='${page.recordCount}' />
     * 
     * 具体写法请参考 BrandList.jsp 页面
     * 
     * 
     * =============================================================================
     * =============================================================================
     * 
     * 分页例子 参考 mypage.tld,PagerTag.java ,BrandList.jsp
     * 
     * 
     * =============================================================================
     * =============================================================================
     * Log4j 请参考 com.log.LogMgr.java
     * 
     * =============================================================================
     * =============================================================================
     * ibatis 配置请参考本项目的com.maps目录 
     * 
     * =============================================================================
     * =============================================================================
     *spring 配置请参考本项目的 
     * WebRoot/WEB-INF/Context-*.xml
     * web.xml
     * struts.xml
     * 
     * =============================================================================
     * =============================================================================
     * ibatis 的缓存设置
     * 
     * =============================================================================
     * =============================================================================
     * ibatis 整合memcahe 缓存
     * 
     * =============================================================================
     * =============================================================================
     * SSI框架举例 看本项目即可
     * 
     * =============================================================================
     * =============================================================================
     * dwr ajax 框架
     * 
     * =============================================================================
     * =============================================================================
     * jquery 
     * 
     * =============================================================================
     * =============================================================================
     * struts2 传递参数乱码问题解决
     * ①修改Tomcat---->conf----->server.xml文件，在修改端口的标签后面加一行代码，如下：

                            <Connector port="8080" protocol="HTTP/1.1"   
                                          connectionTimeout="20000"   
                                          redirectPort="8443"  URIEncoding="UTF-8"/>  
                        
                                    ②编写过滤器Filter
                        
                            import java.io.IOException;  
                              
                            import javax.servlet.Filter;  
                            import javax.servlet.FilterChain;  
                            import javax.servlet.FilterConfig;  
                            import javax.servlet.ServletException;  
                            import javax.servlet.ServletRequest;  
                            import javax.servlet.ServletResponse;  
                              
                            public class CharacterEncodingFilter implements Filter ...{  
                              
                                @Override  
                                public void destroy() ...{  
                                }  
                              
                                @Override  
                                public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,ServletException   {  
                                request.setCharacterEncoding("utf-8");  
                                chain.doFilter(request, response);  
                                }  
                              
                                @Override  
                                public void init(FilterConfig arg0) throws ServletException ...{  
                                }  
                              
                            }  
                        
                        
                        
                          利用过滤器，把requst传递的中文参数都设成“UTF-8”编码。
                        
                        ③修改web.xml文件
                        
                            打开项目里的web.xml文件，在前面加上如下代码：
                        
                            <filter>  
                            <filter-name>characterEncoding</filter-name>  
                            <filter-class>com.v512.example.CharacterEncodingFilter</filter-class>  
                            </filter>  
                            <filter-mapping>  
                            <filter-name>characterEncoding</filter-name>  
                            <url-pattern>/*</url-pattern>  
                            </filter-mapping>  
                        
                        
                        注意其过滤的URL为“/*”，表示当前的request请求。为了使设置生效，重起tomcat。 
                        
     * =============================================================================
     * =============================================================================
     *   
     *   资源国际化 配置
     *   
     *   1.在struts.xml中的<struts></struts>加入

            <constant name="struts.custom.i18n.resources" value="message"></constant>
            
            使国际化生效(也可以不加)
            
            2.编辑国际化文件 放在src下
            
            messageresource_en_US.properties 国际化为英文
            
            messageresource_zh_CN.properties 国际化为中文
            
            3.在xml校验框架中写
            
            <field-validator type="验证内容">
            
            <param name="trim">true</param>
            
            <message key="国际化文件对应的名称"></message>
            
            </field-validator>
            
            或者在action中的validate()方法中写
            
            this.addFieldError("别名", "国际化文件对应的名称");
            
            4.在jsp页面输出
            
            此处一般为INPUT返回的页面如注册页面
            
            <s:textfield name="username" key="国际化文件对应的名称"></s:textfield>国际化label标签
            
            <s:fielderror><s:param>action中的属性名(自动调用校验框架或validte()方法)</s:param> </s:fielderror>
            
            此处一般为SUCCESS页面
            
            <s:text name="国际化文件对应的名称"><s:param>${username}</s:param></s:text> 
            
      =========================================================================
      =========================================================================
     * 
     *   解决资源乱码问题
     *   
     *   打开DOS窗口，CMD到资源文件所在目录，运用JDK的native2ascii工具把我们新建的资源文件改成另一个名字的资源文件，例如bank.properties。命令如下：
                        引用
                            
                        >native2ascii -encoding gbk ApplicationResources_zh.properties bank.properties
            
                        D:\Program Files\Java\jdk1.6.0_43\bin>native2ascii -encoding UTF-8 E:\wsjava\myBrand\src\messageResources_zh_CN.properties e:\messageResources_zh_CN.properties
                        
                        打开bank.properties资源文件，自动生成的代码如下：
                        引用
                             
                        #Generated by ResourceBundle Editor (http://eclipse-rbe.sourceforge.net)
                        
                        example.login.username = \u7528\u6237\u540D
                        example.login.password = \u5BC6\u7801
                        
                        然后在myeclipse窗口中，把原来新建ApplicationResources_zh.properties 删除，并把bank.properties改为ApplicationResources_zh.properties （为了方便记忆，管理）。然后重起tomcat或进行reload文件，我们发现乱码问题没有了。 

      ==================================================================================
      =====================================================================================
                     关于控制 Referer 你想要知道的一切
      
     * 百度搜索 关于referer 
     * 
     * ===========================================================================
     * ===========================================================================
     * java 引入smarty 模板 摸索中
     * 
     * ===========================================================================
     * ===========================================================================
     * java 引入extjs extjs 只适合管理登录 页面风格单一。摸索中
     * 
     * ===========================================================================
     * ===========================================================================
     * 文件下载的servlet 
     * 
     *   <servlet>
            <servlet-name>FileDownServlet</servlet-name>
            <servlet-class>com.tools.FileDownServlet</servlet-class>
          </servlet>
        
         <servlet-mapping>
           <servlet-name>FileDownServlet</servlet-name>
           <url-pattern>/FileDownServlet</url-pattern>
         </servlet-mapping>
     * 
     * 
     */
    
    
}
