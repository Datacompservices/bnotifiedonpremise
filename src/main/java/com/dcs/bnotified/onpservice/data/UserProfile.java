package com.dcs.bnotified.onpservice.data;

import java.sql.Timestamp;

import com.google.gson.annotations.Expose;

public class UserProfile {

	public String getBank_id() {
		return bank_id;
	}
	public void setBank_id(String bank_id) {
		this.bank_id = bank_id;
	}
	public String getCorpid() {
		return corpid;
	}
	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getPwd() {
		return Pwd;
	}
	public void setPwd(String pwd) {
		Pwd = pwd;
	}
	public int getNO_of_atmpts() {
		return NO_of_atmpts;
	}
	public void setNO_of_atmpts(int nO_of_atmpts) {
		NO_of_atmpts = nO_of_atmpts;
	}
	public String getSaluation() {
		return saluation;
	}
	public void setSaluation(String saluation) {
		this.saluation = saluation;
	}
	public String getFirst_name() {
		return First_name;
	}
	public void setFirst_name(String first_name) {
		First_name = first_name;
	}
	public String getMiddle_name() {
		return Middle_name;
	}
	public void setMiddle_name(String middle_name) {
		Middle_name = middle_name;
	}
	public String getLast_name() {
		return Last_name;
	}
	public void setLast_name(String last_name) {
		Last_name = last_name;
	}
	public String getMobile_no_1() {
		return mobile_no_1;
	}
	public void setMobile_no_1(String mobile_no_1) {
		this.mobile_no_1 = mobile_no_1;
	}
	public String getEmail_id_1() {
		return email_id_1;
	}
	public void setEmail_id_1(String email_id_1) {
		this.email_id_1 = email_id_1;
	}
	public Timestamp getPwd_reset_date() {
		return Pwd_reset_date;
	}
	public void setPwd_reset_date(Timestamp pwd_reset_date) {
		Pwd_reset_date = pwd_reset_date;
	}
	public Timestamp getPwd_exp_date() {
		return Pwd_exp_date;
	}
	public void setPwd_exp_date(Timestamp pwd_exp_date) {
		Pwd_exp_date = pwd_exp_date;
	}
	public String getPwd_enable_flag() {
		return Pwd_enable_flag;
	}
	public void setPwd_enable_flag(String pwd_enable_flag) {
		Pwd_enable_flag = pwd_enable_flag;
	}
	public Timestamp getLast_login_date() {
		return Last_login_date;
	}
	public void setLast_login_date(Timestamp last_login_date) {
		Last_login_date = last_login_date;
	}
	public Timestamp getLast_logoff_date() {
		return Last_logoff_date;
	}
	public void setLast_logoff_date(Timestamp last_logoff_date) {
		Last_logoff_date = last_logoff_date;
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
	public String getFree_text1() {
		return free_text1;
	}
	public void setFree_text1(String free_text1) {
		this.free_text1 = free_text1;
	}
	public String getFree_text2() {
		return free_text2;
	}
	public void setFree_text2(String free_text2) {
		this.free_text2 = free_text2;
	}
	public String getFree_text3() {
		return free_text3;
	}
	public void setFree_text3(String free_text3) {
		this.free_text3 = free_text3;
	}
	public String getFree_text4() {
		return free_text4;
	}
	public void setFree_text4(String free_text4) {
		this.free_text4 = free_text4;
	}
	public String getFree_text5() {
		return free_text5;
	}
	public void setFree_text5(String free_text5) {
		this.free_text5 = free_text5;
	}
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getBranchId() {
		return branchId;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	private String bank_id="";
	private String corpid="";
	@Expose
	private String userid="";
	private String Pwd="";
	@Expose
	private int NO_of_atmpts;
	@Expose
	private String saluation="";
	@Expose
	private String First_name="";
	@Expose
	private String Middle_name="";
	@Expose
	private String Last_name="";
	private String mobile_no_1="";
	private String email_id_1="";
	private Timestamp Pwd_reset_date = null;
	@Expose
	private Timestamp Pwd_exp_date = null;
	@Expose
	private String Pwd_enable_flag="";
	@Expose
	private Timestamp Last_login_date = null;
	private Timestamp Last_logoff_date = null;
	private String del_flg="";
	private String r_cre_id="";
	private Timestamp r_cre_time = null;
	private String r_mod_id="";
	private Timestamp r_mod_time = null;
	private String free_text1="";
	private String free_text2="";
	private String free_text3="";
	private String free_text4="";
	@Expose
	private String free_text5="";
	@Expose
	private String empId="";
	@Expose
	private String branchId="";

		
}
