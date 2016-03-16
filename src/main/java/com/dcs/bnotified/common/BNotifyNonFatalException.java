package com.dcs.bnotified.common;

public class BNotifyNonFatalException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2008738746644475680L;

	public BNotifyNonFatalException(String s)
	{
		super(s);
		
	}
	
	public BNotifyNonFatalException(String s,Exception e)
	{
		super(s);
		
	}
	
}
