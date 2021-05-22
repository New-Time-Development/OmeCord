package de.sage.command.devs;

import java.awt.Color;
import java.sql.ResultSet;
import java.time.OffsetDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import de.sage.database.LiteSQL;
import de.sage.main.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Shutdown extends ListenerAdapter{
	
	@SuppressWarnings("unused")
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		  if (event.getMessage().getContentRaw().startsWith(Main.prefix + "shutdown")) {
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
				
				ResultSet lanRS = LiteSQL.onQuery("SELECT * FROM sprache WHERE userid = " + m.getIdLong());
				String lan = null;
			
				
				
				//Hier gehts los
				TemporalAccessor t = OffsetDateTime.now();
				for(long userid : Main.OwnerIds) {
					
					Main.jda.openPrivateChannelById(userid).queue(dm ->{
						dm.sendMessage(new EmbedBuilder()
								.setTimestamp(t)
								.setDescription("Der Bot wurde runtergefahren!")
								.setAuthor(Main.jda.getUserById(userid).getAsTag())
								.setThumbnail(Main.jda.getSelfUser().getAvatarUrl())
								.setTitle("Bot Shudtown")
								.build()).queue();
					});
				}
				
				ch.sendMessage("Der Bot fährt runter!").queue();
				
				Timer shutT = new Timer();
				shutT.schedule(new TimerTask() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						
						shutT.cancel();
						Main.jda.shutdown();
						System.exit(0);
					}
				}, 3000);	
				
				
				//DONT CHANGE
				lan = "en";
	        }
	}

}
