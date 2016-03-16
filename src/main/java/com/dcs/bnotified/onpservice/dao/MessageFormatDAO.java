package com.dcs.bnotified.onpservice.dao;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.dcs.bnotidfied.onpservice.util.ConnectionManager;
import com.dcs.bnotified.onpservice.data.MessageFormatVO;
import com.dcs.bnotified.onpservice.data.PushMessageVO;
import com.dcs.bnotified.onpservice.data.QuartzDetailsVO;

public class MessageFormatDAO {
	
	public int getMessageFormatCount() throws SQLException, Exception {
		String query = "SELECT count(*)  FROM  ONP_Bank_MSG_FORMAT";
		Connection con = ConnectionManager.getConnection();
		ResultSet rs = null;
		PreparedStatement ps = null;
		int counter = 0;

		try {

			ps = con.prepareStatement(query);
			rs = ps.executeQuery();

			while (rs.next()) {
				counter = rs.getInt(1);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} finally {

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

			} catch (Exception e) {
				e.printStackTrace();
				// throw e;
			}
		}

		return counter;

	}
	
	public long totalMessageCount() throws SQLException, Exception {
		String query = "SELECT count(*)  FROM  ONP_BROADCAST_MSG";
		Connection con = ConnectionManager.getConnection();
		ResultSet rs = null;
		PreparedStatement ps = null;
		long counter = 0;

		try {

			ps = con.prepareStatement(query);
			rs = ps.executeQuery();

			while (rs.next()) {
				counter = rs.getLong(1);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} finally {

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

			} catch (Exception e) {
				e.printStackTrace();
				// throw e;
			}
		}

		return counter;

	}
	
	
	
	public long MessageSentTodayCount() throws SQLException, Exception {
		String query = "SELECT count(*)  FROM  ONP_BROADCAST_MSG where  r_cre_time > (select DATE 'today')";
		Connection con = ConnectionManager.getConnection();
		ResultSet rs = null;
		PreparedStatement ps = null;
		long counter = 0;

		try {

			ps = con.prepareStatement(query);
			rs = ps.executeQuery();

			while (rs.next()) {
				counter = rs.getLong(1);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} finally {

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

			} catch (Exception e) {
				e.printStackTrace();
				// throw e;
			}
		}

		return counter;

	}
	

	public List<MessageFormatVO> findAllMessageformats() throws Exception, SQLException {

		String query = "SELECT MSGID,BRANCHID,MESSAGETEXT,R_CRE_ID,to_char(R_CRE_TIME,'DD-MM-YYYY HH24:MI:SS'),R_MOD_ID,to_char(R_MOD_TIME,'DD-MM-YYYY HH24:MI:SS') FROM  ONP_Bank_MSG_FORMAT";

		Connection con = ConnectionManager.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		MessageFormatVO msgFormatVO = null;

		List<MessageFormatVO> msgFormatlist = new ArrayList<MessageFormatVO>();

		try {
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();

			while (rs.next()) {
				msgFormatVO = new MessageFormatVO();

				msgFormatVO.setMsgId(rs.getString(1));
				msgFormatVO.setBranchID(rs.getString(2));
				msgFormatVO.setMessageText(rs.getString(3));
				msgFormatVO.setR_cre_id(rs.getString(4));
				msgFormatVO.setR_mod_id(rs.getString(6));
				msgFormatVO.setR_cre_Time(rs.getTimestamp(5));
				msgFormatVO.setR_mod_Time(rs.getTimestamp(7));

				msgFormatlist.add((msgFormatVO));

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} finally {

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

			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}

		return msgFormatlist;

	}

	public List<QuartzDetailsVO> quartzTriggerDetails() throws Exception, SQLException {
		List<QuartzDetailsVO> result = new ArrayList<QuartzDetailsVO>();

		String query = "SELECT TRIGGER_NAME,TRIGGER_GROUP,START_TIME,PREV_FIRE_TIME,NEXT_FIRE_TIME,MISFIRE_INSTR FROM QRTZ_TRIGGERS";

		Connection con = ConnectionManager.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		QuartzDetailsVO qdetVO = null;

		try {
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();

			while (rs.next()) {
				qdetVO = new QuartzDetailsVO();

				qdetVO.setTriggername(rs.getString(1));
				qdetVO.setTriggergroup(rs.getString(2));
				qdetVO.setStarttime(new Timestamp(new BigInteger(rs.getString(3)).longValue()));
				qdetVO.setPrevfiretime(new Timestamp(new BigInteger(rs.getString(4)).longValue()));
				qdetVO.setNextfiretime(new Timestamp(new BigInteger(rs.getString(5)).longValue()));
				qdetVO.setMisfirenos(rs.getInt(6));

				result.add((qdetVO));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} finally {

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

			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}

		return result;

	}

	public void update(MessageFormatVO msgformatVO) throws Exception, SQLException {
		String query = "UPDATE ONP_Bank_MSG_FORMAT SET MESSAGETEXT=?,branchid=?,R_MOD_TIME=?,R_CRE_ID=? WHERE MSGID=?";

		Connection con = ConnectionManager.getConnection();
		PreparedStatement ps = null;

		try {
			con.setAutoCommit(true);
			ps = con.prepareStatement(query);
			ps.setString(1, msgformatVO.getMessageText());
			ps.setString(2, msgformatVO.getBranchID());
			ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			ps.setString(4, msgformatVO.getR_mod_id());

			ps.setString(4, msgformatVO.getMsgId());
			int i = ps.executeUpdate();
			System.out.println(i + "rows updated in table.");

		} catch (SQLException e) {
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

			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}

	}

	public void insert(MessageFormatVO msgformatVO) throws Exception, SQLException {

		String query = "INSERT INTO ONP_Bank_MSG_FORMAT (bankID,branchid,msg_lang,msg_channel_id,messagetext,del_flg,r_cre_id,r_cre_time,r_mod_id,r_mod_time) VALUES(?,?,?,?,?,?,?,?,?,?)";

		Connection con = ConnectionManager.getConnection();
		PreparedStatement ps = null;

		try {

			con.setAutoCommit(true);
			ps = con.prepareStatement(query);

			ps.setString(1, msgformatVO.getBankID());
			ps.setString(2, msgformatVO.getBranchID());
			ps.setString(3, msgformatVO.getMsg_lang());
			ps.setString(4, msgformatVO.getMsg_Channel_ID());
			ps.setString(5, msgformatVO.getMessageText());
			ps.setString(6, "N");
			ps.setString(7, msgformatVO.getR_cre_id());
			ps.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
			ps.setString(9, msgformatVO.getR_mod_id());
			ps.setTimestamp(10, new Timestamp(System.currentTimeMillis()));

			ps.execute();

		} catch (SQLException e) {

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

			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}

	}

	public void insertPushMessage(PushMessageVO pushmsgVO) throws Exception, SQLException {
		String query = "INSERT INTO ONP_CUSTOM_MESSAGE_FORMAT (bank_id,branch_id,channel_id,message_text,msg_delivery_flg,r_cre_id,r_cre_time) VALUES(?,?,?,?,?,?,?)";
		Connection con = ConnectionManager.getConnection();
		PreparedStatement ps = null;

		try {

			con.setAutoCommit(true);
			ps = con.prepareStatement(query);

			ps.setString(1, pushmsgVO.getBankid());
			ps.setString(2, pushmsgVO.getBranchid());
			ps.setString(3, pushmsgVO.getChannelid());
			ps.setString(4, pushmsgVO.getTextmsg());
			ps.setString(5, pushmsgVO.getMsgdelflag());
			ps.setString(6, pushmsgVO.getRcreid());
			ps.setTimestamp(7, new Timestamp(System.currentTimeMillis()));

			ps.execute();

		} catch (SQLException e) {

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

			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}

	}
}