package com.dcs.bnotified.ONP;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.dcs.bnotified.ONP.dao.BroadcastMessageDAO;
import com.dcs.bnotified.ONP.dao.MessageFormatDAO;
import com.dcs.bnotified.ONP.dao.ONPAuditDAO;
import com.dcs.bnotified.ONP.data.BroadcastMessageVO;
import com.dcs.bnotified.common.BNotifyFatalException;
import com.dcs.bnotified.common.URLConstants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CloudMessagePushDispatcher implements Job {

	public static void main(String[] args) throws SQLException, Exception {

		String fileName = "CustomMsgPush"; // "BDAY";
		String path = "spring/batch/ONP/";
		String[] springConfig = { path + fileName + ".xml" };

		ApplicationContext context = new ClassPathXmlApplicationContext(springConfig);
		ArrayList<BroadcastMessageVO> pushList = new ArrayList<BroadcastMessageVO>();

		MessageFormatDAO msgFormatDAO = (MessageFormatDAO) context.getBean("MessageFormatDAO");
		BroadcastMessageDAO broadcastMsgDAO = (BroadcastMessageDAO) context.getBean("BroadcastMessageDAO");

		  Log log = LogFactory.getLog(CloudMessagePushDispatcher.class.getName());
		  
		  
		
		ONPAuditDAO auditDAO = null;
		Vector<String> batchstatus = null;
		batchstatus = new Vector<String>();
		log.debug("Fetchhing the list of messages to be pushed");
		pushList = broadcastMsgDAO.fetchMessagetoPush();
		msgFormatDAO.PRPMValList();
		auditDAO = (ONPAuditDAO) context.getBean("ONPAuditDAO");
		log.debug(pushList.size() +" No of Messages to be pushed to cloud");
		
		String URL = "";
		if (pushList.size() > 0) {

			Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
			String finalgson = gson.toJson(pushList);
			batchstatus.add(new String(pushList.size() + "No. of messages to be uploaded"));
			batchstatus.add(finalgson);
			System.out.println(finalgson);
			log.debug("JSON value is "+finalgson);
			URL = URLConstants.messagepushURL.replace("<<>>", MessageFormatDAO.PRPMVal.get("BANK_ID"));
			String responsejson = PushMessage(URL,finalgson,batchstatus);
			batchstatus.add("RESPONSE FROM messagePush URL");
			batchstatus.add(responsejson);
			System.out.println(responsejson);
			log.debug("Response from server is  "+responsejson);
			
			if (responsejson.contains("ERROR"))
			{
				batchstatus.add("Exception from Message upload .");
				auditDAO.insertAudit("PUSHMESSAGESERVICE", batchstatus);
				log.debug("Cloud Upload failed error while uploading");
				throw new BNotifyFatalException("Exception from Message upload .", new Exception());
			}
			
			log.debug("upload for Push notifications and sms");
			URL = URLConstants.messagepublishURL.replace("<<>>", MessageFormatDAO.PRPMVal.get("BANK_ID"));
			String publishresponsejson = PushMessage(URL,responsejson,batchstatus);
			batchstatus.add("RESPONSE FROM messagePush URL");
			batchstatus.add(publishresponsejson);
			log.debug("Response from server is " +publishresponsejson);
			System.out.println(publishresponsejson);
			broadcastMsgDAO.update(pushList);
			log.debug("Updated message status ");
		} else {
			batchstatus.add(new String(pushList.size() + " No Records to upload."));
			auditDAO.insertAudit("PUSHMESSAGESERVICE", batchstatus);
			throw new BNotifyFatalException("No Records to upload.", new Exception());
		}

		auditDAO.insertAudit("PUSHMESSAGESERVICE", batchstatus);
	}
	

	private static String PushMessage(String URL ,String json,Vector<String> batchstatus) throws Exception
	{
		String output="";
		String result="";
		try{
			
			URL url = new URL(URL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setDoOutput(true);
			OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
			batchstatus.add("Calling REST Service");
			out.write(json);
			out.close();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));
			
			
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				result+=output;
			}
			batchstatus.add("Received Response from REST Service");
			conn.disconnect();
		
		}catch (Exception e)
		{
			batchstatus.add("Exception while connecting to Server PushMessageService"+e.toString());
			
			e.printStackTrace();
			throw e;
		}	
		
		return result;
		
	}


	public void execute(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		String arr[] = null;
		try {
			CloudMessagePushDispatcher.main(arr);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
	}
	
	
}
