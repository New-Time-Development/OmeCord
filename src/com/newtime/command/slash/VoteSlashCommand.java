package com.newtime.command.slash;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.newtime.database.LiteSQL;
import com.newtime.main.Main;
import com.newtime.util.TopGG;
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

public class VoteSlashCommand extends ListenerAdapter{
	
	@SuppressWarnings("unused")
	public void onSlashCommand(SlashCommandEvent event) {
		
		if(event.getName().equalsIgnoreCase("vote")) {
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
			
			if(event.getOptions().size() == 0) {
				 event.replyEmbeds(new EmbedBuilder()
	          			  .setColor(Color.YELLOW)
	          			  .setTitle("Vote for me!")
	          			  .setDescription(Translations.Vote(lan, "info", m))
	          			  .build()).queue();     
			}else {
				if(event.getOption("command").getAsString().equalsIgnoreCase("check")){
					ResultSet set = LiteSQL.onQuery("SELECT * from vote WHERE userid = " + m.getIdLong());
					try {
						if(!set.next()) {
							LiteSQL.onUpdate("INSERT INTO vote(userid, coins, votes, time) VALUES(" + m.getIdLong() + ", " + "0, 0, 0)");
						}
						
							  long unix = System.currentTimeMillis();
							  long timer12 = set.getLong("time");
							  if(unix >= timer12) {
									String userId = m.getId();
										
										TopGG.getApi().hasVoted(userId).whenComplete((hasVoted, e) -> {
											if(hasVoted == true) {
												
												
												ResultSet votesR = LiteSQL.onQuery("SELECT * from vote WHERE userid = " + m.getIdLong());
												ResultSet coinsR = LiteSQL.onQuery("SELECT * from vote WHERE userid = " + m.getIdLong());
												
												try {
												
													int votes = votesR.getInt("votes");
											        int coins = coinsR.getInt("coins");
												
											        int votesN = votes + 1;
													int coinsN = coins + 1;
											        
												Main.jda.getTextChannelById(856892710367002665l).sendMessageEmbeds(new EmbedBuilder() 
														.setTitle("New Vote")
														.setColor(Color.MAGENTA)
														.setImage("https://cdn.discordapp.com/attachments/845335992440324127/856894995244384316/1672409.png")
														.setFooter("Top.gg vote")
														.setDescription(m.getUser().getAsTag() + " has voted " + votesN + " time for us. You have " + coinsN + " vote coins!")
														.build()).queue(); 
												
												
												
												long unixT = System.currentTimeMillis();
												
												long timer = unixT + 43200000l;
												
												
												LiteSQL.onUpdate("UPDATE vote SET votes = " + votesN + " WHERE userid = " + m.getIdLong());
												LiteSQL.onUpdate("UPDATE vote SET time = " + timer + " WHERE userid = " + m.getIdLong());
												LiteSQL.onUpdate("UPDATE vote SET coins = " + coinsN + " WHERE userid = " + m.getIdLong());
												
												event.reply("Your vote was accepted. Check <#856892710367002665> out!").queue();
												} catch (SQLException e1) {
													// TODO Auto-generated catch block
													e1.printStackTrace();
												}
											}else
												event.reply("You haven´t voted!").queue();
							  

								});
							}else
								event.reply("You have already checked your vote!").queue();
						
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else if(event.getOption("command").getAsString().equalsIgnoreCase("list")){
					ResultSet set = LiteSQL.onQuery("SELECT * from vote WHERE userid = " + m.getIdLong());
					try {
						if(!set.next()) {
							LiteSQL.onUpdate("INSERT INTO vote(userid, coins, votes, time) VALUES(" + m.getIdLong() + ", " + "0, 0, 0)");
						}
							long timer12 = set.getLong("time");
							  long unix = System.currentTimeMillis();
							  
							
			          	  		Date time=  new Date(timer12);
			          	  		
				          	  	SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm | dd/MM/yyyy"); 
				          	String formattedDate = sdf.format(time);
	    
								    ResultSet votesR = LiteSQL.onQuery("SELECT * from vote WHERE userid = " + m.getIdLong());
									ResultSet coinsR = LiteSQL.onQuery("SELECT * from vote WHERE userid = " + m.getIdLong());
									
									try {
										int votes = votesR.getInt("votes");
								        int coins = coinsR.getInt("coins");
								        
								        if(unix >= timer12) {
								        	event.replyEmbeds(new EmbedBuilder()
									        		.setTitle("Your Votes")
									        		.setDescription("You have **" + coins + "** Vote-Coins \n You voted **" + votes + "** times for us!\n You can check your vote again!")
									        		
									        		.build()).setEphemeral(true).queue();
										  }else
											event.replyEmbeds(new EmbedBuilder()
										        		.setTitle("Your Votes")
										        		.setDescription("You have **" + coins + "** Vote-Coins \n You voted **" + votes + "** times for us!\n You can check again at** " + formattedDate + "** (MEZ)")
										        		
										        		.build()).setEphemeral(true).queue();
									}catch(Exception e1) {
										e1.printStackTrace();
										
									}
								    
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		
	}

}