package com.newtime.listener;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * @author SageSphinx63920
 *
 * Copyright (c) 2019 - 2021 by New Time Development/Sage to present. All rights reserved
 */

public class GithubListener extends ListenerAdapter{
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		
		if(event.getChannel().isNews()) {
			if(event.getChannel().getIdLong() == 851433969119002624l) {
				event.getMessage().crosspost().queue();
			}
		}
		
	}

}
