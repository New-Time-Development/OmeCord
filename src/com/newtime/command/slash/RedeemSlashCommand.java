package com.newtime.command.slash;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.newtime.database.LiteSQL;
import com.newtime.main.Main;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * @author SageSphinx63920
 *
 * Copyright (c) 2019 - 2021 by New Time Development/Sage to present. All rights reserved
 */

public class RedeemSlashCommand extends ListenerAdapter{
	
	public void onSlashCommand(SlashCommandEvent event) {
		if(event.getOptions().size() == 1){
			if(event.getName().equalsIgnoreCase("redeem")) {
				
				Member m = event.getMember();
				
				  ResultSet set = LiteSQL.onQuery("SELECT * FROM premium WHERE userid = " + m.getIdLong());  

				  String code = event.getOption("code").getAsString();
				  
	          	  try {
	  
					if(!set.next()) {
			          	  ResultSet set1 = LiteSQL.onQuery("SELECT * FROM codes WHERE code = '" + code + "'");    
			          	  	if(set1.next()){
			          	  		
			          	  		long canEnd = set1.getLong("end");			 				          	
			          	  	
			          	  		LiteSQL.onUpdate("DELETE FROM codes WHERE code = '" + code + "'");
			          	  		if(canEnd == 0) {
			          	  		event.reply("Your code is valid. You actived premium for your account. Use !premium to check out your new functions").queue();
			          	  		LiteSQL.onUpdate("INSERT INTO premium(userid, time, code, end) VALUES(" + m.getIdLong() + ", " + System.currentTimeMillis() + ", '" + code + "', 0)");
			          	  		Main.jda.getTextChannelById(845346722691678278l).sendMessageEmbeds(new EmbedBuilder()
			          	  				.setTitle("Codeaktivierung Ome.tv")
			          	  				.setDescription("Der Code ||" + code + "|| von " + m.getAsMention() + "** / ** " + m.getUser().getAsTag() + "** engel�st.")
			          	  				.setColor(Color.GREEN)
			          	  				.setFooter("Codeaktivierung")
			          	  				.build()).queue();
			          	  		}else {
			          	  		Date time=  new Date(canEnd);
			          	  		
			          	  	SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm | dd/MM/yyyy"); 
			          	// give a timezone reference for formatting (see comment at the bottom)
			          //	sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT-2")); 
			          	String formattedDate = sdf.format(time);
			          		event.reply("Your code is valid. You actived premium for your account. Use !premium to check out your new functions. Premium ends at: " + formattedDate+ " (MEZ)").queue();
			          	  		LiteSQL.onUpdate("INSERT INTO premium(userid, time, code, end) VALUES(" + m.getIdLong() + ", " + System.currentTimeMillis() + ", '" + code + "', " + canEnd + ")");
			          	  		Main.jda.getTextChannelById(845346722691678278l).sendMessageEmbeds(new EmbedBuilder()
			          	  				.setTitle("Codeaktivierung Ome.tv")
			          	  				.setDescription("Der Code ||" + code + "|| von " + m.getAsMention() + "** / ** " + m.getUser().getAsTag() + "** engel�st.")
			          	  				.setColor(Color.GREEN)
			          	  				.setFooter("Codeaktivierung")
			          	  				.build()).queue();
			          	  		}
			          	  		
			          	  		 
			          	  	}else {
			          	  		event.reply("Your code is invaild. Please correct it or contact our support on https://discord.com/j8emH5ap3k").queue();
			          	  	}
					  }else{
						  event.reply("More than the premium version does not exist. You have already claimed premium with a key for your account. You can give the key to your friend or make a giveaway :)").queue();
					  }
	          	  
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	          }
			
		}
		}

}
