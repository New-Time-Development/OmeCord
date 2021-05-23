package com.newtime.beta;

import com.newtime.main.Main;
import com.newtime.system.handler.EchoHandler;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;


public class TestCommand extends ListenerAdapter{
	
	@SuppressWarnings("unused")
	private static Guild temp = null;
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		if(e.getMessage().getContentDisplay().equalsIgnoreCase("!test")) {
			
			connectTo(e.getMember().getVoiceState().getChannel());
		}
	}
	
    private void connectTo(VoiceChannel channel)
    {
    	 Guild guild = channel.getGuild();
    	 temp = guild;
    	for(Guild g : Main.jda.getGuilds()) {
    		System.out.println(g);
    		
    	        // Get an audio manager for this guild, this will be created upon first use for each guild
    	        AudioManager audioManager = guild.getAudioManager();
    	        // Create our Send/Receive handler for the audio connection
    	        EchoHandler handler = new EchoHandler();

    	        // The order of the following instructions does not matter!

    	        AudioManager sendM = g.getAudioManager();
    	        // Set the sending handler to our echo system
    	        sendM.setSendingHandler(handler);
    	        
    	        // Set the receiving handler to the same echo system, otherwise we can't echo anything
    	        audioManager.setReceivingHandler(handler);
    	        // Connect to the voice channel
    	       
    	}
    	channel.getGuild().getAudioManager().openAudioConnection(channel); 
       
    }

}
