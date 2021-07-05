package com.newtime.command.slash;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import com.newtime.command.OmeCommand;
import com.newtime.database.LiteSQL;
import com.newtime.listener.menus.ToSListener;
import com.newtime.main.Main;
import com.newtime.util.Translations;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.selections.SelectionMenu;

/**
 * @author SageSphinx63920
 *
 * Copyright (c) 2019 - 2021 by New Time Development/Sage to present. All rights reserved
 */

public class OmeSlashCommand extends ListenerAdapter{
	
	private List<String> langs = Arrays.asList("de", "en", "trk", "fr", "es", "ru");
	private List<String> gender = Arrays.asList("male", "female", "couples");
	
	public void onSlashCommand(SlashCommandEvent event) {
		if(event.getName().equalsIgnoreCase("ome")) {
			if(event.getOptions().size() == 3) {
				
				String lang = event.getOption("language").getAsString();
				String gender = event.getOption("gender").getAsString();
				boolean transB = event.getOption("tranlations").getAsBoolean();
				String trans = null;
				
				if(transB) {
					trans = "yes";
				}else
					trans = "no";
				
				ResultSet lanRS = LiteSQL.onQuery("SELECT * FROM lang WHERE userid = " + event.getMember().getIdLong());
				String lan = null;
				try {
					if(lanRS.next()) {
						lan = lanRS.getString("botLan");
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				Member m = event.getMember();
				
				ResultSet userData = LiteSQL.onQuery("SELECT * FROM members WHERE userid = " + event.getMember().getIdLong());
				if(langs.contains(lang.toLowerCase())) {
					if(this.gender.contains(gender.toLowerCase())) {
							try {
								if(userData.next()) {
									int mute = userData.getInt("mute");
									LiteSQL.onUpdate("DELETE FROM members WHERE userid = " + m.getIdLong());
									LiteSQL.onUpdate("INSERT INTO members(userid, selcLan, gen, ueber, mute) VALUES(" + m.getIdLong() + ", '" + lang.toLowerCase() +"', '" + gender.toLowerCase() + "', '" +trans.toLowerCase() + "', " + mute + ")");
									event.reply(Translations.OmeCmd(lan, "update")).queue();
								}else {
									if(ToSListener.accepted.containsKey(m.getIdLong())) {
										int days = 259200000;
										long unix = System.currentTimeMillis();
										long endFree = unix + days;
										LiteSQL.onUpdate("INSERT INTO premium(userid, time, code, end) VALUES(" + m.getIdLong() + ", " + System.currentTimeMillis() + ", 'NewUser', " + endFree + ")");
										
										Main.jda.getTextChannelById(845346722691678278l).sendMessageEmbeds(new EmbedBuilder()
					          	  				.setTitle("Codeaktivierung Ome.tv")
					          	  				.setDescription("Der User " + m.getAsMention() + " / ** " + m.getUser().getAsTag() + "** ist neu und hat 3 Tage gratis premium!")
					          	  				.setColor(Color.GREEN)
					          	  				.setFooter("Codeaktivierung")
					          	  				.build()).queue();
										
										LiteSQL.onUpdate("INSERT INTO members(userid, selcLan, gen, ueber, mute) VALUES(" + m.getIdLong() + ", '" + lang.toLowerCase() +"', '" + gender.toLowerCase() + "', '" +trans.toLowerCase() + "', 0)");
										event.reply(Translations.OmeCmd(lan, "insert")).queue();
									}else {
										SelectionMenu menu = SelectionMenu.create("tos:menu")
												.setRequiredRange(1, 1)
											    .addOption("Yes", "yes", "I read and apply the ToS and Privacy", Emoji.fromMarkdown("<:check_mark:845331036737241120>"))
											    .addOption("No", "no", "I dont apply the ToS/Privacy(You cant use the bot)", Emoji.fromMarkdown("<:error123:845331037138976809>"))
												//.setDefaultOptions(Arrays.asList(SelectOption.of("Yes", "yes")))
											    .setPlaceholder("Do you accept it?")
											    .build();
										
										event.replyEmbeds(new EmbedBuilder()
												.setTitle("ToS and Privacy")
												.setDescription("I read and accept the [ToS](https://github.com/New-Time-Development/OmeCord/blob/docs/tos/terms.md) and [Privacy](https://github.com/New-Time-Development/OmeCord/blob/docs/privacy/privacy.md)!")
												.setColor(Color.ORANGE)
												.build()).addActionRow(menu).queue(tosmes->{
													OmeCommand.messages.put(m.getIdLong(), tosmes.getInteraction().getIdLong());
												});
										
									}
								}
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						
					}else
						event.reply(Translations.OmeCmd(lan, "gen")).queue();
				}else 
					event.reply(Translations.OmeCmd(lan, "lang")).queue();
			}
		}
	}

}
