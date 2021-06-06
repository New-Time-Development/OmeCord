package com.newtime.command.slash;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.newtime.command.SettingsCommand;
import com.newtime.database.LiteSQL;
import com.newtime.util.Translations;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.Button;

/**
 * @author SageSphinx63920
 *
 * Copyright (c) 2019 - 2021 by New Time Development/Sage to present. All rights reserved
 */

public class SettingsSlashCommand extends ListenerAdapter{
	
	@SuppressWarnings("unused")
	public void onSlashCommand(SlashCommandEvent event) {
		
		if(event.getName().equalsIgnoreCase("settings")) {
        	Member m = event.getMember();
        	Guild g = event.getGuild();
        	List<Color> ColorList = Arrays.asList(Color.GREEN,Color.BLUE, Color.CYAN, Color.LIGHT_GRAY, Color.MAGENTA, Color.ORANGE, Color.PINK, Color.RED, Color.WHITE, Color.YELLOW, Color.decode("#0b0064"));
			Color Color_RANDOM = ColorList.get(new Random().nextInt(ColorList.size()));
			
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
			boolean premium = true;
			try {
				if(premiumRS.next()) {
					premium = false;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			ResultSet userData = LiteSQL.onQuery("SELECT * FROM members WHERE userid = " + event.getMember().getIdLong());
						try {
							if(userData.next()) {
								String lang = userData.getString("selcLan");
								String gender = userData.getString("gen");
								String trans = userData.getString("ueber");
								
								Emoji botLang = null;
								if(lan.equalsIgnoreCase("de")) {
									botLang = Emoji.fromUnicode("🇩🇪");
								}else if(lan.equalsIgnoreCase("en")) {
									botLang = Emoji.fromUnicode("🇬🇧");
								}
								
								Emoji botsettingsEmote = Emoji.fromUnicode("🌍");
								Emoji premiumEmote = Emoji.fromUnicode("⭐");
								
								event.replyEmbeds(new EmbedBuilder()
										.setTitle("Settings")
										.setDescription("Here you can edit your settings")
										.setColor(Color.PINK)
										.build()).addActionRows(ActionRow.of(
												Button.secondary("bot-language", botLang),
												Button.secondary("bot-settings", botsettingsEmote),
												Button.secondary("premium-functions", premiumEmote).withDisabled(premium))).queue(message ->{
													message.retrieveOriginal().queue(reply ->{
														SettingsCommand.messages.put(m.getIdLong(), reply.getIdLong());
													});
															
															
												});
								
							}else
								event.reply(Translations.database(lan)).queue();
						}catch(SQLException e) {
							e.printStackTrace();
						}
			
			
			//DONT CHANGE
			premium = false;
			lan = "en";

		}
		
	}

}
