package com.manager.gams.domain;

import java.io.Serializable;


public class UnitInfo implements Serializable {

	private static final long serialVersionUID = 8420353443526018500L;
	/*主键*/
	private Integer id;
	private String utdate;
	private String utno;
	private String utname;
	private String utspec;
	private String utcharger;
	private String utuse;
	private String utplace;
	private String utqty;
	private String utstatus;
	private String utremark;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUtdate() {
		return utdate;
	}
	public void setUtdate(String utdate) {
		this.utdate = utdate;
	}
	public String getUtno() {
		return utno;
	}
	public void setUtno(String utno) {
		this.utno = utno;
	}
	public String getUtname() {
		return utname;
	}
	public void setUtname(String utname) {
		this.utname = utname;
	}
	public String getUtspec() {
		return utspec;
	}
	public void setUtspec(String utspec) {
		this.utspec = utspec;
	}
	public String getUtcharger() {
		return utcharger;
	}
	public void setUtcharger(String utcharger) {
		this.utcharger = utcharger;
	}
	public String getUtuse() {
		return utuse;
	}
	public void setUtuse(String utuse) {
		this.utuse = utuse;
	}
	public String getUtplace() {
		return utplace;
	}
	public void setUtplace(String utplace) {
		this.utplace = utplace;
	}
	public String getUtqty() {
		return utqty;
	}
	public void setUtqty(String utqty) {
		this.utqty = utqty;
	}
	public String getUtstatus() {
		return utstatus;
	}
	public void setUtstatus(String utstatus) {
		this.utstatus = utstatus;
	}
	public String getUtremark() {
		return utremark;
	}
	public void setUtremark(String utremark) {
		this.utremark = utremark;
	}
	
}
