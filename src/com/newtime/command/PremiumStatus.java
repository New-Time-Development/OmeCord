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
import com.newtime.util.Translations;
import com.newtime.util.checks.CheckPremium;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class PremiumStatus extends ListenerAdapter{
	
	@SuppressWarnings("unused")
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		  if (event.getMessage().getContentRaw().startsWith(Main.prefix + "premium")) {
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
																									     //1 	
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
				
				CheckPremium.checkStatus(m, ch, lan);
				
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
				if(length > 1) {
					if(args[1].equalsIgnoreCase("check")) {
						if(premium) {
							try {
								ResultSet getEnding = LiteSQL.onQuery("SELECT * FROM premium WHERE userid = " + m.getIdLong());
								long end = getEnding.getLong("end");
								
								if(end  == 0) {
										ch.sendMessage(new EmbedBuilder()
												.setTitle("Premium Status")
												.setDescription(Translations.premiumStatus(lan, "endlos", premium))
												.setThumbnail("https://cdn.discordapp.com/attachments/840976542300372992/845307912750759966/455f8be75219_2018px-Red_star.svg.png")
												.setColor(Color.MAGENTA)
												.build()).queue();
				          	  		}else {
					          	  		Date time=  new Date(end);
					          	  		
						          	    SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm | dd/MM/yyyy"); 
						          	     String formattedDate = sdf.format(time);
						          	   ch.sendMessage(new EmbedBuilder()
												.setTitle("Premium Status")
												.setDescription(Translations.premiumStatus(lan, formattedDate, premium))
												.setThumbnail("https://cdn.discordapp.com/attachments/840976542300372992/845307912750759966/455f8be75219_2018px-Red_star.svg.png")
												.setColor(Color.MAGENTA)
												.build()).queue();
				          	  		}

							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
						}

					}else {
						ch.sendMessage(new EmbedBuilder()
								.setTitle("Premium Status")
								.setDescription(Translations.premiumStatus(lan, "endlos", premium))
								.setThumbnail("https://cdn.discordapp.com/attachments/840976542300372992/845307912750759966/455f8be75219_2018px-Red_star.svg.png")
								.setColor(Color.MAGENTA)
								.build()).queue();
					}

				}
				
				}else {
					ch.sendMessage(Translations.PremiumFUnktions(lan)).queue();
				}
				//DONT CHANGE
				premium = false;
				lan = "en";
	        }
	
	}
}
