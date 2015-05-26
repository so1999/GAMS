package com.manager.gams.domain;

import java.io.Serializable;


public class BorrowInfo implements Serializable {

	private static final long serialVersionUID = 8420353443526018500L;
	/*主键*/
	private Integer bid;
	private String iou;
	private String iopcno;
	private String iopcmodel;
	private String iopcsn;
	private String iopccharger;
	private String iopcplace;
	private String iopcstatus;
	private String ioperson;
	private String ioyon;
	private String ioindate;
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
	public String getIopcno() {
		return iopcno;
	}
	public void setIopcno(String iopcno) {
		this.iopcno = iopcno;
	}
	public String getIopcmodel() {
		return iopcmodel;
	}
	public void setIopcmodel(String iopcmodel) {
		this.iopcmodel = iopcmodel;
	}
	public String getIopcsn() {
		return iopcsn;
	}
	public void setIopcsn(String iopcsn) {
		this.iopcsn = iopcsn;
	}
	public String getIopccharger() {
		return iopccharger;
	}
	public void setIopccharger(String iopccharger) {
		this.iopccharger = iopccharger;
	}
	public String getIopcplace() {
		return iopcplace;
	}
	public void setIopcplace(String iopcplace) {
		this.iopcplace = iopcplace;
	}
	public String getIopcstatus() {
		return iopcstatus;
	}
	public void setIopcstatus(String iopcstatus) {
		this.iopcstatus = iopcstatus;
	}
	public String getIoperson() {
		return ioperson;
	}
	public void setIoperson(String ioperson) {
		this.ioperson = ioperson;
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
	
}
