package com.newtime.command.slash;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.newtime.database.LiteSQL;
import com.newtime.main.Main;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.Button;

/**
 * @author SageSphinx63920
 *
 * Copyright (c) 2019 - 2021 by New Time Development/Sage to present. All rights reserved
 */

public class InviteSlashCommand extends ListenerAdapter{
	
	@SuppressWarnings("unused")
	public void onSlashCommand(SlashCommandEvent event) {
		
		if(event.getName().equalsIgnoreCase("invite")) {
			Guild g = event.getGuild();
			Member m = event.getMember();
			
			ResultSet languageSet = LiteSQL.onQuery("SELECT * FROM lang WHERE userid = " + event.getMember().getIdLong());
			
			try {
				String currentLanguage = languageSet.getString("botLan");
				if(currentLanguage.equals("de")) {
					EmbedBuilder embedBuilder = new EmbedBuilder();
					
					embedBuilder.setTitle("OmeCord || Einladung");
					embedBuilder.setDescription("Du möchtest mich auf deinen Server einladen? Klicke " + "[hier](https://discord.com/api/oauth2/authorize?client_id=838062574963523644&permissions=2268392792&scope=bot%20applications.commands), "
							+ "um mich auf deinen Server einzuladen!" );
					
					embedBuilder.setFooter(Main.footer);
					embedBuilder.setTimestamp(new Date().toInstant());

					embedBuilder.setColor(Color.decode("#e05e36"));
					event.replyEmbeds(embedBuilder.build()).addActionRow(Button.link("https://discord.com/api/oauth2/authorize?client_id=838062574963523644&permissions=2268392792&scope=bot%20applications.commands", "Invite")).queue();
					
				} else if(currentLanguage.equals("en")) {
					EmbedBuilder embedBuilder = new EmbedBuilder();
					
					embedBuilder.setTitle("OmeCord || Invite");
					embedBuilder.setDescription("You want to invite me to your server? Click [here](https://discord.com/api/oauth2/authorize?client_id=838062574963523644&permissions=2268392792&scope=bot%20applications.commands) "
							+ "to invite me to your server!");
					
					embedBuilder.setFooter(Main.footer);
					embedBuilder.setTimestamp(new Date().toInstant());

					embedBuilder.setColor(Color.decode("#e05e36"));
					
					event.replyEmbeds(embedBuilder.build()).addActionRow(Button.link("https://discord.com/api/oauth2/authorize?client_id=838062574963523644&permissions=2268392792&scope=bot%20applications.commands", "Invite")).queue();
					}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

}
