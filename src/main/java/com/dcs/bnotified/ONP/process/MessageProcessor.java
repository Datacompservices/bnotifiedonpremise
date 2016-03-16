package com.dcs.bnotified.ONP.process;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.item.ItemProcessor;

import com.dcs.bnotified.ONP.Dispatcher;
import com.dcs.bnotified.ONP.DispatcherApp;
import com.dcs.bnotified.ONP.custom.MessgeCustom;
import com.dcs.bnotified.ONP.dao.BroadcastMessageDAO;
import com.dcs.bnotified.ONP.dao.CustomMsgBroadcastDAO;
import com.dcs.bnotified.ONP.dao.MessageFormatDAO;
import com.dcs.bnotified.ONP.data.BroadcastMessageVO;
import com.dcs.bnotified.ONP.data.CustomMsgBroadcastVO;
import com.dcs.bnotified.ONP.data.MessageFormatVO;
import com.dcs.bnotified.ONP.data.MessageVO;

import org.springframework.context.ApplicationContext;

/**
 * 
 * @author prashant R
 * 
 * In MessagePRocessor Message parameters are replaced with actual values from MessageVO. 
 * Replace values are done in MessageCustom and BroadcastMessageVO values are set..        
 * com.dcs.bnotified.ONP.dao.BroadcastMessageDAO method insert method stores in ONP_BROADCAST_MSG table. 
 * 
 * 
 *
 */
public class MessageProcessor  implements ItemProcessor<MessageVO,MessageVO> {

	public MessageVO process(MessageVO msgVO) throws Exception {
		
		List<MessageFormatVO> AllMessageFormat = new ArrayList<MessageFormatVO>();
		int MessageFormatCount = MessageFormatDAO.AllMessageFormat.size();
		BroadcastMessageVO broadMsgVO =  new BroadcastMessageVO();
		
		MessgeCustom msgCustom = new MessgeCustom(); 
	
		ApplicationContext context = null;
		boolean temp=false;
		if (MessageFormatCount !=0)
		{
			AllMessageFormat = MessageFormatDAO.AllMessageFormat;
		}
		else
		{
			//ConfigurableApplicationContext context =
              //      new ClassPathXmlApplicationContext("spring/batch/ONP/BDAY.xml");
	        //MessageFormatDAO msgFormatDAO = (MessageFormatDAO) context.getBean("MessageFormatDAO");
	        //msgFormatDAO.findAllMessageformats();
		}
		
		System.out.println("Count is "+MessageFormatCount);
		
		
	MessageFormatVO msgForatVO = null;
	System.out.println("msgVO.getMessageCode()"+msgVO.getMessageCode());
	
	if (msgVO.getMessageCode()!="0")
	{
			for (int i=0;i<MessageFormatCount;i++)
			{
				msgForatVO = null; 
				msgForatVO = new MessageFormatVO();
				msgForatVO = MessageFormatDAO.AllMessageFormat.get(i);
				
				System.out.println(msgVO.getMessageCode());
				System.out.println(msgForatVO.getMsgId());
				
					if (msgVO.getMessageCode().equals(msgForatVO.getMsgId()))
					{
						broadMsgVO.setEntityName(MessageFormatDAO.PRPMVal.get("ENTITY_SHORTNAME"));
						broadMsgVO.setMsgID(msgVO.getMessageCode());
						broadMsgVO.setBankID(MessageFormatDAO.PRPMVal.get("BANK_ID"));
						broadMsgVO.setBranchID(MessageFormatDAO.PRPMVal.get("DEFAULT_BRANCH_ID"));
						broadMsgVO.setChannel_id(MessageFormatDAO.PRPMVal.get("CHANNEL_ID"));
						broadMsgVO.setMsg_delivery_flg(temp);
						broadMsgVO.setMobile_number((msgVO.getMobileNumber()));
						broadMsgVO.setMsgText(msgForatVO.getMessageText());
						msgCustom.BroadCastMessage(broadMsgVO, msgVO);
						context =Dispatcher.getContext();
						BroadcastMessageDAO broadcastMsgDAO = (BroadcastMessageDAO) context.getBean("BroadcastMessageDAO");
						broadcastMsgDAO.insert(broadMsgVO);
						
					}
				}


	}

	if (msgVO.getMessageCode().equals("0"))
	{
		broadMsgVO.setEntityName(MessageFormatDAO.PRPMVal.get("ENTITY_SHORTNAME"));
		broadMsgVO.setMsgID(msgVO.getMessageCode());
		broadMsgVO.setBankID(MessageFormatDAO.PRPMVal.get("BANK_ID"));
		broadMsgVO.setBranchID(MessageFormatDAO.PRPMVal.get("DEFAULT_BRANCH_ID"));
		broadMsgVO.setChannel_id(MessageFormatDAO.PRPMVal.get("CHANNEL_ID"));
		broadMsgVO.setMsg_delivery_flg(temp);
		broadMsgVO.setMobile_number((msgVO.getMobileNumber()));
		context = DispatcherApp.getContext();
		ArrayList<CustomMsgBroadcastVO> Pushlist = DispatcherApp.getMessageList();
		CustomMsgBroadcastVO custMsgpush = null;
		if (Pushlist.size()>0)
	     {
			for (int i=0;i<Pushlist.size();i++)
			{
				 custMsgpush = new CustomMsgBroadcastVO();
				 custMsgpush =(CustomMsgBroadcastVO) Pushlist.get(i);
				 broadMsgVO.setMsgText(custMsgpush.getMessage_text());
				 BroadcastMessageDAO broadcastMsgDAO = (BroadcastMessageDAO) context.getBean("BroadcastMessageDAO");
				 broadcastMsgDAO.insert(broadMsgVO);
			}
	     }
}

	
	
	//BroadcastMessageDAO broadcastMsgDAO = (BroadcastMessageDAO) context.getBean("BroadcastMessageDAO");
	//msgCustom.BroadCastMessage(broadMsgVO, msgVO);
	//broadcastMsgDAO.insert(broadMsgVO);
	return null;
	}

}
