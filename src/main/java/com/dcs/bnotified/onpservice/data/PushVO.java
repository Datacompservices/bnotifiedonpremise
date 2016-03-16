package com.dcs.bnotified.onpservice.data;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@SuppressWarnings("restriction")
@XmlRootElement(name="pushVO")
@XmlAccessorType(XmlAccessType.NONE)

public class PushVO implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public PushVO(){
		
	}
	
	
	public PushVO(long [] mobileNumber, String message,String rcreid){
		this.mobileNumber =mobileNumber;
		this.message=message;
		this.rcreid =rcreid;
		
	}
	@XmlElement
	public long[] getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(long[] mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	 @XmlElement(name="message")
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	 @XmlElement(name="rcreid")
	public String getRcreid() {
		return rcreid;
	}
	public void setRcreid(String rcreid) {
		this.rcreid = rcreid;
	}
	
	long mobileNumber[];

	String message="";

	String rcreid="";
	
		
	@Override
public String toString()
{
		return "NOTHING";
}
	
}
