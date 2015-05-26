package com.manager.sys.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 栏目信息实体类 默认当前栏目为子栏目
 * 
 * @author HMY
 * 
 */
public class Module implements Serializable {
	private static final long serialVersionUID = -5082706645922035988L;
	private Integer id;
	private String moduleName;
	private String moduleSign;
	private String moduleHref;
	private String moduleDesc;
	private Integer moduleOrder;
	private Integer parentId;
	private String parentName;
	private String roleId;
	private Set<FuncInfo> funcs = new HashSet<FuncInfo>();
	private Module parent;
	

	/**
	 * 存放角色信息的对象
	 */
	// private RoleInfo role = new RoleInfo();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getModuleSign() {
		return moduleSign;
	}

	public void setModuleSign(String moduleSign) {
		this.moduleSign = moduleSign;
	}

	public String getModuleHref() {
		return moduleHref;
	}

	public void setModuleHref(String moduleHref) {
		this.moduleHref = moduleHref;
	}

	public String getModuleDesc() {
		return moduleDesc;
	}

	public void setModuleDesc(String moduleDesc) {
		this.moduleDesc = moduleDesc;
	}

	public Integer getModuleOrder() {
		return moduleOrder;
	}

	public void setModuleOrder(Integer moduleOrder) {
		this.moduleOrder = moduleOrder;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Module [id=");
		builder.append(id);
		builder.append(", moduleName=");
		builder.append(moduleName);
		builder.append(", moduleSign=");
		builder.append(moduleSign);
		builder.append(", moduleHref=");
		builder.append(moduleHref);
		builder.append(", moduleDesc=");
		builder.append(moduleDesc);
		builder.append(", moduleOrder=");
		builder.append(moduleOrder);
		builder.append("]");
		return builder.toString();
	}

	/**
	 * @return the parentId
	 */
	public Integer getParentId() {
		return parentId;
	}

	/**
	 * @param parentId
	 *            the parentId to set
	 */
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	// public RoleInfo getRole() {
	// return role;
	// }
	//
	// public void setRole(RoleInfo role) {
	// this.role = role;
	// }

	/**
	 * @return the roleId
	 */
	public String getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId
	 *            the roleId to set
	 */
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public Set<FuncInfo> getFuncs() {
		return funcs;
	}

	public void setFuncs(Set<FuncInfo> funcs) {
		this.funcs = funcs;
	}

	public Module getParent() {
		return parent;
	}

	public void setParent(Module parent) {
		this.parent = parent;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
}
