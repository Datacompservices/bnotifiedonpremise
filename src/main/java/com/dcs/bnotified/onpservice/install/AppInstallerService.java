package com.dcs.bnotified.onpservice.install;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import com.dcs.bnotidfied.onpservice.util.ConnectionManager;
import com.dcs.bnotified.ONP.CloudNewRegUploadDispatcher;
import com.dcs.bnotified.ONP.scheduler.ONPJob;

import static org.quartz.JobBuilder.*; 
import static org.quartz.CronScheduleBuilder.*; 
import static org.quartz.TriggerBuilder.*;
@Path("install")
public class AppInstallerService {

	@Produces({ "application/text" })
	@GET
	@Path("updateprpm/{entityId}/{entityName}/{branchname}/{filePath}")
	public String updateprpm(@PathParam("entityId") final String entityId,
			@PathParam("entityName") final String entityName, @PathParam("branchname") final String branchname,
			@PathParam("filePath") final String filePath) throws SQLException, Exception {
		//// entityid, entityname, entitybranchname, fileuploadpath

		Connection con = ConnectionManager.getConnection();
		con.setAutoCommit(true);
		PreparedStatement st = null;
		
        String arr[] = new String[4];
        
        arr[0] ="UPDATE ONP_Property_manager set PROPERTY_VALUE='" + entityId + "' WHERE PROPERTY_NAME='BANK_ID'";
        arr[1] = "UPDATE ONP_Property_manager set PROPERTY_VALUE='" + entityName+ "' WHERE PROPERTY_NAME='ENTITY_SHORTNAME'";
        arr[2] = "UPDATE ONP_Property_manager set PROPERTY_VALUE='" + branchname+ "' WHERE PROPERTY_NAME='DEFAULT_BRANCH_ID'";
		arr[3] ="UPDATE ONP_Property_manager set PROPERTY_VALUE='" + filePath+ "' WHERE PROPERTY_NAME='UPLOAD_FILE_PATH'";
		
		try {

			for (int i=0;i<=3;i++)
			{
				System.out.println(arr[i]);
				st = con.prepareStatement(arr[i]);
				st.executeUpdate();
					
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}

		finally {

			try {
				if (st != null) {
					st.close();
					st = null;
				}
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}

		return "SUCCESS";

	}
	
	
	@Produces({ "application/json" })
	@GET
	@Path("updatequartzschedule/{schedulevalue}")
	public String updatequartzscheduler(@PathParam("schedulevalue") final String scheduleValue,@Context HttpServletRequest req)
	{
		
        SchedulerFactory schedulerFactory;
		try {
			
			String quartzfilepath= req.getSession().getServletContext().getInitParameter("quartz:config-file");
			System.out.println(quartzfilepath);
			schedulerFactory = new StdSchedulerFactory(quartzfilepath);
			//schedulerFactory = new StdSchedulerFactory("/home/prashant/workspace/DCSApp/src/main/resources/spring/batch/ONP/quartz.properties");
			//schedulerFactory = new StdSchedulerFactory(".\\spring\\batch\\ONP\\quartz.properties");
	        Scheduler sched = schedulerFactory.getScheduler();
	        //String filePath ="\\home\\prashant\\workspace\\DCSApp\\src\\main\\resources\\spring\\batch\\ONP\\"; 
	        String filePath =".\\spring\\batch\\ONP\\";
	        String temp = "";
	        
	        // Expecting Sting as 
	        //BDAY@#@BOD@#@12@#@0
	        //BDAY@#@BOD@#@10@#@0

	        	temp = scheduleValue;
	        	System.out.println(temp);
	        	String arr1[] = temp.split("@");
	        	
	        	System.out.println(arr1[0]);
	        	System.out.println(arr1[1]);
	        	System.out.println(arr1[2]);
	        	
	        	JobDetail job = null;
	        	
	        	if (arr1[0].equals("MessageUpload"))
	        	{
	        		job = newJob(com.dcs.bnotified.ONP.CloudMessagePushDispatcher.class)
		            	      .withIdentity(arr1[0], arr1[1]) 
		            	      .usingJobData("fileName", arr1[0])
		            	      .usingJobData("path", filePath)
		            	      .build();

	        		
	        	} else if (arr1[0].equals("NewCustomerRegistration"))
	        	{
	        		job = newJob(com.dcs.bnotified.ONP.CloudNewRegUploadDispatcher.class)
		            	      .withIdentity(arr1[0], arr1[1]) 
		            	      .usingJobData("fileName", arr1[0])
		            	      .usingJobData("path", filePath)
		            	      .build();

	        		
	        	}
	        	else if (arr1[0].equals("Unsubscription"))
	        	{
	        		job = newJob(com.dcs.bnotified.ONP.CloudUnsubscribeDispatcher.class)
		            	      .withIdentity(arr1[0], arr1[1]) 
		            	      .usingJobData("fileName", arr1[0])
		            	      .usingJobData("path", filePath)
		            	      .build();

	        		
	        	}	        	
	        	else
	        	{
	        		job = newJob(com.dcs.bnotified.ONP.scheduler.ONPJob.class)
		            	      .withIdentity(arr1[0], arr1[1]) 
		            	      .usingJobData("fileName", arr1[0])
		            	      .usingJobData("path", filePath)
		            	      .build();

	        	}
	        	
	        	
	        	
	        	
	              
	              Trigger trigger = null;
	              	if (arr1[1].equals("TRAN"))
	              	{
	                    trigger = newTrigger()
	                  		  .withIdentity(arr1[0], arr1[1]) 
	                  		  .startNow()
	                  		  .withSchedule(SimpleScheduleBuilder.simpleSchedule()
	                  		  .withIntervalInMinutes(Integer.parseInt(arr1[2]))
	                  		  .repeatForever())
	                  		  .build();
	              	
	              	}
	              	else
	              	{
	              		
	            	  trigger = newTrigger()
	            		  .withIdentity(arr1[0], arr1[1]) 
	            	      .startNow()
	            	      .withSchedule(dailyAtHourAndMinute(Integer.parseInt(arr1[2]),Integer.parseInt(arr1[3])))
	            	      .build();
	              	}
	              	sched.scheduleJob(job, trigger);
	       
	        
	        /** just for reference ..... 
	        JobDetail job = newJob(ONPJob.class)
          	      .withIdentity("BDAY", "set1") 
          	      .usingJobData("fileName", "BDAY")
          	      .usingJobData("path", ".\\spring\\batch\\ONP\\")
          	      .build();

              
          	  Trigger trigger = newTrigger()
          	      .withIdentity("BDAY", "set1")
          	      .startNow()
          	      .withSchedule(dailyAtHourAndMinute(16, 00))
          	      .build();
          	      
          	      
          	                        
              Trigger trigger3 = newTrigger()
            		  .withIdentity("CUSTMSGPUSH", "set1")
            		  .startNow()
            		  .withSchedule(SimpleScheduleBuilder.simpleSchedule()
            		  .withIntervalInMinutes(30)
            		  .repeatForever())
            		  .build();

	        
	        **/
	        
	        
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        // Retrieve a scheduler from schedule factory

		return "{'status':'SUCCESS'}";
		
	}
		
	

}
