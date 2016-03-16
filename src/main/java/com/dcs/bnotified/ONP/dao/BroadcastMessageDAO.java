/**
 * 
 */
package com.dcs.bnotified.ONP.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.dcs.bnotified.ONP.data.BroadcastMessageVO;

/**
 * @author prashant insert(BroadcastMessageVO ). Method insert a record in
 *         ONP_BROADCAST_MSG table. insert(BroadcastMessageVO ) is executed from
 *         com.dcs.bnotified.ONP.process.MessageProcessor process(MessageVO
 *         msgVO) method.
 *
 */
public class BroadcastMessageDAO extends JdbcDaoSupport {

	public void insert(final BroadcastMessageVO broadMsgVO) {
		final String query = "insert into ONP_BROADCAST_MSG values (?,?,?,?,?,?,?,?,?)";

		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcTemplate().update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(query);
				ps.setString(1, broadMsgVO.getMsgID());
				ps.setString(2, broadMsgVO.getBankID());
				ps.setString(3, broadMsgVO.getEntityName());
				ps.setString(4, broadMsgVO.getBranchID());
				ps.setString(5, broadMsgVO.getChannel_id());
				ps.setLong(6, broadMsgVO.getMobile_number());
				ps.setString(7, broadMsgVO.getMsgText());
				ps.setBoolean(8, broadMsgVO.getMsg_delivery_flg());
				ps.setTimestamp(9, (new Timestamp(System.currentTimeMillis())));
				return ps;
			}
		}, keyHolder);

	}

	public ArrayList<BroadcastMessageVO> fetchMessagetoPush() {

		List<BroadcastMessageVO> PushList = new ArrayList<BroadcastMessageVO>();
//		final String query = "SELECT bankId,ENTITYNAME,branchId,mobileNumber,notificationText,msgDelflag,r_cre_time FROM ONP_BROADCAST_MSG WHERE msgDelflag ='f'";
		final String query = "SELECT A.BANKID,A.ENTITYNAME,A.BRANCHID,A.MOBILEnUMBER,A.NOTIFICATIONTEXT,A.MSGDELFLAG,A.R_CRE_TIME FROM ONP_BROADCAST_MSG A WHERE A.MSGDELFLAG ='f' AND A.MOBILENUMBER NOT IN (SELECT DISTINCT MOBILENUMBER FROM ONP_DND_MOBILE_REG)";		
		
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getJdbcTemplate().getDataSource().getConnection();

			rs = con.createStatement().executeQuery(query);
			BroadcastMessageVO vo = null;
			
			while (rs.next()) {
				vo = null;
				vo = new BroadcastMessageVO();
				vo.setBankID(rs.getString(1));
				vo.setEntityName(rs.getString(2));
				vo.setBranchID(rs.getString(3));
				vo.setMobile_number(rs.getLong(4));
				vo.setMsgText(rs.getString(5));
				vo.setMsg_delivery_flg(rs.getBoolean(6));
				vo.setR_cre_time((rs.getTimestamp(7)));
				PushList.add(vo);
				

			}
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
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

		return (ArrayList<BroadcastMessageVO>) PushList;
	}

	public void update(ArrayList<BroadcastMessageVO> PushList) {
		BroadcastMessageVO broadMsgVO;

		for (int i = 0; i < PushList.size(); i++) {
			broadMsgVO = new BroadcastMessageVO();
			broadMsgVO = (BroadcastMessageVO) PushList.get(i);
			String sql = " UPDATE ONP_BROADCAST_MSG SET msgDelflag='Y' WHERE R_CRE_TIME ='" + broadMsgVO.getR_cre_time()
					+ "'";
			getJdbcTemplate().update(sql);
		}
	}
}
