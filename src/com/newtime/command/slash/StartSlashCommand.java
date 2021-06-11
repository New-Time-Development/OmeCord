package com.newtime.command.slash;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import com.newtime.database.LiteSQL;
import com.newtime.listener.OnOmeChannelJoin;
import com.newtime.system.Queue;
import com.newtime.util.Translations;

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

public class StartSlashCommand extends ListenerAdapter{
	
	public void onSlashCommand(SlashCommandEvent event) {
		
		if(event.getName().equalsIgnoreCase("start")) {
			Guild g = event.getGuild();
			Member m = event.getMember();
			ResultSet getUserData = LiteSQL.onQuery("SELECT * FROM members WHERE userid = " + m.getIdLong());

			try {
				if (getUserData.next()) {

					if (OnOmeChannelJoin.omechannels.containsValue(event.getTextChannel())) {

						String lan = getUserData.getString("selcLan");
						String gender = getUserData.getString("gen");
						int isMuted = getUserData.getInt("mute");
						
						if(isMuted == 1) {
							event.reply(Translations.Mute(lan)).queue();
							g.kickVoiceMember(m).queue();
							return;
						}
						
						event.replyEmbeds(new EmbedBuilder()
								.setTitle("Queue")
								.setDescription(Translations.Start(lan))
								.setColor(Color.ORANGE)
								.setAuthor("OmeCord Team")
								.build()).queue();
						
						HashMap<Long, Long> user = new HashMap<>();
						user.put(g.getIdLong(), m.getIdLong());
						
						HashMap<Member, Guild> activeData = new HashMap<>();
						activeData.put(m, g);
						
						if(!Queue.WaitingUsers.containsKey(user) ) {
							if(!Queue.activeUser.containsKey(activeData) || !Queue.activeUser.containsValue(activeData)) {
								Queue.addUserToQueue(g, m, lan, gender);
							}
							
						}
						
					
					}

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

}
