package com.dcs.bnotified.onpservice.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;

import com.dcs.bnotidfied.onpservice.util.ConnectionManager;
import com.dcs.bnotidfied.onpservice.util.PropertyManager;

public class AuditDAO {

	public void insertAudit(String IPAddress, String Servicename, @SuppressWarnings("rawtypes") Vector batchstatus,
			String trantype) throws Exception {
		final String query = "insert into ONP_AUDITTABLE values (?,?,?,?,?,?,?)";
		Connection con = ConnectionManager.getConnection();
		new PropertyManager().PRPMValList();
		PreparedStatement ps = null;
		try {
			con.setAutoCommit(true);
			ps = con.prepareStatement(query);

			String temp = "";
			for (int index = 0; index < batchstatus.size(); index++) {
				temp = batchstatus.elementAt(index).toString();
				ps.setString(1, PropertyManager.PRPMVal.get("BANK_ID"));
				ps.setString(2, Servicename);
				ps.setString(3, trantype);
				ps.setString(4, IPAddress);
				ps.setString(5, temp);
				ps.setString(6, "SU");
				ps.setTimestamp(7, (new Timestamp(System.currentTimeMillis())));
				ps.execute();
			}
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