package com.lyd.rbac.user.qvo;
import com.lyd.jdbc.BaseQueryAble;
import com.lyd.rbac.user.domain.UserVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="用户查询条件",parent=UserVO.class, description="")
public class UserQuery extends UserVO implements BaseQueryAble {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 3000693783594458654L;
	
	@ApiModelProperty(name = "blurred", value = "是否模糊查询", dataType = "java.lang.Boolean")
	boolean	blurred; // 是否模糊查询
								
	public boolean isBlurred() {
		return blurred;
	}
	
	public void setBlurred(boolean blurred) {
		this.blurred = blurred;
	}
	
	@Override
	public String toString() {
		return "UserQvo [blurred=" + blurred + ", toString()=" + super.toString() + "]";
	}
}
