package com.dcs.bnotified.onpservice.login;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import com.dcs.bnotidfied.onpservice.util.CryptManager;
import com.dcs.bnotidfied.onpservice.util.PropertyManager;
import com.dcs.bnotified.onpservice.dao.AuditDAO;
import com.dcs.bnotified.onpservice.dao.UserProfileDAO;
import com.dcs.bnotified.onpservice.data.UserProfile;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Path("userlogin")
public class UserLogin {

	@Produces({ "application/json" })
	@GET
	@Path("changepassword/{name}/{pwd}")
	public String changePassword(@PathParam("name") final String name, @PathParam("pwd") final String pwd,
			@Context HttpServletRequest req) throws SQLException, Exception {

		new PropertyManager().PRPMValList();
		Vector<String> batchstatus = new Vector<String>();
		AuditDAO auditdao = new AuditDAO();

		batchstatus.add("UserID [" + name + "] submit for changePassword");

		String result = "SUCCESS";
		try {
			UserProfileDAO userprofileDAO = new UserProfileDAO();
			result = userprofileDAO.updateUserProfile(name, pwd);
			batchstatus.add("changePassword status is" + result);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				auditdao.insertAudit(getIpAddr(req), "changePassword", batchstatus, "M");
			} catch (Exception e) {
				System.out.println("While doing audit exception Thworn");
				e.printStackTrace();
			}
		}
		return result;

	}
	
	@Produces({ "application/json" })
	@GET
	@Path("getuserlist/")
	public String getuserlist() {
	
		UserProfileDAO userprofileDAO = new UserProfileDAO();
		ArrayList<UserProfile> userlist1 = new ArrayList<UserProfile>();
		try {
			userlist1  = userprofileDAO.getUserList();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		System.out.println(gson.toJson(userlist1));
		return gson.toJson(userlist1);
		
	}
	
	
	@Produces({ "application/json" })
	@GET
	@Path("changeuserstatus/{userid}/{flag}")
	public String changeuserstatus(@PathParam("userid") final String userid, @PathParam("flag") final String flag,
			@Context HttpServletRequest req) throws SQLException, Exception {

		new PropertyManager().PRPMValList();
		Vector<String> batchstatus = new Vector<String>();
		AuditDAO auditdao = new AuditDAO();

		batchstatus.add("UserID [" + userid + "] login status modified to ["+flag+"]");

		String result = "SUCCESS";
		try {
			UserProfileDAO userprofileDAO = new UserProfileDAO();
			result = userprofileDAO.updateuserenable(userid, flag);
			batchstatus.add("changeuserstatus status is" + result);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				auditdao.insertAudit(getIpAddr(req), "changeuserstatus", batchstatus, "A");
			} catch (Exception e) {
				System.out.println("While doing audit exception Thworn");
				e.printStackTrace();
			}
		}
		return result;

	}

	@Produces({ "application/json" })
	@GET
	@Path("createuser/{userid}/{pwd}/{firstname}/{middlename}/{lastname}/{empid}")
	public String createuser(@PathParam("userid") final String userid, @PathParam("pwd") final String pwd,
			@PathParam("empid") final String empid,@PathParam("lastname") final String lastname,@PathParam("middlename") final String middlename,@PathParam("firstname") final String firstname, @Context HttpServletRequest req) {
		
		try {
			new PropertyManager().PRPMValList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		UserProfile userprofile = new UserProfile();
		UserProfileDAO userprofileDAO = new UserProfileDAO();
		Vector<String> batchstatus = new Vector<String>();
		AuditDAO auditdao = new AuditDAO();
		//batchstatus.add("UserID [" + userid + "] is created");
		try {
			userprofile.setBank_id(PropertyManager.PRPMVal.get("BANK_ID"));
			userprofile.setBranchId(PropertyManager.PRPMVal.get("DEFAULT_BRANCH_ID"));
			userprofile.setPwd_enable_flag("N");
			userprofile.setR_cre_id("SU");
			userprofile.setR_mod_id("SU");
			
			userprofile.setUserid(userid);
			userprofile.setPwd(pwd);
			userprofile.setFirst_name(firstname);
			userprofile.setLast_name(middlename);
			userprofile.setMiddle_name(lastname);
			userprofile.setEmpId(empid);
			userprofileDAO.insertUser(userprofile);
			batchstatus.add("addUser status is SUCCESS [" +userprofile.getUserid() + "]");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			batchstatus.add("addUser status is FAILED [" +userprofile.getUserid() + "]");
		}finally {

			try {
				auditdao.insertAudit(getIpAddr(req), "createuser", batchstatus, "A");
			} catch (Exception e) {
				System.out.println("While doing audit exception Thworn");
				e.printStackTrace();
			}
		}
		return "SUCCESS";
	
	}
		
	@Produces({ "application/json" })
	@GET
	@Path("getuserprofile/{name}/{pwd}")
	public String getUserProfile(@PathParam("name") final String name, @PathParam("pwd") final String pwd,
			@Context HttpServletRequest req) {

		String loginstatus = "";
		System.out.println("Name is " + name);
		Vector<String> batchstatus = new Vector<String>();
		AuditDAO auditdao = new AuditDAO();

		try {
			batchstatus.add("UserID [" + name + "] submit for getUserProfile");
			loginstatus = doLoginProcess(name, pwd);
			batchstatus.add("Response is " + loginstatus);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			try {
				auditdao.insertAudit(getIpAddr(req), "getUserProfile", batchstatus, "A");
			} catch (Exception e) {
				System.out.println("While doing audit exception Thworn");
				e.printStackTrace();
			}

		}

		System.out.println("Login status is  " + loginstatus);

		return loginstatus;
	}

	public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
			System.out.println(ip);
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
			System.out.println(ip);
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
			System.out.println(ip);
		}
		return ip;
	}

	private String doLoginProcess(String name, String pwd) throws SQLException, Exception {

		new PropertyManager().PRPMValList();
		UserProfile userprofile = new UserProfile();
		
		userprofile.setBank_id(PropertyManager.PRPMVal.get("BANK_ID"));
		userprofile.setCorpid(PropertyManager.PRPMVal.get("CORP_ID"));
		userprofile.setUserid(name);
		userprofile.setPwd(CryptManager.encrypt(pwd));

		userprofile.setFree_text5("N");
		UserProfileDAO userprofileDAO = new UserProfileDAO();
		userprofile = userprofileDAO.getUserDetails(userprofile);

		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

		System.out.println(gson.toJson(userprofile));

		return gson.toJson(userprofile);
	}

}
