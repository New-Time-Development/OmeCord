package de.sage.chat;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import de.sage.LiteSQL.LiteSQL;
import de.sage.listener.OnOmeChannelJoin;
import de.sage.main.Main;
import de.sage.util.Translations;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ChattingListener extends ListenerAdapter{

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
    	if(event.getAuthor().isBot()) return;
    	TextChannel channel = event.getChannel();


    	if(OnOmeChannelJoin.omechannels.containsValue(channel)) {
			ResultSet getUserData = LiteSQL.onQuery("SELECT * FROM 'members' WHERE userid = " + event.getMember().getIdLong());
			
			try {
				if(getUserData.next()) {
					
					boolean isMuted = getUserData.getBoolean("mute");
					String lan = getUserData.getString("selcLan");

					if(isMuted == true) {
						channel.sendMessage(new EmbedBuilder()
								.setTitle("Mute")
								.setDescription(Translations.Mute(lan))
								.setColor(Color.decode("#8d3403"))
								.build()).queue();
						return;
					}
					List<String> BAD_WORDS = Arrays.asList("fick", "fuck", "LGBTQ", "LGBTQ+", "nudes", "nude", "hure", "nutte", "huren", "whore", "bastart", "ficke", "ficken", "porno", "porn", "hurensohn", "schlampe", "fotze", "ficken", "Depp", "idiot", "vollidiot", "arschkriecher", "wichser", "wixxer", "mee6", "wixer", "arsch", "arschloch", "drecksau", "trottel", "hackfresse", "dummkopf", "Dreckschwein", "sau", "bitch", "schwachkopf", "pussy", "pu$$y", "ass", "boobs", "titten", "brüste", "Dickhead", "scheide", "vagina");
                    					
                    			
                    String[] message = event.getMessage().getContentRaw().split(" ");
					for (int i = 0; i < message.length; i++) {
                        for (int j = 0; j < BAD_WORDS.size(); j++) {
                            if(BAD_WORDS.contains(message[j].toLowerCase())) {
                                event.getMessage().addReaction(":dnd:759536079010398239").queue();
                                Main.jda.getTextChannelById(845404663297212466l).sendMessage(new EmbedBuilder()
                                		.setTitle("BadWord")
                                		.setColor(Color.decode("#e05e36"))
                                		.setDescription("Der User **" + event.getMember().getAsMention() + "** / **" + event.getAuthor().getAsTag() + "** \n hat in folgen Satz ein BadWord gesagt: \n ||" + event.getMessage().getContentRaw() + "||")
                                		.build()).queue();
                                return;
                            }
                        }
					}
					if(event.getMessage().getContentDisplay().startsWith(Main.prefix + "next")) {
						return;
					}
					
					if(event.getMessage().getContentDisplay().startsWith("@ping")) {
						
						ChattingHandler.sendPing(channel, event.getMember());
						
						return;
					}
					ChattingHandler.sendingMessage(channel, event.getMessage(), event.getMember(), lan);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
    	}
    }

}
