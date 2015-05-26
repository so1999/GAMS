package com.manager.sys.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * 角色信息实体类
 * 
 * @author HMY
 * 
 */
public class RoleInfo implements Serializable {

	private static final long serialVersionUID = 8503461006937382275L;
	private Integer id;
	private String roleName;
	private String roleDesc;
	private Timestamp createTime;
	private String createUsr;
	private Set<Module> modules = new HashSet<Module>();
	private Set<FuncInfo> funcs = new HashSet<FuncInfo>();
	private String createTimeStr;

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getCreateUsr() {
		return createUsr;
	}

	public void setCreateUsr(String createUsr) {
		this.createUsr = createUsr;
	}

	/**
	 * @return the modules
	 */
	public Set<Module> getModules() {
		return modules;
	}

	/**
	 * @param modules
	 *            the modules to set
	 */
	public void setModules(Set<Module> modules) {
		this.modules = modules;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RoleInfo [roleId=");
		builder.append(id);
		builder.append(", roleName=");
		builder.append(roleName);
		builder.append(", roleDesc=");
		builder.append(roleDesc);
		builder.append(", createTime=");
		builder.append(createTime);
		builder.append(", createUsr=");
		builder.append(createUsr);
		builder.append(", modules=");
		builder.append(modules);
		builder.append("]");
		return builder.toString();
	}

	public Set<FuncInfo> getFuncs() {
		return funcs;
	}

	public void setFuncs(Set<FuncInfo> funcs) {
		this.funcs = funcs;
	}
}
