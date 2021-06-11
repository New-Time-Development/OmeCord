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

public class EftronSlashCommand extends ListenerAdapter{
	
	@SuppressWarnings("unused")
	public void onSlashCommand(SlashCommandEvent event) {
		
		if(event.getName().equalsIgnoreCase("eftron")) {
			Guild g = event.getGuild();
			Member m = event.getMember();
			
			if(event.getOptions().size() == 0) {
				event.reply("https://top.gg/user/660887621169446964").queue();
			}else {
				String bot = event.getOption("bot").getAsString();
				
				if(bot.equalsIgnoreCase("EftronNormal")) {
					event.reply("Invite it now. I dont want to make more yet lol").queue();
				}else if(bot.equalsIgnoreCase("Eftron24")) {
					event.reply("Invite it now. I dont want to make more yet lol").queue();
				}
			}
			
		}
		
	}

}
