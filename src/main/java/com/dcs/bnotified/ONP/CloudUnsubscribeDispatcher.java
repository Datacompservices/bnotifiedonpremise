package com.dcs.bnotified.ONP;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONObject;
import org.postgresql.util.PSQLException;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dcs.bnotidfied.onpservice.util.ConnectionManager;
import com.dcs.bnotified.ONP.dao.BroadcastMessageDAO;
import com.dcs.bnotified.ONP.dao.MessageFormatDAO;
import com.dcs.bnotified.ONP.dao.ONPAuditDAO;
import com.dcs.bnotified.common.BNotifyFatalException;
import com.dcs.bnotified.common.URLConstants;


public class CloudUnsubscribeDispatcher implements Job {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String fileName = "CustomMsgPush"; // "BDAY";
		String path = "spring/batch/ONP/";
		String[] springConfig = { path + fileName + ".xml" };

		ApplicationContext context = new ClassPathXmlApplicationContext(springConfig);
		//ArrayList<BroadcastMessageVO> pushList = new ArrayList<BroadcastMessageVO>();

		MessageFormatDAO msgFormatDAO = (MessageFormatDAO) context.getBean("MessageFormatDAO");
		BroadcastMessageDAO broadcastMsgDAO = (BroadcastMessageDAO) context.getBean("BroadcastMessageDAO");

		ONPAuditDAO auditDAO = null;
		Vector<String> batchstatus = null;
		batchstatus = new Vector<String>();
		
		String URL = "";
		URL = URLConstants.reportURL.replace("<<>>", MessageFormatDAO.PRPMVal.get("BANK_ID"));
		//URL = URLConstants.reportURL.replace("<<>>", "4");
		String responsejson="";
		try {
			responsejson = getCloudDet(URL,responsejson,batchstatus);
			System.out.println(responsejson);
			//Sample JSON RESPONSE from Server. if Status is false 'resubscrive'. and if it is true need to unsubscribe. 
			//{"status":"SUCCESS","errorDescription":"","custdet":[{"mobileNumber":9448011250,"status":true}]}
			JSONObject obj = new JSONObject(responsejson);
			JSONArray arr = obj.getJSONArray("custdet");
			
			long mobileNumber;
			boolean status=false;		
			
			if (arr.length() <=0)
			{
				batchstatus.add("No Records Found in unsubscribe upload");
				
				 throw new BNotifyFatalException("No Records Found in unsubscribe upload.", new Exception());
			}
			
			for (int i = 0; i < arr.length(); i++)
			{
				mobileNumber = arr.getJSONObject(i).getLong("mobileNumber");
				status = arr.getJSONObject(i).getBoolean("status");
			    System.out.println("JSON values are :"+mobileNumber+ "  : status is   "+status);
			    insertdndRecord(status,mobileNumber,"BATCH");
			}
						
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally{
			try {
				auditDAO = new ONPAuditDAO();
				auditDAO.insertAudit("CloudUnsubscribe", batchstatus);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

	}

	private static String getCloudDet(String URL, String responsejson, Vector<String> batchstatus) throws Exception {
		
		
		String output="";
		String result="";
		
		try{
			
			URL url = new URL(URL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setDoOutput(true);
			//OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
			batchstatus.add("Calling REST Service");
			//out.write(json);
			//out.close();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));
			
			
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				result+=output;
			}
			batchstatus.add("Received Response from REST Service");
			batchstatus.add(result);
			conn.disconnect();
		
		}catch (Exception e)
		{
			batchstatus.add("Exception while connecting to Server PushMessageService"+e.toString());
			
			e.printStackTrace();
			throw e;
		}	
		
		return result;
			
	}
	
	private static void insertdndRecord(boolean dndaction, long mobileNumber, String rcreid) {
		// TODO Auto-generated method stub

		System.out.println("dndaction" + dndaction);
		System.out.println("mobileNumber" + mobileNumber);
		System.out.println("rcreid" + rcreid);

		Vector<String> batchstatus = new Vector<String>();
		ONPAuditDAO auditdao = new ONPAuditDAO();
		String query = "";
		if (dndaction) {
			query = "insert into ONP_DND_MOBILE_REG values (?)";
		} else {
			query = "DELETE FROM ONP_DND_MOBILE_REG WHERE mobileNumber =?";
		}
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		Connection con = null;
		Connection con1 = null;
		
		try {
			con = ConnectionManager.getConnection();
			con1 = ConnectionManager.getConnection("HOST");
			ps = con.prepareStatement(query);
			ps1 = con1.prepareStatement(query);
			con.setAutoCommit(true);
			con1.setAutoCommit(true);
			System.out.println("printing the values of mobileNumbers are");
			
				if (mobileNumber > 0) {
					try {
						ps.setLong(1, mobileNumber);
						ps.execute();

						ps1.setLong(1, mobileNumber);
						ps1.execute();
					} catch (PSQLException psql) {
						System.out.println("SQLState is "+psql.getSQLState());
						if (psql.getErrorCode()==23505){
							batchstatus.addElement("REC ALLREADY EXISTS" + mobileNumber);
							
						} else {
							psql.printStackTrace();
						}
					}
				
				batchstatus.addElement("NEW REC" +mobileNumber);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
					con = null;
				}
				if (con1 != null) {
					con1.close();
					con1 = null;
				}

				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (ps1 != null) {
					ps1.close();
					ps1 = null;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		try {
			auditdao.insertAudit("CloudUnsubscribe", batchstatus);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		String arr[] = null;
		try {
			CloudUnsubscribeDispatcher.main(arr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
	}

}
