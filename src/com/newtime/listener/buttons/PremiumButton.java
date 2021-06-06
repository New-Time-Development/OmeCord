package com.newtime.listener.buttons;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.newtime.command.devs.PremiumButtonCommand;
import com.newtime.database.LiteSQL;
import com.newtime.main.Main;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.Button;

/**
 * @author SageSphinx63920
 *
 * Copyright (c) 2019 - 2021 by New Time Development/Sage to present. All rights reserved
 */

public class PremiumButton extends ListenerAdapter {

	@SuppressWarnings("unused")
	public void onButtonClick(ButtonClickEvent event) {
		if(event.getComponentId().equalsIgnoreCase("30premium")) {
			//Get informations
			Member m = event.getMember();
			Guild g = event.getGuild();
			Message mes = event.getMessage();
			Button button = event.getButton();
			
			ResultSet lanRS = LiteSQL.onQuery("SELECT * FROM lang WHERE userid = " + m.getIdLong());
			String lan = null;
			try {
				if(lanRS.next()) {
					lan = lanRS.getString("botLan");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			if(event.getChannelType().equals(ChannelType.TEXT)) {
				//Text Channel
				  ResultSet set = LiteSQL.onQuery("SELECT * FROM premium WHERE userid = " + m.getIdLong());  
	          	  try {
	  
					if(!set.next()) {
			          	  ResultSet set1 = LiteSQL.onQuery("SELECT * FROM codes WHERE code = '" + PremiumButtonCommand.buttoncodes.get(event.getMessageIdLong()) + "'");    
			          	  	if(set1.next()){
			          	  		
			          	  		long canEnd = set1.getLong("end");
			          	  		
					          	ResultSet marcel = LiteSQL.onQuery("SELECT * FROM codes WHERE code = '" + PremiumButtonCommand.buttoncodes.get(event.getMessageIdLong()) + "'");

					          	long userid = marcel.getLong("userid");
					          	
			          	  		
			          	  		LiteSQL.onUpdate("DELETE FROM codes WHERE code = '" + PremiumButtonCommand.buttoncodes.get(event.getMessageIdLong()) + "'");
			          	  		if(canEnd == 0) {
			          	  		  event.getTextChannel().sendMessage("Your code is valid. You actived premium for your account. Use !premium to check out your new functions").queue();
			          	  		LiteSQL.onUpdate("INSERT INTO premium(userid, time, code, end) VALUES(" + m.getIdLong() + ", " + System.currentTimeMillis() + ", '"+ PremiumButtonCommand.buttoncodes.get(event.getMessageIdLong()) + "', 0)");
			          	  		Main.jda.getTextChannelById(845346722691678278l).sendMessage(new EmbedBuilder()
			          	  				.setTitle("Codeaktivierung Ome.tv")
			          	  				.setDescription("Der Code ||" + PremiumButtonCommand.buttoncodes.get(event.getMessageIdLong()) + "|| von " + m.getAsMention() + "** / ** " + m.getUser().getAsTag() + "** engelöst.")
			          	  				.setColor(Color.GREEN)
			          	  				.setFooter("Codeaktivierung")
			          	  				.build()).queue();
			          	  		}else {
			          	  		Date time=  new Date(canEnd);
			          	  		
			          	  	SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm | dd/MM/yyyy"); 
			          	// give a timezone reference for formatting (see comment at the bottom)
			          //	sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT-2")); 
			          	String formattedDate = sdf.format(time);
			            event.getTextChannel().sendMessage("Your code is valid. You actived premium for your account. Use !premium to check out your new functions. Premium ends at: " + formattedDate+ " (MEZ)").queue();
			          	  		LiteSQL.onUpdate("INSERT INTO premium(userid, time, code, end) VALUES(" + m.getIdLong() + ", " + System.currentTimeMillis() + ", '" + PremiumButtonCommand.buttoncodes.get(event.getMessageIdLong()) + "', " + canEnd + ")");
			          	  		Main.jda.getTextChannelById(845346722691678278l).sendMessage(new EmbedBuilder()
			          	  				.setTitle("Codeaktivierung Ome.tv")
			          	  				.setDescription("Der Code ||" + PremiumButtonCommand.buttoncodes.get(event.getMessageIdLong()) + "|| von " + m.getAsMention() + "** / ** " + m.getUser().getAsTag() + "** engelöst.")
			          	  				.setColor(Color.GREEN)
			          	  				.setFooter("Codeaktivierung")
			          	  				.build()).queue();
			          	  		}
			          	  		
			          	  		 
			          	  	}else {
			          	  	  event.getTextChannel().sendMessage("The bot was restarted! The button isnt vaild anymore").queue();
			          	  	}
					  }else{
						  event.getTextChannel().sendMessage("More than the premium version does not exist. You have already claimed premium with a key for your account. You can give the key to your friend or make a giveaway :)").queue();
					   }
	          	  
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else {
				//Private Message
				
			}
		}
		
	}
	
}
