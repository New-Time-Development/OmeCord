package com.newtime.command.slash;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.newtime.database.LiteSQL;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * @author SageSphinx63920
 *
 * Copyright (c) 2019 - 2021 by New Time Development/Sage to present. All rights reserved
 */

public class LanguageSlashCommand extends ListenerAdapter{
	
	@SuppressWarnings("unused")
	public void onSlashCommand(SlashCommandEvent event) {
		
		if(event.getName().equalsIgnoreCase("language")) {
			Guild g = event.getGuild();
			Member m = event.getMember();
			
			if(event.getSubcommandName().equalsIgnoreCase("list")) {
				ResultSet languageSet = LiteSQL
						.onQuery("SELECT * FROM lang WHERE userid = " + event.getMember().getIdLong());
				try {
					String currentLanguage = languageSet.getString("botLan");
					
					String embedLanguage = "";
					if (currentLanguage.equals("en")) {
						embedLanguage = " Current language: **ENGLISH**";
					} else if (currentLanguage.equals("de")) {
						embedLanguage = " Aktuelle Sprache: **DEUTSCH**";
					} else {
						embedLanguage = ":x: We couldn't find your language! Please contact the support";
					}
					
					if (currentLanguage.equals("en")) {
						event.reply("Possible languages to select: **ENGLISH**, **DEUTSCH** || " + embedLanguage).queue();
					} else if (currentLanguage.equals("de")) {
						event.reply("Mögliche Sprachen zur Auswahl: **Englisch**, **Deutsch** || " + embedLanguage).queue();
					} else {
						event.reply(":x: We couldn't find your language! Please contact the support")
								.queue();
					}
				}catch (SQLException e) {
					e.printStackTrace();
				}
			}else if(event.getSubcommandName().equalsIgnoreCase("set")) {
				if(event.getOption("lang").getAsString().equalsIgnoreCase("de")){
					LiteSQL.onUpdate("UPDATE lang SET botLan = 'de' WHERE userid = " + event.getMember().getIdLong());
					event.reply(":flag_de: **|** Deine Sprache wurde aktualisiert. Deine neue Sprache: **DEUTSCH**").queue();
	
				}else if(event.getOption("lang").getAsString().equalsIgnoreCase("en")){
					LiteSQL.onUpdate("UPDATE lang SET botLan = 'en' WHERE userid = " + event.getMember().getIdLong());
					event.reply(":flag_us: **|** Your language has been updated. Your language now is: **ENGLISH**").queue();
				}
			}
		}
		
	}

}
