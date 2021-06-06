package com.newtime.listener.buttons;

import java.time.OffsetDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.Timer;
import java.util.TimerTask;

import com.newtime.main.Main;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * @author SageSphinx63920
 *
 * Copyright (c) 2019 - 2021 by New Time Development/Sage to present. All rights reserved
 */

public class ShutdownButton extends ListenerAdapter{
	
	public void onButtonClick(ButtonClickEvent event) {
		if(event.getComponentId().equalsIgnoreCase("shutdown")) {
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
			
			event.getChannel().sendMessage("Der Bot fährt runter!").queue();
			
			Main.shutdown();
			event.deferEdit().queue();
			event.getHook().deleteOriginal().queue();
			
			event.getMessage().addReaction(":check_mark:845331036737241120").queue();
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
		}
	}

}
