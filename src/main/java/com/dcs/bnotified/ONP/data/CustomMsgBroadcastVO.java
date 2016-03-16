package com.dcs.bnotified.ONP.data;

import java.sql.Timestamp;

public class CustomMsgBroadcastVO {

	
	public String getBank_id() {
		return bank_id;
	}
	public void setBank_id(String bank_id) {
		this.bank_id = bank_id;
	}
	public String getBranch_id() {
		return branch_id;
	}
	public void setBranch_id(String branch_id) {
		this.branch_id = branch_id;
	}
	public String getChannel_id() {
		return channel_id;
	}
	public void setChannel_id(String channel_id) {
		this.channel_id = channel_id;
	}
	public String getMessage_text() {
		return message_text;
	}
	public void setMessage_text(String message_text) {
		this.message_text = message_text;
	}
	public String getMsg_delivery_flg() {
		return msg_delivery_flg;
	}
	public void setMsg_delivery_flg(String msg_delivery_flg) {
		this.msg_delivery_flg = msg_delivery_flg;
	}
	public Timestamp getR_cre_time() {
		return r_cre_time;
	}
	public void setR_cre_time(Timestamp r_cre_time) {
		this.r_cre_time = r_cre_time;
	}
	
	public String getR_cre_id() {
		return r_cre_id;
	}
	public void setR_cre_id(String r_cre_id) {
		this.r_cre_id = r_cre_id;
	}

	
	private String bank_id="";
	private String branch_id="";
	private String channel_id="";
	private String message_text="";
	private String msg_delivery_flg="";
	private String r_cre_id="";
	private Timestamp r_cre_time;
	private String msgID="";
	
	public String getMsgID() {
		return msgID;
	}
	public void setMsgID(String msgID) {
		this.msgID = msgID;
	}
	
}
