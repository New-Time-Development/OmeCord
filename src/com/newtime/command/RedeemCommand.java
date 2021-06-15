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

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class RedeemCommand extends ListenerAdapter {


	@SuppressWarnings( "unused")
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
                
        if (event.getMessage().getContentRaw().startsWith(Main.prefix + "redeem")) {
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
          	  ResultSet set = LiteSQL.onQuery("SELECT * FROM premium WHERE userid = " + m.getIdLong());  
          	  if(args.length >= 2) {
          	  try {
  
				if(!set.next()) {
		          	  ResultSet set1 = LiteSQL.onQuery("SELECT * FROM codes WHERE code = '" + args[1] + "'");    
		          	  	if(set1.next()){
		          	  		
		          	  		long canEnd = set1.getLong("end");
		          	  		
				          	ResultSet marcel = LiteSQL.onQuery("SELECT * FROM codes WHERE code = '" + args[1] + "'");

				          	long userid = marcel.getLong("userid");
				          	
		          	  		
		          	  		LiteSQL.onUpdate("DELETE FROM codes WHERE code = '" + args[1] + "'");
		          	  		if(canEnd == 0) {
		          	  		ch.sendMessage("Your code is valid. You actived premium for your account. Use !premium to check out your new functions").queue();
		          	  		LiteSQL.onUpdate("INSERT INTO premium(userid, time, code, end) VALUES(" + m.getIdLong() + ", " + System.currentTimeMillis() + ", '" + args[1] + "', 0)");
		          	  		Main.jda.getTextChannelById(845346722691678278l).sendMessageEmbeds(new EmbedBuilder()
		          	  				.setTitle("Codeaktivierung Ome.tv")
		          	  				.setDescription("Der Code ||" + args[1] + "|| von " + m.getAsMention() + "** / ** " + m.getUser().getAsTag() + "** engel�st.")
		          	  				.setColor(Color.GREEN)
		          	  				.setFooter("Codeaktivierung")
		          	  				.build()).queue();
		          	  		}else {
		          	  		Date time=  new Date(canEnd);
		          	  		
		          	  	SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm | dd/MM/yyyy"); 
		          	// give a timezone reference for formatting (see comment at the bottom)
		          //	sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT-2")); 
		          	String formattedDate = sdf.format(time);
		          	  		ch.sendMessage("Your code is valid. You actived premium for your account. Use !premium to check out your new functions. Premium ends at: " + formattedDate+ " (MEZ)").queue();
		          	  		LiteSQL.onUpdate("INSERT INTO premium(userid, time, code, end) VALUES(" + m.getIdLong() + ", " + System.currentTimeMillis() + ", '" + args[1] + "', " + canEnd + ")");
		          	  		Main.jda.getTextChannelById(845346722691678278l).sendMessageEmbeds(new EmbedBuilder()
		          	  				.setTitle("Codeaktivierung Ome.tv")
		          	  				.setDescription("Der Code ||" + args[1] + "|| von " + m.getAsMention() + "** / ** " + m.getUser().getAsTag() + "** engel�st.")
		          	  				.setColor(Color.GREEN)
		          	  				.setFooter("Codeaktivierung")
		          	  				.build()).queue();
		          	  		}
		          	  		
		          	  		 
		          	  	}else {
		          	  		ch.sendMessage("Your code is invaild. Please correct it or contact our support on https://discord.com/j8emH5ap3k").queue();
		          	  	}
				  }else{
					  ch.sendMessage("More than the premium version does not exist. You have already claimed premium with a key for your account. You can give the key to your friend or make a giveaway :)").queue();
				  }
          	  
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
          }else
        	  ch.sendMessage("Please use !redeem <code>").queue();
        }
    }
        
 }
