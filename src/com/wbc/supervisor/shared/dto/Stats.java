package com.wbc.supervisor.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.sql.Timestamp;


public class Stats   implements IsSerializable {
	
	private static final long serialVersionUID = 1L;

	private int id;
	private int ivid;
	private int nwid;	
	private Timestamp last;
	private int ud;	
	private int uc;	
	private int vd;	
	private int vc;	
	private int pt;	
	private int pf;	
	private int recv;	
	private int xmit;	
	private int ipmac;
	private int vm;
	private String sampleDate;
	private int sampleno;	

	public String getSampleDate() {
		return sampleDate;
	}

	public void setSampleDate(String sampleDate) {
		this.sampleDate = sampleDate;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIvid() {
		return ivid;
	}

	public void setIvid(int ivid) {
		this.ivid = ivid;
	}

	public int getNwid() {
		return nwid;
	}

	public void setNwid(int nwid) {
		this.nwid = nwid;
	}

	public Timestamp getLast() {
		return last;
	}

	public void setLast(Timestamp last) {
		this.last = last;
	}

	public int getUd() {
		return ud;
	}

	public void setUd(int ud) {
		this.ud = ud;
	}

	public int getUc() {
		return uc;
	}

	public void setUc(int uc) {
		this.uc = uc;
	}

	public int getVd() {
		return vd;
	}

	public void setVd(int vd) {
		this.vd = vd;
	}

	public int getVc() {
		return vc;
	}

	public void setVc(int vc) {
		this.vc = vc;
	}

	public int getPt() {
		return pt;
	}

	public void setPt(int pt) {
		this.pt = pt;
	}

	public int getPf() {
		return pf;
	}

	public void setPf(int pf) {
		this.pf = pf;
	}

	

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getRecv() {
		return recv;
	}

	public void setRecv(int recv) {
		this.recv = recv;
	}

	public int getXmit() {
		return xmit;
	}

	public void setXmit(int xmit) {
		this.xmit = xmit;
	}

	public int getSampleno() {
		return sampleno;
	}

	public void setSampleno(int sampleno) {
		this.sampleno = sampleno;
	}

	public int getIpmac() {
		return ipmac;
	}

	public void setIpmac(int ipmac) {
		this.ipmac = ipmac;
	}

	public int getVm() {
		return vm;
	}

	public void setVm(int vm) {
		this.vm = vm;
	}



	
}

