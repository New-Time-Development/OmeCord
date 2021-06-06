package com.newtime.command.devs;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.newtime.database.LiteSQL;
import com.newtime.main.Main;
import com.newtime.util.RandomStr;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.Button;

/**
 * @author SageSphinx63920
 *
 * Copyright (c) 2019 - 2021 by New Time Development/Sage to present. All rights reserved
 */


public class PremiumButtonCommand extends ListenerAdapter{
	
	public static HashMap<Long, String> buttoncodes = new HashMap<>();
	
	@SuppressWarnings("unused")
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		  if (event.getMessage().getContentRaw().startsWith(Main.prefix + "prebutton")) {
	        	//Angaben
	        	TextChannel ch = event.getChannel();
	        	Member m = event.getMember();
	        	Guild g = event.getGuild();
	        	Message mes = event.getMessage();
	        	List<Color> ColorList = Arrays.asList(Color.GREEN,Color.BLUE, Color.CYAN, Color.LIGHT_GRAY, Color.MAGENTA, Color.ORANGE, Color.PINK, Color.RED, Color.WHITE, Color.YELLOW, Color.decode("#0b0064"));
				Color Color_RANDOM = ColorList.get(new Random().nextInt(ColorList.size()));
				String[] args = event.getMessage().getContentDisplay().split(" ");
				int length = args.length;
				 StringBuilder strbuild = new StringBuilder();
																									     //1 	2
				for(int i = 2; i < length; i++) strbuild.append(args[i] + " "); //i = anzahl von suvfix: e.ichbincool
					
				String argsstring = strbuild.toString().trim();
				
				ResultSet lanRS = LiteSQL.onQuery("SELECT * FROM lang WHERE userid = " + m.getIdLong());
				String lan = null;
				try {
					if(lanRS.next()) {
						lan = lanRS.getString("botLan");
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ResultSet premiumRS = LiteSQL.onQuery("SELECT * FROM premium WHERE userid = " + m.getIdLong());
				boolean premium = false;
				try {
					if(premiumRS.next()) {
						premium = true;
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				//Hier gehts los
				
	          	   if(Main.OwnerIds.contains(m.getIdLong())) {
	           		  String code = RandomStr.generateString(new Random(), "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890", 8) ;
	           		 ResultSet set = LiteSQL.onQuery("SELECT * FROM codes WHERE code = '" + code +"'");
	           		  try {
	 					if(set.next()) {
	 						  ch.sendMessage("Ok du hast es geschafft. Ein code gibt es doppelt. Ein mal applaus für dich! :) Machs einfach nochmal. Es generieren immer neue Codes").queue();
	 					  }else {
				    	    
	 						  mes.delete().queue();
	 						  
	 						  int days = 0;
	 						  long unix = System.currentTimeMillis();
	 						  long end = unix + days;
	 						  
	 						          		  LiteSQL.onUpdate("INSERT INTO codes(userid, time, code, end) VALUES(" + m.getIdLong() + ", " + System.currentTimeMillis() + ", '" + code + "', " +end  + ")");
	 						          		ch.sendMessage("Free 30 days premium!").setActionRow(Button.primary("30premium", "Get it")).queue(message ->{
	 						          		  buttoncodes.put(message.getIdLong(), code);
	 						          		});
	 						          		  m.getUser().openPrivateChannel().queue(dm ->{
	 						          			  dm.sendMessage(new EmbedBuilder()
	 						          					  .setColor(Color.CYAN)
	 						          					  .setTitle("Temp-Button | Ome.tv Premium Code")
	 						          					  .setFooter("VORSICHTIG MEIN FREUND")
	 						          					  .setDescription("Bitte zeige diesen Code NIEMALS in einem Stream usw. \n Er schaltet einmalig für einen User alle Premium Funktionen frei. \n Mit ihm ist vorsichtig um zu gehen! \n Hier ist der Code: ||" + code + "||" + "\n Ende: " + argsstring)
	 						          					  .build()).queue();
	 						          		  });
	 						          		  
	 						          		
	 						          		  
	 						          		  Main.jda.getTextChannelById(845346722691678278l).sendMessage(new EmbedBuilder()
	 						          				  .setDescription("Hier ein Temp-Button-Code von " + m.getAsMention() + "! Bitte NIEMALS im Stream usw. zeigen! \n Code: ||" + code + "||")
	 						          				  .setColor(Color.YELLOW)
	 						          				  .setTitle("Ome.tv Premium Code")
	 						          				  .build()).queue();
	 						    	    

	           	   }
	           		  }catch (Exception e) {
	           			  e.printStackTrace();
	           		  }
				//DONT CHANGE
				premium = false;
				lan = "en";
	        }
		  }
	}

}
