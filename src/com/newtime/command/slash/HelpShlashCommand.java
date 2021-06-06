package com.newtime.command.slash;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.newtime.database.LiteSQL;
import com.newtime.main.Main;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * @author SageSphinx63920
 *
 * Copyright (c) 2019 - 2021 by New Time Development/Sage to present. All rights reserved
 */

public class HelpShlashCommand extends ListenerAdapter{
	
	@SuppressWarnings("unused")
	public void onSlashCommand(SlashCommandEvent event) {
		
		if(event.getName().equalsIgnoreCase("help")) {
			if(event.getChannelType().equals(ChannelType.TEXT)) {
				TextChannel ch = event.getTextChannel();
				Guild g = event.getGuild();
				Member m = event.getMember();
				
				ResultSet languageSet = LiteSQL.onQuery("SELECT * FROM lang WHERE userid = " + event.getMember().getIdLong());
				
				try {
					String currentLanguage = languageSet.getString("botLan");
					if(currentLanguage.equals("en")) {
						EmbedBuilder embedBuilder = new EmbedBuilder();
						
						embedBuilder.setTitle("OmeCord || Helpmenu");
						embedBuilder.addField(":postal_horn: Important Commands:", "`!help` » Show you this help\r\n"
								+ "`!about` » Information about the bot & contact\r\n"
								+ "`!invite` » Sends you the invitation link.\r\n"
								+ "`!setup` » Sets up the bot automatically.\r\n"
								+ "`!ome <language> <gender> <yes, no>` » Creates a user for you *(Yes for automatic translation)*.\r\n"
								+ "`!premium` » Shows you all premium features for the bot.\r\n"
								+ "`!join <message (@user + @server)` » Sets your custom join message *only Premium*"
								, false);
						
						embedBuilder.addField(":flag_white: Language Commands:", "`!language` » Shows you your current language\r\n"
								+ "`!language list` » Shows you all available languages\r\n"
								+ "that are available for selection\r\n"
								+ "`!language <language>` » Change your language", false);
						
						embedBuilder.addField(":loud_sound: General Commands: ", "`!start` » Adds you to the queue\r\n"
								+ "`!next` » Connects you to a new user *(Adapted to your \r\n"
								+ "(Adjusted to your settings.)*\r\n"
								+ "`!report <message...>` » Reports a user\r\n", false);

						embedBuilder.setFooter(Main.footer);
						embedBuilder.setTimestamp(new Date().toInstant());
						
						embedBuilder.setThumbnail(Main.jda.getSelfUser().getAvatarUrl());

						embedBuilder.setColor(Color.decode("#e05e36"));
						
						event.reply(":white_check_mark: **|** You have received a direct message!").queue();
						
						m.getUser().openPrivateChannel().queue((privatechannel) -> {
							privatechannel.sendMessage(embedBuilder.build()).queue(null, failed -> {
								ch.sendMessage(embedBuilder.build()).queue();
							});
						});
						
					} else if(currentLanguage.equals("de")) {
						EmbedBuilder embedBuilder = new EmbedBuilder();
						
						embedBuilder.setTitle("OmeCord || Hilfestellung");
						embedBuilder.addField(":postal_horn: Wichtige Kommandos:", "`!help` » Zeigt dir diese Hilfestellung\r\n"
								+ "`!about` » Informationen zu dem Bot & Kontakt\r\n"
								+ "`!invite` » Sendet dir den Einladungslink.\r\n"
								+ "`!setup` » Richtet den Bot automatisch ein.\r\n"
								+ "`!ome <Sprache> <Geschlecht> <Yes, No>` » Erstellt dir einen Nutzer *(Ja für automatische Übersetzung)*\r\n"
								+ "`!premium` » Zeigt dir alle Premiumfunktionen für den Bot \r\n"
								+ "`!join <Nachricht (@user + @server)` » Setzt deine Custom Join Nachricht *only Premium*"
								, false);
						
						embedBuilder.addField(":flag_white: Sprachen Kommandos:", "`!language` » Zeigt dir deine aktuelle Sprache\r\n"
								+ "`!language list` » Zeigt dir alle verfügbaren Sprachen\r\n"
								+ "die zur Auswahl stehen\r\n"
								+ "`!language <Sprache>` » Ändert deine Sprache", false);
						
						embedBuilder.addField(":loud_sound: Allgemeine Kommandos: ", "`!start` » Fügt dich zu der Warteschlange hinzu\r\n"
								+ "`!next` » Verbindet dich mit einem neuem Benutzer *(An deinen \r\n"
								+ "Einstellungen angepasst.)*\r\n"
								+ "`!report <Nachricht...>` » Meldet einen Nutzer\r\n", false);
						
						embedBuilder.setFooter(Main.footer);
						embedBuilder.setTimestamp(new Date().toInstant());
						
						embedBuilder.setThumbnail(Main.jda.getSelfUser().getAvatarUrl());

						embedBuilder.setColor(Color.decode("#e05e36"));
						
						event.reply(":white_check_mark: **|** Du hast eine Direktnachricht erhalten!").queue();
						
						m.getUser().openPrivateChannel().queue((privatechannel) -> {
							privatechannel.sendMessage(embedBuilder.build()).queue(null, failed -> {
								ch.sendMessage(embedBuilder.build()).queue();
							});
						});
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		
			
		}
		
	}

}
