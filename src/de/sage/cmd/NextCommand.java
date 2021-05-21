package de.sage.cmd;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import de.sage.LiteSQL.LiteSQL;
import de.sage.listener.OnOmeChannelJoin;
import de.sage.main.Main;
import de.sage.system.Queue;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class NextCommand extends ListenerAdapter{
	
	Member m2 = null;
	
	@SuppressWarnings("unused")
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		  if (event.getMessage().getContentRaw().startsWith(Main.prefix + "next")) {
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
				for(int i = 2; i < length; i++) strbuild.append(args[i] + " "); //i = anzahl von suvfix: !.ichbincool
					
				String argsstring = strbuild.toString().trim();
				
				//Hier gehts los
				ResultSet getUserData = LiteSQL.onQuery("SELECT * FROM members WHERE userid = " + m.getIdLong());
				
				try {
					if(getUserData.next()) {
							
						if(OnOmeChannelJoin.omechannels.containsValue(ch)) {
							HashMap<Member, Guild> user1 = new HashMap<>();
							user1.put(m, g);
							if(Queue.activeUser.containsKey(user1)) {

								HashMap<Member, Guild> user2 = Queue.activeUser.get(user1);

									
								user2.keySet().forEach(test ->{
									m2 = test;
								});

								Guild g2 = user2.get(m2);
								//Chat zeugs
								
								Queue.removeFromActiveList(m, m2, g, g2);
							}else if(Queue.activeUser.containsValue(user1)) {
								HashMap<Member, Guild> user2 = Queue.getKeyFromValue(Queue.activeUser, m);
								
								user2.keySet().forEach(test ->{
									m2 = test;
								});

								Guild g2 = user2.get(m2);
								//Chat zeugs
								
								Queue.removeFromActiveList(m2, m, g2, g);
								
								//Chat zeuegs
								
								
							}else {
								
								Queue.matchUser(g, m);
								
							}
						}
	
					}
				}catch (SQLException e) {
					e.printStackTrace();
				}
	        
	 }
	}
}