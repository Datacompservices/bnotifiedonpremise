package com.dcs.bnotified.ONP.data;

import java.sql.Timestamp;

public class MessageFormatVO {
	
	public String getBankID() {
		return bankID;
	}
	public void setBankID(String bankID) {
		this.bankID = bankID;
	}
	public String getBranchID() {
		return branchID;
	}
	public void setBranchID(String branchID) {
		this.branchID = branchID;
	}
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public String getMsg_lang() {
		return msg_lang;
	}
	public void setMsg_lang(String msg_lang) {
		this.msg_lang = msg_lang;
	}
	public String getMsg_Channel_ID() {
		return msg_Channel_ID;
	}
	public void setMsg_Channel_ID(String msg_Channel_ID) {
		this.msg_Channel_ID = msg_Channel_ID;
	}
	public String getDel_flg() {
		return del_flg;
	}
	public void setDel_flg(String del_flg) {
		this.del_flg = del_flg;
	}
	public String getR_cre_id() {
		return r_cre_id;
	}
	public void setR_cre_id(String r_cre_id) {
		this.r_cre_id = r_cre_id;
	}
	public Timestamp getR_cre_Time() {
		return r_cre_Time;
	}
	public void setR_cre_Time(Timestamp r_cre_Time) {
		this.r_cre_Time = r_cre_Time;
	}
	public String getR_mod_id() {
		return r_mod_id;
	}
	public void setR_mod_id(String r_mod_id) {
		this.r_mod_id = r_mod_id;
	}
	public Timestamp getR_mod_Time() {
		return r_mod_Time;
	}
	public void setR_mod_Time(Timestamp r_mod_Time) {
		this.r_mod_Time = r_mod_Time;
	}
	private String bankID="";
	private String branchID="";
	private String msgId="";
	private String msg_lang="";
	private String msg_Channel_ID="";
	private String del_flg;
	private String r_cre_id;
	private Timestamp r_cre_Time;
	private String r_mod_id;
	private Timestamp r_mod_Time;
	private String messageText="";
	
	public String getMessageText() {
		return messageText;
	}
	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}
	

}
