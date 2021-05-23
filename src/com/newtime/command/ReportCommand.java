package com.newtime.command;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
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
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ReportCommand extends ListenerAdapter {

	private Member m2 = null;
	private Guild g2 = null;
	
	@SuppressWarnings("unused")
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
                
        if (event.getMessage().getContentRaw().startsWith(Main.prefix + "report")) {
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
			
			//Hier gehts los
		
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
			
			if(args.length >= 7) {
				 long totalCharacters = argsstring.chars().filter(ch1 -> ch1 != ' ').count();
				if(totalCharacters >= 15) {
					 String temp = lan;
					 m.getUser().openPrivateChannel().queue(dm ->{
						
						 dm.sendMessage(Translations.UserReport(temp, "dm", argsstring)).queue();
						 
					 });
					 ch.sendMessage(Translations.UserReport(lan, "dmSen", "na")).queue();

					 
					 
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

					 Main.jda.getTextChannelById(840977106196365332l).sendMessage(new EmbedBuilder()
							 .setTitle("<:peepoban:759536574240391169> Ome.tv Report <:peepoban:759536574240391169>")
							 .setDescription("Der folgende Report wurde von dem User " + m.getUser().getAsTag() + "/" + m.getAsMention() + " gemeldet: \n \n **" + Übersetzer.übersetzer(argsstring, "de") + "** \n \n *Original: " + argsstring +"* \n \n Connection Data: \n User: **" + m2.getUser().getAsTag() + "** \n Guild: ** " + g2.getName() + "** \n \n *<:haken:759377859365830664> = Report gelöst* \n *<:kreuz:759377868441649172> = Kein Report*  \n")
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
				 }else
					Fehler.sendError("Please wirte 15 or more characters.", ch, "na");
			}else
				 Fehler.sendError("Please wirite 7 or more words", ch, "na");
		       

		  
		        
        }
    }
        
 }
