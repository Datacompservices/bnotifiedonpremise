package com.dcs.bnotified.onpservice.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.dcs.bnotidfied.onpservice.util.ConnectionManager;
import com.dcs.bnotified.ONP.data.CustRegistrationVO;

public class CustRegistrationDAO {

	public void insert(long mobileNumber,CustRegistrationVO custregVO) {
		String query ="SELECT count(*) COUNT FROM ONP_CUSTOMER_REG WHERE mobilenumber=?";  
        String query1 ="INSERT INTO ONP_CUSTOMER_REG(bankid,branchid,mobileNumber,Account_number,cust_id,cust_category,del_flg,r_cre_id,r_cre_time,r_mod_id,r_mod_time,free_text1,free_text2,free_text3) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";  
        
        PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		Connection con = null;
		Connection con1 = null;
		
		try {
			con = ConnectionManager.getConnection();
			con1 = ConnectionManager.getConnection();
			ps = con.prepareStatement(query);
			ps.setLong(1, mobileNumber);
			ResultSet rs = ps.executeQuery();
			int counter =0;
			while(rs.next())
			{
				counter = rs.getInt(1);
			}
			
			System.out.println("Value of Counter is "+counter);
			
					
			
			if (counter==0)
			{
				custregVO.setMobileNumber(mobileNumber);
				
				con1.setAutoCommit(true);
				ps1 = con1.prepareStatement(query1);
				ps1.setString(1,custregVO.getBank_ID());
				ps1.setString(2,custregVO.getBranch_ID());
				ps1.setLong(3,custregVO.getMobileNumber());
				ps1.setString(4,custregVO.getAccount_number());
				ps1.setString(5,custregVO.getCust_id());
				//ps1.setString(6,custregVO.getCust_category());
				ps1.setBoolean(6,Boolean.parseBoolean("f"));
				ps1.setString(7,custregVO.getDel_flg());
				ps1.setString(8, "BATCH");
				ps1.setTimestamp(9, new Timestamp(System.currentTimeMillis()));
				ps1.setString(10, "BATCH");
				ps1.setTimestamp(11, new Timestamp(System.currentTimeMillis()));
				ps1.setString(12, "");
				ps1.setString(13, "");
				ps1.setString(14, "");
				ps1.execute();
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				if (con != null) {
					con.close();
					con = null;
				}
				if (con1 != null) {
					con1.close();
					con1 = null;
				}

				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (ps1 != null) {
					ps1.close();
					ps1 = null;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
			
	
	}
}
