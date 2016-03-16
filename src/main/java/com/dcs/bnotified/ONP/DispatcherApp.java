package com.dcs.bnotified.ONP;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.dcs.bnotified.ONP.dao.CustomMsgBroadcastDAO;
import com.dcs.bnotified.ONP.dao.MessageFormatDAO;
import com.dcs.bnotified.ONP.data.CustomMsgBroadcastVO;
import com.dcs.bnotified.common.BNotifyNonFatalException;

/**
 * 
 *
 */
public class DispatcherApp {

	static ApplicationContext Buffercontext = null;
	static String PushMessage = "";
	Vector batchstatus = null;

	public static void main(String[] args) throws Exception {

		String fileName =args[0];//"CustomMsgPush"; //"BDAY";//  args[0];//"CustomMsgPush";//"CustomMsgPush";// args[0]; //"BDAY"
											// //NewCustRegisteration;
		String path = args[1];// "spring/batch/ONP/";// args[1];
System.out.println("filepath is "+ path);
System.out.println("file name is "+ fileName);
		
		
		if (fileName.equals("CustomMsgPush")) {
			new DispatcherApp().run1(fileName, path);
		} else {
			Dispatcher dis = new Dispatcher(path, fileName, "Y");
		}
	}

	public static void setPushMessage(String PushMessage) {
		DispatcherApp.PushMessage = PushMessage;
	}

	public static String getPushMessage() {
		return PushMessage;
	}

	static ArrayList<CustomMsgBroadcastVO> PushListVO = new ArrayList<CustomMsgBroadcastVO>();

	public static void setMessageList(ArrayList<CustomMsgBroadcastVO> PushListVO1) {
		PushListVO = PushListVO1;
	}

	public static ArrayList<CustomMsgBroadcastVO> getMessageList() {
		return PushListVO;
	}

	public void run1(String fileName, String path) throws Exception {

		String[] springConfig = { path + fileName + ".xml" };

		ApplicationContext context = new ClassPathXmlApplicationContext(springConfig);
		Buffercontext = context;
		MessageFormatDAO msgFormatDAO = (MessageFormatDAO) context.getBean("MessageFormatDAO");

		Job job = (Job) context.getBean("testJob");

		try {
			msgFormatDAO.findAllMessageformats();
			msgFormatDAO.PRPMValList();
		} catch (SQLException e1) {
			e1.printStackTrace();
			throw e1;
		} catch (Exception e1) {
			e1.printStackTrace();
			throw e1;
		}

		CustomMsgBroadcastDAO custMsgPush = (CustomMsgBroadcastDAO) context.getBean("CustomMsgBroadcastDAO");

		ArrayList<CustomMsgBroadcastVO> Pushlist = new ArrayList<CustomMsgBroadcastVO>();

		Pushlist = custMsgPush.getCustomMsgtoBroadcast();

		JobParameters param = new JobParameters();

		JobExecution execution;
		setMessageList(Pushlist);

		System.out.println("Count to send message is " + (Pushlist.size()));
		try {

			if (Pushlist.size() > 0) {
				JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
				execution = jobLauncher.run(job, param);
				System.out.println("Exit Status : " + execution.getStatus());
				System.out.println("Exit Status : " + execution.getAllFailureExceptions());
				System.out.println("Lst run time is:" + execution.getEndTime());

				CustomMsgBroadcastVO custMsgpush1 = null;
				for (int i = 0; i < Pushlist.size(); i++) {
					custMsgpush1 = new CustomMsgBroadcastVO();
					custMsgpush1 = (CustomMsgBroadcastVO) Pushlist.get(i);
					custMsgPush.update(custMsgpush1);
				}

			} else {
				try {
					throw new BNotifyNonFatalException("No Notification to push");
				} catch (BNotifyNonFatalException e) {

					e.printStackTrace();

				}
			}

		} catch (JobExecutionAlreadyRunningException e) {

			e.printStackTrace();
		} catch (JobRestartException e) {

			e.printStackTrace();
		} catch (JobInstanceAlreadyCompleteException e) {

			e.printStackTrace();
		} catch (JobParametersInvalidException e) {

			e.printStackTrace();
		}

	}

	public static ApplicationContext getContext() {
		return Buffercontext;
	}

	/*
	 * 
	 * @SuppressWarnings("unchecked") public void run() {
	 * 
	 * batchstatus = new Vector();
	 * 
	 * String fileName ="NewCustRegisteration"; // String path
	 * ="spring/batch/ONP/";
	 * 
	 * String[] springConfig = { path+fileName+".xml"};
	 * 
	 * batchstatus.add(fileName+"| Started at :" + new
	 * Timestamp(System.currentTimeMillis()));
	 * 
	 * 
	 * ApplicationContext context = new
	 * ClassPathXmlApplicationContext(springConfig); Buffercontext = context; //
	 * MessageFormatDAO msgFormatDAO = (MessageFormatDAO)
	 * context.getBean("MessageFormatDAO");
	 * //msgFormatDAO.findAllMessageformats(); try { //
	 * msgFormatDAO.PRPMValList(); } catch (Exception e1) { // TODO
	 * Auto-generated catch block e1.printStackTrace(); }
	 * 
	 * //System.out.println(msgFormatDAO.getCurrentTime());
	 * //System.out.println(msgFormatDAO.getLastRunTime());
	 * 
	 * 
	 * JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
	 * Job job = (Job) context.getBean("testJob"); JobExecution execution =
	 * null; try {
	 * 
	 * @SuppressWarnings("unused") Map<String, JobParameter> parameters =new
	 * HashMap<String, JobParameter>(); //parameters.put("fromDate",new
	 * JobParameter(msgFormatDAO.getLastRunTime()));
	 * //parameters.put("toDate",new
	 * JobParameter(msgFormatDAO.getCurrentTime()));
	 * 
	 * //JobParameters param = new JobParameters(parameters);
	 * 
	 * JobParameters param = new JobParameters();
	 * 
	 * batchstatus.add(fileName+"| Parameter values are: "+""); execution =
	 * jobLauncher.run(job, param); //execution = sendExecution(execution);
	 * batchstatus.add(fileName+"| Batch Status :"+execution.getStatus());
	 * 
	 * 
	 * List exceptionList = execution.getAllFailureExceptions();
	 * batchstatus.add(fileName+"| Batch Exception Count :"
	 * +exceptionList.size());
	 * 
	 * for (int i=0;i<exceptionList.size();i++) { Throwable t = (Throwable)
	 * exceptionList.get(i); batchstatus.add(fileName+"|Exception from Batch "
	 * +fileName+" "+ t.getStackTrace());
	 * 
	 * }
	 * 
	 * System.out.println("Exit Status : " + execution.getStatus());
	 * System.out.println("Exit Status : " +
	 * execution.getAllFailureExceptions()); //System.out.println(
	 * "Last run time is:"+execution.getId());
	 * 
	 * 
	 * 
	 * } catch (JobExecutionAlreadyRunningException e) {
	 * 
	 * batchstatus.add(fileName+"|Exception from Batch "+fileName+" "
	 * +e.getStackTrace()); e.printStackTrace(); } catch (JobRestartException e)
	 * {
	 * 
	 * batchstatus.add(fileName+"|Exception from Batch "+fileName+" "
	 * +e.getStackTrace()); e.printStackTrace(); } catch
	 * (JobInstanceAlreadyCompleteException e) {
	 * 
	 * batchstatus.add(fileName+"|Exception from Batch "+fileName+" "
	 * +e.getStackTrace()); e.printStackTrace(); } catch
	 * (JobParametersInvalidException e) {
	 * 
	 * batchstatus.add("Exception from Batch "+fileName+" "+e.getStackTrace());
	 * e.printStackTrace();
	 * 
	 * }catch (RestClientException e) {
	 * 
	 * batchstatus.add(fileName+"|Exception from Batch "+fileName+" "
	 * +e.fillInStackTrace().toString()); e.printStackTrace();
	 * 
	 * } catch (Exception e) { batchstatus.add(fileName+"|Exception from Batch "
	 * +fileName+" "+e.fillInStackTrace().toString()); e.printStackTrace(); }
	 * 
	 * finally{ System.out.println("Done"); batchstatus.add(fileName+
	 * "| Batch StartTime :"+new Timestamp(execution.getStartTime().getTime()));
	 * batchstatus.add(fileName+"| Batch Endtime :"+new
	 * Timestamp(execution.getEndTime().getTime())); ONPAuditDAO auditDAO =
	 * (ONPAuditDAO) context.getBean("ONPAuditDAO");
	 * auditDAO.insertAudit(fileName, batchstatus); }
	 * 
	 * 
	 * }
	 */

}
