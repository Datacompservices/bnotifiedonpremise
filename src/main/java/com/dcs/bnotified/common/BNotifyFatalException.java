package com.dcs.bnotified.common;

public class BNotifyFatalException  extends Exception {

	private static final long serialVersionUID = 2008738746644475680L;

	public BNotifyFatalException(String s)
	{
		super(s);
		
	}
	
	public BNotifyFatalException(String s,Exception e)
	{
		super(s);
		
	}
	
}
