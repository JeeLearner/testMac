package com.lyd.rbac.resources.domain;
import java.io.Serializable;
import org.hibernate.validator.constraints.NotBlank;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 菜单类实体（ 数据库）
 * @author lyd
 * @date 2017年9月6日
 * @version 1.0
 * @CSDN http://blog.csdn.net/it_lyd
 */
@ApiModel(value = "菜单类实体DO", description = "")
public class ResourcesDO implements Serializable {
	
	private static final long	serialVersionUID	= -8990353087139542849L;
	
	@ApiModelProperty(name = "id", value = "编号", dataType = "java.lang.String")
	private String id;
	
	@ApiModelProperty(name = "name", value = "菜单名称", dataType = "java.lang.String")
	@NotBlank(message = "菜单名称不能为空")
	private String name;
	
	@ApiModelProperty(name = "parentId", value = "父级编号", dataType = "java.lang.String")
	@NotBlank(message = "父级编号不能为空")
	private String parentId;
	
	@ApiModelProperty(name = "parentIds", value = "父级编号集合", dataType = "java.lang.String")
	//@NotBlank(message = "父级编号集合不能为空")
	private String parentIds;
	
	@ApiModelProperty(name = "resKey", value = "菜单标识", dataType = "java.lang.String")
	@NotBlank(message = "菜单标识不能为空")
	private String resKey;
	
	@ApiModelProperty(name = "type", value = "菜单种类 1 目录，2菜单 ，3 按钮", dataType = "java.lang.String")
	@NotBlank(message = "菜单种类不能为空")
	private String type;
	
	@ApiModelProperty(name = "resUrl", value = "菜单地址", dataType = "java.lang.String")
	@NotBlank(message = "菜单URL不能为空")
	private String resUrl;
	
	@ApiModelProperty(name = "level", value = "菜单等级（暂时 没用)", dataType = "java.lang.Integer")
	private Integer level;
	
	@ApiModelProperty(name = "icon", value = "菜单图标（暂时 没用)", dataType = "java.lang.String")
	private String icon;
	
	@ApiModelProperty(name = "isHide", value = "是否隐藏", dataType = "java.lang.String")
	private Integer isHide;
	
	@ApiModelProperty(name = "description", value = "描述", dataType = "java.lang.String")
	private String description;
	
	@ApiModelProperty(name = "isDelete", value = "是否删除", dataType = "java.lang.Integer")
	private Integer isDelete;
	
	@ApiModelProperty(name = "colorId", value = "颜色ID", dataType = "java.lang.Integer")
	private Integer colorId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getResKey() {
		return resKey;
	}

	public void setResKey(String resKey) {
		this.resKey = resKey;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getResUrl() {
		return resUrl;
	}

	public void setResUrl(String resUrl) {
		this.resUrl = resUrl;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getIsHide() {
		return isHide;
	}

	public void setIsHide(Integer isHide) {
		this.isHide = isHide;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public Integer getColorId() {
		return colorId;
	}

	public void setColorId(Integer colorId) {
		this.colorId = colorId;
	}

	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}

	@Override
	public String toString() {
		return "ResourcesDO [id=" + id + ", name=" + name + ", parentId=" + parentId + ", parentIds=" + parentIds
				+ ", resKey=" + resKey + ", type=" + type + ", resUrl=" + resUrl + ", level=" + level + ", icon=" + icon
				+ ", isHide=" + isHide + ", description=" + description + ", isDelete=" + isDelete + ", colorId="
				+ colorId + "]";
	}

}
