package com.dcs.bnotified.ONP.reader;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.dcs.bnotified.ONP.data.MessageVO;

/**
 * 
 * @author prashant R
 * 
 * The resultset of query fetch is mapped to om.dcs.bnotified.ONP.data.MessageVO in MessageRowMApper. MessageVO accepts parameters 1 to 8.
 * In query 1st parameter is mobile number,2nd is message code in ONP_Bank_MSG_FORMAT table. 
 * 3nd parameter is no of output parameters. In XML 2nd parameter message code is '0' It is custom message push message.  
 *  
 *
 */

public class MessageRowMApper implements RowMapper<MessageVO> {

	public MessageVO mapRow(ResultSet rs, int rowNum) throws SQLException {

		MessageVO msgVO = null;

	
		msgVO = new MessageVO();
		
		msgVO.setMessageCode(rs.getString(2));
		msgVO.setParams(Integer.parseInt(rs.getString(3)));
		msgVO.setMobileNumber(rs.getLong(1));
		System.out.println(msgVO.getMobileNumber());

		if (msgVO.getParams()==1)
		{
			msgVO.setMsgBody1(rs.getString(4));
		}
		else if (msgVO.getParams()==2)
		{
			msgVO.setMsgBody1(rs.getString(4));
			msgVO.setMsgBody2(rs.getString(5));
			
		}
		
		else if (msgVO.getParams()==3)
		{
			msgVO.setMsgBody1(rs.getString(4));
			msgVO.setMsgBody2(rs.getString(5));
			msgVO.setMsgBody3(rs.getString(6));
			
		} 
		else if (msgVO.getParams()==4)
		{
			msgVO.setMsgBody1(rs.getString(4));
			msgVO.setMsgBody2(rs.getString(5));
			msgVO.setMsgBody3(rs.getString(6));
			msgVO.setMsgBody4(rs.getString(7));
			
		}
	
		
		else if (msgVO.getParams()==5)
		{
			msgVO.setMsgBody1(rs.getString(4));
			msgVO.setMsgBody2(rs.getString(5));
			msgVO.setMsgBody3(rs.getString(6));
			msgVO.setMsgBody4(rs.getString(7));
			msgVO.setMsgBody5(rs.getString(8));
			
		}
	
	

		else if (msgVO.getParams()==6)
		{
			msgVO.setMsgBody1(rs.getString(4));
			msgVO.setMsgBody2(rs.getString(5));
			msgVO.setMsgBody3(rs.getString(6));
			msgVO.setMsgBody4(rs.getString(7));
			msgVO.setMsgBody5(rs.getString(8));
			msgVO.setMsgBody6(rs.getString(9));
			
		}


		else if (msgVO.getParams()==7)
		{
			msgVO.setMsgBody1(rs.getString(4));
			msgVO.setMsgBody2(rs.getString(5));
			msgVO.setMsgBody3(rs.getString(6));
			msgVO.setMsgBody4(rs.getString(7));
			msgVO.setMsgBody5(rs.getString(8));
			msgVO.setMsgBody6(rs.getString(9));
			msgVO.setMsgBody6(rs.getString(10));
			
		}
			
		return msgVO;
	}
	
	
	
	
}
