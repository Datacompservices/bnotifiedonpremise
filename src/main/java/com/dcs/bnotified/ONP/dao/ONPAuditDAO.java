package com.dcs.bnotified.ONP.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class ONPAuditDAO extends JdbcDaoSupport {

	public void insertAudit(final String batchname,@SuppressWarnings("rawtypes") Vector batchstatus)
	{
		final String query = "insert into ONP_AUDITTABLE values (?,?,?,?,?,?,?)";
		
		for (int index=0;index<batchstatus.size();index++)
		{
		final String temp = batchstatus.elementAt(index).toString();
			
		 KeyHolder keyHolder = new GeneratedKeyHolder();
		 getJdbcTemplate().update(
		     new PreparedStatementCreator() {
		         public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
		             PreparedStatement ps = connection.prepareStatement(query);
		             ps.setString(1, MessageFormatDAO.PRPMVal.get("BANK_ID"));
		             ps.setString(2, batchname);
		             ps.setString(3, "A");
		             ps.setString(4, "");
		             ps.setString(5, temp);
		             ps.setString(6, "BATCH");
		             ps.setTimestamp(7,(new Timestamp(System.currentTimeMillis())));
		             return ps;
		         }
		     },
		     keyHolder);
		
		}
	}
	
	
	
	public Timestamp getBatchLastRunTime(String batchname) throws SQLException, Exception {
		Timestamp ts = null;

		String query = "SELECT MAX(r_cre_time) from ONP_AUDITTABLE where BATCH_NAME='" + batchname + "'";
		Connection con = null;
		ResultSet rs = null;
		try {
			con = getJdbcTemplate().getDataSource().getConnection();

			rs = con.createStatement().executeQuery(query);

			while (rs.next()) {
				if (rs.getString(1) != null) {
					ts = Timestamp.valueOf(rs.getString(1));
				}

				else {
					ts = new Timestamp(System.currentTimeMillis());
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
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


		return ts;

	}

	
	
	
	
}
