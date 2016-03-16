package com.dcs.bnotified.onpservice.data;

import java.sql.Timestamp;

public class PushMessageVO {

  /**
	 * @return the bankid
	 */
	public String getBankid() {
		return bankid;
	}
	/**
	 * @param bankid the bankid to set
	 */
	public void setBankid(String bankid) {
		this.bankid = bankid;
	}
	/**
	 * @return the branchid
	 */
	public String getBranchid() {
		return branchid;
	}
	/**
	 * @param branchid the branchid to set
	 */
	public void setBranchid(String branchid) {
		this.branchid = branchid;
	}
	/**
	 * @return the channelid
	 */
	public String getChannelid() {
		return channelid;
	}
	/**
	 * @param channelid the channelid to set
	 */
	public void setChannelid(String channelid) {
		this.channelid = channelid;
	}
	/**
	 * @return the textmsg
	 */
	public String getTextmsg() {
		return textmsg;
	}
	/**
	 * @param textmsg the textmsg to set
	 */
	public void setTextmsg(String textmsg) {
		this.textmsg = textmsg;
	}
	/**
	 * @return the msgdelflag
	 */
	public String getMsgdelflag() {
		return msgdelflag;
	}
	/**
	 * @param msgdelflag the msgdelflag to set
	 */
	public void setMsgdelflag(String msgdelflag) {
		this.msgdelflag = msgdelflag;
	}
	/**
	 * @return the rcreid
	 */
	public String getRcreid() {
		return rcreid;
	}
	/**
	 * @param rcreid the rcreid to set
	 */
	public void setRcreid(String rcreid) {
		this.rcreid = rcreid;
	}
	/**
	 * @return the rcretime
	 */
	public Timestamp getRcretime() {
		return rcretime;
	}
	/**
	 * @param rcretime the rcretime to set
	 */
	public void setRcretime(Timestamp rcretime) {
		this.rcretime = rcretime;
	}
	
  private String bankid="";
  private String branchid="";
  private String channelid="";
  private String textmsg="";
  private String msgdelflag="";
  private String rcreid="";
  private Timestamp rcretime=null;
  
}
