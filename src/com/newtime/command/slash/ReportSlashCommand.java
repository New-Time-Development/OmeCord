package com.newtime.command.slash;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import com.newtime.database.LiteSQL;
import com.newtime.main.Main;
import com.newtime.system.Queue;
import com.newtime.util.Fehler;
import com.newtime.util.Translations;
import com.newtime.util.Übersetzer;

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

public class ReportSlashCommand extends ListenerAdapter{
	
	private Member m2;
	private Guild g2;
	
	public void onSlashCommand(SlashCommandEvent event) {
		
		if(event.getName().equalsIgnoreCase("report")) {
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
			
			String message = event.getOption("message").getAsString();
			
				 long totalCharacters = message.chars().filter(ch1 -> ch1 != ' ').count();
				if(totalCharacters >= 15) {
					 String temp = lan;
					 m.getUser().openPrivateChannel().queue(dm ->{
						
						 dm.sendMessage(Translations.UserReport(temp, "dm", message)).queue();
						 
					 });
					event.reply(Translations.UserReport(lan, "dmSen", "na")).queue();

					 
					 
					 HashMap<Member, Guild> user1 = new HashMap<>();
						user1.put(m, g);
						if (Queue.activeUser.containsKey(user1)) {

							HashMap<Member, Guild> user2 = Queue.activeUser.get(user1);

							user2.keySet().forEach(test -> {
								m2 = test;
							});

							 g2 = user2.get(m2);

						} else if (Queue.activeUser.containsValue(user1)) {
							HashMap<Member, Guild> user2 = Queue.getKeyFromValue(Queue.activeUser, m);

							user2.keySet().forEach(test -> {
								m2 = test;
							});

							 g2 = user2.get(m2);
						}

					 Main.jda.getTextChannelById(845346683994374151l).sendMessageEmbeds(new EmbedBuilder()
							 .setTitle("<:peepoban:759536574240391169> Ome.tv Report <:peepoban:759536574240391169>")
							 .setDescription("Der folgende Report wurde von dem User " + m.getUser().getAsTag() + "/" + m.getAsMention() + " gemeldet: \n \n **" + Übersetzer.übersetzer(message, "de") + "** \n \n *Original: " + message +"* \n \n Connection Data: \n User: **" + m2.getUser().getAsTag() + "** \n Guild: ** " + g2.getName() + "** \n \n *<:haken:759377859365830664> = Report gel�st* \n *<:kreuz:759377868441649172> = Kein Report*  \n")
							 .setColor(Color.YELLOW)
							 .setAuthor(m.getUser().getName())
							 .setFooter("User Report", g.getIconUrl())
							 .setThumbnail(m.getUser().getAvatarUrl())
							 .build()).queue(repoM ->{	
								 repoM.crosspost().queue();
								 repoM.addReaction(":haken:759377859365830664").queue();
								 repoM.addReaction(":kreuz:759377868441649172").queue();
							 });
					 Main.jda.getTextChannelById(840977106196365332l).sendMessage("" + Main.jda.getGuildById(747061203070746624l).getPublicRole().getAsMention()).complete().delete().completeAfter(3, TimeUnit.SECONDS);
				 }else {
					 Fehler.sendError("Please wirte 15 or more characters.", event.getTextChannel(), "na");
					 event.deferReply();
				 }
					
		       
		}
		
	}

}
