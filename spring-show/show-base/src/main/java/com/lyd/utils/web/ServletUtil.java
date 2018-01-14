package com.lyd.utils.web;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Servlet的工具类。
 * @author lyd
 * @date 2017年9月6日
 * @version 1.0
 * @CSDN http://blog.csdn.net/it_lyd
 */
public class ServletUtil {
	
	public static Log log = LogFactory.getLog(ServletUtil.class);

	/**
	 * 获取页面传过来的参数
	 * @author lyd
	 * @date 2017年9月6日
	 * @param request
	 * @param paramName
	 * @return
	 */
	public static String getStringParamDefaultBlank(HttpServletRequest request, String paramName) {
		return getStringParamDefault(request, paramName, "");
	}
	
	public static String getStringParamDefault(HttpServletRequest request, String paramName, String deault) {
		String value = request.getParameter(paramName);
		return value == null ? deault : value;
	}
}
