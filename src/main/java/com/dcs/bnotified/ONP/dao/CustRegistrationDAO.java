package com.dcs.bnotified.ONP.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.dcs.bnotified.ONP.data.CustRegistrationVO;

public class CustRegistrationDAO extends JdbcDaoSupport {

	public void insert(final CustRegistrationVO CustRegVO) {
		final String query = "insert into ONP_CUSTOMER_REG values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcTemplate().update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

				ps.setString(1, CustRegVO.getBank_ID());
				ps.setString(2, CustRegVO.getBranch_ID());
				ps.setLong(3, CustRegVO.getMobileNumber());
				ps.setString(4, CustRegVO.getAccount_number());
				ps.setString(5, CustRegVO.getCust_id());
				ps.setString(6, CustRegVO.getCust_category());
				ps.setBoolean(7, CustRegVO.getCust_enabled());
				ps.setString(8, CustRegVO.getDel_flg());
				ps.setString(9, CustRegVO.getR_cre_id());
				ps.setTimestamp(10, (new Timestamp(System.currentTimeMillis())));
				ps.setString(11, CustRegVO.getR_mod_id());
				ps.setTimestamp(12, (new Timestamp(System.currentTimeMillis())));
				ps.setString(13, CustRegVO.getFreetext1());
				ps.setString(14, CustRegVO.getFreetext2());
				ps.setString(15, CustRegVO.getFreetext3());

				return ps;
			}
		}, keyHolder);

	}

	public List<CustRegistrationVO> fetchNewCustReg(MessageFormatDAO msgFormatDAO) {

		List<CustRegistrationVO> newcustList = new ArrayList<CustRegistrationVO>();
		final String query = "SELECT bankId,branchId,mobileNumber FROM ONP_CUSTOMER_REG WHERE  mobileUsageActivated ='f'";

		ResultSet rs=null;
		Connection con = null;
		try {

			con = getJdbcTemplate().getDataSource().getConnection();
			
			rs = con.createStatement().executeQuery(query);

			CustRegistrationVO custregvo = null;
			
			while (rs.next()) {
				custregvo = new CustRegistrationVO();
				custregvo.setBank_ID(rs.getString(1));
				custregvo.setBranch_ID(rs.getString(2));
				custregvo.setMobileNumber(rs.getLong(3));
				custregvo.setCust_enabled(false);
				custregvo.setEntityName(msgFormatDAO.PRPMVal.get("ENTITY_SHORTNAME"));
				custregvo.setEntityLogo(msgFormatDAO.PRPMVal.get("ENTITY_LOGO"));
				newcustList.add(custregvo);
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

		return newcustList;
	}

	public void updatenewcust(ArrayList<CustRegistrationVO> newcustList) {
		CustRegistrationVO custregvo = null;

		for (int i = 0; i < newcustList.size(); i++) {
			custregvo = (CustRegistrationVO) newcustList.get(i);
			String sql = "UPDATE ONP_CUSTOMER_REG SET mobileUsageActivated='t' where mobileNumber="
					+ custregvo.getMobileNumber();
			getJdbcTemplate().update(sql);
		}

	}

}
