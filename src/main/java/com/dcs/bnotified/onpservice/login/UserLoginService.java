package com.dcs.bnotified.onpservice.login;


import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.core.Application;

import com.dcs.bnotified.onpservice.install.AppInstallerService;
import com.dcs.bnotified.onpservice.message.MessageService;


public class UserLoginService extends Application {

	 private Set<Object> singletons = new HashSet();
	    private Set<Class<?>> empty = new HashSet();
	 
	    public UserLoginService() throws SQLException, Exception {
	        // ADD YOUR RESTFUL RESOURCES HERE
	        this.singletons.add(new UserLogin());
	        this.singletons.add(new MessageService());
	        this.singletons.add(new AppInstallerService());
	    }
	 
	    public Set<Class<?>> getClasses()
	    {
	        return this.empty;
	    }
	 
	    public Set<Object> getSingletons()
	    {
	        return this.singletons;
	    }
	
}