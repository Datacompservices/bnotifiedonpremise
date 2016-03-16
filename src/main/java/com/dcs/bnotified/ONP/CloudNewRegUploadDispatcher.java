/**
 * 
 */
package com.dcs.bnotified.ONP;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.json.JSONObject;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dcs.bnotified.ONP.dao.CustRegistrationDAO;
import com.dcs.bnotified.ONP.dao.MessageFormatDAO;
import com.dcs.bnotified.ONP.dao.ONPAuditDAO;
import com.dcs.bnotified.ONP.data.CustRegistrationVO;
import com.dcs.bnotified.common.BNotifyFatalException;
import com.dcs.bnotified.common.URLConstants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;




/**
 * @author prashant
 *
 */
public class CloudNewRegUploadDispatcher implements Job {

	/**
	 * @param args
	 * @throws Exception
	 * @throws SQLException
	 */
	public static void main(String[] args) throws SQLException, Exception {

		String fileName =  "NewCustCloudPush"; // "BDAY";
		String path = "spring/batch/ONP/";
		String[] springConfig = { path + fileName + ".xml" };

		ApplicationContext context = new ClassPathXmlApplicationContext(springConfig);
		ArrayList<CustRegistrationVO> pushList = new ArrayList<CustRegistrationVO>();

		MessageFormatDAO msgFormatDAO = (MessageFormatDAO) context.getBean("MessageFormatDAO");
		CustRegistrationDAO broadcastMsgDAO = (CustRegistrationDAO) context.getBean("CustRegistrationDAO");

		ONPAuditDAO auditDAO = null;
		Vector<String> batchstatus = null;
		batchstatus = new Vector<String>();
		msgFormatDAO.PRPMValList();
		auditDAO = (ONPAuditDAO) context.getBean("ONPAuditDAO");
		pushList = (ArrayList<CustRegistrationVO>) broadcastMsgDAO.fetchNewCustReg(msgFormatDAO);

		if (pushList.size() > 0) {
			Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
			String finalgson = gson.toJson(pushList);
			batchstatus.add(new String(pushList.size() + "No. of registered users uploaded"));
			batchstatus.add(finalgson);
			System.out.println(finalgson);
			String responsejson = postJson(finalgson, msgFormatDAO, batchstatus);
			System.out.println(responsejson);
			batchstatus.add(responsejson);
			Gson gson1 = new Gson();
			
			CustRegistrationVO[] custretvo = gson.fromJson(responsejson, CustRegistrationVO[].class);
			System.out.println(custretvo.length);
			
			if (responsejson.contains("ERROR"))
			{
				batchstatus.add("Exception from Registeration upload.");
				auditDAO.insertAudit("CloudNewRegUploadDispatcher", batchstatus);
				throw new BNotifyFatalException("Exception from Registeration upload.", new Exception());
			}

			
			for (int counter = 0;counter<custretvo.length;counter++)
			{
				System.out.println("_id :"+(custretvo[counter].get_id())+"");
			}
			
			broadcastMsgDAO.updatenewcust(pushList);

		} else {
			batchstatus.add(new String(pushList.size() + " No Records to upload."));
			auditDAO.insertAudit("PUSHMESSAGESERVICE", batchstatus);
			throw new BNotifyFatalException("No Records to upload.", new Exception());

		}

		auditDAO.insertAudit("CloudNewRegUploadDispatcher", batchstatus);

	}
	
	private static String postJson(String json,MessageFormatDAO msgFormatDAO,Vector<String> batchstatus) throws Exception
	{
		String output="";
		String result="";
		try{
			String tempURL =URLConstants.registrationURL.replace("<<>>", MessageFormatDAO.PRPMVal.get("BANK_ID"));
			System.out.println(tempURL);
			URL url = new URL(tempURL);
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
		String args[]=null;
		try {
			CloudNewRegUploadDispatcher.main(args);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}






