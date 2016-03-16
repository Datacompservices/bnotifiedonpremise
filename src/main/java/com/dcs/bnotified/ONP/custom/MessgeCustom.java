package com.dcs.bnotified.ONP.custom;

import com.dcs.bnotified.ONP.dao.MessageFormatDAO;
import com.dcs.bnotified.ONP.data.BroadcastMessageVO;
import com.dcs.bnotified.ONP.data.MessageVO;

public class MessgeCustom {

	public BroadcastMessageVO BroadCastMessage(BroadcastMessageVO broadMsgVO, MessageVO msgVO)
	{
		String temp="";
		int param;
		param= msgVO.getParams();
		temp= broadMsgVO.getMsgText();
				
		
		if(param==1)
		{
			temp = temp.replace("<<$#$>>",msgVO.getMsgBody1().trim());
		}
		
		else if(param==2)
		{
			temp = temp.replace("<<$#$>>",msgVO.getMsgBody1().trim());
			temp = temp.replace("<<$##$>>",msgVO.getMsgBody2().trim());
		}
		
		else if (param==3)
		{
			temp = temp.replace("<<$#$>>",msgVO.getMsgBody1().trim());
			temp = temp.replace("<<$##$>>",msgVO.getMsgBody2().trim());
			temp = temp.replace("<<$###$>>",msgVO.getMsgBody3().trim());
			
		}
		else if (param==4)
		{
			temp = temp.replace("<<$#$>>",msgVO.getMsgBody1().trim());
			temp = temp.replace("<<$##$>>",msgVO.getMsgBody2().trim());
			temp = temp.replace("<<$###$>>",msgVO.getMsgBody3().trim());
			temp = temp.replace("<<$####$>>",msgVO.getMsgBody4().trim());
			
		}
		
		else if (param==5)
		{
			temp = temp.replace("<<$#$>>",msgVO.getMsgBody1().trim());
			temp = temp.replace("<<$##$>>",msgVO.getMsgBody2().trim());
			temp = temp.replace("<<$###$>>",msgVO.getMsgBody3().trim());
			temp = temp.replace("<<$####$>>",msgVO.getMsgBody4().trim());
			temp = temp.replace("<<$#####$>>",msgVO.getMsgBody5().trim());
		}

		else if (param==6)
		{
			temp = temp.replace("<<$#$>>",msgVO.getMsgBody1().trim());
			temp = temp.replace("<<$##$>>",msgVO.getMsgBody2().trim());
			temp = temp.replace("<<$###$>>",msgVO.getMsgBody3().trim());
			temp = temp.replace("<<$####$>>",msgVO.getMsgBody4().trim());
			temp = temp.replace("<<$#####$>>",msgVO.getMsgBody5().trim());
			temp = temp.replace("<<$######$>>",msgVO.getMsgBody6().trim());
		}
		
		
		
		
			
		broadMsgVO.setMsgText(temp);
		return broadMsgVO;
		
	}
	
	
	
}
