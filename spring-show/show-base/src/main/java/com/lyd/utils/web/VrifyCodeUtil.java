package com.lyd.utils.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.ui.ModelMap;

/**
 * 验证码工具
 * @author lyd
 * @date 2017年9月7日
 * @version 1.0
 * @CSDN http://blog.csdn.net/it_lyd
 */
public class VrifyCodeUtil {

	public static final String PARAMETERNAME = "vrifyCode";

	private VrifyCodeUtil() {
		//禁止实例化
	}
	
	/**
	 * 验证码校验逻辑
	 * 		传入的验证码参数名必须叫 vrifyCode
	 * @author lyd
	 * @date 2017年9月7日
	 * @param request
	 * @param model
	 * @return
	 */
	public static boolean checkVrifyCode(HttpServletRequest request, ModelMap model){
		HttpSession session = request.getSession();
		String captchaId = (String)request.getSession().getAttribute(PARAMETERNAME);
		String parameter = request.getParameter(PARAMETERNAME);
		if(parameter == null){
			return false;
		}
		if(!captchaId.equals(parameter)){
			model.addAttribute("error","验证码不正确!");
			cleanSession(session);
			return false;
		}
		cleanSession(session);
		return true;
	}
	
	public static boolean checkvrifyCode(String vrifyCode, HttpServletRequest httpServletRequest) {
		HttpSession session = httpServletRequest.getSession();
		String captchaId = (String) httpServletRequest.getSession().getAttribute(PARAMETERNAME);
		if(vrifyCode == null){
			return false;
		}
		if (!captchaId.equals(vrifyCode)) {
			cleanSession(session);
			return false;
		}
		cleanSession(session);
		return true;
	}
	
	public static boolean checkvrifyCode(String vrifyCode, HttpSession session) {
		String captchaId = (String) session.getAttribute(PARAMETERNAME);
		if(vrifyCode == null){
			return false;
		}
		if (!captchaId.equals(vrifyCode)) {
			cleanSession(session);
			return false;
		}
		cleanSession(session);
		return true;
	}

	/*
	 * 验证码验证通过后，清除验证码会话，防止重复利用攻击API
	 */
	private static void cleanSession(HttpSession session){
		session.removeAttribute(PARAMETERNAME);
	}
	
}
