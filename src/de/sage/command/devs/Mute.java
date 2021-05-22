package de.sage.command.devs;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import de.sage.database.LiteSQL;
import de.sage.main.Main;
import de.sage.util.Translations;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Mute extends ListenerAdapter{
	@SuppressWarnings("unused")
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
                
        if (event.getMessage().getContentRaw().startsWith(Main.prefix + "mute")) {
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
			
			//Hier gehts los
          	   if(Main.OwnerIds.contains(m.getIdLong())) {
						  if(length >= 2) {
							  if(args[2].equalsIgnoreCase("0")) {
								  ResultSet getData = LiteSQL.onQuery("SELECT * FROM mute WHERE userid = " + m.getIdLong());
								  try {
									if(getData.next()) {
										  int val = getData.getInt("val");
										  LiteSQL.onUpdate("UPDATE mute SET val = " + val++);
										  LiteSQL.onUpdate("UPDATE mute SET end = 0");
									  }else {
										  LiteSQL.onUpdate("INSERT INTO mute(userid, end, val) VALUES(" + m.getIdLong() + ", 0, 1)");
									  }
									Main.jda.getUserById(args[1]).openPrivateChannel().queue(dm ->{
										ResultSet getLan = LiteSQL.onQuery("SELECT * FROM lang WHERE userid = " + args[1]);
										String lang;
										try {
											lang = getLan.getString("botLan");
											dm.sendMessage(Translations.SendMute(lang, "endlos")).queue();
										} catch (SQLException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										
									});
									ch.sendMessage("Der User **" + Main.jda.getUserById(args[1]).getAsTag() + "** wurde permanent gemuted").queue();
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

						       }else if(args[2].equalsIgnoreCase("end")){
						    	   ResultSet getData = LiteSQL.onQuery("SELECT * FROM mute WHERE userid = " + m.getIdLong());
									  try {
										if(getData.next()) {
											LiteSQL.onUpdate("UPDATE mute SET end = " + (System.currentTimeMillis() - 1));
										  }else {
											ch.sendMessage("Der user war nie gemuted!").queue();
										  }
										Main.jda.getUserById(args[1]).openPrivateChannel().queue(dm ->{
											ResultSet getLan = LiteSQL.onQuery("SELECT * FROM lang WHERE userid = " + args[1]);
											String lang;
											try {
												lang = getLan.getString("botLan");
												dm.sendMessage(Translations.muteEnd(lang)).queue();
											} catch (SQLException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
											
										});
										ch.sendMessage("Der User **" + Main.jda.getUserById(args[1]).getAsTag() + "** wurde pentmuted! HGW xd <:pepe_cool:845333706625122334>").queue();
									} catch (SQLException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
						       }else {
						    	   SimpleDateFormat formatter = new SimpleDateFormat("HH:mm dd/MM/yyyy");  

						    	    try {
						    	    	Date date = formatter.parse(argsstring);
						    	    	
						    	    	 ResultSet getData = LiteSQL.onQuery("SELECT * FROM mute WHERE userid = " + m.getIdLong());

											if(getData.next()) {
												  int val = getData.getInt("val");
												  LiteSQL.onUpdate("UPDATE mute SET val = " + val++);
												  LiteSQL.onUpdate("UPDATE mute SET end = "+ date.getTime());
											  }else {
												  LiteSQL.onUpdate("INSERT INTO mute(userid, end, val) VALUES(" + m.getIdLong() + ", " + date.getTime() + ", 1)");
											  }
											
											ch.sendMessage("Der User **" + Main.jda.getUserById(args[1]).getAsTag() + "** wurde bis zum **" + argsstring + "** gemuted").queue();

											Main.jda.getUserById(args[1]).openPrivateChannel().queue(dm ->{
												ResultSet getLan = LiteSQL.onQuery("SELECT * FROM lang WHERE userid = " + args[1]);
												String lang;
												try {
													lang = getLan.getString("botLan");
													dm.sendMessage(Translations.SendMute(lang, argsstring)).queue();
												} catch (SQLException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
												
											});
						    	    
						    	    }catch(Exception e) {
						    	    	e.printStackTrace();
						    	    }
						        }
							  
						 }

          		  }
          	   
          	  }
        }
	}
