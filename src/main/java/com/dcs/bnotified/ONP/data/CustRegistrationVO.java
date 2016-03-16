package com.dcs.bnotified.ONP.data;

import java.sql.Timestamp;

import com.google.gson.annotations.Expose;

public class CustRegistrationVO {

	public String getBank_ID() {
		return entityId;
	}
	public void setBank_ID(String bank_ID) {
		entityId = bank_ID;
	}
	public String getBranch_ID() {
		return branchId;
	}
	public void setBranch_ID(String branch_ID) {
		branchId = branch_ID;
	}
	public String getAccount_number() {
		return Account_number;
	}
	public void setAccount_number(String account_number) {
		Account_number = account_number;
	}
	public String getCust_id() {
		return cust_id;
	}
	public void setCust_id(String cust_id) {
		this.cust_id = cust_id;
	}
	public String getCust_category() {
		return cust_category;
	}
	public void setCust_category(String cust_category) {
		this.cust_category = cust_category;
	}
	public boolean getCust_enabled() {
		return mobileUsageActivated;
	}
	public void setCust_enabled(boolean cust_enabled) {
		this.mobileUsageActivated = cust_enabled;
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
	public Timestamp getR_cre_time() {
		return r_cre_time;
	}
	public void setR_cre_time(Timestamp r_cre_time) {
		this.r_cre_time = r_cre_time;
	}
	public String getR_mod_id() {
		return r_mod_id;
	}
	public void setR_mod_id(String r_mod_id) {
		this.r_mod_id = r_mod_id;
	}
	public Timestamp getR_mod_time() {
		return r_mod_time;
	}
	public void setR_mod_time(Timestamp r_mod_time) {
		this.r_mod_time = r_mod_time;
	}
	public String getFreetext1() {
		return freetext1;
	}
	public void setFreetext1(String freetext1) {
		this.freetext1 = freetext1;
	}
	public String getFreetext2() {
		return freetext2;
	}
	public void setFreetext2(String freetext2) {
		this.freetext2 = freetext2;
	}
	public String getFreetext3() {
		return freetext3;
	}
	public void setFreetext3(String freetext3) {
		this.freetext3 = freetext3;
	}
	public long getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getEntityLogo() {
		return entityLogo;
	}
	public void setEntityLogo(String entityLogo) {
		this.entityLogo = entityLogo;
	}
	
	
	String _id="";
	@Expose	
	private String entityId="";
	@Expose
	private String branchId="";
	private String Account_number="";
	private String cust_id="";
	@Expose
	private String entityName="";
	private String cust_category="";
	@Expose
	private boolean mobileUsageActivated=false;
	private String del_flg="";
	private String r_cre_id="";
	private Timestamp r_cre_time;
	private String r_mod_id="";
	private Timestamp r_mod_time;
	private String freetext1="";
	private String freetext2="";
	private String freetext3="";
	@Expose
	private long mobileNumber;
	@Expose
	private String entityLogo="";
	
	
}
