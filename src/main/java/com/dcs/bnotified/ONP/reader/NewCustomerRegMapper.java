package com.dcs.bnotified.ONP.reader;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import com.dcs.bnotified.ONP.dao.MessageFormatDAO;
import com.dcs.bnotified.ONP.data.CustRegistrationVO;


public class NewCustomerRegMapper  implements RowMapper<CustRegistrationVO>  {

	
	public CustRegistrationVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		CustRegistrationVO custregVO = new CustRegistrationVO();
		
		
		custregVO.setBank_ID(MessageFormatDAO.PRPMVal.get("BANK_ID"));
		custregVO.setMobileNumber(rs.getLong(1));
		custregVO.setBranch_ID(rs.getString(2));
		custregVO.setAccount_number(rs.getString(3));
		custregVO.setCust_id(rs.getString(4));
		custregVO.setCust_category(rs.getString(5));
		custregVO.setCust_enabled(false);
		custregVO.setDel_flg("N");
		custregVO.setR_cre_id("BATCH");
		custregVO.setR_mod_id("BATCH");
		custregVO.setFreetext1("");
		custregVO.setFreetext2("");
		custregVO.setFreetext3("");
		return custregVO;
		
	}
}
