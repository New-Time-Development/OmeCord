package com.newtime.listener.buttons;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import com.newtime.database.LiteSQL;
import com.newtime.listener.OnOmeChannelJoin;
import com.newtime.system.Queue;
import com.newtime.util.Translations;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * @author SageSphinx63920
 *
 * Copyright (c) 2019 - 2021 by New Time Development/Sage to present. All rights reserved
 */

public class NextButton extends ListenerAdapter{
	
	private Member m2;
	
	public void onButtonClick(ButtonClickEvent event) {
		if(event.getComponentId().equalsIgnoreCase("next")) {
			
			Member m = event.getMember();
			TextChannel ch = event.getTextChannel();
			Guild g = event.getGuild();
			
			ResultSet getUserData = LiteSQL.onQuery("SELECT * FROM members WHERE userid = " + m.getIdLong());

			ResultSet languageSet = LiteSQL
					.onQuery("SELECT * FROM lang WHERE userid = " + event.getMember().getIdLong());

			String currentLanguage = null;

			try {
				currentLanguage = languageSet.getString("botLan");
			} catch (SQLException e) {
				e.printStackTrace();
			}

			try {
				if (getUserData.next()) {

					if (OnOmeChannelJoin.omechannels.containsValue(ch)) {
						HashMap<Member, Guild> user1 = new HashMap<>();
						user1.put(m, g);
						if (Queue.activeUser.containsKey(user1)) {

							HashMap<Member, Guild> user2 = Queue.activeUser.get(user1);

							user2.keySet().forEach(test -> {
								m2 = test;
							});

							Guild g2 = user2.get(m2);
							// Chat zeugs

							Queue.removeFromActiveList(m, m2, g, g2);

							TextChannel ch1 = OnOmeChannelJoin.omechannels
									.get(m.getVoiceState().getChannel().getIdLong());
							TextChannel ch2 = OnOmeChannelJoin.omechannels
									.get(m2.getVoiceState().getChannel().getIdLong());
							// Chat zeuegs
							Queue.discordnnectChats(ch1, ch2, m, m2);
						//	Queue.disconnectFromGuild(g);
							//Queue.disconnectFromGuild(g2);


							ResultSet getTheUserData = LiteSQL
									.onQuery("SELECT * FROM members WHERE userid = " + m.getIdLong());
							String lan = getTheUserData.getString("selcLan");
							String gender = getTheUserData.getString("gen");
							Queue.addUserToQueue(g, m, lan, gender);
							
							if (!Queue.matchUser(g, m)) {

								ch1.sendMessage(Translations.machError(currentLanguage)).queue();
							}
							
							ResultSet getTheUserData1 = LiteSQL
									.onQuery("SELECT * FROM members WHERE userid = " + m2.getIdLong());
							String lan1 = getTheUserData1.getString("selcLan");
							String gender1 = getTheUserData1.getString("gen");
							Queue.addUserToQueue(g2, m2, lan1, gender1);
							
							if (!Queue.matchUser(g2, m2)) {
								Queue.addUserToQueue(g2, m2, lan1, gender1);
								ch1.sendMessage(Translations.machError(currentLanguage)).queue();
							}

						} else if (Queue.activeUser.containsValue(user1)) {
							HashMap<Member, Guild> user2 = Queue.getKeyFromValue(Queue.activeUser, m);

							user2.keySet().forEach(test -> {
								m2 = test;
							});

							Guild g2 = user2.get(m2);
							// Chat zeugs

							Queue.removeFromActiveList(m2, m, g2, g);

							TextChannel ch1 = OnOmeChannelJoin.omechannels
									.get(m.getVoiceState().getChannel().getIdLong());
							TextChannel ch2 = OnOmeChannelJoin.omechannels
									.get(m2.getVoiceState().getChannel().getIdLong());
							// Chat zeuegs
							Queue.discordnnectChats(ch1, ch2, m, m2);
						//	Queue.disconnectFromGuild(g);
						//	Queue.disconnectFromGuild(g2);

							ResultSet getTheUserData = LiteSQL
									.onQuery("SELECT * FROM members WHERE userid = " + m.getIdLong());
							String lan = getTheUserData.getString("selcLan");
							String gender = getTheUserData.getString("gen");
							Queue.addUserToQueue(g, m, lan, gender);
							
							if (!Queue.matchUser(g, m)) {

								
								ch1.sendMessage(Translations.machError(currentLanguage)).queue();
							}
							
							ResultSet getTheUserData1 = LiteSQL
									.onQuery("SELECT * FROM members WHERE userid = " + m2.getIdLong());
							String lan1 = getTheUserData1.getString("selcLan");
							String gender1 = getTheUserData1.getString("gen");
							Queue.addUserToQueue(g2, m2, lan1, gender1);
							
							if (!Queue.matchUser(g2, m2)) {
							
								Queue.addUserToQueue(g2, m2, lan1, gender1);
								ch1.sendMessage(Translations.machError(currentLanguage)).queue();
							}

						} else {
							ResultSet getTheUserData = LiteSQL
									.onQuery("SELECT * FROM members WHERE userid = " + m.getIdLong());
							String lan = getTheUserData.getString("selcLan");
							String gender = getTheUserData.getString("gen");
							Queue.addUserToQueue(g, m, lan, gender);
							if (!Queue.matchUser(g, m)) {

								Queue.addUserToQueue(g, m, lan, gender);
								ch.sendMessage(Translations.machError(currentLanguage)).queue();
							}
						
						}
					}

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
			
			event.deferEdit().queue();
		}
	}
