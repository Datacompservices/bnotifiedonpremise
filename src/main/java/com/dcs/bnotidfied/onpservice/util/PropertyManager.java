package com.dcs.bnotidfied.onpservice.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;

public class PropertyManager {

	public static Hashtable<String, String> PRPMVal = new Hashtable<String, String>();

	public void PRPMValList() throws SQLException, Exception {

		

		String query = "select PROPERTY_NAME,PROPERTY_VALUE from ONP_Property_manager";

		Connection con = null;
		ResultSet rs = null;

		try {

			if (true) {

				con = ConnectionManager.getConnection();
				rs = con.createStatement().executeQuery(query);

				while (rs.next()) {
					PRPMVal.put(rs.getString(1), rs.getString(2));

				}

				if (PRPMVal.size() <= 0) {
					try {
						throw new Exception("No Records Fetch in ONP_Property_manager table.");
					} catch (Exception e) {
						System.exit(0);
					}
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
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
				throw e;
			}

		}
	}
}
