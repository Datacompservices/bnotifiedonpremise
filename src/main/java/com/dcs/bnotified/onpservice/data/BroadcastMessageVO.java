package com.dcs.bnotified.onpservice.data;

import java.sql.Timestamp;

import com.google.gson.annotations.Expose;

public class BroadcastMessageVO {

	public String getBankID() {
		return entityId;
	}
	public void setBankID(String entityId) {
		this.entityId = entityId;
	}
	public String getBranchID() {
		return branchId;
	}
	public void setBranchID(String branchID) {
		this.branchId = branchID;
	}
	public String getChannel_id() {
		return channel_id;
	}
	public void setChannel_id(String channel_id) {
		this.channel_id = channel_id;
	}
	public long getMobile_number() {
		return mobileNumber;
	}
	public void setMobile_number(long mobile_number) {
		this.mobileNumber = mobile_number;
	}
	public boolean getMsg_delivery_flg() {
		return msgDelflag;
	}
	public void setMsg_delivery_flg(boolean msgDelflag) {
		this.msgDelflag = msgDelflag;
	}
	public Timestamp getR_cre_time() {
		return createdAt;
	}
	public void setR_cre_time(Timestamp r_cre_time) {
		this.createdAt = r_cre_time;
	}
	
	public String getMsgText() {
		return notificationText;
	}
	public void setMsgText(String msgText) {
		this.notificationText = msgText;
	}

	String msgID="";
	public String getMsgID() {
		return msgID;
	}
	public  void  setMsgID(String msgID) {
		this.msgID = msgID;
	}
	
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	
	public String getRcreID() {
		return rcreID;
	}
	public void setRcreID(String rcreID) {
		this.rcreID = rcreID;
	}
	@Expose
	private String entityId="";
	@Expose
	private String branchId="";
	private String channel_id="";
	@Expose
	private long mobileNumber;
	@Expose
	private boolean msgDelflag=false;
	@Expose
	private boolean msgRedstatus=false;
	@Expose
	private Timestamp createdAt;
	@Expose
	private String notificationText="";
	@Expose
	private String entityName ="";
	
	private String rcreID="";
	
}
