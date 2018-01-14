package com.lyd.tool.generator.utils.xss;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * XSS过滤
 * @author lyd
 * @date 2017年9月22日
 * @version 1.0
 * @CSDN http://blog.csdn.net/it_lyd
 */
public class XssFilter implements Filter {
	
	public void init(FilterConfig config) throws ServletException {
	}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper((HttpServletRequest) request);
		chain.doFilter(xssRequest, response);
	}
	
	public void destroy() {
	}
}