package com.newtime.system.objects;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author SageSphinx63920
 *
 * Copyright (c) 2019 - 2021 by New Time Development/Sage to present. All rights reserved
 */

public class LogFile {
	
	private File logFile;
	private String name;
	private final File chatLogs = new File("logs\\chat\\");
	private Date date;
	private boolean hidden;
	
	public LogFile(String name) {
		Date date = new Date();
  	
		this.hidden = false;
		this.date = date;
		this.name = name;
	}
	
	public LogFile(String name, boolean hidden) {
		Date date = new Date();
  	
		this.hidden = hidden;
		this.date = date;
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public File getFile() {
		return logFile;
	}
	
	public Date getDate() {
		return date;
	}
	
	public boolean isHidden() {
		return hidden;
	}
	
	public boolean doesExist(String name) {
		
		File logFile = new File(chatLogs + name + ".omelog");
		if(logFile.exists()) {
			return true;
		}else
			return false;
	}
	
	public boolean createFile() {
		
		File log = new File(chatLogs + this.name + ".omelog");
		if(!log.exists()) {
			try {
				if(log.createNewFile()) {
					return true;
				}else
					return false;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}else
			return false;
		
	}
	
	public String getCreationTime(String format){
		
		if(format.equalsIgnoreCase("long")) {
			return date.getTime() + "";
		}else if(format.equalsIgnoreCase("mez")) {
			SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm dd.MM.yyyy"); 
          	String formattedDate = sdf.format(date.getTime());
          	return formattedDate;
		}else
			return "";
	}	
	

}
