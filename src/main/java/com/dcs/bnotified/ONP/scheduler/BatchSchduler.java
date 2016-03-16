package com.dcs.bnotified.ONP.scheduler;

import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.JobBuilder.*; 
import static org.quartz.SimpleScheduleBuilder.*; 
import static org.quartz.CronScheduleBuilder.*; 
import static org.quartz.CalendarIntervalScheduleBuilder.*; 
import static org.quartz.TriggerBuilder.*; 
import static org.quartz.DateBuilder.*;
import com.dcs.bnotified.ONP.CloudMessagePushDispatcher;
import com.dcs.bnotified.ONP.CloudNewRegUploadDispatcher;
import com.dcs.bnotified.ONP.CloudUnsubscribeDispatcher;

public class BatchSchduler {

	
	BatchSchduler()
	{
		
	}
	
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		
		// Grab the Scheduler instance from the Factory 
       

        // and start it off
        try {
        	
            SchedulerFactory schedulerFactory = new StdSchedulerFactory("./spring/batch/ONP/quartz.properties");
            // Retrieve a scheduler from schedule factory
            Scheduler sched = schedulerFactory.getScheduler();

        	  //sched.start();


            
        
        //sched.deleteJob(new JobKey("CUSTMSGPUSH", "set1"));
      	///sched.deleteJob(new JobKey("LOANPMTALERT", "set2"));
      	//sched.deleteJob(new JobKey("BDAY", "set1"));
      	//sched.deleteJob(new JobKey("DEPOMATALERT", "set2"));

            
            /*
        	  
              JobDetail job = newJob(ONPJob.class)
            	      .withIdentity("CloudPush", "TRAN") 
            	      .usingJobData("fileName", "BDAY")
            	      .usingJobData("path", ".\\spring\\batch\\ONP\\")
            	      .build();

                
            	  Trigger trigger = newTrigger()
            	      .withIdentity("BDAY", "set1")
            	      .startNow()
            	      .withSchedule(dailyAtHourAndMinute(16, 00))
            	      .build();
            	  
            	  
            	
                  JobDetail job1 = newJob(ONPJob.class)
                	      .withIdentity("DEPOMATALERT", "set2") 
                	      .usingJobData("fileName", "DEPOMATALERT")
                	      .usingJobData("path", ".\\spring\\batch\\ONP\\")
                	      .build();

                    
                Trigger trigger1 = newTrigger()
                	      .withIdentity("DEPOMATALERT", "set2")
                	      .startNow()
                	      .withSchedule(dailyAtHourAndMinute(16, 03))
                	      .build();
         	  
              */	  
            
        // sched.deleteJob(new JobKey("Unsubscription", "TRAN"));
         //sched.deleteJob(new JobKey("MessageUpload", "TRAN"));
         //sched.deleteJob(new JobKey("CloudPush", "TRAN"));
         //sched.deleteJob(new JobKey("CloudPush", "TRAN")); 
          
                JobDetail job2 = newJob(CloudUnsubscribeDispatcher.class)
              	      .withIdentity("Unsubscription", "TRAN") 
              	      .usingJobData("fileName", "BroadcastMessage")
              	    .usingJobData("path", ".\\spring\\batch\\ONP\\")
              	      .build();

                  
              Trigger trigger2 = newTrigger()
              	      .withIdentity("Unsubscription", "TRAN")
              	      .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                  	  .withIntervalInMinutes(20)
                  	  .repeatForever())
                  	  .build();
              
              sched.scheduleJob(job2, trigger2);              
                        
              
              JobDetail job3 = newJob(CloudMessagePushDispatcher.class)
              	      .withIdentity("MessageUpload", "TRAN") 
              	      .usingJobData("fileName", "MessageUpload")
              	    .usingJobData("path", ".\\spring\\batch\\ONP\\")
              	      .build();

    
              Trigger trigger3 = newTrigger()
            		  .withIdentity("MessageUpload", "TRAN")
            		  .startNow()
            		  .withSchedule(SimpleScheduleBuilder.simpleSchedule()
            		  .withIntervalInMinutes(5)
            		  .repeatForever())
            		  .build();
            	  
              sched.scheduleJob(job3, trigger3); 
              
              JobDetail job4 = newJob(ONPJob.class)
              	      .withIdentity("BroadcastMessage", "TRAN") 
              	      .usingJobData("fileName", "CustomMsgPush")
              	    .usingJobData("path", ".\\spring\\batch\\ONP\\")
              	      .build();

    
              Trigger trigger4 = newTrigger()
            		  .withIdentity("BroadcastMessage", "TRAN")
            		  .startNow()
            		  .withSchedule(SimpleScheduleBuilder.simpleSchedule()
            		  .withIntervalInMinutes(3)
            		  .repeatForever())
            		  .build();
            	  
              sched.scheduleJob(job4, trigger4); 
              
              
              JobDetail job5 = newJob(CloudNewRegUploadDispatcher.class)
              	      .withIdentity("NewCustomerRegistrationUpload", "TRAN") 
              	      .usingJobData("fileName", "NewCustomerRegistration")
              	    .usingJobData("path", ".\\spring\\batch\\ONP\\")
              	      .build();

    
              Trigger trigger5 = newTrigger()
            		  .withIdentity("NewCustomerRegistrationUpload", "TRAN")
            		  .startNow()
            		  .withSchedule(SimpleScheduleBuilder.simpleSchedule()
            		  .withIntervalInMinutes(15)
            		  .repeatForever())
            		  .build();
            	  
              sched.scheduleJob(job5, trigger5); 
              
              //NewCustomerRegistration

        	 
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

      

	}

}
