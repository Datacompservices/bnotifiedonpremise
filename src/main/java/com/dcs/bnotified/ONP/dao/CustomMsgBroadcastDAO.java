package com.dcs.bnotified.ONP.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;


import com.dcs.bnotified.ONP.data.CustomMsgBroadcastVO;

public class CustomMsgBroadcastDAO extends JdbcDaoSupport {
	
	public void insert(final CustomMsgBroadcastVO CutmsgBroadcastVO)
	{
		final String query = "insert into ONP_CUSTOM_MESSAGE_FORMAT values (?,?,?,?,?,?,?)";
	
		 KeyHolder keyHolder = new GeneratedKeyHolder();
		 getJdbcTemplate().update(
		     new PreparedStatementCreator() {
		         public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
		             PreparedStatement ps = connection.prepareStatement(query);
		             
		             ps.setString(1, CutmsgBroadcastVO.getBank_id());
		             ps.setString(2 ,CutmsgBroadcastVO.getBranch_id());
		             ps.setString(3, CutmsgBroadcastVO.getChannel_id());
		             ps.setString(4, CutmsgBroadcastVO.getMessage_text());
		             ps.setString(5, CutmsgBroadcastVO.getMsg_delivery_flg());
		             ps.setString(6, CutmsgBroadcastVO.getR_cre_id());
		             ps.setTimestamp(7,CutmsgBroadcastVO.getR_cre_time());
		           return ps;
		         }
		     },
		     keyHolder);
	}
	
	
	public void update(final CustomMsgBroadcastVO CutmsgBroadcastVO)
	{
		System.out.println("bla bla"+CutmsgBroadcastVO.getMsgID());
		String sql=" UPDATE ONP_CUSTOM_MESSAGE_FORMAT SET msg_delivery_flg='Y' WHERE msgId='"+CutmsgBroadcastVO.getMsgID()+"'";
		getJdbcTemplate().update(sql);
		
		
	}
	
	public ArrayList<CustomMsgBroadcastVO> getCustomMsgtoBroadcast()
	{
		ArrayList<CustomMsgBroadcastVO> customMsgBroacastlist =new ArrayList<CustomMsgBroadcastVO>();
		
		String query ="SELECT * from ONP_CUSTOM_MESSAGE_FORMAT WHERE msg_delivery_flg='N'";
		ResultSet rs=null;
		Connection con =null;
		try {
			con = getJdbcTemplate().getDataSource().getConnection();
			
		
			rs = con.createStatement().executeQuery(query);
			CustomMsgBroadcastVO  CutmsgBroadcastVO = null;
			while (rs.next())
			{
				CutmsgBroadcastVO = new CustomMsgBroadcastVO();
				CutmsgBroadcastVO.setMsgID(rs.getString(1));
				CutmsgBroadcastVO.setBank_id(rs.getString(2));
				CutmsgBroadcastVO.setBranch_id(rs.getString(3));
				CutmsgBroadcastVO.setChannel_id(rs.getString(4));
				CutmsgBroadcastVO.setMessage_text(rs.getString(5));
				CutmsgBroadcastVO.setMsg_delivery_flg(rs.getString(6));
				CutmsgBroadcastVO.setR_cre_id(rs.getString(7));
				CutmsgBroadcastVO.setR_cre_time(rs.getTimestamp(8));
				
				customMsgBroacastlist.add(CutmsgBroadcastVO);
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (con != null) {
					con.close();
					con = null;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
		return customMsgBroacastlist;
		}
}
