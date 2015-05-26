package com.manager.gams.domain;

import java.io.Serializable;


public class PartInfo implements Serializable {

	private static final long serialVersionUID = 8420353443526018500L;
	/*主键*/
	private Integer id;
	private String part;
	private String partid;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
}
