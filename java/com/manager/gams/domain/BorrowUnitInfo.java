package com.manager.gams.domain;

import java.io.Serializable;


public class BorrowUnitInfo implements Serializable {

	private static final long serialVersionUID = 8420353443526018500L;
	/*主键*/
	private Integer bid;
	private String iou;
	private String ioutno;
	private String ioutname;
	private String ioutspec;
	private String ioutcharger;
	private String ioutplace;
	private String ioutstatus;
	private String ioyon;
	private String ioindate;
	private String ioperson;
	private String ioopdate;
	private String ioopperson;
	private String ioremark;
	public Integer getBid() {
		return bid;
	}
	public void setBid(Integer bid) {
		this.bid = bid;
	}
	public String getIou() {
		return iou;
	}
	public void setIou(String iou) {
		this.iou = iou;
	}
	public String getIoutno() {
		return ioutno;
	}
	public void setIoutno(String ioutno) {
		this.ioutno = ioutno;
	}
	public String getIoutname() {
		return ioutname;
	}
	public void setIoutname(String ioutname) {
		this.ioutname = ioutname;
	}
	public String getIoutspec() {
		return ioutspec;
	}
	public void setIoutspec(String ioutspec) {
		this.ioutspec = ioutspec;
	}
	public String getIoutcharger() {
		return ioutcharger;
	}
	public void setIoutcharger(String ioutcharger) {
		this.ioutcharger = ioutcharger;
	}
	public String getIoutplace() {
		return ioutplace;
	}
	public void setIoutplace(String ioutplace) {
		this.ioutplace = ioutplace;
	}
	public String getIoutstatus() {
		return ioutstatus;
	}
	public void setIoutstatus(String ioutstatus) {
		this.ioutstatus = ioutstatus;
	}
	public String getIoyon() {
		return ioyon;
	}
	public void setIoyon(String ioyon) {
		this.ioyon = ioyon;
	}
	public String getIoindate() {
		return ioindate;
	}
	public void setIoindate(String ioindate) {
		this.ioindate = ioindate;
	}
	public String getIoopdate() {
		return ioopdate;
	}
	public void setIoopdate(String ioopdate) {
		this.ioopdate = ioopdate;
	}
	public String getIoopperson() {
		return ioopperson;
	}
	public void setIoopperson(String ioopperson) {
		this.ioopperson = ioopperson;
	}
	public String getIoremark() {
		return ioremark;
	}
	public void setIoremark(String ioremark) {
		this.ioremark = ioremark;
	}
	public String getIoperson() {
		return ioperson;
	}
	public void setIoperson(String ioperson) {
		this.ioperson = ioperson;
	}
	
}
