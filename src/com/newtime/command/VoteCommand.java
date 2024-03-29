package com.newtime.command;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.newtime.database.LiteSQL;
import com.newtime.main.Main;
import com.newtime.util.Error;
import com.newtime.util.TopGG;
import com.newtime.util.Translations;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * @author SageSphinx63920
 *
 * Copyright (c) 2019 - 2021 by New Time Development/Sage to present. All rights reserved
 */


public class VoteCommand extends ListenerAdapter{
	
	@SuppressWarnings("unused")
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		  if (event.getMessage().getContentRaw().startsWith(Main.prefix + "vote")) {
	        	//Angaben
	        	TextChannel ch = event.getChannel();
	        	Member m = event.getMember();
	        	Guild g = event.getGuild();
	        	Message mes = event.getMessage();
	        	List<Color> ColorList = Arrays.asList(Color.GREEN,Color.BLUE, Color.CYAN, Color.LIGHT_GRAY, Color.MAGENTA, Color.ORANGE, Color.PINK, Color.RED, Color.WHITE, Color.YELLOW, Color.decode("#0b0064"));
				Color Color_RANDOM = ColorList.get(new Random().nextInt(ColorList.size()));
				String[] args = event.getMessage().getContentDisplay().split(" ");
				int length = args.length;
				 StringBuilder strbuild = new StringBuilder();
																									     //1 	2
				for(int i = 2; i < length; i++) strbuild.append(args[i] + " "); //i = anzahl von suvfix: e.ichbincool
					
				String argsstring = strbuild.toString().trim();
				
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
				boolean premium = false;
				try {
					if(premiumRS.next()) {
						premium = true;
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				//Hier gehts los
				if(length == 1) {
					//Hier gehts los
		          	  ch.sendMessageEmbeds(new EmbedBuilder()
		          			  .setColor(Color_RANDOM)
		          			  .setTitle("Vote for me!")
		          			  .setDescription(Translations.Vote(lan, "info", m))
		          			  .build()).queue();      
				}else if(args[1].toLowerCase().equalsIgnoreCase("check")) {
					
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
												
												ch.sendMessage("Your vote was accepted. Check <#856892710367002665> out!").queue();
												} catch (SQLException e1) {
													// TODO Auto-generated catch block
													e1.printStackTrace();
												}
											}else
												ch.sendMessage("You haven´t voted!").queue();
							  

								});
							}else
								ch.sendMessage("You have already checked your vote!").queue();
						
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else if(args[1].equalsIgnoreCase("list")) {
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
								        	ch.sendMessageEmbeds(new EmbedBuilder()
									        		.setTitle("Your Votes")
									        		.setDescription("You have **" + coins + "** Vote-Coins \n You voted **" + votes + "** times for us!\n You can check your vote again!")
									        		
									        		.build()).queue();
										  }else
											 ch.sendMessageEmbeds(new EmbedBuilder()
										        		.setTitle("Your Votes")
										        		.setDescription("You have **" + coins + "** Vote-Coins \n You voted **" + votes + "** times for us!\n You can check again at** " + formattedDate + "** (MEZ)")
										        		
										        		.build()).queue();
									}catch(Exception e1) {
										e1.printStackTrace();
										
									}
								    
					} catch (SQLException e) {
						// TODO Auto-generated catch block
					
						//e.printStackTrace();
					}
				}else
					Error.sendError("Wrong Input", ch, lan);
				
				
				//DONT CHANGE
				premium = false;
				lan = "en";
	        }
	}

}
