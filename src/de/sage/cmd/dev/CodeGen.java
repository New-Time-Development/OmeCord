package de.sage.cmd.dev;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import de.sage.LiteSQL.LiteSQL;
import de.sage.main.Main;
import de.sage.util.RandomStr;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CodeGen extends ListenerAdapter {


	@SuppressWarnings("unused")
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
                
        if (event.getMessage().getContentRaw().startsWith(Main.prefix + "codegen")) {
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
          	   if(Main.OwnerIds.contains(m.getIdLong())) {
          		  String code = RandomStr.generateString(new Random(), "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890", 8) ;
          		 ResultSet set = LiteSQL.onQuery("SELECT * FROM codes WHERE code = '" + code +"'");
          		  try {
					if(set.next()) {
						  ch.sendMessage("Ok du hast es geschafft. Ein code gibt es doppelt. Ein mal applaus für dich! :) Machs einfach nochmal. Es generieren immer neue Codes").queue();
					  }else {
						  if(length >= 1) {
							  if(args[1].equalsIgnoreCase("0")) {
				          		  LiteSQL.onUpdate("INSERT INTO codes(userid, time, code, end) VALUES(" + m.getIdLong() + ", " + System.currentTimeMillis() + ", '" + code + "', 0)");
				          		  ch.sendMessage("DU haben eine neue DM bidde schauen du nach").queue();
				          		  m.getUser().openPrivateChannel().queue(dm ->{
				          			  dm.sendMessage(new EmbedBuilder()
				          					  .setColor(Color.BLUE)
				          					  .setTitle("Ome.tv Premium Code")
				          					  .setFooter("VORSICHTIG MEIN FREUND")
				          					  .setDescription("Bitte zeige diesen Code NIEMALS in einem Stream usw. \n Er schaltet einmalig für einen User alle Premium Funktionen frei. \n Mit ihm ist vorsichtig um zu gehen! \n Hier ist der Code: ||" + code + "||")
				          					  .build()).queue();
				          		  });
				          		  Main.jda.getTextChannelById(840987712873955358l).sendMessage(new EmbedBuilder()
				          				  .setDescription("Hier ein Code von " + m.getAsMention() + "! Bitte NIEMALS im Stream usw. zeigen! \n Code: ||" + code + "||")
				          				  .setColor(Color.orange)
				          				  .setTitle("Ome.tv Premium Code")
				          				  .build()).queue();
						       }else {
						    	   argsstring.replace(Main.prefix + "codegen", "");
						    	   SimpleDateFormat formatter = new SimpleDateFormat("HH:mm dd/MM/yyyy");  

						    	    try
						    	    {
						    	    	Date date = formatter.parse(argsstring);
						    	    	
						          		  LiteSQL.onUpdate("INSERT INTO codes(userid, time, code, end) VALUES(" + m.getIdLong() + ", " + System.currentTimeMillis() + ", '" + code + "', " + date.getTime() + ")");
						          		  ch.sendMessage("DU haben eine neue DM bidde schauen du nach").queue();
						          		  m.getUser().openPrivateChannel().queue(dm ->{
						          			  dm.sendMessage(new EmbedBuilder()
						          					  .setColor(Color.CYAN)
						          					  .setTitle("Temp | Ome.tv Premium Code")
						          					  .setFooter("VORSICHTIG MEIN FREUND")
						          					  .setDescription("Bitte zeige diesen Code NIEMALS in einem Stream usw. \n Er schaltet einmalig für einen User alle Premium Funktionen frei. \n Mit ihm ist vorsichtig um zu gehen! \n Hier ist der Code: ||" + code + "||" + "\n Ende: " + argsstring)
						          					  .build()).queue();
						          		  });
						          		  Main.jda.getTextChannelById(840987712873955358l).sendMessage(new EmbedBuilder()
						          				  .setDescription("Hier ein Temp-Code von " + m.getAsMention() + "! Bitte NIEMALS im Stream usw. zeigen! \n Code: ||" + code + "||" + "\n Ende: " + argsstring)
						          				  .setColor(Color.YELLOW)
						          				  .setTitle("Ome.tv Premium Code")
						          				  .build()).queue();
						    	    }
						    	    catch (ParseException e)
						    	    {
						    	      e.printStackTrace();
						    	    }
						       } 
						  }
					     

		          		  
					  }
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
          	   }
        }
    }
        
 }
