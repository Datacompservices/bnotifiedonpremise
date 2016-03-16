package com.dcs.bnotified.ONP.scheduler;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

import com.dcs.bnotified.ONP.DispatcherApp;

public class ONPJob   implements Job{
	
	@SuppressWarnings("null")
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		String args[] = new String[2];
    	JobKey key = context.getJobDetail().getKey();
    	JobDataMap dataMap = context.getJobDetail().getJobDataMap();

    	
		args[0] = dataMap.getString("fileName");
		args[1] = dataMap.getString("path");
		
		try {
			DispatcherApp.main(args);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
