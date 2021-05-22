package de.sage.command;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import de.sage.database.LiteSQL;
import de.sage.main.Main;
import de.sage.util.Translations;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CustomJoin extends ListenerAdapter{
	
	@SuppressWarnings("unused")
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		  if (event.getMessage().getContentRaw().startsWith(Main.prefix + "join")) {
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
				for(int i = 1; i < length; i++) strbuild.append(args[i] + " "); //i = anzahl von suvfix: e.ichbincool
					
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
				if(premium) {
					if(args.length >= 10) {
						 long totalCharacters = argsstring.chars().filter(ch1 -> ch1 != ' ').count();
						if(totalCharacters >= 25) {
							if(argsstring.contains("$user") && argsstring.contains("$server")) {
								
								List<String> BAD_WORDS = Arrays.asList("fick", "fuck", "LGBTQ", "LGBTQ+", "nudes", "nude", "hure", "nutte", "huren", "whore", "bastart", "ficke", "ficken", "porno", "porn", "hurensohn", "schlampe", "fotze", "ficken", "Depp", "idiot", "vollidiot", "arschkriecher", "wichser", "wixxer", "mee6", "wixer", "arsch", "arschloch", "drecksau", "trottel", "hackfresse", "dummkopf", "Dreckschwein", "sau", "bitch", "schwachkopf", "pussy", "pu$$y", "ass", "boobs", "titten", "brüste", "Dickhead", "scheide", "vagina");
			                    
								for (int i = 0; i < args.length; i++) {
			                        for (int j = 0; j < BAD_WORDS.size(); j++) {
			                            if(BAD_WORDS.contains(args[j].toLowerCase())) {
			                                event.getMessage().addReaction(":dnd:759536079010398239").queue();
			                                Main.jda.getTextChannelById(845404663297212466l).sendMessage(new EmbedBuilder()
			                                		.setTitle("BadWord")
			                                		.setColor(Color.decode("#aa873d"))
			                                		.setDescription("Der User **" + event.getMember().getAsMention() + "** / **" + event.getAuthor().getAsTag() + "** \n hat in der folgenden Custom Join Message ein BadWord genutzt: \n ||" + event.getMessage().getContentRaw() + "||")
			                                		.build()).queue();
			                                return;
			                            }
			                        }
								}
								
								argsstring.replace("_", "");
								String message = argsstring.replace(" ", "_");
								ResultSet haveSet = LiteSQL.onQuery("SELECT * FROM customJoin WHERE userid = " + m.getIdLong());
								try {
									if(haveSet.next()) {
										LiteSQL.onUpdate("UPDATE customJoin SET mes = '" + message + "'");
										message.replace("$server", g.getName());
										message.replace("$user", g.getName());
										ch.sendMessage("You message is now:** " +  message + "**").queue();
									}else {
										LiteSQL.onUpdate("INSERT INTO customJoin(userid, mes) VALUES(" + m.getIdLong() + ", '" + message + "')");
										message.replace("$server", g.getName());
										message.replace("$user", g.getName());
										ch.sendMessage("You message is now:** " +  message + "**").queue();
									}
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}else
								ch.sendMessage("Please use $user and $server!").queue();
						}else
							ch.sendMessage("You can only use up to 25 Characters").queue();
					}else
						ch.sendMessage("You can only use up to 10 words").queue();
				}else
					Translations.onlyPre(lan);
				
				
				//DONT CHANGE
				premium = false;
				lan = "en";
	        }
	}

}
