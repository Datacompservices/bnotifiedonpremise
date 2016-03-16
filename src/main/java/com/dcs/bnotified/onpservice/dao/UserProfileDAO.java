package com.dcs.bnotified.onpservice.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.dcs.bnotidfied.onpservice.util.ConnectionManager;
import com.dcs.bnotidfied.onpservice.util.CryptManager;
import com.dcs.bnotified.onpservice.data.UserProfile;

public class UserProfileDAO {

	public String updateUserProfile(String userid, String password) throws Exception {
		Connection con = ConnectionManager.getConnection();
		PreparedStatement ps = null;
		final String query = "UPDATE ONP_USER_PROFILE SET PWD=?,R_MOD_TIME=? WHERE USERID=?";

		try {
			ps = con.prepareStatement(query);
			ps.setString(1, CryptManager.encrypt(password));
			ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			ps.setString(3, userid);
			int result = ps.executeUpdate();

			return "" + result;

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} finally {

			try {
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (SQLException e) {

				e.printStackTrace();
				throw e;

			}
		}

	}
	
	
	public String updateuserenable(String userid, String flag) throws Exception {
		Connection con = ConnectionManager.getConnection();
		PreparedStatement ps = null;
		final String query = "UPDATE ONP_USER_PROFILE SET PWD_ENABLE_FLAG=?,R_MOD_TIME=?,R_MOD_Id=? WHERE USERID=?";

		try {
			ps = con.prepareStatement(query);
			ps.setString(1, flag);
			ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			ps.setString(3, "SU");
			ps.setString(4, userid);
			
			int result = ps.executeUpdate();

			return "" + result;

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} finally {

			try {
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (SQLException e) {

				e.printStackTrace();
				throw e;

			}
		}

	}
	
	
	
	
	
	
	
	public UserProfile insertUser(UserProfile userprofile) throws SQLException
	{
		Connection con = ConnectionManager.getConnection();
		PreparedStatement ps = null;
		final String query = "INSERT INTO ONP_USER_PROFILE(BANK_ID,USERID,PWD,BRANCH_ID,PWD_ENABLE_FLAG,EMP_ID,FIRST_NAME,MIDDLE_NAME,LAST_NAME,R_CRE_ID,R_CRE_TIME,R_MOD_ID,R_MOD_TIME) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		try {
			con.setAutoCommit(true);
			ps = con.prepareStatement(query);
			ps.setString(1, userprofile.getBank_id());
		    ps.setString(2, userprofile.getUserid());
		    ps.setString(3, CryptManager.encrypt(userprofile.getPwd().trim()));
		    ps.setString(4, userprofile.getBranchId());
		    ps.setString(5, userprofile.getPwd_enable_flag());
		    ps.setString(6, userprofile.getEmpId());
		    ps.setString(7, userprofile.getFirst_name());
		    ps.setString(8, userprofile.getMiddle_name());
		    ps.setString(9, userprofile.getLast_name());
		    ps.setString(10, userprofile.getR_cre_id());
		    ps.setTimestamp(11, new Timestamp(System.currentTimeMillis()));
		    ps.setString(12, userprofile.getR_mod_id());
		    ps.setTimestamp(13,new Timestamp(System.currentTimeMillis()));
		    ps.execute();
		    
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {

			try {
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (SQLException e) {

				e.printStackTrace();
				throw e;
			}
		}
			
		
		
		return userprofile;
	}
	

	public ArrayList<UserProfile> getUserList() throws SQLException
	{
	ArrayList<UserProfile> list = new ArrayList<UserProfile>();
	
	Connection con = ConnectionManager.getConnection();
	PreparedStatement ps = null;
	ResultSet rs = null;
	final String query = "SELECT BANK_ID,USERID,branch_id,pwd_enable_flag,emp_id,first_name,middle_name,last_name FROM ONP_USER_PROFILE";
	try {
		ps = con.prepareStatement(query);
		UserProfile userprofile = new UserProfile();

		rs = ps.executeQuery();
		
		while (rs.next()) {
			userprofile = new UserProfile();
			userprofile.setBank_id(rs.getString("BANK_ID"));
			userprofile.setUserid(rs.getString("USERID"));
			userprofile.setBranchId(rs.getString("BRANCH_ID"));
			userprofile.setPwd_enable_flag(rs.getString("PWD_ENABLE_FLAG"));
			userprofile.setEmpId(rs.getString("EMP_ID"));
			userprofile.setFirst_name(rs.getString("FIRST_NAME"));
			userprofile.setMiddle_name(rs.getString("MIDDLE_NAME"));
			userprofile.setLast_name(rs.getString("LAST_NAME"));
			
			list.add(userprofile);
		}

	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		throw e;
	}

	finally {

		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (con != null) {
				con.close();
				con = null;
			}
		} catch (SQLException e) {

			e.printStackTrace();
			throw e;
		}
	}
		
		
		return list;
		
	}
	
	
	public UserProfile getUserDetails(UserProfile userprofile) throws SQLException, SQLException {
		Connection con = ConnectionManager.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		final String query = "SELECT * FROM ONP_USER_PROFILE WHERE USERID=?";
		try {
			ps = con.prepareStatement(query);
			ps.setString(1, userprofile.getUserid().trim());

			rs = ps.executeQuery();
			String password = "";

			while (rs.next()) {
				userprofile.setNO_of_atmpts(rs.getInt(5));
				userprofile.setSaluation(rs.getString(6));
				userprofile.setFirst_name(rs.getString(7));
				userprofile.setMiddle_name(rs.getString(8));
				userprofile.setLast_name(rs.getString(9));
				userprofile.setPwd_enable_flag(rs.getString(14));

				password = rs.getString(4);

				if (password.trim().equals(userprofile.getPwd().trim())) {
					userprofile.setFree_text5("Y");
				} else {
					userprofile.setFree_text5("N");
				}

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}

		finally {

			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (SQLException e) {

				e.printStackTrace();
				throw e;
			}
		}

		return userprofile;

	}

}
