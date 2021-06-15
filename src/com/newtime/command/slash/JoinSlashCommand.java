package com.newtime.command.slash;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.StringUtils;

import com.newtime.database.LiteSQL;
import com.newtime.main.Main;
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

public class JoinSlashCommand extends ListenerAdapter{
	
	@SuppressWarnings("unused")
	public void onSlashCommand(SlashCommandEvent event) {
		
		if(event.getName().equalsIgnoreCase("join")) {
        	Member m = event.getMember();
        	Guild g = event.getGuild();
        	List<Color> ColorList = Arrays.asList(Color.GREEN,Color.BLUE, Color.CYAN, Color.LIGHT_GRAY, Color.MAGENTA, Color.ORANGE, Color.PINK, Color.RED, Color.WHITE, Color.YELLOW, Color.decode("#0b0064"));
			Color Color_RANDOM = ColorList.get(new Random().nextInt(ColorList.size()));
				
			String argsstring = event.getOption("message").getAsString();
			argsstring = argsstring.replace("°", " ");
			argsstring = argsstring.replace(" ", "°");
			
			String[] args = StringUtils.split(argsstring, "°");
			
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
				if(args.length <= 10) {
					 long totalCharacters = argsstring.chars().filter(ch1 -> ch1 != ' ').count();
					if(totalCharacters <= 25) {
						if(argsstring.contains("@user") && argsstring.contains("@server")) {
							
							List<String> BAD_WORDS = Arrays.asList("fick", "fuck", "LGBTQ", "LGBTQ+", "nudes", "nude", "hure", "nutte", "huren", "whore", "bastart", "ficke", "ficken", "porno", "porn", "hurensohn", "schlampe", "fotze", "ficken", "Depp", "idiot", "vollidiot", "arschkriecher", "wichser", "wixxer", "mee6", "wixer", "arsch", "arschloch", "drecksau", "trottel", "hackfresse", "dummkopf", "Dreckschwein", "sau", "bitch", "schwachkopf", "pussy", "pu$$y", "ass", "boobs", "titten", "br�ste", "Dickhead", "scheide", "vagina");
		                    
							try {
								for (int i = 0; i < args.length; i++) {
			                        for (int j = 0; j < BAD_WORDS.size(); j++) {
			                            if(BAD_WORDS.contains(args[j].toLowerCase())) {
			                                Main.jda.getTextChannelById(845404663297212466l).sendMessageEmbeds(new EmbedBuilder()
			                                		.setTitle("BadWord")
			                                		.setColor(Color.decode("#aa873d"))
			                                		.setDescription("Der User **" + event.getMember().getAsMention() + "** / **" + event.getUser().getAsTag() + "** \n hat in der folgenden Custom Join Message ein BadWord genutzt: \n ||" + argsstring + "||")
			                                		.build()).queue();
			                                event.deferReply().queue();
			                                return;
			                            }
			                        }
								}
							}catch(Exception e1) {
								
							}

							//argsstring.replace("_", "");
						
							ResultSet haveSet = LiteSQL.onQuery("SELECT * FROM customJoin WHERE userid = " + m.getIdLong());
							try {
								if(haveSet.next()) {
									
									LiteSQL.onUpdate("UPDATE customJoin SET mes = '" + argsstring + "'");
									argsstring = argsstring.replace("@server", g.getName());
									argsstring = argsstring.replace("@user", m.getUser().getAsTag());
									event.reply("You message is now: **" +  argsstring + "**").queue();
								}else {
									LiteSQL.onUpdate("INSERT INTO customJoin(userid, mes) VALUES(" + m.getIdLong() + ", '" + argsstring + "')");
									argsstring = argsstring.replace("@server", g.getName());
									argsstring = argsstring.replace("@user", m.getUser().getAsTag());
									event.reply("You message is now: **" +  argsstring + "**").queue();
								}
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}else
							event.reply("Please use $user and $server!").queue();
					}else
						event.reply("You can only use up to 25 Characters").queue();
				}else
					event.reply("You can only use up to 10 words").queue();
			}else
				event.reply(Translations.onlyPre(lan)).queue();
			
			
			//DONT CHANGE
			premium = false;
			lan = "en";
			
		}
		
	}

}
