package com.hunghing.littletrain.model;

import java.sql.Timestamp;

public class Data {

	private boolean isFirst = true;
	private String times;
	private Timestamp lastEdit;
	private int frequency = 0;
	private String identifier;
	
	public void setIsFirst(){
		this.isFirst = false;
	}
	
	public boolean getIsFirst(){
		return this.isFirst;
	}
	
	public void setTimes(String times){
		this.times = times;
	}
	
	public String getTimes(){
		return this.times;
	}
	
	public void setLastEdit(Timestamp lastEdit){
		this.lastEdit = lastEdit;
	}
	
	public Timestamp getLastEdit(){
		return this.lastEdit;
	}
	
	public void setFrequency(int frequency){
		this.frequency = frequency;
	}
	
	public int getFrequency(){
		return this.frequency;
	}
	
	public void setIdentifier(String identifier){
		this.identifier = identifier;
	}
	
	public String getIdentifier(){
		return this.identifier;
	}
}
