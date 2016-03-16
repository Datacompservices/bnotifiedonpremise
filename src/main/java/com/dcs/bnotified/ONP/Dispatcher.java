package com.dcs.bnotified.ONP;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.client.RestClientException;

import com.dcs.bnotified.ONP.dao.MessageFormatDAO;
import com.dcs.bnotified.ONP.dao.ONPAuditDAO;

public class Dispatcher {
	
	public Dispatcher(String Path, String Batchname, String retrivelastrundate) {
		run(Path,Batchname,retrivelastrundate);
	}
	
	public static void main(String[] args) {
		//Dispatcher obj = new Dispatcher();
		// obj.run(args[1],args[2],args[3]);
	}

	static ApplicationContext Buffercontext = null;
	static String PushMessage="";
	Vector batchstatus=null;

	
	@SuppressWarnings("unchecked")
	private void run(String Path, String Batchname, String retrivelastrundate) {

		String[] springConfig = { Path+Batchname+".xml"};
		
		ApplicationContext context = null;
		Buffercontext  = null;
		MessageFormatDAO msgFormatDAO = null;
		JobExecution execution = null;
		Map<String, JobParameter> parameters = null;
		ONPAuditDAO auditDAO = null;

		
		 try{
			  //System.out.println(" Pos 1");
			    batchstatus = new Vector();
				batchstatus.add(Batchname+"| Started at :" + new Timestamp(System.currentTimeMillis()));
				//System.out.println(" Pos 1.1");
				context = new ClassPathXmlApplicationContext(springConfig);
				//System.out.println(" Pos 1.2");
			 	Buffercontext = context;
			 	msgFormatDAO = (MessageFormatDAO) context.getBean("MessageFormatDAO");
			 	msgFormatDAO.findAllMessageformats();
			 	msgFormatDAO.PRPMValList();
			 	
			 	
				JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
				Job job = (Job) context.getBean("testJob");
				parameters =new HashMap<String, JobParameter>(); 
				
				auditDAO = (ONPAuditDAO) context.getBean("ONPAuditDAO");
				Timestamp ts_fromdate = auditDAO.getBatchLastRunTime(Batchname);
			//	System.out.println(" Pos 1.3");
				
				parameters.put("fromDate",new JobParameter(ts_fromdate));
				parameters.put("toDate",new JobParameter(new Timestamp(System.currentTimeMillis())));
				
				batchstatus.add(Batchname+"| Parameter values are: "+ts_fromdate);
				//System.out.println(" Pos 1.4");
				JobParameters param = new JobParameters(parameters);
				execution = jobLauncher.run(job, param);
				//System.out.println(" Pos 2");
				
				System.out.println("Exit Status : " + execution.getStatus());
				System.out.println("Exit Status : " + execution.getAllFailureExceptions());

		 }catch(BeansException e)
		 {
				e.printStackTrace();	
			 execution.addFailureException(e);
	
		 }catch(SQLException e)
		 	{	e.printStackTrace();
			 execution.addFailureException(e);
			 
		 } catch (Exception e) {
			 e.printStackTrace();
			 execution.addFailureException(e);
			
		}
		 
	 finally{
			 batchstatus.add(Batchname+"| Batch Status :"+execution.getStatus());
				List exceptionList = execution.getAllFailureExceptions();
				batchstatus.add(Batchname+"| Batch Exception Count :"+exceptionList.size());
				
				for (int i=0;i<exceptionList.size();i++)
				{
					Throwable t = (Throwable) exceptionList.get(i);
					batchstatus.add(Batchname+"|Exception from Batch "+Batchname+" "+ t.getStackTrace());
					
				}
							
				System.out.println("Exit Status : " + execution.getStatus());
				System.out.println("Exit Status : " + execution.getAllFailureExceptions());
				auditDAO.insertAudit(Batchname, batchstatus);
			 
			 
		 }
	}

    public static void setPushMessage(String PushMessage)
    {
    	DispatcherApp.PushMessage = PushMessage;
    }
    
    public static String getPushMessage()
    {
    	return PushMessage;
    }


    public static ApplicationContext  getContext()
    {
    	return Buffercontext;
    }
  }
