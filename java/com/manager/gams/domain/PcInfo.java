package com.manager.gams.domain;

import java.io.Serializable;


public class PcInfo implements Serializable {

	private static final long serialVersionUID = 8420353443526018500L;
	/*主键*/
	private Integer id;
	private String pcdate;
	private String pcno;
	private String pcmodel;
	private String pcsn;
	private String pccpu;
	private String pcmemory;
	private String pchdd;
	private String pccharger;
	private String pcuse;
	private String pcqty;
	private String pcplace;
	private String pcstatus;
	private String pcremark;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPcdate() {
		return pcdate;
	}
	public void setPcdate(String pcdate) {
		this.pcdate = pcdate;
	}
	public String getPcno() {
		return pcno;
	}
	public void setPcno(String pcno) {
		this.pcno = pcno;
	}
	public String getPcmodel() {
		return pcmodel;
	}
	public void setPcmodel(String pcmodel) {
		this.pcmodel = pcmodel;
	}
	public String getPcsn() {
		return pcsn;
	}
	public void setPcsn(String pcsn) {
		this.pcsn = pcsn;
	}
	public String getPccpu() {
		return pccpu;
	}
	public void setPccpu(String pccpu) {
		this.pccpu = pccpu;
	}
	public String getPcmemory() {
		return pcmemory;
	}
	public void setPcmemory(String pcmemory) {
		this.pcmemory = pcmemory;
	}
	public String getPchdd() {
		return pchdd;
	}
	public void setPchdd(String pchdd) {
		this.pchdd = pchdd;
	}
	public String getPccharger() {
		return pccharger;
	}
	public void setPccharger(String pccharger) {
		this.pccharger = pccharger;
	}
	public String getPcuse() {
		return pcuse;
	}
	public void setPcuse(String pcuse) {
		this.pcuse = pcuse;
	}
	public String getPcqty() {
		return pcqty;
	}
	public void setPcqty(String pcqty) {
		this.pcqty = pcqty;
	}
	public String getPcplace() {
		return pcplace;
	}
	public void setPcplace(String pcplace) {
		this.pcplace = pcplace;
	}
	public String getPcstatus() {
		return pcstatus;
	}
	public void setPcstatus(String pcstatus) {
		this.pcstatus = pcstatus;
	}
	public String getPcremark() {
		return pcremark;
	}
	public void setPcremark(String pcremark) {
		this.pcremark = pcremark;
	}
	
}
