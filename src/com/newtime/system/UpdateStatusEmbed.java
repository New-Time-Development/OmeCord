package com.newtime.system;

import java.awt.Color;
import java.time.OffsetDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.Timer;
import java.util.TimerTask;

import com.newtime.main.Main;

import net.dv8tion.jda.api.EmbedBuilder;

/**
 * @author SageSphinx63920
 *
 * Copyright (c) 2019 - 2021 by New Time Development/Sage to present. All rights reserved
 */

public class UpdateStatusEmbed {
	
	public static void start() {
		
		TemporalAccessor t = OffsetDateTime.now();
		
		Main.jda.getTextChannelById(853019383570759741l).editMessageById(853026730482466876l, new EmbedBuilder()
				.setTitle("OmeCord Status")
				.setDescription("...loading Stats... \n \n Please wait a moment")
				.setColor(Color.GREEN)
				.setTimestamp(t)
				.build()).queue();
		
		 Timer timer = new Timer();
	    	timer.schedule(new TimerTask() {
	    		
	    		@Override
	    		public void run() {

	    			UpdateStatusEmbed.update();
	    		}
	    	}, 5*1000);
	}
	
	public static void shutdown() {
		
		TemporalAccessor t = OffsetDateTime.now();
		
		Main.jda.getTextChannelById(853019383570759741l).editMessageById(853026730482466876l, new EmbedBuilder()
				.setTitle("OmeCord Status")
				.setDescription("*Bot offline* \n \n Waiting for reboot...")
				.setColor(Color.GREEN)
				.setTimestamp(t)
				.build()).queue();
	}

   public static void update() {
			  
		 Timer timer = new Timer();
		timer.schedule(new TimerTask() {
					
			@Override
			public void run() {
				
				TemporalAccessor t = OffsetDateTime.now();
				
				   	  String nameOS = "os.name";  
				      String versionOS = "os.version";  
				      String architectureOS = "os.arch";
						  //  long maxMemory = Runtime.getRuntime().maxMemory();
						      
						     
						      
					Main.jda.getTextChannelById(853019383570759741l).editMessageById(853026730482466876l, new EmbedBuilder()
							.setTitle("OmeCord Status")
							.setDescription("Cores: " + Runtime.getRuntime().availableProcessors() + " \n Ram: " + (Runtime.getRuntime().freeMemory() / 1000000) + " / " +  (Runtime.getRuntime().totalMemory() / 1000000) + " (mb) \n \n OS Name: " +  System.getProperty(nameOS) + " \n Version: " + System.getProperty(versionOS) + " \n Architecture: " + System.getProperty(architectureOS) + " \n \n Java Version: " + Runtime.version())
							.setColor(Color.GREEN)
							.setTimestamp(t)
							.build()).queue();
						
						
						/* Total number of processors or cores available to the JVM 
					    System.out.println("Available processors (cores): " + 
					        Runtime.getRuntime().availableProcesrs());

					     Total amount of free memory available to the JVM 
					    System.out.println("Free memory (bytes): " + 
					        Runtime.getRuntime().freeMemory());

					     This will return Long.MAX_VALUE if there is no preset limit 
		
					     Maximum amount of memory the JVM will attempt to use 
					    System.out.println("Maximum memory (bytes): " + 
					        (maxMemory == Long.MAX_VALUE ? "no limit" : maxMemory));

					     Total memory currently available to the JVM 
					    System.out.println("Total memory available to JVM (bytes): " + 
					        Runtime.getRuntime().totalMemory());

					    

					      System.out.println("\n  The information about OS");
					      System.out.println("\nName of the OS: " + 
					    System.getProperty(nameOS));
					      System.out.println("Version of the OS: " + 
					    System.getProperty(versionOS));
					      System.out.println("Architecture of THe OS: " + 
					    System.getProperty(architectureOS)); */
					  
					}
				}, 0, 30*1000);
		    
		}
		 
}
