package com.lyd.setting.qvo;

import com.lyd.jdbc.BaseQueryAble;
import com.lyd.setting.domain.SocialUserVO;

import io.swagger.annotations.ApiModelProperty;

public class SocialUserQuery extends SocialUserVO implements BaseQueryAble{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7744030552516461819L;
	
	@ApiModelProperty(name = "blurred", value = "是否模糊查询", dataType = "java.lang.Boolean")
	boolean	blurred; // 是否模糊查询
								
	public boolean isBlurred() {
		return blurred;
	}
	
	public void setBlurred(boolean blurred) {
		this.blurred = blurred;
	}

}
