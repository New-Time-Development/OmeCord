package com.newtime.system.objects;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.newtime.database.LiteSQL;

import net.dv8tion.jda.api.entities.Message.Attachment;
import net.dv8tion.jda.api.entities.User;

/**
 * @author SageSphinx63920
 *
 * Copyright (c) 2019 - 2021 by New Time Development/Sage to present. All rights reserved
 */

public class LogFile {
	
	private File logFile;
	//private String name;
	private final File chatLogs = new File("logs\\");
	private Date date;
	private boolean hidden;
	private List<OmeUser> users;
	private int LogNumber;
	private final File logIdentificationFile = new File(chatLogs + "\\ChatLog.txt");
	
	public LogFile(List<OmeUser> users) {
		Date date = new Date();
  	
		ResultSet getLastLog = LiteSQL.onQuery("SELECT * FROM logs");
		try {
			if(getLastLog.next()) {
				
				this.LogNumber = (getLastLog.getInt("logNumber") + 1);
				LiteSQL.onUpdate("UPDATE logs SET logNumber = " + (getLastLog.getInt("logNumber") + 1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.users = users;
		this.hidden = false;
		this.date = date;
	}
	
	public LogFile(List<OmeUser> users, boolean hidden) {
		Date date = new Date();
  	
		this.hidden = hidden;
		this.date = date;
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
	
	public boolean doesExist(int LogNumber) {
		
		File logFolder = new File(chatLogs + "\\" + LogNumber + " - Logs");
		if(logFolder.exists()) {
			return true;
		}else
			return false;
	}
	
	public boolean createLog() {
		
		File logFolder = new File(chatLogs + "\\chat\\\\ ID #" +Integer.toString(LogNumber) + " - Logs");
		File fileFolder = new File(chatLogs + "\\chat\\\\ ID #" +Integer.toString(LogNumber) + " - Logs" + "\\files");
		
		 FileWriter fw;
		 FileReader fr;
		try {
			 
			 fr = new FileReader(logIdentificationFile.getAbsoluteFile());
			 BufferedReader br = new BufferedReader(fr);
			 
			 List<String> lines = br.lines().collect(Collectors.toList());
			 br.close();
			 
			 fw = new FileWriter(logIdentificationFile.getAbsoluteFile());

			BufferedWriter bw = new BufferedWriter(fw);
			 
			 lines.forEach(line ->{
				 try {
					bw.write(line);
					bw.newLine();
				} catch (IOException e) {
					//e.printStackTrace();
				}
			 });
			 bw.write("Connection #" + this.LogNumber + " || Time: " + date.getTime() + " || User 1: Name: " + users.get(0).getMember().getUser().getAsTag() + " | Id: " +  users.get(0).getMember().getIdLong() + " | Guild: " +  users.get(0).getGuild().getIdLong() + " || User 2: Name: " + users.get(1).getMember().getUser().getAsTag() + " | Id: " +  users.get(1).getMember().getIdLong() + " | Guild: " +  users.get(1).getGuild().getIdLong() + " ||");
			 bw.newLine();
			 bw.close();
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if(!logFolder.exists()) {
			if(logFolder.mkdir()) {
				File log = new File(chatLogs + "\\chat\\\\ ID #" +Integer.toString(LogNumber) + " - Logs\\log.txt");
				try {
					log.createNewFile();
					if(fileFolder.mkdir()) {
						return true;
				}else
					return false;
				} catch (IOException e) {
					return false;
				}
				
			}else
				return false;
		}else
			return false;

	}
	
	public List<OmeUser> getUsers() {
		return users;
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
	
	public boolean addLine(String message, User user) {
		if(message != null) {
			File log = new File(chatLogs + "\\chat\\\\ ID #" +Integer.toString(LogNumber) + " - Logs\\log.txt");
			if(log.exists()) {
				 FileWriter fw;
				 FileReader fr;
					 
					 try {
						fr = new FileReader(log.getAbsoluteFile());
						 BufferedReader br = new BufferedReader(fr);
						 
						 List<String> lines = br.lines().collect(Collectors.toList());
							br.close();
						 
						 fw = new FileWriter(log.getAbsoluteFile());

						BufferedWriter bw = new BufferedWriter(fw);
						 
						 lines.forEach(line ->{
							 try {
								bw.write(line);
								bw.newLine();
							} catch (IOException e) {  
								//e.printStackTrace();
							}
						 });
						 bw.write("Connection #" + this.LogNumber + " || Time: " + System.currentTimeMillis() + " || Userid: " +  user.getIdLong() + " || Message: " + message);
						 bw.newLine();
						 bw.close();
						 return true;
					} catch (IOException e1) {
						e1.printStackTrace();
						return false;
					}
					
			}
		}
		
		return false;
	}
	
	public String getFilePath(Attachment attchment, User user) {
		if(attchment != null) {
			File fileFolder = new File(chatLogs + "\\chat\\\\ ID #" +Integer.toString(LogNumber) + " - Logs" + "\\files");
			if(fileFolder.exists()) {
				
				File log = new File(chatLogs + "\\chat\\\\ ID #" +Integer.toString(LogNumber) + " - Logs\\log.txt");
				if(log.exists()) {
					 FileWriter fw;
					 FileReader fr;
						 
						 try {
							fr = new FileReader(log.getAbsoluteFile());
							 BufferedReader br = new BufferedReader(fr);
							 
							 List<String> lines = br.lines().collect(Collectors.toList());
								br.close();
							 
							 fw = new FileWriter(log.getAbsoluteFile());

							BufferedWriter bw = new BufferedWriter(fw);
							 
							 lines.forEach(line ->{
								 try {
									bw.write(line);
									bw.newLine();
								} catch (IOException e) {  
									//e.printStackTrace();
								}
							 });
							 bw.write("Connection #" + this.LogNumber + " || Time: " + System.currentTimeMillis() + " || Userid: " +  user.getIdLong() + " || FileUpload: " + chatLogs + "\\chat\\\\ ID #" +Integer.toString(LogNumber) + " - Logs" + "\\files\\" + attchment.getFileName() + "." + attchment.getFileExtension());
							 bw.newLine();
							 bw.close();
							 return chatLogs + "\\chat\\\\ ID #" +Integer.toString(LogNumber) + " - Logs" + "\\files\\" + attchment.getFileName() + "." + attchment.getFileExtension();
						} catch (IOException e1) {
							e1.printStackTrace();
						}	
				}
			}
		}
		return "";
	}
}
