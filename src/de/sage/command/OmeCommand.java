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

public class OmeCommand extends ListenerAdapter{
	
	private List<String> langs = Arrays.asList("de", "en", "trk", "fr", "es", "ru");
	private List<String> gender = Arrays.asList("male", "female", "couples", "männlich", "weiblich", "paare");
	
	@SuppressWarnings("unused")
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		  if (event.getMessage().getContentRaw().startsWith(Main.prefix + "ome")) {
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
				
				ResultSet userData = LiteSQL.onQuery("SELECT * FROM members WHERE userid = " + m.getIdLong());
				if(args.length == 4) {
					if(langs.contains(args[1].toLowerCase())) {
						if(gender.contains(args[2].toLowerCase())) {
							if(args[3].equalsIgnoreCase("yes") || args[3].equalsIgnoreCase("no") ) {
								try {
									if(userData.next()) {
										int mute = userData.getInt("mute");
										LiteSQL.onUpdate("DELETE FROM members WHERE userid = " + m.getIdLong());
										LiteSQL.onUpdate("INSERT INTO members(userid, selcLan, gen, ueber, mute) VALUES(" + m.getIdLong() + ", '" + args[1] +"', '" + args[2] + "', '" + args[3] + "', " + mute + ")");
										ch.sendMessage(Translations.OmeCmd(lan, "update")).queue();
									}else {
										int days = 259200000;
										long unix = System.currentTimeMillis();
										long endFree = unix + days;
										LiteSQL.onUpdate("INSERT INTO premium(userid, time, code, end) VALUES(" + m.getIdLong() + ", " + System.currentTimeMillis() + ", 'NewUser', " + endFree + ")");
										
										Main.jda.getTextChannelById(845346722691678278l).sendMessage(new EmbedBuilder()
					          	  				.setTitle("Codeaktivierung Ome.tv")
					          	  				.setDescription("Der User " + m.getAsMention() + "** / ** " + m.getUser().getAsTag() + "** ist neu und hat 3 Tage gratis premium!")
					          	  				.setColor(Color.GREEN)
					          	  				.setFooter("Codeaktivierung")
					          	  				.build()).queue();
										
										LiteSQL.onUpdate("INSERT INTO members(userid, selcLan, gen, ueber, mute) VALUES(" + m.getIdLong() + ", '" + args[1] +"', '" + args[2] + "', '" + args[3] + "', 0)");
										ch.sendMessage(Translations.OmeCmd(lan, "insert")).queue();
									}
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}else
							ch.sendMessage(Translations.OmeCmd(lan, "gen")).queue();
					}else 
						ch.sendMessage(Translations.OmeCmd(lan, "lang")).queue();
				}else
					Translations.Fehler("Incorrect useage", ch, lan);
				
				//DONT CHANGE
				premium = false;
				lan = "en";
	        }
	}

}