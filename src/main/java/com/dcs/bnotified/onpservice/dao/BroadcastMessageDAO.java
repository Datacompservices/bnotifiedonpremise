package com.dcs.bnotified.onpservice.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;

import com.dcs.bnotidfied.onpservice.util.ConnectionManager;
import com.dcs.bnotidfied.onpservice.util.PropertyManager;
import com.dcs.bnotified.onpservice.data.BroadcastMessageVO;



public class BroadcastMessageDAO {

	public void insert(BroadcastMessageVO broadMsgVO) throws SQLException, Exception
	{
		final String query = "insert into ONP_BROADCAST_MSG values (?,?,?,?,?,?,?,?,?)";
		PreparedStatement ps = null;
		Connection con =  null;
		try {

			con = ConnectionManager.getConnection();
			con.setAutoCommit(true);
			
			ps = con.prepareStatement(query);
			ps.setString(1, broadMsgVO.getMsgID());
			ps.setString(2, broadMsgVO.getBankID());
			ps.setString(3, broadMsgVO.getEntityName());
			ps.setString(4, broadMsgVO.getBranchID());
			ps.setString(5, broadMsgVO.getChannel_id());
			ps.setLong(6, broadMsgVO.getMobile_number());
			ps.setString(7, broadMsgVO.getMsgText());
			ps.setBoolean(8, broadMsgVO.getMsg_delivery_flg());
			ps.setTimestamp(9, (new Timestamp(System.currentTimeMillis())));
			ps.execute();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
				
	}
	
}
