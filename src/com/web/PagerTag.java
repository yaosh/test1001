package com.web;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class PagerTag extends TagSupport
{
    private static final long serialVersionUID = 1L;
    
    private String url;
    
    private int pageSize = 10;
    
    private int pageNo = 1;
    
    private int recordCount;
    
    public int doStartTag() throws JspException
    {
        int pageCount = (this.recordCount + this.pageSize - 1) / this.pageSize;
        
        StringBuilder sb = new StringBuilder();
        
        sb.append("<style type=\"text/css\">");
        sb.append(".pagination {padding: 5px;float:right;font-size:12px;}");
        sb.append(".pagination a, .pagination a:link, .pagination a:visited {padding:2px 5px;margin:2px;border:1px solid #aaaadd;text-decoration:none;color:#006699;}");
        sb.append(".pagination a:hover, .pagination a:active {border: 1px solid #ff0000;color: #000;text-decoration: none;}");
        sb.append(".pagination span.current {padding: 2px 5px;margin: 2px;border: 1px solid #ff0000;font-weight: bold;background-color: #ff0000;color: #FFF;}");
        sb.append(".pagination span.disabled {padding: 2px 5px;margin: 2px;border: 1px solid #eee; color: #ddd;}");
        sb.append("</style>\r\n");
        sb.append("<div class=\"pagination\">\r\n");
        if (this.recordCount == 0)
        {
            sb.append("<strong>没有可显示的项目</strong>\r\n");
        }
        else
        {
            if (this.pageNo > pageCount)
                this.pageNo = pageCount;
            if (this.pageNo < 1)
                this.pageNo = 1;
            
            sb.append("<form method=\"post\" action=\"")
                    .append(this.url)
                    .append("\" name=\"qPagerForm\">\r\n");
            
            HttpServletRequest request = (HttpServletRequest) this.pageContext.getRequest();
            Enumeration enumeration = request.getParameterNames();
            String name = null;
            String value = null;
            
            while (enumeration.hasMoreElements())
            {
                name = (String) enumeration.nextElement();
                value = request.getParameter(name);
                
                if (name.equals("pageNo"))
                {
                    if ((value != null) && (!"".equals(value)))
                    {
                        this.pageNo = Integer.parseInt(value);
                    }
                }
                else
                {
                    sb.append("<input type=\"hidden\" name=\"")
                            .append(name)
                            .append("\" value=\"")
                            .append(value)
                            .append("\"/>\r\n");
                }
            }
            
            sb.append("<input type=\"hidden\" name=\"")
                    .append("pageNo")
                    .append("\" value=\"")
                    .append(this.pageNo)
                    .append("\"/>\r\n");
            
            sb.append(" 共<strong>")
                    .append(this.recordCount)
                    .append("</strong>项")
                    .append(",<strong>")
                    .append(pageCount)
                    .append("</strong>页: \r\n");
            
            if (this.pageNo == 1)
                sb.append("<span class=\"disabled\">« 上一页")
                        .append("</span>\r\n");
            else
            {
                sb.append("<a href=\"javascript:turnOverPage(")
                        .append(this.pageNo - 1)
                        .append(")\">« 上一页</a>\r\n");
            }
            
            int start = 1;
            if (this.pageNo > 4)
            {
                start = this.pageNo - 1;
                sb.append("<a href=\"javascript:turnOverPage(1)\">1</a>\r\n");
                sb.append("<a href=\"javascript:turnOverPage(2)\">2</a>\r\n");
                sb.append("…\r\n");
            }
            
            int end = this.pageNo + 1;
            if (end > pageCount)
            {
                end = pageCount;
            }
            for (int i = start; i <= end; i++)
            {
                if (this.pageNo == i)
                    sb.append("<span class=\"current\">")
                            .append(i)
                            .append("</span>\r\n");
                else
                {
                    sb.append("<a href=\"javascript:turnOverPage(")
                            .append(i)
                            .append(")\">")
                            .append(i)
                            .append("</a>\r\n");
                }
            }
            
            if (end < pageCount - 2)
            {
                sb.append("…\r\n");
            }
            if (end < pageCount - 1)
            {
                sb.append("<a href=\"javascript:turnOverPage(")
                        .append(pageCount - 1)
                        .append(")\">")
                        .append(pageCount - 1)
                        .append("</a>\r\n");
            }
            if (end < pageCount)
            {
                sb.append("<a href=\"javascript:turnOverPage(")
                        .append(pageCount)
                        .append(")\">")
                        .append(pageCount)
                        .append("</a>\r\n");
            }
            
            if (this.pageNo == pageCount)
                sb.append("<span class=\"disabled\">下一页 »")
                        .append("</span>\r\n");
            else
            {
                sb.append("<a href=\"javascript:turnOverPage(")
                        .append(this.pageNo + 1)
                        .append(")\">下一页 »</a>\r\n");
            }
            sb.append("</form>\r\n");
            
            sb.append("<script language=\"javascript\">\r\n");
            sb.append("  function turnOverPage(no){\r\n");
            sb.append("    if(no>").append(pageCount).append("){");
            sb.append("      no=").append(pageCount).append(";}\r\n");
            sb.append("    if(no<1){no=1;}\r\n");
            sb.append("    document.qPagerForm.pageNo.value=no;\r\n");
            sb.append("    document.qPagerForm.submit();\r\n");
            sb.append("  }\r\n");
            sb.append("</script>\r\n");
        }
        sb.append("</div>\r\n");
        try
        {
            this.pageContext.getOut().println(sb.toString());
        }
        catch (IOException e)
        {
            throw new JspException(e);
        }
        return 0;
    }
    
    public void setUrl(String url)
    {
        this.url = url;
    }
    
    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }
    
    public void setPageNo(int pageNo)
    {
        this.pageNo = pageNo;
    }
    
    public void setRecordCount(int recordCount)
    {
        this.recordCount = recordCount;
    }
}
