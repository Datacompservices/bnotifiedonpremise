package com.dcs.bnotified.ONP.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import com.dcs.bnotified.ONP.DispatcherApp;
import com.dcs.bnotified.ONP.data.MessageFormatVO;
import com.dcs.bnotified.common.BNotifyNonFatalException;

public class MessageFormatDAO extends JdbcDaoSupport {

	public static List<MessageFormatVO> AllMessageFormat = new ArrayList<MessageFormatVO>();
	public static Hashtable<String, String> PRPMVal = new Hashtable<String, String>();
	ApplicationContext context = DispatcherApp.getContext();

	public List<MessageFormatVO> findAllMessageformats() {

		String query = "select * from ONP_Bank_MSG_FORMAT";
		AllMessageFormat = (List<MessageFormatVO>) getJdbcTemplate().query(query,
				new BeanPropertyRowMapper<MessageFormatVO>(MessageFormatVO.class));

		if (AllMessageFormat.size() <= 0) {
			try {
				throw new BNotifyNonFatalException("No Records Fetch in ONP_Bank_MSG_FORMAT table.");
			} catch (BNotifyNonFatalException e) {
				System.exit(0);

			}
		}
		return AllMessageFormat;

	}

	public void PRPMValList() throws SQLException, Exception {

		String query = "select PROPERTY_NAME,PROPERTY_VALUE from ONP_Property_manager";

		Connection con = null;
		ResultSet rs = null;
		try {
			con = getJdbcTemplate().getDataSource().getConnection();
			rs = con.createStatement().executeQuery(query);

			while (rs.next()) {
				PRPMVal.put(rs.getString(1), rs.getString(2));

			}

			if (PRPMVal.size() <= 0) {
				try {
					throw new BNotifyNonFatalException("No Records Fetch in ONP_Property_manager table.");
				} catch (BNotifyNonFatalException e) {
					System.exit(0);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(0);

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

		
	}

	public Timestamp getLastRunTime() throws SQLException, Exception {
		Timestamp ts = null;

		String query = "SELECT PROPERTY_VALUE FROM ONP_Property_manager WHERE PROPERTY_NAME='TRAN_LAST_RUNTIME'";

		Connection con = null;
		ResultSet rs = null;
		try {
			con = getJdbcTemplate().getDataSource().getConnection();

			rs = con.createStatement().executeQuery(query);

			while (rs.next()) {
				ts = Timestamp.valueOf(rs.getString(1));

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
