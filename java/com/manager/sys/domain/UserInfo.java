package com.manager.sys.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * 用户实体信息类
 * 
 * @author HMY
 * 
 */
public class UserInfo implements Serializable {

	private static final long serialVersionUID = 8420353443526018500L;
	/*主键*/
	private Integer id;
	private String usrName;
	private String password;
	private String part;
	private String partid;
	private Timestamp createTime;
	private String createTimeStr;
	private Integer status;
	private String statusStr;
	private String remark;
	private Set<RoleInfo> roles = new HashSet<RoleInfo>();
	/*---------------------------------------------------------------------------------------------*/
	/* 本字段只为在界面上显示数据方便，不存入数据，其中的值和Id的值一致 */
	private String usrId;
	/**
	 * @return the usrName
	 */
	public String getUsrName() {
		return usrName;
	}

	/**
	 * @param usrName
	 *            the usrName to set
	 */
	public void setUsrName(String usrName) {
		this.usrName = usrName;
	}

	/**
	 * @return the password
	 */
	
	public String getPassword() {
		return password;
	}

	public String getPart() {
		return part;
	}

	public void setPart(String part) {
		this.part = part;
	}

	public String getPartid() {
		return partid;
	}

	public void setPartid(String partid) {
		this.partid = partid;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the createTime
	 */
	public Timestamp getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark
	 *            the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the roles
	 */
	public Set<RoleInfo> getRoles() {
		return roles;
	}

	/**
	 * @param roles
	 *            the roles to set
	 */
	public void setRoles(Set<RoleInfo> roles) {
		this.roles = roles;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	 

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof UserInfo) {
			UserInfo userInfo = (UserInfo) obj;
			if (this.usrName.equals(userInfo.getUsrName())) {
				return true;
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		if (usrName != null) {
			return usrName.hashCode();
		}
		return super.hashCode();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserInfo [id=");
		builder.append(id);
		builder.append(", usrName=");
		builder.append(usrName);
		builder.append(", password=");
		builder.append(password);
		builder.append(", part=");
		builder.append(part);
		builder.append(", partid=");
		builder.append(partid);
		builder.append(", createTime=");
		builder.append(createTime);
		builder.append(", createTimeStr=");
		builder.append(createTimeStr);
		builder.append(", status=");
		builder.append(status);
		builder.append(", statusStr=");
		builder.append(statusStr);
		builder.append(", remark=");
		builder.append(remark);
		builder.append(", roles=");
		builder.append(roles);
		builder.append("]");
		return builder.toString();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsrId() {
		return usrId;
	}

	public void setUsrId(String usrId) {
		this.usrId = usrId;
	}

	public String getStatusStr() {
		return statusStr;
	}
}
