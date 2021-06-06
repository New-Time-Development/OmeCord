package com.newtime.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import net.dv8tion.jda.api.entities.Guild;

/**
 * @author SageSphinx63920
 *
 * Copyright (c) 2019 - 2021 by New Time Development/Sage to present. All rights reserved
 */

public class Scanners {
	
	/*@SuppressWarnings("resource")
	public static void startScanner() {
		
		  Scanner scanner = new Scanner(System.in);

		  String input = scanner.nextLine();

		  if(input.equals(Main.prefix + "guilds")) {
			   String out = "\nDer Bot läuft auf folgenden Servern: \n";

		        for (Guild g : Main.jda.getGuilds() ) {
		            out += g.getName() + " (" + g.getId() + ") \n " + g.getOwner().getUser().getAsTag() + " (" + g.getOwnerIdLong() + ") \n \n";
		            
		        }

		        System.out.println(out);
		  }else if(input.equalsIgnoreCase(Main.prefix + "users")) {
				String out1 = "\nDer Bot kennt folgende User: \n";
	            for (User u : Main.jda.getUsers()) {
	            	 out1 += u.getName() + " (" + u.getId() + ") \n";
	            }
	            System.out.println(out1);
		  }else if(input.equalsIgnoreCase(Main.prefix + "active")) {
			  System.out.println(Queue.activeUser);
		  }
		
	}*/
	
	public void runScanner() {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Tippe entweder 'exit' oder 'info' ein.");
		String input = scanner.nextLine();
		
		if(input.equals("exit")) {
			System.exit(0);
		} else if(input.equals("info")) {
			String out = "\nDer Bot läuft auf folgenden Servern: \n";

	        for (Guild g : Main.jda.getGuilds() ) {
	            out += g.getName() + " (" + g.getId() + ") \n " + g.getOwner().getUser().getAsTag() + " (" + g.getOwnerIdLong() + ") \n \n";
	            
	        }
	        System.out.println(out);
		} else {
			System.out.println("Tippe entweder 'exit' oder 'info' ein.");
		}
	}
	
	public static void scanners() {
		
		new Thread(() -> {
			
			String line = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			try {
				while((line = reader.readLine()) != null) {
					
					if(line.equalsIgnoreCase("exit")) {
						
							
						}else if(line.equalsIgnoreCase("info")) {
							 String out = "\nDer Bot läuft auf folgenden Servern: \n";

						        for (Guild g : Main.jda.getGuilds() ) {
						            out += g.getName() + " (" + g.getId() + ") \n " + g.getOwner().getUser().getAsTag() + " (" + g.getOwnerIdLong() + ") \n \n";
						            
						        }
						        System.out.println(out);
						}
						else {
							System.out.println("Use 'exit' to shutdown.");
						}
						
						
						reader.close();
						break;
					}

				
			} catch (IOException e) {
				//e.printStackTrace();
			}
			
		}).start();
	}


}
