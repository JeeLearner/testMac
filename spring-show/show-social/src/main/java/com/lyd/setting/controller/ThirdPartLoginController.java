package com.lyd.setting.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lyd.rbac.user.domain.UserVO;
import com.lyd.setting.domain.SocialUserVO;
import com.lyd.setting.qvo.SocialUserQuery;
import com.lyd.setting.service.SocialUserService;
import com.lyd.social.baidu.service.BaiduSocialSettingService;
import com.lyd.utils.BaseMap;
import com.lyd.utils.DataTypeUtil;
import com.lyd.utils.Json;

import io.swagger.annotations.Api;

@Api(tags = "第三方登陆信息设置")
@Controller
@RequestMapping("/thirdpart/")
public class ThirdPartLoginController {

	@Autowired
	BaiduSocialSettingService baiduUserService;
	
	@Autowired
	SocialUserService socialUserService;
	
	@RequestMapping(value = "thirdpartconfig_do/index.do", method = RequestMethod.GET)
	public String configindex() {
		return "/thirdpart/setting";
	}
	
	@ResponseBody
	@RequestMapping(value = "thirdpartconfig_do/info.do", method = RequestMethod.POST)
	public Map<String, Object> info() {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("baidu", baiduUserService.getSetting());
		/*map.put("sina", weiboUserService.getSetting());
		map.put("weixin", weixinApiService.getSetting());
		map.put("qq", qQuserService.getSetting());
		map.put("github", githubuserService.getSetting());*/
		return map;
	}
	
	@ResponseBody
	@PostMapping(value = "thirdpartconfig_do/update.do")
	public Json update(String qqid, String qqSERCRET, String baiduid, String baiduSERCRET, String sinaid, String sinaSERCRET, String weixinid, String weixinSERCRET, String githubid, String githubSERCRET) {
		Json j = new Json();
		j.setMsg("操作成功");
		// 1.4版本 删除码云登陆， 回调地址 不需要再填写
		baiduUserService.updateSetting(baiduid, baiduSERCRET, "");
		/*weiboUserService.updateSetting(sinaid, sinaSERCRET, "");
		weixinApiService.updateSetting(weixinid, weixinSERCRET);
		qQuserService.updateSetting(qqid, qqSERCRET, "");*/
		j.setSuccess(true);
		return j;
	}
	
	
	/************************************************************/
	/** 第三方账户管理首页 **/
	@RequestMapping(value = "thirdpart_do/index.do", method = RequestMethod.GET)
	public String index() {
		return "/thirdpart/account";
	}
	
	
		/**
		 * 查询用户绑定信息
		 * @author lyd
		 * @date 2017年10月25日
		 * @param user
		 * @return
		 * @throws Exception
		 */
		@ResponseBody
		@GetMapping("thirdpart_do/boundinfo.do")
		public Map<String, Object> boundinfo(@AuthenticationPrincipal UserVO user) throws Exception {
			Map<String, Object> map = new LinkedHashMap<>();
			if (user == null) {
				return null;
			}
			String userId = user.getId();
			SocialUserQuery qvo = new SocialUserQuery();
			qvo.setUserId(userId);
			qvo.setBlurred(true);
			List<SocialUserVO> list = socialUserService.query(qvo);
			map.put("list", list);
			map.put("baidu", checkProviderId("baidu", list));
			/*map.put("qq", checkproviderid("qq", list));
			map.put("sina", checkproviderid("sina", list));
			map.put("weixin", checkproviderid("weixin", list));
			map.put("github", checkproviderid("github", list));*/
			return map;
		}
	
		/**
		 * 解绑第三方平台账号
		 * @author lyd
		 * @date 2017年10月25日
		 * @param user
		 * @param providerid
		 * @return
		 */
		@ResponseBody
		@PostMapping("thirdpart_do/delsocialbind.do")
		public Json delsocialbind(@AuthenticationPrincipal UserVO user, String providerId) {
			Json json = new Json();
			json.setSuccess(true);
			if (user == null) {
				return null;
			}
			try {
				if (DataTypeUtil.checkString(providerId)) {
					BaseMap<String, Object> conditionMap = new BaseMap<>();
					conditionMap.put("userId", user.getId());
					conditionMap.put("providerId", providerId);
					socialUserService.remove(conditionMap);
				}
			} catch (Exception e) {
				e.printStackTrace();
				json.setMsg("操作失败");
				return json;
			}
			json.setMsg("操作成功");
			return json;
		}
		
		/** 判断哪个平台的信息*/
		private boolean checkProviderId(String providerId, List<SocialUserVO> list) {
			boolean flag = false;
			for (SocialUserVO bean : list) {
				if (bean.getProviderId().toLowerCase().equals(providerId.toLowerCase())) {
					return true;
				}
			}
			return flag;
		}
}
