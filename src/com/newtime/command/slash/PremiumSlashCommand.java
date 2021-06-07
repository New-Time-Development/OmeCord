package com.newtime.command.slash;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.newtime.database.LiteSQL;
import com.newtime.util.Translations;
import com.newtime.util.checks.CheckPremium;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * @author SageSphinx63920
 *
 * Copyright (c) 2019 - 2021 by New Time Development/Sage to present. All rights reserved
 */

public class PremiumSlashCommand extends ListenerAdapter{
	
	@SuppressWarnings("unused")
	public void onSlashCommand(SlashCommandEvent event) {
		
		if(event.getName().equalsIgnoreCase("premium")) {
			Guild g = event.getGuild();
			Member m = event.getMember();
			
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
			
			CheckPremium.checkStatus(m, event.getTextChannel(), lan);
			
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
			
			if(event.getSubcommandName().equalsIgnoreCase("functions")) {
				event.replyEmbeds(new EmbedBuilder()
						.setTitle("Premium functions")
						.setColor(Color.BLUE)
						.setDescription("Prioritised support\r\n"
								+ "Also better audio quality(The best audio quality the server can use)\r\n"
								+ "More file upload (Up to 15MB (only possible if you have Nitro))\r\n"
								+ "Slow mode is deactivated and no cooldown\r\n"
								+ "Personalised Connection Message\r\n"
								+ "Early access to beta functions\r\n"
								+ "You can get Premium here: [Click here](https://www.patreon.com/omecord/creators)")
						.build()).queue();
				
			}else if(event.getSubcommandName().equalsIgnoreCase("status")) {
				if(premium) {
					try {
						ResultSet getEnding = LiteSQL.onQuery("SELECT * FROM premium WHERE userid = " + m.getIdLong());
						long end = getEnding.getLong("end");
						
						if(end  == 0) {
							event.replyEmbeds(new EmbedBuilder()
										.setTitle("Premium Status")
										.setDescription(Translations.premiumStatus(lan, "endlos", premium))
										.setThumbnail("https://cdn.discordapp.com/attachments/840976542300372992/845307912750759966/455f8be75219_2018px-Red_star.svg.png")
										.setColor(Color.MAGENTA)
										.build()).queue();
		          	  		}else {
			          	  		Date time=  new Date(end);
			          	  		
				          	    SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm | dd/MM/yyyy"); 
				          	     String formattedDate = sdf.format(time);
				          	   event.replyEmbeds(new EmbedBuilder()
										.setTitle("Premium Status")
										.setDescription(Translations.premiumStatus(lan, formattedDate, premium))
										.setThumbnail("https://cdn.discordapp.com/attachments/840976542300372992/845307912750759966/455f8be75219_2018px-Red_star.svg.png")
										.setColor(Color.MAGENTA)
										.build()).queue();
		          	  		}

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
				}

			}else {
				event.replyEmbeds(new EmbedBuilder()
						.setTitle("Premium Status")
						.setDescription(Translations.premiumStatus(lan, "endlos", premium))
						.setThumbnail("https://cdn.discordapp.com/attachments/840976542300372992/845307912750759966/455f8be75219_2018px-Red_star.svg.png")
						.setColor(Color.MAGENTA)
						.build()).queue();
			}
			}
			
		}
		
	}

}
