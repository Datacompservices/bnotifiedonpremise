package com.dcs.bnotified.onpservice.data;

import java.sql.Timestamp;

import com.google.gson.annotations.Expose;

public class QuartzDetailsVO {

	@Expose
	private String triggername="";
	@Expose
	private String triggergroup="";
	@Expose
	private Timestamp starttime=null;
	@Expose
	private Timestamp nextfiretime=null;
	@Expose
	private Timestamp prevfiretime=null;
	@Expose
	private int misfirenos=0;
	/**
	 * @return the triggername
	 */
	public String getTriggername() {
		return triggername;
	}
	/**
	 * @param triggername the triggername to set
	 */
	public void setTriggername(String triggername) {
		this.triggername = triggername;
	}
	/**
	 * @return the triggergroup
	 */
	public String getTriggergroup() {
		return triggergroup;
	}
	/**
	 * @param triggergroup the triggergroup to set
	 */
	public void setTriggergroup(String triggergroup) {
		this.triggergroup = triggergroup;
	}
	/**
	 * @return the starttime
	 */
	public Timestamp getStarttime() {
		return starttime;
	}
	/**
	 * @param timestamp the starttime to set
	 */
	public void setStarttime(java.sql.Timestamp timestamp) {
		this.starttime = timestamp;
	}
	/**
	 * @return the nextfiretime
	 */
	public Timestamp getNextfiretime() {
		return nextfiretime;
	}
	/**
	 * @param nextfiretime the nextfiretime to set
	 */
	public void setNextfiretime(Timestamp nextfiretime) {
		this.nextfiretime = nextfiretime;
	}
	/**
	 * @return the prevfiretime
	 */
	public Timestamp getPrevfiretime() {
		return prevfiretime;
	}
	/**
	 * @param prevfiretime the prevfiretime to set
	 */
	public void setPrevfiretime(Timestamp prevfiretime) {
		this.prevfiretime = prevfiretime;
	}
	/**
	 * @return the misfirenos
	 */
	public int getMisfirenos() {
		return misfirenos;
	}
	/**
	 * @param misfirenos the misfirenos to set
	 */
	public void setMisfirenos(int misfirenos) {
		this.misfirenos = misfirenos;
	}
			
		
}
