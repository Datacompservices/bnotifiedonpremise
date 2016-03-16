package com.dcs.bnotified.onpservice.data;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

public class DashboardVO {
	@Expose
int noofcustomers;
	@Expose
int noofmessages;
	@Expose
long msgtotalcount=0;
	
	@Expose
	long msgsenttodaycount=0;

	public long getMsgtotalcount() {
		return msgtotalcount;
	}
	public void setMsgtotalcount(long msgtotalcount) {
		this.msgtotalcount = msgtotalcount;
	}
	public long getMsgsenttodaycount() {
		return msgsenttodaycount;
	}
	public void setMsgsenttodaycount(long msgsenttodaycount) {
		this.msgsenttodaycount = msgsenttodaycount;
	}

	
	
public int getNoofcustomers() {
	return noofcustomers;
}
public void setNoofcustomers(int noofcustomers) {
	this.noofcustomers = noofcustomers;
}
public int getNoofmessages() {
	return noofmessages;
}
public void setNoofmessages(int noofmessages) {
	this.noofmessages = noofmessages;
}

@Expose
private List<QuartzDetailsVO> quartzDet;

public List<QuartzDetailsVO> getQuartzDet() {
	return getQuartzDet();
}
public void setQuartzDet(List<QuartzDetailsVO> quartzDet) {
	this.quartzDet = quartzDet;
}


	
}
