package de.sage.cmd.dev;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import de.sage.LiteSQL.LiteSQL;
import de.sage.main.Main;
import de.sage.util.Translations;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class PremiumRemove extends ListenerAdapter{
	
	@SuppressWarnings("unused")
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		  if (event.getMessage().getContentRaw().startsWith(Main.prefix + "rempre")) {
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
				
				if(mes.getMentionedMembers().size() == 1) {
					
					ResultSet premiumRS = LiteSQL.onQuery("SELECT * FROM premium WHERE userid = " + mes.getMentionedMembers().get(0));
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
						LiteSQL.onUpdate("DELETE FROM premium WHERE userid = " +  mes.getMentionedMembers().get(0).getIdLong());
						ch.sendMessage("Der User hat nun kein Premium mehr!").queue();
						mes.getMentionedMembers().get(0).getUser().openPrivateChannel().queue(dm ->{
							ResultSet lanRS = LiteSQL.onQuery("SELECT * FROM lang WHERE userid = " +mes.getMentionedMembers().get(0));
							String lan = null;
							try {
								if(lanRS.next()) {
									lan = lanRS.getString("botLan");
								}
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							dm.sendMessage(Translations.PremiumEndByAdmin(lan)).queue(null , fail -> ch.sendMessage("Der User hat keine DM bekommen"));
						});
					}
					
					//DONT CHANGE
					premium = false;
				
				}

	        }
	}

}
