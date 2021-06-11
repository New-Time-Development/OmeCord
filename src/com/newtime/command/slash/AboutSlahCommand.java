package com.newtime.command.slash;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.newtime.database.LiteSQL;
import com.newtime.main.Main;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * @author SageSphinx63920
 *
 * Copyright (c) 2019 - 2021 by New Time Development/Sage to present. All rights reserved
 */

public class AboutSlahCommand extends ListenerAdapter{
	
	public void onSlashCommand(SlashCommandEvent event) {
		if(event.getName().equalsIgnoreCase("about")) {
			if(event.getChannelType().equals(ChannelType.TEXT)) {

				ResultSet languageSet = LiteSQL
						.onQuery("SELECT * FROM lang WHERE userid = " + event.getMember().getIdLong());
				try {
					String currentLanguage = languageSet.getString("botLan");
					if (currentLanguage.equals("en")) {
						EmbedBuilder enBuilder = new EmbedBuilder();

						enBuilder.setTitle("OmeCord || About");
						enBuilder.setDescription("OmeCord has similar functions to Ome.tv. It's possible to\r\n"
								+ "communicate with other people without being together\r\n"
								+ "in a mutual Discord server or being friends.");

						enBuilder.addField("Helpful links:",
								"- Support Server [(Klick)](https://discord.gg/zEwGEJUPRC)\r\n"
										+ "- Github Link [(Klick)](https://github.com/orgs/New-Time-Development/)\r\n"
										+ "- Invite me to **your** server: [(Klick)](https://discord.com/api/oauth2/authorize?client_id=838062574963523644&permissions=2268392792&scope=bot%20applications.commands)\r\n"
										+ "- Offical Website: [(Klick)](https://new-time-development.github.io/omecord-page/)",
								false);

						enBuilder.setFooter(Main.footer);
						enBuilder.setTimestamp(new Date().toInstant());

						enBuilder.setColor(Color.decode("#e05e36"));

						event.replyEmbeds(enBuilder.build()).queue();

					} else if (currentLanguage.equals("de")) {
						EmbedBuilder deBuilder = new EmbedBuilder();

						deBuilder.setTitle("OmeCord || About");
						deBuilder.setDescription("OmeCord weist **ähnliche** Funktionen wie Ome.tv auf.\r\n"
								+ "Es ist möglich mit anderen Leuten zu **kommunizieren**, **ohne** zusammen\r\n"
								+ "in einem **gemeinsamen** Discord-Server zu sein oder **befreundet** zu sein.");

						deBuilder.addField("N�tzliche Links: ",
								"- Support Server [(Klick)](https://discord.gg/zEwGEJUPRC)\r\n"
										+ "- Github Link [(Klick)](https://github.com/orgs/New-Time-Development/)\r\n"
										+ "- Lade mich zu **deinem** Server ein [(Klick)](https://discord.com/api/oauth2/authorize?client_id=838062574963523644&permissions=2268392792&scope=bot%20applications.commands)\r\n"
										+ "- Offizielle Website: [(Klick)](https://new-time-development.github.io/omecord-page/)",
								false);

						deBuilder.setFooter(Main.footer);
						deBuilder.setTimestamp(new Date().toInstant());

						deBuilder.setColor(Color.decode("#e05e36"));

						event.replyEmbeds(deBuilder.build()).queue();
						
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
		
				}
			
		
	}

}
