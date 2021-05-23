package com.newtime.system.security;

import com.newtime.listener.OnOmeChannelJoin;
import com.newtime.system.Queue;

import net.dv8tion.jda.api.events.channel.text.TextChannelDeleteEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class OmeChannelDelete extends ListenerAdapter{
	
	/*public void onVoiceChannelDelete(VoiceChannelDeleteEvent e) {
		
			if(OnOmeChannelJoin.omechannels.get(e.getChannel().getIdLong()) != null) {
				OnOmeChannelJoin.activeGuilds.remove(e.getGuild());
				OnOmeChannelJoin.omechannels.remove(e.getChannel().getIdLong(), OnOmeChannelJoin.omechannels.get(e.getChannel().getIdLong()));
			}
		
	}*/
	
	public void onTextChannelDelete(TextChannelDeleteEvent e) {
		
		System.out.println("abc1");
		
		if(Queue.getKeyFromValue(OnOmeChannelJoin.omechannels, e.getChannel()) != null) {
			System.out.println("abc2");
			OnOmeChannelJoin.activeGuilds.remove(e.getGuild());
			OnOmeChannelJoin.omechannels.remove(Queue.getKeyFromValue(OnOmeChannelJoin.omechannels, e.getChannel().getIdLong()), e.getChannel());
			
		}
			
	
}

}
