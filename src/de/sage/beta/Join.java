package de.sage.beta;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Join extends ListenerAdapter{
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent e1) {
		
		if(e1.getMessage().getContentDisplay().equals("!join!")) {
			 e1.getGuild().getAudioManager().openAudioConnection(e1.getMember().getVoiceState().getChannel());   
		}
		}
}
