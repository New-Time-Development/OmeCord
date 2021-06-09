package com.newtime.command.slash;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * @author SageSphinx63920
 *
 * Copyright (c) 2019 - 2021 by New Time Development/Sage to present. All rights reserved
 */

public class PingSlashCommand extends ListenerAdapter{
	
	@SuppressWarnings("unused")
	public void onSlashCommand(SlashCommandEvent event) {
		
		if(event.getName().equalsIgnoreCase("ping")) {
			Guild g = event.getGuild();
			Member m = event.getMember();
			
			long time = System.currentTimeMillis();
				event.reply("> Ping: `" + (System.currentTimeMillis() - time)  / 10 + " ms`").queue();
			
		}
		
	}

}
